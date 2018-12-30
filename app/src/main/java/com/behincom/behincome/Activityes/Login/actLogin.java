package com.behincom.behincome.Activityes.Login;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Splash.actSplash;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

public class actLogin extends AppCompatActivity {

    static FragmentManager manager;
    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    static FrameLayout frameLayout;
    public static int STATE = 1;

    protected static String sPhone, sDigitCode, sName, sFamily, sPass, sMoaref;
    protected static int sCodee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        frameLayout = findViewById(R.id.frameLayout);

        Setting.Save("ServerDateTime", "2018-01-01T11:11:11");
//        Setting.Save("ServerURL", "http://164.138.17.243/");aa
        Setting.Save("ServerURL", "http://192.168.1.159/BehincomeWeb/");
        Setting.Save("AccountType", "1");

        manager = getSupportFragmentManager();
        context = this;

        if(Setting.Load("isLogin").equals("1")){
            if(Setting.getType() == 2) {
                actLogin.STATE = 5;
            }else {
                Intent intent = new Intent(context, actSplash.class);
                startActivity(intent);
                finish();
            }
        }else{
//            STATE = 6;//STATE = 6; Login Or Register
            STATE = 1;//STATE = 6; Login Or Register
        }

        setState(STATE);
    }

    private void setState(int mState){
        switch (mState){
            case 1:
                addFragRequestPhone();
                break;
            case 2:
                addFragRequestCode();
                break;
            case 3:
                addFragRegister();
                break;
            case 4:
                addFragJustic();
                break;
            case 5:
                addFragBMM();
                break;
            case 6:
                addFragLogin();
                break;
            case 7:
                addFragRegisterAdmin();
                break;
            case 8:
                addFragResetPassword();
                break;
        }
    }
    protected static void addFragResetPassword(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragResetPass.newInstance(context));
        transaction.commit();
    }
    protected static void addFragRequestPhone(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragLoginRequestPhone.newInstance(context));
        transaction.commit();
    }
    protected static void addFragRequestCode(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragLoginRequestCode.newInstance(context));
        transaction.commit();
    }
    protected static void addFragJustic(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragJustic.newInstance(context));
        transaction.commit();
    }
    protected static void addFragRegister(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragRegister.newInstance(context));
        transaction.commit();
    }
    protected static void addFragBMM(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragChoiseBMM.newInstance(context));
        transaction.commit();
    }
    protected static void addFragLogin(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragLogin.newInstance(context));
        transaction.commit();
    }
    protected static void addFragRegisterAdmin(){
        frameLayout.removeAllViewsInLayout();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragRegisterAdmin.newInstance(context));
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("STATE", STATE);

        savedInstanceState.putString("PhoneNumber", sPhone);
        savedInstanceState.putString("Code", sDigitCode);
        savedInstanceState.putString("Name", sName);
        savedInstanceState.putString("Family", sFamily);
        savedInstanceState.putString("Password", sPass);
        savedInstanceState.putString("Moaref", sMoaref);
        savedInstanceState.putInt("Codee", sCodee);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setState(savedInstanceState.getInt("STATE"));
        sPhone = savedInstanceState.getString("PhoneNumber");
        sDigitCode = savedInstanceState.getString("Code");
        sFamily = savedInstanceState.getString("Family");
        sPass = savedInstanceState.getString("Password");
        sMoaref = savedInstanceState.getString("Moaref");
        sCodee = savedInstanceState.getInt("Codee");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == 1)
        if (permissions[0].equals(Manifest.permission.READ_PHONE_STATE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fragLoginRequestCode.isGrant = true;
        }else{
            Toast.makeText(context, Basics.NeedPermission, Toast.LENGTH_LONG).show();
            fragLoginRequestCode.askForPermission(Manifest.permission.READ_PHONE_STATE);
        }
    }
}
