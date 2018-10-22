package com.behincom.behincome.Activityes.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Account.actProfile;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Activityes.UserManager.actUserManager;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

public class fragAccounts extends Fragment {

    static Context context;
    actMain act = new actMain();
    private RSQLGeter geter = new RSQLGeter();

    TextView lblProfile;
    TextView lblCreadit;
    TextView lblRequest;
    TextView lblInvate;
    TextView lblMarketers;
    TextView lblTitle;
    ImageView imgMessage;
    LinearLayout btnRequest;
    LinearLayout btnCreadit;
    LinearLayout btnInvate;
    LinearLayout btnProfile;
    LinearLayout btnMarketers;
    LinearLayout btnHome1, btnHome2, btnReport, btnAccount;
    TextView lblHome1, lblHome2, lblReport, lblAccount;
    ImageView imgHome1, imgHome2, imgReport, imgAccount;
    ImageView imgSetting;

    public static fragAccounts newInstance(Context mContext){
        fragAccounts fragment = new fragAccounts();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_accounts, container, false);

        imgMessage = view.findViewById(R.id.imgMessage);
        imgSetting = view.findViewById(R.id.imgSetting);
        btnHome1 = view.findViewById(R.id.btnHome1);
        btnHome2 = view.findViewById(R.id.btnHome2);
        btnReport = view.findViewById(R.id.btnReport);
        btnAccount = view.findViewById(R.id.btnAccount);
        lblHome1 = view.findViewById(R.id.lblHome1);
        lblHome2 = view.findViewById(R.id.lblHome2);
        lblReport = view.findViewById(R.id.lblReport);
        lblAccount = view.findViewById(R.id.lblAccount);
        imgHome1 = view.findViewById(R.id.imgHome1);
        imgHome2 = view.findViewById(R.id.imgHome2);
        imgReport = view.findViewById(R.id.imgReport);
        imgAccount = view.findViewById(R.id.imgAccount);
        lblTitle = view.findViewById(R.id.lblTitle);
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

        imgMessage.setVisibility(View.VISIBLE);
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MessageMain);
            }
        });

        imgSetting.setVisibility(View.VISIBLE);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actSetting.class);
                startActivity(intent);
            }
        });
        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MainTasks);
            }
        });
        btnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MainCustomers);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MainReports);
            }
        });
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                act.getFragByState(FragmentState.MainAccounts);
            }
        });

        lblTitle.setText("حساب کاربری");

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
            }
        });
        btnCreadit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Packages);
            }
        });

        return view;
    }

}
