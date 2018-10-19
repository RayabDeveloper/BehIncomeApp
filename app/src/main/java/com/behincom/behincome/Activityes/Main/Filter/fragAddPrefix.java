package com.behincom.behincome.Activityes.Main.Filter;


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

import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class fragAddPrefix extends Fragment {

    static Context context;
    static RSQLGeter geter = new RSQLGeter();
    static adapAddPrefix adapterSub;
    static actMain act = new actMain();

    ImageView btnCheck, imgBack;
    TextView lblTitle;
    RecyclerView lstSub;
    CardView cardSubmit;

    private static List<Basic_NamePrefixes> lTag = geter.getListIsCheck(Basic_NamePrefixes.class);
    protected static List<Basic_NamePrefixes> lTagForCustomer = new ArrayList<>();
    private static List<Basic_NamePrefixes> lTagForCustomerBackup = new ArrayList<>();

    public static fragAddPrefix newInstance(Context mContext, List<Basic_NamePrefixes> lTager) {
        fragAddPrefix fragment = new fragAddPrefix();
        context = mContext;
        lTagForCustomer = lTager;
        lTagForCustomerBackup.addAll(lTager);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_prefix, container, false);

        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblTitle = view.findViewById(R.id.lblTitle);
        cardSubmit = view.findViewById(R.id.cardSubmit);
        lstSub = view.findViewById(R.id.lstSub);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("انتخاب پیشوند مشتری");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lTagForCustomerBackup);
            }
        });

        lstSub.setLayoutManager(new LinearLayoutManager(context));
        lstSub.setHasFixedSize(true);
        lstSub.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.BaseBackgroundColor).size(2).build());
        lstSub.setItemAnimator(new DefaultItemAnimator());

        RSQLite SQL = new RSQLite();
        lTag = SQL.Select("SELECT NamePrefixID, NamePrefixTitle, NamePrefixOrder, NamePrefixFontIcon, NamePrefixColor, Deleted, 'false' as isCheck FROM Basic_NamePrefixes WHERE isCheck='0'", Basic_NamePrefixes.class);
        for (Basic_NamePrefixes data : lTag) {
            for (Basic_NamePrefixes des : lTagForCustomer) {
                if (data.NamePrefixID == des.NamePrefixID)
                    data.isCheck = true;
            }
        }
        adapterSub = new adapAddPrefix(lTag, lTagForCustomer, context);
        lstSub.setAdapter(adapterSub);

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lTagForCustomer = new ArrayList<>();
                for (Basic_NamePrefixes data : adapterSub.getList()) {
                    if(data.isCheck)
                        lTagForCustomer.add(data);
                }
                onBackPressed(lTagForCustomer);
            }
        });

        return view;
    }

    protected static void onBackPressed(List<Basic_NamePrefixes> lList){
        lTag = new ArrayList<>();
        lTagForCustomer = new ArrayList<>();
        lTagForCustomerBackup = new ArrayList<>();

        fragAddCustomerFilter.lNamePrefixes = new ArrayList<>();
        fragAddCustomerFilter.Filter.CustomerPrefixID = new ArrayList<>();
        for (Basic_NamePrefixes data : lList) {
            fragAddCustomerFilter.Filter.CustomerPrefixID.add(data.NamePrefixID);
        }
        act.addFilter(fragAddCustomerFilter.Filter);
    }

}
