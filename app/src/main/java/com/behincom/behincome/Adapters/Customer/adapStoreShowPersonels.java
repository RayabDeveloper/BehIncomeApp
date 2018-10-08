package com.behincom.behincome.Adapters.Customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Datas.Customer.CustomerPersonnel;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;
import java.util.Random;

public class adapStoreShowPersonels extends RecyclerView.Adapter<adapStoreShowPersonels.AdapterMember>{

    private Context context;
    private RSQLite SQL = new RSQLite();

    public List<CustomerPersonnel> lList;
    public adapStoreShowPersonels(List<CustomerPersonnel> lList, Context mContext){
        this.lList = lList;
        this.context = mContext;
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
                .inflate(R.layout.items_store_show_personels, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final CardView cardViewMain = holder.cardViewMain;
        final TextView lblName = holder.lblName;
        final TextView lblNumber = holder.lblNumber;
        final ImageView imgCallLogo = holder.imgCallLogo;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblName.setTypeface(tf);
//        lblNumber.setTypeface(tf);

        lblName.setText(lList.get(position).Name);
        lblNumber.setText(lList.get(position).ContactInfo);
        imgCallLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", lList.get(position).ContactInfo, null));
                context.startActivity(intent);
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public CardView cardViewMain;
        public TextView lblName;
        public TextView lblNumber;
        public ImageView imgCallLogo;

        public AdapterMember(View itemView){
            super(itemView);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lblName = itemView.findViewById(R.id.lblName);
            lblNumber = itemView.findViewById(R.id.lblNumber);
            imgCallLogo = itemView.findViewById(R.id.imgCallLogo);
        }

    }

}
