package com.behincom.behincome.Activityes.Customer;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Activityes.Main.fragCustomers;
import com.behincom.behincome.Adapters.Customer.adapCustomerInvoice;
import com.behincom.behincome.Adapters.Customer.adapGallery;
import com.behincom.behincome.Adapters.Customer.adapStoreShowAct;
import com.behincom.behincome.Adapters.Customer.adapStoreShowPersonels;
import com.behincom.behincome.Adapters.Customer.adapStoreShowTask;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Customer.CustomerActivityFields;
import com.behincom.behincome.Datas.Customer.CustomerPersonnel;
import com.behincom.behincome.Datas.Customer.CustomerProperties;
import com.behincom.behincome.Datas.Customer.CustomerTags;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.maps.model.LatLng;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.behincom.behincome.WebRequest.Retrofite.BASE;

public class fragCustomerShow extends Fragment {

    static Context context;
    private RSQLite SQL = new RSQLite();
    actCustomer actCustom = new actCustomer();
    private RSQLGeter geter = new RSQLGeter();
    Dialog aDialog, mDialog, fDialog, picDialog, invoiceDialog;
    private static Dialog largePicDialog, piDialog, invoiceDialogi;
    adapStoreShowTask adapter_Task;
    adapStoreShowPersonels adapter_Personels;
    adapStoreShowAct adapter_Act;
    SpinAdapter spinAdapter_SubAct, spinAdapter_Condition;
    com.behincom.behincome.Accesories.Dialog pDialog;
    DatePickerDialog DateDialog;
    TimePickerDialog TimePicker;
    DateConverter DC = new DateConverter();
    PersianCalendar PC = new PersianCalendar();
    RWInterface rInterface;
    adapGallery adapPic;
//    private static adapCustomerInvoice adapInvoice;


    //Elements
    TextView lblActivityFields;
    TextView lblMiniAddress;
    ImageView imgAddressOpener;
    TextView lblMiniPersonel;
    ImageView imgPersonelOpener;
    TextView lblMiniActivity;
    ImageView imgActivityOpener;
    TextView lblMiniTag;
    ImageView imgTagOpener;
    TextView lblMiniProperty;
    ImageView imgPropertyOpener;
    TextView lblTitle;
    TextView txtDateTime;
    TextView lblCondition;
    ImageView imgDelete;
    ImageView imgArchive;
    ImageView imgEdite;
    ImageView imgBack;
    ImageView btnPhotos;
    ImageView btnFactors;
    ImageView btnAlarms;
    ImageView btnRefresh;
    TextView lblName;
    TextView lblAddress;
    TextView dialog_btnCancell;
    CardView dialog_cardViewMain;
    ImageView btnMapShow;
    RecyclerView lstPersonels;
    TextView lblTagTitle;
    TextView lblTagList;
    TextView lblDetailTitle;
    TextView lblDetailList;
    TextView lblTaskTitle;
    RecyclerView lstTask;
    TextView lblActionField;
    RecyclerView lstAct;
    RecyclerView.LayoutManager mLayoutManager;

    LinearLayout linAdressOpener;
    LinearLayout linPersonelOpener;
    LinearLayout linActivityOpener;
    LinearLayout linTagOpener;
    LinearLayout linPropertyOpener;
    RelativeLayout relAddres;

    FloatingActionButton btnAddTask;

    public static MyCustomers Customer = new MyCustomers();
    public static List<Invoice> lInvoice = new ArrayList<>();
    public static List<Activities> lTask = new ArrayList<>();
    public static List<Activities> lActivity = new ArrayList<>();
    private List<CustomerPersonnel> lPersonel = new ArrayList<>();
    private List<Basic_ArchiveTypes> lArchiveType = new ArrayList<>();
    public static int position = 0;

    String cSharpDate = "";
    String ShowDate = "";
    String ShowTime = "";
    boolean isDateSet = false;
    boolean isTimeSet = false;
    public static String MBId = "0";
    private static String ActivityFieldHolder = "", TagHolder = "", PropertyHolder = "", AdressHolder = "";

    public static fragCustomerShow newInstance(Context mContext) {
        fragCustomerShow fragment = new fragCustomerShow();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customer_show, container, false);

