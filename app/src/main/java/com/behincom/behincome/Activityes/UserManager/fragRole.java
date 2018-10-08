package com.behincom.behincome.Activityes.UserManager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Adapters.SpinAdapter;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.Datas.Roles.User_Roles;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragRole extends Fragment {

    static Context context;
    RWInterface rInterface;
    Dialog pDialog;
    actUserManager act = new actUserManager();
    SpinAdapter RoleAdap;

    Spinner spinRole;
    TextView lblTitle;
    ImageView imgBack, btnCheck;

    public Marketers Marketer = new Marketers();

    public static fragRole newInstance(Context mContext){
        fragRole fragment = new fragRole();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_role, container, false);

        spinRole = view.findViewById(R.id.spinRole);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);

        lblTitle.setText("تغییر نقش");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.getFragByState(FragmentState.Marketers);
            }
        });

        rInterface = Retrofite.getClient().create(RWInterface.class);
        pDialog = new Dialog(getActivity());
        pDialog.Show();

        Call cGetRoles = rInterface.RQGetBusinessManagerRoles(Setting.getToken());
        cGetRoles.enqueue(new Callback<List<User_Roles>>() {
            @Override
            public void onResponse(Call<List<User_Roles>> call, Response<List<User_Roles>> response) {
                pDialog.DisMiss();
                if(response.isSuccessful()){
                    RoleAdap = new SpinAdapter(context, response.body(), "RoleName");
                    spinRole.setAdapter(RoleAdap);

                    spinRole.setSelection(RoleAdap.getItemPosition("RoleName", Marketer.RoleName));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pDialog.DisMiss();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new Dialog(context);
                pDialog.Show();

                HashMap<String, Object> map = new HashMap<>();
                map.put("UserID", Marketer.UserID);
                map.put("MarketerRoleID", Integer.parseInt(RoleAdap.getItemString(spinRole.getSelectedItemPosition(), "RoleID")));

                Call cChangeRole = rInterface.RQChangeMarketerRole(Setting.getToken(), map);
                cChangeRole.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        pDialog.DisMiss();
                        if(response.isSuccessful()){
                            act.getFragByState(FragmentState.Marketers);
                        }
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

}
