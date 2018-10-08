package com.behincom.behincome.Datas.Marketing;

import com.google.gson.annotations.SerializedName;

public class MarketingCommissionPeriods {

    @SerializedName("MarketingCommissionPeriodID")
    public int MarketingCommissionPeriodID = 0;
    @SerializedName("UserID")
    public int UserID = 0;
    @SerializedName("MarketingCommissionPeriodTitle")
    public String MarketingCommissionPeriodTitle = "";
    @SerializedName("MarketingCommissionPeriodDescription")
    public String MarketingCommissionPeriodDescription = "";
    @SerializedName("MarketingCommissionPeriodDateFrom")
    public String MarketingCommissionPeriodDateFrom;
    @SerializedName("MarketingCommissionPeriodDateTo")
    public String MarketingCommissionPeriodDateTo;

}
