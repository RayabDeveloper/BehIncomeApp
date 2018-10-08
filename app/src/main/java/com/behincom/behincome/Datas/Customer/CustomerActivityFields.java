package com.behincom.behincome.Datas.Customer;

import com.google.gson.annotations.SerializedName;

public class CustomerActivityFields {

    @SerializedName("CustomerActivityFieldID")
    public int CustomerActivityFieldID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
    @SerializedName("ActivityFieldID")
    public int ActivityFieldID = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
