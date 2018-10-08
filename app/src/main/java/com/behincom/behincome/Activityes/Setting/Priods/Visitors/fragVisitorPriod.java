package com.behincom.behincome.Activityes.Setting.Priods.Visitors;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Setting.adapPriod;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingVisitTours;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragVisitorPriod extends Fragment {

    static Context context;
    actSetting act  = new actSetting();
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    private Dialog pDialog;

    private RecyclerView lstMain;
    static ImageView imgHelp, btnCheck;
    static TextView lblHelp1;

    public static List<MarketingVisitTours> lPriod = new ArrayList<>();

    public static String AccountId = "0";

    public static fragVisitorPriod newInstance(Context mContext){
        fragVisitorPriod fragment = new fragVisitorPriod();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_visitor_priod, container, false);

        TextView lblTitle = view.findViewById(R.id.lblTitle);
        FloatingActionButton btnAddPriod = view.findViewById(R.id.btnAddPriod);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        lblHelp1 = view.findViewById(R.id.lblHelp1);
        imgHelp = view.findViewById(R.id.imgHelp);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);


//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblTitle.setTypeface(tFace);
//        lblTitle.setTypeface(tFace);

        lblTitle.setText("دوره ویزیتور");
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.GONE);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Setting);
            }
        });

        btnAddPriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddVisitorPriod.toEdite = false;
                act.getFragByState(FragmentState.AddVisitTour);
            }
        });
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddVisitorPriod.toEdite = false;
                act.getFragByState(FragmentState.AddVisitTour);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Setting);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        lPriod = geter.getList(MarketingVisitTours.class);
        RefreshList();
        if(lPriod.size() == 0) {
            lblHelp1.setVisibility(View.VISIBLE);
            imgHelp.setVisibility(View.VISIBLE);
        }else {
            lblHelp1.setVisibility(View.GONE);
            imgHelp.setVisibility(View.GONE);
        }
    }
    public static void RefreshManager(){
        if(lPriod.size() == 0) {
            lblHelp1.setVisibility(View.VISIBLE);
            imgHelp.setVisibility(View.VISIBLE);
        }else {
            lblHelp1.setVisibility(View.GONE);
            imgHelp.setVisibility(View.GONE);
        }
    }
    private void RefreshList(){
        adapPriod adapter = new adapPriod(lPriod);
        lstMain.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
    }

}
