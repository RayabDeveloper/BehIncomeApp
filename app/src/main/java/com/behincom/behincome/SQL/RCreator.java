package com.behincom.behincome.SQL;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RCreator {

    private Class<? extends RSql> mClass;
    public RCreator(Class<? extends RSql> cls){
        mClass = cls;
    }
    public String CreateTable(){
        Field[] ff = mClass.getDeclaredFields();
        RSqlInfo.saveTable(mClass.getSimpleName());
        String CreateTbl = "CREATE TABLE " + mClass.getSimpleName() + " (";

        /*
            dar halat e sade : (A)
                esm e field + type + key
            dar halate Object : (B)
                esme Object + _id + type
            dar halate List<Object> : (C)
                esme Object + _lst + type
         */
        for (Field field : ff) {
            boolean trash = false;
            trash = field.getName().equals("$change") || field.getName().equals("serialVersionUID");
            if(!trash) {
                if (isType(field.getType())) {
                    CreateTbl += getField(field.getName(), field.getType(), field.getAnnotation(RAnnot.class));// A
                } else {
                    if (field.getType().isAssignableFrom(List.class)) {//isList<Object>
                        String ClassName = field.getDeclaringClass().getSimpleName();// C
                        CreateTbl += ClassName + "_lst TEXT, ";
                    } else {//isObject
                        String ClassName = field.getDeclaringClass().getSimpleName();// B
                        CreateTbl += ClassName + "_id TEXT, ";
                    }
                }
            }
        }
        CreateTbl = CreateTbl.substring(0, CreateTbl.length() - 2) + ");";

        return CreateTbl;
    }
    public String UpgreadTable(SQLiteDatabase Database){
        Database.execSQL("DROP TABLE IF EXISTS " + mClass.getSimpleName());

        Field[] ff = mClass.getDeclaredFields();
        RSqlInfo.saveTable(mClass.getSimpleName());
        String CreateTbl = "CREATE TABLE " + mClass.getSimpleName() + " (";

        /*
            dar halat e sade : (A)
                esm e field + type + key
            dar halate Object : (B)
                esme Object + _id + type
            dar halate List<Object> : (C)
                esme Object + _lst + type
         */
        for (Field field : ff) {
            boolean trash = false;
            trash = field.getName().equals("$change") || field.getName().equals("serialVersionUID");
            if(!trash) {
                if (isType(field.getType())) {
                    CreateTbl += getField(field.getName(), field.getType(), field.getAnnotation(RAnnot.class));// A
                } else {
                    if (field.getType().isAssignableFrom(List.class)) {//isList<Object>
                        String ClassName = field.getDeclaringClass().getSimpleName();// C
                        CreateTbl += ClassName + "_lst TEXT, ";
                    } else {//isObject
                        String ClassName = field.getDeclaringClass().getSimpleName();// B
                        CreateTbl += ClassName + "_id TEXT, ";
                    }
                }
            }
        }
        CreateTbl = CreateTbl.substring(0, CreateTbl.length() - 2) + ");";

        return CreateTbl;
    }
    public List<RSql.DatabaseInfo> CreateTable(int Nothing){
        Field[] ff = mClass.getDeclaredFields();

        List<RSql.DatabaseInfo> lList = new ArrayList<>();
        /*
            dar halat e sade : (A)
                esm e field + type + key
            dar halate Object : (B)
                esme Object + _id + type
            dar halate List<Object> : (C)
                esme Object + _lst + type
         */
        for (Field field : ff) {
            boolean trash = false;
            trash = field.getName().equals("$change") || field.getName().equals("serialVersionUID");
            if(!trash) {
                RSql.DatabaseInfo data = new RSql.DatabaseInfo();
                if (isType(field.getType())) {
                    data.Name(field.getName());
                    data.Type(getType(field.getType()));
                } else {
                    if (field.getType().isAssignableFrom(List.class)) {//isList<Object>
                        String ClassName = field.getDeclaringClass().getSimpleName();// C
                        data.Name(ClassName + "_lst");
                        data.Type("TEXT");
                    } else {//isObject
                        String ClassName = field.getDeclaringClass().getSimpleName();// B
                        data.Name(ClassName + "_id");
                        data.Type("TEXT");
                    }
                }
                lList.add(data);
            }
        }

        return lList;
    }
    private String getField(String name, Class<?> type, RAnnot info){
        return name + " " + getType(type) + " " + (info != null ? getIsKey(info) : "") + ", ";
    }
    private String getType(Class<?> type){
        if(type.equals(int.class) || type.equals(Integer.class) || type.equals(Long.class) || type.equals(long.class)){
            return "INTEGER";
        }else if(type.equals(String.class) || type.equals(Date.class) || type.equals(java.sql.Date.class) || type.equals(java.sql.Time.class)){
            return "TEXT";
        }else if(type.equals(float.class) || type.equals(Float.class)){
            return "REAL";
        }else if(type.equals(double.class) || type.equals(Double.class)){
            return "REAL";
        }else if(type.equals(boolean.class) || type.equals(Boolean.class)){
            return "NUMERIC";
        }else{
            return "OTHER";
        }
    }
    private boolean isType(Class<?> type){
        if(type.equals(int.class)){
            return true;
        }else if(type.equals(String.class)){
            return true;
        }else if(type.equals(float.class)){
            return true;
        }else if(type.equals(double.class)){
            return true;
        }else if(type.equals(Integer.class)){
            return true;
        }else if(type.equals(Double.class)){
            return true;
        }else if(type.equals(Float.class)){
            return true;
        }else if(type.equals(double.class)){
            return true;
        }else if(type.equals(long.class)){
            return true;
        }else if(type.equals(Long.class)){
            return true;
        }else if(type.equals(Date.class)){
            return true;
        }else if(type.equals(java.sql.Time.class)){
            return true;
        }else if(type.equals(Boolean.class)){
            return true;
        }else return type.equals(boolean.class);
    }
    private String getIsKey(RAnnot info){
        if(info.Key() == RSType.PRIMARY)
            return " PRIMARY KEY";
        else
            return "";
    }

}
