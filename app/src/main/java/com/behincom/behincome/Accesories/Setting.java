package com.behincom.behincome.Accesories;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.behincom.behincome.app.AppController;

public class Setting {

    private static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppController.getContext);

    public static void Save(String Key, String Value){
        try{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Key,Value);
            editor.apply();
        }catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }
    public static String Load(String Key){
        try {
            return preferences.getString(Key, "");
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            return "";
        }
    }

    public static String getToken(){
        return "Bearer " + Load("access_token");
    }
    public static String getReloadAll(){
        return Load("ReloadAll");
    }
    public static int getUserID(){
        return Integer.parseInt(Load("UserID"));
    }
    public static int getBMMUserID(){
        try {
            return Integer.parseInt(Load("BMMUserID"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return getUserID();
        }
    }
    public static String getBMMUserName(){
        return Load("BMMUserName");
    }
    public static String getUserName(){
        return Load("userName");
    }
    public static String getReferCode(){
        return Load("ReferCode");
    }
    public static String getServerDateTime(){
        return Load("ServerDateTime");
    }
    public static String getServerURL(){
        return Load("ServerURL");
    }
    public static String getTermAandConditions(){
        return Load("ServerURL") + "TermAndConditions";
    }
    public static int getType(){
        int a = 0;
        try {
            a = Integer.parseInt(Load("AccountType"));
        } catch (NumberFormatException e) {
            a = 0;
        }
        return a;
    }

}
