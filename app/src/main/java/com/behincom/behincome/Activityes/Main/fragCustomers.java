package com.behincom.behincome.Activityes.Main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Accesories.VoiceType;
import com.behincom.behincome.Activityes.Customer.actCustomer;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Main.adapMainCustomerMarketers;
import com.behincom.behincome.Adapters.Main.adapMainCustomers;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_Cities;
import com.behincom.behincome.Datas.BaseData.Basic_ContactTypes;
import com.behincom.behincome.Datas.BaseData.Basic_PersonRoles;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.DataDates;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.MarketerUserAccessProfile;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

import static com.behincom.behincome.Adapters.Main.adapMainCustomers.Selectable;

public class fragCustomers extends Fragment {

    @SuppressLint("StaticFieldLeak")
    static Context context;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    Dialog mDialog;
    com.behincom.behincome.Accesories.Dialog pDialog;
    RWInterface rInterface;
    actMain act = new actMain();
    adapMainCustomers adapter;
    static adapMainCustomerMarketers adapterMarketers;

    TextView lblStore;
    RecyclerView lstMain;
    static RecyclerView lstMarketers;
    RecyclerView.LayoutManager mLayoutManager;
    public static TextInputEditText txtSearch;
    ImageView imgFilter;
    ImageView imgVoice;
    ImageView btnAssign;
    ImageView btnDelete;
    ImageView btnArchive;
    ImageView btnCancellation;
    SwipeRefreshLayout swipeRefresher;
    RelativeLayout RelSetting;
    static RelativeLayout ViewMarketerList;
    static LinearLayout linStatusBar;
    static LinearLayout linSearch;
    FloatingActionButton btnAdd;
    FloatingActionButton btnMapMini;
    LinearLayout btnHome1, btnHome2, btnReport, btnAccount;
    TextView lblHome1, lblHome2, lblReport, lblAccount;
    ImageView imgHome1, imgHome2, imgReport, imgAccount;
    ImageView imgSetting, imgCancellSearch;

    public static List<MyCustomers> lCustomer = new ArrayList<>();
    private List<MyCustomers> lCustomer2 = new ArrayList<>();

    public static fragCustomers newInstance(Context mContext) {
        fragCustomers fragment = new fragCustomers();
        context = mContext;
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customers, container, false);

        lstMarketers = view.findViewById(R.id.lstMarketers);
        ViewMarketerList = view.findViewById(R.id.ViewMarketerList);
        imgCancellSearch = view.findViewById(R.id.imgCancellSearch);
        imgSetting = view.findViewById(R.id.imgSetting);
        btnAdd = view.findViewById(R.id.btnAdd);
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
        swipeRefresher = view.findViewById(R.id.swipeRefresher);
        linSearch = view.findViewById(R.id.linSearch);
        lblStore = view.findViewById(R.id.lblStore);
        lstMain = view.findViewById(R.id.lstMain);
        RelSetting = view.findViewById(R.id.RelSetting);
        txtSearch = view.findViewById(R.id.txtSearch);
        imgFilter = view.findViewById(R.id.imgFilter);
        imgVoice = view.findViewById(R.id.imgVoice);
        btnAssign = view.findViewById(R.id.btnAssign);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnArchive = view.findViewById(R.id.btnArchive);
        btnCancellation = view.findViewById(R.id.btnCancellation);
        linStatusBar = view.findViewById(R.id.linStatusBar);
        btnMapMini = view.findViewById(R.id.btnMapMini);

        imgCancellSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText("");
            }
        });
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
                act.getFragByState(FragmentState.MainTasks);
            }
        });
        btnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                act.getFragByState(FragmentState.MainCustomers);
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
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Basic_Cities> lCit = geter.getListIsCheck(Basic_Cities.class);
                List<Basic_ActivityFields> lAct = geter.getListIsCheck(Basic_ActivityFields.class);
                List<Basic_Tags> lTag = geter.getListIsCheck(Basic_Tags.class);
                List<Basic_Properties> lProp = geter.getListIsCheck(Basic_Properties.class);
                List<Basic_ContactTypes> lCont = geter.getList(Basic_ContactTypes.class);
                List<Basic_PersonRoles> lRole = geter.getList(Basic_PersonRoles.class);
                if(lCit.size() > 0 && lAct.size() > 0 && lTag.size() > 0 && lProp.size() > 0 && lCont.size() > 0 && lRole.size() > 0) {
                    Intent intent = new Intent(getActivity(), actCustomer.class);
                    actCustomer.STATE = FragmentState.AddCustomer;
                    startActivity(intent);
                }else
                    Toast.makeText(context, "لطفا ابتدا تمام موارد را در تنظیمات اطلاعات پایه تکمیل کنید.", Toast.LENGTH_LONG).show();
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtSearch.getText().toString().length() > 0) {
                    lCustomer2 = new ArrayList<>();
                    for (MyCustomers data : lCustomer) {
                        if (data.Customers.CustomerName.contains(txtSearch.getText().toString()))
                            lCustomer2.add(data);
                    }
                    RefreshList();
                    imgCancellSearch.setVisibility(View.VISIBLE);
                }else{
                    imgCancellSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        adapter = new adapMainCustomers(lCustomer, getActivity());
        lstMain.setAdapter(adapter);

        imgVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });

        btnMapMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragCustomersMap.lCustomer = lCustomer;
                act.getFragByState(FragmentState.MainCustomersMap);
            }
        });
        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MyCustomers> fCustomer = new ArrayList<>();
                for (MyCustomers data : adapter.lList) {
                    if (data.Customers.isCheck)
                        fCustomer.add(data);
                }
                fragCustomerAssigns.lCustomers = fCustomer;
                act.getFragByState(FragmentState.MainCustomerAssign);
                HideStatusBar();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideStatusBar();
            }
        });
        btnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Archive();
            }
        });
        btnCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideStatusBar();
            }
        });


        lstMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {// +#
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            getCustomers(page);
                        }
                    }
                }
            }
        });
        swipeRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                lCustomer = new ArrayList<>();
                adapter.lList = new ArrayList<>();
                getCustomers(page);
            }
        });

        return view;
    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    SpinAdapter spinAdapter_SubAct;
    List<Basic_ArchiveTypes> lDataArchive = new ArrayList<>();

    private void Archive() {
        final Dialog aDialog = new Dialog(context);
        aDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        aDialog.setCancelable(true);
        aDialog.setCanceledOnTouchOutside(true);
        aDialog.setContentView(R.layout.dialog_select_archive);
        Objects.requireNonNull(aDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        final Spinner spinArchive = aDialog.findViewById(R.id.spinArchive);
        final Spinner spinDay = aDialog.findViewById(R.id.spinDay);
        final Spinner spinMonth = aDialog.findViewById(R.id.spinMonth);
        final Spinner spinYear = aDialog.findViewById(R.id.spinYear);
        TextView btnCancell = aDialog.findViewById(R.id.lblCancell);
        TextView btnAccept = aDialog.findViewById(R.id.lblAccept);
        final EditText txtDescription = aDialog.findViewById(R.id.txtDescription);

        lDataArchive = geter.getList(Basic_ArchiveTypes.class);
        spinAdapter_SubAct = new SpinAdapter(context, lDataArchive, "ArchiveTypeTitle");
        spinArchive.setAdapter(spinAdapter_SubAct);

        final SpinAdapter adapter1 = new SpinAdapter(context, Day(), "name");
        spinDay.setAdapter(adapter1);
        final SpinAdapter adapter2 = new SpinAdapter(context, Month(), "name");
        spinMonth.setAdapter(adapter2);
        final SpinAdapter adapter3 = new SpinAdapter(context, Year(), "name");
        spinYear.setAdapter(adapter3);

        String[] dates = Setting.getServerDateTime().split("T");
        String[] date = dates[0].split("-");
        int mY = Integer.parseInt(date[0]);
        int mM = Integer.parseInt(date[1]);
        int mD = Integer.parseInt(date[2]);
        final PersianDate PD = new PersianDate();
        PD.setGrgYear(mY);
        PD.setGrgMonth(mM);
        PD.setGrgDay(mD);
        mY = PD.getShYear();
        mM = PD.getShMonth();
        mD = PD.getShDay();

        spinYear.setSelection(adapter3.getItemPosition("name", Integer.toString(mY)));
        spinMonth.setSelection(mM - 1);
        spinDay.setSelection(mD - 1);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final com.behincom.behincome.Accesories.Dialog pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                pDialog.Show();

                List<Integer> lCustomerIDs = new ArrayList<>();
                for (MyCustomers data : adapter.lList) {
                    if (data.Customers.isCheck) {
                        lCustomerIDs.add(data.Customers.CustomerID);
                    }
                }
                int ArchiveType = Integer.parseInt(spinAdapter_SubAct.getItemString(spinArchive.getSelectedItemPosition(), "ArchiveTypeID"));
                String description = txtDescription.getText().toString();
                HashMap<String, Object> map = new HashMap<>();
                map.put("CustomerID", lCustomerIDs);
                map.put("ArchiveTypeID", ArchiveType);
                map.put("Description", description);
                int y = PD.getGrgYear();
                int m = PD.getGrgMonth();
                int d = PD.getGrgDay();
                map.put("ReturnDate", y + "-" + m + "-" + d + "T00:00:00");

                Call ChangeStates = rInterface.RQAddCustomersToArchive(Setting.getToken(), map);
                ChangeStates.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    HideStatusBar();
                                    Toast.makeText(context, "فروشگاه ها بایگانی شدند", Toast.LENGTH_LONG).show();
                                    aDialog.dismiss();
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
        });
        btnCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aDialog.dismiss();
            }
        });
        aDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCustomers(page);
    }

    private static int page = 0;

    private void getCustomers(int mPage) {
        try {
            rInterface = Retrofite.getClient().create(RWInterface.class);

            HashMap<String, Object> map = new HashMap<>();
            map.put("Page", mPage);

            Call cGetAll = rInterface.RQGetCustomerAllData(Setting.getToken(), map);
            cGetAll.enqueue(new Callback<List<MyCustomers>>() {
                @Override
                public void onResponse(Call<List<MyCustomers>> call, Response<List<MyCustomers>> response) {
                    if (response.isSuccessful()) {
                        lCustomer.addAll(response.body());
                        adapter.lList = lCustomer;
                        page++;
                        loading = true;

                        if (swipeRefresher.isRefreshing())
                            swipeRefresher.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    if (swipeRefresher.isRefreshing())
                        swipeRefresher.setRefreshing(false);
                }
            });
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }

    private static boolean statusState = false;

    public void ShowStatusBar() {
        if (statusState) {
            linStatusBar.setVisibility(View.GONE);
            linSearch.setVisibility(View.VISIBLE);
            statusState = false;
        } else {
            linStatusBar.setVisibility(View.VISIBLE);
            linSearch.setVisibility(View.GONE);
            statusState = true;
        }
    }

    private void HideStatusBar() {
        linStatusBar.setVisibility(View.GONE);
        linSearch.setVisibility(View.VISIBLE);
        statusState = false;
        Selectable = false;
        for (MyCustomers data : adapter.lList) {
            data.Customers.isCheck = false;
        }
        adapter.notifyDataSetChanged();
    }

    private void RefreshList() {
        adapMainCustomers adapter = new adapMainCustomers(lCustomer2, getActivity());
        lstMain.setAdapter(adapter);
    }

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    public void startVoiceRecognitionActivity() {
        new VoiceType(context, VOICE_RECOGNITION_REQUEST_CODE);
    }

    private List<DataDates> Day() {
        List<DataDates> mDay = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            DataDates data = new DataDates();
            String nn = "";
            if (i < 10)
                nn = "0" + i;
            else
                nn = Integer.toString(i);
            data.name(nn);
            mDay.add(data);
        }
        return mDay;
    }

    private List<DataDates> Month() {
        List<DataDates> mMonth = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            DataDates data = new DataDates();
            String nn = "";
            if (i < 10)
                nn = "0" + i;
            else
                nn = Integer.toString(i);
            data.name(nn);
            mMonth.add(data);
        }
        return mMonth;
    }

    private List<DataDates> Year() {
        List<DataDates> mYear = new ArrayList<>();
        for (int i = 1370; i < 1451; i++) {
            DataDates data = new DataDates();
            String nn = "";
            if (i < 10)
                nn = "0" + i;
            else
                nn = Integer.toString(i);
            data.name(nn);
            mYear.add(data);
        }
        return mYear;
    }

    private static void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        lstMarketers.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    private static void slideDown(final View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                lstMarketers.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    public static void ShowMarketers(List<MarketerUserAccessProfile> mProfiles){
        lstMarketers.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
        lstMarketers.setHasFixedSize(true);
        lstMarketers.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMarketers.setItemAnimator(new DefaultItemAnimator());

        adapterMarketers = new adapMainCustomerMarketers(mProfiles, context);
        lstMarketers.setAdapter(adapterMarketers);

        slideUp(ViewMarketerList);
    }
    public static void closeView(){
        slideDown(ViewMarketerList);
    }
}