        //Setup
        relAddres = view.findViewById(R.id.relAddres);
        lblActivityFields = view.findViewById(R.id.lblActivityFields);
        btnAddTask = view.findViewById(R.id.btnAddTask);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgDelete = view.findViewById(R.id.imgDelete);
        imgArchive = view.findViewById(R.id.imgArchive);
        imgEdite = view.findViewById(R.id.imgEdite);
        imgBack = view.findViewById(R.id.imgBack);
        btnPhotos = view.findViewById(R.id.btnPhotos);
        btnFactors = view.findViewById(R.id.btnFactors);
        btnAlarms = view.findViewById(R.id.btnAlarms);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        lblName = view.findViewById(R.id.lblName);
        lblCondition = view.findViewById(R.id.lblCondition);
        lblAddress = view.findViewById(R.id.lblAddress);
        btnMapShow = view.findViewById(R.id.btnMapShow);
        lstPersonels = view.findViewById(R.id.lstPersonels);
        lblTagTitle = view.findViewById(R.id.lblTagTitle);
        lblTagList = view.findViewById(R.id.lblTagList);
        lblDetailTitle = view.findViewById(R.id.lblDetailTitle);
        lblDetailList = view.findViewById(R.id.lblDetailList);
        lblTaskTitle = view.findViewById(R.id.lblTaskTitle);
        lstTask = view.findViewById(R.id.lstTask);
        lblActionField = view.findViewById(R.id.lblActionField);
        lstAct = view.findViewById(R.id.lstAct);
        lblMiniAddress = view.findViewById(R.id.lblMiniAddress);
        imgAddressOpener = view.findViewById(R.id.imgAddressOpener);
        lblMiniPersonel = view.findViewById(R.id.lblMiniPersonel);
        imgPersonelOpener = view.findViewById(R.id.imgPersonelOpener);
        lblMiniActivity = view.findViewById(R.id.lblMiniActivity);
        imgActivityOpener = view.findViewById(R.id.imgActivityOpener);
        lblMiniTag = view.findViewById(R.id.lblMiniTag);
        imgTagOpener = view.findViewById(R.id.imgTagOpener);
        lblMiniProperty = view.findViewById(R.id.lblMiniProperty);
        imgPropertyOpener = view.findViewById(R.id.imgPropertyOpener);

        linPersonelOpener = view.findViewById(R.id.linPersonelOpener);
        linAdressOpener = view.findViewById(R.id.linAdressOpener);
        linPropertyOpener = view.findViewById(R.id.linPropertyOpener);
        linTagOpener = view.findViewById(R.id.linTagOpener);
        linActivityOpener = view.findViewById(R.id.linActivityOpener);

        init();

        rInterface = Retrofite.getClient().create(RWInterface.class);

