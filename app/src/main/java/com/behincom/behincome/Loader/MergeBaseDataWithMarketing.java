package com.behincom.behincome.Loader;

import com.behincom.behincome.Datas.Marketing.MarketingActivityFields;
import com.behincom.behincome.Datas.Marketing.MarketingActResults;
import com.behincom.behincome.Datas.Marketing.MarketingCities;
import com.behincom.behincome.Datas.Marketing.MarketingProperties;
import com.behincom.behincome.Datas.Marketing.MarketingTags;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

public class MergeBaseDataWithMarketing {

    public void setOnLoadDataListener(LoadBaseData.onLoadAllListener onLoad) {
        this.onLoadListener = onLoad;
    }
    public interface onLoadAllListener{
        void onLoads();
    }
    private LoadBaseData.onLoadAllListener onLoadListener;
    private void LoadListener(){
        onLoadListener.onLoads();
    }

    public static boolean Merge(){
        try {
            RSQLite SQL = new RSQLite();
            RSQLGeter geter = new RSQLGeter();

            List<MarketingActivityFields> lActivityField = geter.getList(MarketingActivityFields.class);
            List<MarketingActResults> lActivityResult = geter.getList(MarketingActResults.class);
            List<MarketingCities> lCities = geter.getList(MarketingCities.class);
            List<MarketingProperties> lProperties = geter.getList(MarketingProperties.class);
            List<MarketingTags> lTag = geter.getList(MarketingTags.class);

            SQL.Execute("UPDATE Basic_ActivityFields SET isCheck='0'");
            SQL.Execute("UPDATE Basic_ActResults SET isCheck='0'");
            SQL.Execute("UPDATE Basic_citi SET isCheck='0'");
            SQL.Execute("UPDATE Basic_Properties SET isCheck='0'");
            SQL.Execute("UPDATE Basic_taks SET isCheck='0'");

            for (MarketingActivityFields data : lActivityField) {
                SQL.Execute("UPDATE Basic_ActivityFields SET isCheck='1' WHERE ActivityFieldID='" + data.ActivityFieldID + "'");
            }
            for (MarketingActResults data : lActivityResult) {
                SQL.Execute("UPDATE Basic_ActResults SET isCheck='1' WHERE ActResultID='" + data.ActResultID + "'");
            }
            for (MarketingCities data : lCities) {
                SQL.Execute("UPDATE Basic_citi SET isCheck='1' WHERE CityID='" + data.CityID + "'");
            }
            for (MarketingProperties data : lProperties) {
                SQL.Execute("UPDATE Basic_Properties SET isCheck='1' WHERE PropertyID='" + data.PropertyID + "'");
            }
            for (MarketingTags data : lTag) {
                SQL.Execute("UPDATE Basic_taks SET isCheck='1' WHERE TagID='" + data.TagID + "'");
            }
            return true;
        }catch (Exception Ex){
            return false;
        }
    }

}
