package com.behincom.behincome.Activityes.Setting.Pdoructs;


import android.content.Context;
import android.graphics.Typeface;
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

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Setting.adapProduct;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class fragProducts extends Fragment {

    static Context context;
    actSetting act = new actSetting();
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    adapProduct adapter;
    Dialog pDialog;

    TextView lblTitle;
    TextView lblHelp1;
    ImageView imgHelp;
    TextView lblAccept;
    ImageView imgBack, btnCheck;
    RecyclerView lstMain;
    RecyclerView.LayoutManager mLayoutManager;
    CardView cardView;
    FloatingActionButton btnAddProduct;

    protected static List<MarketingProducts> lProduct = new ArrayList<>();

    String AccountId = "0";

    public static fragProducts newInstance(Context mContext){
        fragProducts fragment = new fragProducts();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_products, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblHelp1 = view.findViewById(R.id.lblHelp1);
        imgHelp = view.findViewById(R.id.imgHelp);
        imgBack = view.findViewById(R.id.imgBack);
        lstMain = view.findViewById(R.id.lstMain);
        cardView = view.findViewById(R.id.cardView);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);

//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblHelp1.setTypeface(tFace);
//        lblTitle.setTypeface(tFace);
//        lblAccept.setTypeface(tFace);

        lblTitle.setText("محصولات");
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.GONE);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Setting);
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.AddProducts);
            }
        });
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.AddProducts);
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
        lProduct = geter.getList(MarketingProducts.class);
        RefreshList();
        if(lProduct.size() == 0) {
            lblHelp1.setVisibility(View.VISIBLE);
            imgHelp.setVisibility(View.VISIBLE);
        }else {
            lblHelp1.setVisibility(View.GONE);
            imgHelp.setVisibility(View.GONE);
        }
    }
    private void RefreshList(){
        adapter = new adapProduct(lProduct);
        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
    }

}
