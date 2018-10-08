package com.behincom.behincome.Datas.Customer;

import com.google.gson.annotations.SerializedName;

public class CustomerProperties {

    @SerializedName("CustomerPropertyID")
    public int CustomerPropertyID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
    @SerializedName("PropertyID")
    public int PropertyID = 0;
    @SerializedName("Value")
    public String Value = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
