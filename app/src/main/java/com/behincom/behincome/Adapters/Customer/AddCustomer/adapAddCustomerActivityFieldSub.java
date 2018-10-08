package com.behincom.behincome.Adapters.Customer.AddCustomer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Customer.AddCustomer.fragActivityField;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_TagGroups;
import com.behincom.behincome.Datas.Keys.TagType;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;

import java.util.ArrayList;
import java.util.List;

public class adapAddCustomerActivityFieldSub extends RecyclerView.Adapter<adapAddCustomerActivityFieldSub.AdapterMember> {

    private Context context;
    RSQLGeter geter = new RSQLGeter();

    private List<Basic_ActivityFields> lList = new ArrayList<>();
    private List<Basic_ActivityFields> lListCustomer = new ArrayList<>();

    private int ActivityFieldIDSelectedForRadio = 0;

    public adapAddCustomerActivityFieldSub(List<Basic_ActivityFields> lList, List<Basic_ActivityFields> lCustomer, Context mContext) {
        this.lList = lList;
        this.lListCustomer = lCustomer;
        this.context = mContext;
    }

    public List<Basic_ActivityFields> getList() {
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

        ch00.setVisibility(View.VISIBLE);
        ch.setVisibility(View.GONE);

        ch00.setChecked(lList.get(position).isCheck);
        lblSubTitle.setText(lList.get(position).ActivityFieldTitle);

        ch00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch00.isChecked()){
                    lListCustomer.add(lList.get(position));
                }else{
                    lListCustomer.remove(lList.get(position));
                }
                lList.get(position).isCheck = ch00.isChecked();
                notifyDataSetChanged();
            }
        });

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
