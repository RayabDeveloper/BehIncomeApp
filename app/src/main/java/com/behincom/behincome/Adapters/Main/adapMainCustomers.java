package com.behincom.behincome.Adapters.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Customer.actCustomer;
import com.behincom.behincome.Activityes.Customer.fragCustomerShow;
import com.behincom.behincome.Activityes.Main.fragCustomers;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStatus;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.app.AppController;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.devsmart.android.ui.HorizontalListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.behincom.behincome.WebRequest.Retrofite.BASE;
import static com.behincom.behincome.WebRequest.Retrofite.BASE_URL;

public class adapMainCustomers extends RecyclerView.Adapter<adapMainCustomers.AdapterMember> {

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Customers> lList;
    public static boolean Selectable = false;

    public adapMainCustomers(List<Customers> lList, Context mContext) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_stores, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final CardView cardViewMain = holder.cardViewMain;
        final LinearLayout lin = holder.lin;
        final LinearLayout lin2 = holder.lin2;
        TextView lblName = holder.lblName;
        final ImageView imgCondition = holder.imgCondition;
        final ImageView imgLogo = holder.imgLogo;
        TextView lbltaskCount = holder.lbltaskCount;
        TextView lblAssignCount = holder.lblAssignCount;
        TextView lblLocation = holder.lblLocation;
        HorizontalListView lstAccess = holder.lstAccess;

        lblName.setText(lList.get(position).CustomerName);
        String LocAdd = lList.get(position).CustomerAddress;
        if (LocAdd.length() > 50) LocAdd = LocAdd.substring(0, 50) + "...";
        lblLocation.setText(LocAdd);
        if (lList.get(position).isCheck) {
            lin.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
            lin2.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
        } else {
            lin.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
            lin2.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
        }
        int sID = lList.get(position).CustomerStateID;
        List<Basic_CustomerStates> lState = geter.getList(Basic_CustomerStates.class, "WHERE CustomerStateID='" + sID + "'");
        if (lState.size() > 0) {
            ByteArrayOutputStream stream = null;
            try {
                Bitmap bmp=Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
                Canvas canvas=new Canvas(bmp);
                canvas.drawColor(Color.parseColor("#" + lState.get(0).CustomerStateColor));
                stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Glide.with(context).load(stream.toByteArray()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgCondition) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imgCondition.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            Glide.with(context).load(lList.get(position).CustomerStateID).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgCondition) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imgCondition.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
        String PhotoURL = "";
        try {
            PhotoURL = lList.get(position).Customers_Images.get(0).ImageFilename;
            if (PhotoURL.length() > 5)
                PhotoURL = BASE + "Uploads/CustomerImages/" + PhotoURL;
            else
                PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.customer_default_null_icon).toString();
        } catch (Exception e) {
            PhotoURL = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.customer_default_null_icon).toString();
        }
        Glide.with(context).load(PhotoURL).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgLogo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgLogo.setImageDrawable(circularBitmapDrawable);
            }
        });

        cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Selectable) {
                    fragCustomerShow.Customer = lList.get(position);
                    fragCustomerShow.position = position;
                    Intent intent = new Intent(context, actCustomer.class);
                    actCustomer.STATE = FragmentState.CustomerShow;
                    context.startActivity(intent);
                } else {
                    if (lList.get(position).isCheck) {
                        lin.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
                        lin2.setBackgroundColor(context.getResources().getColor(R.color.txtWhite));
                        lList.get(position).isCheck = false;
                    } else {
                        lin.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
                        lin2.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
                        lList.get(position).isCheck = true;
                    }
                }
            }
        });
        cardViewMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!Selectable) {
                    Selectable = true;
                    fragCustomers frag = new fragCustomers();
                    frag.ShowStatusBar();
                    lin.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
                    lin2.setBackgroundColor(context.getResources().getColor(R.color.txtGray1));
                    lList.get(position).isCheck = true;
                }
                return true;
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout lin;
        public LinearLayout lin2;
        public CardView cardViewMain;
        public TextView lblName;
        public ImageView imgCondition;
        public ImageView imgLogo;
        public TextView lbltaskCount;
        public TextView lblAssignCount;
        public TextView lblLocation;
        public HorizontalListView lstAccess;

        public AdapterMember(View itemView) {
            super(itemView);
            lin = itemView.findViewById(R.id.lin);
            lin2 = itemView.findViewById(R.id.lin2);
            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lblName = itemView.findViewById(R.id.lblName);
            imgCondition = itemView.findViewById(R.id.imgCondition);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            lbltaskCount = itemView.findViewById(R.id.lbltaskCount);
            lblAssignCount = itemView.findViewById(R.id.lblAssignCount);
            lblLocation = itemView.findViewById(R.id.lblLocation);
            lstAccess = itemView.findViewById(R.id.lstAccess);
        }

    }

}
