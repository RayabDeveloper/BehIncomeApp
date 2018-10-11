package com.behincom.behincome.Adapters.Main;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Customer.actCustomer;
import com.behincom.behincome.Activityes.Customer.fragCustomerShow;
import com.behincom.behincome.Activityes.Main.fragCustomers;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.MarketerUserAccessProfile;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.behincom.behincome.WebRequest.Retrofite.BASE;
import static com.behincom.behincome.WebRequest.Retrofite.BASE_URL;

public class adapMainCustomerMarketers extends RecyclerView.Adapter<adapMainCustomerMarketers.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<MarketerUserAccessProfile> lList;
    public static boolean Selectable = false;

    public adapMainCustomerMarketers(List<MarketerUserAccessProfile> lList, Context mContext) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_main_customer_marketers, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final LinearLayout linBG = holder.linBG;
        final ImageView imgPhoto = holder.imgPhoto;
        TextView lblName = holder.lblName;

        lblName.setText(lList.get(position).Firstname + " " + lList.get(position).Lastname);
        Glide.with(context).load(Setting.getServerURL() + lList.get(position).PhotoFilename).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgPhoto) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });

        linBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragCustomers.closeView();
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linBG;
        public ImageView imgPhoto;
        public TextView lblName;

        public AdapterMember(View itemView) {
            super(itemView);
            linBG = itemView.findViewById(R.id.linBG);
            lblName = itemView.findViewById(R.id.lblName);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            lblName = itemView.findViewById(R.id.lblName);
        }

    }

}
