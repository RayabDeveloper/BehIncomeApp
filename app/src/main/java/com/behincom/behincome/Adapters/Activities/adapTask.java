package com.behincom.behincome.Adapters.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Datas.Activityes.DataTodo;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.ArrayList;
import java.util.List;

public class adapTask extends RecyclerView.Adapter<adapTask.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<DataTodo> lList;
    public adapTask(List<DataTodo> lList, Context context){
        this.lList = lList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    public void Clear(){
        try{
            this.lList.clear();
        }catch (Exception ignored){}
    }

    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_current_tasks, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        TextView lblName = holder.lblName;
        TextView lblTime = holder.lblTime;
        CardView cardViewMain = holder.cardViewMain;

        Typeface tFace  = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblName.setTypeface(tFace);
        lblTime.setTypeface(tFace);

        List<Basic_ActResults> lSubActResult = geter.getList(Basic_ActResults.class, "WHERE isCheck='1'");
        List<Basic_ActivityFields> lSubAct = new ArrayList<>();
        for (Basic_ActResults data : lSubActResult) {
            List<Basic_ActivityFields> lFild = geter.getList(Basic_ActivityFields.class, "WHERE ActivityFieldID='" + data.ActID + "'");
            for (Basic_ActivityFields mData : lFild) {
                lSubAct.add(mData);
            }
        }
        String Title = "منسوخ شده";
        if(lSubAct.size() > 0)
            Title = lSubAct.get(0).ActivityFieldTitle + lList.get(position).title();
        else
            Title = lList.get(position).title();
        lblName.setText(Title);
        lblTime.setText(lList.get(position).todo_time());

    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public TextView lblName;
        public TextView lblTime;
        public CardView cardViewMain;

        public AdapterMember(View itemView){
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            lblTime = itemView.findViewById(R.id.lblTime);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
        }

    }

}
