package com.behincom.behincome.Activityes.Main.Package;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.Main.adapMainCustomerMarketers;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Package.Payment_Packages;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragPackage extends Fragment {

    static Context context;
    adapPaymentPackage adapter;
    Dialog pDialog;
    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);

    TextView lblTitle, NotFound404;
    ImageView imgBack, btnCheck;
    RecyclerView lstMain;

    List<Payment_Packages> lPackage = new ArrayList<>();

    public static fragPackage newInstance(Context mContext){
        fragPackage fragment = new fragPackage();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_package, container, false);

        NotFound404 = view.findViewById(R.id.NotFound404);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);

        lblTitle.setText("انتخاب پکیج");
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actMain act = new actMain();
                act.getFragByState(FragmentState.MainAccounts);
            }
        });
        lstMain.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        {//Get All Packages and put into List RecyclerView at 2 Column
            pDialog = new Dialog(context);
            pDialog.Show();

            Call Packages = rInterface.RQGetPackages();
            Packages.enqueue(new Callback<List<Payment_Packages>>() {
                @Override
                public void onResponse(Call<List<Payment_Packages>> call, Response<List<Payment_Packages>> response) {
                    if (response.isSuccessful()) {
                        lPackage = response.body();
                        if(lPackage.size() > 0){
                            adapter = new adapPaymentPackage(lPackage, context);
                            lstMain.setAdapter(adapter);
                            NotFound404.setVisibility(View.GONE);
                            lstMain.setVisibility(View.VISIBLE);
                        }else {
                            NotFound404.setVisibility(View.VISIBLE);
                            lstMain.setVisibility(View.GONE);
                        }
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.DisMiss();
                }
            });
        }

        return view;
    }

}
