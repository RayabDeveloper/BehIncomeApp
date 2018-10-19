package com.behincom.behincome.Datas.Customer;

import com.behincom.behincome.Datas.Keys.CustomerOrder;
import com.google.gson.annotations.SerializedName;

public class CustomerSearchModel {

    @SerializedName("Page")
    public int Page = 0;
    @SerializedName("BusinessManagerId")
    public int BusinessManagerId = 0;
    @SerializedName("OrderTypeModel")
    public int OrderTypeModel = CustomerOrder.CreateDateDesc;
    @SerializedName("SearchKey")
    public String SearchKey = "";
    @SerializedName("FilterModel")
    public CustomerFilter FilterModel = new CustomerFilter();

}
