package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.behincom.behincome.Activityes.Setting.fragBasicData;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.app.AppController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class adapSettingMainItems<T> extends RecyclerView.Adapter<adapSettingMainItems.AdapterMember>{

    Context context = AppController.getContext;
    RSQLite SQL = new RSQLite();

    private List<T> lList = new ArrayList<>();
    private String IdName = "";
    private String TitleName = "";
    private Object mObject;
    public adapSettingMainItems(List<T> lList, String IdName, String TitleName){
        this.lList = lList;
        this.TitleName = TitleName;
        this.IdName = IdName;
        try {
            mObject = lList.get(0).getClass().newInstance();
            int t = 3;
        }catch (Exception ignored){}
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
                .inflate(R.layout.items_setting_main, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, @SuppressLint("RecyclerView") final int position) {
        final LinearLayout linBG = holder.linBG;
        final TextView lblTitle = holder.lblTitle;
        final ImageView img = holder.img;

        if(fragBasicData.Position  == position){
            linBG.setBackgroundColor(context.getResources().getColor(R.color.BaseBackgroundColor));
            lblTitle.setTextColor(context.getResources().getColor(R.color.txtGray4));
        }else{
            linBG.setBackgroundColor(context.getResources().getColor(R.color.txtBlue));
            lblTitle.setTextColor(context.getResources().getColor(R.color.BaseBackgroundColor));
        }

        Field[] fields = mObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().contains(TitleName)){
                try {
                    String Title = field.get(lList.get(position)).toString();
                    lblTitle.setText(Title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        lblTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click(position);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click(position);
            }
        });
        lblTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LongClick(position);
                return false;
            }
        });
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LongClick(position);
                return false;
            }
        });

    }
    private void Click(int position){
        fragBasicData frag = new fragBasicData();

        Field[] fields = mObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().contains(IdName)){
                try {
                    int Id = Integer.parseInt(field.get(lList.get(position)).toString());
                    frag.FilterSubItemsFromMainItemSelected(Id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        fragBasicData.Position = position;
        fragBasicData.SubAdderViewer(position);
//        fragBasicData.showMain();
    }
    private void LongClick(int position){
        boolean isAdmin = true;
        Field[] fields = lList.get(position).getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().contains("ByAdmin")) {
                    try {
                        String msd = field.get(lList.get(position)).toString();
                        if(msd.equalsIgnoreCase("true"))
                            isAdmin = true;
                        else
                            isAdmin = false;
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }

        if(!isAdmin)
            fragBasicData.Choiser(lList.get(position), true, 0);
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linBG;
        public TextView lblTitle;
        public ImageView img;

        AdapterMember(View itemView){
            super(itemView);
            linBG = itemView.findViewById(R.id.linBG);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            img = itemView.findViewById(R.id.img);
        }

    }

}
