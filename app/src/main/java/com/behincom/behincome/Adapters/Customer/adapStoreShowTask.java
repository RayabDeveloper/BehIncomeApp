package com.behincom.behincome.Adapters.Customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;

public class adapStoreShowTask extends RecyclerView.Adapter<adapStoreShowTask.AdapterMember>{

    private Context context;
    private int mSwitcher;
    private RSQLite SQL = new RSQLite();

    public List<Activities> lList;
    public adapStoreShowTask(List<Activities> lList){
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
                .inflate(R.layout.items_store_show_task, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final CardView cardViewMain = holder.cardViewMain;
        final TextView lblDateName = holder.lblDateName;
//        final ImageView imgAlarmSet = holder.imgAlarmSet;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        lblDateName.setTypeface(tf);

        DateConverter DC = new DateConverter(lList.get(position).TodoDate, true);
        String TooLongDate = DC.getLongDateTimeToMiladi();

        lblDateName.setText(TooLongDate);

//        if(lList.get(position).hasReminder) {
//            Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_);
//            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Glide.with(context)
//                    .load(stream.toByteArray())
//                    .asBitmap()
//                    .fitCenter()
//                    .into(new BitmapImageViewTarget(imgAlarmSet) {});
//            lList.get(position).hasReminder=(false);
//        }else {
//            Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_selected);
//            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Glide.with(context)
//                    .load(stream.toByteArray())
//                    .asBitmap()
//                    .fitCenter()
//                    .into(new BitmapImageViewTarget(imgAlarmSet) {});
//            lList.get(position).hasReminder=(true);
//        }
//        imgAlarmSet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(lList.get(position).hasReminder) {
//                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_);
//                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    Glide.with(context)
//                            .load(stream.toByteArray())
//                            .asBitmap()
//                            .fitCenter()
//                            .into(new BitmapImageViewTarget(imgAlarmSet) {});
//                    lList.get(position).hasReminder=(false);
//                    actStoreShow.lTask.get(position).has_reminder(false);
//                    //todo Send To API
//                }else {
//                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.alarm_selected);
//                    Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    Glide.with(context)
//                            .load(stream.toByteArray())
//                            .asBitmap()
//                            .fitCenter()
//                            .into(new BitmapImageViewTarget(imgAlarmSet) {});
//                    lList.get(position).hasReminder=(true);
//                    actStoreShow.lTask.get(position).has_reminder(true);
//                    //todo Send To API
//                }
//            }
//        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public CardView cardViewMain;
        public TextView lblDateName;
//        public ImageView imgAlarmSet;

        public AdapterMember(View itemView){
            super(itemView);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lblDateName = itemView.findViewById(R.id.lblDateName);
//            imgAlarmSet = itemView.findViewById(R.id.imgAlarmSet);
        }

    }

}
