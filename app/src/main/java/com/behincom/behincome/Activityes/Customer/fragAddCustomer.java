package com.behincom.behincome.Activityes.Customer;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.GPSTracker;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.Customer.adapStoreContact;
import com.behincom.behincome.Adapters.Customer.adapStorePic;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_citi;
import com.behincom.behincome.Datas.BaseData.Basic_ContactTypes;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.BaseData.Basic_PersonRoles;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_Ostan;
import com.behincom.behincome.Datas.BaseData.Basic_takGroups;
import com.behincom.behincome.Datas.BaseData.Basic_taks;
import com.behincom.behincome.Datas.Customer.CustomerActivityFields;
import com.behincom.behincome.Datas.Customer.CustomerPersonnel;
import com.behincom.behincome.Datas.Customer.CustomerProperties;
import com.behincom.behincome.Datas.Customer.CustomerTags;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.Customer.ToSend.AddCustomerToSend;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Customer.ToSend.AddCustomerToSendActivityFields;
import com.behincom.behincome.Datas.Customer.ToSend.AddCustomerToSendImages;
import com.behincom.behincome.Datas.Customer.ToSend.AddCustomerToSendPersonnels;
import com.behincom.behincome.Datas.Customer.ToSend.AddCustomerToSendProperties;
import com.behincom.behincome.Datas.Customer.ToSend.AddCustomerToSendTags;
import com.behincom.behincome.Datas.Customer.ToSend.EditCustomerToSend;
import com.behincom.behincome.Datas.Customer.ToSend.EditCustomerToSendActivityFields;
import com.behincom.behincome.Datas.Customer.ToSend.EditCustomerToSendImages;
import com.behincom.behincome.Datas.Customer.ToSend.EditCustomerToSendPersonnels;
import com.behincom.behincome.Datas.Customer.ToSend.EditCustomerToSendProperties;
import com.behincom.behincome.Datas.Customer.ToSend.EditCustomerToSendTags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.TagType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.devsmart.android.ui.HorizontalListView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static java.util.stream.Collectors.groupingBy;

public class fragAddCustomer extends Fragment {

    static Context contexti;
    actCustomer act = new actCustomer();
    RWInterface rInterface;
    private RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    Dialog mDialog, pDialog, lDialog, nyDialog, fDialog, cDialog;
    com.behincom.behincome.Accesories.Dialog lpDialog;
    static SpinAdapter spinAdapRole, spinAdapType;
    adapStorePic adapPic;
    GPSTracker gpsTracker;
    SpinAdapter spinAdap_Ostan, spinAdap_City, spinAdap_Prefix;

    //Elements
    TextView lblTitle, lblasdTitle, lblascTitle, lblaslTitle, lblTags, lblasddTitle, lblDetails, lblaspTitle, lblAccept, lblField, lblFieldTitle, lblProvince, lblCity;
    CardView cardView;
    ScrollView scrollV;
    LinearLayout btnGetAddressLocation;
    HorizontalListView lstPic;
    Spinner spinerOstan, spinerCity, spinadaPrefix;//spinadaActivityField
    EditText txtName, txtAddress;
    ImageView imgBack, btnAddContact, btnCheck;
    static RecyclerView lstCalling;
    static RecyclerView.LayoutManager mLayoutManageri;
    FloatingActionButton btnAddPic, btnGetLocation, btnGetBigMap;
    //    SupportMapFragment mMap;
    MapView mMap;
    GoogleMap GMap;

    //Lists
    private static List<Basic_Ostan> lProvinces = new ArrayList<>();
    private static List<Basic_citi> lCities = new ArrayList<>();
    public static List<String> Prefix = new ArrayList<>();
    public static List<String> ActivityField = new ArrayList<>();
    public static List<CustomerPersonnel> lContact = new ArrayList<>();
    public static List<Basic_Properties> lProperty = new ArrayList<>();
    public static List<Basic_taks> lTags = new ArrayList<>();
    public static List<Basic_taks> lTag = new ArrayList<>();
    public static List<Basic_ActivityFields> lActivityFields = new ArrayList<>();
    public static List<Basic_ActivityFields> lActivityField = new ArrayList<>();
    public static List<Basic_Properties> lProperties = new ArrayList<>();
    public static List<Basic_Properties> lPropertie = new ArrayList<>();

    //Variables
    static String Name = "", Address = "";
    static int Perfix = 0, Hoze = 0;
    int sOstan = 0, sCity = 0;
    List<String> lProfileImg = new ArrayList<>();
    boolean isGrant = false, isGrant_GPS = false;
    private static int Zamine = 0, Ostane = 0, Citye = 0;

    public static boolean mType = false;
    public static FragmentState FragStateCondition = FragmentState.BigMap;
    public static int StateId = 1;
    public static MyCustomers Customer = new MyCustomers();
    public static boolean OstanSelect = false;
    public static LatLng cPosition = new LatLng(1, 1);
    private boolean isTag = false, isDetail = false, isField = false;
    private boolean isAPI = false;
    public static boolean isMap = false, goingToBigMap = false;

    private static String sName = "", sAdrese = "";
    private static List<CustomerPersonnel> lPersoneler = new ArrayList<>();

    public static fragAddCustomer newInstance(Context mContext) {
        fragAddCustomer fragment = new fragAddCustomer();
        contexti = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_customer, container, false);

        mMap = view.findViewById(R.id.mMap);
        spinadaPrefix = view.findViewById(R.id.spinadaPrefix);
        scrollV = view.findViewById(R.id.scrollV);
        lblFieldTitle = view.findViewById(R.id.lblFieldTitle);
        lblField = view.findViewById(R.id.lblField);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblTitle = view.findViewById(R.id.lblTitle);
        btnGetAddressLocation = view.findViewById(R.id.btnGetAddressLocation);
        lstCalling = view.findViewById(R.id.lstCalling);
        btnAddContact = view.findViewById(R.id.btnAddContact);
        lblasdTitle = view.findViewById(R.id.lblasdTitle);
        lblascTitle = view.findViewById(R.id.lblascTitle);
        lblaslTitle = view.findViewById(R.id.lblTagTitle);
        lblTags = view.findViewById(R.id.lblTagList);
        lblasddTitle = view.findViewById(R.id.lblDetailTitle);
        lblDetails = view.findViewById(R.id.lblDetailList);
        lblaspTitle = view.findViewById(R.id.lblaspTitle);
        lblAccept = view.findViewById(R.id.lblAccept);
        btnGetBigMap = view.findViewById(R.id.btnGetBigMap);
        cardView = view.findViewById(R.id.cardView);
        lstPic = view.findViewById(R.id.lstPic);
        spinerOstan = view.findViewById(R.id.spinadaOstan);
        spinerCity = view.findViewById(R.id.spinadaCity);
        txtName = view.findViewById(R.id.txtasdCName);
        txtAddress = view.findViewById(R.id.txtadaAddress);
        imgBack = view.findViewById(R.id.imgBack);
        lblProvince = view.findViewById(R.id.lblProvince);
        lblCity = view.findViewById(R.id.lblCity);
        mLayoutManageri = new LinearLayoutManager(contexti);
        btnGetLocation = view.findViewById(R.id.btnGetLocation);
        btnAddPic = view.findViewById(R.id.btnAddPic);


