package com.behincom.behincome.Datas;

import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

public class RSQLGeter<T> {

    RSQLite SQL = new RSQLite();

    public List<T> getListIsCheck(Class ObjectClass){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName() + " WHERE isCheck='1'", ObjectClass);
    }
    public List<T> getListIsCheck(Class ObjectClass, String ContinueWhere){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName() + " WHERE Deleted='0' AND isCheck='1' AND " + ContinueWhere.replace("WHERE", ""), ObjectClass);
    }
    public List<T> getList(Class ObjectClass){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName(), ObjectClass);
    }
    public List<T> getList(Class ObjectClass, String Where){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName() + " " + Where, ObjectClass);
    }
    public List<T> getListByParrentId(Class ObjectClass, String ParrentIdName, int ParrentId){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName() + " WHERE " + ParrentIdName + "='" + ParrentId + "' AND Deleted='0'", ObjectClass);
    }
    public int getCount(Class ObjectClass){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName(), ObjectClass).size();
    }
    public boolean Any(Class ObjectClass){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName(), ObjectClass).size() > 0;
    }
    public int getCount(Class ObjectClass, String Where){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName() + " " + Where, ObjectClass).size();
    }
    public boolean Any(Class ObjectClass, String Where){
        return SQL.Select("SELECT * FROM " + ObjectClass.getSimpleName() + " " + Where, ObjectClass).size() > 0;
    }
}
