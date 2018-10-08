package com.behincom.behincome.Activityes.Setting.Priods.Visitors;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.Marketing.MarketingVisitTours;
import com.behincom.behincome.Datas.RSQLGeter;
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

public class fragAddVisitorPriod extends Fragment {

    static Context context;
    RWInterface rInterface;
    actSetting act = new actSetting();
    private RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
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
    private static boolean canEdit = false;
    public static int position = 0;
    public static MarketingVisitTours mData = new MarketingVisitTours();

    public static fragAddVisitorPriod newInstance(Context mContext) {
        fragAddVisitorPriod fragment = new fragAddVisitorPriod();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_visitor_priod, container, false);

        TextView lblTitle = view.findViewById(R.id.lblTitle);
        TextView lblName = view.findViewById(R.id.lblName);
        final TextView lblFromDate = view.findViewById(R.id.lblFromDate);
        TextView lblToDate = view.findViewById(R.id.lblToDate);
        TextView lblDescription = view.findViewById(R.id.lblDescription);
        final TextView lblFromDateLongDate = view.findViewById(R.id.lblFromDateLongDate);
        final TextView lblToDateLongDate = view.findViewById(R.id.lblToDateLongDate);
        txtToDate = view.findViewById(R.id.txtToDate);
        txtFromDate = view.findViewById(R.id.txtFromDate);
        txtName = view.findViewById(R.id.txtName);
        txtDescription = view.findViewById(R.id.txtDescription);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        ImageView btnCheck = view.findViewById(R.id.btnCheck);
        ImageView btnDelete = view.findViewById(R.id.btnDelete);

//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblTitle.setTypeface(tFace);
//        lblTitle.setTypeface(tFace);
//        lblName.setTypeface(tFace);
//        lblFromDate.setTypeface(tFace);
//        lblToDate.setTypeface(tFace);
//        txtToDate.setTypeface(tFace);
//        txtFromDate.setTypeface(tFace);
//        txtName.setTypeface(tFace);
//        txtDescription.setTypeface(tFace);
//        lblDescription.setTypeface(tFace);

        lblTitle.setText("دوره ویزیتور");
        imgBack.setVisibility(View.VISIBLE);

