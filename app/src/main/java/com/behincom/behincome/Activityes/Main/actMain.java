package com.behincom.behincome.Activityes.Main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.behincom.behincome.Activityes.Account.fragAccount;
import com.behincom.behincome.Activityes.Account.fragProfileSubmiter;
import com.behincom.behincome.Activityes.Main.Filter.fragAddActivityField;
import com.behincom.behincome.Activityes.Main.Filter.fragAddArchiveType;
import com.behincom.behincome.Activityes.Main.Filter.fragAddCustomerFilter;
import com.behincom.behincome.Activityes.Main.Filter.fragAddCustomerState;
import com.behincom.behincome.Activityes.Main.Filter.fragAddCustomerStatus;
import com.behincom.behincome.Activityes.Main.Filter.fragAddPrefix;
import com.behincom.behincome.Activityes.Main.Filter.fragAddTag;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStatus;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Customer.CustomerFilter;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.R;

import java.util.ArrayList;
import java.util.List;

import static com.behincom.behincome.Activityes.Main.fragCustomers.VOICE_RECOGNITION_REQUEST_CODE;

public class actMain extends AppCompatActivity {

    static FragmentManager manager;
    static Context context;

    static FrameLayout frameLayout;
    public static FragmentState STATE = FragmentState.MainTasks;

    boolean isGrant = false, isGrant_GPS = false, isGrant_GPS2 = false;

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

        isGrant = askForPermission(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    protected static boolean askForPermission(String[] permission, int rCode) {
        if (ContextCompat.checkSelfPermission(context, permission[0]) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, permission[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity) context), permission[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(((Activity) context), permission[1])) {
                ActivityCompat.requestPermissions(((Activity) context), permission, rCode);
                return false;
            } else {
                ActivityCompat.requestPermissions(((Activity) context), permission, rCode);
                return false;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && permissions[1].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                isGrant = true;
            } else {
//                Toast.makeText(context, Basics.NeedPermission, Toast.LENGTH_LONG).show();
                askForPermission(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
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
    public void getActivityFragment(List<Basic_ActivityFields> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddActivityField.newInstance(context, lList));
        transaction.commit();
        STATE = FragmentState.ActivityFields;
    }
    public void getTagFragment(List<Basic_Tags> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddTag.newInstance(context, lList));
        transaction.commit();
        STATE = FragmentState.Tags;
    }
    public void getArchiveFragment(List<Basic_ArchiveTypes> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddArchiveType.newInstance(context, lList));
        transaction.commit();
        STATE = FragmentState.ArchiveType;
    }
    public void getCustomerStateFragment(List<Basic_CustomerStates> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddCustomerState.newInstance(context, lList));
        transaction.commit();
        STATE = FragmentState.CustomerState;
    }
    public void getCustomerStatusFragment(List<Basic_CustomerStatus> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddCustomerStatus.newInstance(context, lList));
        transaction.commit();
        STATE = FragmentState.CustomerStatus;
    }
    public void getNamePrefixFragment(List<Basic_NamePrefixes> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddPrefix.newInstance(context, lList));
        transaction.commit();
        STATE = FragmentState.NamePrefixes;
    }
    public void addFilter(CustomerFilter Filter){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddCustomerFilter.newInstance(context, Filter));
        transaction.commit();
        STATE = FragmentState.AddFilter;
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
            case AddFilter:
                getFragByState(FragmentState.AddFilter);
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
