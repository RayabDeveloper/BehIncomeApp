package com.behincom.behincome.Activityes.Setting.MarketingSetting;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.Marketing.ToSend.MarketingSetups;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragMarketingSetting extends Fragment {

    static Context context;
    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
    private Dialog pDialog;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    CardView cardView;
    TextView lblTitle;
    TextInputEditText txtAddInvoice, txtAddCustomer, txtCustomerH, txtCustomerM, txtActivityH, txtActivityM;
    ImageView btnCheck, imgBack;
    private DatePickerDialog DateDialog;


    List<MarketingSetups> lSetup = new ArrayList<>();
    private boolean isFromDate = false;
    private boolean isToDate = false;
    private int DateType = 0;
    private String cFromDate = "";
    private String cToDate = "";

    public static fragMarketingSetting newInstance(Context mContext) {
        fragMarketingSetting fragment = new fragMarketingSetting();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_marketing_setting, container, false);

        cardView = view.findViewById(R.id.cardView);
        txtCustomerH = view.findViewById(R.id.txtCustomerH);
        txtCustomerM = view.findViewById(R.id.txtCustomerM);
        txtActivityH = view.findViewById(R.id.txtActivityH);
        txtActivityM = view.findViewById(R.id.txtActivityM);
        lblTitle = view.findViewById(R.id.lblTitle);
        txtAddInvoice = view.findViewById(R.id.txtAddInvoice);
        txtAddCustomer = view.findViewById(R.id.txtAddCustomer);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("تنظیمات بازاریاب");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        try {
            lSetup = geter.getList(MarketingSetups.class);
            txtAddCustomer.setText(lSetup.get(0).PointCustomerAdd);
            txtAddInvoice.setText(lSetup.get(0).PointInvoiseAdd);
            String cTime = lSetup.get(0).CustmerEditTime;
            String aTime = lSetup.get(0).ActivityEditTime;
            String[] ccTime = cTime.split(":");
            txtCustomerH.setText(ccTime[0]);
            txtCustomerM.setText(ccTime[1]);
            String[] aaTime = aTime.split(":");
            txtActivityH.setText(aaTime[0]);
            txtActivityM.setText(aaTime[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                final int PointCustomer = Integer.parseInt(txtAddCustomer.getText().toString());
                final int PointInvoice = Integer.parseInt(txtAddInvoice.getText().toString());
                final String CustomerTime = txtCustomerH.getText().toString() + ":" + txtCustomerM.getText().toString() + ":00";
                final String ActivityTime = txtActivityH.getText().toString() + ":" + txtActivityM.getText().toString() + ":00";

                HashMap<String, Object> map = new HashMap<>();
                map.put("MarketingSetupId", lSetup.get(0).MarketingSetupId);
                map.put("PointCustomerAdd", PointCustomer);
                map.put("PointInvoiseAdd", PointInvoice);
                map.put("CustmerEditTime", CustomerTime);
                map.put("ActivityEditTime", ActivityTime);

                Call cAddResults = rInterface.RQAddPointMarketingActivityResults(Setting.getToken(), map);
                cAddResults.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if (simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                SQL.Execute("UPDATE " + Tables.MarketingSetups + " SET PointCustomerAdd='" + PointCustomer + "', PointInvoiseAdd='" + PointInvoice + "', CustmerEditTime='" + CustomerTime + "', ActivityEditTime='" + ActivityTime + "'");
                                Toast.makeText(context, Basics.Submited, Toast.LENGTH_LONG).show();
                            } else if (simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())) {
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        pDialog.DisMiss();
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }

}
