package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_citi {
    @SerializedName("CityID")
    public int CityID = 0;
    @SerializedName("ProvinceID")
    public int ProvinceID = 0;
    @SerializedName("CityTitle")
    public String CityTitle = "";
    @SerializedName("CityOrder")
    public String CityOrder = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
