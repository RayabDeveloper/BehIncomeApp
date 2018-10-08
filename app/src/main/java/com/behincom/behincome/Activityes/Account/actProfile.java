package com.behincom.behincome.Activityes.Account;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.behincom.behincome.Activityes.Login.actLogin;
import com.behincom.behincome.Activityes.Login.fragLoginRequestPhone;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.R;

public class actProfile extends AppCompatActivity {

    public static FragmentManager manager;
    static Context context;

    static FrameLayout frameLayout;
    public static FragmentState STATE = FragmentState.Account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profile);

        frameLayout = findViewById(R.id.frameLayout);

        manager = getSupportFragmentManager();
        context = this;

        getFragByState(STATE);

    }

    public void getFragByState(FragmentState mState){
        switch (mState){
            case Account:
                addAccount();
                STATE = FragmentState.Account;
                break;
            case ProfileSubmiter:
                addFragProfileSubmiter();
                STATE = FragmentState.ProfileSubmiter;
                break;
            case Profile:
                addFragProfileSubmiter();
                STATE = FragmentState.ProfileSubmiter;
                break;
        }
    }
    private void addAccount(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAccount.newInstance(context));
        transaction.commit();
    }
    private void addFragProfileSubmiter(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragProfileSubmiter.newInstance(context));
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("STATE_Account", STATE.toString());

        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getFragByState(FragmentState.valueOf(savedInstanceState.getString("STATE_Account")));
    }
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed() {
        switch (STATE){
            case Account:
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    super.onBackPressed();
                    finish();
                    return;
                }
                else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                mBackPressed = System.currentTimeMillis();
                break;
            case ProfileSubmiter:
                getFragByState(FragmentState.Account);
        }
    }
}
