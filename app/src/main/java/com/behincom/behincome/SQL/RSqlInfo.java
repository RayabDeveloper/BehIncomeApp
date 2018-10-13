package com.behincom.behincome.SQL;

public class RSqlInfo {

    private static SaveSetting saver;

    public RSqlInfo(String DatabaseName){
        saver = new SaveSetting(RSContext.getContext);
        String DBName = saver.Load(RSType.DatabaseName.toString());
        int DBVersion = 1;
        try {DBVersion = Integer.parseInt(saver.Load(RSType.DatabaseVersion.toString())); }catch (Exception ignored){ }
        if(DBName.length() > 0){
            if(DBVersion == 0)
                DBVersion = 1;
            saver.Save(RSType.DatabaseVersion.toString(), Integer.toString(DBVersion));
        }else{
            saver.Save(RSType.DatabaseName.toString(), DatabaseName);
            saver.Save(RSType.DatabaseVersion.toString(), Integer.toString(DBVersion));
        }
    }
    public static String getName(){
        return saver.Load(RSType.DatabaseName.toString());
    }
    public static int getVersion(){
        return Integer.parseInt(saver.Load(RSType.DatabaseVersion.toString()));
    }
    public static void setName(String DatabaseName){
        try{
            saver.Save(RSType.DatabaseName.toString(), DatabaseName);
        }catch (Exception ignored){}
    }
    public static void setVersion(int DatabaseVersion){
        try{
            saver.Save(RSType.DatabaseVersion.toString(), Integer.toString(DatabaseVersion));
        }catch (Exception ignored){}
    }
    public static void saveTable(String TableName){
        saver.Save(RSType.Tables.toString(), TableName + ",");
    }
    public static void deleteTable(String TableName){
        String Tables = saver.Load(RSType.Tables.toString());
        Tables = Tables.replace(TableName + ",", "");
        saver.Save(RSType.Tables.toString(), Tables);
    }
    public static String getTables(){
        return saver.Load(RSType.Tables.toString());
    }

}
