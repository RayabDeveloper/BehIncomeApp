package com.behincom.behincome.Activityes.Account;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Accesories.Validations;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.BaseData.Basic_citi;
import com.behincom.behincome.Datas.BaseData.Basic_Ostan;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Profile;
import com.behincom.behincome.Datas.Profile.ToSend.ToSendEditProfile;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.behincom.behincome.WebRequest.Retrofite.BASE;

public class fragProfileSubmiter extends Fragment {

    static Context context;
    @SuppressLint("StaticFieldLeak")
    static Context contexti;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    Validations validat = new Validations();
    static RSQLite SQLi = new RSQLite();
    Dialog piDialog;
    static Dialog dEditor;
    static Dialog ynDialog;
    PersianCalendar PC = new PersianCalendar();
    DatePickerDialog DateDialog;
    com.behincom.behincome.Accesories.Dialog pDialog;
    com.behincom.behincome.Accesories.Setting Setting = new Setting();
    actMain act = new actMain();
    RWInterface rInterface;

    com.behincom.behincome.Accesories.Dialog mPDialog;
    SpinAdapter spinAdap_Ostan, spinAdap_City;

    //Elements
    @SuppressLint("StaticFieldLeak")
    static TextView lblTitle, lblProfileDetailTitle, txtProfilePersonalDetailsMarri, txtProfilePersonalDetailsSex, lblUserCode1, lblUserCode2, lblUserPhone1, lblUserPhone2, lblProfileAddressDetailsTitle, txtProfilePersonalDetailsBirthDay, lbltxtName, lbltxtFamily, lbltxtBirtDate, lbltxtAddress, lbltxtNationality, lbltxtOstan, lbltxtCity;
    @SuppressLint("StaticFieldLeak")
    static EditText txtProfilePersonalDetailsName, txtProfilePersonalDetailsFamily, txtProfilePersonalDetailsNationalityCode, txtProfileAddressDetailsAddress;
    @SuppressLint("StaticFieldLeak")
    static RadioButton radMan, radWoman, radMotahel, radMojarad;
    @SuppressLint("StaticFieldLeak")
    static Spinner sniperOstan, sniperCity;
    //    RecyclerView lstProfileSchoolDetailsMain, lstProfileWorkDetailsMain, lstProfileSkillDetailsMain;
    @SuppressLint("StaticFieldLeak")
//    static RecyclerView lstProfileSchoolDetailsMaini, lstProfileWorkDetailsMaini, lstProfileSkillDetailsMaini;
            CardView cardView;
    static ScrollView scrollV;
    ImageView imgBack, imgProfile, btnCheck;
    RecyclerView.LayoutManager mLayoutManager;
    static RecyclerView.LayoutManager mLayoutManageri;

    //Lists
//    public static List<DataEducationalExperience> lEducationalExperience = new ArrayList<>();
//    public static List<DataSkill> lSkill = new ArrayList<>();
//    public static List<DataWorkExperience> lWorkExperience = new ArrayList<>();
    public static List<Basic_citi> lEducationalExperience = new ArrayList<>();
    public static List<Basic_citi> lSkill = new ArrayList<>();
    public static List<Basic_citi> lWorkExperience = new ArrayList<>();
    private static List<Basic_Ostan> lListOstan = new ArrayList<>();
    private static List<Basic_citi> lListCity = new ArrayList<>();
    private List<String> lURLs = new ArrayList<>();

    //Variables
    private Profile dMarketer = new Profile();
    static String Name = "", Family = "", BirthDate = "", Nationality = "", About = "", cBirthDate = "0000-00-00T00:00:00", mAddress = "";
    static int OstanSpin = 0, CitySpin = 0;
    static Bitmap ProfileImg = null;
    boolean isGrant = false;
    boolean OstanFrist = false;
    boolean isBirth = false;
    public static boolean toEdite = true;
    public static int mSY = 0, nSY = 0;
    private static String ProfilePhoto = "";

    public static fragProfileSubmiter newInstance(Context mContext) {
        fragProfileSubmiter fragment = new fragProfileSubmiter();
        context = mContext;
        return fragment;
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile_submiter, container, false);

//Define

