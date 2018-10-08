package com.behincom.behincome.Loader;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.Marketing.MarketingClear;
import com.behincom.behincome.Datas.Marketing.MarketingDatas;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommission;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommissions;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadMarketingData {

//    RWInterface rInterface;
//    RSQLite SQL = new RSQLite();
//
//    public void setOnLoadDataListener(LoadBaseData.onLoadAllListener onLoad) {
//        this.onLoadListener = onLoad;
//    }
//    public interface onLoadAllListener{
//        void onLoads();
//    }
//    private LoadBaseData.onLoadAllListener onLoadListener;
//    private void LoadListener(){
//        onLoadListener.onLoads();
//    }
//
//    public LoadMarketingData(){
//        rInterface = Retrofite.getClient().create(RWInterface.class);
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("BusinessManagerID", Setting.getBMMUserID());
//
//        String token = Setting.getToken();
//        Call cMarketingData = rInterface.RQGetMarketingAllData(token, map);
//        cMarketingData.enqueue(new Callback<MarketingDatas>() {
//            @Override
//            public void onResponse(Call<MarketingDatas> call, Response<MarketingDatas> response) {
//                if(response.isSuccessful()){
//                    MarketingDatas datas = response.body();
//                    Inserter(datas);
//                    LoadListener();
//                }
//            }
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                String asdf = "ASD";
//            }
//        });
//    }
//    private void Inserter(MarketingDatas data)
//    {
//        //if Lists was't null, Inserted into Database
//        if(MarketingClear.Clear()){
//            if(data.MarketingActivityFields.size() > 0){
//                SQL.Insert(data.MarketingActivityFields, "MarketingActivityFields");
//            }
//            if(data.MarketingActResults.size() > 0){
//                SQL.Insert(data.MarketingActResults, "MarketingActResults");
//            }
//            if(data.MarketingCities.size() > 0){
//                SQL.Insert(data.MarketingCities, "MarketingCities");
//            }
//            if(data.MarketingCommissionPeriods.size() > 0){
//                SQL.Insert(data.MarketingCommissionPeriods, "MarketingCommissionPeriods");
//            }
//            if(data.MarketingProductCommissions.size() > 0){
////                SQL.Insert(data.MarketingProductCommissions, "MarketingProductCommissions");
//                for (MarketingProductCommissions dataa : data.MarketingProductCommissions) {
//                    SQL.Execute("INSERT INTO MarketingProductCommissions " +
//                            "(ProductCommissionID, MarketingProductID, CommissionPriceFrom, CommissionPriceTo, CommissionPercent) " +
//                            "VALUES " +
//                            "('" + dataa.ProductCommissionID + "', '" + dataa.MarketingProductID + "', '" +
//                            dataa.CommissionPriceFrom + "', '" + dataa.CommissionPriceTo + "', '" + dataa.CommissionPercent + "')");
//                }
//                RSQLGeter geter = new RSQLGeter();
//                List<MarketingProductCommissions> lComision = geter.getList(MarketingProductCommission.class);
//                String gg = "ASD";
//            }
//            if(data.MarketingProducts.size() > 0){
//                SQL.Insert(data.MarketingProducts, "MarketingProducts");
//            }
//            if(data.MarketingProperties.size() > 0){
//                SQL.Insert(data.MarketingProperties, "MarketingProperties");
//            }
//            if(data.MarketingTags.size() > 0){
//                SQL.Insert(data.MarketingTags, "MarketingTags");
//            }
//            if(data.MarketingVisitTours.size() > 0){
//                SQL.Insert(data.MarketingVisitTours, "MarketingVisitTours");
//            }
//        }else {
//            String onError = "ASD";
//        }
//    }

}
