package com.behincom.behincome.Adapters.SwipeItems;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.behincom.behincome.R;

public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView sectionTitle;

    public SectionHeaderViewHolder(View itemView) {
        super(itemView);
        sectionTitle = itemView.findViewById(R.id.lblPrice);
    }

}