        rInterface = Retrofite.getClient().create(RWInterface.class);
        radMan = view.findViewById(R.id.radMan);
        btnCheck = view.findViewById(R.id.btnCheck);
        scrollV = view.findViewById(R.id.scrollV);
        radWoman = view.findViewById(R.id.radWoman);
        radMotahel = view.findViewById(R.id.radMotahel);
        radMojarad = view.findViewById(R.id.radMojarad);
        lbltxtOstan = view.findViewById(R.id.lbltxtOstan);
        lbltxtCity = view.findViewById(R.id.lbltxtCity);
        lbltxtName = view.findViewById(R.id.lbltxtName);
        lbltxtFamily = view.findViewById(R.id.lbltxtFamily);
        lbltxtBirtDate = view.findViewById(R.id.lbltxtBirtDate);
//        lbltxtAboutMe = view.findViewById(R.id.lbltxtAboutMe);//todo todo todo
        lbltxtAddress = view.findViewById(R.id.lbltxtAddress);
        lbltxtNationality = view.findViewById(R.id.lbltxtNationality);
//        btnSchoolAdder = view.findViewById(R.id.btnSchoolAdder);
        lblProfileAddressDetailsTitle = view.findViewById(R.id.lblProfileAddressDetailsTitle);
//        btnWorkAdder = view.findViewById(R.id.btnWorkAdder);
//        btnSkillAdder = view.findViewById(R.id.btnSkillAdder);
        lblTitle = view.findViewById(R.id.lblTitle);
        lblUserCode1 = view.findViewById(R.id.lblUserCode1);
        lblUserCode2 = view.findViewById(R.id.lblUserCode2);
        lblUserPhone1 = view.findViewById(R.id.lblUserPhone1);
        lblUserPhone2 = view.findViewById(R.id.lblUserPhone2);
        imgBack = view.findViewById(R.id.imgBack);
        imgProfile = view.findViewById(R.id.imgProfile);
//        lstProfileSchoolDetailsMain = view.findViewById(R.id.lstProfileSchoolDetailsMain);
//        lstProfileSchoolDetailsMaini = view.findViewById(R.id.lstProfileSchoolDetailsMain);
//        lstProfileWorkDetailsMain = view.findViewById(R.id.lstProfileWorkDetailsMain);
//        lstProfileWorkDetailsMaini = view.findViewById(R.id.lstProfileWorkDetailsMain);
//        lstProfileSkillDetailsMain = view.findViewById(R.id.lstProfileSkillDetailsMain);
//        lstProfileSkillDetailsMaini = view.findViewById(R.id.lstProfileSkillDetailsMain);
//        lblProfileSchoolDetailsTitle = view.findViewById(R.id.lblProfileSchoolDetailsTitle);
//        lblProfileWorkDetailsTitle = view.findViewById(R.id.lblProfileWorkDetailsTitle);
//        lblProfileSkillDetailsTitle = view.findViewById(R.id.lblProfileSkillDetailsTitle);
        lblProfileDetailTitle = view.findViewById(R.id.lblProfileDetailTitle);
        txtProfilePersonalDetailsName = view.findViewById(R.id.txtProfilePersonalDetailsName);
        txtProfilePersonalDetailsFamily = view.findViewById(R.id.txtProfilePersonalDetailsFamily);
        txtProfilePersonalDetailsBirthDay = view.findViewById(R.id.txtProfilePersonalDetailsBirthDay);
        txtProfilePersonalDetailsNationalityCode = view.findViewById(R.id.txtProfilePersonalDetailsNationalityCode);
        txtProfilePersonalDetailsSex = view.findViewById(R.id.txtProfilePersonalDetailsSex);
        txtProfilePersonalDetailsMarri = view.findViewById(R.id.txtProfilePersonalDetailsMarri);
//        txtProfilePersonalDetailsAboutMe = view.findViewById(R.id.txtProfilePersonalDetailsAboutMe);
        sniperOstan = view.findViewById(R.id.spinProfileAddressDetailsOstan);
        sniperCity = view.findViewById(R.id.spinProfileAddressDetailsCity);
        txtProfileAddressDetailsAddress = view.findViewById(R.id.txtProfileAddressDetailsAddress);
        cardView = view.findViewById(R.id.cardView);

