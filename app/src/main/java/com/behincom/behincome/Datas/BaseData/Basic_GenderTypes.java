package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_GenderTypes {
    @SerializedName("GenderTypeID")
    public int GenderTypeID = 0;
    @SerializedName("GenderTypeTitle")
    public String GenderTypeTitle = "";
    @SerializedName("GenderTypeFontIcon")
    public String GenderTypeFontIcon = "327";
    @SerializedName("GenderTypeColor")
    public String GenderTypeColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
