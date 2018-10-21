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
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.Keys.FragmentState;
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
    TextInputEditText txtAddInvoice,
            txtAddCustomer,
            txtActivityNoDoneNegative,
            txtCustomerH,
            txtCustomerM,
            txtActivityH,
            txtActivityM,
            txtInvoiceH,
            txtInvoiceM;
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

        txtActivityNoDoneNegative = view.findViewById(R.id.txtActivityNoDoneNegative);
        txtInvoiceH = view.findViewById(R.id.txtInvoiceH);
        txtInvoiceM = view.findViewById(R.id.txtInvoiceM);
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

        lblTitle.setText("تنظیمات بازاریاب");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actSetting act = new actSetting();
                act.getFragByState(FragmentState.Setting);
            }
        });

        try {
            lSetup = geter.getList(MarketingSetups.class);
            txtAddCustomer.setText(Integer.toString(lSetup.get(0).PointCustomerAdd));
            txtAddInvoice.setText(Integer.toString(lSetup.get(0).PointInvoiseAdd));
            txtActivityNoDoneNegative.setText(Integer.toString(lSetup.get(0).ActivityNotDoneNegativePoint));
            String cTime = lSetup.get(0).CustmerEditTime;
            String aTime = lSetup.get(0).ActivityEditTime;
            String abTime = lSetup.get(0).InvoiceEditTime;
            String[] ccTime = cTime.split(":");
            txtCustomerH.setText(ccTime[0]);
            txtCustomerM.setText(ccTime[1]);
            String[] aaTime = aTime.split(":");
            txtActivityH.setText(aaTime[0]);
            txtActivityM.setText(aaTime[1]);
            String[] aabTime = abTime.split(":");
            txtInvoiceH.setText(aabTime[0]);
            txtInvoiceM.setText(aabTime[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                String hC = txtCustomerH.getText().toString();
                String mC = txtCustomerM.getText().toString();
                String hA = txtActivityH.getText().toString();
                String mA = txtActivityM.getText().toString();
                String hI = txtInvoiceH.getText().toString();
                String mI = txtInvoiceM.getText().toString();

                if(hC.length() == 1)
                    hC = "0" + hC;
                if(mC.length() == 1)
                    mC = "0" + mC;
                if(hA.length() == 1)
                    hA = "0" + hA;
                if(mA.length() == 1)
                    mA = "0" + mA;
                if(hI.length() == 1)
                    hI = "0" + hI;
                if(mI.length() == 1)
                    mI = "0" + mI;

                final int PointCustomer = Integer.parseInt(txtAddCustomer.getText().toString());
                final int PointInvoice = Integer.parseInt(txtAddInvoice.getText().toString());
                final int ActivityNoDoneNegativeInvoice = Integer.parseInt(txtActivityNoDoneNegative.getText().toString());
                final String CustomerTime = hC + ":" + mC + ":00";
                final String ActivityTime = hA + ":" + mA + ":00";
                final String InvoiceTime = hI + ":" + mI + ":00";

                HashMap<String, Object> map = new HashMap<>();
                map.put("MarketingSetupId", lSetup.get(0).MarketingSetupId);
                map.put("PointCustomerAdd", PointCustomer);
                map.put("PointInvoiseAdd", PointInvoice);
                map.put("ActivityNotDoneNegativePoint", ActivityNoDoneNegativeInvoice);
                map.put("CustmerEditTime", CustomerTime);
                map.put("ActivityEditTime", ActivityTime);
                map.put("InvoiceEditTime", InvoiceTime);

                Call cAddResults = rInterface.ControllerActionManager_Marketing_58(Setting.getToken(), map);
                cAddResults.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse simple = response.body();
                            if (simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())) {
                                SQL.Execute("UPDATE " + Tables.MarketingSetups +
                                        " SET PointCustomerAdd='" + PointCustomer +
                                        "', PointInvoiseAdd='" + PointInvoice +
                                        "', CustmerEditTime='" + CustomerTime +
                                        "', InvoiceEditTime='" + InvoiceTime +
                                        "', ActivityNotDoneNegativePoint='" + ActivityNoDoneNegativeInvoice +
                                        "', ActivityEditTime='" + ActivityTime +
                                        "'");
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