        lblTitle.setText("فروشگاه");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picDialog = new Dialog(context);
                picDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                picDialog.setCancelable(true);
                picDialog.setCanceledOnTouchOutside(true);
                picDialog.setContentView(R.layout.dialog_customer_gallery);
                Objects.requireNonNull(picDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                GridView gridview = picDialog.findViewById(R.id.lstMain);
                TextView btnCancell = picDialog.findViewById(R.id.lblCancell);
                TextView btnAccept = picDialog.findViewById(R.id.lblAccept);
                adapPic = new adapGallery(context, Customer.Customers.Customers_Images);
                gridview.setAdapter(adapPic);

                btnCancell.setVisibility(View.GONE);

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picDialog.hide();
                    }
                });
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        largePicDialog = new Dialog(context);
                        largePicDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        largePicDialog.setCancelable(true);
                        largePicDialog.setCanceledOnTouchOutside(true);
                        largePicDialog.setContentView(R.layout.dialog_customer_gallery_big);
                        Objects.requireNonNull(largePicDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);


                        TextView btnCancell = largePicDialog.findViewById(R.id.lblCancell);
                        TextView btnAccept = largePicDialog.findViewById(R.id.lblAccept);
                        final ImageView img = largePicDialog.findViewById(R.id.imgBig);

                        btnCancell.setVisibility(View.GONE);
                        btnAccept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                largePicDialog.dismiss();
                            }
                        });
                        String PhotoURL = "";
                        try {
                            PhotoURL = Customer.Customers.Customers_Images.get(position).ImageFilename;
                            if (PhotoURL.length() > 5)
                                PhotoURL = BASE + "Uploads/CustomerImages/" + PhotoURL;
                            else
                                PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.logo).toString();
                        } catch (Exception e) {
                            PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.logo).toString();
                        }
                        Glide.with(context).load(PhotoURL).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                img.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                        largePicDialog.show();
                    }
                });
                picDialog.show();
            }
        });

        //Get Store Personels
        lPersonel = Customer.Customers.Customers_Personnel;
        //Get Store Prefix
        List<Basic_NamePrefixes> lPrefix = geter.getList(Basic_NamePrefixes.class, "WHERE NamePrefixID='" + Customer.Customers.NamePrefixID + "'");
        String Prefix = "";
        if (lPrefix.size() > 0) Prefix = lPrefix.get(0).NamePrefixTitle;
        //lblName - Prefix + Name
        lblName.setText(Prefix + " " + Customer.Customers.CustomerName);
        //Get Activity Field For Store

        RefreshPersonels();//ListPersonels

        //MapIcon Condition
        if (Customer.Customers.CustomerLatitude > 20 && Customer.Customers.CustomerLongitude > 20)
            btnMapShow.setVisibility(View.VISIBLE);
        else btnMapShow.setVisibility(View.GONE);

        List<Basic_CustomerStates> lState = geter.getList(Basic_CustomerStates.class, "WHERE CustomerStateID='" + Customer.Customers.CustomerStateID + "'");
        lblCondition.setText(lState.get(0).CustomerStateTitle);

        btnFactors.setVisibility(View.GONE);
        btnFactors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoiceDialog = new Dialog(context);
                invoiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                invoiceDialog.setCancelable(true);
                invoiceDialog.setCanceledOnTouchOutside(true);
                invoiceDialog.setContentView(R.layout.dialog_show_invoice);
                Objects.requireNonNull(invoiceDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                RecyclerView lstMain = invoiceDialog.findViewById(R.id.lstMain);
                TextView lblAccept = invoiceDialog.findViewById(R.id.lblAccept);
                TextView lblCancell = invoiceDialog.findViewById(R.id.lblCancell);
                lblCancell.setVisibility(View.GONE);

                lstMain.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                lstMain.setLayoutManager(mLayoutManager);
                lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
                lstMain.setItemAnimator(new DefaultItemAnimator());

                final adapCustomerInvoice adapInvoice = new adapCustomerInvoice(context, lInvoice);
                lstMain.setAdapter(adapInvoice);

                lblAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoiceDialog.dismiss();
                    }
                });
                invoiceDialog.show();
            }
        });//Showing Factors in New Fragment
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                piDialog = new Dialog(context);
                piDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                piDialog.setCancelable(true);
                piDialog.setCanceledOnTouchOutside(true);
                piDialog.setContentView(R.layout.dialog_condition_store);
                Objects.requireNonNull(piDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView Title = piDialog.findViewById(R.id.lblTitle);
                TextView lblCancell = piDialog.findViewById(R.id.lblCancell);
                TextView lblAccept = piDialog.findViewById(R.id.lblAccept);
                final Spinner spinCondition = piDialog.findViewById(R.id.spinCondition);

                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
                Title.setTypeface(tf);
                lblCancell.setTypeface(tf);
                lblAccept.setTypeface(tf);

                List<Basic_CustomerStates> lState = geter.getList(Basic_CustomerStates.class);
                spinAdapter_Condition = new SpinAdapter(context, lState, "CustomerStateTitle");
                spinCondition.setAdapter(spinAdapter_Condition);

                lblCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        piDialog.dismiss();
                    }
                });
                lblAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                        pDialog.Show();

                        HashMap<String, Object> MapChangeState = new HashMap<>();
                        MapChangeState.put("CustomerID", Customer.Customers.CustomerID);
                        final int StateID = Integer.parseInt(spinAdapter_Condition.getItemString(spinCondition.getSelectedItemPosition(), "CustomerStateID").toString());
                        MapChangeState.put("CustomerStateID", StateID);

                        Call ChangeState = rInterface.RQChangeCustomerState(Setting.getToken(), MapChangeState);
                        ChangeState.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.isSuccessful()) {
                                    fragCustomers.lCustomer.get(position).Customers.CustomerStateID = (StateID);
                                    Customer.Customers.CustomerStateID = (StateID);
                                    lblCondition.setText(spinAdapter_Condition.getItemString(spinCondition.getSelectedItemPosition(), "CustomerStateTitle"));
                                }
                                pDialog.DisMiss();
                                piDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                pDialog.DisMiss();
                            }
                        });
                    }
                });
                piDialog.show();
            }
        });//Change Customer States
        btnMapShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragCustomerOnMap.StoreName = Customer.Customers.CustomerName;
                fragCustomerOnMap.Loc = new LatLng(Customer.Customers.CustomerLatitude, Customer.Customers.CustomerLongitude);
                actCustom.getFragByState(FragmentState.CustomerOnMap);
            }
        });//Going to Map If Exist LagLng For This Customer
        btnAlarms.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(context);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setCancelable(true);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.setContentView(R.layout.dialog_alarm_set);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView lblTitle = mDialog.findViewById(R.id.lblTitle);
                TextView lblDescription = mDialog.findViewById(R.id.lblDescription);
                TextView lblDateTime = mDialog.findViewById(R.id.lblDateTime);
                TextView lblCondition = mDialog.findViewById(R.id.lblCondition);
                TextView lblCancell = mDialog.findViewById(R.id.lblCancell);
                TextView lblAccept = mDialog.findViewById(R.id.lblAccept);
                final EditText txtTitle = mDialog.findViewById(R.id.txtTitle);
                final EditText txtDescription = mDialog.findViewById(R.id.txtDescription);
                txtDateTime = mDialog.findViewById(R.id.txtDateTime);
                final RadioButton radActive = mDialog.findViewById(R.id.radActive);
                final RadioButton radDeActive = mDialog.findViewById(R.id.radDeActive);

                txtTitle.setText(Customer.Customers.CustomerName);
                txtDateTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!isDateSet) {
                            isDateSet = true;
                            if (hasFocus) {
                                DateDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                        String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
                                        final DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                                        cSharpDate = DC.getCSharp();
                                        ShowDate = mDate;
                                        txtDateTime.setText(mDate);
                                        isDateSet = false;

                                        Time now = new Time();
                                        now.setToNow();
                                        TimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                                String Time = DC.getTime(hourOfDay, minute);
                                                ShowTime = Time;
                                                String[] cDate = cSharpDate.split("T");
                                                cSharpDate = cDate[0] + "T" + Time;
                                                txtDateTime.setText(ShowDate + " - " + Time);
                                            }
                                        }, now.hour, now.minute, false);
                                        TimePicker.setTitle("زمان را مشخص کنید");
                                        TimePicker.show(getActivity().getFragmentManager(), "AlarmTimeSet");
                                    }
                                }, PC.getPersianYear(), PC.getPersianMonth(), PC.getPersianDay());
                                DateDialog.show(getActivity().getFragmentManager(), "AlarmDateSet");
                            }
                        }
                    }
                });
                DateDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
                        final DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                        cSharpDate = DC.getCSharp();
                        ShowDate = mDate;
                        txtDateTime.setText(mDate);
                        isDateSet = false;

                        Time now = new Time();
                        now.setToNow();
                        TimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                String Time = DC.getTime(hourOfDay, minute);
                                ShowTime = Time;
                                String[] cDate = cSharpDate.split("T");
                                cSharpDate = cDate[0] + "T" + Time;
                                txtDateTime.setText(ShowDate + " - " + Time);
                            }
                        }, now.hour, now.minute, false);
                        TimePicker.setTitle("زمان را مشخص کنید");
                        TimePicker.show(getActivity().getFragmentManager(), "AlarmTimeSet");
                    }
                }, PC.getPersianYear(), PC.getPersianMonth(), PC.getPersianDay());
                DateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        isDateSet = true;
                    }
                });
                txtDateTime.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (!isDateSet) {
                            isDateSet = true;
                            DateDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
                                    final DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                                    cSharpDate = DC.getCSharp();
                                    ShowDate = mDate;
                                    txtDateTime.setText(mDate);
                                    isDateSet = false;

                                    Time now = new Time();
                                    now.setToNow();
                                    TimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                            String Time = DC.getTime(hourOfDay, minute);
                                            ShowTime = Time;
                                            String[] cDate = cSharpDate.split("T");
                                            cSharpDate = cDate[0] + "T" + Time;
                                            txtDateTime.setText(ShowDate + " - " + Time);
                                        }
                                    }, now.hour, now.minute, false);
                                    TimePicker.setTitle("زمان را مشخص کنید");
                                    TimePicker.show(getActivity().getFragmentManager(), "AlarmTimeSet");
                                }
                            }, PC.getPersianYear(), PC.getPersianMonth(), PC.getPersianDay());
                            DateDialog.show(getActivity().getFragmentManager(), "AlarmDateSet");
                        }
                        return false;
                    }
                });
                txtDateTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isDateSet) {
                            isDateSet = true;
                            DateDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    String mDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth);
                                    final DateConverter DC = new DateConverter(year, monthOfYear + 1, dayOfMonth);
                                    cSharpDate = DC.getCSharp();
                                    ShowDate = mDate;
                                    txtDateTime.setText(mDate);
                                    isDateSet = false;

                                    Time now = new Time();
                                    now.setToNow();
                                    TimePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                            String Time = DC.getTime(hourOfDay, minute);
                                            ShowTime = Time;
                                            String[] cDate = cSharpDate.split("T");
                                            cSharpDate = cDate[0] + "T" + Time;
                                            txtDateTime.setText(ShowDate + " - " + Time);
                                        }
                                    }, now.hour, now.minute, false);
                                    TimePicker.setTitle("زمان را مشخص کنید");
                                    TimePicker.show(getActivity().getFragmentManager(), "AlarmTimeSet");
                                }
                            }, PC.getPersianYear(), PC.getPersianMonth(), PC.getPersianDay());
                            DateDialog.show(getActivity().getFragmentManager(), "AlarmDateSet");
                        }
                    }
                });
                lblCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                lblAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isActive = false;
                        if (radActive.isChecked()) isActive = true;
                        else if (radDeActive.isChecked()) isActive = false;

                        pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                        pDialog.Show();

                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });//Set Alarm

        if(Customer.Customers.CustomerAddress.length() > 38)
            lblMiniAddress.setText(Customer.Customers.CustomerAddress.substring(0, 38) + " ...");
        else
            lblMiniAddress.setText(Customer.Customers.CustomerAddress);
        lblAddress.setText(Customer.Customers.CustomerAddress);//lblAddress
        String Fields = "";
        String MiniFields = "";
        try {
            List<CustomerActivityFields> lFieldd = Customer.Customers.Customers_ActivityFields;
            for (int i = 0; i < lFieldd.size(); i++) {
                List<Basic_ActivityFields> lActivityField = geter.getList(Basic_ActivityFields.class, "WHERE ActivityFieldID='" + lFieldd.get(i).ActivityFieldID + "'");
                Fields += lActivityField.get(0).ActivityFieldTitle + "<br>";
                MiniFields += lActivityField.get(0).ActivityFieldTitle + "، ";
            }
            if (lFieldd.size() > 0)
            {
                Fields = Fields.substring(0, Fields.length() - 1);
                MiniFields = MiniFields.substring(0, MiniFields.length() - 2);
                if(MiniFields.length() > 27)//27 = Tedad Chari ke test kardam didam khube ( Jeloe Title Zamine Faaliat )
                    MiniFields = MiniFields.substring(0, 27) + " ...";

                lblActivityFields.setText(Html.fromHtml(Fields));
                lblMiniActivity.setText(MiniFields);
                linActivityOpener.setVisibility(View.VISIBLE);
            }else{
                linActivityOpener.setVisibility(View.GONE);
                lblMiniActivity.setText("");
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }//lblFields
        String Tags = "";
        String MiniTag = "";
        List<CustomerTags> lTag = Customer.Customers.Customers_Tags;
        try {
            for (int i = 0; i < lTag.size(); i++) {
                List<Basic_Tags> lTags = geter.getList(Basic_Tags.class, "WHERE TagID='" + lTag.get(i).TagID + "'");
                Tags += lTags.get(0).TagTitle + "<br>";
                MiniTag += lTags.get(0).TagTitle + "، ";
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        if(lTag.size() > 0){
            Tags = Tags.substring(0, Tags.length() - 1);
            MiniTag = MiniTag.substring(0, MiniTag.length() - 2);
            if(MiniTag.length() > 37)
                MiniTag = MiniTag.substring(0, 37) + " ...";

            lblMiniTag.setText(MiniTag);
            lblTagList.setText(Html.fromHtml(Tags));
            linTagOpener.setVisibility(View.VISIBLE);
        }else{
            linTagOpener.setVisibility(View.GONE);
            lblMiniTag.setText("");
        }//lblTags
        List<CustomerProperties> lProperties = Customer.Customers.Customers_Properties;
        String Property = "";
        String MiniProperty = "";
        try {
            for (int i = 0; i < lProperties.size(); i++) {
                List<Basic_Properties> lProperty = geter.getList(Basic_Properties.class, "WHERE PropertyID='" + lProperties.get(i).PropertyID + "'");
                Property += lProperty.get(0).PropertyTitle + " : " + lProperties.get(i).Value + "<br>";
                MiniProperty += lProperty.get(0).PropertyTitle + "، ";
            }
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        if(lProperties.size() > 0){
            Property = Property.substring(0, Property.length() - 1);
            MiniProperty = MiniProperty.substring(0, MiniProperty.length() - 2);
            if(MiniProperty.length() > 37)
                MiniProperty = MiniProperty.substring(0, 37) + " ...";

            lblMiniProperty.setText(MiniProperty);
            lblDetailList.setText(Html.fromHtml(Property));
            linPropertyOpener.setVisibility(View.VISIBLE);
        }else{
            linPropertyOpener.setVisibility(View.GONE);
            lblMiniProperty.setText("");
        }//lblDetails

        linActivityOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lblActivityFields.getVisibility() == View.GONE){
                    ActivityFieldHolder = lblMiniActivity.getText().toString();
                    lblMiniActivity.setText("");
                    lblActivityFields.setVisibility(View.VISIBLE);
                }else{
                    lblMiniActivity.setText(ActivityFieldHolder);
                    ActivityFieldHolder = "";
                    lblActivityFields.setVisibility(View.GONE);
                }
            }
        });//Open/Close ActivityFields
        linTagOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lblTagList.getVisibility() == View.GONE){
                    TagHolder = lblMiniTag.getText().toString();
                    lblMiniTag.setText("");
                    lblTagList.setVisibility(View.VISIBLE);
                }else{
                    lblMiniTag.setText(TagHolder);
                    TagHolder = "";
                    lblTagList.setVisibility(View.GONE);
                }
            }
        });//Open/Close Tags
        linPropertyOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lblDetailList.getVisibility() == View.GONE){
                    PropertyHolder = lblMiniProperty.getText().toString();
                    lblMiniProperty.setText("");
                    lblDetailList.setVisibility(View.VISIBLE);
                }else{
                    lblMiniProperty.setText(PropertyHolder);
                    PropertyHolder = "";
                    lblDetailList.setVisibility(View.GONE);
                }
            }
        });//Open/Close Properties
        linPersonelOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lstPersonels.getVisibility() == View.GONE){
                    lblMiniPersonel.setText("");
                    lstPersonels.setVisibility(View.VISIBLE);
                }else{
                    String PersonelNames = "";
                    for (CustomerPersonnel data : lPersonel) {
                        PersonelNames += data.Name + "، ";
                    }
                    if(lPersonel.size() > 0){
                        PersonelNames = PersonelNames.substring(0, PersonelNames.length() - 2);
                        if(PersonelNames.length() > 37)
                            PersonelNames = PersonelNames.substring(0, 37) + " ...";

                        lblMiniPersonel.setText(PersonelNames);
                        linPersonelOpener.setVisibility(View.VISIBLE);
                    }else{
                        lblMiniPersonel.setText("");
                        linPersonelOpener.setVisibility(View.GONE);
                    }

                    lstPersonels.setVisibility(View.GONE);
                }
            }
        });//Open/Close Personels
        linAdressOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relAddres.getVisibility() == View.GONE){
                    AdressHolder = lblMiniAddress.getText().toString();
                    lblMiniAddress.setText("");
                    relAddres.setVisibility(View.VISIBLE);
                }else{
                    lblMiniAddress.setText(AdressHolder);
                    AdressHolder = "";
                    relAddres.setVisibility(View.GONE);
                }
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete();
            }
        });//Delete Store
        imgArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Archive();
            }
        });//ArchiveStore
        imgEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit();
            }
        });//EditeStore
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), actActivities.class);
                actActivities.STATE = FragmentState.AddTask;
