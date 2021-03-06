package com.behincom.behincome.Adapters.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragTaskShow;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.ArrayList;
import java.util.List;

public class adapMainTask extends RecyclerView.Adapter<adapMainTask.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
//    actActivities act = new actActivities();

    public List<Activities> lList;

    public adapMainTask(List<Activities> lList, Context context) {
        this.lList = lList;
        this.context = context;
        getActs();
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    public void Clear() {
        try {
            this.lList.clear();
        } catch (Exception ignored) {
        }
    }

    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_main_tasks, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        LinearLayout linLine = holder.linLine;
        TextView lblName = holder.lblName;
        TextView lblTime = holder.lblTime;
        TextView lblCondition = holder.lblCondition;
        CardView cardViewMain = holder.cardViewMain;

        String[] times = lList.get(position).TodoDate.split("T");
        String[] hours = times[1].split(":");
        String mTime = hours[0] + ":" + hours[1];

        String Title = "منسوخ شده";
        for (Basic_Acts data : lSubAct) {
            if(lList.get(position).ActID == data.ActID)
                Title = data.ActTitle;
        }

        lblName.setText(Title + " " + lList.get(position).Title);
        lblTime.setText(mTime);

        linLine.setVisibility(View.GONE);
        if (lList.get(position).EnterDate == null)
            lList.get(position).EnterDate = "";
        if (lList.get(position).ExitDate == null)
            lList.get(position).ExitDate = "";
        if (lList.get(position).TodoDate == null)
            lList.get(position).TodoDate = "";

        if (lList.get(position).TodoDate.length() > 5) {
            if (lList.get(position).EnterDate.length() < 5){//Only Task
                lblName.setTextColor(context.getResources().getColor(R.color.txtBlue2));
                lblTime.setTextColor(context.getResources().getColor(R.color.txtBlue2));
            }else if(lList.get(position).EnterDate.length() > 5 && lList.get(position).ExitDate.length() < 5){//Only Enter
                lblName.setTextColor(context.getResources().getColor(R.color.txtGreen));
                lblTime.setTextColor(context.getResources().getColor(R.color.txtGreen));
            }else if(lList.get(position).EnterDate.length() > 5 && lList.get(position).ExitDate.length() > 5) {//Exited
                lblName.setTextColor(context.getResources().getColor(R.color.txtGreen));
                lblTime.setTextColor(context.getResources().getColor(R.color.txtGreen));
                linLine.setVisibility(View.VISIBLE);
            }
        }

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actActivities.class);
                actActivities.STATE = FragmentState.TaskShow;
                fragTaskShow.lData = lList.get(position);
                fragTaskShow.CustomerId = lList.get(position).CustomerID;
                context.startActivity(intent);
            }
        });

    }
    private static List<Basic_Acts> lSubAct = new ArrayList<>();
    private void getActs(){
        List<Basic_ActResults> lSubActResult = geter.getList(Basic_ActResults.class);
        lSubAct = new ArrayList<>();
        for (Basic_ActResults data : lSubActResult) {
            List<Basic_Acts> lFild = geter.getList(Basic_Acts.class, "WHERE ActID='" + data.ActID + "'");
            for (Basic_Acts mData : lFild) {
                lSubAct.add(mData);
            }
        }
        try {
            for (int i = 0; i < lSubAct.size(); i++) {
                try {
                    int ID = lSubAct.get(i).ActID;
                    for (int j = i + 1; j < lSubAct.size(); j++) {
                        try {
                            if (ID == lSubAct.get(j).ActID) {
                                lSubAct.remove(j);
                                j--;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linLine;
        public TextView lblName;
        public TextView lblCondition;
        public TextView lblTime;
        public CardView cardViewMain;
//        public LinearLayout lblLine;

        public AdapterMember(View itemView) {
            super(itemView);
            linLine = itemView.findViewById(R.id.linLine);
            lblCondition = itemView.findViewById(R.id.lblCondition);
            lblName = itemView.findViewById(R.id.lblName);
            lblTime = itemView.findViewById(R.id.lblTime);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
//            lblLine = itemView.findViewById(R.id.lblLine);
        }

    }

}
