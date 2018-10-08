package com.behincom.behincome.SQL;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.behincom.behincome.SQL.sqlCast.cast;
import static com.behincom.behincome.SQL.sqlDelete.DeleteData;
import static com.behincom.behincome.SQL.sqlInsert.InsertData;
import static com.behincom.behincome.SQL.sqlInsert.InsertList;
import static com.behincom.behincome.SQL.sqlUpdate.UpdateData;
import static com.behincom.behincome.SQL.sqlUpdate.UpdateDataList;

class sqlObjectHandler {

    static String insertObjectHandler(Object mClass, String TableName) throws IllegalAccessException {
        if (mClass instanceof Collection<?>){
            List<Object> nClass = cast(mClass);
            return InsertList(nClass, TableName);
        }else{
            return InsertData(mClass, TableName);
        }
    }

    static String updateObjectHandler(Object mClass) throws IllegalAccessException {
        if (mClass instanceof Collection<?>){
            List<Object> nClass = cast(mClass);
            return UpdateDataList(nClass);
        }else{
            return UpdateData(mClass);
        }
    }
    static String updateObjectHandler(Object mClass, String Where) throws IllegalAccessException {
        if (mClass instanceof Collection<?>){
            List<Object> nClass = cast(mClass);
            return UpdateDataList(nClass, Where);
        }else{
            return UpdateData(mClass, Where);
        }
    }

    static void deleteObjectHandler(SQLiteDatabase db, Object... mClass) throws IllegalAccessException {
        for (Object mObj : mClass) {
            if(mObj instanceof Collection<?>){
                List<Object> nClass = cast(mClass);
                for (Object obj : nClass) {

                    Cursor cs = db.rawQuery("PRAGMA table_info(" + obj.getClass().getSimpleName() + ")", null);
                    if (cs.moveToFirst()) {
                        do {
                            if(cs.getInt(5) == 1) {
                                try {
                                    Field field = obj.getClass().getDeclaredField(cs.getString(1));
                                    if (!(field.getAnnotation(RAnnot.class) instanceof RAnnot)) {
                                        field.setAccessible(true);
                                        db.execSQL("DELETE FROM " + obj.getClass().getSimpleName() + " WHERE " + cs.getString(1) + "='" + field.get(obj).toString() + "'");
                                    }
                                }catch (Exception Ex){
                                    String Er = Ex.getMessage();
                                }
                            }
                        } while (cs.moveToNext());
                    }
                    cs.close();
                }
            }else{
                db.execSQL("DELETE FROM " + mObj.getClass().getSimpleName());
            }
        }
    }
    static void deleteObjectHandler(SQLiteDatabase db, Class... mClass) throws IllegalAccessException {
        for (Object obj : mClass) {
            db.execSQL("DELETE FROM " + obj.getClass().getSimpleName());
        }
    }
    static void deleteObjectHandler(SQLiteDatabase db, Object mClass, String where) throws IllegalAccessException {
        if(mClass instanceof Collection<?>){
            List<Object> nClass = cast(mClass);
            for (Object obj : nClass) {
                db.execSQL("DELETE FROM " + obj.getClass().getSimpleName() + " WHERE " + where.replace("where", "").trim());
            }
        }else{
            db.execSQL("DELETE FROM " + mClass.getClass().getSimpleName() + " WHERE " + where.replace("where", "").trim());
        }
    }
    static void deleteObjectHandler(SQLiteDatabase db, Class mClass, String where) throws IllegalAccessException {
        db.execSQL("DELETE FROM " + mClass.getClass().getSimpleName() + " WHERE " + where.replace("where", "").trim());
    }

}
