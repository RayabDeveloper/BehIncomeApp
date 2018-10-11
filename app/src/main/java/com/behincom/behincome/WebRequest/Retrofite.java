package com.behincom.behincome.WebRequest;

import com.behincom.behincome.Accesories.Setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofite {

//    public static final String BASE = "http://192.168.1.17/BehincomeWeb/";//LocalHost
//    public static final String BASE = "http://94.183.117.65:81/BehincomeWeb/";//LocalHost
    public static final String BASE = Setting.getServerURL();//LocalHost

//    public static final String BASE_URL = BASE + "api/";//LocalHost
    public static final String BASE_URL = BASE + "api/";//LocalHost
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .serializeNulls()
                            .create()))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }

}
