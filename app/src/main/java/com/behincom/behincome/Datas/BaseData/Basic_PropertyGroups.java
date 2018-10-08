package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_PropertyGroups {
    @SerializedName("PropertyGroupID")
    public int PropertyGroupID = 0;
    @SerializedName("PropertyGroupUserId")
    public int PropertyGroupUserId = 0;
    @SerializedName("PropertyGroupTitle")
    public String PropertyGroupTitle = "";
    @SerializedName("PropertyGroupOrder")
    public String PropertyGroupOrder = "";
    @SerializedName("PropertyGroupFontIcon")
    public String PropertyGroupFontIcon = "327";
    @SerializedName("PropertyGroupColor")
    public String PropertyGroupColor = "FFFFFF";
    @SerializedName("PropertyGroupAdjustedByAdmin")
    public boolean PropertyGroupAdjustedByAdmin = false;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
