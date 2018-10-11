package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_Ostan {
    @SerializedName("ProvinceID")
    public int ProvinceID = 0;
    @SerializedName("ProvinceTitle")
    public String ProvinceTitle = "";
    @SerializedName("ProvinceOrder")
    public String ProvinceOrder = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
