package com.behincom.behincome.Activityes.Activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Adapters.Activities.adapShowGroupTask;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Activityes.DataGroupTask;
import com.behincom.behincome.Datas.Activityes.ToSend.ToSendAddTask;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragAddTasksShow extends Fragment {

    static Context context;
    RSQLite SQL = new RSQLite();
    Dialog pDialog;
    RWInterface rInterface;

    TextView lblAccept;
    TextView lblTitle;
    ImageView imgBack;
    ImageView btnCheck;
    RecyclerView lstMain;
    CardView cardView;
    RecyclerView.LayoutManager mLayoutManager;

    public static List<ToSendAddTask> lActTask = new ArrayList<>();
    public static DataGroupTask dTask = new DataGroupTask();

    public static fragAddTasksShow newInstance(Context mContext) {
        fragAddTasksShow fragment = new fragAddTasksShow();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add_tasks_show, container, false);

        lblAccept = view.findViewById(R.id.lblAccept);
        lblTitle = view.findViewById(R.id.lblTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnCheck = view.findViewById(R.id.btnCheck);
        lstMain = view.findViewById(R.id.lstMain);
        cardView = view.findViewById(R.id.cardView);


//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblAccept.setTypeface(tFace);
//        lblTitle.setTypeface(tFace);

        lblTitle.setText("وظایف انتخاب شده");
//        lblAccept.setText("تایید نهایی");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actActivities act = new actActivities();
                //todo todo todo
                //ye seri Etelaat ro bayad bargardune sare jashun :|
                //Mesle Customer hayi ke Select karde va Baghie chiayi ke dare haml mikone az SetTime
                fragAddTasks.lActTask.addAll(lActTask);
                fragAddTasks.dTask = dTask;
                fragAddTasks.CurrentCountNumber = 0;
                act.getFragByState(FragmentState.AddTasks);
            }
        });

        lstMain.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(context);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.setHasFixedSize(true);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rInterface = Retrofite.getClient().create(RWInterface.class);

                pDialog = new Dialog(context);
                pDialog.Show();

                ObjectMapper oMapper = new ObjectMapper();
                List<Map> maps = new ArrayList<>();
                for (ToSendAddTask data : lActTask) {
                    data.ActivityAddedByUserID = Setting.getUserID();
                    data.ActivityOwnerUserID = Setting.getBMMUserID();
                    Map map = oMapper.convertValue(data, Map.class);
                    maps.add(map);
                }

                Call cAddTasks = rInterface.RQAddTasks(Setting.getToken(), maps);
                cAddTasks.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if(response.isSuccessful()){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getActivity(), actMain.class);
                                    actMain.STATE = FragmentState.MainTasks;
                                    startActivity(intent);
                                }
                            });
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        pDialog.DisMiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_LONG).show();
                            }
                        });
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
        RefreshList();
    }
    private void RefreshList() {
        adapShowGroupTask adapter = new adapShowGroupTask(lActTask, context);
        lstMain.setAdapter(adapter);
    }
    private Activities getTaskConfirmData(int position) {
        Activities data = new Activities();

        data.CustomerID = (lActTask.get(position).CustomerID);
        data.ActID = (lActTask.get(position).ActID);
        data.TodoDate = (lActTask.get(position).TaskDate);
        data.ActivityDescription = (lActTask.get(position).ActivityDescription);
        data.Title = (lActTask.get(position).Title);
        data.VisitTourID = (lActTask.get(position).VisitTourID);
        data.hasReminder = (lActTask.get(position).hasReminder);

        return data;
    }
    private void Finisher(){
        lActTask = new ArrayList<>();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }

}
