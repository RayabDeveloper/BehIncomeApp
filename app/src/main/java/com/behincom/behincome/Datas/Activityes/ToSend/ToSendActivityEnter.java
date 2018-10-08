package com.behincom.behincome.Datas.Activityes.ToSend;

import com.google.gson.annotations.SerializedName;

public class ToSendActivityEnter {

    @SerializedName("ParentID")
    public int ParentID = 0;
    @SerializedName("ActivityType")
    public int ActivityType = 0;
    @SerializedName("ActivityAddedByUserID")
    public int ActivityAddedByUserID = 0;
    @SerializedName("ActivityOwnerUserID")
    public int ActivityOwnerUserID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
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
