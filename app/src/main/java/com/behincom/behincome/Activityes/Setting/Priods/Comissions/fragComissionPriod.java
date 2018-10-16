package com.behincom.behincome.Activityes.Setting.Priods.Comissions;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Setting.adapCommissionPriod;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingCommissionPeriods;
import com.behincom.behincome.Datas.Marketing.MarketingVisitTours;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class fragComissionPriod extends Fragment {

    static Context context;
    actSetting act = new actSetting();
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();

    private RecyclerView lstMain;
    static ImageView imgHelp, btnCheck;
    static TextView lblHelp1;
    private FloatingActionButton btnAddPriod;

    public static List<MarketingCommissionPeriods> lCommissionPriod = new ArrayList<>();

    public static String AccountId = "0";

    public static fragComissionPriod newInstance(Context mContext){
        fragComissionPriod fragment = new fragComissionPriod();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_comission_priod, container, false);

        TextView lblTitle = view.findViewById(R.id.lblTitle);
        btnCheck = view.findViewById(R.id.btnCheck);
        btnAddPriod = view.findViewById(R.id.btnAddPriod);
        TextView lblAccept = view.findViewById(R.id.lblAccept);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        lblHelp1 = view.findViewById(R.id.lblHelp1);
        imgHelp = view.findViewById(R.id.imgHelp);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);
        CardView cardView = view.findViewById(R.id.cardView);

        lblTitle.setText("دوره کمیسیون");
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.GONE);

        lCommissionPriod = geter.getList(MarketingCommissionPeriods.class, " WHERE Deleted='0'");

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Setting);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Setting);
            }
        });
        btnAddPriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPriod();
            }
        });
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPriod();
            }
        });

        return view;
    }
    private void newPriod(){
        act.getFragByState(FragmentState.AddCommissionPeriods);
        if(lCommissionPriod.size() > 0) {
            String lDate = lCommissionPriod.get(lCommissionPriod.size() - 1).MarketingCommissionPeriodDateTo;
            String[] Dates = lDate.split("T");
            String[] mDates = Dates[0].split("-");
            int y = Integer.parseInt(mDates[0]);
            int m = Integer.parseInt(mDates[1]);
            int d = Integer.parseInt(mDates[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);
            calendar.add(Calendar.DATE, 1);
            String Date = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "T00:00:00";
            fragAddComissionPriod.LastDate = Date;
            fragAddComissionPriod.isFristDate = false;
        }else {
            fragAddComissionPriod.LastDate = Setting.getServerDateTime();
            fragAddComissionPriod.isFristDate = true;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        lCommissionPriod = geter.getList(MarketingCommissionPeriods.class, " WHERE Deleted='0'");
        RefreshList();
        if(lCommissionPriod.size() == 0) {
            lblHelp1.setVisibility(View.VISIBLE);
            imgHelp.setVisibility(View.VISIBLE);
        }else {
            lblHelp1.setVisibility(View.GONE);
            imgHelp.setVisibility(View.GONE);
        }
    }
    public static void RefreshManager(){
        if(lCommissionPriod.size() == 0) {
            lblHelp1.setVisibility(View.VISIBLE);
            imgHelp.setVisibility(View.VISIBLE);
        }else {
            lblHelp1.setVisibility(View.GONE);
            imgHelp.setVisibility(View.GONE);
        }
    }
    private void RefreshList(){
        adapCommissionPriod adapter = new adapCommissionPriod(lCommissionPriod);
        lstMain.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
    }

}
