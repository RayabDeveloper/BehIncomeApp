package com.behincom.behincome.Adapters.Customer.AddCustomer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Customer.AddCustomer.fragTag;
import com.behincom.behincome.Activityes.Setting.fragBasicData;
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

public class adapAddCustomerTagSub extends RecyclerView.Adapter<adapAddCustomerTagSub.AdapterMember> {

    private Context context;
    RSQLGeter geter = new RSQLGeter();

    private List<Basic_Tags> lList = new ArrayList<>();
    private List<Basic_Tags> lListCustomer = new ArrayList<>();

    private int TagIDSelectedForRadio = 0;

    public adapAddCustomerTagSub(List<Basic_Tags> lList, List<Basic_Tags> lCustomer, Context mContext) {
        this.lList = lList;
        this.lListCustomer = lCustomer;
        this.context = mContext;
    }

    public List<Basic_Tags> getList() {
        return lList;
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_add_customer_tag, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final TextView lblSubTitle = holder.lblSubTitle;
        final AppCompatCheckBox ch = holder.ch;
        final AppCompatCheckBox ch00 = holder.ch00;

        if(isRadio(lList.get(position).TagGroupID)){
            ch00.setVisibility(View.GONE);
            ch.setVisibility(View.VISIBLE);
        }else{
            ch00.setVisibility(View.VISIBLE);
            ch.setVisibility(View.GONE);
        }

        lblSubTitle.setText(lList.get(position).TagTitle);

        if(isRadio(lList.get(position).TagGroupID)) {
            if (lList.get(position).TagID == TagIDSelectedForRadio) {
                ch.setChecked(true);
            } else {
                ch.setChecked(false);
            }
        }else
            ch00.setChecked(lList.get(position).isCheck);

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ch.isChecked()){
                    TagIDSelectedForRadio = lList.get(position).TagID;
                    lListCustomer = new ArrayList<>();
                    lListCustomer.add(lList.get(position));
                }else
                    lListCustomer.remove(lList.get(position));
                lList.get(position).isCheck = !ch.isChecked();
                notifyDataSetChanged();
            }
        });
        ch00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ch00.isChecked()){
                    lListCustomer.add(lList.get(position));//add Top For ch(Radio)
                }else{
                    lListCustomer.remove(lList.get(position));
                }
                lList.get(position).isCheck = !ch00.isChecked();
                notifyDataSetChanged();
            }
        });

    }

    private boolean isRadio(int TagGroupID){
        List<Basic_TagGroups> lGroup = geter.getList(Basic_TagGroups.class, " WHERE TagGroupID='" + TagGroupID + "'");
        if(lGroup.size() > 0){
            if(lGroup.get(0).TagGroupTypeId == TagType.RadioButton)
                return true;
            else
                return false;
        }else
            return false;
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblSubTitle;
        public AppCompatCheckBox ch;
        public AppCompatCheckBox ch00;

        AdapterMember(View itemView) {
            super(itemView);
            lblSubTitle = itemView.findViewById(R.id.lblSubTitle);
            ch00 = itemView.findViewById(R.id.ch00);
            ch = itemView.findViewById(R.id.ch);
        }

    }

}
