package com.behincom.behincome.Activityes.CustomerManager;


import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Accesories.VoiceType;
import com.behincom.behincome.Adapters.Archives.adapArchiveExit;
import com.behincom.behincome.Datas.Archives.ToSendExitArchive;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class fragArchiveExit extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    Dialog pDialog;
    RWInterface rInterface;
    actCustomerManager act = new actCustomerManager();

    TextView lblTitle;
    TextView lblAccept;
    EditText txtSearch;
    ImageView btnFilter;
    ImageView imgBack;
    ImageView btnCheck;
    ImageView imgVoice;
    RecyclerView lstMain;
    CardView cardView;
    RecyclerView.LayoutManager mLayoutManager;

    private List<Customers> lCustomer = new ArrayList<>();
    private List<Customers> lCustomer2 = new ArrayList<>();

    public static fragArchiveExit newInstance(Context mContext) {
        fragArchiveExit fragment = new fragArchiveExit();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_archive_exit, container, false);

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

        lblTitle.setText("خروج از بایگانی");
        lblAccept.setText("خروج از بایگانی");
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
                    if (data.CustomerName.contains(txtSearch.getText().toString()))
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
                    if (data.isCheck) {
                        isChecked = true;
                        break;
                    }
                }
                if (isChecked) {
                    List<ToSendExitArchive> lCustomers = new ArrayList<>();
                    for (Customers data : lCustomer) {
                        if (data.isCheck) {
                            ToSendExitArchive dat = new ToSendExitArchive();
                            dat.CustomerID = data.CustomerID;
                            lCustomers.add(dat);
                        }
                    }
                    pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                    pDialog.Show();

                    ObjectMapper oMapper = new ObjectMapper();
                    List<Map> maps = new ArrayList<>();
                    for (ToSendExitArchive data : lCustomers) {
                        Map map = oMapper.convertValue(data, Map.class);
                        maps.add(map);
                    }

                    Call ChangeStates = rInterface.RQAddCustomersToExitArchive(Setting.getToken(), maps);
                    ChangeStates.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getActivity(), "فروشگاه ها از بایگانی خارج شدند", Toast.LENGTH_SHORT).show();
                                act.getFragByState(FragmentState.CustomerManager);
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                } else {
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

    private void RefreshList() {
        adapArchiveExit adapter = new adapArchiveExit(lCustomer2, context);
        lstMain.setAdapter(adapter);
    }

    private void getCustomers() {
        try {
            pDialog = new com.behincom.behincome.Accesories.Dialog(getActivity());
            pDialog.Show();

            Call cGetAll = rInterface.RQGetCustomerAllDataArchived(Setting.getToken());
            cGetAll.enqueue(new Callback<List<Customers>>() {
                @Override
                public void onResponse(Call<List<Customers>> call, Response<List<Customers>> response) {
                    if (response.isSuccessful()) {
                        lCustomer = response.body();
                        adapArchiveExit adapter = new adapArchiveExit(lCustomer, context);
                        lstMain.setAdapter(adapter);
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    pDialog.DisMiss();
                }
            });
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
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
