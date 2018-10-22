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
import com.behincom.behincome.Accesories.GPSTracker;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Activityes.fragTest;
import com.behincom.behincome.Adapters.Activities.adapFactors;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

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
    static GPSTracker gpsTracker;

    TextView lblTitle, lblEnterInfo, lblExitInfo, lblTaskInfo;
    ImageView btnCheck, imgBack, btnReNew;
    LinearLayout viewEnterExit, viewEnterShow, viewEnterInfo, viewExitShow, viewExitInfo, viewTaskShow, infoTask, viewResult;
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
        viewResult = view.findViewById(R.id.viewResult);
        viewTaskInfo = view.findViewById(R.id.viewTaskInfo);
        txtTitle = view.findViewById(R.id.txtName);
        txtDescription = view.findViewById(R.id.txtDetails);
        spinSubAct = view.findViewById(R.id.spinSubAct);
        spinResult = view.findViewById(R.id.spinResult);
        lstFactor = view.findViewById(R.id.lstFactor);
        btnNewInvoice = view.findViewById(R.id.btnNewInvoice);
        btnSend = view.findViewById(R.id.cardAcceptTask);

        gpsTracker = new GPSTracker(context);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        spinSubAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedAct = spinSubAct.getSelectedItemPosition();
                Activity.ActID = Integer.parseInt(spinAdapter_SubAct.getItemString(SelectedAct, "ActID"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedResult = spinResult.getSelectedItemPosition();
                Activity.ActID = Integer.parseInt(spinAdapter_Result.getItemString(SelectedResult, "ActResultID"));
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
                Activity.Title = txtTitle.getText().toString();
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
                Activity.ActivityDescription = txtDescription.getText().toString();
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
                if (Activity.ActivityID > 0) {//EnterForTask
                    map.put("ActivityID", Activity.ActivityID);
                    map.put("Title", Activity.Title);
                    map.put("ActID", Activity.ActID);
                    map.put("ActivityDescription", Activity.ActivityDescription);
                    map.put("EnterDate", "");
                    map.put("EnterLatutide", MyLocation.latitude);
                    map.put("EnterLongitude", MyLocation.longitude);

                    Call EnterForTask = rInterface.RQAddActivityEnterForTask(Setting.getToken(), map);
                    EnterForTask.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        Activity.ActivityID = Integer.parseInt(response.body().AdditionalData.get("ActivityID").toString().replace(".0", ""));
                                        Activity.EnterDate = response.body().AdditionalData.get("EnterDate").toString();
                                        lblEnterInfo.setText(getLongDate(Activity.EnterDate));
                                        viewShower(viewEnterInfo, viewExitShow, viewEnterExit, btnNewInvoice, lstFactor, spinResult);
                                    } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String value = entry.getValue().toString();
                                            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (Exception Ex) {
                                    String Er = Ex.getMessage();
                                    viewShower(viewEnterInfo, viewExitShow, viewEnterExit, btnNewInvoice, lstFactor, spinResult);
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
                } else {//EnterForNewActivity
                    map.put("ParentID", Activity.ParentID);
                    map.put("ActivityAddedByUserID", 0);
                    map.put("ActivityOwnerUserID", Setting.getBMMUserID());
                    map.put("CustomerID", Activity.CustomerID);
                    map.put("Title", Activity.Title);
                    map.put("ActID", Activity.ActID);
                    map.put("ActivityDescription", Activity.ActivityDescription);
                    map.put("EnterDate", "");
                    map.put("EnterLatutide", MyLocation.latitude);
                    map.put("EnterLongitude", MyLocation.longitude);

                    Call Enter = rInterface.RQAddActivityEnter(Setting.getToken(), map);
                    Enter.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        Activity.ActivityID = Integer.parseInt(response.body().AdditionalData.get("ActivityID").toString().replace(".0", ""));
                                        Activity.EnterDate = response.body().AdditionalData.get("EnterDate").toString();
                                        lblEnterInfo.setText(getLongDate(Activity.EnterDate));
                                        viewShower(viewEnterInfo, viewExitShow, viewEnterExit, btnNewInvoice, lstFactor, spinResult, viewResult);
                                    } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String value = entry.getValue().toString();
                                            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                        }
                                    } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Warning.toString())) {
                                        for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                            String key = entry.getKey();
                                            String value = entry.getValue().toString();
                                            if (!key.equalsIgnoreCase("ActivityID"))
                                                Toast.makeText(context, value, Toast.LENGTH_LONG).show();
                                            else {
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
                            pDialog.DisMiss();
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
                if (lInvoice.size() > 0) {
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
                            if (response.isSuccessful()) {
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
                                map.put("ActivityResultID", Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID")));
                                map.put("ActivityDescription", Activity.ActivityDescription);
                                map.put("ExitDate", "");
                                map.put("ExitLatutide", MyLocation.latitude);
                                map.put("ExitLongitude", MyLocation.longitude);
                                map.put("Invoices", lMapInvoice);

                                Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                                cExitActivity.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                SimpleResponse result = response.body();
                                                if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                                    Activity.ExitDate = response.body().AdditionalData.get("ExitDate").toString();
                                                    lblExitInfo.setText(getLongDate(Activity.ExitDate));
                                                    viewShower(btnCheck, viewEnterInfo, viewExitInfo, viewEnterExitEnd, viewEnterExit, btnNewInvoice, lstFactor, spinResult, btnSend);
                                                } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
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
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ActivityID", Activity.ActivityID);
                    map.put("ActivityResultID", Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID")));
                    map.put("ActivityDescription", Activity.ActivityDescription);
                    map.put("ExitDate", "");
                    map.put("ExitLatutide", MyLocation.latitude);
                    map.put("ExitLongitude", MyLocation.longitude);
                    map.put("Invoices", new HashMap<>());

                    Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                    cExitActivity.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        Activity.ExitDate = response.body().AdditionalData.get("ExitDate").toString();
                                        lblExitInfo.setText(getLongDate(Activity.ExitDate));
                                        viewShower(btnCheck, viewEnterInfo, viewExitInfo, viewEnterExitEnd, viewEnterExit, btnNewInvoice, lstFactor, spinResult, btnSend);
                                    } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
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
                fragAddFactor.lFactores = new ArrayList<>();
                act.getFragByState(FragmentState.AddFactor);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                HashMap<String, Object> map = new HashMap<>();
                if (Activity.ActivityID > 0) {
                    if (lInvoice.size() > 0) {
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
                                if (response.isSuccessful()) {
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

                                    HashMap<String, Object> mapse = new HashMap<>();
                                    mapse.put("ActivityID", Activity.ActivityID);
                                    mapse.put("Description", Activity.ActivityDescription);
                                    mapse.put("Invoices", lMapInvoice);
                                    mapse.put("ActivityResultID", Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID")));

                                    Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), mapse);
                                    cConfirm.enqueue(new Callback<SimpleResponse>() {
                                        @Override
                                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                            if (response.isSuccessful()) {
                                                try {
                                                    SimpleResponse result = response.body();
                                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                                        Toast.makeText(context, Basics.Submited, Toast.LENGTH_LONG).show();
                                                    } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
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
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                pDialog.DisMiss();
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        List<HashMap<String, Object>> lMaps = new ArrayList<>();
                        for (Invoice data : lInvoice) {
                            HashMap<String, Object> mapp = new HashMap<>();
                            mapp.put("InvoiceNumber", data.InvoiceNumber);
                            mapp.put("InvoiceMarketingProductID", data.InvoiceMarketingProductID);
                            mapp.put("InvoiceActivityID", Activity.ActivityID);
                            mapp.put("InvoicePrice", data.InvoicePrice);
                            mapp.put("InvoiceDescription", data.InvoiceDescription);

                            lMaps.add(mapp);
                        }
                        HashMap<String, Object> mapse = new HashMap<>();
                        mapse.put("ActivityID", Activity.ActivityID);
                        mapse.put("Description", Activity.ActivityDescription);
                        mapse.put("Invoices", lMaps);
                        mapse.put("ActivityResultID", Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID")));

                        Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), mapse);
                        cConfirm.enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        SimpleResponse result = response.body();
                                        if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                            Toast.makeText(context, Basics.Submited, Toast.LENGTH_LONG).show();
                                        } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
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
                } else {
                    map.put("CustomerID", Activity.CustomerID);
                    map.put("ActivityOwnerUserID", Setting.getBMMUserID());
                    map.put("Title", Activity.Title);
                    map.put("ActID", Integer.parseInt(spinAdapter_SubAct.getItemString(spinSubAct.getSelectedItemPosition(), "ActID")));
                    map.put("Description", Activity.ActivityDescription);
                    map.put("TaskDate", Activity.TodoDate);
                    map.put("DurationDate", Activity.DurationDate);
//                    map.put("VisitTourID", Activity.VisitTourID);
                    map.put("VisitTourID", 1);//Todo
                    map.put("TodoDateEnd", Activity.TodoDate);

                    Call cConfirm = rInterface.RQAddTask(Setting.getToken(), map);
                    cConfirm.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        getActivity().finish();
                                    } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
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
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                if (lInvoice.size() > 0) {
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
                            if (response.isSuccessful()) {
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
                                map.put("ActivityResultID", Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID")));
                                map.put("Description", Activity.ActivityDescription);
                                map.put("Invoices", lMapInvoice);

                                Call cExitActivity = rInterface.RQSendActivity(Setting.getToken(), map);
                                cExitActivity.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                SimpleResponse result = response.body();
                                                if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                                    getActivity().finish();
                                                } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
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
                } else {
                    List<HashMap<String, Object>> lMaps = new ArrayList<>();
                    for (Invoice data : lInvoice) {
                        HashMap<String, Object> mapp = new HashMap<>();
                        mapp.put("InvoiceNumber", data.InvoiceNumber);
                        mapp.put("InvoiceMarketingProductID", data.InvoiceMarketingProductID);
                        mapp.put("InvoiceActivityID", Activity.ActivityID);
                        mapp.put("InvoicePrice", data.InvoicePrice);
                        mapp.put("InvoiceDescription", data.InvoiceDescription);

                        lMaps.add(mapp);
                    }
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ActivityID", Activity.ActivityID);
                    map.put("ActivityResultID", Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID")));
                    map.put("Description", Activity.ActivityDescription);
                    map.put("Invoices", lMaps);

                    Call cExitActivity = rInterface.RQSendActivity(Setting.getToken(), map);
                    cExitActivity.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    SimpleResponse result = response.body();
                                    if (result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                        getActivity().finish();
                                    } else if (result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
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

    private static LatLng MyLocation = new LatLng(0.0, 0.0);

    @Override
    public void onResume() {
        super.onResume();
        ViewManager();
        try {
            if (gpsTracker.getIsGPSTrackingEnabled()) {
                MyLocation = new LatLng(gpsTracker.latitude, gpsTracker.longitude);
            } else {
                gpsTracker.showSettingsAlert();
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
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
            for (int i = 0; i < lSubAct.size(); i++) {
                try {
                    int ID = lSubAct.get(i).ActID;
                    for (int j = i + 1; j < lSubAct.size(); j++) {
                        try {
                            if (ID == lSubAct.get(j).ActID) {
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
        txtDescription.setText(Activity.ActivityDescription);
        try {
            lblEnterInfo.setText(getLongDate(Activity.EnterDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            lblExitInfo.setText(getLongDate(Activity.ExitDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String lbTask = getLongDate(Activity.TodoDate) + "در ساعت " + getShortTime(Activity.TodoDate) + " به مدت " + getHM(Activity.DurationDate);
            lblTaskInfo.setText(lbTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            spinResult.setSelection(ResultPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            spinSubAct.setSelection(ActPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private String getShortTime(String cDate){
        String[] DatesTimes = cDate.split("T");
        String[] Times = DatesTimes[1].split(":");
        return Times[0] + ":" + Times[1];
    }
    //Get LongDateTime String
    private String getLongDate(String cDate) {
        int y = getYear(cDate);
        int m = getMonth(cDate);
        int d = getDay(cDate);
        saman.zamani.persiandate.PersianDate pDate = new saman.zamani.persiandate.PersianDate();
        pDate.setGrgYear(y);
        pDate.setGrgMonth(m);
        pDate.setGrgDay(d);
        y = pDate.getShYear();
        m = pDate.getShMonth();
        d = pDate.getShDay();
        PersianDate pDatee = new PersianDate(y, m, d);
        DateConverter DC = new DateConverter(pDatee);
        return DC.getStringLongDate();
    }

    private int getYear(String Date) {
        return getDate(Date, 0);
    }

    private int getMonth(String Date) {
        return getDate(Date, 1);
    }

    private int getDay(String Date) {
        return getDate(Date, 2);
    }

    private int getDate(String Date, int Type) {
        String[] DatesTimes = Date.split("T");
        String[] Dates = DatesTimes[0].split("-");
        switch (Type) {
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
    private String getHM(String Date) {
        String[] DatesTimes = Date.split("T");
        String[] Times = DatesTimes[1].split(":");
        return Times[0] + ":" + Times[1];
    }

    //View Manager
    private void ViewManager() {
        switch (getViewType()) {
            case EnterShowTaskShow:
                viewShower(viewEnterShow, viewTaskShow, viewEnterExit);
                break;
            case EnterInfoExitShow:
                viewShower(viewEnterInfo, viewExitShow, viewEnterExit, btnNewInvoice, lstFactor, spinResult, viewResult);
                break;
            case EnterInfoExitInfo:
                viewShower(btnCheck, viewEnterInfo, viewExitInfo, viewEnterExitEnd, viewEnterExit, btnNewInvoice, lstFactor, spinResult, viewResult, btnSend);
                break;
            case TaskShow:
                viewShower(viewTaskInfo, infoTask, btnCheck);
                break;
            case TaskNotShow:
                viewShower(viewEnterShow, viewEnterExit);
                break;
        }
    }

    private ViewType getViewType() {
        if(Activity.TodoDate == null)Activity.TodoDate = "";
        if(Activity.EnterDate == null)Activity.EnterDate = "";
        if(Activity.ExitDate == null)Activity.ExitDate = "";
        if (Activity.EnterDate.length() == 0 && Activity.TodoDate.length() == 0)
            return ViewType.EnterShowTaskShow;
        else if (Activity.TodoDate.length() > 0 && Activity.ActivityID == 0)
            return ViewType.TaskShow;
        else if (Activity.EnterDate.length() > 0 && Activity.ExitDate.length() == 0)
            return ViewType.EnterInfoExitShow;
        else if (Activity.EnterDate.length() > 0 && Activity.ExitDate.length() > 0)
            return ViewType.EnterInfoExitInfo;
        else if (Activity.TodoDate.length() > 0 && Activity.EnterDate.length() == 0 && Activity.ActivityID != 0)
            return ViewType.TaskNotShow;

        return ViewType.EnterShowTaskShow;
    }

    private void viewShower(View... Views) {
        for (View v : AllViews()) {
            for (View view : Views) {
                if (v == view)
                    v.setVisibility(View.VISIBLE);
            }
        }
    }

    private List<View> AllViews() {
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
        lView.add(btnCheck);
        lView.add(btnNewInvoice);
        lView.add(lstFactor);
        lView.add(spinResult);
        lView.add(viewResult);
        for (View view : lView) {
            view.setVisibility(View.GONE);
        }
        return lView;
    }

    private enum ViewType {
        EnterShowTaskShow,
        EnterInfoExitShow,
        EnterInfoExitInfo,
        TaskShow,
        TaskNotShow
    }

}
