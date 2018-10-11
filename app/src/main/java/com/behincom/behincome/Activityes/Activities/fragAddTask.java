package com.behincom.behincome.Activityes.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.behincom.behincome.Activityes.Customer.actCustomer;
import com.behincom.behincome.Activityes.Customer.fragCustomerShow;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.Activities.adapFactors;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendActivityConfirmAndSend;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendActivityEnter;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendActivityEnterForTask;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendActivityExit;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendAddTask;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendInvoice;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendInvoiceImage;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.behincom.behincome.Datas.Keys.FragmentState.AddCustomer;
import static com.behincom.behincome.Datas.Keys.FragmentState.CustomerShow;

public class fragAddTask extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    DateConverter DC = new DateConverter();
    Dialog pDialog;
    SpinAdapter spinAdapter_SubAct, spinAdapter_Result;
    Time now;
    RWInterface rInterface;
    actActivities act = new actActivities();

    //Elements
    TextView lblTitle;
    TextView lblTimeTitle;
    public static TextView lblTimeCondition;
    TextView lblNewFactor;
    TextView lblTaskTimeSet;
    TextView lblTimeSet;
    TextView lblAccept;
    EditText txtName;
    EditText txtDetails;
    ImageView imgBack;
    ImageView imgAlarmSet;
    ImageView btnCheck;
    ImageView btnActCondition;
    ImageView imgCancellTask;
    Spinner spinSubAct;
    Spinner spinResult;
    LinearLayout btnNewFactor;
    LinearLayout btnTaskTimeSet;
    LinearLayout viewTimeSet;
    LinearLayout viewFactor;
    LinearLayout viewTimes;
    ConstraintLayout card2;
    LinearLayout viewEnterExit;
    LinearLayout linEnterExit;
    RelativeLayout itemTimeSet;
    RecyclerView lstFactor;
    CardView cardView;
    CardView cardAcceptTask;
    TextView lblAcceptGreen;
    RecyclerView.LayoutManager mLayoutManager;
    CardView cardViewGreen;

    //Lists
    List<Basic_Acts> lSubAct = new ArrayList<>();
    List<Basic_ActResults> lSubActResult = new ArrayList<>();
    public static List<Invoice> lFactor = new ArrayList<>();
    public static Activities lData = new Activities();

    public static int TaskShowCustomerId = 0;
    public static Customers TaskShowCustomer = new Customers();
    public static Activities TaskShowlData = new Activities();
    public static boolean TaskShowType = false;

    //Variables
    public static int Type = 0;//0 = Hichie Hichi, 1 = Vorud khorde, 2 = Khoruj khorde, 3 = zaman khorde, 4 = tayid shode, 5 = ersal shode
    public static int currentId = 0;

    public static MyCustomers mCustomer = new MyCustomers();
    public static int mPosition = 0;
    public static int sResult = 0;
    public static int mParentID = 0;
    public static int customer_id = 0;
    public static int busines_id = 0;
    public static int duration = 0;
    public static int ActSelected = 0, ResultSelected = 0;
    public static String Descriptioned = "";
    public static String Nameioned = "";
    public static String Name = "";
    public static String Date = "";
    public static String DateToSend = "";
    public static String TimeToSend = "";
    public static boolean isAlarmSet = false;
    public static boolean toEdite = false;
    public static String todoDate = "2018-01-01T00:00:00";
    private static String Description = "";
    private static String EnterTime = "2018-01-01T00:00:00";
    private static String ExitTime = "2018-01-01T00:00:00";
    private static String NowDateTime = "2018-01-01T00:00:00";

    public static String Namee = "", Details = "", enterTime = "";
    public static int spin1 = 0, spin2 = 0;
    public static boolean fac = false;
    public static List<Basic_CustomerStates> lState = new ArrayList<>();

    public static fragAddTask newInstance(Context mContext) {
        fragAddTask fragment = new fragAddTask();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_task, container, false);

        cardAcceptTask = view.findViewById(R.id.cardAcceptTask);
        linEnterExit = view.findViewById(R.id.linEnterExit);
        imgCancellTask = view.findViewById(R.id.imgCancellTask);
        card2 = view.findViewById(R.id.card2);
        txtName = view.findViewById(R.id.txtName);
        btnCheck = view.findViewById(R.id.btnCheck);
        txtDetails = view.findViewById(R.id.txtDetails);
        lblTitle = view.findViewById(R.id.lblTitle);
        lblTimeTitle = view.findViewById(R.id.lblTimeTitle);
        lblTimeCondition = view.findViewById(R.id.lblTimeCondition);
        lblNewFactor = view.findViewById(R.id.lblNewFactor);
        lblTaskTimeSet = view.findViewById(R.id.lblTaskTimeSet);
        lblTimeSet = view.findViewById(R.id.lblTimeSet);
        lblAccept = view.findViewById(R.id.lblAccept);
        imgBack = view.findViewById(R.id.imgBack);
        imgAlarmSet = view.findViewById(R.id.imgAlarmSet);
        btnActCondition = view.findViewById(R.id.btnActCondition);
        spinSubAct = view.findViewById(R.id.spinSubAct);
        spinResult = view.findViewById(R.id.spinResult);
        btnNewFactor = view.findViewById(R.id.btnNewFactor);
        viewTimes = view.findViewById(R.id.viewTimes);
        viewEnterExit = view.findViewById(R.id.viewEnterExit);
        viewTimeSet = view.findViewById(R.id.viewTimeSet);
        viewFactor = view.findViewById(R.id.viewFactor);
        btnTaskTimeSet = view.findViewById(R.id.btnTaskTimeSet);
        itemTimeSet = view.findViewById(R.id.itemTimeSet);
        lstFactor = view.findViewById(R.id.lstFactor);
        cardView = view.findViewById(R.id.cardView);
        lblAcceptGreen = view.findViewById(R.id.lblAcceptGreen);
        mLayoutManager = new LinearLayoutManager(context);
        cardViewGreen = view.findViewById(R.id.cardViewGreen);

        lstFactor.setNestedScrollingEnabled(false);

        //todo todo todo todo todo todo todo
        //Upload Pic Here Frist

        lblTitle.setText("ایجاد وظیفه");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TaskShowType) {
                    Intent intent = new Intent(context, actCustomer.class);
                    fragCustomerShow.Customer = mCustomer;
                    fragCustomerShow.position = mPosition;
                    actCustomer.STATE = CustomerShow;
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }else {
                    fragTaskShow.lData = TaskShowlData;
                    fragTaskShow.CustomerId = TaskShowCustomerId;
                    fragTaskShow.Customer = TaskShowCustomer;
                    act.getFragByState(FragmentState.TaskShow);
                }
            }
        });
        if (Name != null) txtName.setText(Name);
        now = new Time();
        lblTimeCondition.setText(DC.getTime(now.hour, now.minute));

        btnCheck.setVisibility(View.GONE);

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Namee = txtName.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Details = txtDetails.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Spinner Adapters
        lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
        for (Basic_ActResults data : lSubActResult) {
            List<Basic_Acts> lFild = geter.getList(Basic_Acts.class, "WHERE ActID='" + data.ActID + "'");
//            List<Basic_ActivityFields> lFild = SQL.Select("SELECT DISTINCT ActivityFieldID, ActivityFieldGroupID, ActivityFieldTitle, isCheck FROM Basic_ActivityFields WHERE ActivityFieldID='" + data.ActID + "'", Basic_ActivityFields.class);
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
        Basic_Acts data = new Basic_Acts();
        data.ActTitle = ("نوع فعالیت");
        lSubAct.add(0, data);
        Basic_ActResults dataR = new Basic_ActResults();
        dataR.ActResultTitle = ("نتیجه");
        lSubActResult.add(0, dataR);
        spinAdapter_SubAct = new SpinAdapter(context, lSubAct, "ActTitle");
        spinAdapter_Result = new SpinAdapter(context, lSubActResult, "ActResultTitle");
        spinSubAct.setAdapter(spinAdapter_SubAct);
        spinResult.setAdapter(spinAdapter_Result);

        spinSubAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ActSelected = spinSubAct.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ResultSelected = spinResult.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnActCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetEnterExit();
            }
        });
        lblTimeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetEnterExit();
            }
        });
        btnNewFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddFactor.Name = txtName.getText().toString();//injako
                fragAddFactor.Details = txtDetails.getText().toString();
                fragAddFactor.enterTime = lblTimeCondition.getText().toString();
                fragAddFactor.spin1 = ActSelected;
                fragAddFactor.currentId = currentId;
                fragAddFactor.spin2 = ResultSelected;
                fragAddFactor.ActType = Type;

                act.getFragByState(FragmentState.AddFactor);
            }
        });
        btnTaskTimeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Type == 0 || Type == 3) {
                    fragAddTaskSetTime.ActSelected = ActSelected;
                    fragAddTaskSetTime.Descriptioned = txtDetails.getText().toString();
                    fragAddTaskSetTime.Nameioned = txtName.getText().toString();
                    act.getFragByState(FragmentState.AddTaskSetTime);
                    Type = 3;
                    cardAcceptTask.setVisibility(View.VISIBLE);
                } else {
                    mToast("الان نمیشه :-D");
                }
            }
        });
        imgAlarmSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAlarmSet) {
                    Drawable myDrawable = getResources().getDrawable(R.drawable.alarm_);
                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAlarmSet) {});
                    isAlarmSet = false;
                } else {
                    Drawable myDrawable = getResources().getDrawable(R.drawable.alarm_selected);
                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAlarmSet) {
                    });
                    isAlarmSet = true;
                }
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Type == 1) {
                    if (spinResult.getSelectedItemPosition() > 0 && spinSubAct.getSelectedItemPosition() > 0) {
                        pDialog = new Dialog(context);
                        pDialog.Show();
                        if (Type == 3) {//Tasks
                            ObjectMapper oMapper = new ObjectMapper();
                            HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                            Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                            cConfirm.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if (response.isSuccessful()) {
                                        try {
                                            getActivity().runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    mToast("وظیفه ایجاد شد");
                                                    Date = "";
                                                    if (toEdite) {
                                                        fragTaskShow.lData.StateID = (113003);//todo todo todo check StateID
                                                    }
                                                    Intent intent = new Intent(context, actMain.class);
                                                    actMain.STATE = FragmentState.MainCustomers;
                                                    getActivity().startActivity(intent);
                                                    getActivity().finish();
                                                }
                                            });
                                        } catch (Exception Ex) {
                                            String Er = Ex.getMessage();
                                        }
                                    } else {
                                        getActivity().runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                mToast("خطا در برقراری ارتباط با سرور.");
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
                        } else {//Activities
                            if(lFactor.size() > 0){
                                MultipartBody.Part[] body = new MultipartBody.Part[lFactor.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                                for (int i = 0; i < lFactor.get(0).InvoiceImage.size(); i++) {
                                    File file = new File(lFactor.get(i).InvoiceImage.get(i).ImageFilename);
                                    RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                                    body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                                }

                                Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                                addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if(response.isSuccessful()){
                                            List<ToSendInvoiceImage> lURLs = new ArrayList<>();
                                            try {
                                                Object[] keys = response.body().AdditionalData.keySet().toArray();
                                                for (Object data : keys) {
                                                    String val = response.body().AdditionalData.get(data.toString()).toString();
                                                    ToSendInvoiceImage tData = new ToSendInvoiceImage();
                                                    tData.InvoiceFileName = val;
                                                    lURLs.add(tData);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            ObjectMapper oMapper = new ObjectMapper();
                                            HashMap map = oMapper.convertValue(getActConfirmData(lURLs), HashMap.class);

                                            Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                                            cConfirm.enqueue(new Callback<SimpleResponse>() {
                                                @Override
                                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                                    if (response.isSuccessful()) {
                                                        try {
                                                            getActivity().runOnUiThread(new Runnable() {

                                                                @Override
                                                                public void run() {
                                                                    if (toEdite) {
                                                                        fragTaskShow.lData.StateID = (113006);//todo todo todo Check StateID
                                                                    }
                                                                    mToast("فعالیت تایید شد");
                                                                }
                                                            });
                                                        } catch (Exception Ex) {
                                                            String Er = Ex.getMessage();
                                                        }
                                                    } else {
                                                        getActivity().runOnUiThread(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                mToast("خطا در برقراری ارتباط با سرور.");
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
                                    }
                                });
                            }else{
                                ObjectMapper oMapper = new ObjectMapper();
                                HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                                Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                                cConfirm.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                getActivity().runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        if (toEdite) {
                                                            fragTaskShow.lData.StateID = (113006);//todo todo todo Check StateID
                                                        }
                                                        mToast("فعالیت تایید شد");
                                                    }
                                                });
                                            } catch (Exception Ex) {
                                                String Er = Ex.getMessage();
                                            }
                                        } else {
                                            getActivity().runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    mToast("خطا در برقراری ارتباط با سرور.");
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
                        Type = 4;
                    } else
                        Toast.makeText(context, "نوع فعالیت نباید خالی باشند.", Toast.LENGTH_LONG).show();
                } else if (Type == 2 || Type == 4) {
                    if (spinSubAct.getSelectedItemPosition() > 0) {
                        pDialog = new Dialog(context);
                        pDialog.Show();
                        if (Type == 3) {//Tasks
                            ObjectMapper oMapper = new ObjectMapper();
                            HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                            Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                            cConfirm.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if (response.isSuccessful()) {
                                        try {
                                            getActivity().runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    mToast("وظیفه ایجاد شد");
                                                    Date = "";
                                                    if (toEdite) {
                                                        fragTaskShow.lData.StateID = (113003);//todo todo todo check StateID
                                                    }
                                                    Intent intent = new Intent(context, actMain.class);
                                                    actMain.STATE = FragmentState.MainCustomers;
                                                    getActivity().startActivity(intent);
                                                    getActivity().finish();
                                                }
                                            });
                                        } catch (Exception Ex) {
                                            String Er = Ex.getMessage();
                                        }
                                    } else {
                                        getActivity().runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                mToast("خطا در برقراری ارتباط با سرور.");
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
                        } else {//Activities
                            if(lFactor.size() > 0){
                                MultipartBody.Part[] body = new MultipartBody.Part[lFactor.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                                for (int i = 0; i < lFactor.get(0).InvoiceImage.size(); i++) {
                                    File file = new File(lFactor.get(i).InvoiceImage.get(i).ImageFilename);
                                    RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                                    body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                                }

                                Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                                addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if(response.isSuccessful()){
                                            List<ToSendInvoiceImage> lURLs = new ArrayList<>();
                                            try {
                                                Object[] keys = response.body().AdditionalData.keySet().toArray();
                                                for (Object data : keys) {
                                                    String val = response.body().AdditionalData.get(data.toString()).toString();
                                                    ToSendInvoiceImage tData = new ToSendInvoiceImage();
                                                    tData.InvoiceFileName = val;
                                                    lURLs.add(tData);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            ObjectMapper oMapper = new ObjectMapper();
                                            HashMap map = oMapper.convertValue(getActConfirmData(lURLs), HashMap.class);

                                            Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                                            cConfirm.enqueue(new Callback<SimpleResponse>() {
                                                @Override
                                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                                    if (response.isSuccessful()) {
                                                        try {
                                                            getActivity().runOnUiThread(new Runnable() {

                                                                @Override
                                                                public void run() {
                                                                    if (toEdite) {
                                                                        fragTaskShow.lData.StateID = (113006);//todo todo todo Check StateID
                                                                    }
                                                                    mToast("فعالیت تایید شد");
                                                                }
                                                            });
                                                        } catch (Exception Ex) {
                                                            String Er = Ex.getMessage();
                                                        }
                                                    } else {
                                                        getActivity().runOnUiThread(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                mToast("خطا در برقراری ارتباط با سرور.");
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
                                    }
                                });
                            }else {
                                ObjectMapper oMapper = new ObjectMapper();
                                HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                                Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                                cConfirm.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                getActivity().runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        if (toEdite) {
                                                            fragTaskShow.lData.StateID = (113006);//todo todo todo Check StateID
                                                        }
                                                        mToast("فعالیت تایید شد");
                                                    }
                                                });
                                            } catch (Exception Ex) {
                                                String Er = Ex.getMessage();
                                            }
                                        } else {
                                            getActivity().runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    mToast("خطا در برقراری ارتباط با سرور.");
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
                        Type = 4;
                    } else
                        Toast.makeText(context, "نوع فعالیت نباید خالی باشند.", Toast.LENGTH_LONG).show();
                } else if (Type == 3) {
                    if (spinSubAct.getSelectedItemPosition() > 0) {
                        pDialog = new Dialog(context);
                        pDialog.Show();

                        if(lFactor.size() > 0){
                            MultipartBody.Part[] body = new MultipartBody.Part[lFactor.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                            for (int i = 0; i < lFactor.get(0).InvoiceImage.size(); i++) {
                                File file = new File(lFactor.get(i).InvoiceImage.get(i).ImageFilename);
                                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                                body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                            }

                            Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                            addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if(response.isSuccessful()){
                                        List<ToSendInvoiceImage> lURLs = new ArrayList<>();
                                        try {
                                            Object[] keys = response.body().AdditionalData.keySet().toArray();
                                            for (Object data : keys) {
                                                String val = response.body().AdditionalData.get(data.toString()).toString();
                                                ToSendInvoiceImage tData = new ToSendInvoiceImage();
                                                tData.InvoiceFileName = val;
                                                lURLs.add(tData);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        ObjectMapper oMapper = new ObjectMapper();
                                        HashMap map = oMapper.convertValue(getActConfirmData(lURLs), HashMap.class);

                                        Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                                        cConfirm.enqueue(new Callback<SimpleResponse>() {
                                            @Override
                                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                                if (response.isSuccessful()) {
                                                    try {
                                                        getActivity().runOnUiThread(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                if (toEdite) {
                                                                    fragTaskShow.lData.StateID = (113006);//todo todo todo Check StateID
                                                                }
                                                                mToast("فعالیت تایید شد");
                                                            }
                                                        });
                                                    } catch (Exception Ex) {
                                                        String Er = Ex.getMessage();
                                                    }
                                                } else {
                                                    getActivity().runOnUiThread(new Runnable() {

                                                        @Override
                                                        public void run() {
                                                            mToast("خطا در برقراری ارتباط با سرور.");
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
                                }
                            });
                        }else {
                            ObjectMapper oMapper = new ObjectMapper();
                            HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                            Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                            cConfirm.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if (response.isSuccessful()) {
                                        try {
                                            getActivity().runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    if (toEdite) {
                                                        fragTaskShow.lData.StateID = (113006);//todo todo todo Check StateID
                                                    }
                                                    mToast("فعالیت تایید شد");
                                                }
                                            });
                                        } catch (Exception Ex) {
                                            String Er = Ex.getMessage();
                                        }
                                    } else {
                                        getActivity().runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                mToast("خطا در برقراری ارتباط با سرور.");
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
                    } else
                        Toast.makeText(context, "نوع فعالیت نباید خالی باشند.", Toast.LENGTH_LONG).show();
                }
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Type == 1 || Type == 2 || Type == 4) {
                    Type = 4;
                    pDialog = new Dialog(context);//todo todo todo
                    pDialog.Show();

                    if(lFactor.size() > 0){
                        MultipartBody.Part[] body = new MultipartBody.Part[lFactor.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                        for (int i = 0; i < lFactor.get(0).InvoiceImage.size(); i++) {
                            File file = new File(lFactor.get(i).InvoiceImage.get(i).ImageFilename);
                            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                            body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                        }

                        Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                        addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                if(response.isSuccessful()){
                                    List<ToSendInvoiceImage> lURLs = new ArrayList<>();
                                    try {
                                        Object[] keys = response.body().AdditionalData.keySet().toArray();
                                        for (Object data : keys) {
                                            String val = response.body().AdditionalData.get(data.toString()).toString();
                                            ToSendInvoiceImage tData = new ToSendInvoiceImage();
                                            tData.InvoiceFileName = val;
                                            lURLs.add(tData);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    ObjectMapper oMapper = new ObjectMapper();
                                    HashMap map = oMapper.convertValue(getActConfirmData(lURLs), HashMap.class);

                                    Call cSendActivity = rInterface.RQSendActivity(Setting.getToken(), map);
                                    cSendActivity.enqueue(new Callback<SimpleResponse>() {
                                        @Override
                                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                            if (response.isSuccessful()) {
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mToast("فعالیت ارسال شد");
                                                        if (toEdite) {
                                                            fragTaskShow.lData.StateID = (113004);//todo todo todo check StateID
                                                        }
                                                        Intent intent = new Intent(context, actMain.class);
                                                        actMain.STATE = FragmentState.MainCustomers;
                                                        getActivity().startActivity(intent);
                                                        getActivity().finish();
                                                    }
                                                });
                                            }
                                            pDialog.DisMiss();
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mToast("خطا در برقراری ارتباط با سرور");
                                                    pDialog.DisMiss();
                                                }
                                            });
                                        }
                                    });
                                }
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                pDialog.DisMiss();
                            }
                        });
                    }else {
                        ObjectMapper oMapper = new ObjectMapper();
                        HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                        Call cSendActivity = rInterface.RQSendActivity(Setting.getToken(), map);
                        cSendActivity.enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                if (response.isSuccessful()) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mToast("فعالیت ارسال شد");
                                            if (toEdite) {
                                                fragTaskShow.lData.StateID = (113004);//todo todo todo check StateID
                                            }
                                            Intent intent = new Intent(context, actMain.class);
                                            actMain.STATE = FragmentState.MainCustomers;
                                            getActivity().startActivity(intent);
                                            getActivity().finish();
                                        }
                                    });
                                }
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mToast("خطا در برقراری ارتباط با سرور");
                                        pDialog.DisMiss();
                                    }
                                });
                            }
                        });
                    }
                } else if (Type == 3) {
                    Type = 3;
                    pDialog = new Dialog(context);//todo todo todo
                    pDialog.Show();

                    if(lFactor.size() > 0){
                        MultipartBody.Part[] body = new MultipartBody.Part[lFactor.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                        for (int i = 0; i < lFactor.get(0).InvoiceImage.size(); i++) {
                            File file = new File(lFactor.get(i).InvoiceImage.get(i).ImageFilename);
                            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                            body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                        }

                        Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                        addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                if(response.isSuccessful()){
                                    List<ToSendInvoiceImage> lURLs = new ArrayList<>();
                                    try {
                                        Object[] keys = response.body().AdditionalData.keySet().toArray();
                                        for (Object data : keys) {
                                            String val = response.body().AdditionalData.get(data.toString()).toString();
                                            ToSendInvoiceImage tData = new ToSendInvoiceImage();
                                            tData.InvoiceFileName = val;
                                            lURLs.add(tData);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    ObjectMapper oMapper = new ObjectMapper();
                                    HashMap map = oMapper.convertValue(getActConfirmData(lURLs), HashMap.class);

                                    Call cSendActivity = rInterface.RQSendActivity(Setting.getToken(), map);
                                    cSendActivity.enqueue(new Callback<SimpleResponse>() {
                                        @Override
                                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {

                                        }
                                    });
                                }
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                pDialog.DisMiss();
                            }
                        });
                    }else {
                        ObjectMapper oMapper = new ObjectMapper();
                        HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                        Call cSendActivity = rInterface.RQSendActivity(Setting.getToken(), map);
                        cSendActivity.enqueue(new Callback<SimpleResponse>() {
                            @Override
                            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Name = txtName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Description = txtDetails.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (toEdite) {
            Description = lData.ActivityDescription;
            btnTaskTimeSet.setVisibility(View.GONE);
        }
        imgCancellTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
            }
        });
        cardAcceptTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinSubAct.getSelectedItemPosition() > 0) {
                    pDialog = new Dialog(context);
                    pDialog.Show();

                    ObjectMapper oMapper = new ObjectMapper();
                    HashMap map = oMapper.convertValue(getTaskConfirmData(), HashMap.class);

                    Call cConfirm = rInterface.RQAddTask(Setting.getToken(), map);
                    cConfirm.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            mToast("وظیفه ایجاد شد");
                                            Date = "";
                                            if (toEdite) {
                                                fragTaskShow.lData.StateID = (113003);//todo todo todo check StateID
                                            }
                                            Intent intent = new Intent(context, actMain.class);
                                            actMain.STATE = FragmentState.MainCustomers;
                                            getActivity().startActivity(intent);
                                            getActivity().finish();
                                        }
                                    });
                                } catch (Exception Ex) {
                                    String Er = Ex.getMessage();
                                }
                            } else {
                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        mToast("خطا در برقراری ارتباط با سرور.");
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

        return view;
    }
    public void onResume() {
        super.onResume();
        rInterface = Retrofite.getClient().create(RWInterface.class);
        Call GetTime = rInterface.RQGetServerDateTime();
        GetTime.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    NowDateTime = response.body();
                } else {
                    String onError = "";
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String Er = t.getMessage();
            }
        });
        if(Nameioned.length() > 0) {
            spinSubAct.setSelection(ActSelected);
            txtName.setText(Nameioned);
            txtDetails.setText(Descriptioned);
        }

        if(fac){
            txtName.setText(Namee);
            txtDetails.setText(Details);
            spinSubAct.setSelection(spin1);
            spinResult.setSelection(spin2);
            viewEnterExit.setVisibility(View.VISIBLE);

            Drawable myDrawable;
            Bitmap myLogo;
            ByteArrayOutputStream stream;

            if(Type == 1) {
                myDrawable = getResources().getDrawable(R.drawable.task_exit);
                myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                stream = new ByteArrayOutputStream();
                myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                });
                lblTimeCondition.setText(enterTime);
                lblTimeTitle.setText("زمان ورود");
                viewTimeSet.setVisibility(View.GONE);
                lblTimeCondition.setVisibility(View.VISIBLE);
                viewFactor.setVisibility(View.VISIBLE);
                spinResult.setVisibility(View.VISIBLE);

                Type = 1;
            }else {
                card2.setVisibility(View.VISIBLE);
                myDrawable = getResources().getDrawable(R.drawable.task_complete);
                myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                stream = new ByteArrayOutputStream();
                myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                });
                lblTimeCondition.setText(enterTime);
                viewTimeSet.setVisibility(View.GONE);
                lblTimeCondition.setVisibility(View.VISIBLE);
                viewFactor.setVisibility(View.VISIBLE);
                spinResult.setVisibility(View.VISIBLE);
                lblTimeTitle.setText("زمان خروج");
                Type = 2;
            }
        }
        RefreshFactorList();
        if (Date.length() > 0) {
            cardAcceptTask.setVisibility(View.VISIBLE);
            viewEnterExit.setVisibility(View.GONE);
            viewFactor.setVisibility(View.GONE);
            lblTimeSet.setText("");
            imgAlarmSet.setVisibility(View.GONE);
            spinSubAct.setSelection(ActSelected);
            txtDetails.setText(Descriptioned);
            txtName.setText(Nameioned);

            lblTimeSet.setText(Date + " - " + TimeToSend + " به مدت " + Integer.toString(duration));
            lblTimeSet.setVisibility(View.VISIBLE);
            imgAlarmSet.setVisibility(View.GONE);
            if (isAlarmSet) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.alarm_selected);
                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAlarmSet) {
                });
            } else {
                Drawable myDrawable = getResources().getDrawable(R.drawable.alarm_);
                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAlarmSet) {
                });
            }
        } else {
            lblTimeSet.setText("");
            imgAlarmSet.setVisibility(View.GONE);
            lblTimeSet.setVisibility(View.GONE);
            imgCancellTask.setVisibility(View.GONE);
        }
        if (toEdite) {
            btnTaskTimeSet.setVisibility(View.GONE);
            txtName.setText(Name);
            txtDetails.setText(Description);
            spinSubAct.setSelection(spinAdapter_SubAct.getItemPosition("ActID", Integer.toString(lData.ActID)));
//            spinResult.setSelection(spinAdapter_Result.getItemPosition("ActResultID", Integer.toString(lData.sub_act_result_id())));//todo todo todo
            Drawable myDrawable;
            Bitmap myLogo;
            ByteArrayOutputStream stream;
            int mState = 0;
            String EnterDater = "", ExitDate = "";
            if(lData.EnterDate != null)
                EnterDater = lData.EnterDate;
            if(lData.ExitDate != null)
                ExitDate = lData.EnterDate;
            if(lData.TodoDate.length() > 5 && EnterDater.length() < 5 && ExitDate.length() < 5){
                mState = 1;//Hichi nakhorde , faghat vazifast
            }else if(lData.TodoDate.length() > 5 && EnterDater.length() > 5 && ExitDate.length() < 5){
                mState = 2;//Vorud khorde
            }else if(lData.TodoDate.length() > 5 && EnterDater.length() > 5 && ExitDate.length() > 5){
                mState = 3;//Khoruj khorde ya Tayid shode
            }
//            switch (lData.StateID) {
            switch (mState) {
                case 1:
                    myDrawable = getResources().getDrawable(R.drawable.task_enter);
                    myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {});
                    lblTimeTitle.setText("زمان ورود");
                    viewTimeSet.setVisibility(View.GONE);
                    lblTimeCondition.setVisibility(View.GONE);
                    viewFactor.setVisibility(View.GONE);
                    spinResult.setVisibility(View.GONE);

                    txtName.setEnabled(false);
                    Type = 0;
                    break;
                case 2:
                    myDrawable = getResources().getDrawable(R.drawable.task_exit);
                    myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                    });
                    DateConverter vDC = new DateConverter(lData.EnterDate, true);
                    EnterTime = vDC.Hour() + ":" + vDC.Minute();
                    lblTimeCondition.setText(EnterTime);
                    lblTimeTitle.setText("زمان ورود");
                    viewTimeSet.setVisibility(View.GONE);
                    lblTimeCondition.setVisibility(View.VISIBLE);
                    viewFactor.setVisibility(View.VISIBLE);
                    spinResult.setVisibility(View.VISIBLE);

                    txtName.setEnabled(false);
                    Type = 1;
                    break;
                case 3:
                    card2.setVisibility(View.VISIBLE);
                    myDrawable = getResources().getDrawable(R.drawable.task_complete);
                    myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                    });
                    DateConverter bDC = new DateConverter(lData.ExitDate, true);
                    ExitTime = bDC.Hour() + ":" + bDC.Minute();
                    lblTimeCondition.setText(ExitTime);
                    lblTimeCondition.setVisibility(View.VISIBLE);
                    viewFactor.setVisibility(View.VISIBLE);
                    spinResult.setVisibility(View.VISIBLE);
                    lblTimeTitle.setText("زمان خروج");
                    Type = 2;
                    break;
