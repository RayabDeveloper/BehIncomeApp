package com.behincom.behincome.Activityes.Activities;


import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Adapters.Customer.adapStorePic;
import com.behincom.behincome.Adapters.Customer.adapStoreShowPersonels;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.TaskConditions;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Customer.CustomerPersonnel;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Keys.FragmentState;
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

public class fragTaskShow extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    Dialog pDialog;
    android.app.Dialog delDialog;
    RWInterface rInterface;
    adapStoreShowPersonels adapter_Personels;
    actActivities act = new actActivities();

    TextView lblTitle;
    TextView lblDetailTitle;
    TextView lblName;
    TextView lblCondition;
    TextView lblAddress;
    TextView lblTask;
    TextView lblTaskType;
    TextView lblTaskType1;
    TextView lblTaskDate;
    TextView lblTaskDate1;
    TextView lblTaskDescription;
    TextView lblTaskDescription1;
    TextView lblAct;
    TextView lblActDate;
    TextView lblActDate1;
    TextView lblActEndDate;
    TextView lblActEndDate1;
    TextView lblActCondition;
    TextView lblActCondition1;
    ImageView btnMapShow;
    ImageView imgBack;
    ImageView btnCheck;
    ImageView btnEdit;
    ImageView btnDelete;
    FloatingActionButton btnAddTask;
    LinearLayout linToolbar;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView lstPersonels;


    public static int CustomerId = 0;
    public static Customers Customer = new Customers();
    public static Activities lData = new Activities();
    private List<CustomerPersonnel> lPersonel = new ArrayList<>();

    public static fragTaskShow newInstance(Context mContext) {
        fragTaskShow fragment = new fragTaskShow();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_task_show, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblDetailTitle = view.findViewById(R.id.lblDetailTitle);
        lblName = view.findViewById(R.id.lblName);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblCondition = view.findViewById(R.id.lblCondition);
        lblAddress = view.findViewById(R.id.lblAddress);
        lblTask = view.findViewById(R.id.lblTask);
        lblTaskType = view.findViewById(R.id.lblTaskType);
        lblTaskType1 = view.findViewById(R.id.lblTaskType1);
        lblTaskDate = view.findViewById(R.id.lblTaskDate);
        lblTaskDate1 = view.findViewById(R.id.lblTaskDate1);
        lblTaskDescription = view.findViewById(R.id.lblTaskDescription);
        lblTaskDescription1 = view.findViewById(R.id.lblTaskDescription1);
        lblAct = view.findViewById(R.id.lblAct);
        lblActDate = view.findViewById(R.id.lblActDate);
        lblActDate1 = view.findViewById(R.id.lblActDate1);
        lblActEndDate = view.findViewById(R.id.lblActEndDate);
        lblActEndDate1 = view.findViewById(R.id.lblActEndDate1);
        lblActCondition = view.findViewById(R.id.lblActCondition);
        lblActCondition1 = view.findViewById(R.id.lblActCondition1);
        btnMapShow = view.findViewById(R.id.btnMapShow);
        imgBack = view.findViewById(R.id.imgBack);
        btnAddTask = view.findViewById(R.id.btnAddTask);
        lstPersonels = view.findViewById(R.id.lstPersonels);

        init();

        lblTitle.setText("جزئیات وظیفه");
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        pDialog = new Dialog(context);
        if(lData.EnterDate != null){
            if(lData.EnterDate.length() < 5){
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }else{
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            }
        }else{
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        btnMapShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                actShowStoreOnMap.StoreName = Customer.name();//todo todo todo
//                actShowStoreOnMap.Loc = new LatLng(Customer.latitude(), Customer.longitude());
//                Intent intent = new Intent(context, actShowStoreOnMap.class);
//                startActivity(intent);//todo todo todo
            }
        });
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragAddTask.lData = lData;
//                fragAddTask.currentId = lData.ActivityID;
//                fragAddTask.Name = Customer.CustomerName;
//                fragAddTask.customer_id = Customer.CustomerID;
//                fragAddTask.toEdite = true;
//                fragAddTask.sResult = lData.ActivityResultID;
//
//                fragAddTask.TaskShowCustomerId = CustomerId;
//                fragAddTask.TaskShowCustomer = Customer;
//                fragAddTask.TaskShowlData = lData;
//                fragAddTask.TaskShowType = true;

                DateConverter DC = new DateConverter(lData.TodoDate, true);

