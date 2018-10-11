package com.behincom.behincome.SQL;

import com.google.gson.annotations.Expose;

import java.lang.reflect.Field;
import java.util.List;

class sqlInsert {

    static String InsertData(Object mClass, String TableName) throws IllegalAccessException {
        String Query = "";
        String TBName = TableName;
        Query = "INSERT INTO " + TBName + " (";
        Field[] mField = mClass.getClass().getDeclaredFields();
        for (int i=0; i<mField.length - 2; i++) {
            if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                Query += mField[i].getName() + ", ";
            }
        }
        Query = Query.substring(0, Query.length() - 2) + ") VALUES (";
        for(int i=0; i<mField.length - 2; i++){
            if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                Object value = mField[i].get(mClass);
                if(value == null) {
                    Query += "'', ";
                }else {
                    String fValue = value.toString();
                    if(fValue.equalsIgnoreCase("false"))
                        fValue = "0";
                    else if(fValue.equalsIgnoreCase("true"))
                        fValue = "1";
                    Query += "'" + fValue + "', ";
                }
            }
        }
        return Query.substring(0, Query.length() - 2) + ")";
    }
    static String InsertList(List<Object> mClass, String TableName) throws IllegalAccessException {
        String Query = "";
        String TBName = TableName;
        Query = "INSERT INTO " + TBName + " (";
        Field[] mField = mClass.get(0).getClass().getDeclaredFields();
        for (int i = 0; i < mField.length - 2; i++) {
            try {
                if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                    String oxx = mField[i].getName().substring(0, 3);
                    if(!oxx.equalsIgnoreCase("oxx")){
                        Query += mField[i].getName() + ", ";
                    }
                }
            }catch (Exception Ex){
                String Er = Ex.getMessage();
            }
        }
        Query = Query.substring(0, Query.length() - 2) + ") VALUES (";
            for (int j = 0; j < mClass.size(); j++) {
                try {
                    for (int i = 0; i < mField.length - 2; i++) {
                        try {
                            String oxx = mField[i].getName().substring(0, 3);
                            if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                                if(!oxx.equalsIgnoreCase("oxx")){
                                    Object value = mField[i].get(mClass.get(j));
                                    if(value != null) {
                                        String fValue = value.toString();
                                        if(fValue.equalsIgnoreCase("false"))
                                            fValue = "0";
                                        else if(fValue.equalsIgnoreCase("true"))
                                            fValue = "1";
                                        Query += "'" + fValue + "', ";
                                    }else{
                                        Query += "'', ";
                                    }
                                }
                            }
                        }catch (Exception Ex){
                            String Er = Ex.getMessage();
                        }
                    }
                    Query = Query.substring(0, Query.length() - 2) + "), (";
                }catch (Exception Ex){
                    String Er = Ex.getMessage();
                }
            }
        return Query.substring(0, Query.length() - 3);
    }

}
