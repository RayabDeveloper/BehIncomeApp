package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_Acts {

    @SerializedName("ActID")
    public int ActID = 0;
    @SerializedName("ActGroupID")
    public int ActGroupID = 0;
    @SerializedName("ActTitle")
    public String ActTitle = "";
    @SerializedName("ActOrder")
    public String ActOrder = "";
    @SerializedName("ActFontIcon")
    public String ActFontIcon = "327";
    @SerializedName("ActColor")
    public String ActColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;

}
