package com.behincom.behincome.Adapters.Archives;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;
import java.util.Random;

public class adapArchiveEnter extends RecyclerView.Adapter<adapArchiveEnter.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Customers> lList;

    public adapArchiveEnter(List<Customers> lList, Context context) {
        this.lList = lList;
        this.context = context;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_archive_check, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final CardView cardViewMain = holder.cardViewMain;
        final TextView lblName = holder.lblName;
        final LinearLayout imgCheck = holder.imgCheck;
        final LinearLayout lin = holder.lin;
        final TextView lblManagementName = holder.lblManagementName;
        final TextView lblManagementName2 = holder.lblManagementName2;
        final TextView lblLocation = holder.lblLocation;
        final TextView lblCondition = holder.lblCondition;
        final TextView lblCondition2 = holder.lblCondition2;
        final TextView lblLastCondition = holder.lblLastCondition;
        final TextView lblLastCondition2 = holder.lblLastCondition2;

        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblName.setTypeface(tFace);
        lblManagementName.setTypeface(tFace);
        lblManagementName2.setTypeface(tFace);
        lblLocation.setTypeface(tFace);
        lblCondition.setTypeface(tFace);
        lblCondition2.setTypeface(tFace);
        lblLastCondition.setTypeface(tFace);
        lblLastCondition2.setTypeface(tFace);

        lblName.setText(lList.get(position).CustomerName);
        lblManagementName2.setText(lList.get(position).Customers_Personnel.get(0).Name);
        String LocAdd = lList.get(position).CustomerAddress;
        if (LocAdd.length() > 30) LocAdd = LocAdd.substring(0, 30) + "...";
        lblLocation.setText(LocAdd);
        lblCondition2.setText("وارد نشده");
        lblLastCondition2.setText("وارد نشده");

        if (!lList.get(position).isCheck) {
//            imgCheck.setVisibility(View.GONE);
            lin.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
        } else {
//            imgCheck.setVisibility(View.VISIBLE);
            lin.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
        }

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lList.get(position).isCheck) {
//                    imgCheck.setVisibility(View.VISIBLE);
                    lList.get(position).isCheck = (true);
                    lin.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
                } else {
//                    imgCheck.setVisibility(View.GONE);
                    lList.get(position).isCheck = (false);
                    lin.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
                }
            }
        });

    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public CardView cardViewMain;
        public LinearLayout imgCheck;
        public LinearLayout lin;
        public TextView lblName;
        public TextView lblManagementName;
        public TextView lblManagementName2;
        public TextView lblLocation;
        public TextView lblCondition;
        public TextView lblCondition2;
        public TextView lblLastCondition;
        public TextView lblLastCondition2;

        public AdapterMember(View itemView) {
            super(itemView);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lin = itemView.findViewById(R.id.lin);
            lblName = itemView.findViewById(R.id.lblName);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            lblManagementName = itemView.findViewById(R.id.lblManagementName);
            lblManagementName2 = itemView.findViewById(R.id.lblManagementName2);
            lblLocation = itemView.findViewById(R.id.lblLocation);
            lblCondition = itemView.findViewById(R.id.lblCondition);
            lblCondition2 = itemView.findViewById(R.id.lblCondition2);
            lblLastCondition = itemView.findViewById(R.id.lblLastCondition);
            lblLastCondition2 = itemView.findViewById(R.id.lblLastCondition2);
        }

    }

}
