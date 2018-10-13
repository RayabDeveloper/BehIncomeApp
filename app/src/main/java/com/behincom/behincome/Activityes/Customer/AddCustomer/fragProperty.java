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
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerPropertyMain;
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerPropertySub;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_PropertyGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class fragProperty extends Fragment {

    static Context context;
    static RSQLGeter geter = new RSQLGeter();
    adapAddCustomerPropertyMain adapterMain;
    static adapAddCustomerPropertySub adapterSub;
    static actCustomer act = new actCustomer();

    ImageView btnCheck, imgBack;
    TextView lblTitle;
    RecyclerView lstMain;
    static RecyclerView lstSub;
    CardView cardSubmit;

    private List<Basic_PropertyGroups> lPropertyGroup = new ArrayList<>();
    private static List<Basic_Properties> lProperties = geter.getListIsCheck(Basic_Tags.class);
    private static List<Basic_PropertyGroups> lPropertyGroupForCustomer = new ArrayList<>();
    public static List<Basic_Properties> lPropertiesForCustomer = new ArrayList<>();
    private static List<Basic_Properties> lPropertiesForCustomerBackup = new ArrayList<>();

    public static int position = 0, MainIDSelected = 0;

    public static fragProperty newInstance(Context mContext, List<Basic_Properties> lPropertieser) {
        fragProperty fragment = new fragProperty();
        context = mContext;
        lPropertiesForCustomer = lPropertieser;
        lPropertiesForCustomerBackup.addAll(lPropertieser);
        for (Basic_Properties data : lPropertieser) {
            List<Basic_PropertyGroups> lGroup = geter.getList(Basic_PropertyGroups.class, " WHERE PropertyGroupID='" + data.PropertyGroupID + "'");
            if(lGroup.size() > 0)
                lPropertyGroupForCustomer.add(lGroup.get(0));
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_property, container, false);

        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblTitle = view.findViewById(R.id.lblTitle);
        lstMain = view.findViewById(R.id.lstMain);
        lstSub = view.findViewById(R.id.lstSub);
        cardSubmit = view.findViewById(R.id.cardSubmit);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("ورود مشخصات");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lPropertiesForCustomerBackup);
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

        lProperties = geter.getListIsCheck(Basic_Properties.class);
        for (Basic_Properties data : lProperties) {
            List<Basic_PropertyGroups> lGrop = geter.getList(Basic_PropertyGroups.class, " WHERE PropertyGroupID='" + data.PropertyGroupID + "'");
            boolean isIn = false;
            for (Basic_PropertyGroups mData : lPropertyGroup){
                if(mData.PropertyGroupID == lGrop.get(0).PropertyGroupID) {
                    isIn = true;
                    break;
                }
            }
            if(!isIn)
                lPropertyGroup.add(lGrop.get(0));
        }

        adapterMain = new adapAddCustomerPropertyMain(lPropertyGroup, context);
        RSQLite SQL = new RSQLite();
        lProperties = SQL.Select("SELECT PropertyID, PropertyGroupID, PropertyTitle, PropertyDescription, PropertyOrder, PropertyTypeKeyBoardId, Deleted, 'false' as isCheck FROM Basic_Properties WHERE isCheck='1' AND PropertyGroupID='" + lPropertyGroup.get(0).PropertyGroupID + "'", Basic_Properties.class);
        for (Basic_Properties data : lProperties) {
            for (Basic_Properties des : lPropertiesForCustomer) {
                if(data.PropertyID == des.PropertyID)
                    data.PropertyDescription = des.PropertyDescription;
            }
        }
        adapterSub = new adapAddCustomerPropertySub(lProperties, lPropertiesForCustomer, context);
        lstMain.setAdapter(adapterMain);
        lstSub.setAdapter(adapterSub);

        refreshProperties(lPropertyGroup.get(0).PropertyGroupID);

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lPropertiesForCustomer);
            }
        });

        return view;
    }

    public static void refreshProperties(int PropertyGroupID){
        RSQLite SQL = new RSQLite();
        lProperties = SQL.Select("SELECT PropertyID, PropertyGroupID, PropertyTitle, PropertyDescription, PropertyOrder, PropertyTypeKeyBoardId, Deleted, 'false' as isCheck FROM Basic_Properties WHERE isCheck='1' AND PropertyGroupID='" + PropertyGroupID + "'", Basic_Properties.class);
        for (Basic_Properties data : lProperties) {
            for (Basic_Properties des : lPropertiesForCustomer) {
                if (data.PropertyID == des.PropertyID)
                    data.PropertyDescription = des.PropertyDescription;
            }
        }
        adapterSub = new adapAddCustomerPropertySub(lProperties, lPropertiesForCustomer, context);
        lstSub.setAdapter(adapterSub);
    }

    public static void onBackPressed(List<Basic_Properties> lList){
        lProperties = new ArrayList<>();
        lPropertyGroupForCustomer = new ArrayList<>();
        lPropertiesForCustomer = new ArrayList<>();
        lPropertiesForCustomerBackup = new ArrayList<>();

        position = 0;
        MainIDSelected = 0;

        fragAddCustomer.lProperties = lList;
        fragAddCustomer.goingToBigMap = false;
        act.getFragByState(FragmentState.AddCustomer);
        //todo Back
    }

}
