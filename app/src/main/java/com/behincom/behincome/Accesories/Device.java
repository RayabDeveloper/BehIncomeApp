package com.behincom.behincome.Accesories;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.behincom.behincome.app.AppController;

public class Device {

    @SuppressLint("StaticFieldLeak")
    static Context context = AppController.getContext;
    public Device(Context context) {
        Device.context = context;
    }
    public static String DeviceName() {
        return capitalize(getDeviceName());
    }
    public static int OSVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }
    @SuppressLint("HardwareIds")
    public static String IMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        return telephonyManager.getDeviceId();
    }
    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }
    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

}
