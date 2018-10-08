package com.behincom.behincome.SQL;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

class sqlDelete {

    static List<String> DeleteData(SQLiteDatabase db){
        List<String> lTables = new ArrayList<>();
        @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' order by name", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                lTables.add(c.getString( c.getColumnIndex("name")));
                c.moveToNext();
            }
        }

        return lTables;
    }
    static List<String> DeleteData(SQLiteDatabase db, String TableName){
        List<String> lPrimaryVal = new ArrayList<>();
        @SuppressLint("Recycle") Cursor c = db.rawQuery("PRAGMA table_info(" + TableName  + ")", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                //lPrimaryVal.add(c.getString(1));//0("cid") = Value, 1("name") = Column Name, 2("type") = Type( Integer, Text ), 3("notnull") = not null ( 0 = nullable ), 4("dflt_value") = default value, 5("pk") = primary key ( 1 = is Primary Key )
                int isPrimary = c.getInt( c.getColumnIndex("pk"));
                if(isPrimary == 1){
                    lPrimaryVal.add(c.getString( c.getColumnIndex("name")));
                }
                c.moveToNext();
            }
        }

        return lPrimaryVal;
    }

}
