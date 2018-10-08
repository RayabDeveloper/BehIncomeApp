package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_Color {

    @SerializedName("ColorID")
    public int ColorID = 0;
    @SerializedName("ColorCode")
    public String ColorCode = "FFFFFF";
    @SerializedName("ColorOrder")
    public int ColorOrder = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @SerializedName("LastUpdateDate")
    public String LastUpdateDate = "";
    @Expose
    public boolean isCheck = false;

}
