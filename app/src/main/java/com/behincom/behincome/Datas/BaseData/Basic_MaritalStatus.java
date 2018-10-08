package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_MaritalStatus {
    @SerializedName("MaritalStatusID")
    public int MaritalStatusID = 0;
    @SerializedName("MaritalStatusTitle")
    public String MaritalStatusTitle = "";
    @SerializedName("MaritalStatusFontIcon")
    public String MaritalStatusFontIcon = "327";
    @SerializedName("MaritalStatusColor")
    public String MaritalStatusColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
