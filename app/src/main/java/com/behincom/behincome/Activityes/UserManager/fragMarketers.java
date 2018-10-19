package com.behincom.behincome.Activityes.UserManager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.behincom.behincome.Activityes.Account.actProfile;
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

public class fragMarketers extends Fragment {

    static Context context;
    RWInterface rInterface;
    Dialog pDialog;
    adapMarketers adapter;
    actUserManager act = new actUserManager();

    RecyclerView lstMain;
    ImageView imgBack, btnCheck;
    TextView lblTitle;
    FloatingActionButton btnAddMarketer;
    RecyclerView.LayoutManager mLayoutManager;

    private static List<Marketers> lMarketers = new ArrayList<>();
    public FragmentState State = FragmentState.Assign;
    public int UserID = 0;
    public List<Integer> lCustomerIDs = new ArrayList<>();
    public int Type = 0;
    public boolean multiSelect = false;

    public static fragMarketers newInstance(Context mContext){
        fragMarketers fragment = new fragMarketers();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_marketers, container, false);

        btnAddMarketer = view.findViewById(R.id.btnAddMarketer);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblTitle = view.findViewById(R.id.lblTitle);
        lstMain = view.findViewById(R.id.lstMain);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        lblTitle.setText("بازاریاب ها");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), actProfile.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        if(Type == 0){
            btnAddMarketer.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.GONE);
        }else{
            btnAddMarketer.setVisibility(View.GONE);
            imgBack.setVisibility(View.VISIBLE);
        }

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (State){
                    case MarketerAccess:
                        pDialog = new Dialog(getActivity());
                        pDialog.Show();

                        List<ToSendMarketerUserAccess> lMarketersToSend = new ArrayList<>();
                        for (Marketers data : adapter.lList) {
                            if(data.isCheck) {
                                ToSendMarketerUserAccess mData = new ToSendMarketerUserAccess();
                                mData.MainUserID = UserID;
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
                                    //todo todo todo added and now can go fuck your self
                                }
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                pDialog.DisMiss();
                            }
                        });
                        break;
                    case Assign:
                        pDialog = new Dialog(getActivity());
                        pDialog.Show();

                        List<Integer> lMarketerIDs = new ArrayList<>();
                        for (Marketers data : adapter.lList) {
                            if(data.isCheck) {
                                lMarketerIDs.add(data.UserID);
                            }
                        }

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("CustomerIDs", lCustomerIDs);
                        map.put("MarketerIDs", lMarketerIDs);

                        Call cAssigns = rInterface.RQAssignCustomersToUsers(Setting.getToken(), map);
                        cAssigns.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if(response.isSuccessful()){
                                    //todo todo todo Added Assings, can go fuck your self
                                }
                                pDialog.DisMiss();
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                pDialog.DisMiss();
                            }
                        });
                        break;
                }
            }
        });

        btnAddMarketer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.AddUser);
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

                    adapter = new adapMarketers(lMarketers, getActivity());
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