//                fragAddTask.Name = Customer.Customers.CustomerName;
//                fragAddTask.customer_id = Customer.Customers.CustomerID;
//                fragAddTask.Type = 0;
//                fragAddTask.mCustomer = Customer;
//                fragAddTask.mPosition = position;
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });//AddTask

        return view;
    }
    private void init() {
        lstPersonels.setNestedScrollingEnabled(false);
        lstPersonels.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstPersonels.setLayoutManager(mLayoutManager);
        lstPersonels.addItemDecoration(ItemDecoration.getDecoration(context));
        lstPersonels.setItemAnimator(new DefaultItemAnimator());

        lstTask.setNestedScrollingEnabled(false);
        lstTask.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstTask.setLayoutManager(mLayoutManager);
        lstTask.addItemDecoration(ItemDecoration.getDecoration(context));
        lstTask.setItemAnimator(new DefaultItemAnimator());

        lstAct.setNestedScrollingEnabled(false);
        lstAct.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstAct.setLayoutManager(mLayoutManager);
        lstAct.addItemDecoration(ItemDecoration.getDecoration(context));
        lstAct.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public void onResume() {
        super.onResume();
        getTasks();
        getActs();
    }
    private void RefreshPersonels() {
        String PersonelNames = "";
        for (CustomerPersonnel data : lPersonel) {
            PersonelNames += data.Name + "، ";
        }
        if(lPersonel.size() > 0){
            PersonelNames = PersonelNames.substring(0, PersonelNames.length() - 2);
            if(PersonelNames.length() > 37)
                PersonelNames = PersonelNames.substring(0, 37) + " ...";

            lblMiniPersonel.setText(PersonelNames);
            linPersonelOpener.setVisibility(View.VISIBLE);
        }else{
            lblMiniPersonel.setText("");
            linPersonelOpener.setVisibility(View.GONE);
        }
        adapter_Personels = new adapStoreShowPersonels(lPersonel, context);
        lstPersonels.setAdapter(adapter_Personels);
    }
    private void RefreshTask() {
        adapter_Task = new adapStoreShowTask(lTask);
        lstTask.setAdapter(adapter_Task);
    }
    private void RefreshAct() {
        adapter_Act = new adapStoreShowAct(lActivity, getActivity());
        lstAct.setAdapter(adapter_Act);
    }
    private void Archive() {
        aDialog = new Dialog(context);
        aDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        aDialog.setCancelable(true);
        aDialog.setCanceledOnTouchOutside(true);
        aDialog.setContentView(R.layout.dialog_select_archive);
        Objects.requireNonNull(aDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        dialog_btnCancell = aDialog.findViewById(R.id.btnCancell);
        dialog_cardViewMain = aDialog.findViewById(R.id.cardViewMain);
        final Spinner spinArchive = aDialog.findViewById(R.id.spinArchive);
        TextView btnTimeTitle = aDialog.findViewById(R.id.btnTimeTitle);
        TextView lblTitle = aDialog.findViewById(R.id.lblTitle);
        TextView btnCancell = aDialog.findViewById(R.id.lblCancell);
        TextView btnAccept = aDialog.findViewById(R.id.lblAccept);

        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblTitle.setTypeface(tFace);
        btnTimeTitle.setTypeface(tFace);
        btnCancell.setTypeface(tFace);
        btnAccept.setTypeface(tFace);

        lArchiveType = geter.getList(Basic_ArchiveTypes.class);
        Basic_ArchiveTypes data = new Basic_ArchiveTypes();
        data.ArchiveTypeTitle = ("نوع بایگانی");
        lArchiveType.add(0, data);
        spinAdapter_SubAct = new SpinAdapter(context, lArchiveType, "ArchiveTypeTitle");
        spinArchive.setAdapter(spinAdapter_SubAct);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                pDialog.Show();

                HashMap<String, Object> MapChangeState = new HashMap<>();
                MapChangeState.put("CustomerID", Customer.Customers.CustomerID);
                MapChangeState.put("ArchiveTypeID", Integer.parseInt(spinAdapter_SubAct.getItemString(spinArchive.getSelectedItemPosition(), "ArchiveTypeID")));

                Call ChangeState = rInterface.RQAddCustomerToArchive(Setting.getToken(), MapChangeState);
                ChangeState.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(context, actMain.class);
                            actMain.STATE = FragmentState.MainCustomers;
                            context.startActivity(intent);
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
        btnCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aDialog.dismiss();
            }
        });
        aDialog.show();
    }
    private void Delete() {
        pDialog = new com.behincom.behincome.Accesories.Dialog(context);
        pDialog.Show();

        HashMap<String, Object> MapChangeState = new HashMap<>();
        MapChangeState.put("CustomerID", Customer.Customers.CustomerID);

        Call ChangeState = rInterface.RQDeleteCustomer(Setting.getToken(), MapChangeState);
        ChangeState.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    fragCustomers.lCustomer.remove(position);
                    Toast.makeText(context, "فروشگاه حذف شد", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, actMain.class);
                    actMain.STATE = FragmentState.MainCustomers;
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
    private void Edit() {
        fragAddCustomer.Customer = Customer;
        fragAddCustomer.mType = true;
        fragAddCustomer.OstanSelect = true;
        fragAddCustomer.StateId = Customer.Customers.CustomerStateID;
        actCustom.getFragByState(FragmentState.AddCustomer);
    }
    private void getTasks() {
//        pDialog = new com.behincom.behincome.Accesories.Dialog(getActivity());
//        pDialog.Show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("CustomerID", Customer.Customers.CustomerID);

        Call cGetTasks = rInterface.RQGetTasksByCustomerID(Setting.getToken(), map);
        cGetTasks.enqueue(new Callback<List<Activities>>() {
            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                if (response.isSuccessful()) {
                    lTask = response.body();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RefreshTask();
//                            pDialog.DisMiss();
                        }
                    });
                }
//                pDialog.DisMiss();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
//                pDialog.DisMiss();
            }
        });
    }
    private void getActs() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("CustomerID", Customer.Customers.CustomerID);

        Call cGetTasks = rInterface.RQGetActivitiesByCustomerID(Setting.getToken(), map);
        cGetTasks.enqueue(new Callback<List<Activities>>() {
            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                if (response.isSuccessful()) {
                    lActivity = response.body();

                    for (Activities data : lActivity) {
                        if(data.Invoice.size() > 0)
                            lInvoice.addAll(data.Invoice);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RefreshAct();
                            if(lInvoice.size() > 0)
                                btnFactors.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
//                pDialog.DisMiss();
            }
        });
    }
    public static void ShowInvoices(List<Invoice> lList){
        invoiceDialogi = new Dialog(context);
        invoiceDialogi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        invoiceDialogi.setCancelable(true);
        invoiceDialogi.setCanceledOnTouchOutside(true);
        invoiceDialogi.setContentView(R.layout.dialog_show_invoice);
        Objects.requireNonNull(invoiceDialogi.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        RecyclerView lstMain = invoiceDialogi.findViewById(R.id.lstMain);
        TextView lblAccept = invoiceDialogi.findViewById(R.id.lblAccept);
        TextView lblCancell = invoiceDialogi.findViewById(R.id.lblCancell);
        lblCancell.setVisibility(View.GONE);

        lstMain.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        final adapCustomerInvoice adapInvoice = new adapCustomerInvoice(context, lList);
        lstMain.setAdapter(adapInvoice);

        lblAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoiceDialogi.dismiss();
            }
        });
        invoiceDialogi.show();
    }
