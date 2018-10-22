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
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Main.Package.adapPaymentPackage;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Message.Message_Conversation;
import com.behincom.behincome.R;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragMessageMain extends Fragment {

    static Context context;
    adapMessageMain adapter;
    Dialog pDialog;
    RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);

    TextView lblTitle, NotFound404;
    ImageView imgBack, btnCheck;
    RecyclerView lstMain;

    List<Message_Conversation> lMessageMain = new ArrayList<>();

    public static fragMessageMain newInstance(Context mContext){
        fragMessageMain fragment = new fragMessageMain();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_message_main, container, false);

        NotFound404 = view.findViewById(R.id.NotFound404);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);

        lblTitle.setText("پیام");
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actMain act = new actMain();
                act.getFragByState(FragmentState.MainTasks);
            }
        });
        lstMain.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false));
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        {//Get All Messages and put into List RecyclerView
            pDialog = new Dialog(context);
            pDialog.Show();

            HashMap<String, Object> map = new HashMap<>();
            map.put("BusinessManagerId", Setting.getBMMUserID());

            Call Messages = rInterface.RQGetAllMessages(Setting.getToken(), map);
            Messages.enqueue(new Callback<List<Message_Conversation>>() {
                @Override
                public void onResponse(Call<List<Message_Conversation>> call, Response<List<Message_Conversation>> response) {
                    if (response.isSuccessful()) {
                        lMessageMain = response.body();
                        if(lMessageMain.size() > 0){
                            adapter = new adapMessageMain(lMessageMain, context);
                            lstMain.setAdapter(adapter);
                            NotFound404.setVisibility(View.GONE);
                            lstMain.setVisibility(View.VISIBLE);
                        }else {
                            NotFound404.setVisibility(View.VISIBLE);
                            lstMain.setVisibility(View.GONE);
                        }
                    }
                    pDialog.DisMiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.DisMiss();
                }
            });
        }

        return view;
    }

}
