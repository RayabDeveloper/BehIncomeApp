package com.behincom.behincome.Datas.BaseData;

import com.behincom.behincome.Datas.Marketing.MarketingActResults;
import com.behincom.behincome.Datas.Marketing.MarketingActivityFields;
import com.behincom.behincome.Datas.Marketing.MarketingCities;
import com.behincom.behincome.Datas.Marketing.MarketingCommissionPeriods;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommissions;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.Marketing.MarketingProperties;
import com.behincom.behincome.Datas.Marketing.MarketingTags;
import com.behincom.behincome.Datas.Marketing.MarketingVisitTours;
import com.behincom.behincome.Datas.Marketing.ToSend.MarketingSetups;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BasicDatas {
    @SerializedName("Basic_GenderTypes")
    public List<Basic_GenderTypes> Basic_GenderTypes = new ArrayList<>();
    @SerializedName("Basic_MaritalStatus")
    public List<Basic_MaritalStatus> Basic_MaritalStatus = new ArrayList<>();
    @SerializedName("Basic_MilitaryStatus")
    public List<Basic_MilitaryStatus> Basic_MilitaryStatus = new ArrayList<>();
    @SerializedName("Basic_Acts")
    public List<Basic_Acts> Basic_Acts = new ArrayList<>();
    @SerializedName("Basic_ActivityFieldGroups")
    public List<Basic_ActivityFieldGroups> Basic_ActivityFieldGroups = new ArrayList<>();
    @SerializedName("Basic_ActResults")
    public List<Basic_ActResults> Basic_ActResults = new ArrayList<>();
    @SerializedName("Basic_ActivityStates")
    public List<Basic_ActivityStates> Basic_ActivityStates = new ArrayList<>();
    @SerializedName("Basic_ActGroups")
    public List<Basic_ActGroups> Basic_ActGroups = new ArrayList<>();
    @SerializedName("Basic_ArchiveTypes")
    public List<Basic_ArchiveTypes> Basic_ArchiveTypes = new ArrayList<>();
    @SerializedName("Basic_BusinessManagerMarketerStates")
    public List<Basic_BusinessManagerMarketerStates> Basic_BusinessManagerMarketerStates = new ArrayList<>();
    @SerializedName("Basic_CommissionTypes")
    public List<Basic_CommissionTypes> Basic_CommissionTypes = new ArrayList<>();
    @SerializedName("Basic_ContactTypes")
    public List<Basic_ContactTypes> Basic_ContactTypes = new ArrayList<>();
    @SerializedName("Basic_CustomerStates")
    public List<Basic_CustomerStates> Basic_CustomerStates = new ArrayList<>();
    @SerializedName("Basic_CustomerStatus")
    public List<Basic_CustomerStatus> Basic_CustomerStatus = new ArrayList<>();
    @SerializedName("Basic_NamePrefixes")
    public List<Basic_NamePrefixes> Basic_NamePrefixes = new ArrayList<>();
    @SerializedName("Basic_PersonRoles")
    public List<Basic_PersonRoles> Basic_PersonRoles = new ArrayList<>();
    @SerializedName("Basic_PersonTypes")
    public List<Basic_PersonTypes> Basic_PersonTypes = new ArrayList<>();
    @SerializedName("Basic_PropertyGroups")
    public List<Basic_PropertyGroups> Basic_PropertyGroups = new ArrayList<>();
    @SerializedName("Basic_TagGroups")
    public List<Basic_TagGroups> Basic_TagGroups = new ArrayList<>();
    @SerializedName("Basic_Tags")
    public List<Basic_Tags> Basic_Tags = new ArrayList<>();
    @SerializedName("Basic_Cities")
    public List<Basic_Cities> Basic_Cities = new ArrayList<>();
    @SerializedName("Basic_Properties")
    public List<Basic_Properties> Basic_Properties = new ArrayList<>();
    @SerializedName("Basic_Provinces")
    public List<Basic_Provinces> Basic_Provinces = new ArrayList<>();
    @SerializedName("Basic_ActivityFields")
    public List<Basic_ActivityFields> Basic_ActivityFields = new ArrayList<>();
    @SerializedName("Basic_AndroidKeyboardTypes")
    public List<Basic_AndroidKeyboardTypes> Basic_AndroidKeyboardTypes = new ArrayList<>();
    @SerializedName("MarketingActivityFields")
    public List<com.behincom.behincome.Datas.Marketing.MarketingActivityFields> MarketingActivityFields = new ArrayList<>();
    @SerializedName("MarketingActResults")
    public List<com.behincom.behincome.Datas.Marketing.MarketingActResults> MarketingActResults = new ArrayList<>();
    @SerializedName("MarketingCities")
    public List<com.behincom.behincome.Datas.Marketing.MarketingCities> MarketingCities = new ArrayList<>();
    @SerializedName("MarketingCommissionPeriods")
    public List<com.behincom.behincome.Datas.Marketing.MarketingCommissionPeriods> MarketingCommissionPeriods = new ArrayList<>();
    @SerializedName("MarketingProductCommissions")
    public List<com.behincom.behincome.Datas.Marketing.MarketingProductCommissions> MarketingProductCommissions = new ArrayList<>();
    @SerializedName("MarketingProducts")
    public List<com.behincom.behincome.Datas.Marketing.MarketingProducts> MarketingProducts = new ArrayList<>();
    @SerializedName("MarketingProperties")
    public List<com.behincom.behincome.Datas.Marketing.MarketingProperties> MarketingProperties = new ArrayList<>();
    @SerializedName("MarketingTags")
    public List<com.behincom.behincome.Datas.Marketing.MarketingTags> MarketingTags = new ArrayList<>();
    @SerializedName("MarketingVisitTours")
    public List<com.behincom.behincome.Datas.Marketing.MarketingVisitTours> MarketingVisitTours = new ArrayList<>();
    @SerializedName("MarketingSetups")
    public MarketingSetups MarketingSetups = new MarketingSetups();
}
