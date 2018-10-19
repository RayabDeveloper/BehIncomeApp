package com.behincom.behincome.Activityes.Main;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Account.actProfile;
import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragAddTaskSetTime;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Activities.adapMainTask;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragTasks extends Fragment {

    static Context context;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    actMain act = new actMain();
    @SuppressLint("StaticFieldLeak")
    Dialog mDialog;
    DateConverter DC = new DateConverter();
    PersianCalendar PC = new PersianCalendar();
    DatePickerDialog DateDialog;
    RWInterface rInterface;

    TextView lblTaskList, lblDate, lblToDay;
    RecyclerView lstTask;
    FloatingActionButton btnAdd;
    LinearLayout btnHome1, btnHome2, btnReport, btnAccount;
    TextView lblHome1, lblHome2, lblReport, lblAccount;
    ImageView imgHome1, imgHome2, imgReport, imgAccount;
    ImageView imgSetting;
    RecyclerView.LayoutManager mLayoutManager;
    ImageView btnFilter, btnToLeftDate, btnToRightDate;
    boolean isAlarmCompleted = false;

    static List<Activities> lActivityes = new ArrayList<>();
    List<Activities> lActivityes2 = new ArrayList<>();
    int AlarmCount = 0;
    private static String CurrentDateTime = "";
    private static String StaticCurrentDateTime = "";
    private boolean isDate = false;

    public static fragTasks newInstance(Context mContext){
        fragTasks fragment = new fragTasks();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tasks, container, false);

        imgSetting = view.findViewById(R.id.imgSetting);
        btnHome1 = view.findViewById(R.id.btnHome1);
        btnHome2 = view.findViewById(R.id.btnHome2);
        btnReport = view.findViewById(R.id.btnReport);
        btnAccount = view.findViewById(R.id.btnAccount);
        lblHome1 = view.findViewById(R.id.lblHome1);
        lblHome2 = view.findViewById(R.id.lblHome2);
        lblReport = view.findViewById(R.id.lblReport);
        lblAccount = view.findViewById(R.id.lblAccount);
        imgHome1 = view.findViewById(R.id.imgHome1);
        imgHome2 = view.findViewById(R.id.imgHome2);
        imgReport = view.findViewById(R.id.imgReport);
        imgAccount = view.findViewById(R.id.imgAccount);
        btnAdd = view.findViewById(R.id.btnAdd);
        lblToDay = view.findViewById(R.id.lblToDay);
        lblTaskList = view.findViewById(R.id.lblTaskList);
        lblDate = view.findViewById(R.id.lblDate);
        lstTask = view.findViewById(R.id.lstTask);
        btnFilter = view.findViewById(R.id.btnFilter);
        btnToLeftDate = view.findViewById(R.id.btnToLeftDate);
        btnToRightDate = view.findViewById(R.id.btnToRightDate);
        mLayoutManager = new LinearLayoutManager(getActivity());

        DateConverter mDC = new DateConverter(Setting.getServerDateTime(), true);
        final String gDate = mDC.GetDateToHijri();
        lblDate.setText(gDate);

        lstTask.setLayoutManager(mLayoutManager);
        lstTask.setHasFixedSize(true);
        lstTask.addItemDecoration(ItemDecoration.getDecoration(context));
        lstTask.setItemAnimator(new DefaultItemAnimator());

        imgSetting.setVisibility(View.VISIBLE);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actSetting.class);
                startActivity(intent);
            }
        });
        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                act.getFragByState(FragmentState.MainTasks);
            }
        });
        btnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MainCustomers);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MainReports);
            }
        });
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MainAccounts);
            }
        });

        DateDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                    }
                },
                PC.getPersianYear(),
                PC.getPersianMonth(),
                PC.getPersianDay()
        );
        DateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                isDate = false;
            }
        });
        lblDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDate) {
                    isDate = true;
                    DateDialog = DatePickerDialog.newInstance(
                            new DatePickerDialog.OnDateSetListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    String mY = Integer.toString(year);
                                    String mM = Integer.toString(monthOfYear + 1);
                                    String mD = Integer.toString(dayOfMonth);

                                    if(mM.length() == 1)
                                        mM = "0" + mM;
                                    if(mD.length() == 1)
                                        mD = "0" + mD;

                                    lblDate.setText(mY + "/" + mM + "/" + mD);
                                    DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                                    getActivityes(DC.getCSharp());
                                    CurrentDateTime = DC.getCSharp();
                                    isDate = false;
                                }
                            },
                            PC.getPersianYear(),
                            PC.getPersianMonth(),
                            PC.getPersianDay()
                    );
                    DateDialog.show(getActivity().getFragmentManager(), "DateSetter");
                }
            }
        });
        lblToDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateConverter DC = new DateConverter(StaticCurrentDateTime, true);
                CurrentDateTime = StaticCurrentDateTime;
                lblDate.setText(DC.GetDateToHijri());
                getActivityes(CurrentDateTime);
            }
        });
        btnToRightDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation shake;
                shake = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.shake_icon);
                btnToRightDate.startAnimation(shake);

                DateConverter DC = new DateConverter(CurrentDateTime, true);
                CurrentDateTime = DC.MinusGetToMiladi(1);
                lblDate.setText(DC.MinusGetToShamsi(1));
                getActivityes(CurrentDateTime);
            }
        });
        btnToLeftDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation shake;
                shake = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.shake_icon);
                btnToLeftDate.startAnimation(shake);

                DateConverter DC = new DateConverter(CurrentDateTime, true);
                CurrentDateTime = DC.AddGetToMiladi(1);
                lblDate.setText(DC.AddGetToShamsi(1));
                getActivityes(CurrentDateTime);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(context);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setCancelable(true);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.setContentView(R.layout.dialog_filter_task);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView btnRunning = mDialog.findViewById(R.id.btnRunning);
                TextView btnCompleted = mDialog.findViewById(R.id.btnCompleted);
                TextView btnAll = mDialog.findViewById(R.id.btnAll);

                btnRunning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FilterTasks(TaskFilter.Running);//Vorud Khorde
                        mDialog.dismiss();
                    }
                });
                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FilterTasks(TaskFilter.Completed);//Ersal Shode
                        mDialog.dismiss();
                    }
                });
                btnAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FilterTasks(TaskFilter.All);//Hame
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), actActivities.class);
                fragAddTaskSetTime.mType = true;
                fragAddTaskSetTime.isForGroup = true;
                fragAddTaskSetTime.GroupStartEndType = 0;
                actActivities.STATE = FragmentState.AddTaskSetTime;
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        rInterface = Retrofite.getClient().create(RWInterface.class);
        if(CurrentDateTime.length() == 0)
            getActivityes();
        else
            getActivityes(CurrentDateTime);
        Alarms();
    }
    private enum TaskFilter{
        Running,
        Completed,
        All
    }
    private void FilterTasks(TaskFilter Type){
        lActivityes2 = new ArrayList<>();
        switch (Type){
            case Running:
                for (Activities data : lActivityes) {
                    if(data.EnterDate.length() > 5 && data.ExitDate.length() < 5)
                        lActivityes2.add(data);
                }
                break;
            case Completed:
                for (Activities data : lActivityes) {
                    if(data.EnterDate.length() > 5 && data.ExitDate.length() > 5)
                        lActivityes2.add(data);
                }
                break;
            case All:
                lActivityes2.addAll(lActivityes);
                break;
        }
        RefreshList();
    }
    private void RefreshList(){
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                adapMainTask adapter = new adapMainTask(lActivityes2, context);
                lstTask.setAdapter(adapter);
            }
        });
    }
    private void getActivityes(){
        try {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Call GetTime = rInterface.RQGetServerDateTime();
                    GetTime.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                Setting.Save("ServerDateTime", response.body());
                                String[] metaDate = response.body().split("T");
                                CurrentDateTime = metaDate[0] + "T00:00:00";
                                StaticCurrentDateTime = CurrentDateTime;



                                Call cGetAll = rInterface.RQGetTasksToDay(Setting.getToken());
                                cGetAll.enqueue(new Callback<List<Activities>>() {
                                    @Override
                                    public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                                        if(response.isSuccessful()){
                                            DateConverter mDC = new DateConverter(CurrentDateTime, true);
                                            final String gDate = mDC.GetDateToHijri();

                                            lActivityes = response.body();

                                            try {
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        adapMainTask adapter = new adapMainTask(lActivityes, getActivity());
                                                        lstTask.setAdapter(adapter);
                                                        lblDate.setText(gDate);
                                                    }
                                                });
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        String CheShode = t.getMessage();
                                        String ooG = "ASD";
                                    }
                                });
                            } else {
                                String onError = "";
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            String Er = t.getMessage();
                        }
                    });
                }
            });
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    private void getActivityes(final String Date){
        try {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("DateTime", Date);

                    Call cGetAll = rInterface.RQGetTasksByDate(Setting.getToken(), map);
                    cGetAll.enqueue(new Callback<List<Activities>>() {
                        @Override
                        public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                            if(response.isSuccessful()){
                                DateConverter mDC = new DateConverter(CurrentDateTime, true);
                                final String gDate = mDC.GetDateToHijri();

                                lActivityes = response.body();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapMainTask adapter = new adapMainTask(lActivityes, getActivity());
                                        lstTask.setAdapter(adapter);
                                        lblDate.setText(gDate);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            String CheShode = t.getMessage();
                            String ooG = "ASD";
                        }
                    });
                }
            });
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    private void Alarms(){
//        try {
//            getActivity().runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    Map<String, String> Header = Data.Header(context);
//                    Map<String, String> Body = new HashMap<>();
//
//                    final MySQLi SQL = new MySQLi(context);
//                    final QueryMacker QMacker = new QueryMacker();
//                    String Q = QMacker.MethodInputer(Enums.SQLMethod.SELECT, Enums.Tables.TB_MB);
//                    List<Data.MarketerBusinesman> lMBb = SQL.Select(Q, Data.MarketerBusinesman.class, context);
//                    String MBId = Integer.toString(lMBb.get(0).id());
//
//                    WebApi wAPI_Alarm = new WebApi();
//                    String mURL = "";
//                    try {
//                        String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
//                        mURL = Data.APIKeys.Reminder + Uri.encode("?_filters=business_manager_marketer_id eq " + MBId, ALLOWED_URI_CHARS);
//                    } catch (Exception Ex) {
//                        String Er = Ex.getMessage();
//                    }
//                    wAPI_Alarm.API(Request.Method.GET, mURL, Header, Body);
//
//
//                    wAPI_Alarm.setOnIncomingResult(new WebApi.onResponseResult() {
//                        @Override
//                        public void onResponseResults(final String Result, int StatusCode) {
//                            if (!isAlarmCompleted) {
//                                isAlarmCompleted = true;
//                                if (statusChecker.isSuccess(Result, StatusCode)) {
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                JSONObject jo = new JSONObject(Result);
//                                                JSONArray jAlarm = jo.getJSONArray(Data.APIKeys.data);
//
//                                                int AlarmEnableCount = 0;
//
//                                                List<DataAlarm> lAlarm = new ArrayList<>();
//                                                for (int i = 0; i < jAlarm.length(); i++) {
//                                                    DataAlarm data = new DataAlarm();
//                                                    JSONObject mj = jAlarm.getJSONObject(i);
//                                                    data.id(mj.getInt("id"));
//                                                    data.title(mj.getString("title"));
//                                                    try {
//                                                        data.description(mj.getString("description"));
//                                                    }catch (Exception Ex){
//                                                        String Er = Ex.getMessage();
//                                                    }
//                                                    try {
//                                                        data.customer_id(mj.getInt("customer_id"));
//                                                    } catch (Exception ignored) {
//                                                    }
//                                                    data.business_manager_marketer_id(mj.getInt("business_manager_marketer_id"));
//                                                    try {
//                                                        data.activity_id(mj.getInt("activity_id"));
//                                                    } catch (Exception ignored) {
//                                                    }
//                                                    data.active((mj.getBoolean("active")));
//                                                    data.fire_date(mj.getString("fire_date"));
//                                                    data.creation_date_time(mj.getString("creation_date_time"));
//
//                                                    lAlarm.add(data);
//
//                                                    List<DataAlarm> tData = SQL.getAlarm(data.id());
//                                                    String Q = "";
//                                                    if (tData.size() == 0) {
//                                                        Q = QMacker.Create(Enums.SQLMethod.INSERT, Enums.Tables.TB_Alarm,
//                                                                "(id, title, description, customer_id, business_manager_marketer_id, activity_id, active, fire_date)") +
//                                                                "('" + data.id() +
//                                                                "', '" + data.title() +
//                                                                "', '" + (data.description() == null ? "" : data.description()) +
//                                                                "', '" + data.customer_id() +
//                                                                "', '" + data.business_manager_marketer_id() +
//                                                                "', '" + data.activity_id() +
//                                                                "', '" + (data.active() ? 1 : 0) +
//                                                                "', '" + data.fire_date() + "')";
//                                                    } else {
//                                                        Q = "UPDATE TB_Alarm SET title='" + data.title() +
//                                                                "', description='" + (data.description() == null ? "" : data.description()) +
//                                                                "', customer_id='" + data.customer_id() +
//                                                                "', business_manager_marketer_id='" + data.business_manager_marketer_id() +
//                                                                "', activity_id='" + data.activity_id() +
//                                                                "', active='" + (data.active() ? 1 : 0) +
//                                                                "', fire_date='" + data.fire_date() + "' WHERE id='" + data.id() + "'";
//                                                    }
//                                                    SQL.Execute(Q);
//                                                    if(data.active())
//                                                        AlarmEnableCount++;
//                                                }
//                                                new AlarmIntent(context);
//                                                if(AlarmEnableCount > 0) {
//                                                    fragMain.lblAlarmCounter.setText(Integer.toString(AlarmEnableCount));
//                                                    fragMain.lblAlarmCounter.setVisibility(View.VISIBLE);
//                                                }else
//                                                    fragMain.lblAlarmCounter.setVisibility(View.GONE);
//                                            } catch (JSONException Ex) {
//                                                String Er = Ex.getMessage();
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                    });
//                }
//            });
//        }catch (Exception Ex){
//            String Er = Ex.getMessage();
//        }
    }

}
