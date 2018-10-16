package com.behincom.behincome.Activityes.Setting.Pdoructs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Setting.adapCommission;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.BaseData.Basic_CommissionTypes;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommission;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommissions;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragAddProducts extends Fragment {

    static Context context;
    static Context contexti;
    RWInterface rInterface;
    actSetting act = new actSetting();
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    SpinAdapter adapCommissionType;
    adapCommission adapter;
    static adapCommission adapteri;
    Dialog pDialog;

    TextView lblTitle;
    TextView lblCommissionType;
    TextView lblName;
    TextView lblDescription;
    TextView lblAccept;
    EditText txtDescription;
    EditText txtName;
    ImageView imgBack, btnCheck, btnDelete;
    Spinner spinCommissionType;
    RecyclerView lstMain;
    static RecyclerView lstMaini;
    LinearLayout linCommission;
    FloatingActionButton btnAddProduct;
    RecyclerView.LayoutManager mLayoutManager;
    static RecyclerView.LayoutManager mLayoutManageri;
    CardView cardView;

    public static MarketingProducts mData = new MarketingProducts();
    public static List<MarketingProductCommissions> lCommission = new ArrayList<>();
    public static boolean toEdit = false;

    boolean ApiFrist = false;

    public static fragAddProducts newInstance(Context mContext){
        fragAddProducts fragment = new fragAddProducts();
        context = mContext;
        contexti = mContext;
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        txtName.setText("");
        txtDescription.setText("");
        lCommission = new ArrayList<>();
        spinCommissionType.setSelection(0);
        toEdit = false;
        mData = new MarketingProducts();
        lCommission = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_products, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        linCommission = view.findViewById(R.id.linCommission);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        lblCommissionType = view.findViewById(R.id.lblCommissionType);
        lblName = view.findViewById(R.id.lblName);
        lblDescription = view.findViewById(R.id.lblDescription);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtName = view.findViewById(R.id.txtName);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        spinCommissionType = view.findViewById(R.id.spinCommissionType);
        lstMain = view.findViewById(R.id.lstMain);
        lstMaini = view.findViewById(R.id.lstMain);
        btnDelete = view.findViewById(R.id.btnDelete);
        cardView = view.findViewById(R.id.cardView);

        lblTitle.setText("اضافه کردن محصول");
        imgBack.setVisibility(View.VISIBLE);

        List<Basic_CommissionTypes> lBaseData = geter.getList(Basic_CommissionTypes.class);

        adapCommissionType = new SpinAdapter(context, lBaseData, "CommissionTypeTitle");
        spinCommissionType.setAdapter(adapCommissionType);

        if(toEdit){
            btnDelete.setVisibility(View.VISIBLE);
            txtName.setText(mData.MarketingProductTitle);
            txtDescription.setText(mData.MarketingProductDescription);
            spinCommissionType.setSelection(adapCommissionType.getItemPosition("CommissionTypeID", Integer.toString(mData.CommissionTypeID)));
        }else{
            btnDelete.setVisibility(View.GONE);

            txtName.setText("");
            txtDescription.setText("");
            spinCommissionType.setSelection(0);
            lCommission = new ArrayList<>();
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Products);
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCommission();
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtName.getText().toString().length() > 0) {
                    if(lCommission.size() > 0) {
                        if (lCommission.get(lCommission.size() - 1).CommissionPriceTo > 0 && lCommission.get(lCommission.size() - 1).CommissionPercent > 0) {
                            if(toEdit){
                                rInterface = Retrofite.getClient().create(RWInterface.class);
                                pDialog = new Dialog(context);
                                pDialog.Show();

                                final List<MarketingProductCommission> lCommissionToSend = new ArrayList<>();
                                for (MarketingProductCommissions data : lCommission) {
                                    MarketingProductCommission mData = new MarketingProductCommission();
                                    mData.CommissionPercent = data.CommissionPercent;
                                    mData.CommissionPriceFrom = data.CommissionPriceFrom;
                                    mData.CommissionPriceTo = data.CommissionPriceTo;

                                    lCommissionToSend.add(mData);
                                }

                                Map<String, Object> mProduct = new HashMap<>();
                                mProduct.put("MarketingProductID", mData.MarketingProductID);
                                mProduct.put("CommissionTypeID", Integer.parseInt(adapCommissionType.getItemString(spinCommissionType.getSelectedItemPosition(), "CommissionTypeID")));
                                mProduct.put("MarketingProductTitle", txtName.getText().toString());
                                mProduct.put("MarketingProductDescription", txtDescription.getText().toString());
                                mProduct.put("Commissions", lCommissionToSend);

                                Call<SimpleResponse> cProduct = rInterface.RQEditMarketingProducts(Setting.getToken(), new HashMap<>(mProduct));
                                cProduct.enqueue(new Callback<SimpleResponse>() {
                                    @Override
                                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                        pDialog.DisMiss();
                                        if(response.isSuccessful()){
                                            MarketingProducts dataProduct = new MarketingProducts();
                                            dataProduct.CommissionTypeID = Integer.parseInt(adapCommissionType.getItemString(spinCommissionType.getSelectedItemPosition(), "CommissionTypeID"));
                                            dataProduct.MarketingProductDescription = txtDescription.getText().toString();
                                            dataProduct.MarketingProductTitle = txtName.getText().toString();
                                            dataProduct.MarketingProductID = mData.MarketingProductID;

                                            SQL.Execute("DELETE FROM MarketingProducts WHERE MarketingProductID='" + mData.MarketingProductID + "'");
                                            SQL.Execute("DELETE FROM MarketingProductCommissions WHERE MarketingProductID='" + mData.MarketingProductID + "'");
                                            SQL.Insert(dataProduct);

                                            for (MarketingProductCommissions data : lCommission) {
                                                SQL.Insert(data);
                                            }

                                            act.getFragByState(FragmentState.Products);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                        pDialog.DisMiss();
                                    }
                                });
                            }else {
                                Submit();
                            }
                        } else {
                            mToast("لطفا مقداری برای کمیسیون و درصد کمیسیون مشخص کنید");
                        }
                    }else{
                        if(toEdit){

                        }else {
                            Submit();
                        }
                    }
                }else{
                    mToast("لطفا نام محصول را وارد کنید.");
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo NotHaveDelete
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ApiFrist = false;
        if(!toEdit)
            lCommission = new ArrayList<>();
        RefreshList();
    }
    private void RefreshList(){
        adapter = new adapCommission(lCommission);
        adapteri = new adapCommission(lCommission);
        lstMain.setHasFixedSize(true);
        lstMaini.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManageri = new LinearLayoutManager(contexti);
        lstMain.setLayoutManager(mLayoutManager);
        lstMaini.setLayoutManager(mLayoutManageri);
        lstMain.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.txtWhite).size(2).build());
        lstMaini.addItemDecoration(new HorizontalDividerItemDecoration.Builder(contexti).colorResId(R.color.txtWhite).size(2).build());
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMaini.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
        lstMaini.setAdapter(adapteri);
        AddNewCommission();
    }
    private void Submit(){
        rInterface = Retrofite.getClient().create(RWInterface.class);
        pDialog = new Dialog(context);
        pDialog.Show();

        final List<MarketingProductCommission> lCommissionToSend = new ArrayList<>();
        for (MarketingProductCommissions data : lCommission) {
            MarketingProductCommission mData = new MarketingProductCommission();
            mData.CommissionPercent = data.CommissionPercent;
            mData.CommissionPriceFrom = data.CommissionPriceFrom;
            mData.CommissionPriceTo = data.CommissionPriceTo;

            lCommissionToSend.add(mData);
        }

        Map<String, Object> mProduct = new HashMap<>();
        mProduct.put("CommissionTypeID", Integer.parseInt(adapCommissionType.getItemString(spinCommissionType.getSelectedItemPosition(), "CommissionTypeID")));
        mProduct.put("MarketingProductTitle", txtName.getText().toString());
        mProduct.put("MarketingProductDescription", txtDescription.getText().toString());
        mProduct.put("Commissions", lCommissionToSend);

        Call<SimpleResponse> cProduct = rInterface.RQAddMarketingProducts(Setting.getToken(), new HashMap<>(mProduct));
        cProduct.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if(response.isSuccessful()){
                    try {
                        MarketingProducts dataProduct = new MarketingProducts();
                        dataProduct.CommissionTypeID = Integer.parseInt(adapCommissionType.getItemString(spinCommissionType.getSelectedItemPosition(), "CommissionTypeID"));
                        dataProduct.MarketingProductDescription = txtDescription.getText().toString();
                        dataProduct.MarketingProductTitle = txtName.getText().toString();
                        dataProduct.MarketingProductID = Integer.parseInt(response.body().AdditionalData.get("MarketingProductID").toString().replace(".0", ""));

                        SQL.Insert(dataProduct);

                        for (MarketingProductCommissions data : lCommission) {
                            SQL.Insert(data);
                        }
                        act.getFragByState(FragmentState.Products);
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                }
                pDialog.DisMiss();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                String Er = t.getMessage();
                pDialog.DisMiss();
            }
        });
    }
    public static void RefreshAdapter(){
        adapteri = new adapCommission(lCommission);
        adapteri.notifyDataSetChanged();
        lstMaini.setAdapter(adapteri);
    }
    private void AddNewCommission(){
        if (lCommission.size() > 0) {
            if(lCommission.size() > 1) {
                if (lCommission.get(lCommission.size() - 1).CommissionPercent > 0) {
                    if (lCommission.get(lCommission.size() - 1).CommissionPriceTo <= lCommission.get(lCommission.size() - 1).CommissionPriceFrom) {
                        Toast.makeText(context, "مبلغ نباید کمتر از شروع باشد.", Toast.LENGTH_LONG).show();
                    } else {
                        MarketingProductCommissions data = new MarketingProductCommissions();
                        lCommission.add(data);
                    }
                } else {
                    Toast.makeText(context, "درصد نباید 0 باشد", Toast.LENGTH_LONG).show();
                }
            }else if(lCommission.size() == 1){
                if (lCommission.get(lCommission.size() - 1).CommissionPriceTo <= lCommission.get(lCommission.size() - 1).CommissionPriceFrom) {
                    Toast.makeText(context, "مبلغ نباید کمتر از شروع باشد.", Toast.LENGTH_LONG).show();
                } else {
                    MarketingProductCommissions data = new MarketingProductCommissions();
                    lCommission.add(data);
                }
            }
        } else {
            MarketingProductCommissions data = new MarketingProductCommissions();
            lCommission.add(data);
        }
        adapter = new adapCommission(lCommission);
        adapter.notifyDataSetChanged();
        lstMain.setAdapter(adapter);
    }
    private void mToast(String Message){
        Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
    }

}
