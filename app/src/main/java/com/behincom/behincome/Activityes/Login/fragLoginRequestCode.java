package com.behincom.behincome.Activityes.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Device;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.MD5;
import com.behincom.behincome.Accesories.MessageDialogHandler;
import com.behincom.behincome.Accesories.RDate;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Splash.actSplash;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.DataRequestVerificationCode;
import com.behincom.behincome.Datas.Keys.APIKeys;
import com.behincom.behincome.Datas.Result.Loginer;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.Keys.RWAction;
import com.behincom.behincome.WebRequest.Keys.RWController;
import com.behincom.behincome.WebRequest.Keys.RWMethod;
import com.behincom.behincome.WebRequest.Keys.RWType;
import com.behincom.behincome.WebRequest.Object.RWError;
import com.behincom.behincome.WebRequest.Object.RWObject;
import com.behincom.behincome.WebRequest.RQController;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragLoginRequestCode extends Fragment {

    static Context context;
    RWInterface rInterface;
    Dialog pDialog;

    public static fragLoginRequestCode newInstance(Context mContext){
        fragLoginRequestCode fragment = new fragLoginRequestCode();
        context = mContext;
        return fragment;
    }

    TextView lblPhoneNumber, lblChangePhone, lblError, lblTime, lblResendSMS, lblResendCall, lblJustic, lblTitle;
    TextInputEditText txtDigitCode;
    CardView cardView;
    ImageView btnCheck, imgBack;

    CountDownTimer countDownTimer;
    boolean isPaused = false, isStopped = false;
    long stopWatchTimeRemaining = 91000;
    long stopWatchTimeRemainingStatic = 91000;
    long countDownInterval = 1000;
    protected static String PhoneNumber = "", HashedKey = "";
    protected static boolean IsUser = false;
    boolean isTrue = false;
    String mDigitCode = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login_request_code, container, false);

        lblPhoneNumber = view.findViewById(R.id.lblPhoneNumber);
        lblChangePhone = view.findViewById(R.id.lblChangePhone);
        lblError = view.findViewById(R.id.lblError);
        lblTime = view.findViewById(R.id.lblTime);
        lblResendSMS = view.findViewById(R.id.lblResendSMS);
        lblResendCall = view.findViewById(R.id.lblResendCall);
        lblJustic = view.findViewById(R.id.lblJustic);
        txtDigitCode = view.findViewById(R.id.txtDigitCode);
        lblTitle = view.findViewById(R.id.lblTitle);
        cardView = view.findViewById(R.id.cardView);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);

        txtDigitCode.setTransformationMethod(null);

        startStopWatch();
        lblPhoneNumber.setText(PhoneNumber);
        actLogin.sPhone = PhoneNumber;
        imgBack.setVisibility(View.GONE);
        lblTitle.setText("ورود به بهینکام");
        rInterface = Retrofite.getClient().create(RWInterface.class);

        txtDigitCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtDigitCode.getText().toString().length() == 6) {
                    String DigitCode = txtDigitCode.getText().toString();
                    MD5 md5 = new MD5();
                    isTrue = md5.CompareHash(DigitCode, HashedKey);
                    if(!isTrue){
                        //todo set lblError
                    }else{
                        actLogin.sDigitCode = txtDigitCode.getText().toString();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actLogin.STATE = 1;
                actLogin.addFragRequestPhone();
            }
        });
        lblChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actLogin.STATE = 1;
                actLogin.addFragRequestPhone();
            }
        });
        lblJustic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Setting.getTermAandConditions()));
                startActivity(browserIntent);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTrue) {
                    if(IsUser){
                        Device device = new Device(context);
                        pDialog = new Dialog(context);
                        pDialog.Show();

                        Call RQLogin = rInterface.RQLogin(
                                "LoginByPhoneCode",
                                device.IMEI(),
                                device.DeviceName(),
                                Integer.toString(device.OSVersion()),
                                Integer.toString(Setting.getType()),
                                APIKeys.password.toString(),
                                PhoneNumber,
                                txtDigitCode.getText().toString());
                        RQLogin.enqueue(new Callback<Loginer>() {
                            @Override
                            public void onResponse(Call<Loginer> call, Response<Loginer> response) {
                                if (response.isSuccessful()) {
                                    Loginer result = response.body();
                                    RDate date = new RDate(result.issued);
                                    String issued = date.getMiladiDateToString();
                                    date = new RDate(result.expires);
                                    String expires = date.getMiladiDateToString();

                                    Setting setting = new Setting();
                                    setting.Save("access_token", result.access_token);
                                    setting.Save("token_type", result.token_type);
                                    setting.Save("expires_in", Integer.toString(result.expires_in));
                                    setting.Save("userName", result.userName);
                                    setting.Save("password", txtDigitCode.getText().toString());
                                    setting.Save("issued", issued);
                                    setting.Save("expires", expires);
                                    setting.Save("isLogin", "1");

                                    String tokenOnly = result.access_token;
                                    String tokenBearer = Setting.getToken();
                                    Call cGetUserID = rInterface.RQGetUserID("Bearer " + result.access_token);
                                    cGetUserID.enqueue(new Callback<Integer>() {
                                        @Override
                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                            if (response.isSuccessful()) {
                                                Setting.Save("UserID", Integer.toString(response.body()));

                                                if(Setting.getType() == 2) {
                                                    actLogin.STATE = 5;
                                                    actLogin.addFragBMM();
                                                }else{
                                                    Intent intent = new Intent(getActivity(), actSplash.class);
                                                    getActivity().startActivity(intent);
                                                    getActivity().finish();
                                                }
                                            }
                                            pDialog.DisMiss();
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            pDialog.DisMiss();
                                        }
                                    });
                                }else {
                                    pDialog.DisMiss();
                                    MessageDialogHandler Toast = new MessageDialogHandler(context, Basics.ServerError);
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                pDialog.DisMiss();
                                MessageDialogHandler Toast = new MessageDialogHandler(context, t.getMessage());
                            }
                        });
                    }else{
                        actLogin.STATE = 3;
                        actLogin.addFragRegister();
                        fragRegister.PhoneNumber = PhoneNumber;
                        fragRegister.VerficateCode = txtDigitCode.getText().toString();
                    }

                }else{
                    lblError.setText("کد صحیح نیست");
                }
            }
        });
        lblResendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mPhoneNumber = PhoneNumber;
                DataRequestVerificationCode data = new DataRequestVerificationCode();
                if(mPhoneNumber.matches("^[0]{1}[9]{1}[0-9]{2}[0-9]{7}")){
                    data.PhoneNumber = mPhoneNumber;
                }else{
                    lblError.setText("خطا");
                }
                pDialog = new Dialog(getActivity());
                pDialog.Show();

                ObjectMapper oMapper = new ObjectMapper();
                Map map = oMapper.convertValue(data, Map.class);

                Call VerficationCode = rInterface.RQSendPhoneNumber(new HashMap<>(map));
                VerficationCode.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                SimpleResponse result = response.body();

                                for (Map.Entry<String, Object> entry : result.AdditionalData.entrySet()) {
                                    String key = entry.getKey();
                                    String value = entry.getValue().toString();

                                    if (key.contains("VerificationCodeHashMD5")) {
                                        String hashedKey = value;
                                        fragLoginRequestCode.PhoneNumber = mPhoneNumber;
                                        fragLoginRequestCode.HashedKey = hashedKey;

                                        isPaused = false;
                                        isStopped = false;
                                        stopWatchTimeRemaining = 91000;
                                        stopWatchTimeRemainingStatic = 91000;
                                        countDownInterval = 1000;
                                        lblResendSMS.setVisibility(View.GONE);
                                        lblResendCall.setVisibility(View.GONE);
                                        cardView.setVisibility(View.VISIBLE);
                                        startStopWatch();
                                    }
                                }
                            } else {
                                SimpleResponse result = response.body();

                                String Err = "";
                                for (Map.Entry<String, Object> entry : result.AdditionalData.entrySet()) {
                                    Err += entry.getValue().toString() + "<br>";
                                }
                                lblError.setText(Html.fromHtml(Err));
                            }
                        }catch (Exception Ex){
                            String Er = Ex.getMessage();
                        }
                        pDialog.DisMiss();
                    }
                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
                        lblError.setText("مشکل در برقراری ارتباط با سرور");
                        pDialog.DisMiss();
                    }
                });
            }
        });
        lblResendCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mPhoneNumber = PhoneNumber;
                DataRequestVerificationCode data = new DataRequestVerificationCode();
                if(mPhoneNumber.matches("^[0]{1}[9]{1}[0-9]{2}[0-9]{7}")){
                    data.PhoneNumber = mPhoneNumber;
                }else{
                    lblError.setText("خطا");
                }
                pDialog = new Dialog(getActivity());
                pDialog.Show();

                ObjectMapper oMapper = new ObjectMapper();
                Map map = oMapper.convertValue(data, Map.class);

                Call VerficationCode = rInterface.RQSendPhoneNumberCall(new HashMap<>(map));
                VerficationCode.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                SimpleResponse result = response.body();

                                for (Map.Entry<String, Object> entry : result.AdditionalData.entrySet()) {
                                    String key = entry.getKey();
                                    String value = entry.getValue().toString();

                                    if (key.contains("VerificationCodeHashMD5")) {
                                        String hashedKey = value;
                                        fragLoginRequestCode.PhoneNumber = mPhoneNumber;
                                        fragLoginRequestCode.HashedKey = hashedKey;

                                        isPaused = false;
                                        isStopped = false;
                                        stopWatchTimeRemaining = 91000;
                                        stopWatchTimeRemainingStatic = 91000;
                                        countDownInterval = 1000;
                                        lblResendSMS.setVisibility(View.GONE);
                                        lblResendCall.setVisibility(View.GONE);
                                        cardView.setVisibility(View.VISIBLE);
                                        startStopWatch();
                                    }
                                }
                            } else {
                                SimpleResponse result = response.body();

                                String Err = "";
                                for (Map.Entry<String, Object> entry : result.AdditionalData.entrySet()) {
                                    Err += entry.getValue().toString() + "<br>";
                                }
                                lblError.setText(Html.fromHtml(Err));
                            }
                        }catch (Exception Ex){
                            String Er = Ex.getMessage();
                        }
                        pDialog.DisMiss();
                    }
                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
                        lblError.setText("مشکل در برقراری ارتباط با سرور");
                        pDialog.DisMiss();
                    }
                });
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void startStopWatch(){
        ((Activity)context).runOnUiThread(new Runnable() {
            public void run() {
                isPaused=false;
                isStopped=false;
                countDownTimer = new CountDownTimer(stopWatchTimeRemaining,countDownInterval) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(isPaused || isStopped)
                        {
                            //If the user request to cancel or paused the
                            //CountDownTimer we will cancel the current instance
                            cardView.setVisibility(View.GONE);
                            lblResendSMS.setVisibility(View.VISIBLE);
                            lblResendCall.setVisibility(View.VISIBLE);

                            cancel();
                        }
                        else {
                            //Display the remaining seconds to app interface
                            //1 second = 1000 milliseconds
                            //Put count down timer remaining time in a variable
                            long min = 0, sec = 0;
                            min = millisUntilFinished / 60000;
                            sec = (millisUntilFinished % 60000) / 1000;
                            String mMin = Long.toString(min);
                            String mSec = Long.toString(sec);
                            if(mMin.length() == 1)
                                mMin = "0" + mMin;
                            if(mSec.length() == 1)
                                mSec = "0" + mSec;
                            String TimeRemaining = mMin + ":" + mSec;
                            lblTime.setText(TimeRemaining);
                            stopWatchTimeRemaining = millisUntilFinished;
                            if(min == 0 && sec == 2){
                                isStopped = true;
                            }
                        }
                    }
                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        });
    }
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putString("PhoneNumber", PhoneNumber);
//        savedInstanceState.putString("Code", mDigitCode);
//        super.onSaveInstanceState(savedInstanceState);
//    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            PhoneNumber = savedInstanceState.getString(actLogin.sPhone);
            txtDigitCode.setText(actLogin.sDigitCode);
        }catch (Exception ignored){}
    }

}
