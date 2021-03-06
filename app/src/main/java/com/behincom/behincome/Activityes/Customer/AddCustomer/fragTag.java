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
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerTagMain;
import com.behincom.behincome.Adapters.Customer.AddCustomer.adapAddCustomerTagSub;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class fragTag extends Fragment {

    static Context context;
    static RSQLGeter geter = new RSQLGeter();
    adapAddCustomerTagMain adapterMain;
    static adapAddCustomerTagSub adapterSub;
    static actCustomer act = new actCustomer();

    ImageView btnCheck, imgBack;
    TextView lblTitle;
    RecyclerView lstMain;
    static RecyclerView lstSub;
    CardView cardSubmit;

    private List<Basic_TagGroups> lTagGroup = new ArrayList<>();
    private static List<Basic_Tags> lTag = geter.getListIsCheck(Basic_Tags.class, " WHERE Deleted='0'");
    private static List<Basic_TagGroups> lTagGroupForCustomer = new ArrayList<>();
    public static List<Basic_Tags> lTagForCustomer = new ArrayList<>();
    private static List<Basic_Tags> lTagForCustomerBackup = new ArrayList<>();

    public static int position = 0, MainIDSelected = 0;

    public static fragTag newInstance(Context mContext, List<Basic_Tags> lTager) {
        fragTag fragment = new fragTag();
        context = mContext;
        lTagForCustomer = lTager;
        lTagForCustomerBackup.addAll(lTager);
        for (Basic_Tags data : lTager) {
            List<Basic_TagGroups> lGroup = geter.getList(Basic_TagGroups.class, " WHERE TagGroupID='" + data.TagGroupID + "' AND Deleted='0'");
            if(lGroup.size() > 0)
                lTagGroupForCustomer.add(lGroup.get(0));
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tag, container, false);

        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblTitle = view.findViewById(R.id.lblTitle);
        lstMain = view.findViewById(R.id.lstMain);
        lstSub = view.findViewById(R.id.lstSub);
        cardSubmit = view.findViewById(R.id.cardSubmit);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("انتخاب برچسب");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lTagForCustomerBackup);
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

        lTag = geter.getListIsCheck(Basic_Tags.class, " WHERE Deleted='0'");
        for (Basic_Tags data : lTag) {
            List<Basic_TagGroups> lGrop = geter.getList(Basic_TagGroups.class, " WHERE TagGroupID='" + data.TagGroupID + "' AND Deleted='0'");
            boolean isIn = false;
            for (Basic_TagGroups mData : lTagGroup){
                if(mData.TagGroupID == lGrop.get(0).TagGroupID) {
                    isIn = true;
                    break;
                }
            }
            if(!isIn)
                lTagGroup.add(lGrop.get(0));
        }

        adapterMain = new adapAddCustomerTagMain(lTagGroup, context);
        RSQLite SQL = new RSQLite();
        lTag = SQL.Select("SELECT TagID, TagGroupID, TagTitle, TagOrder, Deleted, 'false' as isCheck FROM Basic_Tags WHERE isCheck='1' AND TagGroupID='" + lTagGroup.get(0).TagGroupID + "'", Basic_Tags.class);
        for (Basic_Tags data : lTag) {
            for (Basic_Tags des : lTagForCustomer) {
                if (data.TagID == des.TagID)
                    data.isCheck = true;
            }
        }
        adapterSub = new adapAddCustomerTagSub(lTag, lTagForCustomer, context);
        lstMain.setAdapter(adapterMain);
        lstSub.setAdapter(adapterSub);

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lTagForCustomer);
            }
        });

        return view;
    }

    public static void refreshTags(int TagGroupID){
        RSQLite SQL = new RSQLite();
        lTag = SQL.Select("SELECT TagID, TagGroupID, TagTitle, TagOrder, Deleted, 'false' as isCheck FROM Basic_Tags WHERE isCheck='1' AND TagGroupID='" + TagGroupID + "'", Basic_Tags.class);
        for (Basic_Tags data : lTag) {
            for (Basic_Tags des : lTagForCustomer) {
                if (data.TagID == des.TagID)
                    data.isCheck = true;
            }
        }
        adapterSub = new adapAddCustomerTagSub(lTag, lTagForCustomer, context);
        lstSub.setAdapter(adapterSub);
    }

    public static void onBackPressed(List<Basic_Tags> lList){
        lTag = new ArrayList<>();
        lTagGroupForCustomer = new ArrayList<>();
        lTagForCustomer = new ArrayList<>();
        lTagForCustomerBackup = new ArrayList<>();

        position = 0;
        MainIDSelected = 0;

        fragAddCustomer.lTags = lList;
        fragAddCustomer.goingToBigMap = false;
        act.getFragByState(FragmentState.AddCustomer);
        //todo Back
    }

}
