package com.behincom.behincome.Activityes.Activities;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.Activities.adapTask;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.DataDateSpacing;
import com.behincom.behincome.Datas.Activityes.DataGroupTask;
import com.behincom.behincome.Datas.Activityes.DataTodo;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.DataDates;
import com.behincom.behincome.Datas.Date.CurrentHour;
import com.behincom.behincome.Datas.Date.CurrentMinute;
import com.behincom.behincome.Datas.Date.CurrentMonth;
import com.behincom.behincome.Datas.Date.CurrentYear;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingVisitTours;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragAddTaskSetTime extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    DateConverter DC = new DateConverter();
    PersianCalendar PC = new PersianCalendar();
    TimePickerDialog TimePicker;
    Dialog mDialog, dDialog;
    Thread mThread;
    RWInterface rInterface;
    com.behincom.behincome.Accesories.Dialog pDialog;
    actActivities act = new actActivities();
    static SpinAdapter YearAdap, MonthAdap, DayAdap, HourAdap, MinAdap, DurAdap;

    //    TextView lblSetTime;
    TextView lblYear;
    TextView lblMonth;
    //    TextView btnSetTime;
    TextView lblAlarm;
    TextView lblTitle;
    TextView lblAccept;
    TextView lblError;
    TextView lblError2;
    TextView lblStartTime;
    TextView lblEndTime;
    TextView lblTwoDot;
    TextView lblDurationTime;
    ImageView imgAlarm;
    ImageView imgBack;
    ImageView btnCheck;
    RelativeLayout liner;
    LinearLayout btnAlarm;
    LinearLayout linSetYearAndMonth;
    RecyclerView lstMain;
    CardView cardView;
    Spinner spinMinute, spinHour, spinDuration;
    EditText txtDuration;
    RecyclerView.LayoutManager mLayoutManager;
    static PersianCalendarView calPersian;

    List<DataTodo> lTodo = new ArrayList<>();
    public static List<Activities> lActTask = new ArrayList<>();
    private List<DataDateSpacing> lDate = new ArrayList<>();

    boolean isAlarm = false;
    String Date = "";
    String DateToSend = "";
    String TimeToSend = "";
    String DateToSend1 = "";
    String TimeToSend1 = "";
    String DateToSend2 = "";
    String TimeToSend2 = "";
    int MaxStoreCount = 0;
    public static boolean mType = false;//false One Task, true = Group Task
    private static int Typer = 1;
    boolean isDateSelected = false;
    private String cDateNeeded = "";
    public static boolean isForGroup = false;
    public static int GroupStartEndType = 0;
    public static int ActSelected = 0;
    public static String Descriptioned = "";
    public static String Nameioned = "";
    private boolean isError = false;
    private boolean isRun = true;

    int sysY = 0;
    int sysM = 0;
    int sysD = 0;

    public static fragAddTaskSetTime newInstance(Context mContext) {
        fragAddTaskSetTime fragment = new fragAddTaskSetTime();
        context = mContext;
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_task_set_time, container, false);

        lblTwoDot = view.findViewById(R.id.lblTwoDot);
        lblDurationTime = view.findViewById(R.id.lblDurationTime);
        liner = view.findViewById(R.id.liner);
//        lblSetTime = view.findViewById(R.id.lblSetTime);
        lblYear = view.findViewById(R.id.lblYear);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblMonth = view.findViewById(R.id.lblMonth);
//        btnSetTime = view.findViewById(R.id.btnSetTime);
        lblAlarm = view.findViewById(R.id.lblAlarm);
        lblTitle = view.findViewById(R.id.lblTitle);
        lblAccept = view.findViewById(R.id.lblAccept);
        imgAlarm = view.findViewById(R.id.imgAlarm);
        imgBack = view.findViewById(R.id.imgBack);
        btnAlarm = view.findViewById(R.id.btnAlarm);
        lstMain = view.findViewById(R.id.lstMain);
        cardView = view.findViewById(R.id.cardView);
        mLayoutManager = new LinearLayoutManager(context);
        calPersian = view.findViewById(R.id.calPersian);
        linSetYearAndMonth = view.findViewById(R.id.linSetYearAndMonth);
        spinMinute = view.findViewById(R.id.spinMinute);
        spinHour = view.findViewById(R.id.spinHour);
        spinDuration = view.findViewById(R.id.spinDuration);
        txtDuration = view.findViewById(R.id.txtDuration);

        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        txtDuration.setTransformationMethod(null);

