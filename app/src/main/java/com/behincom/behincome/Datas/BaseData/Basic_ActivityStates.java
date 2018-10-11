package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_ActivityStates {
    @SerializedName("ActivityStateID")
    public int ActivityStateID = 0;
    @SerializedName("ActivityStateTitle")
    public String ActivityStateTitle = "";
    @SerializedName("ActivityStateOrder")
    public String ActivityStateOrder = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;

}
