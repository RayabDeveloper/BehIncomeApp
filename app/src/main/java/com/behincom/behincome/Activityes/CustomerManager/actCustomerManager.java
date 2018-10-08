package com.behincom.behincome.Activityes.CustomerManager;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.behincom.behincome.Activityes.Setting.fragBasicData;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;

public class actCustomerManager extends AppCompatActivity {

    static FragmentManager manager;
    static Context context;
    RSQLGeter<Object> geter = new RSQLGeter<>();

    static FrameLayout frameLayout;
    public static FragmentState STATE = FragmentState.CustomerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_manager);

        frameLayout = findViewById(R.id.frameLayout);

        manager = getSupportFragmentManager();
        context = this;

        getFragByState(FragmentState.CustomerManager);

    }

    public void getFragByState(FragmentState mState){
        fragBasicData frag = new fragBasicData();
        switch (mState){
            case CustomerManager:
                addFragCustomerManager();
                STATE = FragmentState.CustomerManager;
                break;
            case ArchiveEnter:
                addFragArchiveEnter();
                STATE = FragmentState.ArchiveEnter;
                break;
            case ArchiveExit:
                addFragArchiveExit();
                STATE = FragmentState.ArchiveExit;
                break;
        }
    }

    private void addFragCustomerManager(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCustomerManager.newInstance(context));
        transaction.commit();
    }
    private void addFragArchiveEnter(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragArchiveEnter.newInstance(context));
        transaction.commit();
    }
    private void addFragArchiveExit(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragArchiveExit.newInstance(context));
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(STATE == FragmentState.AddProducts){
            getFragByState(FragmentState.Products);
        }else if(STATE == FragmentState.AddCommissionPeriods){
            getFragByState(FragmentState.CommissionPeriods);
        }else if(STATE == FragmentState.AddVisitTour){
            getFragByState(FragmentState.VisitTours);
        }else if(STATE != FragmentState.Setting){
            getFragByState(FragmentState.Setting);
        }else {
            super.onBackPressed();
            finish();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("STATE_Setting", STATE.toString());
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getFragByState(FragmentState.valueOf(savedInstanceState.getString("STATE_Setting")));
    }

}
