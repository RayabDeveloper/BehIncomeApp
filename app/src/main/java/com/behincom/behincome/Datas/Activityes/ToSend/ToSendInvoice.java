package com.behincom.behincome.Datas.Activityes.ToSend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ToSendInvoice {

    @SerializedName("InvoiceNumber")
    public String InvoiceNumber = "";
    @SerializedName("InvoiceMarketingProductID")
    public int InvoiceMarketingProductID = 0;
    @SerializedName("InvoiceActivityID")
    public int InvoiceActivityID = 0;
    @SerializedName("InvoicePrice")
    public double InvoicePrice = 0.0;
    @SerializedName("InvoiceDescription")
    public String InvoiceDescription = "";
    @SerializedName("InvoiceImages")
    public List<ToSendInvoiceImage> InvoiceImages = new ArrayList<>();

}
