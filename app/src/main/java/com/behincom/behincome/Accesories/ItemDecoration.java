package com.behincom.behincome.Accesories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.behincom.behincome.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class ItemDecoration {

    public static RecyclerView.ItemDecoration getDecoration(Context context){
        return new HorizontalDividerItemDecoration.Builder(context)
                .colorResId(R.color.txtGray0)
                .size(2)
                .build();
    }

}
