package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mBasic_ActivityFields {
    @SerializedName("ActivityFieldID")
    public int ActivityFieldID = 0;
    @SerializedName("ActivityFieldGroupID")
    public int ActivityFieldGroupID = 0;
    @SerializedName("ActivityFieldOrder")
    public String ActivityFieldOrder = "";
    @SerializedName("ActivityFieldTitle")
    public String ActivityFieldTitle = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
