package com.behincom.behincome.Adapters.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.behincom.behincome.Accesories.BitmapCreator;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Random;

public class adapFactorPic extends RecyclerView.Adapter<adapFactorPic.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();
    BitmapCreator bmpCreator = new BitmapCreator();

    public List<String> lList;
    public adapFactorPic(List<String> lList, Context context){
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
                .inflate(R.layout.items_factor_pic, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final ImageView imgPic = holder.imgPic;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        File imgFile = new  File(lList.get(position));
        if(imgFile.exists()){
            Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            bmp = bmpCreator.getResizedBitmap(bmp, 200, 200);
            bmp.compress(Bitmap.CompressFormat.PNG, 40, stream);
            Glide.with(context)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new BitmapImageViewTarget(imgPic) {});
        }
    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public ImageView imgPic;

        public AdapterMember(View itemView){
            super(itemView);
            imgPic = itemView.findViewById(R.id.imgPic);
        }

    }

}
