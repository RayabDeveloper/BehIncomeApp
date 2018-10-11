package com.behincom.behincome.SQL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.app.AppController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.behincom.behincome.SQL.sqlObjectHandler.deleteObjectHandler;
import static com.behincom.behincome.SQL.sqlObjectHandler.insertObjectHandler;
import static com.behincom.behincome.SQL.sqlObjectHandler.updateObjectHandler;

public class RSQLite extends SQLiteOpenHelper {

    //Version Of This Database ( Befor Change And Upgrade to New Changes, Must be Change Version ++ )
    private static int SQLiVersion = 1;
    //Database Name
    private static String DatabaseName = "Behincom";
    //Create Tables Here
    private static String[] CreatTableQuery = RSQLiteCreateTable.getCreatTableQuery;

    private SQLiteDatabase Database;
    public RSQLite(){
        super(AppController.getContext, DatabaseName, null, SQLiVersion);
    }
    public void open() throws SQLException {
        Database = this.getWritableDatabase();
    }
    public void close(){
        try {
            this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            for(String CREAT_TABLE : CreatTableQuery){
                try {
                    db.execSQL(CREAT_TABLE);
                }catch (Exception Ex){
                    String Er = Ex.getMessage();
                }
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int Version){
        List<Tables> somethingList = new ArrayList<Tables>(EnumSet.allOf(Tables.class));
        for(Tables Table : somethingList){
            if(!Table.toString().substring(0, 4).equals("View")){
                db.execSQL("DROP TABLE IF EXISTS " + Table.toString());
            }
        }
        onCreate(db);
    }
    public String Execute(String Query){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(Query);
            db.close();

            return "Execute SuccessFully.";
        }catch (Exception Ex){
            return  Ex.getMessage();
        }
    }// Can Execute Your Query Here
    public boolean Insert(Object mClass, String TableName){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(insertObjectHandler(mClass, TableName));
            db.close();

            return true;
        }catch (Exception Ex){
             return false;
        }
    }
    public boolean Insert(Object mClass, Class TableName){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(insertObjectHandler(mClass, TableName.getClass().getSimpleName()));
            db.close();

            return true;
        }catch (Exception Ex){
             return false;
        }
    }
    public boolean Insert(Object mClass){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(insertObjectHandler(mClass, mClass.getClass().getSimpleName()));
            db.close();

            return true;
        }catch (Exception Ex){
             return false;
        }
    }// INSERT To Your Table From Your ObjectData And Name
    public boolean Update(Object mClass){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(updateObjectHandler(mClass));
            db.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }// UPDATE Your Table From Your ObjectData And Name
    public boolean Update(Object mClass, String where){
        try{
            where = where.toLowerCase();
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(updateObjectHandler(mClass, where));
            db.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }// UPDATE Your Table From Your ObjectData And Name AND Using Your Where Statment
    public boolean Delete(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            deleteObjectHandler(db);
            db.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }// DELETE ALL Datas From Your All Tables
    public boolean Delete(Object... objects){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            deleteObjectHandler(db, objects);
            db.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }
    public boolean Delete(Object objects, String WHERE){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            deleteObjectHandler(db, objects, WHERE.replace("WHERE", ""));
            db.close();

            return true;
        }catch (Exception Ex){
            return false;
        }
    }// DELETE ALL Datas From Your All Tables Using Objects
    public <T> List<T> Select(String Query, Class mClass){
        SQLiteDatabase db = this.getWritableDatabase();
        List<T> mList = new ArrayList<>();
        try {
            String OrderField = "";
            Field[] kField = mClass.getDeclaredFields();
            for (Field tField : kField) {
                if(tField.getName().contains("Order"))
                    OrderField = tField.getName();
            }
            if(OrderField.length() > 4)
                Query = Query + " ORDER BY " + OrderField;
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(Query, null);
            cursor.moveToFirst();
            String[] ColumnNames = cursor.getColumnNames();
            int cursorCount = cursor.getCount();
            for (int i = 0; i < cursorCount; i++) {
                int CorsurColumn = 0;
                Object Obj = mClass.newInstance();
                for (String ColumnName : ColumnNames) {
                    try {
                        Field cField = mClass.getDeclaredField(ColumnName);
                        String oxx = "";
                        try{
                            oxx = cField.getName().substring(0, 3);
                        }catch (Exception ignored){}
                        if(!oxx.equalsIgnoreCase("oxx")) {
                            cField.setAccessible(true);

                            if (cField.getType().equals(int.class)) {
                                cField.set(Obj, cursor.getInt(CorsurColumn));
                            } else if (cField.getType().equals(long.class) || cField.getType().equals(Long.class)) {
                                cField.set(Obj, cursor.getLong(CorsurColumn));
                            } else if (cField.getType().equals(String.class)) {
                                cField.set(Obj, cursor.getString(CorsurColumn));
                            } else if (cField.getType().equals(Double.class)) {
                                cField.set(Obj, cursor.getDouble(CorsurColumn));
                            } else if (cField.getType().equals(Float.class)) {
                                cField.set(Obj, cursor.getFloat(CorsurColumn));
                            } else if (cField.getType().equals(Long.class)) {
                                cField.set(Obj, cursor.getLong(CorsurColumn));
                            } else if (cField.getType().equals(boolean.class)) {
                                String aa = cursor.getString(CorsurColumn);
                                int aaa = cursor.getInt(CorsurColumn);
                                cField.set(Obj, (cursor.getInt(CorsurColumn) == 1 ? true : false));
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
    }// SELECT Statment Using Your Query Only
    public int Count(String TableName){
        try{
            String Query = "SELECT * FROM " + TableName;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor Curs = db.rawQuery(Query, null);
            db.close();
            return Curs.getCount();
        }catch (Exception ignored){
            return  0;
        }
    }// Get Count of Records Using Your TableName
    public boolean backUpDataBase(Context context)
    {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File isd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File data = Environment.getDataDirectory();
            FileChannel source=null;
            FileChannel destination=null;
            String currentDBPath = "/data/"+ "com.takbelit.charter.charter" +"/databases/"+DatabaseName;
            String backupDBPath = DatabaseName;
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(isd, backupDBPath);
            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return true;
    }
    public void copyDataBase(Context context) throws IOException
    {
        try {
            InputStream mInput = context.getAssets().open(DatabaseName);
            String outFileName = "" + DatabaseName;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
