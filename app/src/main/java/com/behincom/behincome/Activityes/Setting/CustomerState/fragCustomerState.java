package com.behincom.behincome.Activityes.Setting.CustomerState;


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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.fragBasicData;
import com.behincom.behincome.Adapters.Setting.adapColor;
import com.behincom.behincome.Adapters.Setting.adapCustomerState;
import com.behincom.behincome.Adapters.SwipeItems.Customers.OnCustomerListChangedListener;
import com.behincom.behincome.Adapters.SwipeItems.Helper.OnStartDragListener;
import com.behincom.behincome.Adapters.SwipeItems.SwipeAndDragHelper;
import com.behincom.behincome.Datas.BaseData.Basic_Color;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.RSQLGeter;
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

import retrofit2.Call;

public class fragCustomerState extends Fragment{

    static Context context = AppController.getContext;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    adapCustomerState adapter;
    adapColor adapterColor;
    SwipeAndDragHelper swipeAndDragHelper;
    ItemTouchHelper touchHelper;
    RWInterface rInterface;
    private ItemTouchHelper mItemTouchHelper;

    CardView itemUp, itemDown;
    TextView itemUpTitle, itemDownTitle, lblTitle;
    ImageView itemUpColor, itemDownColor, imgBack, btnCheck;
    RecyclerView lstMain, lstColor;
    static LinearLayout ViewEditor;
    LinearLayout dialogAdd, btnDeleter, btnUpdate;
    TextInputEditText txtTitle;
    CardView cardSubmit;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customer_state, container, false);

        ViewEditor = view.findViewById(R.id.ViewEditor);
        btnDeleter = view.findViewById(R.id.btnDeleter);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        btnAdd = view.findViewById(R.id.btnAdd);
        lstColor = view.findViewById(R.id.lstColor);
        dialogAdd = view.findViewById(R.id.dialogAdd);
        txtTitle = view.findViewById(R.id.txtTitle);
        cardSubmit = view.findViewById(R.id.cardSubmit);
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
        btnDeleter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> BodyParameters = new HashMap<>();
                BodyParameters = new HashMap<>();
                BodyParameters.put("customerStateId", lList.CustomerStateID);

                Call Delete = rInterface.RQDeleteBasicCustomerStates(Setting.getToken(), new HashMap<>(BodyParameters));
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(ViewEditor);
                slideUp(dialogAdd);

                txtTitle.setText(lList.CustomerStateTitle);
                try {
                    List<Basic_Color> lColor = geter.getList(Basic_Color.class);
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
                    BodyParameters.put("CustomerStateID", 0);
                    BodyParameters.put("CustomerStateTitle", txtTitle.getText().toString());
                    BodyParameters.put("CustomerStateColor", ColorID);
                    BodyParameters.put("CustomerStateOrder", 0);
                    BodyParameters.put("CustomerStateFontIcon", "");

                    Call Insert = rInterface.RQInsertBasicCustomerStates(Setting.getToken(), new HashMap<>(BodyParameters));
                }else{
                    Map<String, Object> BodyParameters = new HashMap<>();
                    BodyParameters = new HashMap<>();
                    BodyParameters.put("CustomerStateID", 0);
                    BodyParameters.put("CustomerStateTitle", txtTitle.getText().toString());
                    BodyParameters.put("CustomerStateColor", ColorID);
                    BodyParameters.put("CustomerStateOrder", 0);
                    BodyParameters.put("CustomerStateFontIcon", "");

                    Call Update = rInterface.RQUpdateBasicCustomerStates(Setting.getToken(), new HashMap<>(BodyParameters));
                }
            }
        });

        lState = geter.getList(Basic_CustomerStates.class);
        adapter = new adapCustomerState(lState, context);
        lstMain.setHasFixedSize(true);
        lstMain.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false));
        lstMain.setAdapter(adapter);
        lstMain.setItemAnimator(new DefaultItemAnimator());

        try {
            List<Basic_Color> lColor = geter.getList(Basic_Color.class);
            adapterColor = new adapColor(lColor, context, lColor.get(0).ColorID);
            lstColor.setHasFixedSize(true);
            lstColor.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false));
            lstColor.setAdapter(adapterColor);
            lstColor.setItemAnimator(new DefaultItemAnimator());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
//
//    @Override
//    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        mItemTouchHelper.startDrag(viewHolder);
//    }
//    @Override
//    public void onNoteListChanged(List<Basic_CustomerStates> customers, int fromPosition, int toPosition) {
//
//    }

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

    static Basic_CustomerStates lList = new Basic_CustomerStates();
    public static void onEditor(Basic_CustomerStates lListt){
        lList = lListt;
        slideUp(ViewEditor);
    }

}
