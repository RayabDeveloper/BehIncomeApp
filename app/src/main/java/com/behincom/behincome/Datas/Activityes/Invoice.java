package com.behincom.behincome.Datas.Activityes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

    @SerializedName("InvoiceID")
    public int InvoiceID = 0;
    @SerializedName("InvoiceNumber")
    public String InvoiceNumber = "";
    @SerializedName("InvoiceMarketingProductID")
    public int InvoiceMarketingProductID = 0;
    @SerializedName("InvoiceActivityID")
    public int InvoiceActivityID = 0;
    @SerializedName("InvoicePrice")
    public int InvoicePrice = 0;
    @SerializedName("InvoiceDescription")
    public String InvoiceDescription = "";
    @SerializedName("InvoiceImage")
    public List<InvoiceImage> InvoiceImage = new ArrayList<>();

}
