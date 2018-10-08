package com.behincom.behincome.Activityes.Main;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Account.actProfile;
import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragAddTaskSetTime;
import com.behincom.behincome.Activityes.CustomerManager.actCustomerManager;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.FragmentAdapters.Main.FragmentAdapter;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.app.AppController;

import java.util.ArrayList;
import java.util.List;

public class fragMain extends Fragment {

    @SuppressLint("StaticFieldLeak")
    static Context context;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    static FragmentActivity fer;

    FragmentAdapter fAdapter;

    ViewPager pager;
    TextView lblHome, lblStoreIcon, lblReport, lblAccount, lblTitle, lblComission1, lblComission2,
            lblPoint1, lblPoint2, lblCreadit1, lblCreadit2;
    public static TextView lblMessageCounter, lblAlarmCounter;
    ImageView imgBack, imgHome, imgStore, imgReport, imgAccount, imgSetting, imgMessage, imgAlarm;
    LinearLayout btnHome, btnReport, btnAccount, btnStore;

    List<Fragment> lFrag = new ArrayList<>();

    public static fragMain newInstance(Context mContext){
        fragMain fragment = new fragMain();
        context = mContext;
        fer = (FragmentActivity)((Activity)context);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main, container, false);

        btnStore = view.findViewById(R.id.btnStore);
        btnHome = view.findViewById(R.id.btnHome);
        imgMessage = view.findViewById(R.id.imgMessage);
        lblMessageCounter = view.findViewById(R.id.lblMessageCounter);
        lblAlarmCounter = view.findViewById(R.id.lblAlarmCounter);
        imgAlarm = view.findViewById(R.id.imgAlarm);
        btnReport = view.findViewById(R.id.btnReport);
        btnAccount = view.findViewById(R.id.btnAccount);
        lblTitle = view.findViewById(R.id.lblTitle);
        lblComission1 = view.findViewById(R.id.lblComission1);
        lblComission2 = view.findViewById(R.id.lblComission2);
        lblPoint1 = view.findViewById(R.id.lblPoint1);
        lblPoint2 = view.findViewById(R.id.lblPoint2);
        lblCreadit1 = view.findViewById(R.id.lblCreadit1);
        lblCreadit2 = view.findViewById(R.id.lblCreadit2);
        lblHome = view.findViewById(R.id.lblHome);
        lblStoreIcon = view.findViewById(R.id.lblStoreIcon);
        lblReport = view.findViewById(R.id.lblReport);
        lblAccount = view.findViewById(R.id.lblAccount);
        imgHome = view.findViewById(R.id.imgHome);
        imgStore = view.findViewById(R.id.imgStore);
        imgAccount = view.findViewById(R.id.imgAccount);
        imgReport = view.findViewById(R.id.imgReport);
        imgSetting = view.findViewById(R.id.imgSetting);
        imgBack = view.findViewById(R.id.imgBack);
        pager = view.findViewById(R.id.viewpager);

//        Typeface tf = Typeface.createFromAsset(AppController.getContext.getAssets(), "fonts/ir_sans.ttf");
//        lblTitle.setTypeface(tf);lblComission1.setTypeface(tf);lblComission2.setTypeface(tf);
//        lblPoint1.setTypeface(tf);lblPoint2.setTypeface(tf);lblCreadit1.setTypeface(tf);
//        lblMessageCounter.setTypeface(tf);lblAlarmCounter.setTypeface(tf);
//        lblCreadit2.setTypeface(tf);lblHome.setTypeface(tf);
//        lblStoreIcon.setTypeface(tf);lblReport.setTypeface(tf);lblAccount.setTypeface(tf);


        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, actAlarm.class);
//                startActivity(intent);
            }
        });
        imgAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, actAlarm.class);
//                startActivity(intent);
            }
        });
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, actMessage.class);
//                startActivity(intent);
            }
        });
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pager.setCurrentItem(0);
                Intent intent = new Intent(context, actProfile.class);
                startActivity(intent);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pager.setCurrentItem(1);
//                Intent intent = new Intent(getActivity(), actCustomerManager.class);
//                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pager.setCurrentItem(3);
                Intent intent = new Intent(getActivity(), actSetting.class);
                startActivity(intent);
            }
        });
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                actPlusses.Type = 1;
//                Intent intent = new Intent(getActivity(), actPlusses.class);
                Intent intent = new Intent(getActivity(), actSetting.class);
                startActivity(intent);
            }
        });
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home));
//                        imgAccount.setImageDrawable(getResources().getDrawable(R.drawable.account_blue));
//                        imgReport.setImageDrawable(getResources().getDrawable(R.drawable.finance));
//                        imgStore.setImageDrawable(getResources().getDrawable(R.drawable.store));
//                        break;
//                    case 1:
//                        imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home));
//                        imgAccount.setImageDrawable(getResources().getDrawable(R.drawable.account));
//                        imgReport.setImageDrawable(getResources().getDrawable(R.drawable.report_blue));
//                        imgStore.setImageDrawable(getResources().getDrawable(R.drawable.store));
//                        break;
//                    case 2:
//                        imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home));
//                        imgAccount.setImageDrawable(getResources().getDrawable(R.drawable.account));
//                        imgReport.setImageDrawable(getResources().getDrawable(R.drawable.finance));
//                        imgStore.setImageDrawable(getResources().getDrawable(R.drawable.store_blue));
//                        break;
//                    case 3:
//                        imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_blue));
//                        imgAccount.setImageDrawable(getResources().getDrawable(R.drawable.account));
//                        imgReport.setImageDrawable(getResources().getDrawable(R.drawable.finance));
//                        imgStore.setImageDrawable(getResources().getDrawable(R.drawable.store));
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        try {
            lFrag = getFragments();
            fAdapter = new FragmentAdapter(fer.getSupportFragmentManager(), lFrag);
            pager.setAdapter(fAdapter);
            pager.setCurrentItem(1);
//            imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_blue));
//            imgAccount.setImageDrawable(getResources().getDrawable(R.drawable.account));
//            imgReport.setImageDrawable(getResources().getDrawable(R.drawable.finance));
//            imgStore.setImageDrawable(getResources().getDrawable(R.drawable.store));
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();
//        fList.add(fragAccount.newInstance(getActivity()));
//        fList.add(fragStoreDetails.newInstance(getActivity()));
        fList.add(fragCustomers.newInstance(getActivity()));
        fList.add(fragTasks.newInstance(getActivity()));
        return fList;

    }

}
