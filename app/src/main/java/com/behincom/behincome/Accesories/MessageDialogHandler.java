package com.behincom.behincome.Accesories;

import android.content.Context;
import android.widget.Toast;

import com.behincom.behincome.Datas.Keys.MessageType;

public class MessageDialogHandler {

    private static Context context;
    private static String Title;
    private static String Message;
    private static MessageType Type;

    public MessageDialogHandler(Context mContext, String mMessage){
        context = mContext;
        Message = mMessage;

        Show();
    }
    public MessageDialogHandler(Context mContext, String mTitle, String mMessage, MessageType mType){
        context = mContext;
        Title = mTitle;
        Message = mMessage;
        Type = mType;

        Show();
    }

    private void Show(){
        Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
    }

}
