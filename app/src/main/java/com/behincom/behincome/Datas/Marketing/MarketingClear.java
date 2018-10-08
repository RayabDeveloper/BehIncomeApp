package com.behincom.behincome.Datas.Marketing;

import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.SQL.RSQLite;

public class MarketingClear {

    public static boolean Clear(){
        try {
            RSQLite SQL = new RSQLite();
            SQL.Execute("DELETE FROM " + Tables.MarketingActivityFields);
            SQL.Execute("DELETE FROM " + Tables.MarketingActResults);
            SQL.Execute("DELETE FROM " + Tables.MarketingCities);
            SQL.Execute("DELETE FROM " + Tables.MarketingCommissionPeriods);
            SQL.Execute("DELETE FROM " + Tables.MarketingProductCommissions);
            SQL.Execute("DELETE FROM " + Tables.MarketingProducts);
            SQL.Execute("DELETE FROM " + Tables.MarketingProperties);
            SQL.Execute("DELETE FROM " + Tables.MarketingTags);
            SQL.Execute("DELETE FROM " + Tables.MarketingVisitTours);

            return true;
        }catch (Exception Ex){
            return false;
        }
    }

}
