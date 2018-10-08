package com.behincom.behincome.Activityes.Main;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Device;
import com.behincom.behincome.Activityes.Account.fragAccount;
import com.behincom.behincome.Activityes.Account.fragProfileSubmiter;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.R;

import java.util.ArrayList;

import static com.behincom.behincome.Activityes.Main.fragCustomers.VOICE_RECOGNITION_REQUEST_CODE;

public class actMain extends AppCompatActivity {

    static FragmentManager manager;
    static Context context;

    static FrameLayout frameLayout;
    public static FragmentState STATE = FragmentState.MainTasks;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        frameLayout = findViewById(R.id.frameLayout);

        context = this;

        manager = getSupportFragmentManager();
        context = this;

        getFragByState(FragmentState.MainTasks);

    }

    public void getFragByState(FragmentState mState){
        switch (mState){
            case MainEmpty:
                addMain();
                break;
            case MainTasks:
                addFragTask();
                break;
            case MainCustomers:
                addFragCustomer();
                break;
            case MainReports:
                addFragReport();
                break;
            case MainAccounts:
                addFragAccount();
                break;
            case MainCustomersMap:
                addFragCustomerMap();
                break;
            case MainCustomerAssign:
                addAssign();
                break;
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

    private void addAssign(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCustomerAssigns.newInstance(context));
        transaction.commit();
    }
    private void addMain(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragMain.newInstance(context));
        transaction.commit();
    }
    private void addFragTask(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragTasks.newInstance(context));
        transaction.commit();
    }
    private void addFragCustomer(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCustomers.newInstance(context));
        transaction.commit();
    }
    private void addFragReport(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragReports.newInstance(context));
        transaction.commit();
    }
    private void addFragAccount(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAccounts.newInstance(context));
        transaction.commit();
    }
    private void addFragCustomerMap(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCustomersMap.newInstance(context));
        transaction.commit();
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
    public void onBackPressed()
    {
        switch (STATE){
            case MainTasks:
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    super.onBackPressed();
                    finish();
                    finishAndRemoveTask();
                    finishAffinity();
                    return;
                }
                else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                mBackPressed = System.currentTimeMillis();
                break;
            case MainCustomers:
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    super.onBackPressed();
                    finish();
                    finishAndRemoveTask();
                    finishAffinity();
                    return;
                }
                else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                mBackPressed = System.currentTimeMillis();
                break;
            case MainReports:
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    super.onBackPressed();
                    finish();
                    finishAndRemoveTask();
                    finishAffinity();
                    return;
                }
                else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                mBackPressed = System.currentTimeMillis();
                break;
            case Account:
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    getFragByState(FragmentState.MainAccounts);
                }
                else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                mBackPressed = System.currentTimeMillis();
                break;
            case ProfileSubmiter:
                getFragByState(FragmentState.Account);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            fragCustomers.txtSearch.setText(matches.get(0));
        }
    }
}
