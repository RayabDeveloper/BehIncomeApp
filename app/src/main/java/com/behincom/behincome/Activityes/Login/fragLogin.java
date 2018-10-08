package com.behincom.behincome.Activityes.Login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Device;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.RDate;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Splash.actSplash;
import com.behincom.behincome.Datas.DataRequestVerificationCode;
import com.behincom.behincome.Datas.Keys.APIKeys;
import com.behincom.behincome.Datas.Result.Loginer;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.behincom.behincome.Accesories.ResponseHandler.isSuccess;

public class fragLogin extends Fragment {

    static Context context;
    Dialog pDialog;
    RWInterface rInterface;

    TextInputEditText txtUserName, txtPassWord;
    ImageView imgBack, btnCheck, imgShow;
    TextView lblTitle, btnRegister, btnForgotPassword;

    boolean passShower = false;

    public static fragLogin newInstance(Context mContext){
        fragLogin fragment = new fragLogin();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);

        txtUserName = view.findViewById(R.id.txtUserName);
        txtPassWord = view.findViewById(R.id.txtPassWord);
        imgBack = view.findViewById(R.id.imgBack);
        imgShow = view.findViewById(R.id.imgShow);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblTitle = view.findViewById(R.id.lblTitle);
        btnForgotPassword = view.findViewById(R.id.btnForgotPassword);
        btnRegister = view.findViewById(R.id.btnRegister);

        imgBack.setVisibility(View.GONE);
        lblTitle.setText("ورود");
        txtUserName.setTransformationMethod(null);

        Setting.Save("ReloadAll", "true");

        rInterface = Retrofite.getClient().create(RWInterface.class);
        final Device device = new Device(context);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mPhoneNumber = txtUserName.getText().toString();
                DataRequestVerificationCode data = new DataRequestVerificationCode();
                if (mPhoneNumber.matches("^[0]{1}[9]{1}[0-9]{2}[0-9]{7}")) {
                    pDialog = new Dialog(context);
                    pDialog.Show();

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("PhoneNumber", mPhoneNumber);
                    Call SendRequest = rInterface.RQRequestForgotPasswordToken(map);
                    SendRequest.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(isSuccess(response)){
                                fragResetPass.phone = mPhoneNumber;
                                fragResetPass.TokenHashed = response.body().AdditionalData.get("Md5Token").toString();

                                actLogin.addFragResetPassword();
                            }
                            pDialog.DisMiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                } else {
                    //todo Error Phone Vewrfication
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user = txtUserName.getText().toString().length();
                int pas = txtPassWord.getText().toString().length();
                if(pas > 6 && user >= 11) {
                    pDialog = new Dialog(context);
                    pDialog.Show();

                    Call RQLogin = rInterface.RQLogin(
                            device.IMEI(),
                            device.DeviceName(),
                            Integer.toString(device.OSVersion()),
                            Integer.toString(Setting.getType()),
                            APIKeys.password.toString(),
                            txtUserName.getText().toString(),
                            txtPassWord.getText().toString());
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
                                setting.Save("password", txtPassWord.getText().toString());
                                setting.Save("issued", issued);
                                setting.Save("expires", expires);
                                setting.Save("isLogin", "1");

                                String token = Setting.getToken();
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
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            pDialog.DisMiss();
                        }
                    });
                }
            }
        });

        imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!passShower){
                    passShower = true;
                    imgShow.setImageResource(R.drawable.eye_off);
                    txtPassWord.setTransformationMethod(null);
                }else{
                    passShower = false;
                    imgShow.setImageResource(R.drawable.eye);
                    txtPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actLogin.addFragRequestPhone();
            }
        });

        return view;
    }

}
