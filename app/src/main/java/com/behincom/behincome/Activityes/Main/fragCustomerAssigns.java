package com.behincom.behincome.Activityes.Main;

import android.content.Context;
import android.content.Intent;
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
import com.behincom.behincome.Activityes.UserManager.actUserManager;
import com.behincom.behincome.Activityes.UserManager.fragAccess;
import com.behincom.behincome.Adapters.UserManager.adapMarketerAssigns;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.Customer.ToSend.ToSendCustomersAssigns;
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

public class fragCustomerAssigns extends Fragment {

    static Context context;
    RWInterface rInterface;
    Dialog pDialog;
    adapMarketerAssigns adapter;
    actMain act = new actMain();

    RecyclerView lstMain;
    ImageView imgBack, btnCheck;
    TextView lblTitle;
    RecyclerView.LayoutManager mLayoutManager;

    public static List<MyCustomers> lCustomers = new ArrayList<>();
    private static List<Marketers> lMarketers = new ArrayList<>();

    public static fragCustomerAssigns newInstance(Context mContext){
        fragCustomerAssigns fragment = new fragCustomerAssigns();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customer_assigns, container, false);

        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lblTitle = view.findViewById(R.id.lblTitle);
        lstMain = view.findViewById(R.id.lstMain);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        lblTitle.setText("انتخاب بازاریاب");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.MainCustomers);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Marketers> nMarketer = new ArrayList<>();
                try {
                    for (Marketers data : adapter.lList) {
                        if(data.isCheck)
                            nMarketer.add(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                List<Integer> CustomerIDs = new ArrayList<>();
                List<Integer> MarketerIDs = new ArrayList<>();
                for (MyCustomers data: lCustomers) {
                    CustomerIDs.add(data.Customers.CustomerID);
                }
                for (Marketers data: nMarketer) {
                    MarketerIDs.add(data.UserID);
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("CustomerIDs", CustomerIDs);
                map.put("MarketerIDs", MarketerIDs);

                Call cAddUsers = rInterface.RQAssignCustomersToUsers(Setting.getToken(), map);
                cAddUsers.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), actMain.class);
                            getActivity().startActivity(intent);
//                            act.getFragByState(FragmentState.MainEmpty);
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
