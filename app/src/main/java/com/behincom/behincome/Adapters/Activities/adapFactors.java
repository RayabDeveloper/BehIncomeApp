package com.behincom.behincome.Adapters.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Activities.actActivities;
import com.behincom.behincome.Activityes.Activities.fragAddFactor;
import com.behincom.behincome.Activityes.Activities.fragAddTask;
import com.behincom.behincome.Datas.Activityes.Invoice;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingProducts;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class adapFactors extends RecyclerView.Adapter<adapFactors.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();
    Dialog piDialog;
    actActivities act = new actActivities();

    public List<Invoice> lList;
    public adapFactors(List<Invoice> lList, Context context){
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
                .inflate(R.layout.items_factors, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        TextView lblNumber = holder.lblNumber;
        TextView lblProduct = holder.lblProduct;
        TextView lblPrice = holder.lblPrice;
        CardView cardViewMain = holder.cardViewMain;

        lblNumber.setText(lList.get(position).InvoiceNumber);
        RSQLGeter geter = new RSQLGeter();
        List<MarketingProducts> lProduct = geter.getList(MarketingProducts.class, "WHERE MarketingProductID='" + lList.get(position).InvoiceMarketingProductID + "'");
        if(lProduct.size() > 0)
            lblProduct.setText(lProduct.get(0).MarketingProductTitle);
        else
            lblProduct.setText("خطا");

        String Price = Integer.toString(lList.get(position).InvoicePrice).replace(".", "");
        try {
            String value = Price.replace(",", "");
            String reverseValue = new StringBuilder(value).reverse().toString();
            StringBuilder finalValue = new StringBuilder();
            for (int i = 1; i <= reverseValue.length(); i++) {
                char val = reverseValue.charAt(i - 1);
                finalValue.append(val);
                if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                    finalValue.append(",");
                }
            }
            Price = finalValue.reverse().toString();
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        lblPrice.setText(Price);

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragAddFactor.lFactorImage = lList.get(position).pic();//todo todo todo
                fragAddFactor.name = lList.get(position).InvoiceNumber;
                String mPricer = "";
                try {
                    String value = Double.toString(lList.get(position).InvoicePrice).replace(".", "");
                    String reverseValue = new StringBuilder(value).reverse().toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(",");
                        }
                    }
                    mPricer = finalValue.reverse().toString();
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                fragAddFactor.price = mPricer;
                fragAddFactor.des = lList.get(position).InvoiceDescription;
                fragAddFactor.ProductId = lList.get(position).InvoiceMarketingProductID;
                fragAddFactor.position = position;
                fragAddFactor.toEdit = true;//todo factor todo



//                fragAddFactor.Name = fragAddTask.Namee;
//                fragAddFactor.Details = fragAddTask.Details;
//                fragAddFactor.enterTime = fragAddTask.lblTimeCondition.getText().toString();
//                fragAddFactor.spin1 = fragAddTask.ActSelected;
//                fragAddFactor.spin2 = fragAddTask.ResultSelected;
//                fragAddFactor.currentId = fragAddTask.currentId;
//                fragAddFactor.lFactores = fragAddTask.lFactor;
//                fragAddFactor.ActType = fragAddTask.Type;



                act.getFragByState(FragmentState.AddFactor);
            }
        });
        cardViewMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                piDialog = new Dialog(context);
                piDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                piDialog.setCancelable(true);
                piDialog.setCanceledOnTouchOutside(true);
                piDialog.setContentView(R.layout.dialog_accept_canncell);
                Objects.requireNonNull(piDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                TextView Title = piDialog.findViewById(R.id.lblTitle);
                TextView lblCancell = piDialog.findViewById(R.id.lblCancell);
                TextView lblAccept = piDialog.findViewById(R.id.lblAccept);

//                Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//                Title.setTypeface(tf);lblCancell.setTypeface(tf);lblAccept.setTypeface(tf);

                lblCancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        piDialog.dismiss();
                    }
                });
                lblAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        piDialog.show();
//                        fragAddTask.lFactor.remove(position);
                        notifyDataSetChanged();
                        piDialog.dismiss();
                    }
                });
                return false;
            }
        });

    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public TextView lblNumber;
        public TextView lblProduct;
        public TextView lblPrice;
        public CardView cardViewMain;

        public AdapterMember(View itemView){
            super(itemView);
            lblNumber = itemView.findViewById(R.id.lblNumber);
            lblProduct = itemView.findViewById(R.id.lblProduct);
            lblPrice = itemView.findViewById(R.id.lblPrice);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
        }

    }

}
