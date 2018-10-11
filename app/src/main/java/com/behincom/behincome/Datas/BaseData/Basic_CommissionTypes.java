package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_CommissionTypes {

    @SerializedName("CommissionTypeID")
    public int CommissionTypeID = 0;
    @SerializedName("CommissionTypeTitle")
    public String CommissionTypeTitle = "";
    @SerializedName("CommissionTypeOrder")
    public String CommissionTypeOrder = "";
    @SerializedName("CommissionTypeFontIcon")
    public String CommissionTypeFontIcon = "327";
    @SerializedName("CommissionTypeColor")
    public String CommissionTypeColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;

}
