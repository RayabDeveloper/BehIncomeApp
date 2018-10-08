package com.behincom.behincome.Accesories;

import android.app.ProgressDialog;
import android.content.Context;

public class Dialog {

    Context context;
    ProgressDialog pDialog;

    public Dialog(Context con){
        this.context = con;
    }

    public void Show(String Title, String Message){
        try {
            pDialog = ProgressDialog.show(context, Title, Message, true);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    public void Show(){
        try {
            pDialog = ProgressDialog.show(context, "ارسال درخواست", "لطفا تا پاسخ سرور کمی صبر کنید...", true);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    public void Show(boolean alakiBede){
        try {
            pDialog = ProgressDialog.show(context, "ارسال درخواست", "لطفا تا پاسخ سرور کمی صبر کنید...", true);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    public void DisMiss(){
        try {
            pDialog.dismiss();
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }

}
