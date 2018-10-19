package com.behincom.behincome.Datas.Keys;

import com.behincom.behincome.Datas.BaseData.Basic_MaritalStatus;
import com.behincom.behincome.Datas.RSQLGeter;

import java.util.ArrayList;
import java.util.List;

public class MaritalType {

    final public static int Married = 1;
    final public static int Single = 2;

    private static RSQLGeter geter = new RSQLGeter();
    public static String getMaritalString(int Marital){
        try {
            List<Basic_MaritalStatus> lList = geter.getList(Basic_MaritalStatus.class, " WHERE MaritalStatusID='" + Marital + "'");
            return lList.get(0).MaritalStatusTitle;
        } catch (Exception e) {
            e.printStackTrace();
            return "مشخص نشده";
        }
    }

}
