package com.behincom.behincome.Datas.Keys;

import com.behincom.behincome.Datas.BaseData.Basic_GenderTypes;
import com.behincom.behincome.Datas.BaseData.Basic_MaritalStatus;
import com.behincom.behincome.Datas.RSQLGeter;

import java.util.List;

public class GenderType {

    final public static int Man = 1;
    final public static int Woman = 2;

    private static RSQLGeter geter = new RSQLGeter();
    public static String getGenderString(int Gender){
        List<Basic_GenderTypes> lList = geter.getList(Basic_GenderTypes.class, " WHERE GenderTypeID='" + Gender + "'");
        return lList.get(0).GenderTypeTitle;
    }

}