//                case 6://todo tayid shode bashe
//                    if (lData.ExitDate != null) {
//                        card2.setVisibility(View.VISIBLE);
//                        Drawable myoDrawable = getResources().getDrawable(R.drawable.task_complete);
//                        Bitmap myLogoo = ((BitmapDrawable) myoDrawable).getBitmap();
//                        ByteArrayOutputStream streamo = new ByteArrayOutputStream();
//                        myLogoo.compress(Bitmap.CompressFormat.PNG, 100, streamo);
//                        Glide.with(context).load(streamo.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
//                        });
//                        DateConverter mDC = new DateConverter(lData.ExitDate, true);
//                        ExitTime = mDC.Hour() + ":" + mDC.Minute();
//                        lblTimeCondition.setText(ExitTime);
//                        lblTimeTitle.setText("زمان خروج");
//                        lblTimeCondition.setVisibility(View.VISIBLE);
//                        viewFactor.setVisibility(View.VISIBLE);
//                        Type = 2;
//                    } else {
//                        myDrawable = getResources().getDrawable(R.drawable.task_exit);
//                        myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//                        stream = new ByteArrayOutputStream();
//                        myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
//                        });
//                        DateConverter vDCc = new DateConverter(lData.EnterDate, true);
//                        EnterTime = vDCc.Hour() + ":" + vDCc.Minute();
//                        lblTimeCondition.setText(EnterTime);
//                        lblTimeTitle.setText("زمان ورود");
//                        viewTimeSet.setVisibility(View.GONE);
//                        lblTimeCondition.setVisibility(View.VISIBLE);
//                        viewFactor.setVisibility(View.VISIBLE);
//                        spinResult.setVisibility(View.VISIBLE);
//
//                        txtName.setEnabled(false);
//                        Type = 1;
//                    }
//                    break;
                default:
                    DateConverter kDC = new DateConverter(lData.TodoDate, true);
                    String Dater = kDC.getStringLongDateTime(1);
