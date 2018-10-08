package com.behincom.behincome.Activityes.CustomerManager;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Accesories.VoiceType;
import com.behincom.behincome.Activityes.Main.fragCustomers;
import com.behincom.behincome.Adapters.Archives.adapArchiveEnter;
import com.behincom.behincome.Adapters.Main.adapMainCustomers;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendAddTask;
import com.behincom.behincome.Datas.Archives.ToSendArchive;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.DataDates;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class fragArchiveEnter extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    Dialog aDialog;
    com.behincom.behincome.Accesories.Dialog pDialog;
    SpinAdapter spinAdapter_SubAct;
    actCustomerManager act = new actCustomerManager();
    RWInterface rInterface;

    TextView lblTitle;
    TextView lblAccept;
    EditText txtSearch;
    ImageView btnFilter;
    ImageView imgBack;
    ImageView btnCheck;
    ImageView imgVoice;
    RecyclerView lstMain;
    CardView cardView;
    TextView dialog_lblTitle;
    TextView dialog_btnCancell;
    TextView dialog_btnAccept;
    CardView dialog_cardViewMain;
    RecyclerView.LayoutManager mLayoutManager;

    private List<Customers> lCustomer = new ArrayList<>();
    private static List<Customers> lCustomer2 = new ArrayList<>();
    List<Basic_ArchiveTypes> lDataArchive = new ArrayList<>();
    
    public static fragArchiveEnter newInstance(Context mContext){
        fragArchiveEnter fragment = new fragArchiveEnter();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_archive_enter, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblAccept = view.findViewById(R.id.lblAccept);
        txtSearch = view.findViewById(R.id.txtSearch);
        btnFilter = view.findViewById(R.id.btnFilter);
        imgBack = view.findViewById(R.id.imgBack);
        imgVoice = view.findViewById(R.id.imgVoice);
        lstMain = view.findViewById(R.id.lstMain);
        btnCheck = view.findViewById(R.id.btnCheck);
        cardView = view.findViewById(R.id.cardView);

//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblTitle.setTypeface(tFace);
//        lblAccept.setTypeface(tFace);
//        txtSearch.setTypeface(tFace);

        lstMain.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        lblTitle.setText("بایگانی");
        lblAccept.setText("انتخاب نوع بایگانی");
        imgBack.setVisibility(View.VISIBLE);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.CustomerManager);
            }
        });
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lCustomer2 = new ArrayList<>();
                for (Customers data : lCustomer) {
                    if(data.CustomerName.contains(txtSearch.getText().toString()))
                        lCustomer2.add(data);
                }
                RefreshList();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = false;
                for (Customers data : lCustomer) {
                    if(data.isCheck) {
                        isChecked = true;
                        break;
                    }
                }
                if(isChecked) {
                    aDialog = new Dialog(context);
                    aDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    aDialog.setCancelable(true);
                    aDialog.setCanceledOnTouchOutside(true);
                    aDialog.setContentView(R.layout.dialog_select_archive);
                    Objects.requireNonNull(aDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                    dialog_btnCancell = aDialog.findViewById(R.id.btnCancell);
                    dialog_cardViewMain = aDialog.findViewById(R.id.cardViewMain);
                    final Spinner spinArchive = aDialog.findViewById(R.id.spinArchive);
                    Spinner spinDay = aDialog.findViewById(R.id.spinDay);
                    Spinner spinMonth = aDialog.findViewById(R.id.spinMonth);
                    Spinner spinYear = aDialog.findViewById(R.id.spinYear);
                    TextView btnTimeTitle = aDialog.findViewById(R.id.btnTimeTitle);
                    TextView lblTitle = aDialog.findViewById(R.id.lblTitle);
                    TextView btnCancell = aDialog.findViewById(R.id.lblCancell);
                    TextView btnAccept = aDialog.findViewById(R.id.lblAccept);

//                    Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                    lblTitle.setTypeface(tFace);
//                    btnTimeTitle.setTypeface(tFace);
//                    btnCancell.setTypeface(tFace);
//                    btnAccept.setTypeface(tFace);

                    //TODO Fill below line code
                    lDataArchive = geter.getList(Basic_ArchiveTypes.class);
                    Basic_ArchiveTypes data = new Basic_ArchiveTypes();
                    data.ArchiveTypeTitle=("نوع بایگانی");
                    lDataArchive.add(0, data);
                    spinAdapter_SubAct = new SpinAdapter(context, lDataArchive, "ArchiveTypeTitle");
                    spinArchive.setAdapter(spinAdapter_SubAct);

                    SpinAdapter adapter1 = new SpinAdapter(context, Day(), "name");
                    spinDay.setAdapter(adapter1);
                    SpinAdapter adapter2 = new SpinAdapter(context, Month(), "name");
                    spinMonth.setAdapter(adapter2);
                    SpinAdapter adapter3 = new SpinAdapter(context, Year(), "name");
                    spinYear.setAdapter(adapter3);

                    spinYear.setSelection(27);
                    spinMonth.setSelection(2);
                    spinDay.setSelection(8);

                    btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isSelected = false;
                            for (Customers datap : lCustomer) {
                                if(datap.isCheck)
                                    isSelected = true;
                            }
                            if(isSelected) {
                                pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                                pDialog.Show();

                                List<ToSendArchive> lToSend = new ArrayList<>();
                                for (Customers data : lCustomer) {
                                    if (data.isCheck) {
                                        ToSendArchive datas = new ToSendArchive();
                                        datas.CustomerID = data.CustomerID;
//                                        datas.ArchiveTypeID = Integer.parseInt(spinAdapter_SubAct.getItemString(spinArchive.getSelectedItemPosition(), "ArchiveTypeID"));
                                        lToSend.add(datas);
                                    }
                                }

                                ObjectMapper oMapper = new ObjectMapper();
                                List<Map> maps = new ArrayList<>();
                                for (ToSendArchive data : lToSend) {
                                    Map map = oMapper.convertValue(data, Map.class);
                                    maps.add(map);
                                }

//                                Call ChangeStates = rInterface.RQAddCustomersToArchive(Setting.getToken(), maps);
//                                ChangeStates.enqueue(new Callback() {
//                                    @Override
//                                    public void onResponse(Call call, Response response) {
//                                        if(response.isSuccessful()){
//                                            getActivity().runOnUiThread(new Runnable() {
//
//                                                @Override
//                                                public void run() {
//                                                    Toast.makeText(context, "فروشگاه ها بایگانی شدند", Toast.LENGTH_LONG).show();
//                                                    act.getFragByState(FragmentState.CustomerManager);
//                                                }
//                                            });
//                                        }
//                                        pDialog.DisMiss();
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call call, Throwable t) {
//                                        pDialog.DisMiss();
//                                    }
//                                });
                            }
                        }
                    });
                    btnCancell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            aDialog.dismiss();
                        }
                    });

                    aDialog.show();
                }else{
                    Toast.makeText(context, "لطفا فروشگاه یا فروشگاه هایی را انتخاب کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
        imgVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        rInterface = Retrofite.getClient().create(RWInterface.class);
        getCustomers();
    }

    private void RefreshList(){
        adapArchiveEnter adapter = new adapArchiveEnter(lCustomer, context);
        lstMain.setAdapter(adapter);
    }
    private void getCustomers(){
        try {
            pDialog = new com.behincom.behincome.Accesories.Dialog(getActivity());
            pDialog.Show();

            Call cGetAll = rInterface.RQGetCustomerAllData(Setting.getToken());
            cGetAll.enqueue(new Callback<List<Customers>>() {
                @Override
                public void onResponse(Call<List<Customers>> call, Response<List<Customers>> response) {
                    if(response.isSuccessful()){
                        lCustomer = response.body();
                        adapArchiveEnter adapter = new adapArchiveEnter(lCustomer, context);
                        lstMain.setAdapter(adapter);
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    pDialog.DisMiss();
                }
            });
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }

    private List<DataDates> Day(){
        List<DataDates> mDay = new ArrayList<>();
        for(int i=1; i<32; i++){
            DataDates data = new DataDates();
            String nn = "";
            if(i<10)
                nn = "0" + i;
            else
                nn = Integer.toString(i);
            data.name(nn);
            mDay.add(data);
        }
        return mDay;
    }
    private List<DataDates> Month(){
        List<DataDates> mMonth = new ArrayList<>();
        for(int i=1; i<13; i++){
            DataDates data = new DataDates();
            String nn = "";
            if(i<10)
                nn = "0" + i;
            else
                nn = Integer.toString(i);
            data.name(nn);
            mMonth.add(data);
        }
        return mMonth;
    }
    private List<DataDates> Year(){
        List<DataDates> mYear = new ArrayList<>();
        for(int i=1370; i<1451; i++){
            DataDates data = new DataDates();
            String nn = "";
            if(i<10)
                nn = "0" + i;
            else
                nn = Integer.toString(i);
            data.name(nn);
            mYear.add(data);
        }
        return mYear;
    }
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    public void startVoiceRecognitionActivity() {
        VoiceType vc = new VoiceType(context, VOICE_RECOGNITION_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            txtSearch.setText(matches.get(0));
        }
    }

}
