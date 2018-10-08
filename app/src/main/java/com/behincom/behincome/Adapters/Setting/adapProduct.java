package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.Pdoructs.fragAddProducts;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Datas.BaseData.Basic_CommissionTypes;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommissions;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

public class adapProduct extends RecyclerView.Adapter<adapProduct.AdapterMember>{

    Context context;
    int mSwitcher;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<MarketingProducts> lList;
    public adapProduct(List<MarketingProducts> lList){
        this.lList = lList;
        this.mSwitcher = mSwitcher;
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
                .inflate(R.layout.items_setting_product_commission, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        CardView cardViewMain = holder.cardViewMain;
        final TextView lblCommission = holder.lblCommission;
        TextView lblName = holder.lblName;
        TextView lblName1 = holder.lblName1;
        TextView lblCommissionType = holder.lblCommissionType;
        TextView lblCommissionType1 = holder.lblCommissionType1;
        RelativeLayout btnDown = holder.btnDown;
        final TextView lblCommissions = holder.lblCommissions;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblName.setTypeface(tf);
//        lblName1.setTypeface(tf);
//        lblCommissionType.setTypeface(tf);
//        lblCommissionType1.setTypeface(tf);
//        lblDescription.setTypeface(tf);
//        lblDescription1.setTypeface(tf);

        List<Basic_CommissionTypes> lBaseData = geter.getList(Basic_CommissionTypes.class, "WHERE CommissionTypeID='" + lList.get(position).CommissionTypeID + "'");
        String Commission = "انتخاب نشده";
        if(lBaseData.size() > 0)
            Commission = lBaseData.get(0).CommissionTypeTitle;

        lblName1.setText(lList.get(position).MarketingProductTitle);
        lblCommissionType1.setText(Commission);
//        lblDescription1.setText((lList.get(position).MarketingProductDescription != null ? lList.get(position).MarketingProductDescription : ""));

        String ComisionS = "";
        List<MarketingProductCommissions> lComision = geter.getList(MarketingProductCommissions.class, "WHERE MarketingProductID='" + lList.get(position).MarketingProductID + "'");
        for (MarketingProductCommissions data : lComision) {
            ComisionS += "از " + data.CommissionPriceFrom + " تومان تا " + data.CommissionPriceTo + " تومان " + data.CommissionPercent + " درصد.<br>";
        }
        if(lComision.size() > 0) {
            try {
                ComisionS = Commission.substring(0, ComisionS.length() - 4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lblCommission.setText(Html.fromHtml(ComisionS));

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddProducts.mData = lList.get(position);
                fragAddProducts.lCommission = geter.getList(MarketingProductCommissions.class, "WHERE MarketingProductID='" + lList.get(position).MarketingProductID + "'");
                fragAddProducts.toEdit = true;
                actSetting act = new actSetting();
                act.getFragByState(FragmentState.AddProducts);
            }
        });
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lblCommission.getVisibility() == View.GONE){
                    lblCommission.setVisibility(View.VISIBLE);
                }else{
                    lblCommission.setVisibility(View.GONE);
                }
            }
        });
    }
    public static class AdapterMember extends RecyclerView.ViewHolder {

        public CardView cardViewMain;
        public TextView lblCommission;
        public TextView lblName;
        public TextView lblName1;
        public TextView lblCommissionType;
        public TextView lblCommissionType1;
        public TextView lblDescription;
        public TextView lblCommissions;
        public RelativeLayout btnDown;

        public AdapterMember(View itemView){
            super(itemView);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lblCommission = itemView.findViewById(R.id.lblCommission);
            lblName = itemView.findViewById(R.id.lblName);
            lblName1 = itemView.findViewById(R.id.lblName1);
            lblCommissionType = itemView.findViewById(R.id.lblCommissionType);
            lblCommissionType1 = itemView.findViewById(R.id.lblCommissionType1);
            lblDescription = itemView.findViewById(R.id.lblDescription);
            lblCommissions = itemView.findViewById(R.id.lblCommissions);
            btnDown = itemView.findViewById(R.id.btnDown);
        }

    }
}