//        if(!mType) {
//            lblAccept.setText("تنظیم زمان");
//            liner.setVisibility(View.VISIBLE);
//        }else{
//            liner.setVisibility(View.GONE);
//            lblAccept.setText("تایید تاریخ");
//        }
        lblTitle.setText("تنظیم زمان");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mType && isForGroup && GroupStartEndType == 0) {
                    fragAddTask.ActSelected = ActSelected;
                    fragAddTask.Descriptioned = Descriptioned;
                    fragAddTask.Nameioned = Nameioned;
                    act.getFragByState(FragmentState.AddTask);
                }else{
                    Intent intent = new Intent(context, actMain.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        Time now = new Time();
        now.setToNow();
        String Time = DC.getTime(now.hour, now.minute);
        TimeToSend = Time;
//        btnSetTime.setText(Time);

        List<CurrentHour> lHour = new ArrayList<>();
        List<CurrentMinute> lMinute = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            CurrentHour data = new CurrentHour();
            data.id = i;
            String namew = Integer.toString(i);
            if(namew.length() == 1)
                namew = "0" + namew;
            data.Name = namew;
            lHour.add(data);
        }
        for (int i = 0; i < 60; i++) {
            CurrentMinute data = new CurrentMinute();
            data.id = i;
            String namee = Integer.toString(i);
            if(namee.length() == 1)
                namee = "0" + namee;
            data.Name = namee;
            lMinute.add(data);
        }
        List<CurrentHour> lDur = new ArrayList<>();
        CurrentHour mData = new CurrentHour();
        mData.id = 1;
        mData.Name = "5";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 2;
        mData.Name = "10";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 3;
        mData.Name = "15";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 4;
        mData.Name = "20";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 5;
        mData.Name = "25";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 6;
        mData.Name = "30";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 7;
        mData.Name = "40";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 8;
        mData.Name = "50";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 9;
        mData.Name = "60";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 10;
        mData.Name = "90";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 11;
        mData.Name = "120";
        lDur.add(mData);
        mData = new CurrentHour();
        mData.id = 12;
        mData.Name = "سایر";
        lDur.add(mData);
        HourAdap = new SpinAdapter(context, lHour, "Name");
        MinAdap = new SpinAdapter(context, lMinute, "Name");
        DurAdap = new SpinAdapter(context, lDur, "Name");
        Time noww = new Time();
        noww.setToNow();

        spinHour.setAdapter(HourAdap);
        spinMinute.setAdapter(MinAdap);
        spinDuration.setAdapter(DurAdap);

        spinHour.setSelection(HourAdap.getItemPosition("Name", Integer.toString(noww.hour)));
        spinMinute.setSelection(MinAdap.getItemPosition("Name", Integer.toString(noww.minute)));
        spinDuration.setSelection(2);

        spinDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 11) {
                    spinDuration.setVisibility(View.GONE);
                    txtDuration.setVisibility(View.VISIBLE);
                    txtDuration.requestFocus();
                    txtDuration.setText("");
                    txtDuration.setSelection(0);
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        txtDuration.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (txtDuration.getText().toString().length() == 0) {
                    txtDuration.setVisibility(View.GONE);
                    spinDuration.setVisibility(View.VISIBLE);
                    spinDuration.setSelection(2);
                    spinDuration.performClick();
                }
                return false;
            }
        });

        PersianCalendarHandler calendarHandler = calPersian.getCalendar();
        calendarHandler.setTypeface(tFace);
        calendarHandler.setColorBackground(getResources().getColor(R.color.txtGray2));
