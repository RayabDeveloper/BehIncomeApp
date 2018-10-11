package com.behincom.behincome.Datas.Marketing;

import com.google.gson.annotations.SerializedName;

public class MarketingProducts {

    @SerializedName("MarketingProductID")
    public int MarketingProductID = 0;
    @SerializedName("UserID")
    public int UserID = 0;
    @SerializedName("CommissionTypeID")
    public int CommissionTypeID = 0;
    @SerializedName("MarketingProductTitle")
    public String MarketingProductTitle = "";
    @SerializedName("MarketingProductDescription")
    public String MarketingProductDescription = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
