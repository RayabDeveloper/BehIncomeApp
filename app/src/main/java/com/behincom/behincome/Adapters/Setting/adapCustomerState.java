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
import android.widget.Toast;

import com.behincom.behincome.Accesories.Dialog;
import com.behincom.behincome.Accesories.Setting;
import com.behincom.behincome.Activityes.Setting.CustomerState.fragCustomerState;
import com.behincom.behincome.Adapters.SwipeItems.Customers.OnCustomerListChangedListener;
import com.behincom.behincome.Adapters.SwipeItems.Helper.ItemTouchHelperViewHolder;
import com.behincom.behincome.Adapters.SwipeItems.Helper.OnStartDragListener;
import com.behincom.behincome.Adapters.SwipeItems.SwipeAndDragHelper;
import com.behincom.behincome.Datas.Base.Basics;
import com.behincom.behincome.Datas.BaseData.Basic_CustomerStates;
import com.behincom.behincome.Datas.Keys.ResponseMessageType;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.Datas.Result.SimpleResponse;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.WebRequest.RWInterface;
import com.behincom.behincome.WebRequest.Retrofite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapCustomerState extends RecyclerView.Adapter<adapCustomerState.AdapterMemberGtoup> {

    Context context;
    AlertDialog.Builder builder;
    static RWInterface rInterface = Retrofite.getClient().create(RWInterface.class);
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
        ImageView btnUp = holder.btnUp;
        ImageView btnDown = holder.btnDown;
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

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updater(position, true);
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updater(position, false);
            }
        });
    }
    int FromPosition = 0;
    int ToPosition = 0;
    int CustomerID1 = 0;
    int CustomerID2 = 0;
    String FromOrder = "0";
    String ToOrder = "0";
    Basic_CustomerStates itemFrom = new Basic_CustomerStates();
    Basic_CustomerStates itemTo = new Basic_CustomerStates();
    private void Updater(final int position, boolean isDown){
        final Dialog pDialog = new Dialog(context);
        pDialog.Show();

        FromPosition = position;
        if(isDown){
            ToPosition = position + 1;
            if(position != lList.size() - 1){
                itemFrom = lList.get(position);
                itemTo = lList.get(ToPosition);
                CustomerID1 = itemFrom.CustomerStateID;
                CustomerID2 = itemTo.CustomerStateID;
                FromOrder = lList.get(FromPosition).CustomerStateOrder;
                ToOrder = lList.get(ToPosition).CustomerStateOrder;
            }
        }else{
            ToPosition = position - 1;
            if(position != 0){
                itemFrom = lList.get(position);
                itemTo = lList.get(ToPosition);
                CustomerID1 = itemFrom.CustomerStateID;
                CustomerID2 = itemTo.CustomerStateID;
                FromOrder = lList.get(FromPosition).CustomerStateOrder;
                ToOrder = lList.get(ToPosition).CustomerStateOrder;
            }
        }
        Map<String, Object> BodyParameters = new HashMap<>();
        BodyParameters.put("CustomerStateID1", CustomerID1);
        BodyParameters.put("CustomerStateID2", CustomerID2);
        BodyParameters.put("CustomerStateOrder1", FromOrder);
        BodyParameters.put("CustomerStateOrder2", ToOrder);

        Call Update = rInterface.RQChangePositionBasicCustomerStates(Setting.getToken(), new HashMap<>(BodyParameters));
        Update.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                String Err = Basics.ServerError;
                if(response.isSuccessful()){
                    SimpleResponse simple = response.body();
                    if(simple.Type.equalsIgnoreCase(ResponseMessageType.Success.toString())){
//                        SQL.Update(lList, " WHERE CustomerStateID='" + lList.CustomerStateID + "'");
                        SQL.Execute("UPDATE " + Tables.Basic_CustomerStates + " SET CustomerStateOrder='" + FromOrder + "' WHERE CustomerStateID='" + CustomerID2 + "'");
                        SQL.Execute("UPDATE " + Tables.Basic_CustomerStates + " SET CustomerStateOrder='" + ToOrder + "' WHERE CustomerStateID='" + CustomerID1 + "'");
                        lList.set(FromPosition, lList.get(FromPosition));
                        lList.set(ToPosition, lList.get(ToPosition));
                        MoverNotify(FromPosition, ToPosition);
                    }else if(simple.Type.equalsIgnoreCase(ResponseMessageType.Error.toString())){
                        for (Map.Entry<String, Object> entry : simple.Errors.entrySet()) {
                            Err = entry.getValue().toString();
                        }
                    }
                }
                pDialog.DisMiss();
                Toast.makeText(context, Err, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, Basics.ServerError, Toast.LENGTH_LONG).show();
                pDialog.DisMiss();
            }
        });
    }
    private void MoverNotify(int From, int To){
        notifyItemChanged(From);
        notifyItemChanged(To);
        notifyItemMoved(From, To);
    }
    public static class AdapterMemberGtoup extends RecyclerView.ViewHolder{

        public CardView cardViewMain;
        public TextView lblTitle;
        public ImageView imgColor;
        public ImageView btnUp;
        public ImageView btnDown;

        public AdapterMemberGtoup(View itemView){
            super(itemView);

            cardViewMain = itemView.findViewById(R.id.cardViewMain);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            btnUp = itemView.findViewById(R.id.btnUp);
            btnDown = itemView.findViewById(R.id.btnDown);
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
