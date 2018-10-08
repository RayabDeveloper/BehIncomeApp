package com.behincom.behincome.Activityes.Login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.behincom.behincome.R;

public class fragJustic extends Fragment {

    static Context context;

    public static fragJustic newInstance(Context mContext){
        fragJustic fragment = new fragJustic();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_justic, container, false);



        return view;
    }

}
