package com.behincom.behincome.SQL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.behincom.behincome.SQL.sqlObjectHandler.deleteObjectHandler;
import static com.behincom.behincome.SQL.sqlObjectHandler.insertObjectHandler;
import static com.behincom.behincome.SQL.sqlObjectHandler.updateObjectHandler;

public class RSql extends SQLiteOpenHelper{

    private SaveSetting Saver;
    private String CreateTable = "";
    private SQLiteDatabase Database;

//    public RSql(String DatabaseName){
//        super(RSContext.getContext, DatabaseName, null, RSqlInfo.getVersion());
//        RSqlInfo.setName(DatabaseName);
//    }
    public RSql(){
        super(RSContext.getContext, RSqlInfo.getName(), null, RSqlInfo.getVersion());
        Database = this.getWritableDatabase();
    }
    private boolean getQuery(){
        try {
            RCreator creator = new RCreator(this.getClass());
            List<DatabaseInfo> lInfo = new ArrayList<>();
            List<DatabaseInfo> lInfo2 = new ArrayList<>();
            Cursor c = Database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + this.getClass().getSimpleName() + "'", null);
            if (c.getCount() > 0) {
                c = Database.rawQuery("PRAGMA table_info(" + this.getClass().getSimpleName() + ")", null);
                if (c.moveToFirst()) {
                    do {
                        DatabaseInfo data = new DatabaseInfo();
                        data.Name(c.getString(1));
                        data.Type(c.getString(2));
                        lInfo.add(data);
                    } while (c.moveToNext());
                }
                c.close();
                lInfo2 = creator.CreateTable(1);

                if (lInfo.size() == lInfo2.size()) {
                    for (DatabaseInfo data : lInfo2) {
                        boolean isFind = false;
                        for (DatabaseInfo data2 : lInfo) {
                            String d1 = data.Name();
                            String d2 = data2.Name();
                            String d3 = data.Type();
                            String d4 = data2.Type();
                            if (data.Name().equals(data2.Name()) && data.Type().equals(data2.Type())) {
                                isFind = true;
                            }
                        }
                        if (!isFind) {
                            CreateTable = creator.UpgreadTable(Database);
                            return true;
                        }
                    }
                } else {
                    CreateTable = creator.UpgreadTable(Database);
                    return true;
                }
            } else {
                CreateTable = creator.CreateTable();
                return true;
            }
        }catch (Exception Ex){
            return false;
        }
        return false;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Database = sqLiteDatabase;
        try {
            RCreator creator = new RCreator(this.getClass());
            List<DatabaseInfo> lInfo = new ArrayList<>();
            List<DatabaseInfo> lInfo2 = new ArrayList<>();
            Cursor c = Database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + this.getClass().getSimpleName() + "'", null);
            if(c.getCount() > 0){
                c = Database.rawQuery("PRAGMA table_info(" + this.getClass().getSimpleName() + ")", null);
                if (c.moveToFirst()) {
                    do {
                        DatabaseInfo data = new DatabaseInfo();
                        data.Name(c.getString(1));
                        data.Type(c.getString(2));
                        lInfo.add(data);
                    } while (c.moveToNext());
                }
                c.close();
                lInfo2 = creator.CreateTable(1);

                if(lInfo.size() == lInfo2.size()) {
                    for (DatabaseInfo data : lInfo2) {
                        boolean isFind = false;
                        for (DatabaseInfo data2 : lInfo) {
                            if (data.equals(data2)) {
                                isFind = true;
                            }
                        }
                        if(!isFind){
                            CreateTable = creator.UpgreadTable(Database);
                            break;
                        }
                    }
                }else{
                    CreateTable = creator.UpgreadTable(Database);
                }
            }else{
                CreateTable = creator.CreateTable();
            }

