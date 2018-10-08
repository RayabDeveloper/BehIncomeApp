package com.behincom.behincome.SQL;

import java.lang.reflect.Field;
import java.util.List;

class sqlUpdate {

    static String UpdateData(Object mClass) throws IllegalAccessException {
        String Query = "";
        String TBName = mClass.getClass().getSimpleName();
        Query = "UPDATE " + TBName + " SET ";
        Field[] mField = mClass.getClass().getDeclaredFields();
        for (int i=0; i<mField.length - 2; i++) {
            if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                Object value = mField[i].get(mClass);
                String fValue = value.toString();
                Query += mField[i].getName() + "='" + fValue + "', ";
            }
        }
        return Query.substring(0, Query.length() - 2);
    }
    static String UpdateDataList(List<Object> mClass) throws IllegalAccessException {
        String mQuery = "";
        String TBName = mClass.getClass().getSimpleName();
        for(int j=0; j<mClass.size(); j++) {
            String Query = "";
            Query = "UPDATE " + TBName + " SET ";
            Field[] mField = mClass.get(j).getClass().getDeclaredFields();
            for (int i = 0; i < mField.length - 2; i++) {
                if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                    Object value = mField[i].get(mClass.get(j));
                    String fValue = value.toString();
                    Query += mField[i].getName() + "='" + fValue + "', ";
                }
            }
            mQuery = mQuery + Query.substring(0, Query.length() - 2) + ";";
        }
        return mQuery;
    }

    static String UpdateData(Object mClass, String Where) throws IllegalAccessException {
        String Query = "";
        String TBName = mClass.getClass().getSimpleName();
        Query = "UPDATE " + TBName + " SET ";
        Field[] mField = mClass.getClass().getDeclaredFields();
        for (int i=0; i<mField.length - 2; i++) {
            if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                Object value = mField[i].get(mClass);
                String fValue = value.toString();
                Query += mField[i].getName() + "='" + fValue + "', ";
            }
        }
        return Query.substring(0, Query.length() - 2) + " WHERE " + Where.replace("where ", "").trim();
    }
    static String UpdateDataList(List<Object> mClass, String Where) throws IllegalAccessException {
        String mQuery = "";
        String TBName = mClass.getClass().getSimpleName();
        for(int j=0; j<mClass.size(); j++) {
            String Query = "";
            Query = "UPDATE " + TBName + " SET ";
            Field[] mField = mClass.get(j).getClass().getDeclaredFields();
            for (int i = 0; i < mField.length - 2; i++) {
                if (!(mField[i].getAnnotation(RAnnot.class) instanceof RAnnot)) {
                    Object value = mField[i].get(mClass.get(j));
                    String fValue = value.toString();
                    Query += mField[i].getName() + "='" + fValue + "', ";
                }
            }
            mQuery = mQuery + Query.substring(0, Query.length() - 2) + " WHERE " + Where.replace("where ", "").trim() + ";";
        }
        return mQuery;
    }

}
