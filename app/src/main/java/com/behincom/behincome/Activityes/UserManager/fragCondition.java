package com.behincom.behincome.Activityes.UserManager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Login.fragJustic;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragCondition extends Fragment {

    static Context context;
    RWInterface rInterface;
    Dialog pDialog;
    actUserManager act = new actUserManager();

    RadioButton radActive, radLock, radDeActive;
    LinearLayout btnTaskCount, btnCustomerCount, btnCustomerArchiveCount, btnAccessCount;
    TextView lblTaskCount, lblCustomerCount, lblCustomerArchiveCount, lblAccessCount, lblTitle;
    ImageView imgBack, btnCheck;

    public Marketers Marketer = new Marketers();
    public int TaskCount = 0;
    public int CustomerCount = 0;
    public int CustomerArchiveCOunt = 0;
    public int AcessCount = 0;

    private int StateID = 0;

    public static fragCondition newInstance(Context mContext){
        fragCondition fragment = new fragCondition();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_condition, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        radActive = view.findViewById(R.id.radActive);
        radLock = view.findViewById(R.id.radLock);
        radDeActive = view.findViewById(R.id.radDeActive);
        btnTaskCount = view.findViewById(R.id.btnTaskCount);
        btnCustomerCount = view.findViewById(R.id.btnCustomerCount);
        btnCustomerArchiveCount = view.findViewById(R.id.btnCustomerArchiveCount);
        btnAccessCount = view.findViewById(R.id.btnAccessCount);
        lblTaskCount = view.findViewById(R.id.lblTaskCount);
        lblCustomerCount = view.findViewById(R.id.lblCustomerCount);
        lblCustomerArchiveCount = view.findViewById(R.id.lblCustomerArchiveCount);
        lblAccessCount = view.findViewById(R.id.lblAccessCount);

        lblTitle.setText("وضعیت کارمند");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Marketers);
            }
        });

        lblTaskCount.setText(Integer.toString(TaskCount));
        lblCustomerCount.setText(Integer.toString(CustomerCount));
        lblCustomerArchiveCount.setText(Integer.toString(CustomerArchiveCOunt));
        lblAccessCount.setText(Integer.toString(AcessCount));

        radActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    StateID = 1;
            }
        });
        radLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    StateID = 2;
            }
        });
        radDeActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    StateID = 3;
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rInterface = Retrofite.getClient().create(RWInterface.class);
                pDialog = new Dialog(getActivity());
                pDialog.Show();

                HashMap<String, Object> map = new HashMap<>();
                map.put("UserID", Marketer.UserID);
                map.put("BusinessManagerMarketerStateID", StateID);

                Call cChangeState = rInterface.RQChangeMarketerCondition(Setting.getToken(), map);
                cChangeState.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            act.getFragByState(FragmentState.Marketers);
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        pDialog.DisMiss();
                    }
                });
            }
        });

        return view;
    }

}
