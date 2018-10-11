package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_BusinessManagerMarketerStates {
    @SerializedName("BusinessManagerMarketerStateID")
    public int BusinessManagerMarketerStateID = 0;
    @SerializedName("BusinessManagerMarketerStateTitle")
    public String BusinessManagerMarketerStateTitle = "";
    @SerializedName("BusinessManagerMarketerStateOrder")
    public String BusinessManagerMarketerStateOrder = "";
    @SerializedName("BusinessManagerMarketerStateFontIcon")
    public String BusinessManagerMarketerStateFontIcon = "327";
    @SerializedName("BusinessManagerMarketerStateColor")
    public String BusinessManagerMarketerStateColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
