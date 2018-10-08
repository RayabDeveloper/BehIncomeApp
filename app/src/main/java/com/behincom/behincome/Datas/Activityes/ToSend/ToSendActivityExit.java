package com.behincom.behincome.Datas.Activityes.ToSend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ToSendActivityExit {

    @SerializedName("ActID")
    public int ActivityID = 0;
    @SerializedName("ActResultID")
    public int ActivityResultID = 0;
    @SerializedName("ActivityDescription")
    public String  ActivityDescription = "";
    @SerializedName("ExitDate")
    public String ExitDate = "2018-01-01T00:00:00";
    @SerializedName("ExitLatutide")
    public double ExitLatutide = 0.0;
    @SerializedName("ExitLongitude")
    public double ExitLongitude = 0.0;
    @SerializedName("Invoices")
    public List<ToSendInvoice> Invoices = new ArrayList<>();

}
