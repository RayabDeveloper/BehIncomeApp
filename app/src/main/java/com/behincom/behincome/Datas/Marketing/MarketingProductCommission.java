package com.behincom.behincome.Datas.Marketing;

import com.google.gson.annotations.SerializedName;

public class MarketingProductCommission {

    @SerializedName("CommissionPriceFrom")
    public long CommissionPriceFrom;
    @SerializedName("CommissionPriceTo")
    public long CommissionPriceTo;
    @SerializedName("CommissionPercent")
    public int CommissionPercent = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
