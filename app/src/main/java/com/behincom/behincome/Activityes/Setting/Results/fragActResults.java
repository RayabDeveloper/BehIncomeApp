package com.behincom.behincome.Activityes.Setting.Results;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Setting.adapActResults;
import com.behincom.behincome.Adapters.Setting.adapActivityResultsMaker;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_ActResultsMaker;
import com.behincom.behincome.Datas.BaseData.Basic_ActGroups;
import com.behincom.behincome.Datas.DataTest;
import com.behincom.behincome.Datas.DataTest2;
import com.behincom.behincome.Datas.DataTest3;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.Marketing.ToSend.ToSendMarketingActivityResults;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragActResults extends Fragment {

    static Context context;
    private RSQLite SQL = new RSQLite();
    static RSQLGeter geter = new RSQLGeter();
    adapActResults adapterMain;
    adapActivityResultsMaker adapterSub;

    actSetting act = new actSetting();

    private static RecyclerView lstMain, lstSub;
    private ImageView btnCheck, imgBack;
    private TextView lblTitle;
    Switch sw;
    static LinearLayout linMain;

    private List<Basic_ActResults> lSubActResult = new ArrayList<>();
    private static List<Basic_ActResultsMaker> lMaker = new ArrayList<>();
    private static List<Basic_ActGroups> lGroup = new ArrayList<>();
    private static List<Basic_Acts> lActs = new ArrayList<>();

    public static int IdsToSend = 0;
    public static int Position = 0;
    private String Id = "0";

    public static fragActResults newInstance(Context mContext) {
        fragActResults fragment = new fragActResults();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_act_results, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        linMain = view.findViewById(R.id.linMain);
        lstSub = view.findViewById(R.id.lstSub);
        lstMain = view.findViewById(R.id.lstMain);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        sw = view.findViewById(R.id.sw);

        IdsToSend = 0;

        lblTitle.setText("انتخاب نتیجه");
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Setting);
            }
        });

        sw.setChecked(true);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    showMain();
                else
                    hideMain();
            }
        });

        return view;
    }
    public static void hideMain(){
        try {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linMain.getLayoutParams();
            params.width = 230;
            linMain.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showMain(){
        try {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linMain.getLayoutParams();
            params.width = params.MATCH_PARENT;
            linMain.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        lGroup = geter.getList(Basic_ActGroups.class, " WHERE Deleted='0'");
        if(lGroup.size() > 0) {
            adapterMain = new adapActResults(lGroup, context);
            lstMain.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            lstMain.setLayoutManager(mLayoutManager);
            lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
            lstMain.setItemAnimator(new DefaultItemAnimator());
            lstMain.setAdapter(adapterMain);

            RefreshSub(lGroup.get(0).ActGroupID);
        }
    }

    public void RefreshSub(int MainID){
        lMaker = new ArrayList<>();
        lActs = geter.getList(Basic_Acts.class, " WHERE ActGroupID='" + MainID + "' AND Deleted='0'");
        for (Basic_Acts data : lActs) {
            Basic_ActResultsMaker maker = new Basic_ActResultsMaker();
            maker.ActResultsType = (1);
            maker.ActResultsTitle = (data.ActTitle);
            maker.ActResultsID = (data.ActID);
            maker.ActID = (data.ActID);
            lMaker.add(maker);
            List<Basic_ActResults> lResult = geter.getList(Basic_ActResults.class, " WHERE ActID='" + data.ActID + "' AND Deleted='0'");
            for (Basic_ActResults rData : lResult) {
                Basic_ActResultsMaker mrData = new Basic_ActResultsMaker();
                mrData.ActResultsType = (2);
                mrData.ActResultsTitle = (rData.ActResultTitle);
                mrData.ActResultsID = (rData.ActResultID);
                mrData.ActTypeID = (rData.ActID);
                mrData.ActID = rData.ActID;
                mrData.Point = rData.Point;
                mrData.isCheck = (rData.isCheck);
                lMaker.add(mrData);
            }
        }

        adapterSub = new adapActivityResultsMaker(lMaker, context);
        lstSub.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        lstSub.setLayoutManager(mLayoutManager);
        lstSub.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
        lstSub.setItemAnimator(new DefaultItemAnimator());
        lstSub.setAdapter(adapterSub);
    }

}
