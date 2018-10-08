package com.behincom.behincome.Activityes.CustomerManager;


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

import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragAddTaskSetTime;
import com.behincom.behincome.Activityes.Login.fragJustic;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.R;

public class fragCustomerManager extends Fragment {

    static Context context;
    actCustomerManager act = new actCustomerManager();

    TextView lblTitle;
    ImageView imgBack, btnCheck;

    public static fragCustomerManager newInstance(Context mContext){
        fragCustomerManager fragment = new fragCustomerManager();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customer_manager, container, false);

        LinearLayout btnArchiveEnter = view.findViewById(R.id.btnArchiveEnter);
        LinearLayout btnArchiveExit = view.findViewById(R.id.btnArchiveExit);
        LinearLayout btnGroupTask = view.findViewById(R.id.btnGroupTask);
        TextView lblArchiveEnter = view.findViewById(R.id.lblArchiveEnter);
        TextView lblArchiveExit = view.findViewById(R.id.lblArchiveExit);
        TextView lblGroupTask = view.findViewById(R.id.lblGroupTask);
        lblTitle = view.findViewById(R.id.lblTitle);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);

//        Typeface tFace = Typeface.createFromAsset(getAssets(), "fonts/ir_sans.ttf");
//        lblArchiveEnter.setTypeface(tFace);
//        lblArchiveExit.setTypeface(tFace);
//        lblGroupTask.setTypeface(tFace);
//        lblTitle.setTypeface(tFace);

        lblTitle.setText("فروشگاه");
        imgBack.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.CustomerManager);
            }
        });

        btnArchiveEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.ArchiveEnter);
            }
        });
        btnArchiveExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.ArchiveExit);
            }
        });
        btnGroupTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), actActivities.class);
                fragAddTaskSetTime.mType = true;
                fragAddTaskSetTime.isForGroup = true;
                fragAddTaskSetTime.GroupStartEndType = 0;
                actActivities.STATE = FragmentState.AddTaskSetTime;
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
