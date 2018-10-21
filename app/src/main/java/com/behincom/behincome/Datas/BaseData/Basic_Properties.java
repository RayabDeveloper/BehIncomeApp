package com.behincom.behincome.Datas.BaseData;

import com.behincom.behincome.SQL.RAnnot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_Properties {
    @SerializedName("PropertyID")
    public int PropertyID = 0;
    @SerializedName("PropertyGroupID")
    public int PropertyGroupID = 0;
    @SerializedName("PropertyTitle")
    public String PropertyTitle = "";
    @SerializedName("PropertyDescription")
    public String PropertyDescription = "";
    @SerializedName("PropertyOrder")
    public String PropertyOrder = "";
    @SerializedName("PersonTypeFontIcon")
    public String PersonTypeFontIcon = "";
    @SerializedName("PersonTypeColor")
    public String PersonTypeColor = "";
    @SerializedName("PropertyTypeKeyBoardId")
    public int PropertyTypeKeyBoardId = 1;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
