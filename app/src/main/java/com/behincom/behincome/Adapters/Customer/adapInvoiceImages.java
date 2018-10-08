package com.behincom.behincome.Adapters.Customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.behincom.behincome.Datas.Activityes.InvoiceImage;
import com.behincom.behincome.Datas.Customer.CustomerImages;
import com.behincom.behincome.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import static com.behincom.behincome.WebRequest.Retrofite.BASE;

public class adapInvoiceImages extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<InvoiceImage> lList = new ArrayList<>();

    public adapInvoiceImages(Context mCOntext, List<InvoiceImage> mList) {
        mInflater = LayoutInflater.from(mCOntext);
        mContext = mCOntext;
        lList = mList;
    }

    public int getCount() {
        return lList.size();
    }

    public Object getItem(int position) {
        return lList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final AdapterMember Members;
        if (convertView == null) {
            Members = new AdapterMember();
            convertView = mInflater.inflate(R.layout.items_customer_gallery, parent, false);
            Members.img = convertView.findViewById(R.id.img);

            convertView.setTag(Members);
        } else {
            Members = (AdapterMember) convertView.getTag();
        }

        String PhotoURL = "";
        try {
            PhotoURL = lList.get(position).ImageFilename;
            if (PhotoURL.length() > 5)
                PhotoURL = BASE + "Uploads/InvoiceImages/" + PhotoURL;
            else
                PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.logo).toString();
        } catch (Exception e) {
            PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.logo).toString();
        }
        Glide.with(mContext).load(PhotoURL).asBitmap().centerCrop().into(new BitmapImageViewTarget(Members.img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                Members.img.setImageDrawable(circularBitmapDrawable);
            }
        });

        return convertView;
    }

    class AdapterMember {
        ImageView img;
    }

}