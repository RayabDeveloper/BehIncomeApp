package com.behincom.behincome.Activityes.Account;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Login.fragJustic;
import com.behincom.behincome.Activityes.UserManager.actUserManager;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.R;

public class fragAccount extends Fragment {

    @SuppressLint("StaticFieldLeak")
    static Context context;
    actProfile act = new actProfile();

    TextView lblProfile;
    TextView lblCreadit;
    TextView lblRequest;
    TextView lblInvate;
    TextView lblMarketers;
    TextView lblTitle;
    ImageView btnCheck, imgBack;
    LinearLayout btnRequest;
    LinearLayout btnCreadit;
    LinearLayout btnInvate;
    LinearLayout btnProfile;
    LinearLayout btnMarketers;

    public static fragAccount newInstance(Context mContext){
        fragAccount fragment = new fragAccount();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_account, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblMarketers = view.findViewById(R.id.lblMarketers);
        btnMarketers = view.findViewById(R.id.btnMarketers);
        lblProfile = view.findViewById(R.id.lblProfile);
        lblCreadit = view.findViewById(R.id.lblCreadit);
        lblRequest = view.findViewById(R.id.lblRequest);
        lblInvate = view.findViewById(R.id.lblInvate);
        btnRequest = view.findViewById(R.id.btnRequest);
        btnCreadit = view.findViewById(R.id.btnCreadit);
        btnInvate = view.findViewById(R.id.btnInvate);
        btnProfile = view.findViewById(R.id.btnProfile);

        lblTitle.setText("حساب کاربری");
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.ProfileSubmiter);
            }
        });
        btnMarketers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), actUserManager.class);
                actUserManager.STATE = FragmentState.Marketers;
                actUserManager.Type = 0;
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