//                fragAddTask.DateToSend = DC.getOnlyDate();
//                fragAddTask.TimeToSend = DC.getOnlyTime();
//                fragAddTask.todoDate = lData.TodoDate;
//
//                fragAddTask.Type = getType(lData.StateID);

                act.getFragByState(FragmentState.AddTask);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragSetTimeEdit.Activityes = lData;
                fragSetTimeEdit.CustomerId = CustomerId;
                fragSetTimeEdit.Customer = Customer;
                act.getFragByState(FragmentState.SetTimeEdit);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delDialog = new android.app.Dialog(context);
                delDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                delDialog.setCancelable(true);
                delDialog.setCanceledOnTouchOutside(true);
                delDialog.setContentView(R.layout.dialog_accept_cancell);
                Objects.requireNonNull(delDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView lblYes = delDialog.findViewById(R.id.lblAccept);
                TextView lblNo = delDialog.findViewById(R.id.lblCancell);
                TextView lblTitle = delDialog.findViewById(R.id.lblTitle);

                lblTitle.setText("آیا مایل به حذف این وظیفه هستید ؟");

                lblYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rInterface = Retrofite.getClient().create(RWInterface.class);
                        pDialog = new Dialog(context);
                        pDialog.Show();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("ActivityID", lData.ActivityID);

                        Call getDeleteTask = rInterface.RQDeleteTask(Setting.getToken(), map);
                        getDeleteTask.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if(response.isSuccessful()){
                                    getActivity().finish();
                                }
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                pDialog.DisMiss();
                            }
                        });
                        delDialog.dismiss();
                    }
                });
                lblNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delDialog.dismiss();
                    }
                });
                delDialog.show();
            }
        });

        return view;
    }
    private int getType(int Typer) {
        switch (Typer) {//todo todo todo Check Typer
            case 1://113001
                return 1;
            case 2://113002
                return 2;
            case 6://113006
                return 4;
            default:
                return 3;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        rInterface = Retrofite.getClient().create(RWInterface.class);
        RefreshCondition();
    }
    private void init() {
        lstPersonels.setNestedScrollingEnabled(false);
        lstPersonels.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstPersonels.setLayoutManager(mLayoutManager);
        lstPersonels.addItemDecoration(ItemDecoration.getDecoration(context));
        lstPersonels.setItemAnimator(new DefaultItemAnimator());
    }
    private void RefreshPersonels() {
        adapter_Personels = new adapStoreShowPersonels(lPersonel, context);
        lstPersonels.setAdapter(adapter_Personels);
    }
    private void RefreshCondition() {

        pDialog.Show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("CustomerID", CustomerId);

        Call getCustomer = rInterface.RQGetCustomer(Setting.getToken(), map);
        getCustomer.enqueue(new Callback<Customers>() {
            @Override
            public void onResponse(Call<Customers> call, Response<Customers> response) {
                if (response.isSuccessful()) {
                    try {
                        Customer = response.body();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (Customer.CustomerLatitude > 1 && Customer.CustomerLongitude > 1)
                                    btnMapShow.setVisibility(View.VISIBLE);
                                else btnMapShow.setVisibility(View.GONE);
                                lblName.setText(Customer.CustomerName);
                                List<Basic_CustomerStates> lCustomerStates = geter.getList(Basic_CustomerStates.class, "WHERE CustomerStateID='" + Customer.CustomerStateID + "'");
                                lblCondition.setText(lCustomerStates.get(0).CustomerStateTitle);
                                if (Customer.CustomerLatitude > 20 && Customer.CustomerLongitude > 20)
                                    btnMapShow.setVisibility(View.VISIBLE);
                                else btnMapShow.setVisibility(View.GONE);
                                lblAddress.setText(Customer.CustomerAddress);
                                lPersonel = Customer.Customers_Personnel;
                                RefreshPersonels();

                                List<Basic_ActResults> lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
                                List<Basic_ActivityFields> lSubAct = new ArrayList<>();
                                for (Basic_ActResults data : lSubActResult) {
                                    List<Basic_ActivityFields> lFild = geter.getList(Basic_ActivityFields.class, "WHERE ActivityFieldID='" + data.ActID + "'");
                                    for (Basic_ActivityFields mData : lFild) {
                                        lSubAct.add(mData);
                                    }
                                }

                                if (lSubAct.size() > 0)
                                    lblTaskType1.setText(lSubAct.get(0).ActivityFieldTitle);
                                else lblTaskType1.setText("منسوخ شده");
                                DateConverter DC = new DateConverter(lData.TodoDate, true);
                                String DateTime = DC.getStringLongDateTime(true);

                                String[] dater = lData.DurationDate.split("T");
                                String[] timer = dater[1].split(":");
                                String durMin = timer[1];

                                lblTaskDate1.setText(DateTime + " به مدت " + durMin);//todo todo todo Duration Change to DateTime
                                lblTaskDescription1.setText((lData.ActivityDescription != null ? lData.ActivityDescription : "وارد نشده"));
                                String EntryDate = "ورود نشده";
                                if (lData.EnterDate != null) {
                                    DC = new DateConverter(lData.EnterDate, true);
                                    EntryDate = DC.getStringLongDateTime(true);
                                }
                                lblActDate1.setText(EntryDate);
                                String ExitDate = "خروج نشده";
                                if (lData.ExitDate != null) {
                                    DC = new DateConverter(lData.ExitDate, true);
                                    ExitDate = DC.getStringLongDateTime(true);
                                }
                                TaskConditions tCond = new TaskConditions(lData.StateID);
                                lblActEndDate1.setText(ExitDate);
                                lblActCondition1.setText(tCond.getCondition());
                                if (lData.StateID == 4)//todo todo todo Check StateID//113004
                                    btnAddTask.setVisibility(View.GONE);
                                else btnAddTask.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
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
    private void Finisher(){
        CustomerId = 0;
        Customer = new Customers();
        lData = new Activities();
        lPersonel = new ArrayList<>();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }

}
