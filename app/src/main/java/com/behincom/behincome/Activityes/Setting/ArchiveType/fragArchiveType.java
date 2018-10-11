package com.behincom.behincome.Activityes.Setting.ArchiveType;


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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.CustomerState.fragCustomerState;
import com.behincom.behincome.Adapters.Setting.adapArchiveType;
import com.behincom.behincome.Adapters.Setting.adapColor;
import com.behincom.behincome.Adapters.Setting.adapCustomerState;
import com.behincom.behincome.Adapters.SwipeItems.SwipeAndDragHelper;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_ArchiveTypes;
import com.behincom.behincome.Datas.BaseData.Basic_Color;
import com.behincom.behincome.Datas.BaseData.Basic_ContactTypes;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.behincom.behincome.app.AppController;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragArchiveType extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    adapArchiveType adapter;
    RWInterface rInterface;

    private List<Basic_ArchiveTypes> lState = new ArrayList<>();
    private boolean isInsert = true;

    ImageView imgBack, btnCheck;
    TextView lblTitle;
    RecyclerView lstMain;
    FloatingActionButton btnAdd;
    LinearLayout dialogAdd, btnDeleter, btnUpdate;
    static LinearLayout ViewEditor;
    TextInputEditText txtTitle;
    CardView cardSubmit;

    public static fragArchiveType newInstance(Context mContext) {
        fragArchiveType fragment = new fragArchiveType();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_archive_type, container, false);

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
                List<Integer> ids = new ArrayList<>();
                ids.add(lList.ArchiveTypeID);
                BodyParameters.put("Ids", ids);

                Call Delete = rInterface.RQDeleteBasicArchiveTypes(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if(response.isSuccessful()){
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                SQL.Delete(lList.getClass(), " WHERE ArchiveTypeID='" + lList.ArchiveTypeID + "'");
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                        lState = geter.getList(Basic_ArchiveTypes.class);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(ViewEditor);
                slideUp(dialogAdd);

                txtTitle.setText(lList.ArchiveTypeTitle);
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
                    BodyParameters.put("ArchiveTypeID", 0);
                    BodyParameters.put("ArchiveTypeTitle", txtTitle.getText().toString());
                    BodyParameters.put("ArchiveTypeOrder", 0);
                    BodyParameters.put("LastUpdateDate", "");
                    BodyParameters.put("Deleted", false);
                    BodyParameters.put("ArchiveTypeUserId", 0);
                    BodyParameters.put("AdjustedByAdmin", false);
                    BodyParameters.put("ArchiveTypeFontIcon", "");
                    BodyParameters.put("ArchiveTypeColor", "");

                    Call Insert = rInterface.RQInsertBasicArchiveTypes(Setting.getToken(), new HashMap<>(BodyParameters));
                    Insert.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                SimpleResponse simple = response.body();
                                Map<String, Object> addional = simple.AdditionalData;
                                String mID = addional.get("ItemId").toString();
                                int Id = Integer.parseInt(mID.replace(".0", ""));

                                Basic_ArchiveTypes data = new Basic_ArchiveTypes();
                                data.AdjustedByAdmin = false;
                                data.ArchiveTypeID = Id;
                                data.ArchiveTypeTitle = txtTitle.getText().toString();
                                data.isCheck = true;

                                SQL.Insert(data);
                            }
                            lState = geter.getList(Basic_ArchiveTypes.class);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Map<String, Object> BodyParameters = new HashMap<>();
                    BodyParameters = new HashMap<>();
                    BodyParameters.put("ArchiveTypeID", 0);
                    BodyParameters.put("ArchiveTypeTitle", txtTitle.getText().toString());
                    BodyParameters.put("ArchiveTypeOrder", 0);
                    BodyParameters.put("LastUpdateDate", "");
                    BodyParameters.put("Deleted", false);
                    BodyParameters.put("ArchiveTypeUserId", 0);
                    BodyParameters.put("AdjustedByAdmin", false);
                    BodyParameters.put("ArchiveTypeFontIcon", "");
                    BodyParameters.put("ArchiveTypeColor", "");

                    Call Update = rInterface.RQUpdateBasicArchiveTypes(Setting.getToken(), new HashMap<>(BodyParameters));
                    Update.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                SimpleResponse simple = response.body();
                                if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                    SQL.Update(lList, " WHERE ArchiveTypeID='" + lList.ArchiveTypeID + "'");
                                }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                    String Err = "";
                                    for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                        Err = entry.getValue().toString();
                                    }
                                    Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                                }
//                                Map<String, Object> addional = simple.AdditionalData;
//                                String mID = addional.get("ItemId").toString();
//                                int Id = Integer.parseInt(mID.replace(".0", ""));
//
//                                Basic_ArchiveTypes data = new Basic_ArchiveTypes();
//                                data.AdjustedByAdmin = false;
//                                data.ArchiveTypeID = Id;
//                                data.ArchiveTypeTitle = txtTitle.getText().toString();
//                                data.isCheck = true;
//
//                                SQL.Insert(data);
                                lState = geter.getList(Basic_ArchiveTypes.class);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        lState = geter.getList(Basic_ArchiveTypes.class);
        adapter = new adapArchiveType(lState, context);
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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    static Basic_ArchiveTypes lList = new Basic_ArchiveTypes();
    public static void onEditor(Basic_ArchiveTypes lListt){
        lList = lListt;
        slideUp(ViewEditor);
    }

}
