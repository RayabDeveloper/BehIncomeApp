package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Setting.fragBaseDataCity;
import com.behincom.behincome.Activityes.Setting.fragBasicData;
import com.behincom.behincome.Datas.BaseData.Basic_Cities;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.Keys.TagType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
import com.behincom.behincome.app.AppController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class adapBasicDataCity extends RecyclerView.Adapter<adapBasicDataCity.AdapterMember>{

    private Context context = AppController.getContext;
    RSQLite SQL = new RSQLite();

    protected List<Basic_Cities> lList = new ArrayList<>();
    public adapBasicDataCity(List<Basic_Cities> lList, Context context){
        this.lList = lList;
        this.context = context;
    }

    public List<Basic_Cities> getList(){
        return lList;
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
                .inflate(R.layout.items_setting_sub, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final TextView lblSubTitle = holder.lblSubTitle;
        final AppCompatCheckBox ch = holder.ch;

        lblSubTitle.setText(lList.get(position).CityTitle);
        ch.setChecked(lList.get(position).isCheck);
        if(lList.get(position).isCheck){
            fragBasicData.IdsToSend = fragBasicData.IdsToSend.replace(lList.get(position).CityID + ", ", "");
            fragBasicData.IdsToSend += lList.get(position).CityID + ", ";
        }

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lList.get(position).isCheck = ch.isChecked();
                fragBaseDataCity.IdsToSend = Integer.toString(lList.get(position).CityID);
                fragBaseDataCity.RequestManager(ch.isChecked(), lList.get(position).CityID);
            }
        });

    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblSubTitle;
        public AppCompatCheckBox ch;

        AdapterMember(View itemView){
            super(itemView);
            lblSubTitle = itemView.findViewById(R.id.lblSubTitle);
            ch = itemView.findViewById(R.id.ch);
        }

    }

}
