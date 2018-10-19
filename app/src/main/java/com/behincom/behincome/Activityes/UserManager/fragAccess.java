package com.behincom.behincome.Activityes.UserManager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Adapters.UserManager.adapMarketerAssigns;
import com.behincom.behincome.Adapters.UserManager.adapMarketers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.Datas.Profile.ToSend.ToSendMarketerUserAccess;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragAccess extends Fragment {

    static Context context;
    RWInterface rInterface;
    Dialog pDialog;
    adapMarketerAssigns adapter;
    actUserManager act = new actUserManager();

    RecyclerView lstMain;
    ImageView imgBack, btnCheck;
    TextView lblTitle;
    RecyclerView.LayoutManager mLayoutManager;

    private static List<Marketers> lMarketers = new ArrayList<>();
    public static Marketers Marketer = new Marketers();

    public static fragAccess newInstance(Context mContext){
        fragAccess fragment = new fragAccess();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_access, container, false);

        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblTitle = view.findViewById(R.id.lblTitle);
        lstMain = view.findViewById(R.id.lstMain);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(getActivity()));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        lblTitle.setText("انتخاب بازاریاب");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Marketers);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ToSendMarketerUserAccess> lMarketersToSend = new ArrayList<>();
                for (Marketers data : adapter.lList) {
                    if(data.isCheck) {
                        ToSendMarketerUserAccess mData = new ToSendMarketerUserAccess();
                        mData.MainUserID = Marketer.UserID;
                        mData.SubUserID = data.UserID;

                        lMarketersToSend.add(mData);
                    }
                }

                ObjectMapper oMapper = new ObjectMapper();
                List<Map> maps = new ArrayList<>();
                for (ToSendMarketerUserAccess data : lMarketersToSend) {
                    Map map = oMapper.convertValue(data, Map.class);
                    maps.add(map);
                }

                Call cAddUsers = rInterface.RQAddMarketerUsersAccess(Setting.getToken(), maps);
                cAddUsers.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            act.getFragByState(FragmentState.Marketers);
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        pDialog.DisMiss();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        rInterface = Retrofite.getClient().create(RWInterface.class);
        pDialog = new Dialog(getActivity());
        pDialog.Show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("ManagerId", Setting.getBMMUserID());

        Call cGetAll = rInterface.RQGetMarketers(Setting.getToken(), map);
        cGetAll.enqueue(new Callback<List<Marketers>>() {
            @Override
            public void onResponse(Call<List<Marketers>> call, Response<List<Marketers>> response) {
                if(response.isSuccessful()){
                    lMarketers = response.body();

                    adapter = new adapMarketerAssigns(lMarketers, getActivity());
                    lstMain.setAdapter(adapter);
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
