package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_ContactTypes {
    @SerializedName("ContactTypeID")
    public int ContactTypeID = 0;
    @SerializedName("AndroidKeyboardTypeID")
    public int AndroidKeyboardTypeID = 0;
    @SerializedName("ContactTypeUserId")
    public int ContactTypeUserId = 0;
    @SerializedName("ContactTypeTitle")
    public String ContactTypeTitle = "";
    @SerializedName("ContactTypeOrder")
    public String ContactTypeOrder = "";
    @SerializedName("ContactTypeFontIcon")
    public String ContactTypeFontIcon = "327";
    @SerializedName("ContactTypeColor")
    public String ContactTypeColor = "FFFFFF";
    @SerializedName("AdjustedByAdmin")
    public boolean AdjustedByAdmin = true;
    @Expose
    public boolean isCheck = false;
}
