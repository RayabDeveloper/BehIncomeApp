package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_takGroups {
    @SerializedName("TagGroupID")
    public int TagGroupID = 0;
    @SerializedName("TagGroupUserId")
    public int TagGroupUserId = 0;
    @SerializedName("TagGroupTypeId")
    public int TagGroupTypeId = 0;
    @SerializedName("TagGroupTitle")
    public String TagGroupTitle = "";
    @SerializedName("TagGroupOrder")
    public String TagGroupOrder = "";
    @SerializedName("TagGroupFontIcon")
    public String TagGroupFontIcon = "327";
    @SerializedName("TagGroupColor")
    public String TagGroupColor = "FFFFFF";
    @SerializedName("TagGroupAdjustedByAdmin")
    public boolean TagGroupAdjustedByAdmin = true;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
