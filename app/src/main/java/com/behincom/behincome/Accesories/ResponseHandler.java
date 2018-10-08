package com.behincom.behincome.Accesories;

import com.behincom.behincome.Datas.Keys.MessageType;
import com.behincom.behincome.Datas.Result.SimpleResponse;

import retrofit2.Response;

public class ResponseHandler {

    public static boolean isSuccess(Response response){
        if(response.isSuccessful()){
            if(response.body() instanceof SimpleResponse){
                SimpleResponse simple = (SimpleResponse)response.body();
                if(simple.Type.equals(MessageType.Success.toString())){
                    return true;
                }else if(simple.Type.equals(MessageType.SeeDescription.toString())){
                    return onDescription(response);
                }else if(simple.Type.equals(MessageType.Error.toString())){
                    return onError(response);
                }else if(simple.Type.equals(MessageType.Warning.toString())){
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
