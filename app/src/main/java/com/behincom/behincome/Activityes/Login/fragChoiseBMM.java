package com.behincom.behincome.Activityes.Login;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Splash.actSplash;
import com.behincom.behincome.Adapters.Login.adapChoiseBMM;
import com.behincom.behincome.Datas.Profile.BussinessManagerMarketing;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragChoiseBMM extends Fragment {

    static Context context;
    RWInterface rInterface;
    Dialog pDialog;

    TextView lblTitle;
    ImageView imgBack;
    ImageView btnCheck;
    RecyclerView lstMain;
    RecyclerView.LayoutManager mLayoutManager;

    public static fragChoiseBMM newInstance(Context mContext){
        fragChoiseBMM fragment = new fragChoiseBMM();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_choise_bmm, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);

        lblTitle.setText("انتخاب اکانت");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity();
            }
        });
        btnCheck.setVisibility(View.GONE);

        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        pDialog = new Dialog(context);
        pDialog.Show();
        rInterface = Retrofite.getClient().create(RWInterface.class);

        Call cGetBMM = rInterface.RQGetBMM(Setting.getToken());
        cGetBMM.enqueue(new Callback<List<BussinessManagerMarketing>>() {
            @Override
            public void onResponse(Call<List<BussinessManagerMarketing>> call, Response<List<BussinessManagerMarketing>> response) {
                if (response.isSuccessful()){
                    List<BussinessManagerMarketing> lData = response.body();
                    BussinessManagerMarketing data = new BussinessManagerMarketing();
                    data.CompanyName = "شخصی";
                    data.LogoFilename = getURLForResource(R.drawable.logo);
                    data.UserID = Setting.getUserID();
                    lData.add(0, data);

                    if(lData.size() > 1) {
                        adapChoiseBMM adapter = new adapChoiseBMM(lData, context);
                        lstMain.setAdapter(adapter);
                    }else {
                        Setting.Save("BMMUserID", Integer.toString(Setting.getUserID()));
                        Setting.Save("BMMUserName", Setting.getUserName());

                        Intent intent = new Intent(context, actSplash.class);
                        context.startActivity(intent);
                    }
                }
                pDialog.DisMiss();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pDialog.DisMiss();
            }
        });

        return view;
    }

    private String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

}
