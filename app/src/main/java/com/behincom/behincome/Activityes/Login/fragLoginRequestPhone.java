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
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.DataRequestVerificationCode;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
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

        Setting.Save("ReloadAll", "true");

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
                String PhoneNumber = "";
                DataRequestVerificationCode data = new DataRequestVerificationCode();
                if (mPhoneNumber.matches("^[0]{1}[9]{1}[0-9]{2}[0-9]{7}")) {
                    PhoneNumber = mPhoneNumber;
                } else {
                    lblError.setText("خطا");
                }
                pDialog = new Dialog(getActivity());
                pDialog.Show();

//                ObjectMapper oMapper = new ObjectMapper();
//                Map map = oMapper.convertValue(data, Map.class);

                HashMap<String, Object> map = new HashMap<>();
                map.put("PhoneNumber", PhoneNumber);

                Call VerficationCode = rInterface.RQSendPhoneNumber(new HashMap<>(map));
                final String finalPhoneNumber = PhoneNumber;
                VerficationCode.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                SimpleResponse result = response.body();
                                if(result.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                    String Err = "";
                                    for (Map.Entry<String, Object> entry : result.Errors.entrySet()) {
                                        String value = entry.getValue().toString();
                                        Err += value + "<br>";
                                    }
                                    lblError.setText(Html.fromHtml(Err));
                                }else if(result.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                    fragLoginRequestCode.IsUser = result.AdditionalData.get("IsUser").toString().equalsIgnoreCase("false") ? false : true;
                                    fragLoginRequestCode.PhoneNumber = finalPhoneNumber;
                                    fragLoginRequestCode.HashedKey = result.AdditionalData.get("VerificationCode").toString();
                                    actLogin.STATE = 2;
                                    actLogin.addFragRequestCode();
                                }
                            } else {
                                lblError.setText(Basics.ServerError);
                            }
                        } catch (Exception Ex) {
                            String Er = Ex.getMessage();
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
                        lblError.setText(Basics.ServerError);
                        pDialog.DisMiss();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            txtPhoneNumber.setText(actLogin.sPhone);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            txtPhoneNumber.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
