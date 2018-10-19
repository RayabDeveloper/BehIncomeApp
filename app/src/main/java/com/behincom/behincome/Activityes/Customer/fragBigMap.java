package com.behincom.behincome.Activityes.Customer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class fragBigMap extends Fragment {

    static Context context;
    actCustomer act = new actCustomer();
    Customers customer = new Customers();

    MapView mMap;
    GoogleMap GMap;
    ImageView imgLoc;
    TextView lblTitle;
    ImageView btnCheck, imgBack;

    public static fragBigMap newInstance(Context mContext) {
        fragBigMap fragment = new fragBigMap();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_big_map, container, false);

        lblTitle = view.findViewById(R.id.lblTitle);
        btnCheck = view.findViewById(R.id.btnCheck);
        imgBack = view.findViewById(R.id.imgBack);
        imgLoc = view.findViewById(R.id.imgLoc);
        mMap = view.findViewById(R.id.mMap);

        lblTitle.setText("انتخاب موقعیت");
        btnCheck.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddCustomer.goingToBigMap = false;
                act.getFragByState(FragmentState.AddCustomer);
            }
        });

        try {;
            mMap.onCreate(savedInstanceState);
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        mMap.onResume();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GMap = googleMap;
                googleMap.setMyLocationEnabled(true);

                LatLng MainCameraPoint = new LatLng(35.698399, 51.030049);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(MainCameraPoint).zoom(16).build();
                GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        imgLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition cp = GMap.getCameraPosition();
                fragAddCustomer.cPosition = cp.target;
                fragAddCustomer.isMap = true;
                act.getFragByState(FragmentState.AddCustomer);
                fragAddCustomer.goingToBigMap = false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.onResume();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMap != null) {
            mMap.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragAddCustomer.goingToBigMap = false;
    }
}
