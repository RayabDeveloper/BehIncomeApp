package com.behincom.behincome.Loader;

import com.behincom.behincome.Accesories.Device;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.BaseData.BasicDatas;
import com.behincom.behincome.Datas.BaseData.Basic_ActGroups;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFieldGroups;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityStates;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_BusinessManagerMarketerStates;
import com.behincom.behincome.Datas.BaseData.Basic_citi;
import com.behincom.behincome.Datas.BaseData.Basic_Color;
import com.behincom.behincome.Datas.BaseData.Basic_CommissionTypes;
import com.behincom.behincome.Datas.BaseData.Basic_ContactTypes;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStatus;
import com.behincom.behincome.Datas.BaseData.Basic_GenderTypes;
import com.behincom.behincome.Datas.BaseData.Basic_MaritalStatus;
import com.behincom.behincome.Datas.BaseData.Basic_MilitaryStatus;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.BaseData.Basic_PersonRoles;
import com.behincom.behincome.Datas.BaseData.Basic_PersonTypes;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_PropertyGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Ostan;
import com.behincom.behincome.Datas.BaseData.Basic_takGroups;
import com.behincom.behincome.Datas.BaseData.Basic_taks;
import com.behincom.behincome.Datas.Keys.APIKeys;
import com.behincom.behincome.Datas.Marketing.MarketingActResults;
import com.behincom.behincome.Datas.Marketing.MarketingActivityFields;
import com.behincom.behincome.Datas.Marketing.MarketingCities;
import com.behincom.behincome.Datas.Marketing.MarketingCommissionPeriods;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommissions;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.Marketing.MarketingProperties;
import com.behincom.behincome.Datas.Marketing.MarketingTags;
import com.behincom.behincome.Datas.Marketing.MarketingVisitTours;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Web API A :
http://164.138.17.243/api/BaseData/GetAllBaseData

Parameter in Body :
{
	"ReloadAll":"false",
	"BusinessManagerID":"2009",
	"DeviceImei":"358240051111110"
}

Web API B :
http://164.138.17.243/api/BaseData/GetAllBaseData_Ack

Parameter in Body :
{
	"DeviceImei":358240051111110
}

if Application is in the First Run, Call Web API A and set ReloadAll to true;
after get All CAll Web API B
after every Application Run, Call WebAPI A and set ReloadAll to false

if need to Call All Datas, seet ReloadAll to true only, and again Call Web API B
 */

public class LoadBaseData {

    RWInterface rInterface;
    RWInterface rInterface2;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public void setOnLoadDataListener(onLoadAllListener onLoad, onFailedLoad onFailed) {
        this.onLoadListener = onLoad;
        this.onFailed = onFailed;
    }
    public interface onLoadAllListener{
        void onLoads();
    }
    public interface onFailedLoad{
        void onFailed(String Error);
    }
    private onLoadAllListener onLoadListener;
    private onFailedLoad onFailed;
    private void LoadListener(){
        onLoadListener.onLoads();
    }
    private void FailedLoad(String Error){
        onFailed.onFailed(Error);
    }