        rInterface = Retrofite.getClient().create(RWInterface.class);
        if (!mType) {
            lblTitle.setText("اضافه کردن فروشگاه");
        } else {
            lblTitle.setText("ویرایش فروشگاه");
        }
        imgBack.setVisibility(View.VISIBLE);

        lContact = new ArrayList<>();
        gpsTracker = new GPSTracker(contexti);
        ActivityField = new ArrayList<>();
        scrollV.fullScroll(View.FOCUS_UP);

        lstCalling.setHasFixedSize(true);
        mLayoutManageri = new LinearLayoutManager(contexti);
        lstCalling.setLayoutManager(mLayoutManageri);
        lstCalling.addItemDecoration(ItemDecoration.getDecoration(contexti));
        lstCalling.setItemAnimator(new DefaultItemAnimator());

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sName = txtName.getText().toString();
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
                sAdrese = txtAddress.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                act.getFragByState(FragStateCondition);
                getActivity().finish();
            }
        });
//        if (savedInstanceState == null) {
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mMap, new Fragment()).commit();
//        }
        try {
            ;
            mMap.onCreate(savedInstanceState);
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        mMap.onResume();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GMap = googleMap;
                googleMap.setMyLocationEnabled(true);
                GMap.getUiSettings().setScrollGesturesEnabled(false);

                GMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Toast.makeText(contexti, "ASD", Toast.LENGTH_SHORT).show();
                    }
                });

                LatLng MainCameraPoint = new LatLng(35.827339, 50.959113);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(MainCameraPoint).zoom(12).build();
                GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        btnGetAddressLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    txtAddress.setText(gpsTracker.getAddressLine(contexti));
                } else {
                    gpsTracker.showSettingsAlert();
                }
            }
        });
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (gpsTracker.getIsGPSTrackingEnabled()) {
                        LatLng MainCameraPoint = new LatLng(gpsTracker.latitude, gpsTracker.longitude);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(MainCameraPoint).zoom(15).build();
                        GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
            }
        });
        btnGetBigMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goingToBigMap = true;
                act.getFragByState(FragmentState.BigMap);
            }
        });

        SQL.Execute("DELETE FROM mBasic_ActivityFields");
        SQL.Execute("DELETE FROM mBasic_Tags");
        SQL.Execute("DELETE FROM mBasic_Properties");

        List<Basic_NamePrefixes> lPrefix = geter.getList(Basic_NamePrefixes.class);
        spinAdap_Prefix = new SpinAdapter(contexti, lPrefix, "NamePrefixTitle");
        spinadaPrefix.setAdapter(spinAdap_Prefix);
        spinadaPrefix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Perfix = spinadaPrefix.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinerOstan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sOstan = position;
                if (OstanSelect) {
                    OstanSelect = false;
                    try {
                        spinerCity.setSelection(spinAdap_City.getItemPosition("CityID", Integer.toString(Customer.Customers.CityID)));

                        lCities = geter.getList(Basic_citi.class, "WHERE ProvinceID='" + spinAdap_Ostan.getItemString(spinerOstan.getSelectedItemPosition(), "ProvinceID") + "' AND isCheck='1'");
                        if (lCities.size() > 1) {
                            Basic_citi dCity = new Basic_citi();
                            dCity.CityTitle = ("شهر را انتخاب کنید");
                            lCities.add(0, dCity);
                            spinAdap_City = new SpinAdapter(contexti, lCities, "CityTitle");
                            spinerCity.setAdapter(spinAdap_City);
                            spinerCity.setVisibility(View.VISIBLE);
                            lblCity.setVisibility(View.GONE);
                        } else {
                            spinerCity.setVisibility(View.GONE);
                            lblCity.setVisibility(View.VISIBLE);
                            lblCity.setText(lCities.get(0).CityTitle);
                        }

                        spinerCity.setSelection(spinAdap_City.getItemPosition("CityID", Integer.toString(Customer.Customers.CityID)));
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                } else {
                    if (spinerOstan.getSelectedItemPosition() > 0) {
                        try {
                            String hhh = spinAdap_Ostan.getItemString(spinerOstan.getSelectedItemPosition(), "ProvinceID");
                            lCities = geter.getList(Basic_citi.class, "WHERE ProvinceID='" + hhh + "' AND isCheck='1'");
                            if (lCities.size() > 1) {
                                Basic_citi dCity = new Basic_citi();
                                dCity.CityTitle = ("شهر را انتخاب کنید");
                                lCities.add(0, dCity);
                                spinAdap_City = new SpinAdapter(contexti, lCities, "CityTitle");
                                spinerCity.setAdapter(spinAdap_City);
                                spinerCity.setVisibility(View.VISIBLE);
                                lblCity.setVisibility(View.GONE);
                            } else {
                                spinerCity.setVisibility(View.GONE);
                                lblCity.setVisibility(View.VISIBLE);
                                lblCity.setText(lCities.get(0).CityTitle);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            lCities = new ArrayList<>();
                            if (lCities.size() > 1) {
                                Basic_citi dCity = new Basic_citi();
                                dCity.CityTitle = ("شهر را انتخاب کنید");
                                lCities.add(dCity);
                                spinAdap_City = new SpinAdapter(contexti, lCities, "CityTitle");
                                spinerCity.setAdapter(spinAdap_City);
                                spinerCity.setVisibility(View.VISIBLE);
                                lblCity.setVisibility(View.GONE);
                            } else {
                                spinerCity.setVisibility(View.GONE);
                                lblCity.setVisibility(View.VISIBLE);
                                lblCity.setText(lCities.get(0).CityTitle);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lblField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityFieldManager();
            }
        });
        lblTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagManager();
            }
        });//Lable - Add Edit
        lblDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertiesManager();
            }
        });//Details Adder Or Editer
        btnAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicManager();
            }
        });//Pic Adder (AddOnly)
        spinadaPrefix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Perfix = spinadaPrefix.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newContact();
            }
        });
        lstPic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                nyDialog = new Dialog(contexti);
                nyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nyDialog.setCancelable(true);
                nyDialog.setCanceledOnTouchOutside(true);
                nyDialog.setContentView(R.layout.dialog_accept_cancell);
                Objects.requireNonNull(nyDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView lblYes = nyDialog.findViewById(R.id.lblAccept);
                TextView lblNo = nyDialog.findViewById(R.id.lblCancell);
                TextView lblTitle = nyDialog.findViewById(R.id.lblTitle);

                Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
                lblYes.setTypeface(tf);
                lblNo.setTypeface(tf);
                lblTitle.setTypeface(tf);

                lblTitle.setText("آیا مایل به ادامه حذف هستید ؟");

                lblYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lProfileImg.remove(position);
                        adapPic = new adapStorePic(contexti, lProfileImg);
                        lstPic.setAdapter(adapPic);
                        nyDialog.dismiss();
                    }
                });
                lblNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nyDialog.dismiss();
                    }
                });

                nyDialog.show();
                return false;
            }
        });

        lpDialog = new com.behincom.behincome.Accesories.Dialog(contexti);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinerOstan.getSelectedItemPosition() > 0 && lActivityFields.size() > 0 && txtName.getText().length() > 0) {
                    if (!mType) {
                        lpDialog.Show();

                        final RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);

                        if(lProfileImg.size() > 0) {
                            MultipartBody.Part[] body = new MultipartBody.Part[lProfileImg.size()];
                            for (int i = 0; i < lProfileImg.size(); i++) {
                                File file = new File(lProfileImg.get(i));
                                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                                body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                            }

                            Call<SimpleResponse> call4 = rInterface.RQAddCustomerPic(Setting.getToken(), body);
                            call4.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    try {
                                        if (response.isSuccessful()) {
                                            List<String> lURLs = new ArrayList<>();
                                            try {
                                                Object[] keys = response.body().AdditionalData.keySet().toArray();
                                                for (Object data : keys) {
                                                    String val = response.body().AdditionalData.get(data.toString()).toString();
                                                    lURLs.add(val);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            Map<String, Object> mCustomer = new HashMap<>();
                                            ObjectMapper oMapper = new ObjectMapper();
                                            mCustomer = oMapper.convertValue(getrCustomerDataToAdd(lURLs), Map.class);

                                            Call<SimpleResponse> AddCustomer = rInterface.RQAddCustomer(Setting.getToken(), new HashMap<>(mCustomer));
                                            AddCustomer.enqueue(new Callback<SimpleResponse>() {
                                                @Override
                                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                                    if (response.isSuccessful()) {
                                                        Intent intent = new Intent(contexti, actMain.class);
                                                        actMain.STATE = FragmentState.MainCustomers;
                                                        contexti.startActivity(intent);
                                                        try {
                                                            getActivity().finish();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                            ((Activity) contexti).finish();
                                                        }
                                                    }
                                                    lpDialog.DisMiss();
                                                }

                                                @Override
                                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                                    lpDialog.DisMiss();
                                                }
                                            });
                                        }
                                        lpDialog.DisMiss();
                                    } catch (Exception Ex) {
                                        String Er = Ex.getMessage();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    lpDialog.DisMiss();
                                }
                            });
                        }else {
                            Map<String, Object> mCustomer = new HashMap<>();
                            ObjectMapper oMapper = new ObjectMapper();
                            mCustomer = oMapper.convertValue(getrCustomerDataToAdd(new ArrayList<String>()), Map.class);

                            Call<SimpleResponse> AddCustomer = rInterface.RQAddCustomer(Setting.getToken(), new HashMap<>(mCustomer));
                            AddCustomer.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if (response.isSuccessful()) {
                                        Intent intent = new Intent(contexti, actMain.class);
                                        actMain.STATE = FragmentState.MainCustomers;
                                        contexti.startActivity(intent);
                                        try {
                                            getActivity().finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ((Activity) contexti).finish();
                                        }
                                    }
                                    lpDialog.DisMiss();
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    lpDialog.DisMiss();
                                }
                            });
                        }
                    } else {
                        lpDialog.Show();

                        final RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);

                        if(lProfileImg.size() > 0) {
                            MultipartBody.Part[] body = new MultipartBody.Part[lProfileImg.size()];
                            for (int i = 0; i < lProfileImg.size(); i++) {
                                File file = new File(lProfileImg.get(i));
                                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                                body[i] = MultipartBody.Part.createFormData("image" + i, file.getName(), surveyBody);
                            }

                            Call<SimpleResponse> call4 = rInterface.RQAddCustomerPic(Setting.getToken(), body);
                            call4.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    try {
                                        if (response.isSuccessful()) {
                                            List<String> lURLs = new ArrayList<>();
                                            try {
                                                Object[] keys = response.body().AdditionalData.keySet().toArray();
                                                for (Object data : keys) {
                                                    String val = response.body().AdditionalData.get(data.toString()).toString();
                                                    lURLs.add(val);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            Map<String, Object> mCustomer = new HashMap<>();
                                            ObjectMapper oMapper = new ObjectMapper();
                                            mCustomer = oMapper.convertValue(getrCustomerDataToEdit(lURLs), Map.class);

                                            Call<SimpleResponse> EditCustomer = rInterface.RQEditCustomer(Setting.getToken(), new HashMap<>(mCustomer));
                                            EditCustomer.enqueue(new Callback<SimpleResponse>() {
                                                @Override
                                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                                    if (response.isSuccessful()) {
                                                        Intent intent = new Intent(contexti, actMain.class);
                                                        actMain.STATE = FragmentState.MainCustomers;
                                                        contexti.startActivity(intent);
                                                        getActivity().finish();
                                                    }
                                                    lpDialog.DisMiss();
                                                }

                                                @Override
                                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                                    lpDialog.DisMiss();
                                                }
                                            });
                                        }
                                        lpDialog.DisMiss();
                                    } catch (Exception Ex) {
                                        String Er = Ex.getMessage();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    lpDialog.DisMiss();
                                }
                            });
                        }else {
                            Map<String, Object> mCustomer = new HashMap<>();
                            ObjectMapper oMapper = new ObjectMapper();
                            mCustomer = oMapper.convertValue(getrCustomerDataToEdit(new ArrayList<String>()), Map.class);

                            Call<SimpleResponse> EditCustomer = rInterface.RQEditCustomer(Setting.getToken(), new HashMap<>(mCustomer));
                            EditCustomer.enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if (response.isSuccessful()) {
                                        Intent intent = new Intent(contexti, actMain.class);
                                        actMain.STATE = FragmentState.MainCustomers;
                                        contexti.startActivity(intent);
                                        getActivity().finish();
                                    }
                                    lpDialog.DisMiss();
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    lpDialog.DisMiss();
                                }
                            });
                        }
                    }
                    getActivity().finish();
                } else {
                    Toast.makeText(contexti, "لطفا همه اطلاعات را پر کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
    private AddCustomerToSend getrCustomerDataToAdd(List<String> PhotoURL) {
        final AddCustomerToSend data = new AddCustomerToSend();
        try {
            data.BusinessManagerMarketerID = (Setting.getBMMUserID());
            data.CustomerAddedByUserID = (Setting.getUserID());
            data.CustomerOwenerUserID = (Setting.getUserID());
            data.NamePrefixID = (Integer.parseInt(spinAdap_Prefix.getItemString(spinadaPrefix.getSelectedItemPosition(), "NamePrefixID")));
            data.CustomerName = (txtName.getText().toString());
            if (lCities.size() > 1) {
                data.CityID = (Integer.parseInt(spinAdap_City.getItemString(spinerCity.getSelectedItemPosition(), "CityID")));
            } else {
                data.CityID = lCities.get(0).CityID;
            }
            data.CustomerAddress = (txtAddress.getText().toString());
            data.CustomerLatitude = (cPosition.latitude);
            data.CustomerLongitude = (cPosition.longitude);
            data.CustomerStateID = (StateId);
            data.CustomerStatusID = 1;
            AddCustomerToSendImages iData;
            for (String dat : PhotoURL) {
                iData = new AddCustomerToSendImages();
                iData.ImageFilename = dat;

                data.Customers_Images.add(iData);
            }
            data.CustomerDescription = ("AASD");//TODO Description nadare ???

            List<AddCustomerToSendTags> lTagToSend = new ArrayList<>();
            for (Basic_taks datas : lTags) {
                AddCustomerToSendTags mData = new AddCustomerToSendTags();
                mData.TagID = (datas.TagID);

                lTagToSend.add(mData);
            }
            data.Customers_Tags = (lTagToSend);

            List<AddCustomerToSendProperties> lDetail = new ArrayList<>();
            for (Basic_Properties datas : lProperties) {
                AddCustomerToSendProperties mData = new AddCustomerToSendProperties();
                mData.Value = (datas.PropertyDescription);
                mData.PropertyID = (datas.PropertyID);

                lDetail.add(mData);
            }
            data.Customers_Properties = (lDetail);

            List<AddCustomerToSendPersonnels> lPersonel = new ArrayList<>();
            for (int j = 0; j < lContact.size(); j++) {
                for (int i = 0; i < lContact.size(); i++) {
                    if (lContact.get(i).Name.length() == 0 || lContact.get(i).ContactInfo.length() == 0)
                        lContact.remove(i);
                }
            }
            for (CustomerPersonnel dataa : lContact) {
                AddCustomerToSendPersonnels mData = new AddCustomerToSendPersonnels();
                mData.ContactInfo = (dataa.ContactInfo);
                mData.ContactTypeID = (dataa.ContactTypeID);
                mData.Name = (dataa.Name);
                mData.PersonnelRoleID = (dataa.PersonnelRoleID);
                mData.PersonnelRoleName = (dataa.PersonnelRoleName);

                lPersonel.add(mData);
            }
            data.Customers_Personnel = (lPersonel);

            List<AddCustomerToSendActivityFields> lActField = new ArrayList<>();
            for (Basic_ActivityFields fData : lActivityFields) {
                AddCustomerToSendActivityFields aData = new AddCustomerToSendActivityFields();
                aData.ActivityFieldID = (fData.ActivityFieldID);

                lActField.add(aData);
            }
            data.Customers_ActivityFields = (lActField);
        } catch (Exception Ex) {
            String Er = Ex.getLocalizedMessage();
        }
        return data;
    }
    private EditCustomerToSend getrCustomerDataToEdit(List<String> PhotoURL) {
        EditCustomerToSend data = new EditCustomerToSend();
        data.NamePrefixID = (Integer.parseInt(spinAdap_Prefix.getItemString(spinadaPrefix.getSelectedItemPosition(), "NamePrefixID")));
        data.CustomerName = (txtName.getText().toString());
        data.CustomerID = (Customer.Customers.CustomerID);
        data.CityID = (Integer.parseInt(spinAdap_City.getItemString(spinerCity.getSelectedItemPosition(), "CityID")));
        data.CustomerAddress = (txtAddress.getText().toString());
        data.CustomerLatitude = (cPosition.latitude);
        data.CustomerLongitude = (cPosition.longitude);
        data.CustomerStateID = (StateId);
        EditCustomerToSendImages iData;
        for (String dat : PhotoURL) {
            iData = new EditCustomerToSendImages();
            iData.ImageFilename = dat;

            data.Customers_Images.add(iData);
        }
        data.CustomerDescription = ("AASD");//TODO Description nadare ???

        List<EditCustomerToSendTags> lTagToSend = new ArrayList<>();
        for (Basic_taks datas : lTags) {
            EditCustomerToSendTags mData = new EditCustomerToSendTags();
            mData.TagID = (datas.TagID);

            lTagToSend.add(mData);
        }
        data.Customers_Tags = (lTagToSend);

        List<EditCustomerToSendProperties> lDetail = new ArrayList<>();
        for (Basic_Properties datas : lProperties) {
            EditCustomerToSendProperties mData = new EditCustomerToSendProperties();
            mData.Value = (datas.PropertyDescription);
            mData.PropertyID = (datas.PropertyID);

            lDetail.add(mData);
        }
        data.Customers_Properties = (lDetail);

        List<EditCustomerToSendPersonnels> lPersonel = new ArrayList<>();
        for (CustomerPersonnel mData : lContact) {
            for (int i = 0; i < lContact.size(); i++) {
                if (lContact.get(i).Name.length() == 0 || lContact.get(i).ContactInfo.length() == 0)
                    lContact.remove(i);
            }
        }
        for (CustomerPersonnel dataa : lContact) {
            EditCustomerToSendPersonnels mData = new EditCustomerToSendPersonnels();
            mData.ContactInfo = (dataa.ContactInfo);
            mData.ContactTypeID = (dataa.ContactTypeID);
            mData.Name = (dataa.Name);
            mData.PersonnelRoleID = (dataa.PersonnelRoleID);
            mData.PersonnelRoleName = (dataa.PersonnelRoleName);

            lPersonel.add(mData);
        }
        data.Customers_Personnel = (lPersonel);

        List<EditCustomerToSendActivityFields> lActField = new ArrayList<>();
        for (Basic_ActivityFields fData : lActivityFields) {
            EditCustomerToSendActivityFields aData = new EditCustomerToSendActivityFields();
            aData.ActivityFieldID = (fData.ActivityFieldID);

            lActField.add(aData);
        }
        data.Customers_ActivityFields = (lActField);
        //Customer Image ham ke joda joda ersal mishe .

        return data;
    }
    private boolean askForPermission(String permission, int rCode) {
        if (ContextCompat.checkSelfPermission(contexti, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity) contexti), permission)) {
                ActivityCompat.requestPermissions(((Activity) contexti), new String[]{permission}, rCode);
                return false;
            } else {
                ActivityCompat.requestPermissions(((Activity) contexti), new String[]{permission}, rCode);
                return false;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (isMap) {
            isMap = false;
            scrollV.fullScroll(View.FOCUS_DOWN);
        }
        if (mMap != null) {
            mMap.onResume();
        }

        if (cPosition.longitude > 2 && cPosition.longitude > 2) {
            try {
                mMap.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        GMap = googleMap;
                        googleMap.setMyLocationEnabled(true);
                        GMap.getUiSettings().setScrollGesturesEnabled(false);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(cPosition).zoom(18).build();
                        GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        lCities = new ArrayList<>();
        lProvinces = new ArrayList<>();

        lCities = geter.getListIsCheck(Basic_citi.class);
        for (Basic_citi data : lCities) {
            List<Basic_Ostan> lProv = geter.getList(Basic_Ostan.class, "WHERE ProvinceID='" + data.ProvinceID + "'");
            if (lProv.size() > 0) lProvinces.add(lProv.get(0));
        }
//        for(int k=0; k<lProvinces.size(); k++) {
        try {
            for (int i = 0; i < lProvinces.size(); i++) {
                try {
                    for (int j = i + 1; j < lProvinces.size(); j++) {
                        try {
                            if (lProvinces.get(i).ProvinceID == lProvinces.get(j).ProvinceID) {
                                lProvinces.remove(j);
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
//        }
        if (lProvinces.size() > 1) {
            Basic_Ostan dOstan = new Basic_Ostan();
            dOstan.ProvinceTitle = ("استان را انتخاب کنید");
            lProvinces.add(0, dOstan);
            spinerOstan.setVisibility(View.VISIBLE);
            lblProvince.setVisibility(View.GONE);
        } else {
            lblProvince.setText(lProvinces.get(0).ProvinceTitle);
            spinerOstan.setVisibility(View.GONE);
            lblProvince.setVisibility(View.VISIBLE);
        }
        if (lCities.size() > 1) {
            Basic_citi dCity = new Basic_citi();
            dCity.CityTitle = ("شهر را انتخاب کنید");
            lCities.add(0, dCity);
            spinerCity.setVisibility(View.VISIBLE);
            lblCity.setVisibility(View.GONE);
        } else {
            lblCity.setText(lCities.get(0).CityTitle);
            spinerCity.setVisibility(View.GONE);
            lblCity.setVisibility(View.VISIBLE);
        }

        spinAdap_Ostan = new SpinAdapter(contexti, lProvinces, "ProvinceTitle");
        spinAdap_City = new SpinAdapter(contexti, lCities, "CityTitle");
        spinerOstan.setAdapter(spinAdap_Ostan);
        spinerCity.setAdapter(spinAdap_City);

        if (lProvinces.size() > 0) spinerOstan.setSelection(sOstan);
        if (lCities.size() > 0) spinerCity.setSelection(sCity);
        spinadaPrefix.setSelection(Perfix);
        if(lPersoneler.size() > 0){
            lContact = new ArrayList<>();
            lContact.addAll(lPersoneler);
        }
        ContactRefresher();
        ContactRefresher2();
        txtName.setText(sName);
        txtAddress.setText(sAdrese);
        try {
            spinerOstan.setSelection(sOstan);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            spinerCity.setSelection(sCity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isGrant = askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 3);
        isGrant_GPS = askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, 2);
        ContactRefresher2();

        if (mType) {
            txtName.setText(Customer.Customers.CustomerName);
            try {
                spinadaPrefix.setSelection(spinAdap_Prefix.getItemPosition("NamePrefixID", Integer.toString(Customer.Customers.NamePrefixID)));
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            lContact = new ArrayList<>();
            lContact.addAll(Customer.Customers.Customers_Personnel);
            ContactRefresher2();
            txtAddress.setText(Customer.Customers.CustomerAddress);
            String lblActivityField = "";
            try {
                if (Customer.Customers.Customers_ActivityFields.size() > 0) {
                    isField = true;
                    for (CustomerActivityFields tData : Customer.Customers.Customers_ActivityFields) {
//                        Basic_ActivityFields fField = new Basic_ActivityFields();
//                        fField.ActivityFieldID = (tData.ActivityFieldID);
                        List<Basic_ActivityFields> pList = geter.getList(Basic_ActivityFields.class, " WHERE ActivityFieldID='" + tData.ActivityFieldID + "'");
                        if(pList.size() > 0)
                            lActivityFields.add(pList.get(0));
                    }
                }
                try {
//                    List<CustomerActivityFields> lField = Customer.Customers_ActivityFields;
//                    for (int i = 0; i < lField.size(); i++) {
//                        List<Basic_ActivityFields> lFields = geter.getList(Basic_ActivityFields.class, "WHERE ActivityFieldID='" + lField.get(i).ActivityFieldID + "'");
//                        aFields += lFields.get(0).ActivityFieldTitle + "<br>";
//                    }
//                    aFields = aFields.substring(0, aFields.length() - 4);
                    for (Basic_ActivityFields data : lActivityFields) {
                        lblActivityField += "☑ - " + data.ActivityFieldTitle + "<br>";
                    }
                    lblField.setText(Html.fromHtml(lblActivityField));
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                if (lblActivityField.length() > 0) lblField.setText(Html.fromHtml(lblActivityField));
                else lblField.setText("برای اضافه کردن اینجا را لمس کنید.");
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            String lblTager = "";
            if (Customer.Customers.Customers_Tags.size() > 0) {
                isTag = true;
                lTags = new ArrayList<>();
                for (CustomerTags tData : Customer.Customers.Customers_Tags) {
//                    Basic_taks tTag = new Basic_taks();
//                    tTag.TagID = (tData.TagID);
                    List<Basic_taks> pList = geter.getList(Basic_taks.class, " WHERE TagID='" + tData.TagID + "'");
                    if(pList.size() > 0)
                        lTags.add(pList.get(0));
                }
            }
            try {
                for (Basic_taks data : lTags) {
                    List<Basic_takGroups> lGroup = geter.getList(Basic_takGroups.class, " WHERE TagGroupID='" + data.TagGroupID + "'");
                    if(lGroup.get(0).TagGroupTypeId == TagType.CheckBox)
                        lblTager += "☑ - " + data.TagTitle + "<br>";
                    else
                        lblTager += "◉ - " + data.TagTitle + "<br>";
                }
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            if (lblTager.length() > 0) lblTags.setText(Html.fromHtml(lblTager));
            else lblTags.setText("برای اضافه کردن اینجا را لمس کنید.");
            if (Customer.Customers.Customers_Properties.size() > 0) {
                isDetail = true;
                lProperties = new ArrayList<>();
                for (CustomerProperties tData : Customer.Customers.Customers_Properties) {
//                    Basic_Properties tDetail = new Basic_Properties();
//                    tDetail.PropertyID = (tData.PropertyID);
//                    tDetail.PropertyDescription = (tData.Value);
                    List<Basic_Properties> pList = geter.getList(Basic_Properties.class, " WHERE PropertyID='" + tData.PropertyID + "'");
                    if(pList.size() > 0) {
                        Basic_Properties dat = new Basic_Properties();
                        dat.PropertyID = pList.get(0).PropertyID;
                        dat.PropertyDescription = tData.Value;
                        dat.Deleted = pList.get(0).Deleted;
                        dat.isCheck = pList.get(0).isCheck;
                        dat.PropertyGroupID = pList.get(0).PropertyGroupID;
                        dat.PropertyOrder = pList.get(0).PropertyOrder;
                        dat.PropertyTitle = pList.get(0).PropertyTitle;
                        dat.PropertyTypeKeyBoardId = pList.get(0).PropertyTypeKeyBoardId;

                        lProperties.add(dat);
                    }
                }
            }
//            List<CustomerProperties> lDetails = Customer.Customers_Properties;
            String lblPropertier = "";
            try {
//                for (int i = 0; i < lDetails.size(); i++) {
//                    List<Basic_Properties> lDetail = geter.getList(Basic_Properties.class, "WHERE PropertyID='" + lDetails.get(i).PropertyID + "'");
//                    Details += lDetail.get(0).PropertyTitle + " : " + lDetails.get(i).Value + "<br>";
//                }
//                if (lDetails.size() > 1) Details = Details.substring(0, Details.length() - 1);
                for (Basic_Properties data : lProperties) {
                    lblPropertier += "☑ - " + data.PropertyTitle + " : " + data.PropertyDescription + "<br>";
                }
                lblDetails.setText(Html.fromHtml(lblPropertier));
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            if (lblPropertier.length() > 0) lblDetails.setText(Html.fromHtml(lblPropertier));
            else lblDetails.setText("برای اضافه کردن اینجا را لمس کنید.");
            try {
                List<Basic_citi> lCit = geter.getList(Basic_citi.class, "WHERE CityID='" + Customer.Customers.CityID + "'");
                List<Basic_Ostan> lProv = geter.getList(Basic_Ostan.class, "WHERE ProvinceID='" + lCit.get(0).ProvinceID + "'");
                spinerOstan.setSelection(spinAdap_Ostan.getItemPosition("ProvinceID", Integer.toString(lProv.get(0).ProvinceID)));
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }

        }else{
            String lblPropertier = "";
            try {
                for (Basic_Properties data : lProperties) {
                    lblPropertier += "☑ - " + data.PropertyTitle + " : " + data.PropertyDescription + "<br>";
                }
                lblDetails.setText(Html.fromHtml(lblPropertier));
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            if (lblPropertier.length() > 0) lblDetails.setText(Html.fromHtml(lblPropertier.substring(0, lblPropertier.length() - 4)));
            else lblDetails.setText("برای اضافه کردن اینجا را لمس کنید.");

            String lblTager = "";
            try {
                for (Basic_taks data : lTags) {
                    List<Basic_takGroups> lGroup = geter.getList(Basic_takGroups.class, " WHERE TagGroupID='" + data.TagGroupID + "'");
                    if(lGroup.get(0).TagGroupTypeId == TagType.CheckBox)
                        lblTager += "☑ - " + data.TagTitle + "<br>";
                    else
                        lblTager += "◉ - " + data.TagTitle + "<br>";
                }
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            if (lblTager.length() > 0) lblTags.setText(Html.fromHtml(lblTager.substring(0, lblTager.length() - 4)));
            else lblTags.setText("برای اضافه کردن اینجا را لمس کنید.");

            String lblActivityField = "";
            try {
                for (Basic_ActivityFields data : lActivityFields) {
                    lblActivityField += "☑ - " + data.ActivityFieldTitle + "<br>";
                }
                lblField.setText(Html.fromHtml(lblActivityField.substring(0, lblActivityField.length() - 4)));
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
            if (lblActivityField.length() > 0) lblField.setText(Html.fromHtml(lblActivityField));
            else lblField.setText("برای اضافه کردن اینجا را لمس کنید.");
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        Uri tempUri = getImageUri(getActivity(), photo);
                        File finalFile = new File(getRealPathFromURI(tempUri));

                        lProfileImg.add(finalFile.getPath());
                        adapPic = new adapStorePic(contexti, lProfileImg);
                        lstPic.setAdapter(adapPic);
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri pickedImage = imageReturnedIntent.getData();
                        String fPath = getPath(getActivity(), pickedImage);
                        lProfileImg.add(fPath);
//                        Uri pickedImage = imageReturnedIntent.getData();
//                        String[] filePath = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = contexti.getContentResolver().query(pickedImage, filePath, null, null, null);
//                        cursor.moveToFirst();
//                        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
//
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                        lProfileImg.add(BitmapFactory.decodeFile(imagePath, options));
                        adapPic = new adapStorePic(contexti, lProfileImg);
                        lstPic.setAdapter(adapPic);
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                break;
        }
        scrollV.fullScroll(View.FOCUS_DOWN);
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
    @SuppressLint("ClickableViewAccessibility")
    private void newContact() {
        if (lContact.size() <= 10) {
            cDialog = new Dialog(contexti);
            cDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            cDialog.setCancelable(true);
            cDialog.setCanceledOnTouchOutside(true);
            cDialog.setContentView(R.layout.dialog_add_contact);
            Objects.requireNonNull(cDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

            TextView lblAccept = cDialog.findViewById(R.id.lblAccept);
            TextView lblCancell = cDialog.findViewById(R.id.lblCancell);
            final EditText txtName = cDialog.findViewById(R.id.txtName);
            final EditText txtInfo = cDialog.findViewById(R.id.txtInfo);
            final EditText txtRole = cDialog.findViewById(R.id.txtRole);
            final EditText txtType = cDialog.findViewById(R.id.txtType);
            final Spinner spinRole = cDialog.findViewById(R.id.spinRole);
            final Spinner spinType = cDialog.findViewById(R.id.spinType);

//            Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
//            txtName.setTypeface(tf);
//            txtInfo.setTypeface(tf);

            List<Basic_PersonRoles> lPersonRoles = new ArrayList<>();
            List<Basic_ContactTypes> lContactType = new ArrayList<>();
            lPersonRoles = geter.getList(Basic_PersonRoles.class);
            lContactType = geter.getList(Basic_ContactTypes.class);
            spinAdapRole = new SpinAdapter(contexti, lPersonRoles, "PersonRoleTitle");
            spinAdapType = new SpinAdapter(contexti, lContactType, "ContactTypeTitle");
            spinRole.setAdapter(spinAdapRole);
            spinType.setAdapter(spinAdapType);

            txtInfo.setInputType(getType(1));
            txtInfo.setSelection(txtInfo.getText().length());

            spinRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int order = Integer.parseInt(spinAdapRole.getItemString(spinRole.getSelectedItemPosition(), "PersonRoleOrder"));
                    if (order == 999) {
                        spinRole.setVisibility(View.GONE);
                        txtRole.setVisibility(View.VISIBLE);
                        txtRole.requestFocus();
                        txtRole.setSelection(0);
                        try {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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
            spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int order = Integer.parseInt(spinAdapType.getItemString(spinType.getSelectedItemPosition(), "ContactTypeOrder"));
                    if (order == 999) {
                        spinType.setVisibility(View.GONE);
                        txtType.setVisibility(View.VISIBLE);
                        txtType.requestFocus();
                        txtType.setSelection(0);
                        try {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                    txtInfo.setInputType(getType(Integer.parseInt(spinAdapType.getItemString(spinType.getSelectedItemPosition(), "AndroidKeyboardTypeID"))));
                    txtInfo.setSelection(txtInfo.getText().length());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            txtRole.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtRole.setVisibility(View.GONE);
                    spinRole.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            txtType.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    txtType.setVisibility(View.GONE);
                    spinType.setVisibility(View.VISIBLE);
                    return false;
                }
            });

            lblAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtName.getText().toString().length() > 0 && txtInfo.getText().toString().length() > 0) {
                        int ContactTypeID = Integer.parseInt(spinAdapType.getItemString(spinType.getSelectedItemPosition(), "ContactTypeID"));
                        int PersonnelRoleID = Integer.parseInt(spinAdapRole.getItemString(spinRole.getSelectedItemPosition(), "PersonRoleID"));
                        String PersonnelRoleName = spinAdapRole.getItemString(spinRole.getSelectedItemPosition(), "PersonRoleTitle");

                        CustomerPersonnel data = new CustomerPersonnel();
                        data.ContactInfo = (txtInfo.getText().toString());
                        data.Name = (txtName.getText().toString());
                        data.ContactTypeID = (ContactTypeID);
                        data.PersonnelRoleID = (PersonnelRoleID);
                        data.PersonnelRoleName = (PersonnelRoleName);

                        lContact.add(data);
                        lPersoneler.add(data);
//                Toast.makeText(contexti, "ذخیره شد", Toast.LENGTH_SHORT).show();
                        cDialog.dismiss();
                        ContactRefresher();
                    } else
                        Toast.makeText(contexti, "مقادیر نباید خالی باشند.", Toast.LENGTH_SHORT).show();
                }
            });
            lblCancell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cDialog.dismiss();
                }
            });
            cDialog.show();
        } else
            Toast.makeText(contexti, "نمیتوان بیشتر از 10 مورد اضافه کرد", Toast.LENGTH_SHORT).show();

    }
    private int getType(int AndroidKey) {
        switch (AndroidKey) {
            case 1:
                return InputType.TYPE_CLASS_TEXT;
            case 2:
                return InputType.TYPE_CLASS_NUMBER;
            case 3:
                return InputType.TYPE_CLASS_DATETIME;
            case 4:
                return InputType.TYPE_CLASS_DATETIME;
            case 5:
                return InputType.TYPE_NUMBER_FLAG_DECIMAL;
            case 6:
                return InputType.TYPE_CLASS_PHONE;
            case 7:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case 8:
                return InputType.TYPE_TEXT_VARIATION_URI;
            case 9:
                return InputType.TYPE_CLASS_DATETIME;
        }
        return InputType.TYPE_CLASS_TEXT;
    }
    public void onClick_Delete(List<CustomerPersonnel> lList, int position) {
        lContact.remove(position);
        ContactRefresher2();
    }
    public static void ContactRefresher() {
        adapStoreContact adapter = new adapStoreContact(lContact, contexti);
        lstCalling.setAdapter(adapter);
    }
    private void ContactRefresher2() {
        adapStoreContact adapter = new adapStoreContact(lContact, contexti);
        lstCalling.setAdapter(adapter);
    }
    private void ActivityFieldManager() {
        goingToBigMap = true;
        actCustomer.ShowFragActivityFields(lActivityFields);
//        fDialog = new Dialog(contexti);
//        fDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        fDialog.setCancelable(true);
//        fDialog.setCanceledOnTouchOutside(true);
//        fDialog.setContentView(R.layout.dialog_field);
//        Objects.requireNonNull(fDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//        final RecyclerView lstFields = fDialog.findViewById(R.id.lstFields);
//        TextView lblAccept = fDialog.findViewById(R.id.lblAccept);
//        TextView lblCancell = fDialog.findViewById(R.id.lblCancell);
//
////        Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
////        lblAccept.setTypeface(tf);
////        lblCancell.setTypeface(tf);
//
//        if (isField) {
//            lActivityField = geter.getListIsCheck(Basic_ActivityFields.class);
//            isField = false;
//            for (Basic_ActivityFields ddata : lActivityField) {
//                ddata.isCheck = false;
//            }
//            for (Basic_ActivityFields fild : lActivityFields) {
//                for (Basic_ActivityFields mField : lActivityField) {
//                    if (fild.ActivityFieldID == mField.ActivityFieldID) {
//                        mField.isCheck = true;
//                    }
//                }
//            }
//            for (Basic_ActivityFields data : lActivityFields) {
//                boolean isIn = false;
//                for (Basic_ActivityFields dataa : lActivityField) {
//                    if(data.ActivityFieldID == dataa.ActivityFieldID) {
//                        isIn = true;
//                        break;
//                    }
//                }
//                if(!isIn){
//                    List<Basic_ActivityFields> lActFild = geter.getList(Basic_ActivityFields.class, " WHERE ActivityFieldID='" + data.ActivityFieldID + "'");
//                    if(lActFild.size() > 0) {
//                        Basic_ActivityFields oData = new Basic_ActivityFields();
//                        oData.ActivityFieldID = lActFild.get(0).ActivityFieldID;
//                        oData.isCheck = true;
//                        oData.ActivityFieldGroupID = lActFild.get(0).ActivityFieldGroupID;
//                        oData.ActivityFieldOrder = lActFild.get(0).ActivityFieldOrder;
//                        oData.ActivityFieldTitle = lActFild.get(0).ActivityFieldTitle;
//                        oData.Deleted = lActFild.get(0).Deleted;
//                        lActivityField.add(oData);
//                    }
//                }
//            }
//        } else {
//            if (lActivityField.size() == 0) {
//                lActivityField = geter.getListIsCheck(Basic_ActivityFields.class);
//                for (Basic_ActivityFields data : lActivityField) {
//                    data.isCheck = false;
//                }
//            }
//        }
//
//        adapStoreActivityField adapter = new adapStoreActivityField(lActivityField);
//        lstFields.setHasFixedSize(true);
//        mLayoutManageri = new LinearLayoutManager(contexti);
//        lstFields.setLayoutManager(mLayoutManageri);
//        lstFields.addItemDecoration(ItemDecoration.getDecoration(contexti));
//        lstFields.setItemAnimator(new DefaultItemAnimator());
//        lstFields.setAdapter(adapter);
//
//        lblAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lActivityFields = new ArrayList<>();
//                String fName = "";
//                for (Basic_ActivityFields data : lActivityField) {
//                    if (data.isCheck) {
//                        fName += data.ActivityFieldTitle + "<br>";
//                        lActivityFields.add(data);
//                    }
//                }
//                if (fName.length() > 1) {
//                    fName = fName.substring(0, fName.length() - 2);
//                } else {
//                    fName = "برای اضافه کردن اینجا را لمس کنید";
//                }
//                lblField.setText(Html.fromHtml(fName));
//                fDialog.dismiss();
//            }
//        });
//        lblCancell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fDialog.dismiss();
//            }
//        });
//
//        fDialog.show();
    }
    private void TagManager() {
        goingToBigMap = true;
        actCustomer.ShowFragTags(lTags);
//        lDialog = new Dialog(contexti);
//        lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        lDialog.setCancelable(true);
//        lDialog.setCanceledOnTouchOutside(true);
//        lDialog.setContentView(R.layout.dialog_lable);
//        Objects.requireNonNull(lDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//        final RecyclerView lstLable = lDialog.findViewById(R.id.lstLable);
//        TextView lblAccept = lDialog.findViewById(R.id.lblAccept);
//        TextView lblCancell = lDialog.findViewById(R.id.lblCancell);

//        if (isTag) {
//            lTag = geter.getListIsCheck(Basic_taks.class);
//            isTag = false;
//            for (Basic_taks ddata : lTag) {
//                ddata.isCheck = false;
//            }
//            for (Basic_taks fild : lTags) {
//                for (Basic_taks mField : lTag) {
//                    if (fild.TagID == mField.TagID) {
//                        mField.isCheck = true;
//                    }
//                }
//            }
//            for (Basic_taks data : lTags) {
//                boolean isIn = false;
//                for (Basic_taks dataa : lTag) {
//                    if(data.TagID == dataa.TagID) {
//                        isIn = true;
//                        break;
//                    }
//                }
//                if(!isIn){
//                    List<Basic_taks> lTagO = geter.getList(Basic_taks.class, " WHERE TagID='" + data.TagID + "'");
//                    if(lTagO.size() > 0) {
//                        Basic_taks oData = new Basic_taks();
//                        oData.TagID = lTagO.get(0).TagID;
//                        oData.isCheck = true;
//                        oData.TagGroupID = lTagO.get(0).TagGroupID;
//                        oData.TagOrder = lTagO.get(0).TagOrder;
//                        oData.TagTitle = lTagO.get(0).TagTitle;
//                        oData.Deleted = lTagO.get(0).Deleted;
//                        lTag.add(oData);
//                    }
//                }
//            }
//        } else {
//            if (lTag.size() == 0) {
//                lTag = geter.getListIsCheck(Basic_taks.class);
//                for (Basic_taks data : lTag) {
//                    data.isCheck = false;
//                }
//            }
//        }
//
//        adapStoreTag adapter = new adapStoreTag(lTag);
//        lstLable.setHasFixedSize(true);
//        mLayoutManageri = new LinearLayoutManager(contexti);
//        lstLable.setLayoutManager(mLayoutManageri);
//        lstLable.addItemDecoration(ItemDecoration.getDecoration(contexti));
//        lstLable.setItemAnimator(new DefaultItemAnimator());
//        lstLable.setAdapter(adapter);
//
////        txtSearch.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                List<Basic_taks> lTag = new ArrayList<>();
////                lTag = geter.getList(Basic_taks.class, "WHERE TagTitle LIKE '%" + txtSearch.getText().toString() + "%'");
////                adapStoreTag adapter = new adapStoreTag(lTag);
////                lstLable.setAdapter(adapter);
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////            }
////        });
//        lblAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lTags = new ArrayList<>();
//                String tName = "";
//                for (Basic_taks data : lTag) {
//                    if (data.isCheck) {
//                        tName += data.TagTitle + "<br>";
//                        lTags.add(data);
//                    }
//                }
//                if (tName.length() > 1) {
//                    tName = tName.substring(0, tName.length() - 2);
//                } else {
//                    tName = "برای اضافه کردن اینجا را لمس کنید";
//                }
//                lblTags.setText(Html.fromHtml(tName));
//                lDialog.dismiss();
//            }
//        });
//        lblCancell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lDialog.dismiss();
//            }
//        });
//
//        lDialog.show();
    }
    private void PropertiesManager() {
        goingToBigMap = true;
        actCustomer.ShowFragProperties(lProperties);
//        mDialog = new Dialog(contexti);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setCancelable(true);
//        mDialog.setCanceledOnTouchOutside(true);
//        mDialog.setContentView(R.layout.dialog_detail);
//        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//        final RecyclerView lstDetail = mDialog.findViewById(R.id.lstDetail);
//        TextView lblAccept = mDialog.findViewById(R.id.lblAccept);
//        TextView lblCancell = mDialog.findViewById(R.id.lblCancell);
//
//        Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
//        lblAccept.setTypeface(tf);
//        lblCancell.setTypeface(tf);
//
//        if (isDetail) {
//            lPropertie = geter.getListIsCheck(Basic_Properties.class);
//            isDetail = false;
//            for (Basic_Properties ddata : lPropertie) {
//                ddata.isCheck = false;
//            }
//            for (Basic_Properties fild : lProperties) {
//                for (Basic_Properties mField : lPropertie) {
//                    if (fild.PropertyID == mField.PropertyID) {
//                        mField.isCheck = true;
//                    }
//                }
//            }
//            for (Basic_Properties data : lProperties) {
//                boolean isIn = false;
//                for (Basic_Properties dataa : lPropertie) {
//                    if(data.PropertyID == dataa.PropertyID) {
//                        isIn = true;
//                        dataa.PropertyDescription = data.PropertyDescription;
//                    }
//                }
//                if(!isIn){
//                    List<Basic_Properties> lTagO = geter.getList(Basic_Properties.class, " WHERE PropertyID='" + data.PropertyID + "'");
//                    if(lTagO.size() > 0) {
//                        Basic_Properties oData = new Basic_Properties();
//                        oData.PropertyID = lTagO.get(0).PropertyID;
//                        oData.isCheck = true;
//                        oData.PropertyGroupID = lTagO.get(0).PropertyGroupID;
//                        oData.PropertyOrder = lTagO.get(0).PropertyOrder;
//                        oData.PropertyTitle = lTagO.get(0).PropertyTitle;
//                        oData.PropertyDescription = data.PropertyDescription;
//                        oData.Deleted = lTagO.get(0).Deleted;
//                        lPropertie.add(oData);
//                    }
//                }
//            }
//        } else {
//            if (lPropertie.size() == 0) {
//                lPropertie = geter.getListIsCheck(Basic_Properties.class);
//                for (Basic_Properties data : lPropertie) {
//                    data.isCheck = false;
//                }
//            }
//        }
//
//        adapStoreDetail adapter = new adapStoreDetail(lPropertie);
//        lstDetail.setHasFixedSize(true);
//        mLayoutManageri = new LinearLayoutManager(contexti);
//        lstDetail.setLayoutManager(mLayoutManageri);
//        lstDetail.addItemDecoration(ItemDecoration.getDecoration(contexti));
//        lstDetail.setItemAnimator(new DefaultItemAnimator());
//        lstDetail.setAdapter(adapter);
//
//        lblAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lProperties = new ArrayList<>();
//                String tName = "";
//                for (Basic_Properties data : lPropertie) {
//                    if (data.PropertyDescription.length() > 0) {
//                        tName += data.PropertyTitle + " : " + data.PropertyDescription + "<br>";
//                        lProperties.add(data);
//                    }
//                }
//                if (tName.length() > 1) {
//                    tName = tName.substring(0, tName.length() - 2);
//                } else {
//                    tName = "برای اضافه کردن اینجا را لمس کنید";
//                }
//                lblDetails.setText(Html.fromHtml(tName));
//                mDialog.dismiss();
//            }
//        });
//        lblCancell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//
//        mDialog.show();
    }
    private void PicManager() {
        pDialog = new Dialog(contexti);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.setContentView(R.layout.dialog_takepic);
        Objects.requireNonNull(pDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView lblTakePhoto = pDialog.findViewById(R.id.lblTakePhoto);
        TextView lblChooseFromGallery = pDialog.findViewById(R.id.lblChooseFromGallery);
        Typeface tf = Typeface.createFromAsset(contexti.getAssets(), "fonts/ir_sans.ttf");
        lblTakePhoto.setTypeface(tf);
        lblChooseFromGallery.setTypeface(tf);

        lblTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                pDialog.dismiss();
            }
        });
        lblChooseFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                pDialog.dismiss();
            }
        });
        pDialog.show();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMap != null) {
            mMap.onSaveInstanceState(outState);
        }
    }
    private void Finisher() {
        if (!goingToBigMap) {
            lProvinces = new ArrayList<>();
            lCities = new ArrayList<>();
            Prefix = new ArrayList<>();
            ActivityField = new ArrayList<>();
            lContact = new ArrayList<>();
            lProperty = new ArrayList<>();
            lTags = new ArrayList<>();
            lTag = new ArrayList<>();
            lActivityFields = new ArrayList<>();
            lActivityField = new ArrayList<>();
            lProperties = new ArrayList<>();
            lPropertie = new ArrayList<>();
            Name = "";
            Address = "";
            Perfix = 0;
            Hoze = 0;
            sOstan = 0;
            sCity = 0;
            lProfileImg = new ArrayList<>();
            isGrant = false;
            isGrant_GPS = false;
            Zamine = 0;
            Ostane = 0;
            Citye = 0;
            mType = false;
            FragStateCondition = FragmentState.BigMap;//todo Default Mus Be MainPage
            StateId = 1;
            Customer = new MyCustomers();
            OstanSelect = false;
            cPosition = new LatLng(1, 1);
            isTag = false;
            isDetail = false;
            isField = false;
            isAPI = false;
            isMap = false;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }
}
