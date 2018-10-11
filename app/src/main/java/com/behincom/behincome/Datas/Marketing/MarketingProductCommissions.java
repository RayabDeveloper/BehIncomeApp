package com.behincom.behincome.Datas.Marketing;

import com.google.gson.annotations.SerializedName;

public class MarketingProductCommissions {

    @SerializedName("ProductCommissionID")
    public int ProductCommissionID = 0;
    @SerializedName("MarketingProductID")
    public int MarketingProductID = 0;
    @SerializedName("CommissionPriceFrom")
    public long CommissionPriceFrom = 0;
    @SerializedName("CommissionPriceTo")
    public long CommissionPriceTo = 0;
    @SerializedName("CommissionPercent")
    public int CommissionPercent = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
