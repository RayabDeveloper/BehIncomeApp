package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_ActGroups {
    @SerializedName("ActGroupID")
    public int ActGroupID = 0;
    @SerializedName("ActGroupTitle")
    public String ActGroupTitle = "";
    @SerializedName("ActGroupFontIcon")
    public String ActGroupFontIcon = "327";
    @SerializedName("ActGroupColor")
    public String ActGroupColor = "FFFFFF";
    @SerializedName("ActGroupOrder")
    public String ActGroupOrder = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
