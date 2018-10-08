package com.behincom.behincome.Datas.Customer;

import com.google.gson.annotations.SerializedName;

public class CustomerTags {

    @SerializedName("CustomerTagID")
    public int CustomerTagID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
    @SerializedName("TagID")
    public int TagID = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
