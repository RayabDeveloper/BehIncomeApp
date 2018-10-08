package com.behincom.behincome.Adapters.Customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.behincom.behincome.Activityes.Customer.fragAddCustomer;
import com.behincom.behincome.Datas.BaseData.Basic_Properties;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.BaseData.Basic_Tags;
import com.behincom.behincome.Datas.BaseData.mBasic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.mBasic_Tags;
import com.behincom.behincome.Datas.Keys.Tables;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class adapStoreTag extends RecyclerView.Adapter<adapStoreTag.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();

    public List<Basic_Tags> lList;
    public adapStoreTag(List<Basic_Tags> lList){
        this.lList = lList;
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
                .inflate(R.layout.items_addstore_tag, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final AppCompatCheckBox chTag = holder.chTag;

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
//        chTag.setTypeface(tf);

        chTag.setText(lList.get(position).TagTitle);
        if(lList.get(position).isCheck)
            chTag.setChecked(true);
        else
            chTag.setChecked(false);
        chTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chTag.isChecked()) {
//                    SQL.Execute("UPDATE Basic_Tags SET isCheck='1' WHERE TagID='" + lList.get(position).TagID + "'");
//                    SQL.Insert(lList.get(position), mBasic_Tags.class);
                    for (Basic_Tags data : fragAddCustomer.lTag) {
                        if(data.TagID == lList.get(position).TagID){
                            data.isCheck = true;
                        }
                    }
                }else {
//                    SQL.Execute("UPDATE Basic_Tags SET isCheck='0' WHERE TagID='" + lList.get(position).TagID + "'");
//                    SQL.Execute("DELETE FROM " + Tables.mBasic_Tags + " WHERE TagID='" + lList.get(position).TagID + "'");
                    for (Basic_Tags data : fragAddCustomer.lTag) {
                        if(data.TagID == lList.get(position).TagID){
                            data.isCheck = false;
                        }
                    }
                }
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder{

        public AppCompatCheckBox chTag;

        public AdapterMember(View itemView){
            super(itemView);
            chTag = itemView.findViewById(R.id.chTag);
        }

    }

}
