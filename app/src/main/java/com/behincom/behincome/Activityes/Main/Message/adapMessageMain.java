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
import android.widget.Toast;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Activityes.Main.Package.adapPaymentPackage;
import com.behincom.behincome.Activityes.Main.actMain;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Message.Message_Conversation;
import com.behincom.behincome.Datas.Package.Payment_Packages;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class adapMessageMain extends RecyclerView.Adapter<adapMessageMain.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Message_Conversation> lList;

    public adapMessageMain(List<Message_Conversation> lList, Context mContext) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_message_main, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final LinearLayout linBG = holder.linBG;
        final ImageView imgSenderLogo = holder.imgSenderLogo;
        final ImageView imgIsSystem = holder.imgIsSystem;
        final TextView lblSenderName = holder.lblSenderName;
        final TextView lblLastMessageText = holder.lblLastMessageText;

        lblSenderName.setText(lList.get(position).ConversationSenderName);
        String Text = "";
        if(lList.get(position).Message_Message.size() > 0){
            Text = lList.get(position).Message_Message.get(lList.get(position).Message_Message.size() - 1).MessageText;
            if(Text.length() > 30)
                Text = Text.substring(0, 30) + "...";
        }
        lblLastMessageText.setText(Text);
        if(lList.get(position).ConversationIsSystem)
            imgIsSystem.setVisibility(View.VISIBLE);
        else
            imgIsSystem.setVisibility(View.GONE);

        Glide.with(context).load(lList.get(position).ConversationSenderLogoFilePath).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgSenderLogo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgSenderLogo.setImageDrawable(circularBitmapDrawable);
            }
        });
        linBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragMessageSub.lMessageSub = lList.get(position).Message_Message;
                fragMessageSub.SenderName = lList.get(position).ConversationSenderName;
                actMain act = new actMain();
                act.getFragByState(FragmentState.MessageSub);
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linBG;
        public ImageView imgSenderLogo;
        public ImageView imgIsSystem;
        public TextView lblSenderName;
        public TextView lblLastMessageText;

        public AdapterMember(View itemView) {
            super(itemView);
            linBG = itemView.findViewById(R.id.linBG);
            imgSenderLogo = itemView.findViewById(R.id.imgSenderLogo);
            imgIsSystem = itemView.findViewById(R.id.imgIsSystem);
            lblSenderName = itemView.findViewById(R.id.lblSenderName);
            lblLastMessageText = itemView.findViewById(R.id.lblLastMessageText);
        }

    }

}