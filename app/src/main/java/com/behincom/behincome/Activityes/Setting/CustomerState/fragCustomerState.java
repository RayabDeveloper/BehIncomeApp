package com.behincom.behincome.Activityes.Setting.CustomerState;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Adapters.Setting.adapColor;
import com.behincom.behincome.Adapters.Setting.adapCustomerState;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_Color;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.behincom.behincome.app.AppController;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragCustomerState extends Fragment{

    static Context context = AppController.getContext;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    static adapCustomerState adapter;
    adapColor adapterColor;
    RWInterface rInterface;
    Dialog AEDialog;
    com.behincom.behincome.Accesories.Dialog pDialog;

    CardView itemUp, itemDown;
    TextView itemUpTitle, itemDownTitle, lblTitle;
    ImageView itemUpColor, itemDownColor, imgBack, btnCheck;
    RecyclerView lstMain;
    static LinearLayout ViewEditor;
    static LinearLayout btnDeleter, btnUpdate, btnCancell;
    TextInputEditText txtTitle;
    FloatingActionButton btnAdd;

    private List<Basic_CustomerStates> lState = new ArrayList<>();
    public static int ColorID = 0;
    private boolean isInsert = true;

    public static fragCustomerState newInstance(Context mContext) {
        fragCustomerState fragment = new fragCustomerState();
        context = mContext;
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        slideDown(ViewEditor);
        txtTitle.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customer_state, container, false);

        btnCancell = view.findViewById(R.id.btnCancell);
        ViewEditor = view.findViewById(R.id.ViewEditor);
        btnDeleter = view.findViewById(R.id.btnDeleter);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtTitle = view.findViewById(R.id.txtTitle);
        itemUp = view.findViewById(R.id.itemUp);
        itemDown = view.findViewById(R.id.itemDown);
        itemUpTitle = view.findViewById(R.id.itemUpTitle);
        itemDownTitle = view.findViewById(R.id.itemDownTitle);
        itemUpColor = view.findViewById(R.id.itemUpColor);
        itemDownColor = view.findViewById(R.id.itemDownColor);
        lstMain = view.findViewById(R.id.lstMain);

        rInterface = Retrofite.getClient().create(RWInterface.class);

        btnCheck.setVisibility(View.GONE);
        lblTitle.setText("وضعیت مشتری");
        itemUpTitle.setText("ثبت اولیه");
        itemDownTitle.setText("پایان کار");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actSetting act = new actSetting();
                act.getFragByState(FragmentState.Setting);
            }
        });
        lState = geter.getList(Basic_CustomerStates.class, " WHERE Deleted='0'");
        try {
            ByteArrayOutputStream stream = null;
            try {
                Bitmap bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmp);
                canvas.drawColor(Color.parseColor("#" + lState.get(0).CustomerStateColor));
                stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(itemUpColor) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    itemUpColor.setImageDrawable(circularBitmapDrawable);
                }
            });
            ByteArrayOutputStream stream2 = null;
            try {
                Bitmap bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmp);
                canvas.drawColor(Color.parseColor("#" + lState.get(1).CustomerStateColor));
                stream2 = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Glide.with(context).load(stream2.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(itemDownColor) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    itemDownColor.setImageDrawable(circularBitmapDrawable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                ids.add(lList.CustomerStateID);
                BodyParameters.put("Ids", ids);

                Call Delete = rInterface.RQDeleteBasicCustomerStates(Setting.getToken(), new HashMap<>(BodyParameters));
                Delete.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if(response.isSuccessful()){
                            SimpleResponse simple = response.body();
                            if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                SQL.Delete(lList, " WHERE CustomerStateID='" + lList.CustomerStateID + "'");
                            }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                String Err = "";
                                for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                    Err = entry.getValue().toString();
                                }
                                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                            }
                        }
                        lState = geter.getList(Basic_CustomerStates.class, " WHERE Deleted='0'");
                        adapter = new adapCustomerState(lState, context);
                        lstMain.setAdapter(adapter);
                        pDialog.DisMiss();
                        slideDown(ViewEditor);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                        pDialog.DisMiss();
                        slideDown(ViewEditor);
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

        adapter = new adapCustomerState(lState, context);
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

    static Basic_CustomerStates lList = new Basic_CustomerStates();
    public static void onEditor(Basic_CustomerStates lListt){
        lList = lListt;
        slideUp(ViewEditor);
    }

    private void AddEditManager(){
        AEDialog = new Dialog(context);
        AEDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AEDialog.setCancelable(true);
        AEDialog.setCanceledOnTouchOutside(true);
        AEDialog.setContentView(R.layout.dialog_setting_insertedit_customerstate);
        Objects.requireNonNull(AEDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        txtTitle = AEDialog.findViewById(R.id.txtTitle);
        TextView lblCancell = AEDialog.findViewById(R.id.lblCancell);
        TextView lblAccept = AEDialog.findViewById(R.id.lblAccept);
        RecyclerView lstColor = AEDialog.findViewById(R.id.lstColor);

        if(!isInsert){
            txtTitle.setText(lList.CustomerStateTitle);
            try {
                List<Basic_Color> lColor = geter.getList(Basic_Color.class, " WHERE Deleted='0'");
                int cID = 0;
                for (Basic_Color color : lColor) {
                    if(color.ColorCode.equalsIgnoreCase(lList.CustomerStateColor))
                        cID = color.ColorID;
                }
                adapterColor = new adapColor(lColor, context, cID);
                lstColor.setHasFixedSize(true);
                lstColor.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false));
                lstColor.setAdapter(adapterColor);
                lstColor.setItemAnimator(new DefaultItemAnimator());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            txtTitle.setText("");
            try {
                List<Basic_Color> lColor = geter.getList(Basic_Color.class, " WHERE Deleted='0'");
                adapterColor = new adapColor(lColor, context, lColor.get(0).ColorID);
                lstColor.setHasFixedSize(true);
                lstColor.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false));
                lstColor.setAdapter(adapterColor);
                lstColor.setItemAnimator(new DefaultItemAnimator());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                    BodyParameters.put("CustomerStateID", 0);
                    BodyParameters.put("CustomerStateTitle", txtTitle.getText().toString());
                    BodyParameters.put("CustomerStateColor", ColorID);
                    BodyParameters.put("CustomerStateOrder", 0);
                    BodyParameters.put("CustomerStateFontIcon", "");

                    Call Insert = rInterface.RQInsertBasicCustomerStates(Setting.getToken(), new HashMap<>(BodyParameters));
                    Insert.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                Basic_CustomerStates data = null;
                                try {
                                    SimpleResponse simple = response.body();
                                    Map<String, Object> addional = simple.AdditionalData;
                                    String mID = addional.get("ItemId").toString();
                                    int Id = Integer.parseInt(mID.replace(".0", ""));
                                    String mOrder = addional.get("ItemOrder").toString().replace(".0", "");

                                    data = new Basic_CustomerStates();
                                    data.CustomerStateAdjustedByAdmin = false;
                                    data.CustomerStateID = Id;
                                    data.CustomerStateOrder = mOrder;
                                    data.CustomerStateTitle = txtTitle.getText().toString();
                                    List<Basic_Color> lCol = geter.getList(Basic_Color.class, " WHERE ColorID='" + ColorID + "'");
                                    if(lCol.size() > 0)
                                        data.CustomerStateColor = lCol.get(0).ColorCode;
                                    data.isCheck = true;
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                SQL.Insert(data);
                            }
                            lState = geter.getList(Basic_CustomerStates.class, " WHERE Deleted='0'");
                            adapter = new adapCustomerState(lState, context);
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
                    BodyParameters.put("CustomerStateID", lList.CustomerStateID);
                    BodyParameters.put("CustomerStateTitle", txtTitle.getText().toString());
                    BodyParameters.put("CustomerStateColor", ColorID);
                    BodyParameters.put("CustomerStateOrder", lList.CustomerStateOrder);
                    BodyParameters.put("CustomerStateFontIcon", "");

                    Call Update = rInterface.RQUpdateBasicCustomerStates(Setting.getToken(), new HashMap<>(BodyParameters));
                    Update.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.isSuccessful()){
                                SimpleResponse simple = response.body();
                                if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
                                    lList.CustomerStateTitle = txtTitle.getText().toString();
                                    SQL.Update(lList, " WHERE CustomerStateID='" + lList.CustomerStateID + "'");
                                }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                                    String Err = "";
                                    for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                                        Err = entry.getValue().toString();
                                    }
                                    Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
                                }
                                lState = geter.getList(Basic_CustomerStates.class, " WHERE Deleted='0'");
                                adapter = new adapCustomerState(lState, context);
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
