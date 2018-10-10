package com.behincom.behincome.Activityes.Setting.PersonRole;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.CustomerState.fragCustomerState;
import com.behincom.behincome.Adapters.Setting.adapArchiveType;
import com.behincom.behincome.Adapters.Setting.adapPersonRole;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_ContactTypes;
import com.behincom.behincome.Datas.BaseData.Basic_PersonRoles;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragPersonRole extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    adapPersonRole adapter;
    RWInterface rInterface;

    private List<Basic_PersonRoles> lState = new ArrayList<>();
    private boolean isInsert = true;

    ImageView imgBack, btnCheck;
    TextView lblTitle;
    RecyclerView lstMain;
    FloatingActionButton btnAdd;
    LinearLayout dialogAdd, btnDeleter, btnUpdate;
    static LinearLayout ViewEditor;
    TextInputEditText txtTitle;
    CardView cardSubmit;

    public static fragPersonRole newInstance(Context mContext) {
        fragPersonRole fragment = new fragPersonRole();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_person_role, container, false);

        ViewEditor = view.findViewById(R.id.ViewEditor);
        btnDeleter = view.findViewById(R.id.btnDeleter);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        btnAdd = view.findViewById(R.id.btnAdd);
        dialogAdd = view.findViewById(R.id.dialogAdd);
        txtTitle = view.findViewById(R.id.txtTitle);
        cardSubmit = view.findViewById(R.id.cardSubmit);
        lstMain = view.findViewById(R.id.lstMain);

        rInterface = Retrofite.getClient().create(RWInterface.class);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("نوع بایگانی");

        btnDeleter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> BodyParameters = new HashMap<>();
                BodyParameters = new HashMap<>();
                BodyParameters.put("archiveTypeId", lList.PersonRoleID);

                Call Delete = rInterface.RQDeleteBasicPersonRoles(Setting.getToken(), new HashMap<>(BodyParameters));
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(ViewEditor);
                slideUp(dialogAdd);

                txtTitle.setText(lList.PersonRoleTitle);
                isInsert = false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp(dialogAdd);
            }
        });
        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(dialogAdd);

                if(isInsert) {
                    Map<String, Object> BodyParameters = new HashMap<>();
                    BodyParameters = new HashMap<>();
                    BodyParameters.put("PersonRoleID", 0);
                    BodyParameters.put("PersonRoleTitle", txtTitle.getText().toString());
                    BodyParameters.put("PersonRoleOrder", 0);
                    BodyParameters.put("PersonRoleFontIcon", "");
                    BodyParameters.put("PersonRoleColor", "");

                    Call Insert = rInterface.RQInsertBasicPersonRoles(Setting.getToken(), new HashMap<>(BodyParameters));
                    Insert.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                SimpleResponse simple = response.body();
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));

                                Basic_PersonRoles data = new Basic_PersonRoles();
                                data.PersonRoleAdjustedByAdmin = false;
                                data.PersonRoleID = Id;
                                data.PersonRoleTitle = txtTitle.getText().toString();
                                data.isCheck = true;

                                SQL.Insert(data);
                                lState = geter.getList(Basic_ContactTypes.class);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            String asd = "ASD";
                        }
                    });
                }else{
                    Map<String, Object> BodyParameters = new HashMap<>();
                    BodyParameters = new HashMap<>();
                    BodyParameters.put("PersonRoleID", 0);
                    BodyParameters.put("PersonRoleTitle", txtTitle.getText().toString());
                    BodyParameters.put("PersonRoleOrder", 0);
                    BodyParameters.put("PersonRoleFontIcon", "");
                    BodyParameters.put("PersonRoleColor", "");

                    Call Update = rInterface.RQUpdateBasicPersonRoles(Setting.getToken(), new HashMap<>(BodyParameters));
                    Update.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                SimpleResponse simple = response.body();
//                                Map<String, Object> addional = simple.AdditionalData;
//                                String mID = addional.get("ItemId").toString();
//                                int Id = Integer.parseInt(mID.replace(".0", ""));
//
//                                Basic_PersonRoles data = new Basic_PersonRoles();
//                                data.PersonRoleAdjustedByAdmin = false;
//                                data.PersonRoleID = Id;
//                                data.PersonRoleTitle = txtTitle.getText().toString();
//                                data.isCheck = true;
//
//                                SQL.Insert(data);
                                lState = geter.getList(Basic_ContactTypes.class);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            String asd = "ASD";
                        }
                    });
                }
            }
        });

        lState = geter.getList(Basic_PersonRoles.class);
        adapter = new adapPersonRole(lState, context);
        lstMain.setHasFixedSize(true);
        lstMain.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false));
        lstMain.setAdapter(adapter);
        lstMain.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private static void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    private static void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    static Basic_PersonRoles lList = new Basic_PersonRoles();
    public static void onEditor(Basic_PersonRoles lListt){
        lList = lListt;
        slideUp(ViewEditor);
    }
}
