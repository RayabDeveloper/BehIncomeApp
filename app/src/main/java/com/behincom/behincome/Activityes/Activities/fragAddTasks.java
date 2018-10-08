package com.behincom.behincome.Activityes.Activities;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
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

import com.android.volley.Request;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Accesories.VoiceType;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.Activities.adapGroupTask;
import com.behincom.behincome.Adapters.Main.adapMainCustomers;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.DataGroupTask;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendAddTask;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class fragAddTasks extends Fragment {

    static Context context;
    static Context contexti;
    actActivities act = new actActivities();
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    Dialog mDialog;
    adapGroupTask adapter;
    static adapGroupTask adapteri;
    SpinAdapter spinAdapter_SubAct, spinAdapter_Period;
    actMain actMaine = new actMain();
    RWInterface rInterface;

    TextView lblTitle;
    TextView lblAccept;
    EditText txtSearch;
    ImageView btnFilter;
    ImageView imgBack;
    ImageView btnCheck;
    ImageView imgVoice;
    FloatingActionButton btnLocation;
    RecyclerView lstMain;
    CardView cardView;
    RecyclerView.LayoutManager mLayoutManager;

    private List<Customers> lCustomer = new ArrayList<>();
    private List<Customers> lCustomer2 = new ArrayList<>();
    public static List<ToSendAddTask> lActTask = new ArrayList<>();

    public static DataGroupTask dTask = new DataGroupTask();
    public static int MaxCount = 0;
    public static int CurrentCountNumber = 0;

    public static fragAddTasks newInstance(Context mContext) {
        fragAddTasks fragment = new fragAddTasks();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_tasks, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblAccept = view.findViewById(R.id.lblAccept);
        txtSearch = view.findViewById(R.id.txtSearch);
        btnFilter = view.findViewById(R.id.btnFilter);
        imgBack = view.findViewById(R.id.imgBack);
        btnLocation = view.findViewById(R.id.btnLocation);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgVoice = view.findViewById(R.id.imgVoice);
        lstMain = view.findViewById(R.id.lstMain);
        cardView = view.findViewById(R.id.cardView);

        contexti = context;

        lstMain.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        lblTitle.setText("ایجاد وظیفه گروهی");
//        lblAccept.setText("تایید");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), actMain.class);
                getActivity().startActivity(intent);
                getActivity().finish();
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
                int i = 0;
                for(int ii=0; ii<lCustomer.size(); ii++){
                    for(int j=ii+1; j<lCustomer.size(); j++){
                        if(lCustomer.get(ii).count_number > lCustomer.get(j).count_number){
                            Customers data = new Customers();
                            data.CustomerID = lCustomer.get(ii).CustomerID;
                            data.CustomerAddedByUserID = lCustomer.get(ii).CustomerAddedByUserID;
                            data.CustomerOwenerUserID = lCustomer.get(ii).CustomerOwenerUserID;
                            data.ArchiveTypeID = lCustomer.get(ii).ArchiveTypeID;
                            data.CustomerStateID = lCustomer.get(ii).CustomerStateID;
                            data.CustomerStatusID = lCustomer.get(ii).CustomerStatusID;
                            data.CityID = lCustomer.get(ii).CityID;
                            data.NamePrefixID = lCustomer.get(ii).NamePrefixID;
                            data.ArchiveDate = lCustomer.get(ii).ArchiveDate;
                            data.CustomerName = lCustomer.get(ii).CustomerName;
                            data.CustomerAddress = lCustomer.get(ii).CustomerAddress;
                            data.CustomerLatitude = lCustomer.get(ii).CustomerLatitude;
                            data.CustomerLongitude = lCustomer.get(ii).CustomerLongitude;
                            data.CustomerDescription = lCustomer.get(ii).CustomerDescription;
                            data.Deleted = lCustomer.get(ii).Deleted;
                            data.LastUpdateDate = lCustomer.get(ii).LastUpdateDate;
                            data.isCheck = lCustomer.get(ii).isCheck;
                            data.count_number = lCustomer.get(ii).count_number;

                            lCustomer.get(ii).CustomerID = lCustomer.get(j).CustomerID;
                            lCustomer.get(ii).CustomerAddedByUserID = lCustomer.get(j).CustomerAddedByUserID;
                            lCustomer.get(ii).CustomerOwenerUserID = lCustomer.get(j).CustomerOwenerUserID;
                            lCustomer.get(ii).ArchiveTypeID = lCustomer.get(j).ArchiveTypeID;
                            lCustomer.get(ii).CustomerStateID = lCustomer.get(j).CustomerStateID;
                            lCustomer.get(ii).CustomerStatusID = lCustomer.get(j).CustomerStatusID;
                            lCustomer.get(ii).CityID = lCustomer.get(j).CityID;
                            lCustomer.get(ii).NamePrefixID = lCustomer.get(j).NamePrefixID;
                            lCustomer.get(ii).ArchiveDate = lCustomer.get(j).ArchiveDate;
                            lCustomer.get(ii).CustomerName = lCustomer.get(j).CustomerName;
                            lCustomer.get(ii).CustomerAddress = lCustomer.get(j).CustomerAddress;
                            lCustomer.get(ii).CustomerLatitude = lCustomer.get(j).CustomerLatitude;
                            lCustomer.get(ii).CustomerLongitude = lCustomer.get(j).CustomerLongitude;
                            lCustomer.get(ii).CustomerDescription = lCustomer.get(j).CustomerDescription;
                            lCustomer.get(ii).Deleted = lCustomer.get(j).Deleted;
                            lCustomer.get(ii).LastUpdateDate = lCustomer.get(j).LastUpdateDate;
                            lCustomer.get(ii).isCheck = lCustomer.get(j).isCheck;
                            lCustomer.get(ii).count_number = lCustomer.get(j).count_number;

                            lCustomer.get(j).CustomerID = data.CustomerID;
                            lCustomer.get(j).CustomerAddedByUserID = data.CustomerAddedByUserID;
                            lCustomer.get(j).CustomerOwenerUserID = data.CustomerOwenerUserID;
                            lCustomer.get(j).ArchiveTypeID = data.ArchiveTypeID;
                            lCustomer.get(j).CustomerStateID = data.CustomerStateID;
                            lCustomer.get(j).CustomerStatusID = data.CustomerStatusID;
                            lCustomer.get(j).CityID = data.CityID;
                            lCustomer.get(j).NamePrefixID = data.NamePrefixID;
                            lCustomer.get(j).ArchiveDate = data.ArchiveDate;
                            lCustomer.get(j).CustomerName = data.CustomerName;
                            lCustomer.get(j).CustomerAddress = data.CustomerAddress;
                            lCustomer.get(j).CustomerLatitude = data.CustomerLatitude;
                            lCustomer.get(j).CustomerLongitude = data.CustomerLongitude;
                            lCustomer.get(j).CustomerDescription = data.CustomerDescription;
                            lCustomer.get(j).Deleted = data.Deleted;
                            lCustomer.get(j).LastUpdateDate = data.LastUpdateDate;
                            lCustomer.get(j).isCheck = data.isCheck;
                            lCustomer.get(j).count_number = data.count_number;
                        }
                    }
                }
                for (Customers data : lCustomer) {
                    if (data.isCheck) {
                        ToSendAddTask mData = new ToSendAddTask();
                        mData.CustomerID = (data.CustomerID);
                        mData.Title = (data.CustomerName);
                        String mH = Integer.toString(dTask.lSpacing().get(i).cTime().getHours());
                        String mM = Integer.toString(dTask.lSpacing().get(i).cTime().getMinutes());
                        if (mH.length() == 1) mH = "0" + mH;
                        if (mM.length() == 1) mM = "0" + mM;
                        String mDate = dTask.lSpacing().get(i).mDate() + "T" + mH + ":" + mM + ":00";
                        mData.TaskDate = (mDate);
                        mData.DurationDate = (Integer.toString(dTask.lSpacing().get(i).duration()));//todo todo todo Duration Must be Convert to DateTime
                        mData.ActID = dTask.subActId();
                        mData.DurationDate = "2018-01-01T00:" + dTask.lSpacing().get(0).duration() + ":00";
                        mData.VisitTourID = dTask.visitorTourId();
                        lActTask.add(mData);
                        i++;
                    }
                }
                if (lActTask.size() > 0) {
                    act.getFragByState(FragmentState.AddTasksShow);
                    fragAddTasksShow.lActTask.addAll(lActTask);
                    fragAddTasksShow.dTask = dTask;
                } else
                    Toast.makeText(context, "فروشگاه یا فروشگاه هایی را انتخاب کنید", Toast.LENGTH_LONG).show();
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lCustomer.size() > 0) {
                    fragTasksShowMap.lCustomer = lCustomer;
                    act.getFragByState(FragmentState.TaskShow);
                } else Toast.makeText(context, "فروشگاهی موجود نیست", Toast.LENGTH_SHORT).show();
            }
        });
        CurrentCountNumber = 0;


        getCustomers();
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
        adapter = new adapGroupTask(lCustomer, context);
        adapteri = new adapGroupTask(lCustomer, contexti);
        lstMain.setAdapter(adapter);
    }
    private void RefreshList() {
        adapter = new adapGroupTask(lCustomer2, context);
        adapteri = new adapGroupTask(lCustomer2, contexti);
        lstMain.setAdapter(adapter);
    }
    public void setList(List<Customers> lList) {
        this.lCustomer = lList;
    }
    private void getCustomers() {
        try {
            rInterface = Retrofite.getClient().create(RWInterface.class);

            String user = Setting.Load("userName");

            Call cGetAll = rInterface.RQGetCustomerAllData(Setting.getToken());
            cGetAll.enqueue(new Callback<List<Customers>>() {
                @Override
                public void onResponse(Call<List<Customers>> call, Response<List<Customers>> response) {
                    if (response.isSuccessful()) {
                        lCustomer = response.body();
//                        adapMainCustomers adapter = new adapMainCustomers(lCustomer, getActivity());
//                        lstMain.setAdapter(adapter);
                        if(lActTask.size() > 0){
                            CurrentCountNumber++;
                            for (ToSendAddTask data : lActTask) {
                                for (Customers custom : lCustomer) {
                                    if(data.CustomerID == custom.CustomerID) {
                                        custom.isCheck = true;
                                        custom.count_number = CurrentCountNumber++;
                                    }
                                }
                            }
                        }
                        lActTask = new ArrayList<>();

                        adapter = new adapGroupTask(lCustomer, context);
                        adapteri = new adapGroupTask(lCustomer, contexti);
                        lstMain.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    String CheShode = t.getMessage();
                    String ooG = "ASD";
                }
            });
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }

//        Map<String, String> Header = Data.Header_Patch(context);
//        Map<String, String> Body = new HashMap<>();
//
//        QueryMacker QMacker = new QueryMacker();
//        String Q = QMacker.MethodInputer(Enums.SQLMethod.SELECT, Enums.Tables.TB_MB);
//        List<Data.MarketerBusinesman> lMBb = SQL.Select(Q, Data.MarketerBusinesman.class, context);
//        String MBId = Integer.toString(lMBb.get(0).id());
//
//        WebApi wAPI_Customers = new WebApi();
//        String mURL = "";
//        try {
//            String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
//            mURL = Data.APIKeys.Arvhices + Uri.encode(MBId + Data.APIKeys.getCustomerNotArchives, ALLOWED_URI_CHARS);
//        }catch (Exception Ex){
//            String Er = Ex.getMessage();
//        }
//        wAPI_Customers.API(Request.Method.GET, mURL, Header, Body);
//
//        wAPI_Customers.setOnIncomingResult(new WebApi.onResponseResult() {
//            @Override
//            public void onResponseResults(String Result, int StatusCode) {
//                try {
//                    JSONObject jo = new JSONObject(Result);
//                    JSONArray ja = jo.getJSONArray("data");
//
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<DataCustomer>>() {}.getType();
//                    lCustomer = gson.fromJson(ja.toString(), type);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter = new adapGroupTask(lCustomer, context);
//                            adapteri = new adapGroupTask(lCustomer, contexti);
//                            lstMain.setAdapter(adapter);
//                        }
//                    });
//                }catch (Exception Ex){
//                    String Er = Ex.getMessage();
//                }
//            }
//        });
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
    private void Finisher(){
        lCustomer = new ArrayList<>();
        lCustomer2 = new ArrayList<>();
        lActTask = new ArrayList<>();
        dTask = new DataGroupTask();
        MaxCount = 0;
        CurrentCountNumber = 0;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }

    private void changer(Customers data1, Customers data2){
        Customers data = new Customers();
        data.CustomerID = data1.CustomerID;
        data.CustomerAddedByUserID = data1.CustomerAddedByUserID;
        data.CustomerOwenerUserID = data1.CustomerOwenerUserID;
        data.ArchiveTypeID = data1.ArchiveTypeID;
        data.CustomerStateID = data1.CustomerStateID;
        data.CustomerStatusID = data1.CustomerStatusID;
        data.CityID = data1.CityID;
        data.NamePrefixID = data1.NamePrefixID;
        data.ArchiveDate = data1.ArchiveDate;
        data.CustomerName = data1.CustomerName;
        data.CustomerAddress = data1.CustomerAddress;
        data.CustomerLatitude = data1.CustomerLatitude;
        data.CustomerLongitude = data1.CustomerLongitude;
        data.CustomerDescription = data1.CustomerDescription;
        data.Deleted = data1.Deleted;
        data.LastUpdateDate = data1.LastUpdateDate;
        data.isCheck = data1.isCheck;
        data.count_number = data1.count_number;

        data1.CustomerID = data2.CustomerID;
        data1.CustomerAddedByUserID = data2.CustomerAddedByUserID;
        data1.CustomerOwenerUserID = data2.CustomerOwenerUserID;
        data1.ArchiveTypeID = data2.ArchiveTypeID;
        data1.CustomerStateID = data2.CustomerStateID;
        data1.CustomerStatusID = data2.CustomerStatusID;
        data1.CityID = data2.CityID;
        data1.NamePrefixID = data2.NamePrefixID;
        data1.ArchiveDate = data2.ArchiveDate;
        data1.CustomerName = data2.CustomerName;
        data1.CustomerAddress = data2.CustomerAddress;
        data1.CustomerLatitude = data2.CustomerLatitude;
        data1.CustomerLongitude = data2.CustomerLongitude;
        data1.CustomerDescription = data2.CustomerDescription;
        data1.Deleted = data2.Deleted;
        data1.LastUpdateDate = data2.LastUpdateDate;
        data1.isCheck = data2.isCheck;
        data1.count_number = data2.count_number;

        data2.CustomerID = data.CustomerID;
        data2.CustomerAddedByUserID = data.CustomerAddedByUserID;
        data2.CustomerOwenerUserID = data.CustomerOwenerUserID;
        data2.ArchiveTypeID = data.ArchiveTypeID;
        data2.CustomerStateID = data.CustomerStateID;
        data2.CustomerStatusID = data.CustomerStatusID;
        data2.CityID = data.CityID;
        data2.NamePrefixID = data.NamePrefixID;
        data2.ArchiveDate = data.ArchiveDate;
        data2.CustomerName = data.CustomerName;
        data2.CustomerAddress = data.CustomerAddress;
        data2.CustomerLatitude = data.CustomerLatitude;
        data2.CustomerLongitude = data.CustomerLongitude;
        data2.CustomerDescription = data.CustomerDescription;
        data2.Deleted = data.Deleted;
        data2.LastUpdateDate = data.LastUpdateDate;
        data2.isCheck = data.isCheck;
        data2.count_number = data.count_number;
    }

}
