package com.behincom.behincome.Activityes.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Activityes.fragTest;
import com.behincom.behincome.Adapters.Activities.adapFactors;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragAddTask extends Fragment {

    static Context context;
    RSQLGeter geter = new RSQLGeter();
    DateConverter DC = new DateConverter();
    Dialog pDialog;
    SpinAdapter spinAdapter_SubAct, spinAdapter_Result;
    Time now;
    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
    actActivities act = new actActivities();
    android.app.Dialog mDialog;

    TextView lblTitle, lblEnterInfo, lblExitInfo, lblTaskInfo;
    ImageView btnCheck, imgBack, btnReNew;
    LinearLayout viewEnterExit, viewEnterShow, viewEnterInfo, viewExitShow, viewExitInfo, viewTaskShow, infoTask;
    RelativeLayout viewEnterExitEnd, viewTaskInfo;
    TextInputEditText txtTitle, txtDescription;
    Spinner spinSubAct, spinResult;
    RecyclerView lstFactor;
    FloatingActionButton btnNewInvoice;
    CardView btnSend;

    public static Activities Activity = new Activities();
    List<Basic_Acts> lSubAct = new ArrayList<>();
    List<Basic_ActResults> lSubActResult = new ArrayList<>();
    public static List<Invoice> lInvoice = new ArrayList<>();
    private static String Title = "", Description = "";
    private static int SelectedAct = 0, SelectedResult = 0;
    private static String NowDateTime = "2018-01-01T11:11:11";

    public static fragAddTask newInstance(Context mContext) {
        fragAddTask fragment = new fragAddTask();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_task, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblEnterInfo = view.findViewById(R.id.lblEnterInfo);
        lblExitInfo = view.findViewById(R.id.lblExitInfo);
        lblTaskInfo = view.findViewById(R.id.lblTaskInfo);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        btnReNew = view.findViewById(R.id.btnReNew);
        viewEnterExit = view.findViewById(R.id.viewEnterExit);
        viewEnterShow = view.findViewById(R.id.viewEnterShow);
        viewEnterInfo = view.findViewById(R.id.viewEnterInfo);
        viewExitShow = view.findViewById(R.id.viewExitShow);
        viewExitInfo = view.findViewById(R.id.viewExitInfo);
        viewTaskShow = view.findViewById(R.id.viewTaskShow);
        infoTask = view.findViewById(R.id.infoTask);
        viewEnterExitEnd = view.findViewById(R.id.viewEnterExitEnd);
        viewTaskInfo = view.findViewById(R.id.viewTaskInfo);
        txtTitle = view.findViewById(R.id.txtName);
        txtDescription = view.findViewById(R.id.txtDetails);
        spinSubAct = view.findViewById(R.id.spinSubAct);
        spinResult = view.findViewById(R.id.spinResult);
        lstFactor = view.findViewById(R.id.lstFactor);
        btnNewInvoice = view.findViewById(R.id.btnNewInvoice);
        btnSend = view.findViewById(R.id.cardAcceptTask);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo Back to What ???
            }
        });
        spinSubAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedAct = spinSubAct.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedResult = spinResult.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Title = txtTitle.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Description = txtDescription.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        viewEnterShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                HashMap<String, Object> map = new HashMap<>();
                if(Activity.ActivityID > 0) {//EnterForTask
                    map.put("ActivityID", Activity.ActivityID);
                    map.put("Title", Activity.ActivityID);
                    map.put("ActID", Activity.ActivityID);
                    map.put("ActivityDescription", Activity.ActivityID);
                    map.put("EnterDate", Activity.ActivityID);
                    map.put("EnterLatutide", Activity.ActivityID);
                    map.put("EnterLongitude", Activity.ActivityID);

                    Call EnterForTask = rInterface.RQAddActivityEnterForTask(Setting.getToken(), map);
                    EnterForTask.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        viewShower(viewEnterInfo, viewExitShow, viewEnterExit, btnNewInvoice, lstFactor, spinResult);
                                    }else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String value = entry.getValue().toString();
                                            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (Exception Ex) {
                                    String Er = Ex.getMessage();
                                }
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            pDialog.DisMiss();
                        }
                    });
                }else{//EnterForNewActivity
                    map.put("ParentID", Activity.ParentID);
                    map.put("ActivityAddedByUserID", Activity.ActivityAddedByUserID);
                    map.put("ActivityOwnerUserID", Activity.ActivityOwnerUserID);
                    map.put("CustomerID", Activity.CustomerID);
                    map.put("Title", Activity.Title);
                    map.put("ActID", Activity.ActID);
                    map.put("ActivityDescription", Activity.ActivityDescription);
                    map.put("EnterDate", Activity.EnterDate);
                    map.put("EnterLatutide", Activity.EnterLatutide);
                    map.put("EnterLongitude", Activity.EnterLongitude);

                    Call Enter = rInterface.RQAddActivityEnter(Setting.getToken(), map);
                    Enter.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        viewShower(viewEnterInfo, viewExitShow, viewEnterExit, btnNewInvoice, lstFactor, spinResult);
                                    }else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String value = entry.getValue().toString();
                                            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                        }
                                    }else if (result.Type.equalsIgnoreCase(ResponseMessageType.Warning.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String key = entry.getKey();
                                            String value = entry.getValue().toString();
                                            if(!key.equalsIgnoreCase("ActivityID"))
                                                Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                            else{
                                                //todo Call That Activity And Goto That
                                            }
                                        }
                                    }
                                } catch (Exception Ex) {
                                    String Er = Ex.getMessage();
                                }
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        viewExitShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                if(lInvoice.size() > 0){
                    MultipartBody.Part[] body = new MultipartBody.Part[lInvoice.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                    for (int i = 0; i < lInvoice.get(0).InvoiceImage.size(); i++) {
                        File file = new File(lInvoice.get(i).InvoiceImage.get(i).ImageFilename);
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                        body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                    }

                    Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                    addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                List<HashMap<String, Object>> lMapURLs = new ArrayList<>();
                                try {
                                    Object[] keys = response.body().AdditionalData.keySet().toArray();
                                    for (Object data : keys) {
                                        String val = response.body().AdditionalData.get(data.toString()).toString();
                                        HashMap<String, Object> mapURLs = new HashMap<>();
                                        mapURLs.put("InvoiceFileName", val);
                                        lMapURLs.add(mapURLs);


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                List<HashMap<String, Object>> lMapInvoice = new ArrayList<>();
                                for (Invoice datas : lInvoice) {
                                    HashMap<String, Object> mapInvoice = new HashMap<>();
                                    mapInvoice.put("InvoiceNumber", datas.InvoiceNumber);
                                    mapInvoice.put("InvoiceMarketingProductID", datas.InvoiceMarketingProductID);
                                    mapInvoice.put("InvoiceActivityID", datas.InvoiceActivityID);
                                    mapInvoice.put("InvoicePrice", datas.InvoicePrice);
                                    mapInvoice.put("InvoiceDescription", datas.InvoiceDescription);
                                    mapInvoice.put("InvoiceImages", lMapURLs);

                                    lMapInvoice.add(mapInvoice);
                                }

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("ActivityID", Activity.ActivityID);
                                map.put("ActivityResultID", Activity.ActivityID);
                                map.put("ActivityDescription", Activity.ActivityID);
                                map.put("ExitDate", Activity.ActivityID);
                                map.put("ExitLatutide", Activity.ActivityID);
                                map.put("ExitLongitude", Activity.ActivityID);
                                map.put("Invoices", lMapInvoice);

                                Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                                cExitActivity.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                SimpleResponse result = response.body();
                                                if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                                    viewShower(viewEnterInfo, viewExitInfo, viewEnterExitEnd, viewEnterExit, btnNewInvoice, lstFactor, spinResult, btnSend);
                                                }else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                                    for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                                        String value = entry.getValue().toString();
                                                        Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            } catch (Exception Ex) {
                                                Ex.printStackTrace();
                                            }
                                        }
                                        pDialog.DisMiss();
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        pDialog.DisMiss();
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            pDialog.DisMiss();
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ActivityID", Activity.ActivityID);
                    map.put("ActivityResultID", Activity.ActivityID);
                    map.put("ActivityDescription", Activity.ActivityID);
                    map.put("ExitDate", Activity.ActivityID);
                    map.put("ExitLatutide", Activity.ActivityID);
                    map.put("ExitLongitude", Activity.ActivityID);
                    map.put("Invoices", new HashMap<String, Object>());

                    Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                    cExitActivity.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        viewShower(viewEnterInfo, viewExitInfo, viewEnterExitEnd, viewEnterExit, btnNewInvoice, lstFactor, spinResult, btnSend);
                                    }else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String value = entry.getValue().toString();
                                            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (Exception Ex) {
                                    Ex.printStackTrace();
                                }
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        viewTaskShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.AddTaskSetTime);
            }
        });
        btnReNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewShower(viewEnterExit, viewEnterShow, viewTaskShow);
            }
        });
        btnNewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.AddFactor);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                HashMap<String, Object> map = new HashMap<>();
                map.put("ActivityID", Activity.ActivityID);
                map.put("Description", Activity.ActivityDescription);
                map.put("Invoices", Activity.Invoice);
                map.put("ActivityResultID", Activity.ActivityResultID);

                Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                cConfirm.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            try {
                                SimpleResponse result = response.body();
                                if(result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                    Intent intent = new Intent(context, actMain.class);
                                    actMain.STATE = FragmentState.MainCustomers;
                                    getActivity().startActivity(intent);
                                    getActivity().finish();
                                }else if(result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                    for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                        String value = entry.getValue().toString();
                                        Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                    }
                                }
                            } catch (Exception Ex) {
                                String Er = Ex.getMessage();
                            }
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    mToast("خطا در برقراری ارتباط با سرور.");
                                }
                            });
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        pDialog.DisMiss();
                    }
                });
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                if(lInvoice.size() > 0){
                    MultipartBody.Part[] body = new MultipartBody.Part[lInvoice.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                    for (int i = 0; i < lInvoice.get(0).InvoiceImage.size(); i++) {
                        File file = new File(lInvoice.get(i).InvoiceImage.get(i).ImageFilename);
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                        body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                    }

                    Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                    addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                List<HashMap<String, Object>> lMapURLs = new ArrayList<>();
                                try {
                                    Object[] keys = response.body().AdditionalData.keySet().toArray();
                                    for (Object data : keys) {
                                        String val = response.body().AdditionalData.get(data.toString()).toString();
                                        HashMap<String, Object> mapURLs = new HashMap<>();
                                        mapURLs.put("InvoiceFileName", val);
                                        lMapURLs.add(mapURLs);


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                List<HashMap<String, Object>> lMapInvoice = new ArrayList<>();
                                for (Invoice datas : lInvoice) {
                                    HashMap<String, Object> mapInvoice = new HashMap<>();
                                    mapInvoice.put("InvoiceNumber", datas.InvoiceNumber);
                                    mapInvoice.put("InvoiceMarketingProductID", datas.InvoiceMarketingProductID);
                                    mapInvoice.put("InvoiceActivityID", datas.InvoiceActivityID);
                                    mapInvoice.put("InvoicePrice", datas.InvoicePrice);
                                    mapInvoice.put("InvoiceDescription", datas.InvoiceDescription);
                                    mapInvoice.put("InvoiceImages", lMapURLs);

                                    lMapInvoice.add(mapInvoice);
                                }

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("ActivityID", Activity.ActivityID);
                                map.put("ActivityResultID", Activity.ActivityID);
                                map.put("ActivityDescription", Activity.ActivityID);
                                map.put("Invoices", lMapInvoice);

                                Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                                cExitActivity.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                SimpleResponse result = response.body();
                                                if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                                    Intent intent = new Intent(context, actMain.class);
                                                    actMain.STATE = FragmentState.MainCustomers;
                                                    getActivity().startActivity(intent);
                                                    getActivity().finish();
                                                }else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                                    for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                                        String value = entry.getValue().toString();
                                                        Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            } catch (Exception Ex) {
                                                Ex.printStackTrace();
                                            }
                                        }
                                        pDialog.DisMiss();
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        pDialog.DisMiss();
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            pDialog.DisMiss();
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ActivityID", Activity.ActivityID);
                    map.put("ActivityResultID", Activity.ActivityID);
                    map.put("ActivityDescription", Activity.ActivityID);
                    map.put("Invoices", new HashMap<String, Object>());

                    Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                    cExitActivity.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        Intent intent = new Intent(context, actMain.class);
                                        actMain.STATE = FragmentState.MainCustomers;
                                        getActivity().startActivity(intent);
                                        getActivity().finish();
                                    }else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String value = entry.getValue().toString();
                                            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (Exception Ex) {
                                    Ex.printStackTrace();
                                }
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ViewManager();
        //get Server Now Time
        Call GetTime = rInterface.RQGetServerDateTime();
        GetTime.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    NowDateTime = response.body();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String Er = t.getMessage();
            }
        });
        //Spinner Adapters
        lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
        for (Basic_ActResults data : lSubActResult) {
            List<Basic_Acts> lFild = geter.getList(Basic_Acts.class, "WHERE ActID='" + data.ActID + "'");
            for (Basic_Acts mData : lFild) {
                lSubAct.add(mData);
            }
        }
        try {
            for (int i=0; i<lSubAct.size(); i++){
                try {
                    int ID = lSubAct.get(i).ActID;
                    for(int j=i+1; j<lSubAct.size(); j++){
                        try {
                            if(ID == lSubAct.get(j).ActID){
                                lSubAct.remove(j);
                                j--;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinAdapter_SubAct = new SpinAdapter(context, lSubAct, "ActTitle");
        spinAdapter_Result = new SpinAdapter(context, lSubActResult, "ActResultTitle");
        spinSubAct.setAdapter(spinAdapter_SubAct);
        spinResult.setAdapter(spinAdapter_Result);
        int ActPosition = spinAdapter_SubAct.getItemPosition("ActID", Integer.toString(Activity.ActID));
        int ResultPosition = spinAdapter_SubAct.getItemPosition("ActResultID", Integer.toString(Activity.ActivityResultID));
        //Set Datas
        txtTitle.setText(Activity.Title);
        txtDescription.setText(Activity.Title);
        lblEnterInfo.setText(getLongDate(Activity.EnterDate));
        lblExitInfo.setText(getLongDate(Activity.ExitDate));
        lblTaskInfo.setText(getLongDate(Activity.TodoDate) + " به مدت " + getHM(Activity.DurationDate));
        spinResult.setSelection(ResultPosition);
        spinSubAct.setSelection(ActPosition);
        lInvoice = Activity.Invoice;
        RefreshFactorList();
    }
    //Refresh List
    private void RefreshFactorList() {
        adapFactors adapter = new adapFactors(lInvoice, context);
        lstFactor.setHasFixedSize(true);
        lstFactor.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false));
        lstFactor.addItemDecoration(ItemDecoration.getDecoration(context));
        lstFactor.setItemAnimator(new DefaultItemAnimator());
        lstFactor.setAdapter(adapter);
    }
    //Get LongDateTime String
    private String getLongDate(String cDate){
        PersianDate pDate = new PersianDate(getYear(cDate), getMonth(cDate), getDay(cDate));
        DateConverter DC = new DateConverter(pDate);
        return DC.getStringLongDate();
    }
    private int getYear(String Date){
        return getDate(Date, 0);
    }
    private int getMonth(String Date){
        return getDate(Date, 1);
    }
    private int getDay(String Date){
        return getDate(Date, 2);
    }
    private int getDate(String Date, int Type){
        String[] DatesTimes = Date.split("T");
        String[] Dates = DatesTimes[0].split("-");
        switch (Type){
            case 0://GetYearOnly
                return Integer.parseInt(Dates[0]);
            case 1://GetMonthOnly
                return Integer.parseInt(Dates[1]);
            case 2://GetDayOnly
                return Integer.parseInt(Dates[2]);
        }
        return Integer.parseInt(Dates[0]);
    }
    //Get Houre & Minute
    private String getHM(String Date){
        String[] DatesTimes = Date.split("T");
        String[] Times = DatesTimes[1].split(":");
        return Times[0] + ":" + Times[1];
    }
    //View Manager
    private void ViewManager(){
        switch (getViewType()){
            case EnterShowTaskShow:
                viewShower(viewEnterShow, viewTaskShow, viewEnterExit);
                break;
            case EnterInfoExitShow:
                viewShower(viewEnterInfo, viewExitShow, viewEnterExit, btnNewInvoice, lstFactor, spinResult);
                break;
            case EnterInfoExitInfo:
                viewShower(viewEnterInfo, viewExitInfo, viewEnterExitEnd, viewEnterExit, btnNewInvoice, lstFactor, spinResult, btnSend);
                break;
            case TaskShow:
                viewShower(viewTaskInfo);
                break;
            case TaskNotShow:
                viewShower(viewEnterShow, viewEnterExit);
                break;
        }
    }
    private ViewType getViewType(){
        if(Activity.EnterDate.length() == 0 && Activity.TodoDate.length() == 0)
            return ViewType.EnterShowTaskShow;
        else if(Activity.TodoDate.length() > 0 && Activity.ActivityID == 0)
            return ViewType.TaskShow;
        else if(Activity.EnterDate.length() > 0 && Activity.ExitDate.length() == 0)
            return ViewType.EnterInfoExitShow;
        else if(Activity.EnterDate.length() > 0 && Activity.ExitDate.length() > 0)
            return ViewType.EnterInfoExitInfo;
        else if(Activity.TodoDate.length() > 0 && Activity.EnterDate.length() == 0 && Activity.ActivityID != 0)
            return ViewType.TaskNotShow;

        return ViewType.EnterShowTaskShow;
    }
    private void viewShower(View... Views){
        for (View v : AllViews()) {
            for (View view : Views) {
                if(v == view)
                    v.setVisibility(View.VISIBLE);
            }
        }
    }
    private List<View> AllViews(){
        List<View> lView = new ArrayList<>();
        lView.add(viewEnterExit);
        lView.add(viewEnterShow);
        lView.add(viewEnterInfo);
        lView.add(viewExitShow);
        lView.add(viewExitInfo);
        lView.add(viewTaskShow);
        lView.add(infoTask);
        lView.add(viewEnterExitEnd);
        lView.add(viewTaskInfo);
        lView.add(btnSend);
        lView.add(btnNewInvoice);
        lView.add(lstFactor);
        lView.add(spinResult);
        for (View view : lView) {
            view.setVisibility(View.GONE);
        }
        return lView;
    }
    private enum ViewType{
        EnterShowTaskShow,
        EnterInfoExitShow,
        EnterInfoExitInfo,
        TaskShow,
        TaskNotShow
    }

}
