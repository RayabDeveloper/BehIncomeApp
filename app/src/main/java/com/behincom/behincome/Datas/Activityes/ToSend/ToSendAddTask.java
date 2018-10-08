package com.behincom.behincome.Datas.Activityes.ToSend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToSendAddTask {

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
    @SerializedName("TaskDate")
    public String TaskDate = "2018-01-01T00:00:00";
    @SerializedName("DurationDate")
    public String  DurationDate = "2018-01-01T00:00:00";
    @SerializedName("VisitTourID")
    public int VisitTourID = 0;
    @Expose
    public boolean hasReminder = false;

}
