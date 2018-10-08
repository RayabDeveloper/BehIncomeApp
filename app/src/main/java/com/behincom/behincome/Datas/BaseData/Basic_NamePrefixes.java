package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_NamePrefixes {
    @SerializedName("NamePrefixID")
    public int NamePrefixID = 0;
    @SerializedName("NamePrefixTitle")
    public String NamePrefixTitle = "";
    @SerializedName("NamePrefixOrder")
    public String NamePrefixOrder = "";
    @SerializedName("NamePrefixFontIcon")
    public String NamePrefixFontIcon = "327";
    @SerializedName("NamePrefixColor")
    public String NamePrefixColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
