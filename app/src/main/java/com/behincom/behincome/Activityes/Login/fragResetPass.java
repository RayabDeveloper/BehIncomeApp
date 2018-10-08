package com.behincom.behincome.Activityes.Login;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Device;
import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.MD5;
import com.behincom.behincome.Accesories.RDate;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.Keys.APIKeys;
import com.behincom.behincome.Datas.Result.Loginer;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.behincom.behincome.Accesories.ResponseHandler.isSuccess;
import static com.behincom.behincome.Accesories.ResponseHandler.showErrorMessage;

public class fragResetPass extends Fragment {

    static Context context;
    Dialog pDialog;
    RWInterface rInterface;

    public static fragResetPass newInstance(Context mContext) {
        fragResetPass fragment = new fragResetPass();
        context = mContext;
        return fragment;
    }

    LinearLayout linDigitCode, linPassword;
    ImageView imgBack, btnCheck, imgShow;
    TextView lblTitle, lblTitler;
    TextInputLayout lblpass;
    TextInputEditText txtPassWord;
    EditText txt1, txt2, txt3, txt4, txt5, txt6;
    CardView cardCode;

    protected static String phone = "";
    protected static String TokenHashed = "";
    boolean passShower = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_reset_pass, container, false);

        linPassword = view.findViewById(R.id.linPassword);
        linDigitCode = view.findViewById(R.id.linDigitCode);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        lblTitler = view.findViewById(R.id.lblTitler);
        lblTitle = view.findViewById(R.id.lblTitle);
        cardCode = view.findViewById(R.id.cardCode);
        txt1 = view.findViewById(R.id.txt1);
        txt2 = view.findViewById(R.id.txt2);
        txt3 = view.findViewById(R.id.txt3);
        txt4 = view.findViewById(R.id.txt4);
        txt5 = view.findViewById(R.id.txt5);
        txt6 = view.findViewById(R.id.txt6);

        txt1.setTransformationMethod(null);
        txt2.setTransformationMethod(null);
        txt3.setTransformationMethod(null);
        txt4.setTransformationMethod(null);
        txt5.setTransformationMethod(null);
        txt6.setTransformationMethod(null);

        lblTitle.setText("فراموشی کلمه عبور");
        imgBack.setVisibility(View.GONE);

        txt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt1.getText().toString().length() == 1) {
                    txt2.requestFocus();
                    txt2.setSelection(0);

                    CheckAndRequest();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt2.getText().toString().length() == 1) {
                    txt3.requestFocus();
                    txt3.setSelection(0);

                    CheckAndRequest();
                } else if (txt2.getText().toString().length() == 0) {
                    txt1.requestFocus();
                    txt1.setSelection(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt3.getText().toString().length() == 1) {
                    txt4.requestFocus();
                    txt4.setSelection(0);

                    CheckAndRequest();
                } else if (txt3.getText().toString().length() == 0) {
                    txt2.requestFocus();
                    txt2.setSelection(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt4.getText().toString().length() == 1) {
                    txt5.requestFocus();
                    txt5.setSelection(0);

                    CheckAndRequest();
                } else if (txt4.getText().toString().length() == 0) {
                    txt3.requestFocus();
                    txt3.setSelection(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt5.getText().toString().length() == 1) {
                    txt6.requestFocus();
                    txt6.setSelection(0);

                    CheckAndRequest();
                } else if (txt5.getText().toString().length() == 0) {
                    txt4.requestFocus();
                    txt4.setSelection(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt6.getText().toString().length() == 1) {
                    CheckAndRequest();
                } else if (txt6.getText().toString().length() == 0) {
                    txt5.requestFocus();
                    txt5.setSelection(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (txt6.getText().toString().length() == 1) {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        txt5.requestFocus();
                    }
                }
                return false;
            }
        });
        txt5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt4.requestFocus();
                }
                return false;
            }
        });
        txt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt3.requestFocus();
                }
                return false;
            }
        });
        txt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt2.requestFocus();
                }
                return false;
            }
        });
        txt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt1.requestFocus();
                }
                return false;
            }
        });

        imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!passShower) {
                    passShower = true;
                    imgShow.setImageResource(R.drawable.eye_off);
                    txtPassWord.setTransformationMethod(null);
                } else {
                    passShower = false;
                    imgShow.setImageResource(R.drawable.eye);
                    txtPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAndRequest();
            }
        });

        return view;
    }

    private void CheckAndRequest() {
        MD5 md5 = new MD5();
        boolean isTrue = md5.CompareHash("6 Digit Code", "Hashed Key");
        //todo Code To Check Hash is Here
        if (isTrue) {
            lblTitler.setText("کلمه عبور جدید را وارد کنید");
            linDigitCode.setVisibility(View.GONE);
            linPassword.setVisibility(View.VISIBLE);

            final String Pass = txtPassWord.getText().toString();

            pDialog = new Dialog(context);
            pDialog.Show();

            HashMap<String, Object> map = new HashMap<>();
            map.put("PhoneNumber", phone);
            map.put("Password", Pass);
            map.put("ConfirmPassword", Pass);
            map.put("Token", TokenHashed);
            Call ResetPass = rInterface.RQForgotPassword(new HashMap<>(map));
            ResetPass.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (isSuccess(response)) {
                        Device device = new Device(context);
                        Call RQLogin = rInterface.RQLogin(
                                device.IMEI(),
                                device.DeviceName(),
                                Integer.toString(device.OSVersion()),
                                Integer.toString(Setting.getType()),
                                APIKeys.password.toString(),
                                phone,
                                Pass);
                        RQLogin.enqueue(new Callback<Loginer>() {
                            @Override
                            public void onResponse(Call<Loginer> call, Response<Loginer> response) {
                                if (response.isSuccessful()) {
                                    if(response.body() instanceof Loginer) {
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
                                        setting.Save("password", Pass);
                                        setting.Save("issued", issued);
                                        setting.Save("expires", expires);
                                        setting.Save("isLogin", "1");

                                        Call cGetUserID = rInterface.RQGetUserID(Setting.getToken());
                                        cGetUserID.enqueue(new Callback<Integer>() {
                                            @Override
                                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                if (response.isSuccessful()) {
                                                    Setting.Save("UserID", Integer.toString(response.body()));

                                                    if (Setting.getType() == 2) {
                                                        actLogin.STATE = 5;
                                                        actLogin.addFragBMM();
                                                        pDialog.DisMiss();
                                                    } else {
                                                        actLogin.STATE = 7;
                                                        actLogin.addFragRegisterAdmin();
                                                        pDialog.DisMiss();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call call, Throwable t) {
                                                pDialog.DisMiss();
                                            }
                                        });
                                    }else{
                                        //todo Error
                                    }
                                } else {
                                    pDialog.DisMiss();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                pDialog.DisMiss();
                            }
                        });
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    pDialog.DisMiss();
                }
            });
        }
    }

}
