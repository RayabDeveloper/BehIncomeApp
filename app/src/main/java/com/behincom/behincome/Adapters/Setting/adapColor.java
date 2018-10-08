package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.CustomerState.fragCustomerState;
import com.behincom.behincome.Activityes.Setting.Pdoructs.fragAddProducts;
import com.behincom.behincome.Datas.BaseData.Basic_Color;
import com.behincom.behincome.Datas.Marketing.MarketingProductCommissions;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class adapColor extends RecyclerView.Adapter<adapColor.AdapterMember> {

    Context context;
    public List<Basic_Color> lList;
    private int ColorID = 0;
    public adapColor(List<Basic_Color> lList, Context mContext, int ColorID) {
        this.lList = lList;
        this.context = mContext;
        this.ColorID = ColorID;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_color, parent, false);



        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final ImageView imgColor = holder.imgColor;
        final LinearLayout linBG = holder.linBG;

        if(lList.get(position).ColorID == ColorID)
            linBG.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
        else
            linBG.setBackgroundColor(context.getResources().getColor(R.color.txtGray0));

        ByteArrayOutputStream stream = null;
        try {
            Bitmap bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            canvas.drawColor(Color.parseColor("#" + lList.get(position).ColorCode));
            stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgColor) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgColor.setImageDrawable(circularBitmapDrawable);
            }
        });

        imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorID = lList.get(position).ColorID;
                fragCustomerState.ColorID = ColorID;
                notifyDataSetChanged();
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public ImageView imgColor;
        public LinearLayout linBG;

        public AdapterMember(View itemView) {
            super(itemView);
            imgColor = itemView.findViewById(R.id.imgColor);
            linBG = itemView.findViewById(R.id.linBG);
        }

    }
}
