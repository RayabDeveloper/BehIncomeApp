package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_CustomerStatus {

    @SerializedName("CustomerStatusID")
    public int CustomerStatusID = 0;
    @SerializedName("CustomerStatusTitle")
    public String CustomerStatusTitle = "";
    @SerializedName("CustomerStatusOrder")
    public String CustomerStatusOrder = "";
    @SerializedName("CustomerStatusFontIcon")
    public String CustomerStatusFontIcon = "327";
    @SerializedName("CustomerStatusColor")
    public String CustomerStatusColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;

}