//                    lblTimeSet.setText(Dater + " به مدت " + Integer.toString(lData.DurationDate));//todo todo todo Duration Changed to DateTime
                    lblTimeSet.setVisibility(View.VISIBLE);
                    imgAlarmSet.setVisibility(View.GONE);
                    if (lData.hasReminder) {
                        Drawable mDrawable = getResources().getDrawable(R.drawable.alarm_selected);
                        Bitmap mLogo = ((BitmapDrawable) mDrawable).getBitmap();
                        ByteArrayOutputStream Mstream = new ByteArrayOutputStream();
                        mLogo.compress(Bitmap.CompressFormat.PNG, 100, Mstream);
                        Glide.with(context).load(Mstream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAlarmSet) {
                        });
                    } else {
                        Drawable yDrawable = getResources().getDrawable(R.drawable.alarm_);
                        Bitmap yLogo = ((BitmapDrawable) yDrawable).getBitmap();
                        ByteArrayOutputStream Nstream = new ByteArrayOutputStream();
                        yLogo.compress(Bitmap.CompressFormat.PNG, 100, Nstream);
                        Glide.with(context).load(Nstream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAlarmSet) {
                        });
                    }
                    Type = 0;
                    break;
            }
            //TODO Factor ham begire ba khodesh biare inja List o por kone
        }
    }
    private void RefreshFactorList() {
        adapFactors adapter = new adapFactors(lFactor, context);
        lstFactor.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstFactor.setLayoutManager(mLayoutManager);
        lstFactor.addItemDecoration(ItemDecoration.getDecoration(context));
        lstFactor.setItemAnimator(new DefaultItemAnimator());
        lstFactor.setAdapter(adapter);
    }
    private void SetEnterExit() {
        btnCheck.setVisibility(View.VISIBLE);
        if (Type == 0) {
            if (txtName.getText().toString().length() > 0 && spinSubAct.getSelectedItemPosition() > 0) {
                pDialog = new Dialog(context);
                pDialog.Show();

                if(currentId > 0){
                    ObjectMapper oMapper = new ObjectMapper();
                    HashMap map = oMapper.convertValue(getEnterDataForTask(), HashMap.class);

                    Call cEnterActivityForTask = rInterface.RQAddActivityEnterForTask(Setting.getToken(), map);
                    cEnterActivityForTask.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    currentId = Integer.parseInt(response.body().AdditionalData.get("ActivityEnterForTaskChange").toString().replace(".0",""));//todo age in fix shod in replace ro bardar ( id ro intri mide 1.0 ya 2.0 )

                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                Drawable myDrawable = getResources().getDrawable(R.drawable.task_exit);
                                                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                                                });
                                                String EnteryDate = NowDateTime;
                                                DateConverter mDC = new DateConverter(EnteryDate, true);
                                                EnterTime = mDC.Hour() + ":" + mDC.Minute();
                                                lblTimeCondition.setText(EnterTime);
                                                lblTimeTitle.setText("زمان ورود");
                                                viewTimeSet.setVisibility(View.GONE);
                                                lblTimeCondition.setVisibility(View.VISIBLE);
                                                viewFactor.setVisibility(View.VISIBLE);
                                                spinResult.setVisibility(View.VISIBLE);

                                                txtName.setEnabled(false);
                                                Type = 1;
                                                if (toEdite) {
                                                    fragTaskShow.lData.EnterDate = (EnteryDate);
                                                    fragTaskShow.lData.StateID = (113001);//todo todo todo Check StateID
                                                }
                                            } catch (Exception Ex) {
                                                String Er = Ex.getMessage();
                                            }
                                        }
                                    });
                                } catch (Exception Ex) {
                                    mToast("خطا در برقراری ارتباط با سرور.");
                                }
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                }else{
                    ObjectMapper oMapper = new ObjectMapper();
                    HashMap map = oMapper.convertValue(getEnterData(), HashMap.class);

                    Call cEnterActivity = rInterface.RQAddActivityEnter(Setting.getToken(), map);
                    cEnterActivity.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    currentId = Integer.parseInt(response.body().AdditionalData.get("ActivityID").toString().replace(".0", ""));//todo age in fix shod in replace ro bardar ( id ro intri mide 1.0 ya 2.0 )

                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                Drawable myDrawable = getResources().getDrawable(R.drawable.task_exit);
                                                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                                                });
                                                String EnteryDate = NowDateTime;
                                                DateConverter mDC = new DateConverter(EnteryDate, true);
                                                EnterTime = mDC.Hour() + ":" + mDC.Minute();
                                                lblTimeCondition.setText(EnterTime);
                                                lblTimeTitle.setText("زمان ورود");
                                                viewTimeSet.setVisibility(View.GONE);
                                                lblTimeCondition.setVisibility(View.VISIBLE);
                                                viewFactor.setVisibility(View.VISIBLE);
                                                spinResult.setVisibility(View.VISIBLE);

                                                txtName.setEnabled(false);
                                                Type = 1;
                                                if (toEdite) {
                                                    fragTaskShow.lData.EnterDate = (EnteryDate);
                                                    fragTaskShow.lData.StateID = (113001);//todo todo todo Check StateID
                                                }
                                            } catch (Exception Ex) {
                                                String Er = Ex.getMessage();
                                            }
                                        }
                                    });
                                } catch (Exception Ex) {
                                    mToast("خطا در برقراری ارتباط با سرور.");
                                }
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                }
            } else
                Toast.makeText(context, "لطفا نام و نوع فعالیت را مشخص کنید", Toast.LENGTH_LONG).show();
        } else if (Type == 1) {
            int a = spinResult.getSelectedItemPosition();
            if (spinResult.getSelectedItemPosition() > 0) {
                pDialog = new Dialog(context);
                pDialog.Show();

                if(lFactor.size() > 0){
                    MultipartBody.Part[] body = new MultipartBody.Part[lFactor.get(0).InvoiceImage.size()];//todo todo hamishe avalin factor ro add mikone ( axash o )
                    for (int i = 0; i < lFactor.get(0).InvoiceImage.size(); i++) {
                        File file = new File(lFactor.get(i).InvoiceImage.get(i).ImageFilename);
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                        body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                    }

                    Call<SimpleResponse> addInvoiceImage = rInterface.RQAddInvoicePic(Setting.getToken(), body);
                    addInvoiceImage.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                List<ToSendInvoiceImage> lURLs = new ArrayList<>();
                                try {
                                    Object[] keys = response.body().AdditionalData.keySet().toArray();
                                    for (Object data : keys) {
                                        String val = response.body().AdditionalData.get(data.toString()).toString();
                                        ToSendInvoiceImage tData = new ToSendInvoiceImage();
                                        tData.InvoiceFileName = val;
                                        lURLs.add(tData);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                ObjectMapper oMapper = new ObjectMapper();
                                HashMap map = oMapper.convertValue(getExitData(lURLs), HashMap.class);

                                Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                                cExitActivity.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                getActivity().runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        try {
                                                            card2.setVisibility(View.VISIBLE);
                                                            Drawable myDrawable = getResources().getDrawable(R.drawable.task_complete);
                                                            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                            myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                            Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                                                            });
                                                            String ExitDate = NowDateTime;
                                                            DateConverter mDC = new DateConverter(ExitDate, true);
                                                            ExitTime = mDC.Hour() + ":" + mDC.Minute();
                                                            lblTimeCondition.setText(ExitTime);
                                                            lblTimeTitle.setText("زمان خروج");
                                                            Type = 2;
                                                            if (toEdite) {
                                                                fragTaskShow.lData.ExitDate = (ExitDate);
                                                                fragTaskShow.lData.StateID = (113002);//todo todo tod Check StateID
                                                                int ResultID = Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "id"));
                                                                fragTaskShow.lData.ActivityResultID = (ResultID);
                                                            }
                                                            spinResult.setVisibility(View.GONE);
                                                        } catch (Exception Ex) {
                                                            String Er = Ex.getMessage();
                                                        }
                                                    }
                                                });
                                            } catch (Exception Ex) {
                                                mToast("خطا در برقراری ارتباط با سرور.");
                                            }
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
                        }
                    });
                }else {
                    ObjectMapper oMapper = new ObjectMapper();
                    HashMap map = oMapper.convertValue(getExitData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                    Call cExitActivity = rInterface.RQAddActivityExit(Setting.getToken(), map);
                    cExitActivity.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if (response.isSuccessful()) {
                                try {
                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                card2.setVisibility(View.VISIBLE);
                                                Drawable myDrawable = getResources().getDrawable(R.drawable.task_complete);
                                                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {
                                                });
                                                String ExitDate = NowDateTime;
                                                DateConverter mDC = new DateConverter(ExitDate, true);
                                                ExitTime = mDC.Hour() + ":" + mDC.Minute();
                                                lblTimeCondition.setText(ExitTime);
                                                lblTimeTitle.setText("زمان خروج");
                                                Type = 2;
                                                if (toEdite) {
                                                    fragTaskShow.lData.ExitDate = (ExitDate);
                                                    fragTaskShow.lData.StateID = (113002);//todo todo tod Check StateID
                                                    int ResultID = Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "id"));
                                                    fragTaskShow.lData.ActivityResultID = (ResultID);
                                                }
                                                spinResult.setVisibility(View.GONE);
                                            } catch (Exception Ex) {
                                                String Er = Ex.getMessage();
                                            }
                                        }
                                    });
                                } catch (Exception Ex) {
                                    mToast("خطا در برقراری ارتباط با سرور.");
                                }
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                }
            } else mToast("لطفا نتیجه را مشخص کنید.");
        }
    }
    private void mToast(String Message) {
        Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
    }
    private ToSendActivityEnter getEnterData() {
        ToSendActivityEnter data = new ToSendActivityEnter();
        data.ParentID = (mParentID);
        data.ActivityAddedByUserID = Setting.getUserID();
        data.ActivityOwnerUserID = Setting.getBMMUserID();
        data.CustomerID = (customer_id);
        data.Title = (txtName.getText().toString());
        int ActID = getActivityFieldID();
        int ActivityTypeID = getActivityTypeID(ActID);
        data.ActivityType = ActivityTypeID;
        data.ActID = ActID;
        if (txtDetails.getText().toString().length() > 0)
            data.ActivityDescription = (txtDetails.getText().toString());
        data.EnterLatutide = (1.1);
        data.EnterLongitude = (1.1);
        data.EnterDate = (NowDateTime);
        return data;
    }
    private ToSendActivityEnterForTask getEnterDataForTask() {
        ToSendActivityEnterForTask data = new ToSendActivityEnterForTask();
        data.ActivityID = (currentId);
        data.Title = (txtName.getText().toString());
        int ActID = getActivityFieldID();
        data.ActID = ActID;
        if (txtDetails.getText().toString().length() > 0)
            data.ActivityDescription = (txtDetails.getText().toString());
        data.EnterLatutide = (1.1);
        data.EnterLongitude = (1.1);
        data.EnterDate = (NowDateTime);
        return data;
    }
    private int getActivityFieldID() {
        return Integer.parseInt(spinAdapter_SubAct.getItemString(spinSubAct.getSelectedItemPosition(), "ActID"));
    }
    private int getActivityTypeID(int FieldID) {
        List<Basic_Acts> lFild = geter.getList(Basic_Acts.class, " WHERE ActID='" + FieldID + "'");
        return lFild.get(0).ActGroupID;
    }
    private ToSendActivityExit getExitData(List<ToSendInvoiceImage> lImages) {//$$$$%%$$$$
        ToSendActivityExit data = new ToSendActivityExit();
        data.ActivityID = (currentId);
        if (sResult == 0)
            sResult = Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID"));//todo todo todo
        data.ActivityResultID = (sResult);
        if (txtDetails.getText().toString().length() > 0)
            data.ActivityDescription = (txtDetails.getText().toString());
        data.ExitLatutide = (1.1);
        data.ExitLongitude = (1.1);
        data.ExitDate = (NowDateTime);
        data.Invoices = getInvoices(lImages);

        return data;
    }
    private List<ToSendInvoice> getInvoices(List<ToSendInvoiceImage> lImages) {
        List<ToSendInvoice> InvoiceSend = new ArrayList<>();
        for (Invoice datas : lFactor) {
            ToSendInvoice inv = new ToSendInvoice();
            inv.InvoiceNumber = datas.InvoiceNumber;
            inv.InvoiceMarketingProductID = datas.InvoiceMarketingProductID;
            inv.InvoiceActivityID = datas.InvoiceActivityID;
            inv.InvoicePrice = datas.InvoicePrice;
            inv.InvoiceDescription = datas.InvoiceDescription;
            inv.InvoiceImages = lImages;

            InvoiceSend.add(inv);
        }
        return InvoiceSend;
    }
    private ToSendActivityConfirmAndSend getActConfirmData(List<ToSendInvoiceImage> lImages) {//$$$$%%$$$$
        ToSendActivityConfirmAndSend data = new ToSendActivityConfirmAndSend();
        data.ActivityID = (currentId);
        if (sResult == 0)
            sResult = Integer.parseInt(spinAdapter_Result.getItemString(spinResult.getSelectedItemPosition(), "ActResultID"));//todo todo todo
        data.ActivityResultID = (sResult);
        if (txtDetails.getText().toString().length() > 0)
            data.ActivityDescription = (txtDetails.getText().toString());
        data.Invoices = getInvoices(lImages);
        return data;
    }
    private ToSendAddTask getTaskConfirmData() {
        ToSendAddTask data = new ToSendAddTask();
        int activFieldID = getActivityFieldID();
        int ActivityTypeID = getActivityTypeID(activFieldID);
        data.ActivityType = ActivityTypeID;
        data.ActivityAddedByUserID = Setting.getUserID();
        data.ActivityOwnerUserID = Setting.getUserID();

        data.CustomerID = (customer_id);
        data.Title = (txtName.getText().toString());
        data.ActID = (Integer.parseInt(spinAdapter_SubAct.getItemString(spinSubAct.getSelectedItemPosition(), "ActID")));
        if (txtDetails.getText().toString().length() > 0)
            data.ActivityDescription = (txtDetails.getText().toString());
        DateConverter DC = new DateConverter(DateToSend, TimeToSend);
        data.TaskDate = (DC.getCSharp2());
        data.DurationDate=("2018-01-01T11:11:11");//todo todo todo Duration change to DateTime
//        data.hasReminder = (isAlarmSet);
        return data;
    }
    private void resetAll(){
        lSubAct = new ArrayList<>();
        lSubActResult = new ArrayList<>();
        lFactor = new ArrayList<>();
        lData = new Activities();


        //Variables
        Type = 0;//0 = Hichie Hichi, 1 = Vorud khorde, 2 = Khoruj khorde, 3 = zaman khorde, 4 = tayid shode, 5 = ersal shode
        currentId = 0;

        sResult = 0;
        mParentID = 0;
        customer_id = 0;
        busines_id = 0;
        duration = 0;
        ActSelected = 0;
        Descriptioned = "";
        Date = "";
        DateToSend = "";
        TimeToSend = "";
        isAlarmSet = false;
        toEdite = false;
        todoDate = "2018-01-01T00:00:00";
        Description = "";
        EnterTime = "2018-01-01T00:00:00";
        ExitTime = "2018-01-01T00:00:00";
        NowDateTime = "2018-01-01T00:00:00";

        Namee = "";
        Details = "";
        enterTime = "";
        spin1 = 0;
        spin2 = 0;
        boolean fac = false;

        if (Name != null) txtName.setText(Name);
        txtDetails.setText("");
        spinSubAct.setSelection(0);
        spinResult.setSelection(0);
        lblTimeCondition.setText("");
        lblTimeSet.setText("");

        Drawable myDrawable;
        Bitmap myLogo;
        ByteArrayOutputStream stream;
        myDrawable = getResources().getDrawable(R.drawable.task_enter);
        myLogo = ((BitmapDrawable) myDrawable).getBitmap();
        stream = new ByteArrayOutputStream();
        myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(btnActCondition) {});
        lblTimeTitle.setText("زمان ورود");
        viewTimeSet.setVisibility(View.VISIBLE);
        lblTimeCondition.setVisibility(View.VISIBLE);
        viewFactor.setVisibility(View.GONE);
        spinResult.setVisibility(View.GONE);
        btnTaskTimeSet.setVisibility(View.VISIBLE);
        linEnterExit.setVisibility(View.VISIBLE);
        btnActCondition.setVisibility(View.VISIBLE);
        viewTimes.setVisibility(View.VISIBLE);
        viewEnterExit.setVisibility(View.VISIBLE);
        lblTimeTitle.setVisibility(View.VISIBLE);
        imgCancellTask.setVisibility(View.GONE);

        txtName.setEnabled(false);
        Type = 0;
    }
    private void Finisher(){
        lSubAct = new ArrayList<>();
        lSubActResult = new ArrayList<>();
        lFactor = new ArrayList<>();
        lData = new Activities();
        Type = 0;//0 = Hichie Hichi, 1 = Vorud khorde, 2 = Khoruj khorde, 3 = zaman khorde, 4 = tayid shode, 5 = ersal shode
        currentId = 0;
        sResult = 0;
        mParentID = 0;
        customer_id = 0;
        busines_id = 0;
        duration = 0;
        ActSelected = 0;
        Descriptioned = "";
        Name = "";
        Date = "";
        DateToSend = "";
        TimeToSend = "";
        isAlarmSet = false;
        toEdite = false;
        todoDate = "2018-01-01T00:00:00";
        Description = "";
        EnterTime = "2018-01-01T00:00:00";
        ExitTime = "2018-01-01T00:00:00";
        NowDateTime = "2018-01-01T00:00:00";
        Namee = "";
        Details = "";
        enterTime = "";
        spin1 = 0;
        spin2 = 0;
        fac = false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }

}
