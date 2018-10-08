package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_AndroidKeyboardTypes {

    @SerializedName("AndroidKeyboardTypeID")
    public int AndroidKeyboardTypeID = 0;
    @SerializedName("AndroidKeyboardTypeTitle")
    public String AndroidKeyboardTypeTitle = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;

}
