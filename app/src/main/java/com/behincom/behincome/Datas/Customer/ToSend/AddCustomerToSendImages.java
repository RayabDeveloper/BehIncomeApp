package com.behincom.behincome.Datas.Customer.ToSend;

import com.google.gson.annotations.SerializedName;

public class AddCustomerToSendImages {

    @SerializedName("ImageFilename")
    public String ImageFilename = "";
    @SerializedName("ImagePrefix")
    public int ImagePrefix = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
