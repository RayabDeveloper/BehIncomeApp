package com.behincom.behincome.Activityes.Account.Experiances;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.behincom.behincome.R;

public class fragExperianceWork extends Fragment {

    static Context context;

    public static fragExperianceWork newInstance(Context mContext){
        fragExperianceWork fragment = new fragExperianceWork();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_experiance_work, container, false);



        return view;
    }

}