        if (toEdite) {
            btnDelete.setVisibility(View.VISIBLE);
            txtName.setText(mData.VisitTourTitle);
            txtDescription.setText(mData.VisitTourDescription);
            DateConverter DC = new DateConverter(mData.DateFrom, true);
            String FromD = DC.getOnlyDate();
//            String FromLongDate = DC.getStringLongDate();
            DC = new DateConverter(mData.DateTo, true);
            String FromT = DC.getOnlyDate();
            cFromDate = mData.DateFrom;
            cToDate = mData.DateTo;

            com.behincom.behincome.Accesories.PersianCalendar pc = new com.behincom.behincome.Accesories.PersianCalendar();
            String[] pDate = FromD.split("/");
            pc.setPersainCalendarWithJalali(Integer.parseInt(pDate[0]), Integer.parseInt(pDate[1]), Integer.parseInt(pDate[2]));
            String FromDateLong = DC.getStringLongDate(pc.getDayOfWeek(), pc.getDay(), pc.getMonth(), pc.getYear());
            String[] pDate2 = FromT.split("/");
            pc.setPersainCalendarWithJalali(Integer.parseInt(pDate2[0]), Integer.parseInt(pDate2[1]), Integer.parseInt(pDate2[2]));
            String ToDateLong = DC.getStringLongDate(pc.getDayOfWeek(), pc.getDay(), pc.getMonth(), pc.getYear());

            txtFromDate.setText(FromD);
            txtToDate.setText(FromT);
            lblFromDateLongDate.setText(FromDateLong);
            lblToDateLongDate.setText(ToDateLong);
        }else{
            btnDelete.setVisibility(View.GONE);
            cFromDate = "";
            cToDate = "";
            txtFromDate.setText("");
            txtToDate.setText("");

            isFromDate = false;
            isToDate = false;
            DateType = 0;
            cFromDate = "";
            cToDate = "";

            toEdite = false;
            position = 0;
            mData = new MarketingVisitTours();
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.VisitTours);
                txtName.setText("");
                txtFromDate.setText("");
                txtToDate.setText("");
                txtDescription.setText("");
            }
        });
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
                    lblFromDateLongDate.setText(longDate);
                    isFromDate = false;
                } else {
                    cToDate = DC.getCSharp();
                    txtToDate.setText(mDate);
                    String longDate = DC.getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear());
                    lblToDateLongDate.setText(longDate);
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
                if (DC.CompareDates(cFromDate, cToDate) == 1) {
                    if (txtName.getText().toString().length() > 0 && txtFromDate.getText().toString().length() > 0 && txtToDate.getText().toString().length() > 0) {
                        if (!toEdite) {
                            String Q = "";
                            String Name = txtName.getText().toString();
                            String FromDate = cFromDate;
                            String ToDate = cToDate;
                            String Description = txtDescription.getText().toString();
                            MarketingVisitTours data = new MarketingVisitTours();
                            data.UserID = (Integer.parseInt(fragVisitorPriod.AccountId));
                            data.VisitTourTitle = (Name);
                            data.DateFrom = (FromDate);
                            data.DateTo = (ToDate);
                            data.VisitTourDescription = (Description);
                            fragVisitorPriod.lPriod.add(data);

                            sendRequest();
                        } else {
                            String Q = "";
                            String Name = txtName.getText().toString();
                            String FromDate = cFromDate;
                            String ToDate = cToDate;
                            String Description = txtDescription.getText().toString();
                            fragVisitorPriod.lPriod.get(position).UserID = (Integer.parseInt(fragVisitorPriod.AccountId));
                            fragVisitorPriod.lPriod.get(position).VisitTourTitle = (Name);
                            fragVisitorPriod.lPriod.get(position).DateFrom = (FromDate);
                            fragVisitorPriod.lPriod.get(position).DateTo = (ToDate);
                            fragVisitorPriod.lPriod.get(position).VisitTourDescription = (Description);

                            sendRequest();
                        }
                    } else
                        Toast.makeText(getActivity(), "نام و تاریخ نباید خالی باشد", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), "تاریخ شروع نباید بعد از تاریخ پایان ، یا یکی باشد.", Toast.LENGTH_LONG).show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rInterface = Retrofite.getClient().create(RWInterface.class);

                pDialog = new Dialog(context);
                pDialog.Show();

                Map<String, Object> map = new HashMap<>();
                map.put("VisitTourID", mData.VisitTourID);
                map.put("BmmID", mData.UserID);

                Call<SimpleResponse> cDelete = rInterface.RQDeleteVisitTour(Setting.getToken(), new HashMap<>(map));
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
                                            map.put("VisitTourID", mData.VisitTourID);
                                            map.put("BmmID", mData.UserID);

                                            Call<SimpleResponse> cDeleted = rInterface.RQDeletedVisitTour(Setting.getToken(), new HashMap<>(map));
                                            cDeleted.enqueue(new Callback<SimpleResponse>() {
                                                @Override
                                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                                    pDialog.DisMiss();
                                                    if(response.isSuccessful()){
                                                        SQL.Execute("DELETE FROM MarketingVisitTours WHERE VisitTourID='" + response.body().AdditionalData.get("DeletedID").toString().replace(".0", "") + "'");

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

        Map<String, Object> mVisitTour = new HashMap<>();
        mVisitTour.put("VisitTourTitle", txtName.getText().toString());
        mVisitTour.put("VisitTourDescription", txtDescription.getText().toString());
        mVisitTour.put("DateFrom", cFromDate);
        mVisitTour.put("DateTo", cToDate);

        Call<SimpleResponse> cCommission = rInterface.RQAddMarketingVisitTour(Setting.getToken(), new HashMap<>(mVisitTour));
        cCommission.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if(response.isSuccessful()){
                    MarketingVisitTours data = new MarketingVisitTours();
                    data.VisitTourID = Integer.parseInt(response.body().AdditionalData.get("VisitTourID").toString().replace(".0", ""));
                    data.VisitTourTitle = txtName.getText().toString();
                    data.VisitTourDescription = txtName.getText().toString();
                    data.DateFrom = cFromDate;
                    data.DateTo = cToDate;

                    SQL.Insert(data);

                    act.getFragByState(FragmentState.VisitTours);
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
