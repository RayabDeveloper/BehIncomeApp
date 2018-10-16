package com.behincom.behincome.Activityes;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragAddFactor;
import com.behincom.behincome.Activityes.Activities.fragAddTask;
import com.behincom.behincome.Activityes.Activities.fragAddTaskSetTime;
import com.behincom.behincome.Activityes.Activities.fragTaskShow;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendInvoiceImage;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragTest extends Fragment {

    static Context context;
    RSQLGeter geter = new RSQLGeter();
    DateConverter DC = new DateConverter();
    Dialog pDialog;
    SpinAdapter spinAdapter_SubAct, spinAdapter_Result;
    Time now;
    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
    actActivities act = new actActivities();
    android.app.Dialog mDialog;

    TextView lblTitle, lblEnterInfo, lblExitInfo, lblTaskInfo;
    ImageView btnCheck, imgBack, btnReNew;
    LinearLayout viewEnterExit, viewEnterShow, viewEnterInfo, viewExitShow, viewExitInfo, viewTaskShow, infoTask;
    RelativeLayout viewEnterExitEnd, viewTaskInfo;
    TextInputEditText txtTitle, txtDetails;
    Spinner spinSubAct, spinResult;
    RecyclerView lstFactor;
    FloatingActionButton btnNewInvoice;

    public static Activities Activity = new Activities();
    List<Basic_Acts> lSubAct = new ArrayList<>();
    List<Basic_ActResults> lSubActResult = new ArrayList<>();
    private static String Title = "", Description = "";
    private static int SelectedAct = 0, SelectedResult = 0;

    public static fragAddTask newInstance(Context mContext) {
        fragAddTask fragment = new fragAddTask();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_test, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        lblEnterInfo = view.findViewById(R.id.lblEnterInfo);
        lblExitInfo = view.findViewById(R.id.lblExitInfo);
        lblTaskInfo = view.findViewById(R.id.lblTaskInfo);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        btnReNew = view.findViewById(R.id.btnReNew);
        viewEnterExit = view.findViewById(R.id.viewEnterExit);
        viewEnterShow = view.findViewById(R.id.viewEnterShow);
        viewEnterInfo = view.findViewById(R.id.viewEnterInfo);
        viewExitShow = view.findViewById(R.id.viewExitShow);
        viewExitInfo = view.findViewById(R.id.viewExitInfo);
        viewTaskShow = view.findViewById(R.id.viewTaskShow);
        infoTask = view.findViewById(R.id.infoTask);
        viewEnterExitEnd = view.findViewById(R.id.viewEnterExitEnd);
        viewTaskInfo = view.findViewById(R.id.viewTaskInfo);
        txtTitle = view.findViewById(R.id.txtName);
        txtDetails = view.findViewById(R.id.txtDetails);
        spinSubAct = view.findViewById(R.id.spinSubAct);
        spinResult = view.findViewById(R.id.spinResult);
        lstFactor = view.findViewById(R.id.lstFactor);
        btnNewInvoice = view.findViewById(R.id.btnNewInvoice);

        ViewManager();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo Back to What ???
            }
        });
