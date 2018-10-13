package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_ActResults {
    @SerializedName("ActResultID")
    public int ActResultID = 0;
    @SerializedName("ActID")
    public int ActID = 0;
    @SerializedName("ActResultTitle")
    public String ActResultTitle = "";
    @SerializedName("ActResultOrder")
    public String ActResultOrder = "";
    @SerializedName("ActResultFontIcon")
    public String ActResultFontIcon = "327";
    @SerializedName("ActResultColor")
    public String ActResultColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
    @Expose
    public int Point = 0;

}
