package com.behincom.behincome.Activityes.Main.Message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Message.Message_Conversation;
import com.behincom.behincome.Datas.Message.Message_Message;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class adapMessageSub extends RecyclerView.Adapter<adapMessageSub.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Message_Message> lList;
    public adapMessageSub(List<Message_Message> lList, Context mContext) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_message_sub, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final LinearLayout linBG = holder.linBG;
        final ImageView imgFile = holder.imgFile;
        final TextView lblText = holder.lblText;
        final TextView lblSendDate = holder.lblSendDate;

        lblText.setText(lList.get(position).MessageText);
        String SendDate = lList.get(position).MessageSendDate;
        DateConverter DC = new DateConverter();
        SendDate = DC.getToHijri(SendDate);
        lblSendDate.setText(SendDate);

        Glide.with(context).load(lList.get(position).MessageFilePath).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgFile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgFile.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linBG;
        public ImageView imgFile;
        public TextView lblText;
        public TextView lblSendDate;

        public AdapterMember(View itemView) {
            super(itemView);
            linBG = itemView.findViewById(R.id.linBG);
            imgFile = itemView.findViewById(R.id.imgFile);
            lblText = itemView.findViewById(R.id.lblText);
            lblSendDate = itemView.findViewById(R.id.lblSendDate);
        }

    }

}