package com.behincom.behincome.Activityes.Setting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.R;

public class fragSetting extends Fragment {

    static Context context;
    actSetting ACTSetting = new actSetting();

    TextView lblPoint;
    TextView lblComission;
    TextView lblProduct;
    TextView lblAddress;
    TextView lblTag;
    TextView lblDetail;
    TextView lblPriod;
    TextView lblCommissionPriod;
    TextView lblSubActResult;
    TextView lblActvityField;
    TextView lblTitle;
    TextView lblCustomerState;
    ImageView imgBack;
    ImageView btnCheck;
    LinearLayout btnCustomerState;
    LinearLayout btnPoint;
    LinearLayout btnDetail;
    LinearLayout btnAddress;
    LinearLayout btnPeriod;
    LinearLayout btnCommissionPeriod;
    LinearLayout btnProduct;
    LinearLayout btnComission;
    LinearLayout btnActivityResults;
    LinearLayout btnTag;
    LinearLayout btnActvityField;

    public static fragSetting newInstance(Context mContext){
        fragSetting fragment = new fragSetting();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_setting, container, false);

        //Define Elements
        btnCustomerState = view.findViewById(R.id.btnCustomerState);
        lblCustomerState = view.findViewById(R.id.lblCustomerState);
        lblTitle = view.findViewById(R.id.lblTitle);
        lblCommissionPriod = view.findViewById(R.id.lblCommissionPriod);
        btnCommissionPeriod = view.findViewById(R.id.btnCommissionPeriod);
        imgBack = view.findViewById(R.id.imgBack);
        lblPoint = view.findViewById(R.id.lblPoint);
        lblProduct = view.findViewById(R.id.lblProduct);
        lblComission = view.findViewById(R.id.lblComission);
        lblAddress = view.findViewById(R.id.lblAddress);
        lblTag = view.findViewById(R.id.lblTag);
        lblDetail = view.findViewById(R.id.lblDetail);
        lblPriod = view.findViewById(R.id.lblPriod);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblSubActResult = view.findViewById(R.id.lblSubActResult);
        lblActvityField = view.findViewById(R.id.lblActvityField);
        btnPoint = view.findViewById(R.id.btnPoint);
        btnDetail = view.findViewById(R.id.btnDetail);
        btnAddress = view.findViewById(R.id.btnAddress);
        btnPeriod = view.findViewById(R.id.btnPeriod);
        btnProduct = view.findViewById(R.id.btnProduct);
        btnComission = view.findViewById(R.id.btnComission);
        btnActivityResults = view.findViewById(R.id.btnActivityResults);
        btnTag = view.findViewById(R.id.btnTag);
        btnActvityField = view.findViewById(R.id.btnActvityField);

        //Define Basic Setting
        lblTitle.setText("تنظیمات");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btnCheck.setVisibility(View.GONE);

        //OnClick on Elements
        btnPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.VisitTours);
            }
        });
        btnCommissionPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.CommissionPeriods);
            }
        });
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.Products);
            }
        });
        btnComission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnActvityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.ActivityFields);
            }
        });
        btnActivityResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.ActivityResults);
            }
        });
        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.Tags);
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.Properties);
            }
        });
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.Cities);
            }
        });
        btnCustomerState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTSetting.getFragByState(FragmentState.CustomerState);
            }
        });

        return view;
    }

}
