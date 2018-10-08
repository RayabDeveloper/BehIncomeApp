package com.behincom.behincome.SQL;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

public class SaveSetting {

    private SharedPreferences preferences;

    public SaveSetting(Context context){
        try {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }

    public void Save(String Key, String Value){
        try{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Key,Value);
            editor.apply();
        }catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
    }
    public String Load(String Key){
        try {
            return preferences.getString(Key, "");
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            return "";
        }
    }

}
