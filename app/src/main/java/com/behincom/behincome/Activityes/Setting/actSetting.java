package com.behincom.behincome.Activityes.Setting;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.behincom.behincome.Activityes.Setting.ArchiveType.fragArchiveType;
import com.behincom.behincome.Activityes.Setting.ContactType.fragContactType;
import com.behincom.behincome.Activityes.Setting.CustomerState.fragCustomerState;
import com.behincom.behincome.Activityes.Setting.Pdoructs.fragAddProducts;
import com.behincom.behincome.Activityes.Setting.Pdoructs.fragProducts;
import com.behincom.behincome.Activityes.Setting.PersonRole.fragPersonRole;
import com.behincom.behincome.Activityes.Setting.Priods.Comissions.fragAddComissionPriod;
import com.behincom.behincome.Activityes.Setting.Priods.Comissions.fragComissionPriod;
import com.behincom.behincome.Activityes.Setting.Priods.Visitors.fragAddVisitorPriod;
import com.behincom.behincome.Activityes.Setting.Priods.Visitors.fragVisitorPriod;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFieldGroups;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_Cities;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_PropertyGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Provinces;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;

public class actSetting extends AppCompatActivity {

    static FragmentManager manager;
    static Context context;
    RSQLGeter<Object> geter = new RSQLGeter<>();

    static FrameLayout frameLayout;
    public static FragmentState STATE = FragmentState.Setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);

        frameLayout = findViewById(R.id.frameLayout);

        manager = getSupportFragmentManager();
        context = this;

        getFragByState(FragmentState.Setting);
    }

    public void getFragByState(FragmentState mState){
        fragBasicData frag = new fragBasicData();
        switch (mState){
            case ActivityFields:
                frag.objects = new Initializer<>(
                        geter.getList(Basic_ActivityFieldGroups.class),
                        geter.getList(Basic_ActivityFields.class),
                        Basic_ActivityFields.class,
                        "ActivityFieldGroupID",
                        "ActivityFieldID",
                        "ActivityFieldGroupTitle",
                        "ActivityFieldTitle",
                        "ActivityFieldGroupID",
                        "حوزه فعالیت");
                addFragBaseData();
                STATE = FragmentState.ActivityFields;
                break;
            case Cities:
                frag.objects = new Initializer<>(
                        geter.getList(Basic_Provinces.class),
                        geter.getList(Basic_Cities.class),
                        Basic_Cities.class,
                        "ProvinceID",
                        "CityID",
                        "ProvinceTitle",
                        "CityTitle",
                        "ProvinceID",
                        "استان و شهر");
                addFragBaseData();
                STATE = FragmentState.Cities;
                break;
            case Tags:
                frag.objects = new Initializer<>(
                        geter.getList(Basic_TagGroups.class),
                        geter.getList(Basic_Tags.class),
                        Basic_Tags.class,
                        "TagGroupID",
                        "TagID",
                        "TagGroupTitle",
                        "TagTitle",
                        "TagGroupID",
                        "برچسب");
                addFragBaseData();
                STATE = FragmentState.Tags;
                break;
            case Properties:
                frag.objects = new Initializer<>(
                        geter.getList(Basic_PropertyGroups.class),
                        geter.getList(Basic_Properties.class),
                        Basic_Properties.class,
                        "PropertyGroupID",
                        "PropertyID",
                        "PropertyGroupTitle",
                        "PropertyTitle",
                        "PropertyGroupID",
                        "مشخصات");
                addFragBaseData();
                STATE = FragmentState.Properties;
                break;
            case ActivityResults:
                addFragActivity();
                STATE = FragmentState.ActivityResults;
                break;
            case CommissionPeriods:
                addFragCommissionPeriods();
                STATE = FragmentState.CommissionPeriods;
                break;
            case Products:
                addFragProduct();
                STATE = FragmentState.Products;
                break;
            case VisitTours:
                addFragVisitTour();
                STATE = FragmentState.VisitTours;
                break;
            case Setting:
                addFragSetting();
                STATE = FragmentState.Setting;
                break;
            case AddCommissionPeriods:
                addFragAddCommissionPriod();
                STATE = FragmentState.AddCommissionPeriods;
                break;
            case AddProducts:
                addFragAddProducts();
                STATE = FragmentState.AddProducts;
                break;
            case AddVisitTour:
                addFragAddVisitorPriod();
                STATE = FragmentState.AddVisitTour;
                break;
            case CustomerState:
                addFragCustomerState();
                STATE = FragmentState.CustomerState;
                break;

            case PersonRole:
                addFragPersonRole();
                STATE = FragmentState.PersonRole;
                break;
            case ContactType:
                addFragContactType();
                STATE = FragmentState.ContactType;
                break;
            case ArchiveType:
                addFragArchiveType();
                STATE = FragmentState.ArchiveType;
                break;
        }
    }

    private void addFragPersonRole(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragPersonRole.newInstance(context));
        transaction.commit();
    }
    private void addFragContactType(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragContactType.newInstance(context));
        transaction.commit();
    }
    private void addFragArchiveType(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragArchiveType.newInstance(context));
        transaction.commit();
    }
    private void addFragCustomerState(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragCustomerState.newInstance(context));
        transaction.commit();
    }
    private void addFragBaseData(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragBasicData.newInstance(context));
        transaction.commit();
    }
    private void addFragActivity(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragActResults.newInstance(context));
        transaction.commit();
    }
    private void addFragCommissionPeriods(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragComissionPriod.newInstance(context));
        transaction.commit();
    }
    private void addFragProduct(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragProducts.newInstance(context));
        transaction.commit();
    }
    private void addFragVisitTour(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragVisitorPriod.newInstance(context));
        transaction.commit();
    }
    private void addFragSetting(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragSetting.newInstance(context));
        transaction.commit();
    }
    private void addFragAddCommissionPriod(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddComissionPriod.newInstance(context));
        transaction.commit();
    }
    private void addFragAddProducts(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddProducts.newInstance(context));
        transaction.commit();
    }
    private void addFragAddVisitorPriod(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragAddVisitorPriod.newInstance(context));
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
