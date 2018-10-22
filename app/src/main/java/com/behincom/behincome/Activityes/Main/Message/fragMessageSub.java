package com.behincom.behincome.Activityes.Main.Message;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Activityes.Main.Package.adapPaymentPackage;
import com.behincom.behincome.Activityes.Main.Package.fragPackage;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Message.Message_Message;
import com.behincom.behincome.Datas.Package.Payment_Packages;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.ArrayList;
import java.util.List;

public class fragMessageSub extends Fragment {

    static Context context;
    adapMessageSub adapter;
    Dialog pDialog;
    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);

    TextView lblTitle, NotFound404;
    ImageView imgBack, btnCheck;
    RecyclerView lstMain;

    protected static List<Message_Message> lMessageSub = new ArrayList<>();
    protected static String SenderName = "";

    public static fragMessageSub newInstance(Context mContext){
        fragMessageSub fragment = new fragMessageSub();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_message_sub, container, false);

        NotFound404 = view.findViewById(R.id.NotFound404);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);

        NotFound404 = view.findViewById(R.id.NotFound404);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);

        lblTitle.setText(SenderName);
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actMain act = new actMain();
                act.getFragByState(FragmentState.MessageMain);
            }
        });
        lstMain.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false));
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        if(lMessageSub.size() > 0) {
            NotFound404.setVisibility(View.GONE);
            adapter = new adapMessageSub(lMessageSub, context);
            lstMain.setAdapter(adapter);
        }else
            NotFound404.setVisibility(View.VISIBLE);

        return view;
    }

}
