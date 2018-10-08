package com.behincom.behincome.WebRequest;

import android.text.Html;
import android.view.View;

import com.behincom.behincome.Activityes.Login.fragLoginRequestCode;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.WebRequest.Keys.RWType;
import com.behincom.behincome.WebRequest.Object.RWError;
import com.behincom.behincome.WebRequest.Object.RWObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RQController<T> {

    private onSuccess<T> onSuccessListener;
    private onFaile onFaileListener;
    public void setOnSuccess(onSuccess onSuccess, onFaile onFaile){
        this.onSuccessListener = onSuccess;
        this.onFaileListener = onFaile;
    }
    private void Successed(Response<T> response){
        onSuccessListener.onSuccess(response);
    }
    private void Failed(RWError E){
        onFaileListener.onFailed(E);
    }
    public interface onSuccess<T> {
        void onSuccess(Response<T> Response);
    }
    public interface onFaile{
        void onFailed(RWError E);
    }

    private RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
    private RWObject mObject;
    private Class mClass = null;

    public RQController(RWObject object){
        mObject = object;
        switch (mObject.Method){
            case GET:
                GET();
                break;
            case POST:
                if(mObject.Type == RWType.Body)
                    POST();
                else if(mObject.Type == RWType.MultiPart)
                    MULTIPART();
                break;
            case Null:

                break;
        }
    }
    private void GET(){
        if(mObject.isToken)
            GetToken();
        else
            GetNull();
    }
    private void POST(){
        if(mObject.Object != null) {
            if (mObject.Object instanceof Collection<?>) {
                if(mObject.isToken)
                    PostTokenList();
                else
                    PostList();
            }else{
                if(mObject.isToken)
                    PostTokenParameter();
                else{
                    PostParameter();
                }
            }
        }else
            PostToken();
    }
    private void MULTIPART(){
        if(mObject.MultiPartBody != null){
            if(mObject.MultiPartBody.getClass().isArray())
                MultiPartArray();
            else
                MultiPart();
        }//todo nabayad null bashe aslan
    }
    private void GetNull(){
        Call<T> Geter = rInterface.GET(mObject.Controller.toString(), mObject.Action.toString());
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void GetToken(){
        Call<T> Geter = rInterface.GET(mObject.Controller.toString(), mObject.Action.toString(), mObject.Token);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void PostToken(){
        Call<T> Geter = rInterface.POST(mObject.Controller.toString(), mObject.Action.toString(), mObject.Token);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void PostTokenParameter(){
        ObjectMapper oMapper = new ObjectMapper();
        HashMap<String, Object> map = oMapper.convertValue(mObject.Object, HashMap.class);
        Call<T> Geter = rInterface.POST(mObject.Controller.toString(), mObject.Action.toString(), mObject.Token, map);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void PostParameter(){
        ObjectMapper oMapper = new ObjectMapper();
        HashMap<String, Object> map = oMapper.convertValue(mObject.Object, HashMap.class);
        Call<T> Geter = rInterface.POST(mObject.Controller.toString(), mObject.Action.toString(), map);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void PostTokenList(){
        ObjectMapper oMapper = new ObjectMapper();
        List<Map> maps = new ArrayList<>();
        List<Object> nClass = RWCast.cast(mObject.Object);
        for (Object data : nClass) {
            Map map = oMapper.convertValue(data, Map.class);
            maps.add(map);
        }
        Call<T> Geter = rInterface.POST(mObject.Controller.toString(), mObject.Action.toString(), mObject.Token, maps);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void PostList(){
        ObjectMapper oMapper = new ObjectMapper();
        List<Map> maps = new ArrayList<>();
        List<Object> nClass = RWCast.cast(mObject.Object);
        for (Object data : nClass) {
            Map map = oMapper.convertValue(data, Map.class);
            maps.add(map);
        }
        Call<T> Geter = rInterface.POST(mObject.Controller.toString(), mObject.Action.toString(), maps);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void MultiPart(){
        Call<T> Geter = rInterface.POSTFile(mObject.Controller.toString(), mObject.Action.toString(), mObject.Token, (MultipartBody.Part)mObject.MultiPartBody);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }
    private void MultiPartArray(){
        Call<T> Geter = rInterface.POSTFile(mObject.Controller.toString(), mObject.Action.toString(), mObject.Token, (MultipartBody.Part[])mObject.MultiPartBody);
        Geter.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed(t);
            }
        });
    }

    public HashMap<String, Object> getMapResult(Object obj){
        LinkedTreeMap<Object, Object> map = (LinkedTreeMap) obj;
        HashMap<String, Object> mMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            mMap.put(key, value);
        }
        return mMap;
    }
    public Object getClassObject(HashMap<String, Object> map, Class mClass) {
        for (Field field : mClass.getFields()) {
            if (map.containsKey(field.getName())) {
                try {
                    field.set(mClass, map.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return mClass;
    }

    private void onSuccess(Response<T> response){
        if(response.isSuccessful())
            Successed(response);
        else{
            SimpleResponse result = SimpleResponse.class.cast(response);

            String Err = "";
            for (Map.Entry<String, Object> entry : result.AdditionalData.entrySet()) {
                Err += entry.getValue().toString() + "<br>";
            }
            //todo , Error Dialog, On Application
        }
    }
    private void onFailed(Throwable t){
        Failed(new RWError());
    }

}
