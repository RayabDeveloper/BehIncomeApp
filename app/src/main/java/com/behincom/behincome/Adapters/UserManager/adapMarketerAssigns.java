package com.behincom.behincome.Adapters.UserManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Activityes.UserManager.actUserManager;
import com.behincom.behincome.Activityes.UserManager.fragAccess;
import com.behincom.behincome.Activityes.UserManager.fragCondition;
import com.behincom.behincome.Activityes.UserManager.fragRole;
import com.behincom.behincome.Activityes.UserManager.fragViewProfile;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Profile.Marketers;
import com.behincom.behincome.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;
import java.util.Objects;

public class adapMarketerAssigns extends RecyclerView.Adapter<adapMarketerAssigns.AdapterMember> {

    private Context context;
    private Dialog mDialog;
    private actUserManager act = new actUserManager();


    public List<Marketers> lList;

    public adapMarketerAssigns(List<Marketers> lList, Context mContext) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_marketer_assign, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final ImageView imgProfile = holder.imgProfile;
        TextView lblName = holder.lblName;
        TextView lblRole = holder.lblRole;
        TextView lblSubmitDate = holder.lblSubmitDate;
        final CardView cardViewMain = holder.cardViewMain;
        final LinearLayout badeAx = holder.badeAx;

//        Glide.with(context).load(lList.get(position).MarkPhotoFilenameeterName).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {});
        if(lList.get(position).MarkPhotoFilenameeterName != null) {
            if (lList.get(position).MarkPhotoFilenameeterName.length() > 5) {
                Glide.with(context).load(lList.get(position).MarkPhotoFilenameeterName).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imgProfile.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
                Glide.with(context).load(R.drawable.profile_pic_icon).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imgProfile.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        }else{
            Glide.with(context).load(R.drawable.profile_pic_icon).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imgProfile.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
        lblName.setText(lList.get(position).UserName);
//        lblRole.setText(lList.get(position).RoleName);todo todo todo
        lblRole.setText("فلان سمت");
        DateConverter DC = new DateConverter();
        lblSubmitDate.setText(DC.getToHijri(lList.get(position).SubmitDate));

        if (!lList.get(position).isCheck) {
            badeAx.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
        } else {
            badeAx.setBackgroundColor(context.getResources().getColor(R.color.txtGray0));
        }

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lList.get(position).isCheck) {
                    lList.get(position).isCheck = (true);
                    badeAx.setBackgroundColor(context.getResources().getColor(R.color.txtGray0));
                } else {
                    lList.get(position).isCheck = (false);
                    badeAx.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
                }
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        private ImageView imgProfile;
        private TextView lblName;
        private TextView lblRole;
        private TextView lblSubmitDate;
        private CardView cardViewMain;
        private LinearLayout badeAx;

        public AdapterMember(View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            lblName = itemView.findViewById(R.id.lblName);
            lblRole = itemView.findViewById(R.id.lblRole);
            lblSubmitDate = itemView.findViewById(R.id.lblSubmitDate);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            badeAx = itemView.findViewById(R.id.badeAx);
        }

    }
}
