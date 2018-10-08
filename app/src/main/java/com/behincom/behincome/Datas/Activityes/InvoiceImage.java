package com.behincom.behincome.Datas.Activityes;

import com.google.gson.annotations.SerializedName;

public class InvoiceImage {

    @SerializedName("InvoiceImageID")
    public int InvoiceImageID = 0;
    @SerializedName("InvoiceID")
    public int InvoiceID = 0;
    @SerializedName("ImageFilename")
    public String ImageFilename = "";
    @SerializedName("ImagePrefix")
    public int ImagePrefix = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @SerializedName("InvoiceDescription")
    public String InvoiceDescription = "";

}
