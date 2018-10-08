package com.behincom.behincome.Activityes.UserManager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.behincom.behincome.Activityes.Account.actProfile;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.R;

public class actUserManager extends AppCompatActivity {

    public static FragmentManager manager;
    static Context context;

    static FrameLayout frameLayout;
    public static FragmentState STATE = FragmentState.Marketers;
    public static int Type = 0;//0 = From fragAccount ( Need to Show + )
    //1 = From Assign
    //2 = From MarketerUsers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_manager);

        frameLayout = findViewById(R.id.frameLayout);

        manager = getSupportFragmentManager();
        context = this;

        getFragByState(STATE);

    }

    public void getFragByState(FragmentState mState){
        switch (mState){
            case Marketers:
                addMarketer();
                STATE = FragmentState.Marketers;
                break;
            case AddUser:
                AddUser();
                STATE = FragmentState.AddUser;
                break;
            case MarketerCondition:
                AddCondition();
                STATE = FragmentState.MarketerCondition;
                break;
            case MarketerRole:
                AddRole();
                STATE = FragmentState.MarketerRole;
                break;
            case MarketerAccesss:
                AddAccess();
                STATE = FragmentState.MarketerAccesss;
                break;
            case MarketerProfile:
                AddProfile();
                STATE = FragmentState.MarketerProfile;
                break;
        }
    }
    private void addMarketer(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragMarketers.newInstance(context));
        transaction.commit();
        fragMarketers fer = new fragMarketers();
        fer.Type = 0;
    }
    private void AddUser(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddUser.newInstance(context));
        transaction.commit();
    }
    private void AddCondition(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCondition.newInstance(context));
        transaction.commit();
    }
    private void AddRole(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragRole.newInstance(context));
        transaction.commit();
    }
    private void AddAccess(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAccess.newInstance(context));
        transaction.commit();
    }
    private void AddProfile(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragViewProfile.newInstance(context));
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("STATE_UserManager", STATE.toString());

        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getFragByState(FragmentState.valueOf(savedInstanceState.getString("STATE_UserManager")));
    }

    @Override
    public void onBackPressed() {
        switch (STATE){
            case Marketers:
                finish();
                super.onBackPressed();
                break;
            case AddUser:
                getFragByState(FragmentState.Marketers);
                break;
            case MarketerCondition:
                getFragByState(FragmentState.Marketers);
                break;
            case MarketerRole:
                getFragByState(FragmentState.Marketers);
                break;
            case MarketerAccesss:
                getFragByState(FragmentState.Marketers);
                break;
            case MarketerProfile:
                getFragByState(FragmentState.Marketers);
        }
    }
}
