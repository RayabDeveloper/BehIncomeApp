package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.fragBaseDataCity;
import com.behincom.behincome.Activityes.Setting.fragBasicData;
import com.behincom.behincome.Datas.BaseData.Basic_Provinces;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.app.AppController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class adapBasicDataProvince extends RecyclerView.Adapter<adapBasicDataProvince.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();

    private List<Basic_Provinces> lList = new ArrayList<>();
    public adapBasicDataProvince(List<Basic_Provinces> lList, Context context){
        this.lList = lList;
        this.context = context;
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

        if(fragBaseDataCity.Position  == position){
            linBG.setBackgroundColor(context.getResources().getColor(R.color.BaseBackgroundColor));
            lblTitle.setTextColor(context.getResources().getColor(R.color.txtGray4));
        }else{
            linBG.setBackgroundColor(context.getResources().getColor(R.color.txtBlue));
            lblTitle.setTextColor(context.getResources().getColor(R.color.BaseBackgroundColor));
        }

        lblTitle.setText(lList.get(position).ProvinceTitle);

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

    }
    private void Click(int position){
        fragBaseDataCity frag = new fragBaseDataCity();

        frag.FilterSubItemsFromMainItemSelected(lList.get(position).ProvinceID);
        fragBaseDataCity.Position = position;
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
