package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_ActResultsMaker {

    @SerializedName("ActResultsID")
    public int ActResultsID = 0;
    @SerializedName("ActTypeID")
    public int ActTypeID = 0;
    @SerializedName("ActID")
    public int ActID = 0;
    @SerializedName("Point")
    public int Point = 0;
    @SerializedName("ActResultsTitle")
    public String ActResultsTitle = "";
    @SerializedName("ActResultsType")
    public int ActResultsType = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;

}
