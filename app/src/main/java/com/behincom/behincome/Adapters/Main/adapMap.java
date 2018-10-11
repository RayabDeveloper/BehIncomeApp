package com.behincom.behincome.Adapters.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Main.fragCustomersMap;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Random;

public class adapMap extends RecyclerView.Adapter<adapMap.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<MyCustomers> lList;
    public adapMap(List<MyCustomers> lList){
        this.lList = lList;
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
                .inflate(R.layout.items_store_map, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        TextView lblName = holder.lblName;
        TextView lblActivityField = holder.lblActivityField;
        TextView lblActivityField1 = holder.lblActivityField1;
        TextView lblAddress = holder.lblAddress;
        TextView lblAddress1 = holder.lblAddress1;
        CardView cardViewMain = holder.cardViewMain;

        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblName.setTypeface(tFace);
        lblActivityField.setTypeface(tFace);
        lblActivityField1.setTypeface(tFace);
        lblAddress.setTypeface(tFace);
        lblAddress1.setTypeface(tFace);

        List<Basic_NamePrefixes> lPrefixNames = geter.getList(Basic_NamePrefixes.class);
        String Prefix = "";
        String Name = "";
        if(lPrefixNames.size() > 0) {
            Prefix = lPrefixNames.get(0).NamePrefixTitle;
            Name = Prefix + " " + lList.get(position).Customers.CustomerName;
        }else{
            Name = lList.get(position).Customers.CustomerName;
        }
        lblName.setText(Name);
        List<Basic_ActivityFields> lField = geter.getList(Basic_ActivityFields.class, "WHERE id='" + lList.get(position).Customers.Customers_ActivityFields.get(0).ActivityFieldID + "'");
        String Field = "وارد نشده";
        if(lField.size() > 0){
            Field = lField.get(0).ActivityFieldTitle;
        }
        lblActivityField1.setText(Field);
        lblAddress1.setText(lList.get(position).Customers.CustomerAddress);

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragCustomersMap act = new fragCustomersMap();
                act.getLoc(new LatLng(lList.get(position).Customers.CustomerLatitude, lList.get(position).Customers.CustomerLongitude));
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public TextView lblName;
        public TextView lblActivityField;
        public TextView lblActivityField1;
        public TextView lblAddress;
        public TextView lblAddress1;
        public CardView cardViewMain;

        public AdapterMember(View itemView){
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            lblActivityField = itemView.findViewById(R.id.lblActivityField);
            lblActivityField1 = itemView.findViewById(R.id.lblActivityField1);
            lblAddress = itemView.findViewById(R.id.lblAddress);
            lblAddress1 = itemView.findViewById(R.id.lblAddress1);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
        }

    }

}
