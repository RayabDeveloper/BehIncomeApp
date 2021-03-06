package com.behincom.behincome.Accesories;

import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.Result.SimpleResponse;

import retrofit2.Response;

public class ResponseHandler {

    public static boolean isSuccess(Response response){
        if(response.isSuccessful()){
            if(response.body() instanceof SimpleResponse){
                SimpleResponse simple = (SimpleResponse)response.body();
                if(simple.Type.equals(ResponseMessageType.Success.toString())){
                    return true;
                }else if(simple.Type.equals(ResponseMessageType.SeeDescription.toString())){
                    return onDescription(response);
                }else if(simple.Type.equals(ResponseMessageType.Error.toString())){
                    return onError(response);
                }else if(simple.Type.equals(ResponseMessageType.Warning.toString())){
                    return onError(response);
                }else
                    return false;
            }else
                return true;
        }else {
            return false;
        }
    }

    public static void showErrorMessage(Object Errors){

    }
    private static boolean onDescription(Response response){
        return true;
    }
    private static boolean onError(Response response){
        return false;
    }

}
