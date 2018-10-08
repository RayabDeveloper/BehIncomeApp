package com.behincom.behincome.Datas.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Marketers {

    @SerializedName("UserID")
    public int UserID = 0;
    @SerializedName("SubmitDate")
    public String SubmitDate = "2018-01-01T00:00:00";
    @SerializedName("RoleName")
    public String RoleName = "";
    @SerializedName("UserName")
    public String UserName = "";
    @SerializedName("MarkPhotoFilenameeterName")
    public String MarkPhotoFilenameeterName = "";
    @Expose
    public boolean isCheck = false;

}