//    private void getInvoice() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("CustomerID", Customer.CustomerID);
//
//        Call getInvoices = rInterface.RQGetCustomerInvoice(Setting.getToken(), map);
//        getInvoices.enqueue(new Callback<List<Invoice>>() {
//            @Override
//            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
//                if (response.isSuccessful()) {
//                    lInvoice = response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                String Er = t.getMessage();
//            }
//        });
//    }
//    public void getInvoice(int ActivityID) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("ActivityID", ActivityID);
//
//        Call getInvoices = rInterface.RQGetCustomerInvoice(Setting.getToken(), map);
//        getInvoices.enqueue(new Callback<List<Invoice>>() {
//            @Override
//            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
//                if (response.isSuccessful()) {
//                    List<Invoice> mInvoice = response.body();
//
//                    invoiceDialog = new Dialog(context);
//                    invoiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    invoiceDialog.setCancelable(true);
//                    invoiceDialog.setCanceledOnTouchOutside(true);
//                    invoiceDialog.setContentView(R.layout.dialog_show_invoice);
//                    Objects.requireNonNull(invoiceDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//
//                    RecyclerView lstMain = invoiceDialog.findViewById(R.id.lstMain);
//                    TextView lblAccept = invoiceDialog.findViewById(R.id.lblAccept);
//                    TextView lblCancell = invoiceDialog.findViewById(R.id.lblCancell);
//                    lblCancell.setVisibility(View.GONE);
//
//                    adapInvoice = new adapCustomerInvoice(mInvoice, context);
//                    lstMain.setAdapter(adapInvoice);
//
//                    lblAccept.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            invoiceDialog.dismiss();
//                        }
//                    });
//                    invoiceDialog.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                String Er = t.getMessage();
//            }
//        });
//    }
    private void Finisher(){
        Customer = new MyCustomers();
        lTask = new ArrayList<>();
        lActivity = new ArrayList<>();
        lPersonel = new ArrayList<>();
        lArchiveType = new ArrayList<>();
        position = 0;
        cSharpDate = "";
        ShowDate = "";
        ShowTime = "";
        isDateSet = false;
        isTimeSet = false;
        MBId = "0";
        ActivityFieldHolder = "";
        TagHolder = "";
        PropertyHolder = "";
        AdressHolder = "";
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }

}
