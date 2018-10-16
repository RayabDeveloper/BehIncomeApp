package com.behincom.behincome.Activityes.Setting.PersonRole;


import android.app.Dialog;
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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.CustomerState.fragCustomerState;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Setting.adapArchiveType;
import com.behincom.behincome.Adapters.Setting.adapContactType;
import com.behincom.behincome.Adapters.Setting.adapCustomerState;
import com.behincom.behincome.Adapters.Setting.adapPersonRole;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_ContactTypes;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.BaseData.Basic_PersonRoles;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragPersonRole extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    adapPersonRole adapter;
    RWInterface rInterface;
    Dialog AEDialog;
    com.behincom.behincome.Accesories.Dialog pDialog;

    private List<Basic_PersonRoles> lState = new ArrayList<>();
    private boolean isInsert = true;

    ImageView imgBack, btnCheck;
    TextView lblTitle;
    RecyclerView lstMain;
    FloatingActionButton btnAdd;
    static LinearLayout btnDeleter, btnUpdate, btnCancell;
    static LinearLayout ViewEditor;
    TextInputEditText txtTitle;

    public static fragPersonRole newInstance(Context mContext) {
        fragPersonRole fragment = new fragPersonRole();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_person_role, container, false);

        btnCancell = view.findViewById(R.id.btnCancell);
        ViewEditor = view.findViewById(R.id.ViewEditor);
        btnDeleter = view.findViewById(R.id.btnDeleter);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtTitle = view.findViewById(R.id.txtTitle);
        lstMain = view.findViewById(R.id.lstMain);

        rInterface = Retrofite.getClient().create(RWInterface.class);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("نوع بایگانی");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actSetting act = new actSetting();
                act.getFragByState(FragmentState.Setting);
            }
        });
        btnCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(ViewEditor);
            }
        });
        btnDeleter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                pDialog.Show();

                Map<String, Object> BodyParameters = new HashMap<>();
                BodyParameters = new HashMap<>();
                List<Integer> ids = new ArrayList<>();
                ids.add(lList.PersonRoleID);
                BodyParameters.put("Ids", ids);

                Call Delete = rInterface.RQDeleteBasicPersonRoles(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if(response.isSuccessful()){
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                SQL.Delete(lList, " WHERE PersonRoleID='" + lList.PersonRoleID + "'");
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                        lState = geter.getList(Basic_PersonRoles.class, " WHERE Deleted='0'");
                        adapter = new adapPersonRole(lState, context);
                        lstMain.setAdapter(adapter);
                        pDialog.DisMiss();
                        slideDown(ViewEditor);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                        pDialog.DisMiss();
                    }
                });
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(ViewEditor);

                isInsert = false;
                AddEditManager();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInsert = true;
                AddEditManager();
            }
        });

        lState = geter.getList(Basic_PersonRoles.class, " WHERE Deleted='0'");
        adapter = new adapPersonRole(lState, context);
        lstMain.setHasFixedSize(true);
        lstMain.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false));
        lstMain.setAdapter(adapter);
        lstMain.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private static void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        btnDeleter.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
        btnCancell.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    private static void slideDown(final View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                btnDeleter.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);
                btnCancell.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    static Basic_PersonRoles lList = new Basic_PersonRoles();
    public static void onEditor(Basic_PersonRoles lListt){
        lList = lListt;
        slideUp(ViewEditor);
    }

    private void AddEditManager(){
        AEDialog = new Dialog(context);
        AEDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AEDialog.setCancelable(true);
        AEDialog.setCanceledOnTouchOutside(true);
        AEDialog.setContentView(R.layout.dialog_setting_insertedit);
        Objects.requireNonNull(AEDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        txtTitle = AEDialog.findViewById(R.id.txtTitle);
        TextView lblCancell = AEDialog.findViewById(R.id.lblCancell);
        TextView lblAccept = AEDialog.findViewById(R.id.lblAccept);

        if(!isInsert){
            txtTitle.setText(lList.PersonRoleTitle);
        }else{
            txtTitle.setText("");
        }

        lblCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AEDialog.dismiss();
            }
        });
        lblAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new com.behincom.behincome.Accesories.Dialog(context);
                pDialog.Show();

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
                            }
                            lState = geter.getList(Basic_PersonRoles.class, " WHERE Deleted='0'");
                            adapter = new adapPersonRole(lState, context);
                            lstMain.setAdapter(adapter);
                            pDialog.DisMiss();
                            AEDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                            pDialog.DisMiss();
                        }
                    });
                }else{
                    slideDown(ViewEditor);

                    Map<String, Object> BodyParameters = new HashMap<>();
                    BodyParameters = new HashMap<>();
                    BodyParameters.put("PersonRoleID", lList.PersonRoleID);
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
                                if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                    lList.PersonRoleTitle = txtTitle.getText().toString();
                                    lList.PersonRoleOrder = "0";
                                    lList.Deleted = false;
                                    SQL.Update(lList, " WHERE PersonRoleID='" + lList.PersonRoleID + "'");
                                }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                    String Err = "";
                                    for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                        Err = entry.getValue().toString();
                                    }
                                    Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                                }
                                lState = geter.getList(Basic_PersonRoles.class, " WHERE Deleted='0'");
                                adapter = new adapPersonRole(lState, context);
                                lstMain.setAdapter(adapter);
                                pDialog.DisMiss();
                                AEDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                            pDialog.DisMiss();
                        }
                    });
                }
            }
        });
        AEDialog.show();
    }

}
