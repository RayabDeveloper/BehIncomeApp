package com.behincom.behincome.Activityes.Customer.AddCustomer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Customer.actCustomer;
import com.behincom.behincome.Activityes.Customer.fragAddCustomer;
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerActivityFieldMain;
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerActivityFieldSub;
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerTagMain;
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerTagSub;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFieldGroups;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class fragActivityField extends Fragment {

    static Context context;
    static RSQLGeter geter = new RSQLGeter();
    adapAddCustomerActivityFieldMain adapterMain;
    static adapAddCustomerActivityFieldSub adapterSub;
    static actCustomer act = new actCustomer();

    ImageView btnCheck, imgBack;
    TextView lblTitle;
    RecyclerView lstMain;
    static RecyclerView lstSub;
    CardView cardSubmit;
    
    private List<Basic_ActivityFieldGroups> lActivityFieldGroup = new ArrayList<>();
    private static List<Basic_ActivityFields> lActivityField = geter.getListIsCheck(Basic_ActivityFields.class);
    private static List<Basic_ActivityFieldGroups> lActivityFieldGroupForCustomer = new ArrayList<>();
    public static List<Basic_ActivityFields> lActivityFieldForCustomer = new ArrayList<>();
    private static List<Basic_ActivityFields> lActivityFieldForCustomerBackup = new ArrayList<>();

    public static int position = 0, MainIDSelected = 0;

    public static fragActivityField newInstance(Context mContext, List<Basic_ActivityFields> lActivityFielder) {
        fragActivityField fragment = new fragActivityField();
        context = mContext;
        lActivityFieldForCustomer = lActivityFielder;
        lActivityFieldForCustomerBackup.addAll(lActivityFielder);
        for (Basic_ActivityFields data : lActivityFielder) {
            List<Basic_ActivityFieldGroups> lGroup = geter.getList(Basic_ActivityFieldGroups.class, " WHERE ActivityFieldGroupID='" + data.ActivityFieldGroupID + "'");
            if(lGroup.size() > 0)
                lActivityFieldGroupForCustomer.add(lGroup.get(0));
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_activity_field, container, false);

        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblTitle = view.findViewById(R.id.lblTitle);
        lstMain = view.findViewById(R.id.lstMain);
        lstSub = view.findViewById(R.id.lstSub);
        cardSubmit = view.findViewById(R.id.cardSubmit);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("انتخاب زمینه فعالیت");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lActivityFieldForCustomerBackup);
            }
        });

        lstMain.setLayoutManager(new LinearLayoutManager(context));
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtBlue).size(2).build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstSub.setLayoutManager(new LinearLayoutManager(context));
        lstSub.setHasFixedSize(true);
        lstSub.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.BaseBackgroundColor).size(2).build());
        lstSub.setItemAnimator(new DefaultItemAnimator());

        lActivityField = geter.getListIsCheck(Basic_ActivityFields.class);
        for (Basic_ActivityFields data : lActivityField) {
            List<Basic_ActivityFieldGroups> lGrop = geter.getList(Basic_ActivityFieldGroups.class, " WHERE ActivityFieldGroupID='" + data.ActivityFieldGroupID + "'");
            boolean isIn = false;
            for (Basic_ActivityFieldGroups mData : lActivityFieldGroup){
                if(mData.ActivityFieldGroupID == lGrop.get(0).ActivityFieldGroupID) {
                    isIn = true;
                    break;
                }
            }
            if(!isIn)
                lActivityFieldGroup.add(lGrop.get(0));
        }

        adapterMain = new adapAddCustomerActivityFieldMain(lActivityFieldGroup, context);
        RSQLite SQL = new RSQLite();
        lActivityField = SQL.Select("SELECT ActivityFieldID, ActivityFieldGroupID, ActivityFieldOrder, ActivityFieldTitle, ActivityFieldFontIcon, Deleted, 'false' as isCheck FROM Basic_ActivityFields WHERE isCheck='1' AND ActivityFieldGroupID='" + lActivityFieldGroup.get(0).ActivityFieldGroupID + "'", Basic_ActivityFields.class);
        for (Basic_ActivityFields data : lActivityField) {
            for (Basic_ActivityFields des : lActivityFieldForCustomer) {
                if(data.ActivityFieldID == des.ActivityFieldID)
                    data.isCheck = true;
            }
        }
        adapterSub = new adapAddCustomerActivityFieldSub(lActivityField, lActivityFieldForCustomer, context);
        lstMain.setAdapter(adapterMain);
        lstSub.setAdapter(adapterSub);

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lActivityFieldForCustomer);
            }
        });

        return view;
    }

    public static void refreshActivityFields(int ActivityFieldGroupID){
        RSQLite SQL = new RSQLite();
        lActivityField = SQL.Select("SELECT ActivityFieldID, ActivityFieldGroupID, ActivityFieldOrder, ActivityFieldTitle, ActivityFieldFontIcon, Deleted, 'false' as isCheck FROM Basic_ActivityFields WHERE isCheck='1' AND ActivityFieldGroupID='" + ActivityFieldGroupID + "'", Basic_ActivityFields.class);
        for (Basic_ActivityFields data : lActivityField) {
            for (Basic_ActivityFields des : lActivityFieldForCustomer) {
                if (data.ActivityFieldID == des.ActivityFieldID)
                    data.isCheck = true;
            }
        }
        adapterSub = new adapAddCustomerActivityFieldSub(lActivityField, lActivityFieldForCustomer, context);
        lstSub.setAdapter(adapterSub);
    }

    public static void onBackPressed(List<Basic_ActivityFields> lList){
        lActivityField = new ArrayList<>();
        lActivityFieldGroupForCustomer = new ArrayList<>();
        lActivityFieldForCustomer = new ArrayList<>();
        lActivityFieldForCustomerBackup = new ArrayList<>();

        position = 0;
        MainIDSelected = 0;

        fragAddCustomer.lActivityFields = lList;
        fragAddCustomer.goingToBigMap = false;
        act.getFragByState(FragmentState.AddCustomer);
        //todo Back
    }

}
