package com.behincom.behincome.Adapters.Activities;

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
import android.widget.Toast;

import com.behincom.behincome.Activityes.Activities.fragAddTasks;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;
import java.util.Random;

public class adapGroupTask extends RecyclerView.Adapter<adapGroupTask.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Customers> lList;

    public adapGroupTask(List<Customers> lList, Context context) {
        this.lList = lList;
        this.context = context;
    }

    public void setData(List<Customers> lList) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_store_archive, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final CardView cardViewMain = holder.cardViewMain;
        final TextView lblName = holder.lblName;
        final LinearLayout imgCheck = holder.imgCheck;
        final LinearLayout lin = holder.lin;
        final TextView lblCountNumber = holder.lblCountNumber;
        final TextView lblManagementName = holder.lblManagementName;
        final TextView lblManagementName2 = holder.lblManagementName2;
        final TextView lblLocation = holder.lblLocation;
        final TextView lblCondition = holder.lblCondition;
        final TextView lblCondition2 = holder.lblCondition2;
        final TextView lblLastCondition = holder.lblLastCondition;
        final TextView lblLastCondition2 = holder.lblLastCondition2;

//        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblCountNumber.setTypeface(tFace);
//        lblName.setTypeface(tFace);
//        lblManagementName.setTypeface(tFace);
//        lblManagementName2.setTypeface(tFace);
//        lblLocation.setTypeface(tFace);
//        lblCondition.setTypeface(tFace);
//        lblCondition2.setTypeface(tFace);
//        lblLastCondition.setTypeface(tFace);
//        lblLastCondition2.setTypeface(tFace);

        lblName.setText(lList.get(position).CustomerName);
        try {
            lblManagementName2.setText(lList.get(position).Customers_Personnel.get(0).Name);
        }catch (Exception Ex) {
            lblManagementName2.setText("مدیر مربوطه");
        }
        String LocAdd = lList.get(position).CustomerAddress;
        if (LocAdd.length() > 30) LocAdd = LocAdd.substring(0, 30) + "...";
        lblLocation.setText(LocAdd);
        lblCondition2.setText("وارد نشده");
        lblLastCondition2.setText("وارد نشده");

        if (!lList.get(position).isCheck) {
            imgCheck.setVisibility(View.GONE);
            lin.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
        } else {
            imgCheck.setVisibility(View.VISIBLE);
            lin.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
            lblCountNumber.setText(Integer.toString(lList.get(position).count_number));
        }

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lList.get(position).isCheck) {
                    if (fragAddTasks.CurrentCountNumber < fragAddTasks.MaxCount) {
                        imgCheck.setVisibility(View.VISIBLE);
                        lin.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
                        fragAddTasks.CurrentCountNumber++;
                        lblCountNumber.setText(Integer.toString(fragAddTasks.CurrentCountNumber));
                        lList.get(position).count_number = (fragAddTasks.CurrentCountNumber);
                        lList.get(position).isCheck = (true);
                        fragAddTasks act = new fragAddTasks();
                        act.setList(lList);
                    } else {
                        Toast.makeText(context, "نمیتوانید بیشتر از حد مجاز فروشگاه انتخاب کنید", Toast.LENGTH_LONG).show();
                    }
                } else {
                    imgCheck.setVisibility(View.GONE);
                    lin.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
                    lList.get(position).isCheck = (false);

                    int cCount = lList.get(position).count_number;
                    fragAddTasks.CurrentCountNumber--;
                    for (Customers data : lList) {
                        if (data.isCheck) {
                            if (data.count_number > cCount)
                                data.count_number = (data.count_number - 1);
                        }
                    }
                    lList.get(position).count_number = (fragAddTasks.CurrentCountNumber);
                    notifyDataSetChanged();
                    fragAddTasks act = new fragAddTasks();
                    act.setList(lList);
                }
            }
        });

    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public CardView cardViewMain;
        public LinearLayout imgCheck;
        public LinearLayout lin;
        public TextView lblName;
        public TextView lblCountNumber;
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
            lblName = itemView.findViewById(R.id.lblName);
            lin = itemView.findViewById(R.id.lin);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            lblCountNumber = itemView.findViewById(R.id.lblCountNumber);
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
