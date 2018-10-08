package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_MilitaryStatus {
    @SerializedName("MilitaryStatusID")
    public int MilitaryStatusID = 0;
    @SerializedName("MilitaryStatusTitle")
    public String MilitaryStatusTitle = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
