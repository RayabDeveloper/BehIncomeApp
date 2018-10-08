package com.behincom.behincome.Datas.Activityes.ToSend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ToSendActivityConfirmAndSend {

    @SerializedName("ActID")
    public int ActivityID = 0;
    @SerializedName("ActResultID")
    public int ActivityResultID = 0;
    @SerializedName("ActivityDescription")
    public String  ActivityDescription = "";
    @SerializedName("Invoices")
    public List<ToSendInvoice> Invoices = new ArrayList<>();

}
