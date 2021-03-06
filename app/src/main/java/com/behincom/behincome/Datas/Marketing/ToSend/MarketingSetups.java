package com.behincom.behincome.Datas.Marketing.ToSend;

import com.google.gson.annotations.SerializedName;

public class MarketingSetups {

    @SerializedName("MarketingSetupId")
    public int MarketingSetupId = 0;
    @SerializedName("PointCustomerAdd")
    public int PointCustomerAdd = 0;
    @SerializedName("PointInvoiseAdd")
    public int PointInvoiseAdd = 0;
    @SerializedName("CustmerEditTime")
    public String CustmerEditTime = "00:00:00";
    @SerializedName("ActivityEditTime")
    public String ActivityEditTime = "00:00:00";
    @SerializedName("InvoiceEditTime")
    public String InvoiceEditTime = "00:00:00";
    @SerializedName("ActivityNotDoneNegativePoint")
    public int ActivityNotDoneNegativePoint = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @SerializedName("OwnerId")
    public int OwnerId = 0;

}
