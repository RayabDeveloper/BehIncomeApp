package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.SerializedName;

public class BasicPointStates {

    @SerializedName("PointStateId")
    public int PointStateId = 0;
    @SerializedName("PointStateTitle")
    public String PointStateTitle = "";
    @SerializedName("PointStateOrder")
    public int PointStateOrder = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
