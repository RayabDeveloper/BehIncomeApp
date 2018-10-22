package com.behincom.behincome.Activityes.Main.Package;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Customer.actCustomer;
import com.behincom.behincome.Activityes.Customer.fragCustomerShow;
import com.behincom.behincome.Activityes.Main.fragCustomers;
import com.behincom.behincome.Adapters.Main.adapMainCustomers;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Package.Payment_Packages;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class adapPaymentPackage extends RecyclerView.Adapter<adapPaymentPackage.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Payment_Packages> lList;
    public static boolean Selectable = false;

    public adapPaymentPackage(List<Payment_Packages> lList, Context mContext) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_package, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final LinearLayout linBG = holder.linBG;
        final ImageView imgPKG = holder.imgPKG;
        final TextView lblTitle = holder.lblTitle;
        final TextView lblStart = holder.lblStart;
        final TextView lblEnd = holder.lblEnd;
        final TextView lblPrice = holder.lblPrice;

        String StartDate = lList.get(position).PaymentPakageStartDate,
                EndDate = lList.get(position).PaymentPakageEndDate;
        DateConverter DC = new DateConverter();
        StartDate = DC.getToHijri(StartDate);
        EndDate = DC.getToHijri(EndDate);

        lblTitle.setText(lList.get(position).PaymentPackagesName);
        lblStart.setText(StartDate);
        lblEnd.setText(EndDate);
        lblPrice.setText(lList.get(position).PaymentPackagePrice);

        Glide.with(context).load(lList.get(position).PaymentPackagesImagePath).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgPKG) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgPKG.setImageDrawable(circularBitmapDrawable);
            }
        });
        linBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, lList.get(position).PaymentPackagesName, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linBG;
        public ImageView imgPKG;
        public TextView lblTitle;
        public TextView lblStart;
        public TextView lblEnd;
        public TextView lblPrice;

        public AdapterMember(View itemView) {
            super(itemView);
            linBG = itemView.findViewById(R.id.linBG);
            imgPKG = itemView.findViewById(R.id.imgPKG);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            lblStart = itemView.findViewById(R.id.lblStart);
            lblEnd = itemView.findViewById(R.id.lblEnd);
            lblPrice = itemView.findViewById(R.id.lblPrice);
        }

    }

}