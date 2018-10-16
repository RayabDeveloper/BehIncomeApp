package com.behincom.behincome.Activityes.Login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Device;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.MessageDialogHandler;
import com.behincom.behincome.Accesories.RDate;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Splash.actSplash;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.Keys.APIKeys;
import com.behincom.behincome.Datas.Profile.BussinessManagerMarketing;
import com.behincom.behincome.Datas.Result.Loginer;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragRegister extends Fragment {

    static Context context;
    Dialog pDialog;
    RWInterface rInterface;

    public static fragRegister newInstance(Context mContext){
        fragRegister fragment = new fragRegister();
        context = mContext;
        return fragment;
    }

    TextView lblUserName, lblTitle;
    TextInputEditText txtName, txtFamily, txtPassword;
    EditText txtMoaref;
    ImageView imgShow, btnCheck, imgBack;
    RadioGroup radGroup;
    AppCompatRadioButton radMan, radWoman;

    boolean passShower = false;
    protected static String PhoneNumber = "";
    protected static String VerficateCode = "";
    int Sex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_register, container, false);

        radGroup = view.findViewById(R.id.radGroup);
        lblUserName = view.findViewById(R.id.lblUserName);
        txtName = view.findViewById(R.id.txtName);
        txtFamily = view.findViewById(R.id.txtFamily);
        radMan = view.findViewById(R.id.radMan);
        radWoman = view.findViewById(R.id.radWoman);
        txtMoaref = view.findViewById(R.id.txtMoaref);
        txtPassword = view.findViewById(R.id.txtPassword);
        imgShow = view.findViewById(R.id.imgShow);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblTitle = view.findViewById(R.id.lblTitle);

        lblTitle.setText("ثبت نام");

        txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        txtMoaref.setTransformationMethod(null);
        lblUserName.setText(PhoneNumber);

        rInterface = Retrofite.getClient().create(RWInterface.class);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actLogin.STATE = 1;
                actLogin.addFragRequestPhone();
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtName.getText().toString().length() > 0 && txtFamily.getText().toString().length() > 0) {
                    pDialog = new Dialog(context);
                    pDialog.Show();

                    final Device device = new Device(context);
                    Map<String, Object> Register = new HashMap<>();
                    Register.put(APIKeys.Firstname.toString(), txtName.getText().toString());
                    Register.put(APIKeys.Lastname.toString(), txtFamily.getText().toString());
                    Register.put(APIKeys.Username.toString(), PhoneNumber);
                    Register.put(APIKeys.Email.toString(), "");
                    Register.put(APIKeys.Password.toString(), VerficateCode);
                    Register.put(APIKeys.ConfirmPassword.toString(), VerficateCode);
                    Register.put(APIKeys.GenderTypeID.toString(), (radMan.isChecked() ? "1" : "2"));
                    Register.put(APIKeys.RefererID.toString(), txtMoaref.getText().toString());
                    Register.put(APIKeys.ProfileTypeID.toString(), Integer.toString(Setting.getType()));
                    Register.put(APIKeys.DeviceImei.toString(), device.IMEI());
                    Register.put(APIKeys.CreateWithPhoneCode.toString(), true);
                    Register.put(APIKeys.VerificationCode.toString(), VerficateCode);

                    Call RQRegistery = rInterface.RQRegister(new HashMap<>(Register));
                    RQRegistery.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()) {
                                Call RQLogin = rInterface.RQLogin(
                                        "LoginByPhoneCode",
                                        device.IMEI(),
                                        device.DeviceName(),
                                        Integer.toString(device.OSVersion()),
                                        Integer.toString(Setting.getType()),
                                        APIKeys.password.toString(),
                                        PhoneNumber,
                                        VerficateCode);
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
                                            setting.Save("password", VerficateCode);
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
                            }else {
                                Toast.makeText(context, "مشکل در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();
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
                    txtPassword.setTransformationMethod(null);
                }else{
                    passShower = false;
                    imgShow.setImageResource(R.drawable.eye);
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        radMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actLogin.sCodee = 1;
                Sex = 1;
            }
        });
        radWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actLogin.sCodee = 2;
                Sex = 2;
            }
        });

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actLogin.sName = txtName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtFamily.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actLogin.sFamily = txtFamily.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actLogin.sPass = txtPassword.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMoaref.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actLogin.sMoaref = txtMoaref.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putString("PhoneNumber", PhoneNumber);
//        savedInstanceState.putString("Name", txtName.getText().toString());
//        savedInstanceState.putString("Family", txtFamily.getText().toString());
//        savedInstanceState.putString("Password", txtPassword.getText().toString());
//        savedInstanceState.putString("Moaref", txtMoaref.getText().toString());
//        savedInstanceState.putInt("Code", Sex);
//        super.onSaveInstanceState(savedInstanceState);
//    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            PhoneNumber = actLogin.sPhone;
            txtName.setText(actLogin.sName);
            txtFamily.setText(actLogin.sFamily);
            txtPassword.setText(actLogin.sPass);
            txtMoaref.setText(actLogin.sMoaref);
            int mSex = actLogin.sCodee;
            if (mSex == 1) radMan.setChecked(true);
            else if (mSex == 2) radWoman.setChecked(true);
        }catch (Exception ignored){}
    }

}
