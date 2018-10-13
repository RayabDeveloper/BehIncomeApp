package com.behincom.behincome.Datas.BaseData;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_Provinces {

    @SerializedName("ProvinceID")
    public int ProvinceID = 0;
    @SerializedName("ProvinceTitle")
    public String ProvinceTitle = "";
    @SerializedName("ProvinceOrder")
    public String ProvinceOrder = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
