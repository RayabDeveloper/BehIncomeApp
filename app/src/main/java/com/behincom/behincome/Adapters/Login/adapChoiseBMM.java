package com.behincom.behincome.Adapters.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Splash.actSplash;
import com.behincom.behincome.Datas.Profile.BussinessManagerMarketing;
import com.behincom.behincome.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;
import java.util.Random;

public class adapChoiseBMM extends RecyclerView.Adapter<adapChoiseBMM.AdapterMember> {

    Context context;

    public List<BussinessManagerMarketing> lList;

    public adapChoiseBMM(List<BussinessManagerMarketing> lList, Context context) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_choise_bmm, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        TextView lblName = holder.lblName;
        ImageView imgProfile = holder.imgProfile;
        CardView cardViewMain = holder.cardViewMain;

        lblName.setText(lList.get(position).CompanyName);
        Glide.with(context)
                .load(lList.get(position).LogoFilename)
                .asBitmap()
                .fitCenter()
                .into(new BitmapImageViewTarget(imgProfile) {});

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0) {
                    Setting.Save("BMMUserID", Integer.toString(Setting.getUserID()));
                    Setting.Save("BMMUserName", Setting.getUserName());
                }else{
                    Setting.Save("BMMUserID", Integer.toString(lList.get(position).UserID));
                    Setting.Save("BMMUserName", lList.get(position).CompanyName);
                }

                Intent intent = new Intent(context, actSplash.class);
                context.startActivity(intent);
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblName;
        public ImageView imgProfile;
        public CardView cardViewMain;

        public AdapterMember(View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
        }

    }

}
