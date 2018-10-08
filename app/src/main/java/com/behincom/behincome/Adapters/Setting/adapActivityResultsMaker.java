package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.fragActResults;
import com.behincom.behincome.Datas.BaseData.Basic_ActResultsMaker;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

public class adapActivityResultsMaker extends RecyclerView.Adapter<adapActivityResultsMaker.AdapterMember> {

    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    fragActResults frag = new fragActResults();

    public List<Basic_ActResultsMaker> lList;

    public adapActivityResultsMaker(List<Basic_ActResultsMaker> lList) {
        this.lList = lList;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_sub_act_subact_result, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        LinearLayout linMainTitle = holder.linMainTitle;
        LinearLayout linSubTitle = holder.linSubTitle;
        RelativeLayout linTitle = holder.linTitle;
        TextView lblMainTitle = holder.lblMainTitle;
        TextView lblSubTitle = holder.lblSubTitle;
        TextView lblTitle = holder.lblTitle;
        final AppCompatCheckBox ch = holder.ch;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblMainTitle.setTypeface(tf);
//        lblSubTitle.setTypeface(tf);
//        lblTitle.setTypeface(tf);

        switch (lList.get(position).ActResultsType) {
            case 0:
                linMainTitle.setVisibility(View.VISIBLE);
                linSubTitle.setVisibility(View.GONE);
                linTitle.setVisibility(View.GONE);

                lblMainTitle.setText(lList.get(position).ActResultsTitle);
                break;
            case 1:
                linMainTitle.setVisibility(View.GONE);
                linSubTitle.setVisibility(View.VISIBLE);
                linTitle.setVisibility(View.GONE);

                lblSubTitle.setText(lList.get(position).ActResultsTitle);
                break;
            case 2:
                linMainTitle.setVisibility(View.GONE);
                linSubTitle.setVisibility(View.GONE);
                linTitle.setVisibility(View.VISIBLE);

                lblTitle.setText(lList.get(position).ActResultsTitle);
                if (lList.get(position).isCheck) {
                    ch.setChecked(true);
                    frag.IdsToSend += lList.get(position).ActResultsID + ", ";
                    String rr = "ASD";
                }else
                    ch.setChecked(false);
                break;
        }
        lblTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch.isChecked()) ch.setChecked(false);
                else ch.setChecked(true);
                Checker(ch.isChecked(), position);
            }
        });
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checker(ch.isChecked(), position);
            }
        });
    }

    private void Checker(boolean isCheck, int position) {
        String Q2 = "";
        if (isCheck) {
            Q2 = "UPDATE Basic_ActResults SET isCheck='1' WHERE ActResultID='" + lList.get(position).ActResultsID + "'";
            frag.IdsToSend += lList.get(position).ActResultsID + ", ";
        } else {
            Q2 = "UPDATE Basic_ActResults SET isCheck='0' WHERE ActResultID='" + lList.get(position).ActResultsID + "'";
            frag.IdsToSend = frag.IdsToSend.replace(lList.get(position).ActResultsID + ", ", "") ;
        }
        String Q = "UPDATE Basic_Acts SET isCheck='" + (isCheck ? "1" : "0") + "' WHERE ActID='" + lList.get(position).ActID + "'";
        SQL.Execute(Q);
        SQL.Execute(Q2);
        lList.get(position).isCheck = (isCheck);
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linMainTitle;
        public LinearLayout linSubTitle;
        public RelativeLayout linTitle;
        public TextView lblMainTitle;
        public TextView lblSubTitle;
        public TextView lblTitle;
        public AppCompatCheckBox ch;

        public AdapterMember(View itemView) {
            super(itemView);
            linMainTitle = itemView.findViewById(R.id.linMainTitle);
            linSubTitle = itemView.findViewById(R.id.linSubTitle);
            linTitle = itemView.findViewById(R.id.linTitle);
            lblMainTitle = itemView.findViewById(R.id.lblMainTitle);
            lblSubTitle = itemView.findViewById(R.id.lblSubTitle);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            ch = itemView.findViewById(R.id.ch);
        }

    }

}