//        calendarHandler.setMonthNames(DC.getMonthNames());
        calendarHandler.setColorDayName(getResources().getColor(R.color.txtGray4));
        calendarHandler.setColorHoliday(getResources().getColor(R.color.txtRed));
        calendarHandler.setColorNormalDay(getResources().getColor(R.color.txtGray4));
        calendarHandler.setColorNormalDaySelected(getResources().getColor(R.color.txtGray4));
        calendarHandler.setColorEventUnderline(getResources().getColor(R.color.txtWhite));
        calendarHandler.getToday();
        PersianDate today = calendarHandler.getToday();
        DateConverter DC2 = new DateConverter(today);
        DateToSend = DC2.getDate();
        Date = DC2.getStringLongDate();
        String mName = DC2.getMonthName(today.getMonth() - 1);
        final String Year = Integer.toString(today.getYear());
        lblYear.setText(Year);
        lblMonth.setText(mName);

        linSetYearAndMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog hDialog;
                hDialog = new Dialog(context);
                hDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                hDialog.setCancelable(true);
                hDialog.setCanceledOnTouchOutside(true);
                hDialog.setContentView(R.layout.dialog_addtask_settime_yearandmonth);
                Objects.requireNonNull(hDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                final Spinner spinYear = hDialog.findViewById(R.id.spinYear);
                final Spinner spinMonth = hDialog.findViewById(R.id.spinMonth);
                final Spinner spinDay = hDialog.findViewById(R.id.spinDay);
                TextView lblAccept = hDialog.findViewById(R.id.lblAccept);
                TextView lblCancell = hDialog.findViewById(R.id.lblCancell);

                List<CurrentYear> lYear = getYear();
                List<CurrentMonth> lMonth = getMonth();
                List<DataDates> lDay = Day();
                YearAdap = new SpinAdapter(context, lYear, "Name");
                MonthAdap = new SpinAdapter(context, lMonth, "Name");
                DayAdap = new SpinAdapter(context, lDay, "name");

                spinYear.setAdapter(YearAdap);
                spinMonth.setAdapter(MonthAdap);
                spinDay.setAdapter(DayAdap);

                String[] Datese = Setting.getServerDateTime().split("T");
                String[] mDate = Datese[0].split("-");
                int sY = Integer.parseInt(mDate[0]);
                int sM = Integer.parseInt(mDate[1]);
                int sD = Integer.parseInt(mDate[2]);

                saman.zamani.persiandate.PersianDate kPD = new saman.zamani.persiandate.PersianDate();
                kPD.setGrgYear(sY);
                kPD.setGrgMonth(sM);
                kPD.setGrgDay(sD);
                sY = kPD.getShYear();
                sM = kPD.getShMonth();
                sD = kPD.getShDay();

                spinYear.setSelection(sY == 1397 ? 0 : 1);
                spinMonth.setSelection(sM - 1);
                spinDay.setSelection(sD - 1);

                lblAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int y = Integer.parseInt(YearAdap.getItemString(spinYear.getSelectedItemPosition(), "Name"));
                        int m = Integer.parseInt(MonthAdap.getItemString(spinMonth.getSelectedItemPosition(), "id"));
                        int d = Integer.parseInt(DayAdap.getItemString(spinDay.getSelectedItemPosition(), "name"));
                        PersianDate pd = new PersianDate(y, m, d);
                        DateConverter DC2 = new DateConverter(pd);
                        DateToSend = DC2.getDate();
                        Date = DC2.getStringLongDate();
                        String mName = DC2.getMonthName(pd.getMonth() - 1);
                        String Year = Integer.toString(pd.getYear());
                        lblYear.setText(Year);
                        lblMonth.setText(mName);
                        hDialog.dismiss();
                        calPersian.goToDate(pd);
                    }
                });
                lblCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hDialog.dismiss();
                    }
                });
                hDialog.show();
            }
        });

        calPersian.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onChanged(PersianDate persianDate) {
                DateConverter DC2 = new DateConverter(persianDate);
                DateToSend = DC2.getDate();
                Date = DC2.getStringLongDate();
                String mName = DC2.getMonthName(persianDate.getMonth() - 1);
                String Year = Integer.toString(persianDate.getYear());
                lblYear.setText(Year);
                lblMonth.setText(mName);
            }
        });
        calPersian.setOnDayClickedListener(new OnDayClickedListener() {
            @Override
            public void onClick(PersianDate persianDate) {
                DateConverter DC2 = new DateConverter(persianDate);
                DateToSend = DC2.getDate();
                cDateNeeded = DC2.getCSharpOnlyDate(persianDate);
                Date = DC2.getStringLongDate();
                isDateSelected = true;
                String cStartDate = DC2.getCSharpDate(persianDate);

                pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                pDialog.Show(true);

                HashMap<String, Object> map = new HashMap<>();
                map.put("DateTime", cStartDate);

                Call cGetTaskByDate = rInterface.RQGetTasksByDate(Setting.getToken(), map);
                cGetTaskByDate.enqueue(new Callback<List<Activities>>() {
                    @Override
                    public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                        if (response.isSuccessful()) {
                            List<Activities> lact = response.body();
                            lTodo = new ArrayList<>();
                            for (Activities data : lact) {
                                DataTodo mData = new DataTodo();
                                mData.duration(data.DurationDate);
                                mData.id(data.ActivityID);
                                mData.sub_act_id(data.ActID);
                                mData.title(data.Title);
                                mData.todo_date(data.TodoDate);
                                mData.todo_time(data.TodoDate);

                                lTodo.add(mData);
                            }
                            RefreshMainList();
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        String Er = t.getMessage();
                        pDialog.DisMiss();
                    }
                });
            }
        });
