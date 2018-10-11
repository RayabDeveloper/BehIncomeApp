package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_CustomerStates {
    @SerializedName("CustomerStateID")
    public int CustomerStateID = 0;
    @SerializedName("CustomerStateUserId")
    public int CustomerStateUserId = 0;
    @SerializedName("CustomerStateColor")
    public String CustomerStateColor = "";
    @SerializedName("CustomerStateTitle")
    public String CustomerStateTitle = "";
    @SerializedName("CustomerStateOrder")
    public String CustomerStateOrder = "";
    @SerializedName("CustomerStateFontIcon")
    public String CustomerStateFontIcon = "";
    @SerializedName("CustomerStateAdjustedByAdmin")
    public boolean CustomerStateAdjustedByAdmin = true;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
