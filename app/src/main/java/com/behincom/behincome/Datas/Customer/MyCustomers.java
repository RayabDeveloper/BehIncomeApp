package com.behincom.behincome.Datas.Customer;

import com.behincom.behincome.Datas.Profile.MarketerUserAccessProfile;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyCustomers {

    @SerializedName("Customer")
    public Customers Customers = new Customers();
    @SerializedName("ActivityCount")
    public int ActivityCount = 0;
    @SerializedName("Owner")
    public List<com.behincom.behincome.Datas.Profile.MarketerUserAccessProfile> Owner = new ArrayList<>();

}
