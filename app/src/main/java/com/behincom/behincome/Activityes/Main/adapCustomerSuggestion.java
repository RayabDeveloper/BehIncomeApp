package com.behincom.behincome.Activityes.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Activityes.Main.Filter.adapAddPrefix;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;

import java.util.ArrayList;
import java.util.List;

public class adapCustomerSuggestion extends RecyclerView.Adapter<adapCustomerSuggestion.AdapterMember> {

    private Context context;
    RSQLGeter geter = new RSQLGeter();

    private List<String> lList = new ArrayList<>();;

    public adapCustomerSuggestion(List<String> lList, Context mContext) {
        this.lList = lList;
        this.context = mContext;
    }

    public List<String> getList() {
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
                .inflate(R.layout.items_customer_suggestion, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        final TextView lblTitle = holder.lblTitle;

        lblTitle.setText(Html.fromHtml(lList.get(position)));
        lblTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragCustomers.setSearch(lblTitle.getText().toString());
            }
        });
    }

    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblTitle;

        AdapterMember(View itemView) {
            super(itemView);
            lblTitle = itemView.findViewById(R.id.lblTitle);
        }

    }

}