//        btnSetTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTime();
//            }
//        });
//        lblSetTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTime();
//            }
//        });
        imgAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAlarm) {
                    Drawable myDrawable = getResources().getDrawable(R.drawable.alarm_);
                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().fitCenter().into(new BitmapImageViewTarget(imgAlarm) {
                    });
                    isAlarm = false;
                } else {
                    Drawable myDrawable = getResources().getDrawable(R.drawable.alarm_selected);
                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(context).load(stream.toByteArray()).asBitmap().fitCenter().into(new BitmapImageViewTarget(imgAlarm) {
                    });
                    isAlarm = true;
                }
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                if (!mType) {
                    String[] Dates = DateToSend.split("/");
                    String[] sysDateTime = Setting.getServerDateTime().split("T");
                    String[] sysDates = sysDateTime[0].split("-");
                    sysY = Integer.parseInt(sysDates[0]);
                    sysM = Integer.parseInt(sysDates[1]);
                    sysD = Integer.parseInt(sysDates[2]);
                    int sY = sysY;
                    int sM = sysM;
                    int sD = sysD;
                    saman.zamani.persiandate.PersianDate PD = new saman.zamani.persiandate.PersianDate();
                    PD.setGrgYear(sysY);
                    PD.setGrgMonth(sysM);
                    PD.setGrgDay(sysD);
                    sysY = PD.getShYear();
                    sysM = PD.getShMonth();
                    sysD = PD.getShDay();
                    int Y = Integer.parseInt(Dates[0]);
                    int M = Integer.parseInt(Dates[1]);
                    int D = Integer.parseInt(Dates[2]);
                    int H = Integer.parseInt(HourAdap.getItemString(spinHour.getSelectedItemPosition(), "Name"));
                    int MM = Integer.parseInt(MinAdap.getItemString(spinMinute.getSelectedItemPosition(), "Name"));
                    Time now = new Time();
                    now.setToNow();
                    int nowH = now.hour;
                    int nowM = now.minute;
                    if (sysY <= Y && sysM <= M) {
                        int Dur = 0;
                        if (spinDuration.getVisibility() == View.VISIBLE)
                            Dur = getDurSpin(spinDuration.getSelectedItemPosition());
                        else
                            Dur = Integer.parseInt(txtDuration.getText().toString());

                        Calendar kDate = Calendar.getInstance();
                        kDate.set(sY, sM, sD, nowH, nowM);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH.mm.ss");
                        Calendar startTime = kDate;
                        startTime.add(kDate.MINUTE, Dur);

                        String[] datese = df.format(startTime.getTime()).split(":");
                        String[] sdDate = datese[0].split("-");

                        int smY = 0;
                        int smM = 0;
                        int smD = 0;
                        try {
                            smY = Integer.parseInt(sdDate[0]);
                            smM = Integer.parseInt(sdDate[1]);
                            smD = Integer.parseInt(sdDate[2]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        saman.zamani.persiandate.PersianDate PDE = new saman.zamani.persiandate.PersianDate();
                        PDE.setGrgYear(smY);
                        PDE.setGrgMonth(smM);
                        PDE.setGrgDay(smD);
                        smY = PDE.getShYear();
                        smM = PDE.getShMonth() - 1;
                        smD = PDE.getShDay();

                        if (smY == sysY && smM == sysM) {
                            if(txtDuration.getText().toString() == null)
                                txtDuration.setText("15");
                            if(txtDuration.getText().toString().length() == 0)
                                txtDuration.setText("15");
                            if (Integer.parseInt(txtDuration.getText().toString()) <= 600) {
                                fragAddTask.Date = Date;
                                fragAddTask.DateToSend = DateToSend;
                                fragAddTask.TimeToSend = TimeToSend;
                                fragAddTask.isAlarmSet = isAlarm;
                                fragAddTask.ActSelected = ActSelected;
                                fragAddTask.Descriptioned = Descriptioned;
                                fragAddTask.Nameioned = Nameioned;
                                fragAddTask.duration = Integer.parseInt(txtDuration.getText().toString());
                                act.getFragByState(FragmentState.AddTask);
//                                dDialog.dismiss();
                            } else
                                Toast.makeText(context, "مدت زمان نباید بیشتر از 600 دقیقه باشد", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(context, "مدت زمان نباید از امروز بگذرد", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(context, "نمیتوان تاریخ و زمان را قبل از تاریخ و زمان امروز انتخاب کرد", Toast.LENGTH_LONG).show();
                } else {
                    if (isDateSelected) {
                        if (Typer == 0) {
                            DateToSend1 = DateToSend;
                            TimeToSend1 = TimeToSend;
                            Toast.makeText(context, "زمان شروع ثبت شد", Toast.LENGTH_LONG).show();
                            lblAccept.setText("تنظیم زمان پایان");
                            Typer = 1;
                        } else if (Typer == 1) {
                            mDialog = new Dialog(context);//haya injak injako
                            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            mDialog.setCancelable(true);
                            mDialog.setCanceledOnTouchOutside(true);
                            mDialog.setContentView(R.layout.dialog_space_time);
                            Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                            final EditText txtSpaceTime = mDialog.findViewById(R.id.txtSpaceTime);
                            lblError = mDialog.findViewById(R.id.lblError);
                            lblError2 = mDialog.findViewById(R.id.lblError2);
                            lblStartTime = mDialog.findViewById(R.id.lblStartTime);
                            lblEndTime = mDialog.findViewById(R.id.lblEndTime);
                            final TextView txtDescription = mDialog.findViewById(R.id.txtDescription);
                            TextView lblAct = mDialog.findViewById(R.id.lblAct);
                            TextView lblVisitorTour = mDialog.findViewById(R.id.lblVisitorTour);
                            TextView lblDetails = mDialog.findViewById(R.id.lblDetails);
                            TextView lblSpacing = mDialog.findViewById(R.id.lblSpacing);
                            TextView lblCancell = mDialog.findViewById(R.id.lblCancell);
                            TextView lblAccept = mDialog.findViewById(R.id.lblAccept);
                            final Spinner spinAct = mDialog.findViewById(R.id.spinAct);
                            final Spinner spinVisitorTour = mDialog.findViewById(R.id.spinVisitorTour);

                            txtSpaceTime.setTransformationMethod(null);

//                            Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                            lblStartTime.setTypeface(tFace);
//                            lblEndTime.setTypeface(tFace);
//                            txtDescription.setTypeface(tFace);
//                            lblAct.setTypeface(tFace);
//                            lblVisitorTour.setTypeface(tFace);
//                            lblDetails.setTypeface(tFace);
//                            lblSpacing.setTypeface(tFace);
//                            txtSpaceTime.setTypeface(tFace);
//                            lblError.setTypeface(tFace);
//                            lblError2.setTypeface(tFace);
//                            lblCancell.setTypeface(tFace);
//                            lblAccept.setTypeface(tFace);

                            List<Basic_ActResults> lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
                            List<Basic_Acts> lSubAct = new ArrayList<>();
                            for (Basic_ActResults data : lSubActResult) {
                                List<Basic_Acts> lFild = geter.getList(Basic_Acts.class, "WHERE ActID='" + data.ActID + "'");
                                for (Basic_Acts mData : lFild) {
                                    lSubAct.add(mData);
                                }
                            }
                            for (int i = 0; i < lSubAct.size(); i++) {
                                for (int j = i + 1; j < lSubAct.size(); j++) {
                                    if (lSubAct.get(i).ActID == lSubAct.get(j).ActID) {
                                        lSubAct.remove(j);
                                    }
                                }
                            }
                            Basic_Acts data = new Basic_Acts();
                            data.ActTitle = ("انتخاب نوع فعالیت");
                            lSubAct.add(0, data);
                            final SpinAdapter spinAdapter_SubAct = new SpinAdapter(context, lSubAct, "ActTitle");
                            spinAct.setAdapter(spinAdapter_SubAct);
                            List<MarketingVisitTours> lVisitorTour = geter.getList(MarketingVisitTours.class);
                            if (lVisitorTour.size() == 0) {
                                spinVisitorTour.setVisibility(View.GONE);
                                lblVisitorTour.setVisibility(View.GONE);
                            } else {
                                MarketingVisitTours dataa = new MarketingVisitTours();
                                dataa.VisitTourTitle = ("انتخاب دوره ویزیتور");
                                lVisitorTour.add(0, dataa);
                            }
                            final SpinAdapter spinAdapter_VisitorTour = new SpinAdapter(context, lVisitorTour, "VisitTourTitle");
                            spinVisitorTour.setAdapter(spinAdapter_VisitorTour);


                            lblStartTime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GroupStartEndType = 0;
                                    setTime();
//                                    final Dialog timeDialog = new Dialog(context);
//                                    timeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                    timeDialog.setCancelable(true);
//                                    timeDialog.setCanceledOnTouchOutside(true);
//                                    timeDialog.setContentView(R.layout.dialog_set_time_task);
//                                    Objects.requireNonNull(timeDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                                    final Spinner spinH = timeDialog.findViewById(R.id.spinHour);
//                                    final Spinner spinM = timeDialog.findViewById(R.id.spinMinute);
//                                    final Spinner lblCancell = timeDialog.findViewById(R.id.lblCancell);
//                                    final Spinner lblAccept = timeDialog.findViewById(R.id.lblAccept);
//
//                                    List<CurrentHour> lH = new ArrayList<>();
//                                    for(int i=0; i<23; i++){
//                                        CurrentHour data = new CurrentHour();
//                                        data.id = i;
//                                        String nameH = Integer.toString(i);
//                                        if(nameH.length() == 1)
//                                            nameH = "0" + nameH;
//                                        data.Name = nameH;
//
//                                        lH.add(data);
//                                    }
//                                    List<CurrentMinute> lM = new ArrayList<>();
//                                    for(int i=0; i<23; i++){
//                                        CurrentMinute data = new CurrentMinute();
//                                        data.id = i;
//                                        String nameM = Integer.toString(i);
//                                        if(nameM.length() == 1)
//                                            nameM = "0" + nameM;
//                                        data.Name = nameM;
//
//                                        lM.add(data);
//                                    }
//                                    final SpinAdapter HSpinAdap = new SpinAdapter(context, lH, "Name");
//                                    final SpinAdapter MSpinAdap = new SpinAdapter(context, lM, "Name");
//
//                                    spinH.setAdapter(HSpinAdap);
//                                    spinM.setAdapter(MSpinAdap);
//
//                                    Time now = new Time();
//                                    now.setToNow();
//
//                                    spinH.setSelection(HSpinAdap.getItemPosition("id", Integer.toString(now.hour)));
//                                    spinM.setSelection(MSpinAdap.getItemPosition("id", Integer.toString(now.hour)));
//
//                                    lblCancell.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            timeDialog.dismiss();
//                                        }
//                                    });
//                                    lblAccept.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            String h = HSpinAdap.getItemString(spinH.getSelectedItemPosition(), "Name");
//                                            String m = MSpinAdap.getItemString(spinM.getSelectedItemPosition(), "Name");
//                                            lblStartTime.setText(h + ":" + m);
//                                            String Time = DC.getTime(Integer.parseInt(h), Integer.parseInt(m));
//                                            TimeToSend1 = Time;
//                                            lblStartTime.setText(Time);
//                                            timeDialog.dismiss();
//                                        }
//                                    });
//                                    timeDialog.show();
                                }
                            });
                            lblEndTime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GroupStartEndType = 1;
                                    setTime();
//                                    final Dialog timeDialog = new Dialog(context);
//                                    timeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                    timeDialog.setCancelable(true);
//                                    timeDialog.setCanceledOnTouchOutside(true);
//                                    timeDialog.setContentView(R.layout.dialog_set_time_task);
//                                    Objects.requireNonNull(timeDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                                    final Spinner spinH = timeDialog.findViewById(R.id.spinHour);
//                                    final Spinner spinM = timeDialog.findViewById(R.id.spinMinute);
//                                    final Spinner lblCancell = timeDialog.findViewById(R.id.lblCancell);
//                                    final Spinner lblAccept = timeDialog.findViewById(R.id.lblAccept);
//
//                                    List<CurrentHour> lH = new ArrayList<>();
//                                    for(int i=0; i<23; i++){
//                                        CurrentHour data = new CurrentHour();
//                                        data.id = i;
//                                        String nameH = Integer.toString(i);
//                                        if(nameH.length() == 1)
//                                            nameH = "0" + nameH;
//                                        data.Name = nameH;
//
//                                        lH.add(data);
//                                    }
//                                    List<CurrentMinute> lM = new ArrayList<>();
//                                    for(int i=0; i<23; i++){
//                                        CurrentMinute data = new CurrentMinute();
//                                        data.id = i;
//                                        String nameM = Integer.toString(i);
//                                        if(nameM.length() == 1)
//                                            nameM = "0" + nameM;
//                                        data.Name = nameM;
//
//                                        lM.add(data);
//                                    }
//                                    final SpinAdapter HSpinAdap = new SpinAdapter(context, lH, "Name");
//                                    final SpinAdapter MSpinAdap = new SpinAdapter(context, lM, "Name");
//
//                                    spinH.setAdapter(HSpinAdap);
//                                    spinM.setAdapter(MSpinAdap);
//
//                                    Time now = new Time();
//                                    now.setToNow();
//
//                                    spinH.setSelection(HSpinAdap.getItemPosition("id", Integer.toString(now.hour)));
//                                    spinM.setSelection(MSpinAdap.getItemPosition("id", Integer.toString(now.hour)));
//
//                                    lblCancell.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            timeDialog.dismiss();
//                                        }
//                                    });
//                                    lblAccept.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            String h = HSpinAdap.getItemString(spinH.getSelectedItemPosition(), "Name");
//                                            String m = MSpinAdap.getItemString(spinM.getSelectedItemPosition(), "Name");
//                                            lblStartTime.setText(h + ":" + m);
//                                            String Time = DC.getTime(Integer.parseInt(h), Integer.parseInt(m));
//                                            TimeToSend2 = Time;
//                                            lblEndTime.setText(Time);
//                                            timeDialog.dismiss();
//                                        }
//                                    });
//                                    timeDialog.show();
                                }
                            });
                            txtSpaceTime.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (TimeToSend1.length() > 0 && TimeToSend2.length() > 0) {
                                        try {
                                            mThread = new Thread(new Runnable() {
                                                public void run() {
                                                    while (isRun) {
                                                        int Spacing = 0;
                                                        try {
                                                            Spacing = Integer.parseInt(txtSpaceTime.getText().toString());
                                                        } catch (Exception ignored) {
                                                            Spacing = -1;
                                                        }
                                                        if (Spacing == -1) {
                                                            String mText = "<font color='#CB3542'>لطفا زمان حضور را وارد کنید.</font>";
                                                            setTexter2(mText);
                                                        } else {
                                                            if (Spacing >= 5 && Spacing <= 60) {
                                                                DateConverter mDC = new DateConverter();
                                                                java.util.Date min = mDC.getMinTime(TimeToSend1);
                                                                Date max = mDC.getMaxTime(TimeToSend2);

                                                                Date minSpacing = mDC.SumTimes(min, Spacing);
                                                                if ((min.before(max) || mDC.KuchiktarMosavi(min, max)) && (mDC.KuchiktarMosavi(minSpacing, max))) {
                                                                    try {
                                                                        lDate.clear();
                                                                    } catch (Exception ignored) {
                                                                    }
                                                                    while ((mDC.KuchiktarMosavi(minSpacing, max))) {
                                                                        DataDateSpacing data = new DataDateSpacing();
                                                                        data.cTime(min);
                                                                        data.eTime(minSpacing);
                                                                        data.duration(Spacing);
                                                                        data.mDate(cDateNeeded);
                                                                        lDate.add(data);

                                                                        min = minSpacing;
                                                                        minSpacing = mDC.SumTimes(min, Spacing);
                                                                    }
                                                                    if (lTodo.size() > 0) {
                                                                        boolean Interference = false;
                                                                        for (DataTodo data : lTodo) {
                                                                            for (DataDateSpacing mData : lDate) {
                                                                                Date A, B, Time1, Time2;
                                                                                A = mData.cTime();
                                                                                B = mData.eTime();
                                                                                Time1 = mDC.getMaxTime(data.todo_time());
                                                                                String[] durs = data.duration().split("T");
                                                                                String[] dur = durs[1].split(":");
                                                                                Time2 = mDC.SumTimes(Time1, Integer.parseInt(dur[1]));

                                                                                if (Time1.after(A) && Time2.before(B)) {
                                                                                    Interference = true;
                                                                                    break;
                                                                                }
                                                                                if (Time1.before(A) && Time2.after(A) && Time2.before(B)) {
                                                                                    Interference = true;
                                                                                    break;
                                                                                }
                                                                                if (Time1.after(A) && Time1.before(B) && Time1.after(B)) {
                                                                                    Interference = true;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }
                                                                        if (Interference) {
                                                                            String Count = Integer.toString(lDate.size());
                                                                            String mText2 = "<font color='#2E6F7A'>میتوانید " + Count + " فروشگاه را در لیست قرارید.</font>";
                                                                            setTexter(mText2);
                                                                            MaxStoreCount = lDate.size();

                                                                            String mText = "<font color='#CB3542'>در زمان های انتخاب شده تداخل زمانی وجود دارد." + "<br>ادامه میدهید و بعدا ویرایش میکنید ؟</font>";
                                                                            setTexter2(mText);
                                                                            MaxStoreCount = lDate.size();
                                                                            isError = false;
                                                                        } else {
                                                                            String Count = Integer.toString(lDate.size());
                                                                            String mText = "<font color='#2E6F7A'>میتوانید " + Count + " فروشگاه را در لیست قرارید.</font>";
                                                                            setTexter(mText);
                                                                            MaxStoreCount = lDate.size();
                                                                            isError = false;

                                                                            setTexter2("");
                                                                        }
                                                                    } else {
                                                                        String Count = Integer.toString(lDate.size());
                                                                        String mText = "<font color='#2E6F7A'>میتوانید " + Count + " فروشگاه را در لیست قرارید.</font>";
                                                                        setTexter(mText);
                                                                        MaxStoreCount = lDate.size();
                                                                        isError = false;

                                                                        setTexter2("");
                                                                    }
                                                                } else {
                                                                    String mText = "<font color='#CB3542'>زمان شروع نباید بیشتر از زمان پایان باشد." + "<br>زمان وقفه به علاوه زمان شروع نباید بیشتر از زمان پایان باشد.</font>";
                                                                    setTexter2(mText);
                                                                    isError = true;

                                                                    setTexter("");
                                                                }
                                                            } else {
                                                                String mText = "<font color='#CB3542'>فاصله زمانی نباید کمتر از 5 دقیقه باشد. نباید بیشتر از 60 دقیقه باشد.</font>";
                                                                setTexter2(mText);
                                                                isError = true;

                                                                setTexter("");
                                                            }
                                                        }
                                                        isRun = false;
                                                    }
                                                }
                                            });
                                            mThread.start();
                                        } catch (Exception Ex) {
                                            String Er = Ex.getMessage();
                                        }
                                    } else {
                                        String mText = "<font color='#CB3542'>زمان شروع یا پایان ثبت نشده</font>";
                                        setTexter2(mText);
                                        isError = true;
                                    }
                                    isRun = true;
                                }
                            });

                            lblAccept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!isError) {
                                        if (spinAct.getSelectedItemPosition() > 0) {
                                            DataGroupTask dTask = new DataGroupTask();
                                            dTask.lSpacing(lDate);
                                            dTask.date(Date);
                                            dTask.dateToSend1(DateToSend1);
                                            dTask.timeToSend1(TimeToSend1);
                                            dTask.dateToSend2(DateToSend2);
                                            dTask.timeToSend2(TimeToSend2);
                                            dTask.maxStoreCount(MaxStoreCount);
                                            dTask.description(txtDescription.getText().toString());
                                            dTask.alarmSet(isAlarm);
                                            String actString = spinAdapter_SubAct.getItemString(spinAct.getSelectedItemPosition(), "ActID");//injakol
                                            int actID = Integer.parseInt(actString);
                                            dTask.subActId(actID);
                                            try {
                                                dTask.visitorTourId(Integer.parseInt(spinAdapter_VisitorTour.getItemString(spinVisitorTour.getSelectedItemPosition(), "VisitTourID")));
                                                String aaa = "ASD";
                                            } catch (Exception Ex) {
                                                String Er = Ex.getMessage();
                                            }

                                            fragAddTasks.dTask = dTask;
                                            fragAddTasks.MaxCount = MaxStoreCount;
                                            act.getFragByState(FragmentState.AddTasks);
                                            mDialog.dismiss();
                                            Typer = 0;
                                        } else
                                            Toast.makeText(context, "نوع فعالیت را انتخاب کنید.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            lblCancell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                }
                            });
                            mDialog.show();
                        }
                    } else
                        Toast.makeText(context, "لطفا یک روز رو در تاریخ انتخاب کنید", Toast.LENGTH_LONG).show();
                }
            }
        });




        String[] sysDate = Setting.getServerDateTime().split("T");
        String[] Dates = sysDate[0].split("-");
        final PersianDate pd = new PersianDate(Integer.parseInt(Dates[0]), Integer.parseInt(Dates[1]), Integer.parseInt(Dates[2]));