//Spinner Adapters
        lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
        for (Basic_ActResults data : lSubActResult) {
            List<Basic_Acts> lFild = geter.getList(Basic_Acts.class, "WHERE ActID='" + data.ActID + "'");
//            List<Basic_ActivityFields> lFild = SQL.Select("SELECT DISTINCT ActivityFieldID, ActivityFieldGroupID, ActivityFieldTitle, isCheck FROM Basic_ActivityFields WHERE ActivityFieldID='" + data.ActID + "'", Basic_ActivityFields.class);
            for (Basic_Acts mData : lFild) {
                lSubAct.add(mData);
            }
        }
        try {
            for (int i=0; i<lSubAct.size(); i++){
                try {
                    int ID = lSubAct.get(i).ActID;
                    for(int j=i+1; j<lSubAct.size(); j++){
                        try {
                            if(ID == lSubAct.get(j).ActID){
                                lSubAct.remove(j);
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
        Basic_Acts data = new Basic_Acts();
        data.ActTitle = ("نوع فعالیت");
        lSubAct.add(0, data);
        Basic_ActResults dataR = new Basic_ActResults();
        dataR.ActResultTitle = ("نتیجه");
        lSubActResult.add(0, dataR);
        spinAdapter_SubAct = new SpinAdapter(context, lSubAct, "ActTitle");
        spinAdapter_Result = new SpinAdapter(context, lSubActResult, "ActResultTitle");
        spinSubAct.setAdapter(spinAdapter_SubAct);
        spinResult.setAdapter(spinAdapter_Result);

        spinSubAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedAct = spinSubAct.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedResult = spinResult.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Title = txtTitle.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Description = txtDetails.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        viewEnterShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewExitShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewTaskShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddTaskSetTime.ActSelected = SelectedAct;
                fragAddTaskSetTime.Descriptioned = txtDetails.getText().toString();
                fragAddTaskSetTime.Nameioned = txtTitle.getText().toString();
                act.getFragByState(FragmentState.AddTaskSetTime);
            }
        });
        btnReNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewShower(viewEnterExit, viewEnterShow, viewTaskShow);
            }
        });
        btnNewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddFactor.Name = txtTitle.getText().toString();
                fragAddFactor.Details = txtDetails.getText().toString();
                fragAddFactor.enterTime = lblEnterInfo.getText().toString();
                fragAddFactor.spin1 = SelectedAct;
                fragAddFactor.Activity = Activity;
                fragAddFactor.spin2 = SelectedResult;

                act.getFragByState(FragmentState.AddFactor);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                ObjectMapper oMapper = new ObjectMapper();
                HashMap map = oMapper.convertValue(getActConfirmData(new ArrayList<ToSendInvoiceImage>()), HashMap.class);

                Call cConfirm = rInterface.RQConfirmActivity(Setting.getToken(), map);
                cConfirm.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            try {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        if (toEdite) {
//                                            fragTaskShow.lData.StateID = (113003);//todo todo todo check StateID
//                                        }
                                        Intent intent = new Intent(context, actMain.class);
                                        actMain.STATE = FragmentState.MainCustomers;
                                        getActivity().startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                            } catch (Exception Ex) {
                                String Er = Ex.getMessage();
                            }
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    mToast("خطا در برقراری ارتباط با سرور.");
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

        return view;
    }

    //View Manager
    private void ViewManager(){
        switch (getViewType()){
            case EnterShowTaskShow:
                viewShower(viewEnterExit, viewEnterShow, viewTaskShow);
                break;
            case EnterInfoExitShow:
                viewShower(viewEnterInfo, viewExitShow, viewTaskShow);
                break;
            case EnterInfoExitInfo:
                viewShower(viewEnterInfo, viewExitInfo, viewEnterExitEnd, viewTaskShow);
                break;
            case TaskShow:
                viewShower(viewTaskInfo);
                break;
        }
    }
    private ViewType getViewType(){
        if(Activity.EnterDate.length() == 0 && Activity.TodoDate.length() == 0)
            return ViewType.EnterShowTaskShow;
        else if(Activity.TodoDate.length() > 0 && Activity.ActivityID == 0)
            return ViewType.TaskShow;
        else if(Activity.EnterDate.length() > 0 && Activity.ExitDate.length() == 0)
            return ViewType.EnterInfoExitShow;
        else if(Activity.EnterDate.length() > 0 && Activity.ExitDate.length() > 0)
            return ViewType.EnterInfoExitInfo;

        return ViewType.EnterShowTaskShow;
    }
    private void viewShower(View... Views){
        for (View v : AllViews()) {
            for (View view : Views) {
                if(v == view)
                    v.setVisibility(View.VISIBLE);
            }
        }
    }
    private List<View> AllViews(){
        List<View> lView = new ArrayList<>();
        lView.add(viewEnterExit);
        lView.add(viewEnterShow);
        lView.add(viewEnterInfo);
        lView.add(viewExitShow);
        lView.add(viewExitInfo);
        lView.add(viewTaskShow);
        lView.add(infoTask);
        lView.add(viewEnterExitEnd);
        lView.add(viewTaskInfo);
        for (View view : lView) {
            view.setVisibility(View.GONE);
        }
        return lView;
    }
    private enum ViewType{
        EnterShowTaskShow,
        EnterInfoExitShow,
        EnterInfoExitInfo,
        TaskShow
    }



}
