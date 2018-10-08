package com.behincom.behincome.Datas.Customer.ToSend;

import com.google.gson.annotations.SerializedName;

public class EditCustomerToSendProperties {

    @SerializedName("PropertyID")
    public int PropertyID = 0;
    @SerializedName("Value")
    public String Value = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
