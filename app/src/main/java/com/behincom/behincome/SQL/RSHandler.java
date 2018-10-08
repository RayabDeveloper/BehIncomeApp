package com.behincom.behincome.SQL;

public class RSHandler {

    protected String getQuery(String Type, Class Table){
        if(Type.equals("1")){
            return "SELECT * FROM " + Table.getSimpleName();
        }else{
            return "SELECT " + Type + " FROM " + Table.getSimpleName();
        }
    }

}
