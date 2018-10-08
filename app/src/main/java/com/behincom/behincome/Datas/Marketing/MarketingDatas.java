package com.behincom.behincome.Datas.Marketing;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MarketingDatas {

    @SerializedName("MarketingActivityFields")
    public List<MarketingActivityFields> MarketingActivityFields = new ArrayList<>();
    @SerializedName("MarketingActResults")
    public List<MarketingActResults> MarketingActResults = new ArrayList<>();
    @SerializedName("MarketingCities")
    public List<MarketingCities> MarketingCities = new ArrayList<>();
    @SerializedName("MarketingCommissionPeriods")
    public List<MarketingCommissionPeriods> MarketingCommissionPeriods = new ArrayList<>();
    @SerializedName("MarketingProductCommissions")
    public List<MarketingProductCommissions> MarketingProductCommissions = new ArrayList<>();
    @SerializedName("MarketingProducts")
    public List<MarketingProducts> MarketingProducts = new ArrayList<>();
    @SerializedName("MarketingProperties")
    public List<MarketingProperties> MarketingProperties = new ArrayList<>();
    @SerializedName("MarketingTags")
    public List<MarketingTags> MarketingTags = new ArrayList<>();
    @SerializedName("MarketingVisitTours")
    public List<MarketingVisitTours> MarketingVisitTours = new ArrayList<>();

}
