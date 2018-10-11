package com.behincom.behincome.Adapters.Customer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragAddTask;
import com.behincom.behincome.Activityes.Customer.fragCustomerShow;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.BaseData.Basic_ActResults;
import com.behincom.behincome.Datas.BaseData.Basic_Acts;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingActResults;
import com.behincom.behincome.Datas.Marketing.MarketingActivityFields;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class adapStoreShowAct extends RecyclerView.Adapter<adapStoreShowAct.AdapterMember> {

    private Context context;
    private static adapCustomerInvoice adapInvoice;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    static actActivities act = new actActivities();

    public List<Activities> lList;

    public adapStoreShowAct(List<Activities> lList, Context mContext) {
        this.lList = lList;
        this.context = mContext;
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
                .inflate(R.layout.items_actions, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final CardView cardViewMain = holder.cardViewMain;
        final TextView lblActionType1 = holder.lblActionType1;
        final TextView lblActionType2 = holder.lblActionType2;
        final TextView lblName = holder.lblName;
        final TextView lblDateTime = holder.lblDateTime;
        final TextView lblResult1 = holder.lblResult1;
        final TextView lblResult2 = holder.lblResult2;
        final TextView lblDateResult1 = holder.lblDateResult1;
        final TextView lblDateResult2 = holder.lblDateResult2;
        final TextView lblDetails1 = holder.lblDetails1;
        final TextView lblDetail2 = holder.lblDetail2;
        final ImageView imgFactor = holder.imgFactor;
        final ImageView imgReplayTo = holder.imgReplayTo;

        String EnterTime = "";
        if (lList.get(position).ExitDate != null)
            EnterTime = lList.get(position).ExitDate;
        String ExitTime = "";
        if (lList.get(position).ExitDate != null)
            ExitTime = lList.get(position).ExitDate;
        String SubmitDate = "هنوز ثبت نشده";
        if (EnterTime.length() > 0) {
            DateConverter DC1 = new DateConverter(EnterTime, true);
            SubmitDate = DC1.getShortDateTimeToMiladi();
        }
        String RunDate = "هنوز انجام نشده";
        if (ExitTime.length() > 0) {
            DateConverter DC2 = new DateConverter(ExitTime, true);
            RunDate = DC2.getShortDateTimeToMiladi();
        }

        List<Basic_Acts> lAct = new ArrayList<>();
        final List<Basic_ActResults> lActResult = geter.getList(Basic_ActResults.class, "WHERE ActResultID='" + lList.get(position).ActivityResultID + "'");
        if (lActResult.size() > 0) {
            lAct = geter.getList(Basic_Acts.class, "WHERE ActID='" + lActResult.get(0).ActID + "'");
        }

        String ActName = "منسوخ";
        if (lAct.size() > 0)
            ActName = lAct.get(0).ActTitle;
        String Result = "هنوز نتیجه ای وارد نشده";
        if (lActResult.size() > 0)
            Result = lActResult.get(0).ActResultTitle;
        String Details = "توضیحاتی وارد نشده";
        try {
            if (lList.get(position).ActivityDescription.length() > 0)
                Details = lList.get(position).ActivityDescription;
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }

        lblActionType2.setText(ActName);
        lblName.setText(lList.get(position).Title);
        lblDateTime.setText(SubmitDate);
        lblResult2.setText(Result);
        lblDateResult2.setText(RunDate);
        lblDetail2.setText(Details);

        if (lList.get(position).Invoice.size() > 0)
            imgFactor.setVisibility(View.VISIBLE);
        else
            imgFactor.setVisibility(View.GONE);

        imgReplayTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actActivities.class);
                actActivities.STATE = FragmentState.AddTask;
                fragAddTask.Name = fragCustomerShow.Customer.Customers.CustomerName;
                fragAddTask.customer_id = fragCustomerShow.Customer.Customers.CustomerID;
                fragAddTask.Type = 0;
                fragAddTask.mParentID = lList.get(position).ActivityID;
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        imgFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragCustomerShow ff = new fragCustomerShow();
                ff.ShowInvoices(lList.get(position).Invoice);
            }
        });
        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, actActivities.class);
                actActivities.STATE = FragmentState.AddTask;
                fragAddTask.currentId = lList.get(position).ActivityID;
                fragAddTask.lFactor = lList.get(position).Invoice;
                fragAddTask.lData = lList.get(position);
                fragAddTask.Namee = lList.get(position).Title;
                fragAddTask.Details = lList.get(position).ActivityDescription;
                if (lList.get(position).ExitDate != null) {
                    if (lList.get(position).ExitDate.length() > 5) {
                        String[] Timer = lList.get(position).ExitDate.split("T");
                        String[] Times = Timer[1].split(":");
                        fragAddTask.enterTime = Times[0] + ":" + Times[1];
                    } else {
                        String[] Timer = lList.get(position).EnterDate.split("T");
                        String[] Times = Timer[1].split(":");
                        fragAddTask.enterTime = Times[0] + ":" + Times[1];
                    }
                    fragAddTask.Type = 2;//todo todo todo todo Check This
                } else {
                    if (lList.get(position).EnterDate != null) {
                        if (lList.get(position).EnterDate.length() > 5) {
                            String[] Timer = lList.get(position).EnterDate.split("T");
                            String[] Times = Timer[1].split(":");
                            fragAddTask.enterTime = Times[0] + ":" + Times[1];
                        } else {
                            Toast.makeText(context, "Fail 101", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Fail 101", Toast.LENGTH_SHORT).show();
                    }
                    fragAddTask.Type = 1;//todo todo todo todo Check This
                }
                int ActID = 0;
                int ActResult = 0;
                List<Basic_Acts> lActs = new ArrayList<>();
                List<Basic_ActResults> lActResults = new ArrayList<>();
                List<Basic_Acts> lmActs = new ArrayList<>();
                List<Basic_ActResults> lmActResults = new ArrayList<>();
                lActs = geter.getList(Basic_Acts.class, "WHERE ActID='" + lList.get(position).ActID + "'");
                if (lList.get(position).ActivityResultID > 0) {
                    lActResults = geter.getList(Basic_ActResults.class, "WHERE ActResultID='" + lList.get(position).ActivityResultID + "'");
                }

//                lmActs = geter.getListIsCheck(Basic_Acts.class);
                if (lActResults.size() > 0) {
                    lmActResults = geter.getListIsCheck(Basic_ActResults.class);
                    for (Basic_ActResults data : lmActResults) {
                        List<Basic_Acts> lFild = geter.getList(Basic_Acts.class, "WHERE ActID='" + data.ActID + "'");
                        for (Basic_Acts mData : lFild) {
                            lmActs.add(mData);
                        }
                    }
                    try {
                        for (int i = 0; i < lmActs.size(); i++) {
                            try {
                                int ID = lmActs.get(i).ActID;
                                for (int j = i + 1; j < lmActs.size(); j++) {
                                    try {
                                        if (ID == lmActs.get(j).ActID) {
                                            lmActs.remove(j);
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

                boolean isAct = false, isActResult = false;
                int iAct = 0, iResult = 0;
                for (int i = 0; i < lmActs.size(); i++) {
                    if (lActs.get(0).ActID == lmActs.get(i).ActID) {
                        isAct = true;
                        iAct = i + 1;
                    }
                }
                for (int i = 0; i < lmActResults.size(); i++) {
                    if (lActResults.get(0).ActResultID == lmActResults.get(i).ActResultID) {
                        isActResult = true;
                        iResult = i + 1;
                    }
                }
                if (isAct)
                    fragAddTask.spin1 = iAct;
                else
                    fragAddTask.spin1 = 0;
                if (isActResult)
                    fragAddTask.spin2 = iResult;
                else
                    fragAddTask.spin2 = 0;
                fragAddTask.fac = true;
                ((Activity) context).startActivity(intent);
            }
        });

    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public CardView cardViewMain;
        public TextView lblActionType1;
        public TextView lblActionType2;
        public TextView lblName;
        public TextView lblDateTime;
        public TextView lblResult1;
        public TextView lblResult2;
        public TextView lblDateResult1;
        public TextView lblDateResult2;
        public TextView lblDetails1;
        public TextView lblDetail2;
        public ImageView imgFactor;
        public ImageView imgReplayTo;

        public AdapterMember(View itemView) {
            super(itemView);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lblActionType1 = itemView.findViewById(R.id.lblActionType1);
            lblActionType2 = itemView.findViewById(R.id.lblActionType2);
            lblName = itemView.findViewById(R.id.lblName);
            lblDateTime = itemView.findViewById(R.id.lblDateTime);
            imgFactor = itemView.findViewById(R.id.imgFactor);
            imgReplayTo = itemView.findViewById(R.id.imgReplayTo);
            lblResult1 = itemView.findViewById(R.id.lblResult1);
            lblResult2 = itemView.findViewById(R.id.lblResult2);
            lblDateResult1 = itemView.findViewById(R.id.lblDateResult1);
            lblDateResult2 = itemView.findViewById(R.id.lblDateResult2);
            lblDetails1 = itemView.findViewById(R.id.lblDetails1);
            lblDetail2 = itemView.findViewById(R.id.lblDetail2);
        }

    }

}
