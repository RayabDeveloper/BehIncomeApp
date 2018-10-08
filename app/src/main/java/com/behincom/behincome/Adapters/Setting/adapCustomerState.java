package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.CustomerState.fragCustomerState;
import com.behincom.behincome.Adapters.SwipeItems.Customers.OnCustomerListChangedListener;
import com.behincom.behincome.Adapters.SwipeItems.Helper.ItemTouchHelperViewHolder;
import com.behincom.behincome.Adapters.SwipeItems.Helper.OnStartDragListener;
import com.behincom.behincome.Adapters.SwipeItems.SwipeAndDragHelper;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class adapCustomerState extends RecyclerView.Adapter<adapCustomerState.AdapterMemberGtoup> {

    Context context;
    private OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListener mListChangedListener;
    private static boolean moving = false;
    AlertDialog.Builder builder;
    private ItemTouchHelper touchHelper;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();

    public List<Basic_CustomerStates> lList;
    public adapCustomerState(List<Basic_CustomerStates> lList, Context context){
        this.lList = lList;
        this.context = context;
        builder = new AlertDialog.Builder(context);
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
    public AdapterMemberGtoup onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_setting_customer_state, parent, false);

        return new AdapterMemberGtoup(itemView);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMemberGtoup holder, final int position) {
        CardView cardViewMain = holder.cardViewMain;
        LinearLayout btnMove = holder.btnMove;
        TextView lblTitle = holder.lblTitle;
        final ImageView imgColor = holder.imgColor;

        lblTitle.setText(lList.get(position).CustomerStateTitle);
        try {
            ByteArrayOutputStream stream = null;
            try {
                Bitmap bmp=Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
                Canvas canvas=new Canvas(bmp);
                canvas.drawColor(Color.parseColor("#" + lList.get(position).CustomerStateColor));
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardViewMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fragCustomerState.onEditor(lList.get(position));
                return false;
            }
        });
    }
    public void MoveToDown(int position) {
        int NextPosition = position + 1;
        if(position != lList.size() - 1){
            Basic_CustomerStates itemA = lList.get(position);
            Basic_CustomerStates itemB = lList.get(NextPosition);
            lList.set(position, itemB);
            lList.set(NextPosition, itemA);

            notifyItemMoved(position, NextPosition);
        }
    }
    public void MoveToUp(int position) {
        int PreviusePosition = position - 1;
        if(position != 0){
            Basic_CustomerStates itemA = lList.get(position);
            Basic_CustomerStates itemB = lList.get(PreviusePosition);
            lList.set(position, itemB);
            lList.set(PreviusePosition, itemA);

            notifyItemMoved(position, PreviusePosition);
        }
    }
    public static class AdapterMemberGtoup extends RecyclerView.ViewHolder{

        public CardView cardViewMain;
        public LinearLayout btnMove;
        public TextView lblTitle;
        public ImageView imgColor;

        public AdapterMemberGtoup(View itemView){
            super(itemView);

            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            btnMove = itemView.findViewById(R.id.btnMove);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            imgColor = itemView.findViewById(R.id.imgColor);
        }

//        @Override
//        public void onItemSelected() {
//            //xitemView.setBath://valokafor.com/wp-admin/post.php?post=1804&action=edit#ckgroundColor(Color.LTGRAY);
//        }
//
//        @Override
//        public void onItemClear() {
//            itemView.setBackgroundColor(0);
//        }

    }
//    @Override
//    public void onViewMoved(int fromPosition, int toPosition) {
////        Datas.Services data = new Datas.Services();
////        data.id(lList.get(fromPosition).id());
////        data.title(lList.get(fromPosition).title());
////
////        lList.get(fromPosition).id(lList.get(toPosition).id());
////        lList.get(fromPosition).title(lList.get(toPosition).title());
////
////        lList.get(toPosition).id(data.id());
////        lList.get(toPosition).title(data.title());
////
////        notifyItemMoved(fromPosition, toPosition);
////        actGroups.getList(lList);
//
//        List<Basic_CustomerStates> lData = geter.getList(Basic_CustomerStates.class, " WHERE CustomerStateID='" + lList.get(fromPosition).CustomerStateID + "'");
//        Basic_CustomerStates lData2 = lList.get(toPosition);
//    }
//    @Override
//    public void onViewSwiped(int position) {
////        notifyItemRemoved(position);
//    }
//    public void setTouchHelper(ItemTouchHelper touchHelper) {
//        this.touchHelper = touchHelper;
//    }

}
