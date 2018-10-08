package com.behincom.behincome.Datas.Customer.ToSend;

import com.google.gson.annotations.SerializedName;

public class AddCustomerToSendAssign {

    @SerializedName("MarketerUserID")
    public int MarketerUserID = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @SerializedName("CreationDateTime")
    public String CreationDateTime = "";

}
