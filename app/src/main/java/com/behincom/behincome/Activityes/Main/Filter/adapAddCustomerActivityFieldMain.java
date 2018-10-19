package com.behincom.behincome.Activityes.Main.Filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behincom.behincome.Datas.BaseData.Basic_ActivityFieldGroups;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;

import java.util.ArrayList;
import java.util.List;

public class adapAddCustomerActivityFieldMain extends RecyclerView.Adapter<adapAddCustomerActivityFieldMain.AdapterMember> {

    Context context;
    RSQLGeter geter = new RSQLGeter();

    private List<Basic_ActivityFieldGroups> lList = new ArrayList<>();

    public adapAddCustomerActivityFieldMain(List<Basic_ActivityFieldGroups> lList, Context mContext) {
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

        if (fragAddActivityField.position == position) {
            linBG.setBackgroundColor(context.getResources().getColor(R.color.BaseBackgroundColor));
            lblTitle.setTextColor(context.getResources().getColor(R.color.txtGray4));
        } else {
            linBG.setBackgroundColor(context.getResources().getColor(R.color.txtBlue));
            lblTitle.setTextColor(context.getResources().getColor(R.color.BaseBackgroundColor));
        }

        lblTitle.setText(lList.get(position).ActivityFieldGroupTitle);

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

    private void Click(int position) {
        fragAddActivityField.refreshActivityFields(lList.get(position).ActivityFieldGroupID);
        fragAddActivityField.position = position;
        fragAddActivityField.MainIDSelected = lList.get(position).ActivityFieldGroupID;
        notifyDataSetChanged();
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public LinearLayout linBG;
        public TextView lblTitle;
        public ImageView img;

        AdapterMember(View itemView) {
            super(itemView);
            linBG = itemView.findViewById(R.id.linBG);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            img = itemView.findViewById(R.id.img);
        }

    }

}
