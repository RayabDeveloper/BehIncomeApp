package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mBasic_Tags {
    @SerializedName("TagID")
    public int TagID = 0;
    @SerializedName("TagGroupID")
    public int TagGroupID = 0;
    @SerializedName("TagTitle")
    public String TagTitle = "";
    @SerializedName("TagOrder")
    public String TagOrder = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
