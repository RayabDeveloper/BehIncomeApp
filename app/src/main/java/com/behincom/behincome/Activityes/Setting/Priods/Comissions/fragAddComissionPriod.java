package com.behincom.behincome.Activityes.Setting.Priods.Comissions;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.Priods.Visitors.fragVisitorPriod;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.Marketing.MarketingCommissionPeriods;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragAddComissionPriod extends Fragment {

    static Context context;
    RWInterface rInterface;
    actSetting act = new actSetting();
    private RSQLite SQL = new RSQLite();
    private Dialog pDialog;
    private PersianCalendar PC = new PersianCalendar();
    private DatePickerDialog DateDialog;

    private TextView txtToDate;
    private TextView txtFromDate;
    private EditText txtName;
    private EditText txtDescription;

    private boolean isFromDate = false;
    private boolean isToDate = false;
    private int DateType = 0;
    private String cFromDate = "";
    private String cToDate = "";

    public static boolean toEdite = false;
    public static int position = 0;
    public static String LastDate = Setting.getServerDateTime();
    public static boolean isFristDate = false;
    public static MarketingCommissionPeriods mData = new MarketingCommissionPeriods();

    public static fragAddComissionPriod newInstance(Context mContext) {
        fragAddComissionPriod fragment = new fragAddComissionPriod();
        context = mContext;
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        txtName.setText("");
        txtFromDate.setText("");
        txtToDate.setText("");
        txtDescription.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_comission_priod, container, false);

        TextView lblTitle = view.findViewById(R.id.lblTitle);
        TextView lblAccept = view.findViewById(R.id.lblAccept);
        TextView lblName = view.findViewById(R.id.lblName);
        TextView lblFromDate = view.findViewById(R.id.lblFromDate);
        TextView lblDescription = view.findViewById(R.id.lblDescription);
        TextView lblToDate = view.findViewById(R.id.lblToDate);
        final TextView lblFromLongDate = view.findViewById(R.id.lblFromLongDate);
        final TextView lblToLongDate = view.findViewById(R.id.lblToLongDate);
        txtToDate = view.findViewById(R.id.txtToDate);
        txtFromDate = view.findViewById(R.id.txtFromDate);
        txtName = view.findViewById(R.id.txtName);
        txtDescription = view.findViewById(R.id.txtDescription);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        ImageView btnCheck = view.findViewById(R.id.btnCheck);
        ImageView btnDelete = view.findViewById(R.id.btnDelete);
        CardView cardView = view.findViewById(R.id.cardView);

//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblTitle.setTypeface(tFace);
//        lblName.setTypeface(tFace);
//        lblFromDate.setTypeface(tFace);
//        lblToDate.setTypeface(tFace);
//        lblAccept.setTypeface(tFace);
//        txtToDate.setTypeface(tFace);
//        txtFromDate.setTypeface(tFace);
//        txtName.setTypeface(tFace);
//        txtDescription.setTypeface(tFace);
//        lblDescription.setTypeface(tFace);

        lblTitle.setText("دوره کمیسیون");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.CommissionPeriods);
                txtName.setText("");
                txtFromDate.setText("");
                txtToDate.setText("");
                txtDescription.setText("");
            }
        });

        final DateConverter DCDC = new DateConverter(LastDate, true);

        if (toEdite) {
            btnDelete.setVisibility(View.VISIBLE);
            txtName.setText(mData.MarketingCommissionPeriodTitle);
            txtDescription.setText(mData.MarketingCommissionPeriodDescription);
            DateConverter DC = new DateConverter(mData.MarketingCommissionPeriodDateFrom, true);
            String FromD = DC.getOnlyDate();
            DC = new DateConverter(mData.MarketingCommissionPeriodDateTo, true);
            String FromT = DC.getOnlyDate();
            cFromDate = mData.MarketingCommissionPeriodDateFrom;
            cToDate = mData.MarketingCommissionPeriodDateTo;

            com.behincom.behincome.Accesories.PersianCalendar pc = new com.behincom.behincome.Accesories.PersianCalendar();
            String[] pDate = FromD.split("/");
            pc.setPersainCalendarWithJalali(Integer.parseInt(pDate[0]), Integer.parseInt(pDate[1]), Integer.parseInt(pDate[2]));
            String FromDateLong = DC.getStringLongDate(pc.getDayOfWeek(), pc.getDay(), pc.getMonth(), pc.getYear());
            String[] pDate2 = FromT.split("/");
            pc.setPersainCalendarWithJalali(Integer.parseInt(pDate2[0]), Integer.parseInt(pDate2[1]), Integer.parseInt(pDate2[2]));
            String ToDateLong = DC.getStringLongDate(pc.getDayOfWeek(), pc.getDay(), pc.getMonth(), pc.getYear());

            txtFromDate.setText(FromD);
            txtToDate.setText(FromT);
            lblFromLongDate.setText(FromDateLong);
            lblToLongDate.setText(ToDateLong);
        }else{
            btnDelete.setVisibility(View.GONE);
            txtName.setText("");
            txtDescription.setText("");
            txtFromDate.setText("");
            txtToDate.setText("");

            isFromDate = false;
            isToDate = false;
            DateType = 0;
            cFromDate = "";
            cToDate = "";

            toEdite = false;
            position = 0;
            LastDate = "";
            isFristDate = false;
            MarketingCommissionPeriods mData = new MarketingCommissionPeriods();
        }

        DateDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                ir.mirrajabi.persiancalendar.core.models.PersianDate tPD = new ir.mirrajabi.persiancalendar.core.models.PersianDate(year, monthOfYear + 1, dayOfMonth);
                String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
                DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                if (DateType == 0) {
                    cFromDate = DC.getCSharp();
                    txtFromDate.setText(mDate);

                    String longDate = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());
                    lblFromLongDate.setText(longDate);

                    isFromDate = false;
                } else {
                    cToDate = DC.getCSharp();
                    txtToDate.setText(mDate);

                    String longDate = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());
                    lblToLongDate.setText(longDate);

                    isToDate = false;
                }
            }
        }, PC.getPersianYear(), PC.getPersianMonth(), PC.getPersianDay());
        DateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (DateType == 0) isFromDate = false;
                else isToDate = false;
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.CommissionPeriods);
            }
        });
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFromDate) {
                    isFromDate = true;
                    DateDialog.show(getActivity().getFragmentManager(), "FromDate");
                    DateType = 0;
                }
            }
        });
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isToDate) {
                    isToDate = true;
                    DateDialog.show(getActivity().getFragmentManager(), "ToDate");
                    DateType = 1;
                }
            }
        });
        txtName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    txtFromDate.requestFocus();
                    return true;
                }
                return false;
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateConverter DC = new DateConverter();
                if(DCDC.CompareDates(cFromDate) != 2) {
                    if (DC.CompareDates(cFromDate, cToDate) == 1) {
                        if (txtName.getText().toString().length() > 0 && txtFromDate.getText().toString().length() > 0 && txtToDate.getText().toString().length() > 0) {
                            if (!toEdite) {
                                String Q = "";
                                String Name = txtName.getText().toString();
                                String FromDate = cFromDate;
                                String ToDate = cToDate;
                                String Description = txtDescription.getText().toString();
                                MarketingCommissionPeriods data = new MarketingCommissionPeriods();
                                data.UserID = (Integer.parseInt(fragComissionPriod.AccountId));
                                data.MarketingCommissionPeriodTitle = (Name);
                                data.MarketingCommissionPeriodDateFrom = (FromDate);
                                data.MarketingCommissionPeriodDateTo = (ToDate);
                                data.MarketingCommissionPeriodDescription = (Description);
                                fragComissionPriod.lCommissionPriod.add(data);

                                sendRequest();
                            } else {
                                String Q = "";
                                String Name = txtName.getText().toString();
                                String FromDate = cFromDate;
                                String ToDate = cToDate;
                                String Description = txtDescription.getText().toString();
                                fragComissionPriod.lCommissionPriod.get(position).UserID = (Integer.parseInt(fragComissionPriod.AccountId));
                                fragComissionPriod.lCommissionPriod.get(position).MarketingCommissionPeriodTitle = (Name);
                                fragComissionPriod.lCommissionPriod.get(position).MarketingCommissionPeriodDateFrom = (FromDate);
                                fragComissionPriod.lCommissionPriod.get(position).MarketingCommissionPeriodDateTo = (ToDate);
                                fragComissionPriod.lCommissionPriod.get(position).MarketingCommissionPeriodDescription = (Description);

                                sendRequest();
                            }
                        } else Toast.makeText(context, "نام و تاریخ نباید خالی باشد", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(context, "تاریخ شروع نباید بعد از تاریخ پایان ، یا یکی باشد.", Toast.LENGTH_LONG).show();
                }else{
                    if(isFristDate)
                        Toast.makeText(context, "تاریخ شروع نباید از تاریخ امروز بیشتر باشد", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "تاریخ شروع نباید از آخرین تاریخ دوره کمیسیون بیشتر باشد", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rInterface = Retrofite.getClient().create(RWInterface.class);

                pDialog = new Dialog(context);
                pDialog.Show();

                Map<String, Object> map = new HashMap<>();
                map.put("CommissionPeriodID", mData.MarketingCommissionPeriodID);
                map.put("BmmID", mData.UserID);

                Call<SimpleResponse> cDelete = rInterface.RQDeleteCommissionPriod(Setting.getToken(), new HashMap<>(map));
                cDelete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, final Response<SimpleResponse> response) {
                        if(response.isSuccessful()){
                            try {
                                pDialog.DisMiss();
                                if(Boolean.parseBoolean(response.body().AdditionalData.get("CanDelete").toString()) == false){
                                    Toast.makeText(getActivity(), response.body().Errors.get("inUse").toString(), Toast.LENGTH_LONG).show();
                                }else if(Boolean.parseBoolean(response.body().AdditionalData.get("CanDelete").toString()) == true){
                                    final android.app.Dialog mDialog;
                                    mDialog = new android.app.Dialog(context);
                                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    mDialog.setCancelable(true);
                                    mDialog.setCanceledOnTouchOutside(true);
                                    mDialog.setContentView(R.layout.dialog_accept_cancell);
                                    Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                                    TextView lblTitle = mDialog.findViewById(R.id.lblTitle);
                                    TextView lblAccept = mDialog.findViewById(R.id.lblAccept);
                                    TextView lblCancell = mDialog.findViewById(R.id.lblCancell);

                                    lblTitle.setText("آیا مایل به حذف دوره ویزیتور هستید ؟");

                                    lblCancell.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mDialog.dismiss();
                                        }
                                    });
                                    lblAccept.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pDialog = new Dialog(context);
                                            pDialog.Show();

                                            Map<String, Object> map = new HashMap<>();
                                            map.put("CommissionPeriodID", mData.MarketingCommissionPeriodID);
                                            map.put("BmmID", mData.UserID);

                                            Call<SimpleResponse> cDeleted = rInterface.RQDeletedCommissionPriod(Setting.getToken(), new HashMap<>(map));
                                            cDeleted.enqueue(new Callback<SimpleResponse>() {
                                                @Override
                                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                                    pDialog.DisMiss();
                                                    if(response.isSuccessful()){
                                                        SQL.Execute("DELETE FROM MarketingCommissionPeriods WHERE MarketingCommissionPeriodID='" + response.body().AdditionalData.get("DeletedID").toString().replace(".0", "") + "'");

                                                        fragVisitorPriod.lPriod.remove(mData);
                                                        act.getFragByState(FragmentState.VisitTours);
                                                        mDialog.dismiss();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                                    pDialog.DisMiss();
                                                }
                                            });
                                        }
                                    });
                                    mDialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
                        pDialog.DisMiss();
                    }
                });
            }
        });

        return view;
    }
    private void sendRequest() {
        rInterface = Retrofite.getClient().create(RWInterface.class);

        pDialog = new Dialog(context);
        pDialog.Show();

        String[] shDate = txtFromDate.getText().toString().split("/");
        DateConverter DC = new DateConverter(Integer.parseInt(shDate[0]), Integer.parseInt(shDate[1]), Integer.parseInt(shDate[2]));
        final String cFromDate = DC.getCSharp();
        String[] shDateTo = txtToDate.getText().toString().split("/");
        DC = new DateConverter(Integer.parseInt(shDateTo[0]), Integer.parseInt(shDateTo[1]), Integer.parseInt(shDateTo[2]));
        final String cToDate = DC.getCSharp();

        Map<String, Object> mCommission = new HashMap<>();
        mCommission.put("MarketingCommissionPeriodTitle", txtName.getText().toString());
        mCommission.put("MarketingCommissionPeriodDescription", txtDescription.getText().toString());
        mCommission.put("MarketingCommissionPeriodDateFrom", cFromDate);
        mCommission.put("MarketingCommissionPeriodDateTo", cToDate);

        Call<SimpleResponse> cCommission = rInterface.RQAddMarketingCommissionPeriod(Setting.getToken(), new HashMap<>(mCommission));
        cCommission.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if(response.isSuccessful()){
                    SimpleResponse simple = response.body();
                    if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                        MarketingCommissionPeriods data = new MarketingCommissionPeriods();
                        data.MarketingCommissionPeriodID = Integer.parseInt(response.body().AdditionalData.get("MarketingCommissionPeriodID").toString().replace(".0", ""));
                        data.MarketingCommissionPeriodTitle = txtName.getText().toString();
                        data.MarketingCommissionPeriodDescription = txtName.getText().toString();
                        data.MarketingCommissionPeriodDateFrom = cFromDate;
                        data.MarketingCommissionPeriodDateTo = cToDate;

                        SQL.Insert(data);

                        act.getFragByState(FragmentState.CommissionPeriods);
                    }else{
                        String eror = "خطا";
                        try {
                            eror = simple.Errors.get("NotJoinedDate").toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, eror, Toast.LENGTH_LONG).show();
                    }
                }
                pDialog.DisMiss();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                pDialog.DisMiss();
            }
        });
    }

}