//        DateConverter DC22 = new DateConverter(pd);
//        DateToSend = DC22.getDate();
//        cDateNeeded = DC22.getCSharpOnlyDate(pd);
//        Date = DC22.getStringLongDate();
//        isDateSelected = true;
//        String cStartDate = DC22.getCSharpDate(pd);

        pDialog = new com.behincom.behincome.Accesories.Dialog(context);
        pDialog.Show(true);

        HashMap<String, Object> map = new HashMap<>();
        map.put("DateTime", Setting.getServerDateTime());
        rInterface = Retrofite.getClient().create(RWInterface.class);
        Call cGetTaskByDate = rInterface.RQGetTasksByDate(Setting.getToken(), map);
        cGetTaskByDate.enqueue(new Callback<List<Activities>>() {
            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                if (response.isSuccessful()) {
                    List<Activities> lact = response.body();
                    lTodo = new ArrayList<>();
                    for (Activities data : lact) {
                        DataTodo mData = new DataTodo();
                        mData.duration(data.DurationDate);
                        mData.id(data.ActivityID);
                        mData.sub_act_id(data.ActID);
                        mData.title(data.Title);
                        mData.todo_date(data.TodoDate);
                        mData.todo_time(data.TodoDate);

                        lTodo.add(mData);
                    }
                    RefreshMainList();


                    String[] sysDate = Setting.getServerDateTime().split("T");
                    String[] Dates = sysDate[0].split("-");
                    saman.zamani.persiandate.PersianDate kPD = new saman.zamani.persiandate.PersianDate();
                    kPD.setGrgYear(Integer.parseInt(Dates[0]));
                    kPD.setGrgMonth(Integer.parseInt(Dates[1]));
                    kPD.setGrgDay(Integer.parseInt(Dates[2]));
                    final PersianDate pd = new PersianDate(kPD.getShYear(), kPD.getShMonth(), kPD.getShDay());
                    calPersian.goToDate(pd);
                }
                pDialog.DisMiss();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String Er = t.getMessage();
                pDialog.DisMiss();
            }
        });

        return view;
    }
    private List<CurrentYear> getYear() {
        String mDate = Setting.getServerDateTime();
        String[] dates = mDate.split("T");
        String[] datse = dates[0].split("-");
        PersianDate mpd = new PersianDate(Integer.parseInt(datse[0]), Integer.parseInt(datse[1]), Integer.parseInt(datse[2]));
        saman.zamani.persiandate.PersianDate pd = new saman.zamani.persiandate.PersianDate();
        pd.setGrgYear(Integer.parseInt(datse[0]));
        pd.setGrgMonth(Integer.parseInt(datse[1]));
        pd.setGrgDay(Integer.parseInt(datse[2]));

        List<CurrentYear> yer = new ArrayList<>();
        CurrentYear data = new CurrentYear();
        data.id = 1;
        data.Name = Integer.toString(pd.getShYear());
        yer.add(data);
        data = new CurrentYear();
        data.id = 2;
        data.Name = Integer.toString(pd.getShYear() + 1);
        yer.add(data);

        return yer;
    }
    private List<CurrentMonth> getMonth() {
        List<CurrentMonth> mon = new ArrayList<>();

        CurrentMonth data = new CurrentMonth();
        data.id = 1;
        data.Name = "فروردین";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 2;
        data.Name = "اردیبهشت";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 3;
        data.Name = "خرداد";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 4;
        data.Name = "تیر";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 5;
        data.Name = "مرداد";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 6;
        data.Name = "شهریور";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 7;
        data.Name = "مهر";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 8;
        data.Name = "آبان";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 9;
        data.Name = "آذر";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 10;
        data.Name = "دی";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 11;
        data.Name = "بهمن";
        mon.add(data);
        data = new CurrentMonth();
        data.id = 12;
        data.Name = "اسفند";
        mon.add(data);

        return mon;
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
    private void setTime() {
        Time now = new Time();
        now.setToNow();
        TimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                if (!isForGroup) {
                    String Time = DC.getTime(hourOfDay, minute);
                    TimeToSend = Time;
//                    btnSetTime.setText(Time);
                } else {
                    if (GroupStartEndType == 0) {
                        String Time = DC.getTime(hourOfDay, minute);
                        TimeToSend1 = Time;
                        lblStartTime.setText(Time);
                    } else {
                        String Time = DC.getTime(hourOfDay, minute);
                        TimeToSend2 = Time;
                        lblEndTime.setText(Time);
                    }
                    if (TimeToSend1.length() > 0 && TimeToSend2.length() > 0) {
                        DateConverter mDC = new DateConverter();
                        Date min = mDC.getMinTime(TimeToSend1);
                        Date max = mDC.getMaxTime(TimeToSend2);
                        if (!mDC.KuchiktarMosavi(min, max)) {
                            String mText = "<font color='#CB3542'>زمان شروع نباید بیشتر از زمان پایان باشد." + "<br>زمان وقفه به علاوه زمان شروع نباید بیشتر از زمان پایان باشد.</font>";
                            setTexter2(mText);

                            setTexter("");
                            isError = true;
                        } else {
                            String mText = "";
                            setTexter2(mText);
                            isError = true;
                        }
                    }
                }
            }
        }, now.hour, now.minute, false);
        if (!isForGroup) {
            TimePicker.setTitle("زمان را مشخص کنید");
        } else {
            if (GroupStartEndType == 0) {
                TimePicker.setTitle("تنظیم زمان شروع");
            } else {
                TimePicker.setTitle("تنظیم زمان پایان");
            }
        }
        TimePicker.show(getActivity().getFragmentManager(), "Time");
    }
    private void RefreshMainList() {
        adapTask adapter = new adapTask(lTodo, context);
        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        rInterface = Retrofite.getClient().create(RWInterface.class);
        Typer = 1;
        if(mType && isForGroup && GroupStartEndType == 0){
            liner.setVisibility(View.GONE);
        }
    }
    private void setTexter(final String Text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblError.setText(Html.fromHtml(Text));
            }
        });
    }
    private void setTexter2(final String Text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblError2.setText(Html.fromHtml(Text));
            }
        });
    }
    private int getDurSpin(int position){
        switch (position){
            case 0:
                return 5;
            case 1:
                return 10;
            case 2:
                return 15;
            case 3:
                return 20;
            case 4:
                return 25;
            case 5:
                return 30;
            case 6:
                return 40;
            case 7:
                return 50;
            case 8:
                return 60;
            case 9:
                return 90;
            case 10:
                return 120;
        }
        return 120;
    }
    private void Finisher(){
        lTodo = new ArrayList<>();
        lActTask = new ArrayList<>();
        lDate = new ArrayList<>();

        isAlarm = false;
        Date = "";
        DateToSend = "";
        TimeToSend = "";
        DateToSend1 = "";
        TimeToSend1 = "";
        DateToSend2 = "";
        TimeToSend2 = "";
        MaxStoreCount = 0;
        mType = false;//false One Task, true = Group Task
        Typer = 1;
        isDateSelected = false;
        cDateNeeded = "";
        isForGroup = false;
        GroupStartEndType = 0;
        ActSelected = 0;
        Descriptioned = "";
        isError = false;
        isRun = true;
        sysY = 0;
        sysM = 0;
        sysD = 0;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }
}