        txtProfilePersonalDetailsNationalityCode.setTransformationMethod(null);
//        txtMoaref.setTransformationMethod(null);

        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManageri = new LinearLayoutManager(contexti);
        pDialog = new com.behincom.behincome.Accesories.Dialog(context);

        lblTitle.setText("ویرایش پروفایل");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Finisher();
            }
        });

        //TODO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollV.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    mSY = scrollY;
                }
            });
        }

        dEditor = new Dialog(context);
        ynDialog = new Dialog(context);
        dEditor.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ynDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ynDialog.setCancelable(true);
        ynDialog.setCanceledOnTouchOutside(true);

        //add Skill Or Exprience
//        btnSchoolAdder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nSY = mSY;
//                Schooler();
//            }
//        });
//        btnWorkAdder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nSY = mSY;
//                Worker();
//            }
//        });
//        btnSkillAdder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nSY = mSY;
//                Skiller();
//            }
//        });

        radMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radWoman.setChecked(false);
            }
        });
        radWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radMan.setChecked(false);
            }
        });
        radMotahel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radMojarad.setChecked(false);
            }
        });
        radMojarad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radMotahel.setChecked(false);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicSelector();
            }
        });
        txtProfilePersonalDetailsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Name = txtProfilePersonalDetailsName.getText().toString();
            }
        });
        txtProfilePersonalDetailsFamily.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Family = txtProfilePersonalDetailsFamily.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtProfilePersonalDetailsBirthDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BirthDate = txtProfilePersonalDetailsBirthDay.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtProfilePersonalDetailsNationalityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Nationality = txtProfilePersonalDetailsNationalityCode.getText().toString();
                if (txtProfilePersonalDetailsNationalityCode.getText().length() == 10) {
                    if (validat.Nationality(txtProfilePersonalDetailsNationalityCode.getText().toString()))
                        Toast("کد ملی صحیح نیست");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        txtProfilePersonalDetailsAboutMe.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                About = txtProfilePersonalDetailsAboutMe.getText().toString();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        DateDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
                DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                cBirthDate = DC.getCSharp();
                BirthDate = mDate;
                txtProfilePersonalDetailsBirthDay.setText(mDate);
                isBirth = false;
            }
        }, PC.getPersianYear(), PC.getPersianMonth(), PC.getPersianDay());
        DateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                isBirth = false;
            }
        });
        txtProfilePersonalDetailsBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBirth) {
                    isBirth = true;
                    DateDialog.show(getActivity().getFragmentManager(), "تاریخ تولد");
                }
            }
        });
        //Set Switch Setting
        lListOstan = geter.getList(Basic_Ostan.class);
        lListCity = geter.getList(Basic_citi.class, "WHERE CityID='0'");
        Basic_Ostan dOstan = new Basic_Ostan();
        Basic_citi dCity = new Basic_citi();
        dOstan.ProvinceTitle = ("استان را انتخاب کنید");
        dCity.CityTitle = ("شهر را انتخاب کنید");
        lListOstan.add(0, dOstan);
        lListCity.add(0, dCity);

        spinAdap_Ostan = new SpinAdapter(context, lListOstan, "ProvinceTitle");
        spinAdap_City = new SpinAdapter(context, lListCity, "CityTitle");
        sniperOstan.setAdapter(spinAdap_Ostan);
        sniperCity.setAdapter(spinAdap_City);

        sniperCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CitySpin = sniperCity.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sniperOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OstanSpin = sniperOstan.getSelectedItemPosition();
                if (!OstanFrist) {
                    if (sniperOstan.getSelectedItemPosition() > 0) {
                        lListCity = geter.getList(Basic_citi.class, "WHERE ProvinceID='" + spinAdap_Ostan.getItemString(sniperOstan.getSelectedItemPosition(), "ProvinceID") + "'");
                        Basic_citi dCity = new Basic_citi();
                        dCity.CityTitle = ("شهر را انتخاب کنید");
                        lListCity.add(0, dCity);
                        spinAdap_City = new SpinAdapter(context, lListCity, "CityTitle");
                        sniperCity.setAdapter(spinAdap_City);
                    } else {
                        lListCity = new ArrayList<>();
                        Basic_citi dCity = new Basic_citi();
                        dCity.CityTitle = ("شهر را انتخاب کنید");
                        lListCity.add(dCity);
                        spinAdap_City = new SpinAdapter(context, lListCity, "CityTitle");
                        sniperCity.setAdapter(spinAdap_City);
                    }
                } else {
                    if(firster) {
                        OstanFrist = false;
                    }
                    if(!firster){
                        firster = true;
                    }
                    String aaa = spinAdap_Ostan.getItemString(sniperOstan.getSelectedItemPosition(), "ProvinceID");
                    lListCity = geter.getList(Basic_citi.class, "WHERE ProvinceID='" + aaa + "'");
                    Basic_citi dCity = new Basic_citi();
                    dCity.CityTitle = ("شهر را انتخاب کنید");
                    lListCity.add(0, dCity);
                    spinAdap_City = new SpinAdapter(context, lListCity, "CityTitle");
                    sniperCity.setAdapter(spinAdap_City);
                    if (lListCity.size() > 0) {
                        CitySpin = spinAdap_City.getItemPosition("CityID", Integer.toString(dMarketer.CityID));
                        sniperCity.setSelection(CitySpin);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        lLogin = SQL.Select(Data.APIKeys.getProfile, Data.Login.class, context);
        try {
            lblUserCode2.setText(com.behincom.behincome.Accesories.Setting.getReferCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lblUserPhone2.setText(com.behincom.behincome.Accesories.Setting.getUserName());

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sniperOstan.getSelectedItemPosition() > 0 && sniperCity.getSelectedItemPosition() > 0 && txtProfilePersonalDetailsName.getText().length() > 0 && txtProfilePersonalDetailsNationalityCode.getText().length() > 0) {
                    pDialog.Show();

                    File mFile = new File(ProfileImagePath);
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), mFile);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("profileimage", mFile.getName(), reqFile);

                    Call<SimpleResponse> call4 = rInterface.RQProfilePic(Setting.getToken(), body);
                    call4.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            try {
                                if(response.isSuccessful()){
                                    pDialog.Show();

                                    lURLs = new ArrayList<>();
                                    try {
                                        Object[] keys = response.body().AdditionalData.keySet().toArray();
                                        for (Object data : keys) {
                                            String val = response.body().AdditionalData.get(data.toString()).toString();
                                            lURLs.add(val);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    ObjectMapper oMapper = new ObjectMapper();
                                    HashMap map = oMapper.convertValue(getData(), HashMap.class);

                                    Call cEditProfile = rInterface.RQEditProfile(Setting.getToken(), map);
                                    cEditProfile.enqueue(new Callback<SimpleResponse>() {
                                        @Override
                                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                            if (response.isSuccessful()) {
                                                Toast("اطلاعات پروفایل ویرایش شدند.");
                                                pDialog.DisMiss();
                                                Finisher();
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
                            }catch (Exception Ex){
                                String Er = Ex.getMessage();
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                } else {
                    Toast("لطفا همه اطلاعات را پر کنید");
                }
            }
        });

//        lstProfileSchoolDetailsMain.setNestedScrollingEnabled(false);
//        lstProfileSchoolDetailsMaini.setNestedScrollingEnabled(false);
//        lstProfileWorkDetailsMain.setNestedScrollingEnabled(false);
//        lstProfileWorkDetailsMaini.setNestedScrollingEnabled(false);
//        lstProfileSkillDetailsMain.setNestedScrollingEnabled(false);
//        lstProfileSkillDetailsMaini.setNestedScrollingEnabled(false);

        if (toEdite) {
            mPDialog = new com.behincom.behincome.Accesories.Dialog(context);
            mPDialog.Show();

            Call cProfile = rInterface.RQGetProfile(Setting.getToken());
            cProfile.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if (response.isSuccessful()) {
                        dMarketer = response.body();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String PhotoURL = "";
                                    try {
                                        PhotoURL = dMarketer.PhotoFilename;
                                        if (PhotoURL.length() > 5)
                                            PhotoURL = BASE + "Uploads/ProfileImages/" + PhotoURL;
                                        else
                                            PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.profile_pic_icon).toString();
                                    } catch (Exception e) {
                                        PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.profile_pic_icon).toString();
                                    }
                                    Glide.with(context).load(PhotoURL).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                                        @Override
                                        protected void setResource(Bitmap resource) {
                                            RoundedBitmapDrawable circularBitmapDrawable =
                                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                            circularBitmapDrawable.setCircular(true);
                                            imgProfile.setImageDrawable(circularBitmapDrawable);
                                        }
                                    });
                                    com.behincom.behincome.Accesories.Setting.Save("ReferCode", dMarketer.ReferedCode);
                                    lblUserCode2.setText(dMarketer.ReferedCode);
                                    lblUserPhone2.setText(com.behincom.behincome.Accesories.Setting.getUserName());
                                    Name = dMarketer.Firstname;
                                    mAddress = dMarketer.Address;
                                    txtProfilePersonalDetailsName.setText((Name == null ? "" : Name));
                                    Family = dMarketer.Lastname;
                                    txtProfilePersonalDetailsFamily.setText((Family == null ? "" : Family));
                                    try {
                                        DateConverter DC = new DateConverter(dMarketer.BirthDate, true);
                                        cBirthDate = dMarketer.BirthDate;
                                        BirthDate = DC.getOnlyDate();
                                    } catch (Exception Ex) {
                                        String Er = Ex.getMessage();
                                        cBirthDate = "0000-00-00T00:00:00";
                                        BirthDate = "";
                                    }
                                    txtProfilePersonalDetailsBirthDay.setText(BirthDate);
                                    Nationality = dMarketer.NationalCode;
                                    txtProfilePersonalDetailsNationalityCode.setText((Nationality == null ? "" : Nationality));
                                    txtProfileAddressDetailsAddress.setText((mAddress == null ? "" : mAddress));
//                                    About = dMarketer.description();
//                                    txtProfilePersonalDetailsAboutMe.setText((About == null ? "" : About));
                                    if (lListOstan.size() > 0) {
                                        int cID = dMarketer.CityID;
                                        List<Basic_citi> lCities = geter.getList(Basic_citi.class, "WHERE CityID='" + cID + "'");
                                        if(lCities.size() > 0) {
                                            OstanSpin = spinAdap_Ostan.getItemPosition("ProvinceID", Integer.toString(lCities.get(0).ProvinceID));
                                            OstanFrist = true;
                                        }
                                        sniperOstan.setSelection(OstanSpin);
                                    }
                                    if (dMarketer.GenderTypeID == 1) {
                                        radMan.setChecked(true);
                                        radWoman.setChecked(false);
                                    } else {
                                        radMan.setChecked(false);
                                        radWoman.setChecked(true);
                                    }
                                    if (dMarketer.MilitaryStatusID == 1) {
                                        radMotahel.setChecked(true);
                                        radMojarad.setChecked(false);
                                    } else {
                                        radMotahel.setChecked(false);
                                        radMojarad.setChecked(true);
                                    }
                                    toEdite = false;
//                                    lEducationalExperience = dMarketer.educational_experiences();
//                                    lWorkExperience = dMarketer.work_experiences();
//                                    lSkill = dMarketer.skills();


                                    if (lListOstan.size() > 0) sniperOstan.setSelection(OstanSpin);
                                    if (lListCity.size() > 0) sniperCity.setSelection(CitySpin);
                                    Manager();
                                } catch (Exception Ex) {
                                    String Er = Ex.getMessage();
                                }
                            }
                        });
                    }
                    mPDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    mPDialog.DisMiss();
                }
            });
        }
        scrollV.fullScroll(View.FOCUS_UP);

        return view;
    }
    private boolean firster = false;
    private boolean askForPermission(String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity) context), permission)) {
                ActivityCompat.requestPermissions(((Activity) context), new String[]{permission}, 3);
                return false;
            } else {
                ActivityCompat.requestPermissions(((Activity) context), new String[]{permission}, 3);
                return false;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        rInterface = Retrofite.getClient().create(RWInterface.class);
        Manager();
        if (nSY > 0) {
            scrollV.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollV.scrollTo(0, nSY);
                    nSY = 0;
                }
            }, 1000);
        }
    }
    private void Manager() {
//        lstRefresher();
        if (!toEdite) {
            try {
                try {
                    lblUserCode2.setText(com.behincom.behincome.Accesories.Setting.getReferCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lblUserPhone2.setText(com.behincom.behincome.Accesories.Setting.getUserName());
                txtProfilePersonalDetailsName.setText(Name);
                txtProfilePersonalDetailsFamily.setText(Family);
                txtProfilePersonalDetailsBirthDay.setText(BirthDate);
                txtProfilePersonalDetailsNationalityCode.setText(Nationality);
//                txtProfilePersonalDetailsAboutMe.setText(About);
                txtProfileAddressDetailsAddress.setText(mAddress);
                if (lListOstan.size() > 0) sniperOstan.setSelection(OstanSpin);
                if (lListCity.size() > 0) sniperCity.setSelection(CitySpin);
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
        }
    }
    private ToSendEditProfile getData() {
        ToSendEditProfile mData = new ToSendEditProfile();
        mData.Firstname = (txtProfilePersonalDetailsName.getText().toString());
        mData.Lastname = (txtProfilePersonalDetailsFamily.getText().toString());
        mData.BirthDate = (cBirthDate);
        mData.NationalCode = (txtProfilePersonalDetailsNationalityCode.getText().toString());
        int mGender;
        if (radMan.isChecked()) mGender = 1;
        else mGender = 2;
        int mMarital;
        if (radMotahel.isChecked()) mMarital = 2;//Motahel
        else mMarital = 1;//Mojarad
        mData.GenderTypeID = (mGender);
        mData.MaritalStatusID = 1;
        mData.MilitaryStatusID = (mMarital);
        try {
            mData.ProvinceID = (Integer.parseInt(spinAdap_Ostan.getItemString(sniperOstan.getSelectedItemPosition(), "ProvinceID")));
            mData.CityID = (Integer.parseInt(spinAdap_City.getItemString(sniperCity.getSelectedItemPosition(), "CityID")));
        } catch (Exception ignored) {
        }
        mData.Address = (txtProfileAddressDetailsAddress.getText().toString());
        mData.PhotoFilename = lURLs.get(0);

        return mData;
    }
    private void Toast(String Message) {
        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
    }
    private void PicSelector() {
        if (askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            isGrant = askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            piDialog = new Dialog(context);
            piDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            piDialog.setCancelable(true);
            piDialog.setCanceledOnTouchOutside(true);
            piDialog.setContentView(R.layout.dialog_takepic);
            Objects.requireNonNull(piDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            TextView lblTakePhoto = piDialog.findViewById(R.id.lblTakePhoto);
            TextView lblChooseFromGallery = piDialog.findViewById(R.id.lblChooseFromGallery);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
            lblTakePhoto.setTypeface(tf);
            lblChooseFromGallery.setTypeface(tf);

            lblTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                    piDialog.dismiss();
                }
            });
            lblChooseFromGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                    piDialog.dismiss();
                }
            });
            piDialog.show();
        }
    }
    String ProfileImagePath = null;
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        ProfileImg = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        try {
                            Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                            Uri tempUri = getImageUri(getActivity(), photo);
                            ProfileImagePath = tempUri.getPath();

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            ProfileImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imgProfile.setImageDrawable(circularBitmapDrawable);
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
                        ProfileImagePath = getPath(getActivity(), pickedImage);
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor cursor = context.getContentResolver().query(pickedImage, filePath, null, null, null);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        ProfileImg = BitmapFactory.decodeFile(imagePath, options);

                        try {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            ProfileImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imgProfile.setImageDrawable(circularBitmapDrawable);
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
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    private String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }
    public static void lstRefresher() {
//        SchoolExprience();
//        WorkExprience();
//        SkillExprience();
    }
    private static void SchoolExprience() {
        try {
//            adapProfileSchool adapter = new adapProfileSchool(lEducationalExperience, contexti, 1);
//            lstProfileSchoolDetailsMaini.setHasFixedSize(true);
//            mLayoutManageri = new LinearLayoutManager(contexti);
//            lstProfileSchoolDetailsMaini.setLayoutManager(mLayoutManageri);
//            lstProfileSchoolDetailsMaini.addItemDecoration(new HorizontalDividerItemDecoration.Builder(contexti).colorResId(R.color.txtWhite).size(2).build());
//            lstProfileSchoolDetailsMaini.setItemAnimator(new DefaultItemAnimator());
//            lstProfileSchoolDetailsMaini.setAdapter(adapter);
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }
    private static void WorkExprience() {
        try {
//            adapProfileWork adapter = new adapProfileWork(lWorkExperience, contexti, 1);
//            lstProfileWorkDetailsMaini.setHasFixedSize(true);
//            mLayoutManageri = new LinearLayoutManager(contexti);
//            lstProfileWorkDetailsMaini.setLayoutManager(mLayoutManageri);
//            lstProfileWorkDetailsMaini.addItemDecoration(new HorizontalDividerItemDecoration.Builder(contexti).colorResId(R.color.txtWhite).size(2).build());
//            lstProfileWorkDetailsMaini.setItemAnimator(new DefaultItemAnimator());
//            lstProfileWorkDetailsMaini.setAdapter(adapter);
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }
    private static void SkillExprience() {
        try {
//            adapProfileSkill adapter = new adapProfileSkill(lSkill, contexti, 1);
//            lstProfileSkillDetailsMaini.setHasFixedSize(true);
//            mLayoutManageri = new LinearLayoutManager(contexti);
//            lstProfileSkillDetailsMaini.setLayoutManager(mLayoutManageri);
//            lstProfileSkillDetailsMaini.addItemDecoration(new HorizontalDividerItemDecoration.Builder(contexti).colorResId(R.color.txtWhite).size(2).build());
//            lstProfileSkillDetailsMaini.setItemAnimator(new DefaultItemAnimator());
//            lstProfileSkillDetailsMaini.setAdapter(adapter);
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }
    //    public static void onLongTouch_School(List<DataEducationalExperience> lList, final int position, final Context mCon) {
    public static void onLongTouch_School(List<Basic_citi> lList, final int position, final Context mCon) {
//        dEditor = new Dialog(contexti);
//        dEditor.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dEditor.setCancelable(true);
//        dEditor.setCanceledOnTouchOutside(true);
//        dEditor.setContentView(R.layout.dialog_editorer);
//        Objects.requireNonNull(dEditor.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//        TextView lblEdit = dEditor.findViewById(R.id.lblEdit);
//        TextView lblDelete = dEditor.findViewById(R.id.lblDelete);
//        Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
//        lblEdit.setTypeface(tf);
//        lblDelete.setTypeface(tf);
//
//        lblDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ynDialog == null) ynDialog = new Dialog(contexti);
//                ynDialog.setCancelable(false);
//                ynDialog.setContentView(R.layout.dialog_accept_cancell);
//                Objects.requireNonNull(ynDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                TextView lblNo = ynDialog.findViewById(R.id.lblCancell);
//                TextView lblYes = ynDialog.findViewById(R.id.lblAccept);
//                Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
//                lblNo.setTypeface(tf);
//                lblYes.setTypeface(tf);
//                lblYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        lEducationalExperience.remove(position);
////                        lstRefresher();
////                        ynDialog.dismiss();
//                    }
//                });
//                lblNo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ynDialog.dismiss();
//                    }
//                });
//                dEditor.dismiss();
//                ynDialog.show();
//            }
//        });
//        lblEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                actRecordsSchool.Type = 1;
////                actRecordsSchool.position = position;
////                Intent intenti = new Intent(mCon, actRecordsSchool.class);
////                mCon.startActivity(intenti);
////                dEditor.dismiss();
//            }
//        });
//        dEditor.show();
    }
    //    public static void onLongTouch_Work(List<DataWorkExperience> lList, final int position, final Context mCon) {
    public static void onLongTouch_Work(List<Basic_citi> lList, final int position, final Context mCon) {
//        Window window = dEditor.getWindow();
//        LayoutInflater inflater = (LayoutInflater) contexti.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        RelativeLayout lay = new RelativeLayout(contexti);
//        View dialogView = inflater.inflate(R.layout.dialog_editorer, lay);
//        dEditor.setContentView(dialogView);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        TextView lblEdit = dEditor.findViewById(R.id.lblEdit);
//        TextView lblDelete = dEditor.findViewById(R.id.lblDelete);
//        Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
//        lblEdit.setTypeface(tf);
//        lblDelete.setTypeface(tf);
//
//        lblDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ynDialog == null) ynDialog = new Dialog(contexti);
//                ynDialog.setCancelable(false);
//                ynDialog.setContentView(R.layout.dialog_accept_cancell);
//                Objects.requireNonNull(ynDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                TextView lblNo = ynDialog.findViewById(R.id.lblCancell);
//                TextView lblYes = ynDialog.findViewById(R.id.lblAccept);
//                lblYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        lWorkExperience.remove(position);
////                        lstRefresher();
////                        ynDialog.dismiss();
//                    }
//                });
//                lblNo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ynDialog.dismiss();
//                    }
//                });
//                dEditor.dismiss();
//                ynDialog.show();
//            }
//        });
//        lblEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                actRecordsWork.Type = 1;
////                actRecordsWork.position = position;
////                Intent intenti = new Intent(mCon, actRecordsWork.class);
////                mCon.startActivity(intenti);
////                dEditor.dismiss();
//            }
//        });
//        dEditor.show();
    }
    //    public static void onLongTouch_Skill(List<DataSkill> lList, final int position, final Context mCon) {
    public static void onLongTouch_Skill(List<Basic_citi> lList, final int position, final Context mCon) {
//        Window window = dEditor.getWindow();
//        LayoutInflater inflater = (LayoutInflater) contexti.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        RelativeLayout lay = new RelativeLayout(contexti);
//        View dialogView = inflater.inflate(R.layout.dialog_editorer, lay);
//        dEditor.setContentView(dialogView);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        TextView lblEdit = dEditor.findViewById(R.id.lblEdit);
//        TextView lblDelete = dEditor.findViewById(R.id.lblDelete);
//        Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
//        lblEdit.setTypeface(tf);
//        lblDelete.setTypeface(tf);
//
//        lblDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ynDialog == null) ynDialog = new Dialog(contexti);
//                ynDialog.setCancelable(false);
//                ynDialog.setContentView(R.layout.dialog_accept_cancell);
//                Objects.requireNonNull(ynDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                TextView lblNo = ynDialog.findViewById(R.id.lblCancell);
//                TextView lblYes = ynDialog.findViewById(R.id.lblAccept);
//                Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
//                lblNo.setTypeface(tf);
//                lblYes.setTypeface(tf);
//                lblYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        lSkill.remove(position);
//                        lstRefresher();
//                        ynDialog.dismiss();
//                    }
//                });
//                lblNo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ynDialog.dismiss();
//                    }
//                });
//                dEditor.dismiss();
//                ynDialog.show();
//            }
//        });
//        lblEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                actRecordsSkill.Type = 1;
////                actRecordsSkill.position = position;
////                Intent intenti = new Intent(mCon, actRecordsSkill.class);
////                mCon.startActivity(intenti);
//                dEditor.dismiss();
//            }
//        });
//        dEditor.show();
    }
    private void Schooler() {
//        if (lEducationalExperience.size() <= 6) {
//            actRecordsSchool.Type = 0;
//            Intent intent = new Intent(context, actRecordsSchool.class);
//            startActivity(intent);
//        } else Toast("بیشتر از 6 مورد نمیتوان اضافه کرد");
    }
    private void Worker() {
//        if (lWorkExperience.size() <= 6) {
//            actRecordsWork.Type = 0;
//            Intent intent = new Intent(context, actRecordsWork.class);
//            startActivity(intent);
//        } else Toast("بیشتر از 6 مورد نمیتوان اضافه کرد");
    }
    private void Skiller() {
//        if (lSkill.size() <= 6) {
//            actRecordsSkill.Type = 0;
//            Intent intent = new Intent(context, actRecordsSkill.class);
//            startActivity(intent);
//        } else Toast("بیشتر از 6 مورد نمیتوان اضافه کرد");
    }
    private void Finisher(){
        lEducationalExperience = new ArrayList<>();
        lSkill = new ArrayList<>();
        lWorkExperience = new ArrayList<>();
        lListOstan = new ArrayList<>();
        lListCity = new ArrayList<>();
        dMarketer = new Profile();
        Name = ""; Family = "";
        BirthDate = "";
        Nationality = "";
        About = "";
        cBirthDate = "0000-00-00T00:00:00";
        mAddress = "";
        OstanSpin = 0;
        CitySpin = 0;
        ProfileImg = null;
        isGrant = false;
        OstanFrist = false;
        isBirth = false;
        toEdite = true;
        mSY = 0;
        nSY = 0;
        ProfilePhoto = "";
        act.getFragByState(FragmentState.MainAccounts);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }
}
