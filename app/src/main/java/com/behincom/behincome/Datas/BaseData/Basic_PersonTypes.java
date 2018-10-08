package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_PersonTypes {
    @SerializedName("PersonTypeID")
    public int PersonTypeID = 0;
    @SerializedName("PersonTitleTitle")
    public String PersonTitleTitle = "";
    @SerializedName("PersonRoleOrder")
    public String PersonRoleOrder = "";
    @SerializedName("PersonTypeFontIcon")
    public String PersonTypeFontIcon = "327";
    @SerializedName("PersonTypeColor")
    public String PersonTypeColor = "FFFFFF";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
