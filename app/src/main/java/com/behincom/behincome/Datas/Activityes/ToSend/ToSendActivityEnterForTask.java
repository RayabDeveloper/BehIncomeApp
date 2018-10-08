package com.behincom.behincome.Datas.Activityes.ToSend;

import com.google.gson.annotations.SerializedName;

public class ToSendActivityEnterForTask {

    @SerializedName("ActID")
    public int ActivityID = 0;
    @SerializedName("Title")
    public String Title = "";
    @SerializedName("ActID")
    public int ActID = 0;
    @SerializedName("ActivityDescription")
    public String ActivityDescription = "";
    @SerializedName("EnterDate")
    public String EnterDate = "2018-01-01T00:00:00";
    @SerializedName("EnterLatutide")
    public double EnterLatutide = 0.0;
    @SerializedName("EnterLongitude")
    public double EnterLongitude = 0.0;

}
