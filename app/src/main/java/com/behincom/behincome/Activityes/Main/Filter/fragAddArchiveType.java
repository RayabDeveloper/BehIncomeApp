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
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class fragAddArchiveType extends Fragment {

    static Context context;
    static RSQLGeter geter = new RSQLGeter();
    static adapAddArchiveType adapterSub;
    static actMain act = new actMain();

    ImageView btnCheck, imgBack;
    TextView lblTitle;
    RecyclerView lstSub;
    CardView cardSubmit;

    private static List<Basic_ArchiveTypes> lTag = geter.getListIsCheck(Basic_ArchiveTypes.class);
    protected static List<Basic_ArchiveTypes> lTagForCustomer = new ArrayList<>();
    private static List<Basic_ArchiveTypes> lTagForCustomerBackup = new ArrayList<>();

    public static fragAddArchiveType newInstance(Context mContext, List<Basic_ArchiveTypes> lTager) {
        fragAddArchiveType fragment = new fragAddArchiveType();
        context = mContext;
        lTagForCustomer = lTager;
        lTagForCustomerBackup.addAll(lTager);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_archive_type, container, false);

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
        for (Basic_ArchiveTypes data : lTag) {
            for (Basic_ArchiveTypes des : lTagForCustomer) {
                if (data.ArchiveTypeID == des.ArchiveTypeID)
                    data.isCheck = true;
            }
        }
        adapterSub = new adapAddArchiveType(lTag, lTagForCustomer, context);
        lstSub.setAdapter(adapterSub);

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(lTagForCustomer);
            }
        });

        return view;
    }

    protected static void onBackPressed(List<Basic_ArchiveTypes> lList){
        lTag = new ArrayList<>();
        lTagForCustomer = new ArrayList<>();
        lTagForCustomerBackup = new ArrayList<>();

        fragAddCustomerFilter.lArchiveTypes = lList;
        act.getFragByState(FragmentState.AddFilter);
    }

}
