package com.behincom.behincome.Activityes.Login;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Splash.actSplash;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.BaseData.Basic_Cities;
import com.behincom.behincome.Datas.BaseData.Basic_Provinces;
import com.behincom.behincome.Datas.Profile.ToSend.ToSendEditProfileAdmin;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class fragRegisterAdmin extends Fragment {

    static Context context;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    private Dialog picDialog;
    private com.behincom.behincome.Accesories.Dialog pDialog;
    private SpinAdapter adapOstan, adapCity;
    RWInterface rInterface;

    TextView lblTitle;
    TextView lblAccept;
    TextView lblEconomicTitle;
    TextView lbltxtActivityField;
    TextView lbltxtExprience;
    TextView lblWorkRangeTitle;
    TextView lblAddressTitle;
    TextView lbltxtOstan;
    TextView lbltxtCity;
    TextView lbltxtAddress;
    TextView lblDetailTitle;
    TextView lbltxtName;
    TextView lbltxtManagerName;
    TextView lbltxtSubmitNumber;
    TextView btnSetWorkRangeList;
    TextView lbltxtActivityField2;
    TextView lbltxtAct;
    TextView lbltxtNationalityCode;
    TextView lbltxtDescription;
    TextView lblPhone;
    TextView lblMobile;
    TextView lblEmail;
    TextView lblWebsite;
    EditText txtPhone;
    EditText txtMobile;
    EditText txtEmail;
    EditText txtWebsite;
    EditText txtManagerName;
    EditText txtSubmitNumber;
    EditText txtActRange;
    EditText txtExprience;
    EditText txtActivityField;
    EditText txtActivityField2;
    EditText txtAct;
    EditText txtName;
    EditText txtNationalityCode;
    EditText txtAddress;
    EditText txtDescription;
    ImageView imgBack;
    ImageView imgLogo;
    ImageView btnCheck;
    Spinner spinOstan;
    Spinner spinCity;
    CardView cardView;
    ScrollView scrollV;

    private List<Basic_Provinces> lOstan = new ArrayList<>();
    private List<Basic_Cities> lCity = new ArrayList<>();

    private String ManagerName = "", SubmitNumber = "", ActRange = "", Exprience = "", ActivityField = "", ActivityField2 = "", Act = "", Name = "", NationalityCode = "", Address = "", Description = "";
    private Bitmap Logo = null;
    private int OstanId = 0, CityId = 0;
    private boolean OstanFrist = false;
    private boolean isGrant = false;

    public static fragRegisterAdmin newInstance(Context mContext) {
        fragRegisterAdmin fragment = new fragRegisterAdmin();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register_admin, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblAccept = view.findViewById(R.id.lblAccept);
        lblEconomicTitle = view.findViewById(R.id.lblEconomicTitle);
        lbltxtActivityField = view.findViewById(R.id.lbltxtActivityField);
        lbltxtExprience = view.findViewById(R.id.lbltxtExprience);
        lblWorkRangeTitle = view.findViewById(R.id.lblWorkRangeTitle);
        lblAddressTitle = view.findViewById(R.id.lblAddressTitle);
        lbltxtOstan = view.findViewById(R.id.lbltxtOstan);
        lbltxtCity = view.findViewById(R.id.lbltxtCity);
        lbltxtAddress = view.findViewById(R.id.lbltxtAddress);
        lblDetailTitle = view.findViewById(R.id.lblDetailTitle);
        lbltxtName = view.findViewById(R.id.lbltxtName);
        lbltxtManagerName = view.findViewById(R.id.lbltxtManagerName);
        lbltxtSubmitNumber = view.findViewById(R.id.lbltxtSubmitNumber);
        lbltxtActivityField2 = view.findViewById(R.id.lbltxtActivityField2);
        lbltxtAct = view.findViewById(R.id.lbltxtAct);
        lbltxtNationalityCode = view.findViewById(R.id.lbltxtNationalityCode);
        lbltxtDescription = view.findViewById(R.id.lbltxtDescription);
        txtManagerName = view.findViewById(R.id.txtManagerName);
        txtSubmitNumber = view.findViewById(R.id.txtSubmitNumber);
        txtExprience = view.findViewById(R.id.txtExprience);
        txtActivityField = view.findViewById(R.id.txtActivityField);
        txtActivityField2 = view.findViewById(R.id.txtActivityField2);
        txtAct = view.findViewById(R.id.txtAct);
        txtName = view.findViewById(R.id.txtName);
        txtNationalityCode = view.findViewById(R.id.txtNationalityCode);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtDescription = view.findViewById(R.id.txtDescription);
        imgBack = view.findViewById(R.id.imgBack);
        imgLogo = view.findViewById(R.id.imgLogo);
        spinOstan = view.findViewById(R.id.spinOstan);
        spinCity = view.findViewById(R.id.spinCity);
        scrollV = view.findViewById(R.id.scrollV);
        cardView = view.findViewById(R.id.cardView);
        btnSetWorkRangeList = view.findViewById(R.id.btnSetWorkRangeList);
        lblPhone = view.findViewById(R.id.lblPhone);
        lblMobile = view.findViewById(R.id.lblMobile);
        lblEmail = view.findViewById(R.id.lblEmail);
        lblWebsite = view.findViewById(R.id.lblWebsite);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtWebsite = view.findViewById(R.id.txtWebsite);
        btnCheck = view.findViewById(R.id.btnCheck);

//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblTitle.setTypeface(tFace);lblAccept.setTypeface(tFace);lblEconomicTitle.setTypeface(tFace);lbltxtActivityField.setTypeface(tFace);
//        lbltxtExprience.setTypeface(tFace);lbltxtActRange.setTypeface(tFace);lblWorkRangeTitle.setTypeface(tFace);lblAddressTitle.setTypeface(tFace);
//        lbltxtOstan.setTypeface(tFace);lbltxtCity.setTypeface(tFace);lbltxtAddress.setTypeface(tFace);lblWorkTimeTitle.setTypeface(tFace);
//        lbltxtFromTime.setTypeface(tFace);txtFromTime.setTypeface(tFace);lbltxtToTime.setTypeface(tFace);txtToTime.setTypeface(tFace);
//        lbltxtFromDay.setTypeface(tFace);txtFromDay.setTypeface(tFace);lbltxtTomDay.setTypeface(tFace);txtToDay.setTypeface(tFace);
//        lblDetailTitle.setTypeface(tFace);lbltxtName.setTypeface(tFace);lbltxtManagerName.setTypeface(tFace);lbltxtSubmitNumber.setTypeface(tFace);
//        lbltxtActivityField.setTypeface(tFace);lbltxtAct.setTypeface(tFace);lbltxtNationalityCode.setTypeface(tFace);lbltxtDescription.setTypeface(tFace);
//        txtManagerName.setTypeface(tFace);txtSubmitNumber.setTypeface(tFace);txtActRange.setTypeface(tFace);txtExprience.setTypeface(tFace);
//        txtActivityField.setTypeface(tFace);txtActivityField2.setTypeface(tFace);txtAct.setTypeface(tFace);txtName.setTypeface(tFace);
//        txtNationalityCode.setTypeface(tFace);txtAddress.setTypeface(tFace);txtDescription.setTypeface(tFace);chHollyDay.setTypeface(tFace);
//        chAllDay.setTypeface(tFace);btnSetWorkRangeList.setTypeface(tFace);

        lblTitle.setText("ایجاد رزومه");
        imgBack.setVisibility(View.GONE);
        rInterface = Retrofite.getClient().create(RWInterface.class);

        txtNationalityCode.setTransformationMethod(null);
        txtPhone.setTransformationMethod(null);
        txtMobile.setTransformationMethod(null);

        Call cGetProvinces = rInterface.RQGetProvinces();
        cGetProvinces.enqueue(new Callback<List<Basic_Provinces>>() {
            @Override
            public void onResponse(Call<List<Basic_Provinces>> call, Response<List<Basic_Provinces>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size() > 0){
                        SQL.Insert(response.body());
                        lOstan = geter.getList(Basic_Provinces.class);

                        lCity = new ArrayList<>();
                        Basic_Provinces dOstan = new Basic_Provinces();
                        Basic_Cities dCity = new Basic_Cities();
                        dOstan.ProvinceTitle = ("استان را انتخاب کنید");
                        dCity.CityTitle = ("شهر را انتخاب کنید");
                        lOstan.add(0, dOstan);
                        lCity.add(0, dCity);
                        adapOstan = new SpinAdapter(context, lOstan, "ProvinceTitle");
                        adapCity = new SpinAdapter(context, lCity, "CityTitle");
                        spinOstan.setAdapter(adapOstan);
                        spinCity.setAdapter(adapCity);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String gg = "ASD";
            }
        });
        Call cGetCities = rInterface.RQGetCities();
        cGetCities.enqueue(new Callback<List<Basic_Cities>>() {
            @Override
            public void onResponse(Call<List<Basic_Cities>> call, Response<List<Basic_Cities>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size() > 0){
                        SQL.Insert(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String gg = "ASD";
            }
        });

        txtManagerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ManagerName = txtManagerName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtSubmitNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SubmitNumber = txtSubmitNumber.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//        txtActRange.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ActRange = txtActRange.getText().toString();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//        txtExprience.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Exprience = txtExprience.getText().toString();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//        txtActivityField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ActivityField = txtActivityField.getText().toString();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
        txtActivityField2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ActivityField2 = txtActivityField2.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtAct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Act = txtAct.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
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
        txtNationalityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NationalityCode = txtNationalityCode.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Address = txtAddress.getText().toString();
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
        spinOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OstanId = adapOstan.getIdItem(spinOstan.getSelectedItemPosition(), "id");
                if (!OstanFrist) {
                    if (spinOstan.getSelectedItemPosition() > 0) {
                        lCity = geter.getList(Basic_Cities.class, "WHERE ProvinceID='" + adapOstan.getItemString(spinOstan.getSelectedItemPosition(), "ProvinceID") + "'");
                        Basic_Cities dCity = new Basic_Cities();
                        dCity.CityTitle = ("شهر را انتخاب کنید");
                        lCity.add(0, dCity);
                        adapCity = new SpinAdapter(context, lCity, "CityTitle");
                        spinCity.setAdapter(adapCity);
                    } else {
                        lCity = new ArrayList<>();
                        Basic_Cities dCity = new Basic_Cities();
                        dCity.CityTitle = ("شهر را انتخاب کنید");
                        lCity.add(dCity);
                        adapCity = new SpinAdapter(context, lCity, "CityTitle");
                        spinCity.setAdapter(adapCity);
                    }
                } else {
                    OstanFrist = false;
                    String aaa = adapOstan.getItemString(spinOstan.getSelectedItemPosition(), "ProvinceID");
                    lCity = geter.getList(Basic_Cities.class, "WHERE ProvinceID='" + aaa + "'");
                    Basic_Cities dCity = new Basic_Cities();
                    dCity.CityTitle = ("شهر را انتخاب کنید");
                    lCity.add(0, dCity);
                    adapCity = new SpinAdapter(context, lCity, "CityTitle");
                    spinCity.setAdapter(adapCity);
                    if (lCity.size() > 0) {
                        CityId = adapCity.getIdItem(spinCity.getSelectedItemPosition(), "CityID");
                        spinCity.setSelection(adapCity.getItemPosition("CityID", Integer.toString(CityId)));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CityId = adapCity.getIdItem(spinCity.getSelectedItemPosition(), "CityID");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*
        lblPhone;
        lblMobile;
        lblEmail;
        lblWebsite;
        txtPhone;
        txtMobile;
        txtEmail;
        txtWebsite;
        ina AddTextChangedListener nashodan
         */

//        chAllDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AllDay = chAllDay.isChecked();
//            }
//        });
//        chHollyDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HollyDay = chHollyDay.isChecked();
//            }
//        });
//
//        txtFromTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTime(1);
//            }
//        });
//        txtToTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTime(2);
//            }
//        });
//        txtFromDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dayDialog = new Dialog(context);
//                dayDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dayDialog.setCancelable(true);
//                dayDialog.setCanceledOnTouchOutside(true);
//                dayDialog.setContentView(R.layout.dialog_select_day_of_week);
//                Objects.requireNonNull(dayDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                final TextView btn0 = dayDialog.findViewById(R.id.btn0);
//                final TextView btn1 = dayDialog.findViewById(R.id.btn1);
//                final TextView btn2 = dayDialog.findViewById(R.id.btn2);
//                final TextView btn3 = dayDialog.findViewById(R.id.btn3);
//                final TextView btn4 = dayDialog.findViewById(R.id.btn4);
//                final TextView btn5 = dayDialog.findViewById(R.id.btn5);
//                final TextView btn6 = dayDialog.findViewById(R.id.btn6);
//
//                Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                btn0.setTypeface(tFace);
//                btn1.setTypeface(tFace);
//                btn2.setTypeface(tFace);
//                btn3.setTypeface(tFace);
//                btn4.setTypeface(tFace);
//                btn5.setTypeface(tFace);
//                btn6.setTypeface(tFace);
//
//                btn0.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtFromDay.setText(btn0.getText().toString());
//                        FromDay = "0";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtFromDay.setText(btn1.getText().toString());
//                        FromDay = "1";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtFromDay.setText(btn2.getText().toString());
//                        FromDay = "2";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtFromDay.setText(btn3.getText().toString());
//                        FromDay = "3";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn4.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtFromDay.setText(btn4.getText().toString());
//                        FromDay = "4";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn5.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtFromDay.setText(btn5.getText().toString());
//                        FromDay = "5";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn6.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtFromDay.setText(btn6.getText().toString());
//                        FromDay = "6";
//                        dayDialog.dismiss();
//                    }
//                });
//                dayDialog.show();
//            }
//        });
//        txtToDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dayDialog = new Dialog(context);
//                dayDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dayDialog.setCancelable(true);
//                dayDialog.setCanceledOnTouchOutside(true);
//                dayDialog.setContentView(R.layout.dialog_select_day_of_week);
//                Objects.requireNonNull(dayDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                final TextView btn0 = dayDialog.view.findViewById(R.id.btn0);
//                final TextView btn1 = dayDialog.view.findViewById(R.id.btn1);
//                final TextView btn2 = dayDialog.view.findViewById(R.id.btn2);
//                final TextView btn3 = dayDialog.view.findViewById(R.id.btn3);
//                final TextView btn4 = dayDialog.view.findViewById(R.id.btn4);
//                final TextView btn5 = dayDialog.view.findViewById(R.id.btn5);
//                final TextView btn6 = dayDialog.view.findViewById(R.id.btn6);
//
//                Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                btn0.setTypeface(tFace);
//                btn1.setTypeface(tFace);
//                btn2.setTypeface(tFace);
//                btn3.setTypeface(tFace);
//                btn4.setTypeface(tFace);
//                btn5.setTypeface(tFace);
//                btn6.setTypeface(tFace);
//
//                btn0.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtToDay.setText(btn0.getText().toString());
//                        ToDay = "0";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtToDay.setText(btn1.getText().toString());
//                        ToDay = "1";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtToDay.setText(btn2.getText().toString());
//                        ToDay = "2";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtToDay.setText(btn3.getText().toString());
//                        ToDay = "3";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn4.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtToDay.setText(btn4.getText().toString());
//                        ToDay = "4";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn5.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtToDay.setText(btn5.getText().toString());
//                        ToDay = "5";
//                        dayDialog.dismiss();
//                    }
//                });
//                btn6.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txtToDay.setText(btn6.getText().toString());
//                        ToDay = "6";
//                        dayDialog.dismiss();
//                    }
//                });
//                dayDialog.show();
//            }
//        });
//        btnSetWorkRangeList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                workRangeDialog = new Dialog(context);
//                workRangeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                workRangeDialog.setCancelable(true);
//                workRangeDialog.setCanceledOnTouchOutside(true);
//                workRangeDialog.setContentView(R.layout.dialog_set_work_range_list);
//                Objects.requireNonNull(workRangeDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                RecyclerView.LayoutManager mLayoutManager;
//                mLayoutManager = new LinearLayoutManager(context);
//                final TextView lbltxtOstan = workRangeDialog.view.findViewById(R.id.lbltxtOstan);
//                final TextView lbltxtCity = workRangeDialog.view.findViewById(R.id.lbltxtCity);
//                final TextView lblCancell = workRangeDialog.view.findViewById(R.id.lblCancell);
//                final TextView lblAccept = workRangeDialog.view.findViewById(R.id.lblAccept);
//                final Spinner spinOstanRange = workRangeDialog.view.findViewById(R.id.spinOstan);
//                final Spinner spinCityRange = workRangeDialog.view.findViewById(R.id.spinCity);
//                final ImageView btnAdd = workRangeDialog.view.findViewById(R.id.btnAdd);
//                final RecyclerView lstWorkRangeList = workRangeDialog.view.findViewById(R.id.lstWorkRangeList);
//
//                Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                lbltxtOstan.setTypeface(tFace);
//                lbltxtCity.setTypeface(tFace);
//                lOstanRange = SQL.Select(Data.APIKeys.getOstan, DataProvince.class, context);
//                lCityRange = new ArrayList<>();
//                DataProvince dOstan = new DataProvince();
//                DataCity dCity = new DataCity();
//                dOstan.name("استان را انتخاب کنید");
//                dCity.name("شهر را انتخاب کنید");
//                lOstanRange.add(0, dOstan);
//                lCityRange.add(0, dCity);
//                adapOstanRange = new SpinAdapter(context, lOstanRange, "name");
//                adapCityRange = new SpinAdapter(context, lCityRange, "name");
//                spinOstanRange.setAdapter(adapOstanRange);
//                spinCityRange.setAdapter(adapCityRange);
//                spinOstanRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        if (spinOstanRange.getSelectedItemPosition() > 0) {
//                            String Q = Data.APIKeys.getCity + " WHERE province_id='" + adapOstanRange.getItemString(spinOstanRange.getSelectedItemPosition(), "id") + "'";
//                            lCityRange = SQL.Select(Q, DataCity.class, context);
//                            DataCity dCity = new DataCity();
//                            dCity.name("شهر را انتخاب کنید");
//                            lCityRange.add(0, dCity);
//                            adapCityRange = new SpinAdapter(context, lCityRange, "name");
//                            spinCityRange.setAdapter(adapCityRange);
//                        } else {
//                            lCityRange = new ArrayList<>();
//                            DataCity dCity = new DataCity();
//                            dCity.name("شهر را انتخاب کنید");
//                            lCityRange.add(dCity);
//                            adapCityRange = new SpinAdapter(context, lCityRange, "name");
//                            spinCityRange.setAdapter(adapCityRange);
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                    }
//                });
//
//                lWorkRange2 = new ArrayList<>();
//                lWorkRange2.addAll(lWorkRange);
//
//                final adapWorkRange adapterRange = new adapWorkRange(lWorkRange2, context);
//                lstWorkRangeList.setHasFixedSize(true);
//                mLayoutManager = new LinearLayoutManager(context);
//                lstWorkRangeList.setLayoutManager(mLayoutManager);
//                lstWorkRangeList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
//                lstWorkRangeList.setItemAnimator(new DefaultItemAnimator());
//                lstWorkRangeList.setAdapter(adapterRange);
//
//                btnAdd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (spinOstanRange.getSelectedItemPosition() > 0 && spinCityRange.getSelectedItemPosition() > 0) {
//                            DataWorkRange data = new DataWorkRange();
//                            data.ostanId(adapOstanRange.getIdItem(spinOstanRange.getSelectedItemPosition(), "id"));
//                            data.cityId(adapCityRange.getIdItem(spinCityRange.getSelectedItemPosition(), "id"));
//                            lWorkRange2.add(data);
//                            lWorkRange.add(data);
//                            adapterRange.notifyDataSetChanged();
//                        } else {
//                            Toast.makeText(context, "استان یا شهر نباید خالی باشد.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                lblCancell.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        workRangeDialog.dismiss();
//                    }
//                });
//                lblAccept.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (lWorkRange2.size() > 0) {
//                            String WorkRanger = "";
//                            for (DataWorkRange data : lWorkRange2) {
//                                List<DataProvince> lOstan = SQL.Select(Data.APIKeys.getOstan + " WHERE id='" + data.ostanId() + "'", DataProvince.class, context);
//                                List<DataCity> lCity = SQL.Select(Data.APIKeys.getCity + " WHERE id='" + data.cityId() + "'", DataCity.class, context);
//
//                                WorkRanger += "استان " + lOstan.get(0).name() + " - شهر " + lCity.get(0).name() + "<br>";
//                            }
//                            lWorkRange = new ArrayList<>();
//                            lWorkRange.addAll(lWorkRange2);
//                            btnSetWorkRangeList.setText(Html.fromHtml(WorkRanger));
//                            workRangeDialog.dismiss();
//                        } else
//                            Toast.makeText(context, "لیست نباید خالی باشد.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                workRangeDialog.show();
//            }
//        });
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicSelector();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new com.behincom.behincome.Accesories.Dialog(getActivity());
                pDialog.Show();

                ObjectMapper oMapper = new ObjectMapper();
                Map map = oMapper.convertValue(getDatas(), Map.class);

                Call cEditProfile = rInterface.RQEditProfileAdmin(Setting.getToken(), new HashMap<>(map));
                cEditProfile.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if(response.isSuccessful()){
                            Setting.Save("BMMUserID", Integer.toString(Setting.getUserID()));
                            Setting.Save("BMMUserName", txtManagerName.getText().toString());

                            Intent intent = new Intent(context, actSplash.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
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

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        txtManagerName.setText(ManagerName);
        txtSubmitNumber.setText(ActRange);
//        txtActRange.setText(ActRange);
//        txtExprience.setText(Exprience);
//        txtActivityField.setText(ActivityField);
        txtActivityField2.setText(ActivityField2);
        txtAct.setText(Act);
        txtName.setText(Name);
        txtNationalityCode.setText(NationalityCode);
        txtAddress.setText(Address);
        txtDescription.setText(Description);
        try {
            spinOstan.setSelection(adapOstan.getItemPosition("ProvinceID", Integer.toString(OstanId)));
            spinCity.setSelection(adapCity.getItemPosition("CityID", Integer.toString(CityId)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        scrollV.scrollTo(0, 0);
    }

    private boolean askForPermission(String permission) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, 3);
                return false;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, 3);
                return false;
            }
        } else {
            return true;
        }
    }

    private void PicSelector() {
        if (askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            isGrant = askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            picDialog = new Dialog(context);
            picDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            picDialog.setCancelable(true);
            picDialog.setCanceledOnTouchOutside(true);
            picDialog.setContentView(R.layout.dialog_takepic);
            Objects.requireNonNull(picDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            TextView lblTakePhoto = picDialog.findViewById(R.id.lblTakePhoto);
            TextView lblChooseFromGallery = picDialog.findViewById(R.id.lblChooseFromGallery);
//            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ir_sans.ttf");
//            lblTakePhoto.setTypeface(tf);
//            lblChooseFromGallery.setTypeface(tf);

            lblTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                    picDialog.dismiss();
                }
            });
            lblChooseFromGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                    picDialog.dismiss();
                }
            });
            picDialog.show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        Logo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        try {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            Logo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgLogo) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imgLogo.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                        } catch (Exception Ex) {
                            String Er = Ex.getMessage();
                        }
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri pickedImage = imageReturnedIntent.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Logo = BitmapFactory.decodeFile(imagePath, options);

                        try {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            Logo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgLogo) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imgLogo.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                        } catch (Exception Ex) {
                            String Er = Ex.getMessage();
                        }
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                break;
        }
    }

    private static List<Integer> lActivityFields = new ArrayList<>();

    private ToSendEditProfileAdmin getDatas(){
        ToSendEditProfileAdmin data = new ToSendEditProfileAdmin();

        data.UserID = Setting.getUserID();
        data.CityID = Integer.parseInt(adapCity.getItemString(spinCity.getSelectedItemPosition(), "CityID"));
        data.LogoFilename = "";//todo
        data.CompanyTitle = txtName.getText().toString();
        data.ManagerName = txtManagerName.getText().toString();
        data.EconomicalNumber = txtSubmitNumber.getText().toString();
        data.NationalCode = txtNationalityCode.getText().toString();
        data.OtherActivitieyFields = txtActivityField2.getText().toString();
        data.ActivityExperience = txtExprience.getText().toString();
        data.Address = txtAddress.getText().toString();
        data.Phone = txtPhone.getText().toString();
        data.Mobile = txtMobile.getText().toString();
        data.Email = txtEmail.getText().toString();
        data.Website = txtWebsite.getText().toString();
        data.Latitude = 0.1;//todo
        data.Longitude = 0.1;//todo
        data.Description = txtDescription.getText().toString();

        return data;
    }

}
