package com.behincom.behincome.Activityes.Customer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.behincom.behincome.Activityes.Customer.AddCustomer.fragActivityField;
import com.behincom.behincome.Activityes.Customer.AddCustomer.fragProperty;
import com.behincom.behincome.Activityes.Customer.AddCustomer.fragTag;
import com.behincom.behincome.Activityes.Login.fragLoginRequestCode;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

public class actCustomer extends AppCompatActivity {

    static FragmentManager manager;
    static Context context;
    RSQLGeter geter = new RSQLGeter<>();

    static FrameLayout frameLayout;
    public static FragmentState STATE = FragmentState.AddCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer);

        frameLayout = findViewById(R.id.frameLayout);

        manager = getSupportFragmentManager();
        context = this;

        RSQLite SQL = new RSQLite();
        List<Basic_Tags> lTag = SQL.Select("SELECT * FROM Basic_Tags WHERE isCheck='1'", Basic_Tags.class);
        List<Basic_Tags> lTag2 = SQL.Select("SELECT TagID, TagGroupID, TagTitle, Deleted, (0) as isCheck FROM Basic_Tags", Basic_Tags.class);
        String asd = "ASD";

        getFragByState(STATE);
    }

    public void getFragByState(FragmentState mState){
        switch (mState){
            case AddCustomer:
                addCustommer();
                break;
            case BigMap:
                addBigMap();
                break;
            case CustomerOnMap:
                addCustomerOnMap();
                break;
            case CustomerShow:
                addCustomerShow();
                break;
        }
    }

    protected static void ShowFragProperties(List<Basic_Properties> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragProperty.newInstance(context, lList));
        transaction.commit();
    }
    protected static void ShowFragActivityFields(List<Basic_ActivityFields> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragActivityField.newInstance(context, lList));
        transaction.commit();
    }
    protected static void ShowFragTags(List<Basic_Tags> lList){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragTag.newInstance(context, lList));
        transaction.commit();
    }
    private void addCustommer(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddCustomer.newInstance(context));
        transaction.commit();
    }
    private void addBigMap(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragBigMap.newInstance(context));
        transaction.commit();
    }
    private void addCustomerOnMap(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCustomerOnMap.newInstance(context));
        transaction.commit();
    }
    private void addCustomerShow(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCustomerShow.newInstance(context));
        transaction.commit();
    }
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed() {
        switch (STATE){
            case AddCustomer:
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
                {
                    super.onBackPressed();
                    finish();
                    return;
                }
                else { Toast.makeText(getBaseContext(), "برای خروج دوباره از گزینه برگشت استفاده کنید", Toast.LENGTH_SHORT).show(); }
                mBackPressed = System.currentTimeMillis();
                break;
            case BigMap:
                getFragByState(FragmentState.AddCustomer);
                break;
            case CustomerOnMap:
                super.onBackPressed();
                finish();
                break;
            case CustomerShow:
                super.onBackPressed();
                finish();
                break;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("STATE", STATE.toString());
        switch (STATE){
            case AddCustomer:

                break;
            case BigMap:

                break;
            case CustomerOnMap:

                break;
            case CustomerShow:

                break;
        }
        super.onSaveInstanceState(bundle);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switch (STATE){
            case AddCustomer:

                break;
            case BigMap:

                break;
            case CustomerOnMap:

                break;
            case CustomerShow:

                break;
        }
        getFragByState(FragmentState.valueOf(savedInstanceState.getString("STATE")));
    }


}
