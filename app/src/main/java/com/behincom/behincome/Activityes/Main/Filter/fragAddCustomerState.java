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
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class fragAddCustomerState extends Fragment {

    static Context context;
    static RSQLGeter geter = new RSQLGeter();
    static adapAddCustomerState adapterSub;
    static actMain act = new actMain();

    ImageView btnCheck, imgBack;
    TextView lblTitle;
    RecyclerView lstSub;
    CardView cardSubmit;

    private static List<Basic_CustomerStates> lTag = geter.getListIsCheck(Basic_CustomerStates.class);
    protected static List<Basic_CustomerStates> lTagForCustomer = new ArrayList<>();
    private static List<Basic_CustomerStates> lTagForCustomerBackup = new ArrayList<>();

    public static fragAddCustomerFilter newInstance(Context mContext, List<Basic_CustomerStates> lTager) {
        fragAddCustomerFilter fragment = new fragAddCustomerFilter();
        context = mContext;
        lTagForCustomer = lTager;
        lTagForCustomerBackup.addAll(lTager);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_customer_state, container, false);

        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblTitle = view.findViewById(R.id.lblTitle);
        cardSubmit = view.findViewById(R.id.cardSubmit);
        lstSub = view.findViewById(R.id.lstSub);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("انتخاب برچسب");
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
        lTag = SQL.Select("SELECT ArchiveTypeID, ArchiveTypeUserId, ArchiveTypeTitle, ArchiveTypeOrder, ArchiveTypeFontIcon, ArchiveTypeColor, AdjustedByAdmin, Deleted, 'false' as isCheck FROM Basic_Tags WHERE isCheck='1'", Basic_ArchiveTypes.class);
        for (Basic_CustomerStates data : lTag) {
            for (Basic_CustomerStates des : lTagForCustomer) {
                if (data.CustomerStateID == des.CustomerStateID)
                    data.isCheck = true;
            }
        }
        adapterSub = new adapAddCustomerState(lTag, lTagForCustomer, context);
        lstSub.setAdapter(adapterSub);

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lTagForCustomer);
            }
        });

        return view;
    }

    protected static void onBackPressed(List<Basic_CustomerStates> lList){
        lTag = new ArrayList<>();
        lTagForCustomer = new ArrayList<>();
        lTagForCustomerBackup = new ArrayList<>();

        fragAddCustomerFilter.lCustomerStates = lList;
        act.getFragByState(FragmentState.AddFilter);
    }

}
