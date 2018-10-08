package com.behincom.behincome.Activityes.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Datas.DataRequestVerificationCode;
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
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.behincom.behincome.WebRequest.Keys.RWAction.RequestPhoneVerificationCode;
import static com.behincom.behincome.WebRequest.Keys.RWAction.VerifyPhoneNumber;

public class fragLoginRequestPhone extends Fragment {

    static Context context;
    Dialog pDialog;
    RWInterface rInterface;

    public static fragLoginRequestPhone newInstance(Context mContext) {
        fragLoginRequestPhone fragment = new fragLoginRequestPhone();
        context = mContext;
        return fragment;
    }

    TextInputEditText txtPhoneNumber;

    String PhoneNumber = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login_request_phone, container, false);

        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        ImageView btnCheck = view.findViewById(R.id.btnCheck);
        ImageView imgBack = view.findViewById(R.id.imgBack);
        TextView lblTitle = view.findViewById(R.id.lblTitle);
        final TextView lblError = view.findViewById(R.id.lblError);

        imgBack.setVisibility(View.GONE);
        lblTitle.setText("ورود به بهینکام");
        rInterface = Retrofite.getClient().create(RWInterface.class);

        txtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actLogin.sPhone = txtPhoneNumber.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mPhoneNumber = txtPhoneNumber.getText().toString();
                DataRequestVerificationCode data = new DataRequestVerificationCode();
                if (mPhoneNumber.matches("^[0]{1}[9]{1}[0-9]{2}[0-9]{7}")) {
                    data.PhoneNumber = mPhoneNumber;
                } else {
                    lblError.setText("خطا");
                }
                pDialog = new Dialog(getActivity());
                pDialog.Show();

                ObjectMapper oMapper = new ObjectMapper();
                Map map = oMapper.convertValue(data, Map.class);

//                RWObject RRequest = new RWObject();
//                RRequest.Method = RWMethod.POST;
//                RRequest.Controller = RWController.Account;
//                RRequest.Action = RequestPhoneVerificationCode;
//                RRequest.Type = RWType.Body;
//                RRequest.isToken = false;
//                RRequest.Object = map;
//                final RQController RQ = new RQController<SimpleResponse>(RRequest);
//                RQ.setOnSuccess(new RQController.onSuccess() {
//                    @Override
//                    public void onSuccess(Response Response) {
//                        if (Response.isSuccessful()) {
//                            Response result0 = null;
//                            SimpleResponse result = new SimpleResponse();;
//                            try {
//                                result0 = Response.class.cast(Response);
//
//                                try {
//                                    HashMap<String, Object> mmap = new HashMap<>();
//                                    mmap = RQ.getMapResult(result0.body());
//                                    Class aClass = result.getClass();
//                                    for (Field field : aClass.getFields()) {
//                                        if (mmap.containsKey(field.getName())) {
//                                            field.set(result, mmap.get(field.getName()));
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                try {
//                                    SimpleResponse mobj = SimpleResponse.class.cast(RQ.getMapResult(result0.body()));
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                                for (Map.Entry<String, Object> entry : result.AdditionalData.entrySet()) {
//                                    String key = entry.getKey();
//                                    String value = entry.getValue().toString();
//
//                                    if (key.contains("VerificationCodeHashMD5")) {
//                                        String hashedKey = value;
//                                        fragLoginRequestCode.PhoneNumber = mPhoneNumber;
//                                        fragLoginRequestCode.HashedKey = hashedKey;
//                                        actLogin.STATE = 2;
//                                        actLogin.addFragRequestCode();
//                                    }
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            try {
//                                SimpleResponse result = SimpleResponse.class.cast(Response.body());
//
//                                String Err = "";
//                                for (Map.Entry<String, Object> entry : result.AdditionalData.entrySet()) {
//                                    Err += entry.getValue().toString() + "<br>";
//                                }
//                                lblError.setText(Html.fromHtml(Err));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        pDialog.DisMiss();
//                    }
//                }, new RQController.onFaile() {
//                    @Override
//                    public void onFailed(RWError E) {
//                        String asd = "ASD";
//                        pDialog.DisMiss();
//                    }
//                });

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
                                        actLogin.STATE = 2;
                                        actLogin.addFragRequestCode();
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
                        } catch (Exception Ex) {
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


        /*String token = Setting.getToken();
        Call cMarketingData = rInterface.RQGetMarketingAllData("Bearer " + token);
        cMarketingData.enqueue(new Callback<MarketingDatas>() {
            @Override
            public void onResponse(Call<MarketingDatas> call, Response<MarketingDatas> response) {
                if(response.isSuccessful()){
                    MarketingDatas datas = response.body();
                    String gg = "ASD";
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                String asdf = "ASD";
            }
        });*/

        /*Device device = new Device(context);
        Call RQLogin = rInterface.RQLogin(
                device.IMEI(),
                "AndroidEmulator",//todo
                "Android API 23",//todo
                "2",//todo
                APIKeys.password.toString(),
                "09010278030",
                "Rayab6941");
        RQLogin.enqueue(new Callback<Loginer>() {
            @Override
            public void onResponse(Call<Loginer> call, Response<Loginer> response) {
                if(response.isSuccessful()) {
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
                    setting.Save("password", "Rayab6941");
                    setting.Save("issued", issued);
                    setting.Save("expires", expires);

                    Intent intent = new Intent(context, actSplash.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String asdweq = "ASD";
            }
        });*/

        return view;
    }

    //    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putString("PhoneNumber", PhoneNumber);
//        super.onSaveInstanceState(savedInstanceState);
//    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            txtPhoneNumber.setText(actLogin.sPhone);
        } catch (Exception ignored) {
        }
    }

}