    public LoadBaseData(){
        rInterface = Retrofite.getClient().create(RWInterface.class);
        Map<String, Object> getBaseData = new HashMap<>();
        boolean reload = Boolean.parseBoolean(Setting.getReloadAll());
        getBaseData.put(APIKeys.ReloadAll.toString(), reload);
        getBaseData.put(APIKeys.DeviceImei.toString(), Device.IMEI());
        try {
            getBaseData.put(APIKeys.BusinessManagerID.toString(), Setting.getBMMUserID());
        } catch (Exception e) {
            getBaseData.put(APIKeys.BusinessManagerID.toString(), Setting.getUserID());
        }

        Call cBaseData = rInterface.RQBaseData(new HashMap<>(getBaseData));//todo check FirstRun ReloadAll(True), and EveryRun ReloadAll(False)
        cBaseData.enqueue(new Callback<BasicDatas>() {
            @Override
            public void onResponse(Call<BasicDatas> call, Response<BasicDatas> response) {
                if(response.isSuccessful()){//todo check FirstRun ReloadAll(True), and EveryRun ReloadAll(False)
                    BasicDatas datas = response.body();
                    Inserter(datas);
                }else{
                    FailedLoad(response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                FailedLoad(t.getMessage());
            }
        });
    }
    private void Inserter(BasicDatas data)
    {
        //todo Write :
        //todo if > 0 : select * where id == id : if exist Update else Insert
        //if Lists was't null, Inserted into Database
        boolean isFirst = Boolean.parseBoolean(Setting.getReloadAll());
        if(isFirst){
            SQL.Insert(data.Basic_Acts, "Basic_Acts");
            SQL.Insert(data.Basic_ActGroups, "Basic_ActGroups");
            SQL.Insert(data.Basic_ActivityFieldGroups, "Basic_ActivityFieldGroups");
            SQL.Insert(data.Basic_ActivityFields, "Basic_ActivityFields");
            SQL.Insert(data.Basic_ActResults, "Basic_ActResults");
            SQL.Insert(data.Basic_ArchiveTypes, "Basic_ArchiveTypes");
            SQL.Insert(data.Basic_citi, "Basic_citi");
            SQL.Insert(data.Basic_CommissionTypes, "Basic_CommissionTypes");
            SQL.Insert(data.Basic_CustomerStates, "Basic_CustomerStates");
            SQL.Insert(data.Basic_ContactTypes, "Basic_ContactTypes");
            SQL.Insert(data.Basic_GenderTypes, "Basic_GenderTypes");
            SQL.Insert(data.Basic_MaritalStatus, "Basic_MaritalStatus");
            SQL.Insert(data.Basic_CustomerStatus, "Basic_CustomerStatus");
            SQL.Insert(data.Basic_MilitaryStatus, "Basic_MilitaryStatus");
            SQL.Insert(data.Basic_NamePrefixes, "Basic_NamePrefixes");
            SQL.Insert(data.Basic_PersonRoles, "Basic_PersonRoles");
            SQL.Insert(data.Basic_PersonTypes, "Basic_PersonTypes");
            SQL.Insert(data.Basic_Properties, "Basic_Properties");
            SQL.Insert(data.Basic_PropertyGroups, "Basic_PropertyGroups");
            SQL.Insert(data.Basic_Ostan, "Basic_Ostan");
            SQL.Insert(data.Basic_takGroups, "Basic_takGroups");
            SQL.Insert(data.Basic_taks, "Basic_taks");
            SQL.Insert(data.MarketingActivityFields, "MarketingActivityFields");
            SQL.Insert(data.MarketingActResults, "MarketingActResults");
            SQL.Insert(data.MarketingCities, "MarketingCities");
            SQL.Insert(data.MarketingCommissionPeriods, "MarketingCommissionPeriods");
            SQL.Insert(data.MarketingProductCommissions, "MarketingProductCommissions");
            SQL.Insert(data.MarketingProducts, "MarketingProducts");
            SQL.Insert(data.MarketingProperties, "MarketingProperties");
            SQL.Insert(data.MarketingTags, "MarketingTags");
            SQL.Insert(data.MarketingVisitTours, "MarketingVisitTours");
            SQL.Insert(data.MarketingSetups, "MarketingSetups");
            SQL.Insert(data.Basic_Color, "Basic_Color");

            SubmitData();
        }else{
            try {
                for (Basic_Acts dataa : data.Basic_Acts) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ActID" + "='" + dataa.ActID + "'")){
                            SQL.Update(dataa, " WHERE ActID" + "='" + dataa.ActID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_ActGroups dataa : data.Basic_ActGroups) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ActGroupID" + "='" + dataa.ActGroupID + "'")){
                            SQL.Update(dataa, " WHERE ActGroupID" + "='" + dataa.ActGroupID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_ActivityFieldGroups dataa : data.Basic_ActivityFieldGroups) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ActivityFieldGroupID" + "='" + dataa.ActivityFieldGroupID + "'")){
                            SQL.Update(dataa, " WHERE ActivityFieldGroupID" + "='" + dataa.ActivityFieldGroupID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_ActivityFields dataa : data.Basic_ActivityFields) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ActivityFieldID" + "='" + dataa.ActivityFieldID + "'")){
                            SQL.Update(dataa, " WHERE ActivityFieldID" + "='" + dataa.ActivityFieldID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_ActResults dataa : data.Basic_ActResults) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ActResultID" + "='" + dataa.ActResultID + "'")){
                            SQL.Update(dataa, " WHERE ActResultID" + "='" + dataa.ActResultID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_ArchiveTypes dataa : data.Basic_ArchiveTypes) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ArchiveTypeID" + "='" + dataa.ArchiveTypeID + "'")){
                            SQL.Update(dataa, " WHERE ArchiveTypeID" + "='" + dataa.ArchiveTypeID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_citi dataa : data.Basic_citi) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE CityID" + "='" + dataa.CityID + "'")){
                            SQL.Update(dataa, " WHERE CityID" + "='" + dataa.CityID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_CommissionTypes dataa : data.Basic_CommissionTypes) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE CommissionTypeID" + "='" + dataa.CommissionTypeID + "'")){
                            SQL.Update(dataa, " WHERE CommissionTypeID" + "='" + dataa.CommissionTypeID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_CustomerStates dataa : data.Basic_CustomerStates) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE CustomerStateID" + "='" + dataa.CustomerStateID + "'")){
                            SQL.Update(dataa, " WHERE CustomerStateID" + "='" + dataa.CustomerStateID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_ContactTypes dataa : data.Basic_ContactTypes) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ContactTypeID" + "='" + dataa.ContactTypeID + "'")){
                            SQL.Update(dataa, " WHERE ContactTypeID" + "='" + dataa.ContactTypeID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_GenderTypes dataa : data.Basic_GenderTypes) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE GenderTypeID" + "='" + dataa.GenderTypeID + "'")){
                            SQL.Update(dataa, " WHERE GenderTypeID" + "='" + dataa.GenderTypeID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_MaritalStatus dataa : data.Basic_MaritalStatus) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MaritalStatusID" + "='" + dataa.MaritalStatusID + "'")){
                            SQL.Update(dataa, " WHERE MaritalStatusID" + "='" + dataa.MaritalStatusID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_CustomerStatus dataa : data.Basic_CustomerStatus) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE CustomerStatusID" + "='" + dataa.CustomerStatusID + "'")){
                            SQL.Update(dataa, " WHERE CustomerStatusID" + "='" + dataa.CustomerStatusID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_MilitaryStatus dataa : data.Basic_MilitaryStatus) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MilitaryStatusID" + "='" + dataa.MilitaryStatusID + "'")){
                            SQL.Update(dataa, " WHERE MilitaryStatusID" + "='" + dataa.MilitaryStatusID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_NamePrefixes dataa : data.Basic_NamePrefixes) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE NamePrefixID" + "='" + dataa.NamePrefixID + "'")){
                            SQL.Update(dataa, " WHERE NamePrefixID" + "='" + dataa.NamePrefixID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_PersonRoles dataa : data.Basic_PersonRoles) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE PersonRoleID" + "='" + dataa.PersonRoleID + "'")){
                            SQL.Update(dataa, " WHERE PersonRoleID" + "='" + dataa.PersonRoleID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_PersonTypes dataa : data.Basic_PersonTypes) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE PersonTypeID" + "='" + dataa.PersonTypeID + "'")){
                            SQL.Update(dataa, " WHERE PersonTypeID" + "='" + dataa.PersonTypeID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_Properties dataa : data.Basic_Properties) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE PropertyID" + "='" + dataa.PropertyID + "'")){
                            SQL.Update(dataa, " WHERE PropertyID" + "='" + dataa.PropertyID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_PropertyGroups dataa : data.Basic_PropertyGroups) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE PropertyGroupID" + "='" + dataa.PropertyGroupID + "'")){
                            SQL.Update(dataa, " WHERE PropertyGroupID" + "='" + dataa.PropertyGroupID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_Ostan dataa : data.Basic_Ostan) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ProvinceID" + "='" + dataa.ProvinceID + "'")){
                            SQL.Update(dataa, " WHERE ProvinceID" + "='" + dataa.ProvinceID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_takGroups dataa : data.Basic_takGroups) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE TagGroupID" + "='" + dataa.TagGroupID + "'")){
                            SQL.Update(dataa, " WHERE TagGroupID" + "='" + dataa.TagGroupID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_taks dataa : data.Basic_taks) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE TagID" + "='" + dataa.TagID + "'")){
                            SQL.Update(dataa, " WHERE TagID" + "='" + dataa.TagID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (Basic_Color dataa : data.Basic_Color) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ColorID" + "='" + dataa.ColorID + "'")){
                            SQL.Update(dataa, " WHERE ColorID" + "='" + dataa.ColorID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                for (MarketingActivityFields dataa : data.MarketingActivityFields) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MarketingActivityFieldID" + "='" + dataa.MarketingActivityFieldID + "'")){
                            SQL.Update(dataa, " WHERE MarketingActivityFieldID" + "='" + dataa.MarketingActivityFieldID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingActResults dataa : data.MarketingActResults) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MarketingActResultID" + "='" + dataa.MarketingActResultID + "'")){
                            SQL.Update(dataa, " WHERE MarketingActResultID" + "='" + dataa.MarketingActResultID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingCities dataa : data.MarketingCities) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MarketingCityID" + "='" + dataa.MarketingCityID + "'")){
                            SQL.Update(dataa, " WHERE MarketingCityID" + "='" + dataa.MarketingCityID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingCommissionPeriods dataa : data.MarketingCommissionPeriods) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MarketingCommissionPeriodID" + "='" + dataa.MarketingCommissionPeriodID + "'")){
                            SQL.Update(dataa, " WHERE MarketingCommissionPeriodID" + "='" + dataa.MarketingCommissionPeriodID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingProductCommissions dataa : data.MarketingProductCommissions) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE ProductCommissionID" + "='" + dataa.ProductCommissionID + "'")){
                            SQL.Update(dataa, " WHERE ProductCommissionID" + "='" + dataa.ProductCommissionID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingProducts dataa : data.MarketingProducts) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MarketingProductID" + "='" + dataa.MarketingProductID + "'")){
                            SQL.Update(dataa, " WHERE MarketingProductID" + "='" + dataa.MarketingProductID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingProperties dataa : data.MarketingProperties) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MarkettingPropertyID" + "='" + dataa.MarkettingPropertyID + "'")){
                            SQL.Update(dataa, " WHERE MarkettingPropertyID" + "='" + dataa.MarkettingPropertyID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingTags dataa : data.MarketingTags) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE MarketingTagID" + "='" + dataa.MarketingTagID + "'")){
                            SQL.Update(dataa, " WHERE MarketingTagID" + "='" + dataa.MarketingTagID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (MarketingVisitTours dataa : data.MarketingVisitTours) {
                    try {
                        if(geter.Any(dataa.getClass(), " WHERE VisitTourID" + "='" + dataa.VisitTourID + "'")){
                            SQL.Update(dataa, " WHERE VisitTourID" + "='" + dataa.VisitTourID + "'");
                        }else{
                            SQL.Insert(dataa);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(geter.Any(data.MarketingSetups.getClass(), " WHERE MarketingSetupId" + "='" + data.MarketingSetups.MarketingSetupId + "'")){
                    SQL.Update(data.MarketingSetups, " WHERE MarketingSetupId" + "='" + data.MarketingSetups.MarketingSetupId + "'");
                }else{
                    SQL.Insert(data.MarketingSetups);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            LoadListener();
        }
    }
    private void SubmitData(){
        rInterface = Retrofite.getClient().create(RWInterface.class);
        Map<String, Object> getBaseData = new HashMap<>();
        getBaseData.put(APIKeys.DeviceImei.toString(), Device.IMEI());
        Call SubmitData = rInterface.RQSubmitBaseData(new HashMap<>(getBaseData));
        SubmitData.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if(response.isSuccessful()){
                    Setting.Save("ReloadAll", "false");
                    LoadListener();
                }else{
                    String Err = "";
                    for (Map.Entry<String, Object> entry : response.body().Errors.entrySet()) {
                        Err += entry.getValue().toString() + "<br>";
                    }
                    FailedLoad(Err);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                FailedLoad(t.getMessage());
            }
        });
    }
}
