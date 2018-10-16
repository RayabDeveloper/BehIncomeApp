package com.behincom.behincome.SQL;

import java.lang.reflect.Field;
import java.util.List;

class sqlInsert {

    static String InsertData(Object mClass, String TableName) throws IllegalAccessException {
        String Query = "";
        String TBName = TableName;
        Query = "INSERT INTO " + TBName + " (";
        Field[] mField = mClass.getClass().getDeclaredFields();
        int aa = mField.length;
        for (Field mFields : mField) {
            mFields.setAccessible(true);
            if (!mFields.getName().equalsIgnoreCase("$change") && !mFields.getName().equalsIgnoreCase("serialVersionUID")) {
                if (!(mFields.getAnnotation(RAnnot.class) instanceof RAnnot)) {
                    Query += mFields.getName() + ", ";
                }
            }
        }
        Query = Query.substring(0, Query.length() - 2) + ") VALUES (";
        for (Field mFields : mField) {
            mFields.setAccessible(true);
            if (!mFields.getName().equalsIgnoreCase("$change") && !mFields.getName().equalsIgnoreCase("serialVersionUID")) {
                if (!(mFields.getAnnotation(RAnnot.class) instanceof RAnnot)) {
                    Object value = mFields.get(mClass);
                    if (value == null) {
                        Query += "'', ";
                    } else {
                        String fValue = value.toString();
                        if (fValue.equalsIgnoreCase("false"))
                            fValue = "0";
                        else if (fValue.equalsIgnoreCase("true"))
                            fValue = "1";
                        Query += "'" + fValue + "', ";
                    }
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
        for (Field mFields : mField) {
            try {
                if (!mFields.getName().equalsIgnoreCase("$change") && !mFields.getName().equalsIgnoreCase("serialVersionUID")) {
                    if (!(mFields.getAnnotation(RAnnot.class) instanceof RAnnot)) {
                        String oxx = mFields.getName().substring(0, 3);
                        if (!oxx.equalsIgnoreCase("oxx")) {
                            Query += mFields.getName() + ", ";
                        }
                    }
                }
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
        }
        Query = Query.substring(0, Query.length() - 2) + ") VALUES (";
        for (int j = 0; j < mClass.size(); j++) {
            try {
                for (Field mFields : mField) {
                    try {
                        if (!mFields.getName().equalsIgnoreCase("$change") && !mFields.getName().equalsIgnoreCase("serialVersionUID")) {
                            String oxx = mFields.getName().substring(0, 3);
                            if (!(mFields.getAnnotation(RAnnot.class) instanceof RAnnot)) {
                                if (!oxx.equalsIgnoreCase("oxx")) {
                                    Object value = mFields.get(mClass.get(j));
                                    if (value != null) {
                                        String fValue = value.toString();
                                        if (fValue.equalsIgnoreCase("false"))
                                            fValue = "0";
                                        else if (fValue.equalsIgnoreCase("true"))
                                            fValue = "1";
                                        Query += "'" + fValue + "', ";
                                    } else {
                                        Query += "'', ";
                                    }
                                }
                            }
                        }
                    } catch (Exception Ex) {
                        String Er = Ex.getMessage();
                    }
                }
                Query = Query.substring(0, Query.length() - 2) + "), (";
            } catch (Exception Ex) {
                String Er = Ex.getMessage();
            }
        }
        return Query.substring(0, Query.length() - 3);
    }

}