            Database.execSQL(CreateTable);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Database = sqLiteDatabase;
        try {
            if (RSqlInfo.getTables() != null) {
                String[] Tables = RSqlInfo.getTables().split(",");
                for (String Table : Tables) {
                    if (Table.trim().length() > 0) {
                        if (!Table.substring(0, 4).equals("View")) {
                            Database.execSQL("DROP TABLE IF EXISTS " + Table);
                        }
                    }
                }
            }
            onCreate(Database);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    public static class DatabaseInfo{
        String Name, Type;

        public String Name() {
            return Name;
        }
        public void Name(String name) {
            Name = name;
        }

        public String Type() {
            return Type;
        }
        public void Type(String type) {
            Type = type;
        }
    }





    // ### Commands ###
    /*
        reNew Database and reCreate All Tables Again

        @return = true is 'success'
     */
    public boolean reNewDatabase() throws Exception {
        List<DatabaseInfo> lInfo = new ArrayList<>();
        List<String> lTables = new ArrayList<>();
        List<String> lCreateTables = new ArrayList<>();
        Cursor cs = Database.rawQuery("SELECT name, sql FROM sqlite_master WHERE type='table' AND name != 'android_metadata'", null);
        if (cs.moveToFirst()) {
            do {
                lTables.add(cs.getString(0));
                String mCreateTable = cs.getString(1);
                lCreateTables.add(mCreateTable);
            } while (cs.moveToNext());
        }
        cs.close();
        for (String Table : lTables) {
            Database.execSQL("DROP TABLE IF EXISTS " + Table);
        }
        for (String createTable : lCreateTables) {
            Database.execSQL(createTable);
        }
        onCreate(Database);

        return true;
    }
    /*
        TRUNCATE Table

        @param Table = String Table Name
        @return = true is 'success'
     */
    public boolean TRUNCATE(Class Class) throws Exception {
        List<DatabaseInfo> lInfo = new ArrayList<>();
        List<String> lTables = new ArrayList<>();
        List<String> lCreateTables = new ArrayList<>();
        Cursor cs = Database.rawQuery("SELECT name, sql FROM sqlite_master WHERE type='table' AND name = '" + Class.getSimpleName() + "'", null);
        if (cs.moveToFirst()) {
            do {
                lTables.add(cs.getString(0));
                String mCreateTable = cs.getString(1);
                lCreateTables.add(mCreateTable);
            } while (cs.moveToNext());
        }
        cs.close();
        for (String mTable : lTables) {
            Database.execSQL("DROP TABLE IF EXISTS " + mTable);
        }
        for (String createTable : lCreateTables) {
            Database.execSQL(createTable);
        }
        onCreate(Database);

        return true;
    }
    /*
        Excecute Data Query

        @param Query = Data Query for Excecuting
        @return = true is 'success'
     */
    public boolean Execute(String Query) throws Exception {
        Database.execSQL(Query);
        Database.close();

        return true;
    }
    /*
        INSERT your Object into SQLite

        @param object = any Object or List<Object> For getting Data
        @param Class = Object Class Type
        @return = true is 'success'
     */
    public boolean Insert(Object object){
        try {
            String TableName = object.getClass().getSimpleName();
            Database.execSQL(insertObjectHandler(object, TableName));
            Database.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }
    /*
        Update your Object into SQLite

        @param object = any Object or List<Object> For getting Data
        @return = true is 'success'
     */
    public boolean Update(Object object){
        try{
            Database.execSQL(updateObjectHandler(object));
            Database.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }
    /*
        Update your Object into SQLite

        @param object = any Object or List<Object> For getting Data
        @param where = SQLite Where for your Update Object
        @return = true is 'success'
     */
    public boolean Update(Object object, String where){
        try{
            where = where.toLowerCase();
            Database.execSQL(updateObjectHandler(object) + " WHERE " + where.replace("where", "").trim());
            Database.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }
    /*
        Delete your Object from SQLite

        @param object = objects can be one Object Or Many objects ( Sample : DataTest, Then DELETE FROM DataTest, Sample 2 : DataTest1, DataTest2, DataTest3..., Then DELETE FROM DataTest1, DELETE FROM DataTest2, DELETE FROM DataTest3 ... )
                        in this method, you must be set your Primary Key Field Value, else return Exception Error Message .
                        so Primary Key Field Must Be Have Value
        @return = true is 'success'
     */
    public boolean Delete(Object... objects)throws IllegalAccessException{
        deleteObjectHandler(Database, objects);
        Database.close();

        return true;
    }
    /*
        Delete your Object from SQLite

        @param Class = Class can be one Class Or Many classes ( Sample : DataTest.class, Then DELETE FROM DataTest, Sample 2 : DataTest1.class, DataTest2.class, DataTest3.class ..., Then DELETE FROM DataTest1, DELETE FROM DataTest2, DELETE FROM DataTest3 ... )
        @return = true is 'success'
     */
    public boolean Delete(Class... Classes){
        try{
            deleteObjectHandler(Database, Classes);
            Database.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }
    /*
        Delete your Object from SQLite

        @param object = object can be one object, Sample : DataTest
        @param where = is your where for Delete method Sample : id=2 AND name='Mehdi' AND date between (2018/01/01, 2018/01/02)
        @return = true is 'success'
     */
    public boolean Delete(Object object, String where)throws IllegalAccessException{
        try{
            deleteObjectHandler(Database, object, where);
            Database.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }
    /*
        Delete your Object from SQLite

        @param Class = Class can be one Class, Sample : DataTest.class
        @param where = is your where for Delete method Sample : id=2 AND name='Mehdi' AND date between (2018/01/01, 2018/01/02)
        @return = true is 'success'
     */
    public boolean Delete(Class Class, String where){
        try{
            deleteObjectHandler(Database, Class, where);
            Database.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }
    /*
        Get Count From All Records in Tour Class Inputed

        @param Class = Object Class Type
        @param return = return count of records in Table
     */
    public int Count(Object object){
        try{
            String Query = "";
            Cursor cs = Database.rawQuery("PRAGMA table_info(" + object.getClass().getSimpleName() + ")", null);
            if (cs.moveToFirst()) {
                do {
                    if(cs.getInt(5) == 1) {
                        try {
                            Field field = object.getClass().getDeclaredField(cs.getString(1));
                            field.setAccessible(true);
                            Query = "SELECT * FROM " + object.getClass().getSimpleName() + " WHERE " + cs.getString(1) + " = '" + field.get(object).toString() + "'";
                        }catch (Exception Ex){
                            String Er = Ex.getMessage();
                        }
                    }
                } while (cs.moveToNext());
            }
            cs.close();

            Cursor Curs = Database.rawQuery(Query, null);
            Database.close();
            return Curs.getCount();
        }catch (Exception ignored){
            return  0;
        }
    }
    /*
        Get Count From All Records in Tour Class Inputed

        @param Class = Object Class Type
        @param return = return count of records in Table
     */
    public int Count(Object object, String where){
        try{
            String Query = "SELECT * FROM " + object.getClass().getSimpleName() + " WHERE " + where.replace("where", "").trim();
            Cursor Curs = Database.rawQuery(Query, null);
            Database.close();
            return Curs.getCount();
        }catch (Exception ignored){
            return  0;
        }
    }
    /*
        Get Count From All Records in Tour Class Inputed

        @param Class = Object Class Type
        @param return = return count of records in Table
     */
    public int Count(Class Class){
        try{
            String Query = "SELECT * FROM " + Class.getSimpleName();
            Cursor Curs = Database.rawQuery(Query, null);
            Database.close();
            return Curs.getCount();
        }catch (Exception ignored){
            return  0;
        }
    }
    public <T> List<T> Select(Class Table){
        SQLiteDatabase db = this.getWritableDatabase();

        List<T> mList = new ArrayList<>();
        try {
            RSHandler handler = new RSHandler();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(handler.getQuery("1", Table), null);
            cursor.moveToFirst();
            String[] ColumnNames = cursor.getColumnNames();
            int cursorCount = cursor.getCount();
            for (int i = 0; i < cursorCount; i++) {
                int CorsurColumn = 0;
                Object Obj = Table.newInstance();
                for (String ColumnName : ColumnNames) {
                    try {
                        Field cField = Table.getDeclaredField(ColumnName);
                        String oxx = "";
                        try{
                            oxx = cField.getName().substring(0, 3);
                        }catch (Exception ignored){}
                        if(!oxx.equalsIgnoreCase("oxx")) {
                            cField.setAccessible(true);

                            if (cField.getType().equals(int.class)) {
                                cField.set(Obj, cursor.getInt(CorsurColumn));
                            } else if (cField.getType().equals(String.class)) {
                                cField.set(Obj, cursor.getString(CorsurColumn));
                            } else if (cField.getType().equals(Double.class)) {
                                cField.set(Obj, cursor.getDouble(CorsurColumn));
                            } else if (cField.getType().equals(Float.class)) {
                                cField.set(Obj, cursor.getFloat(CorsurColumn));
                            } else if (cField.getType().equals(Long.class)) {
                                cField.set(Obj, cursor.getLong(CorsurColumn));
                            }
                        }
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                    CorsurColumn++;
                }
                Class<T> dClass = (Class<T>) Obj.getClass();
                try {
                    mList.add(dClass.cast(Obj));
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                cursor.moveToNext();
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        db.close();
        return mList;
    }
    public <T> List<T> Select(Class Table, String where){
        SQLiteDatabase db = this.getWritableDatabase();

        List<T> mList = new ArrayList<>();
        try {
            RSHandler handler = new RSHandler();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(handler.getQuery("1", Table) + " WHERE " + where.replace("where", "").trim(), null);
            cursor.moveToFirst();
            String[] ColumnNames = cursor.getColumnNames();
            int cursorCount = cursor.getCount();
            for (int i = 0; i < cursorCount; i++) {
                int CorsurColumn = 0;
                Object Obj = Table.newInstance();
                for (String ColumnName : ColumnNames) {
                    try {
                        Field cField = Table.getDeclaredField(ColumnName);
                        String oxx = "";
                        try{
                            oxx = cField.getName().substring(0, 3);
                        }catch (Exception ignored){}
                        if(!oxx.equalsIgnoreCase("oxx")) {
                            cField.setAccessible(true);

                            if (cField.getType().equals(int.class)) {
                                cField.set(Obj, cursor.getInt(CorsurColumn));
                            } else if (cField.getType().equals(String.class)) {
                                cField.set(Obj, cursor.getString(CorsurColumn));
                            } else if (cField.getType().equals(Double.class)) {
                                cField.set(Obj, cursor.getDouble(CorsurColumn));
                            } else if (cField.getType().equals(Float.class)) {
                                cField.set(Obj, cursor.getFloat(CorsurColumn));
                            } else if (cField.getType().equals(Long.class)) {
                                cField.set(Obj, cursor.getLong(CorsurColumn));
                            }
                        }
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                    CorsurColumn++;
                }
                Class<T> dClass = (Class<T>) Obj.getClass();
                try {
                    mList.add(dClass.cast(Obj));
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                cursor.moveToNext();
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        db.close();
        return mList;
    }
    public <T> List<T> Select(String Field, Class Table){
        SQLiteDatabase db = this.getWritableDatabase();

        List<T> mList = new ArrayList<>();
        try {
            RSHandler handler = new RSHandler();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(handler.getQuery(Field, Table), null);
            cursor.moveToFirst();
            String[] ColumnNames = cursor.getColumnNames();
            int cursorCount = cursor.getCount();
            for (int i = 0; i < cursorCount; i++) {
                int CorsurColumn = 0;
                Object Obj = Table.newInstance();
                for (String ColumnName : ColumnNames) {
                    try {
                        Field cField = Table.getDeclaredField(ColumnName);
                        String oxx = "";
                        try{
                            oxx = cField.getName().substring(0, 3);
                        }catch (Exception ignored){}
                        if(!oxx.equalsIgnoreCase("oxx")) {
                            cField.setAccessible(true);

                            if (cField.getType().equals(int.class)) {
                                cField.set(Obj, cursor.getInt(CorsurColumn));
                            } else if (cField.getType().equals(String.class)) {
                                cField.set(Obj, cursor.getString(CorsurColumn));
                            } else if (cField.getType().equals(Double.class)) {
                                cField.set(Obj, cursor.getDouble(CorsurColumn));
                            } else if (cField.getType().equals(Float.class)) {
                                cField.set(Obj, cursor.getFloat(CorsurColumn));
                            } else if (cField.getType().equals(Long.class)) {
                                cField.set(Obj, cursor.getLong(CorsurColumn));
                            }
                        }
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                    CorsurColumn++;
                }
                Class<T> dClass = (Class<T>) Obj.getClass();
                try {
                    mList.add(dClass.cast(Obj));
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                cursor.moveToNext();
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        db.close();
        return mList;
    }
    public <T> List<T> Select(String Field, Class Table, String where){
        SQLiteDatabase db = this.getWritableDatabase();

        List<T> mList = new ArrayList<>();
        try {
            RSHandler handler = new RSHandler();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(handler.getQuery(Field, Table) + " WHERE " + where.replace("where", "").trim(), null);
            cursor.moveToFirst();
            String[] ColumnNames = cursor.getColumnNames();
            int cursorCount = cursor.getCount();
            for (int i = 0; i < cursorCount; i++) {
                int CorsurColumn = 0;
                Object Obj = Table.newInstance();
                for (String ColumnName : ColumnNames) {
                    try {
                        Field cField = Table.getDeclaredField(ColumnName);
                        String oxx = "";
                        try{
                            oxx = cField.getName().substring(0, 3);
                        }catch (Exception ignored){}
                        if(!oxx.equalsIgnoreCase("oxx")) {
                            cField.setAccessible(true);

                            if (cField.getType().equals(int.class)) {
                                cField.set(Obj, cursor.getInt(CorsurColumn));
                            } else if (cField.getType().equals(String.class)) {
                                cField.set(Obj, cursor.getString(CorsurColumn));
                            } else if (cField.getType().equals(Double.class)) {
                                cField.set(Obj, cursor.getDouble(CorsurColumn));
                            } else if (cField.getType().equals(Float.class)) {
                                cField.set(Obj, cursor.getFloat(CorsurColumn));
                            } else if (cField.getType().equals(Long.class)) {
                                cField.set(Obj, cursor.getLong(CorsurColumn));
                            }
                        }
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                    CorsurColumn++;
                }
                Class<T> dClass = (Class<T>) Obj.getClass();
                try {
                    mList.add(dClass.cast(Obj));
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                cursor.moveToNext();
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        db.close();
        return mList;
    }
    /*
        BackUp Data from your Database

        @return = true is 'success'
     */
    public boolean Backup() throws IOException, Exception{
        File sd = Environment.getExternalStorageDirectory();
        File isd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + RSContext.getContext.getPackageName() + "/databases/" + RSqlInfo.getName();
        String backupDBPath = RSqlInfo.getName();
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(isd, backupDBPath);

        source = new FileInputStream(currentDB).getChannel();
        destination = new FileOutputStream(backupDB).getChannel();
        destination.transferFrom(source, 0, source.size());
        source.close();
        destination.close();
        return true;
    }
    /*
        Restore Data from your backup

        @return = true is 'success'
     */
    public boolean Restore() throws IOException {
        InputStream mInput = RSContext.getContext.getAssets().open(RSqlInfo.getName());
        String outFileName = "" + RSqlInfo.getName();
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();

        return true;
    }

    //todo need to SELECT to get Only Object
    //todo need handle boolean type

}
