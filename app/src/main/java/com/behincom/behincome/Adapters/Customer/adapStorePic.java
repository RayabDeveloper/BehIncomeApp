package com.behincom.behincome.Adapters.Customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.behincom.behincome.Accesories.BitmapCreator;
import com.behincom.behincome.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class adapStorePic extends BaseAdapter {

    Context mContext;
    BitmapCreator bmpCreator = new BitmapCreator();

    private LayoutInflater mInflater;
    public List<String> lList;
    int positioner = 0;

    public adapStorePic(Context context, List<String> lList){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.lList = lList;
        this.positioner = positioner;
    }

    @Override
    public int getCount(){
        return lList.size();
    }

    @Override
    public Object getItem(int position){
        return  null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    public void Clear(){
        try{
            lList.clear();
        }catch (Exception ignored){}
    }

    public static class AdapterMember{

        ImageView img;
    }

    AdapterMember Members = null;

    @Override
    public View getView(final int position, View ConvertView, final ViewGroup Parent){

        if(ConvertView == null){
            ConvertView                         = mInflater.inflate(R.layout.items_addstore_pic, Parent, false);
            Members                             = new AdapterMember();
            Members.img                         = ConvertView.findViewById(R.id.img);

            ConvertView.setTag(Members);
        }else{
            Members = (AdapterMember)ConvertView.getTag();
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        File imgFile = new  File(lList.get(position));
        if(imgFile.exists()){
            Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            bmp = bmpCreator.getResizedBitmap(bmp, 200, 200);
            bmp.compress(Bitmap.CompressFormat.PNG, 40, stream);
            Glide.with(mContext)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new BitmapImageViewTarget(Members.img) {});
        }
//        Members.img.setImageBitmap(lList.get(position));

        return ConvertView;

    }

}