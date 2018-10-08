package com.behincom.behincome.Datas;

import com.behincom.behincome.Accesories.Setting;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUrl {

    public static final String ALAKIPALAKI = Setting.getBMMUserName();

    public Map<String, String> getHeader(){
        return new HashMap<>();
    }

    // Trailing slash is needed
    public static final String BASE_URL = "http://192.168.1.19/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//    private String Application = "BehincomeWeb/api/";
//
//    public String RequestVerificationCode = URLHandler("account", "RequestVerificationCode");
//    public String RequestVerificationCode1 = URLHandler("account2", "RequestVerificationCod3e");
//
//    private String URLHandler(String Key, String Action){
//        return Domain + Application + Key + "/" + Action;
//    }

}
