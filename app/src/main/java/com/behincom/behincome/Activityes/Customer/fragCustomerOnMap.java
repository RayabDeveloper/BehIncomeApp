package com.behincom.behincome.Activityes.Customer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.behincom.behincome.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragCustomerOnMap extends Fragment {

    static Context context;
    FragmentActivity fer = (FragmentActivity)((Activity)context);
    actCustomer act = new actCustomer();

    MapView mMap;
    static GoogleMap GMap;

    public static String StoreName = "";
    public static LatLng Loc = new LatLng(0,0);

    public static fragBigMap newInstance(Context mContext) {
        fragBigMap fragment = new fragBigMap();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customer_on_map, container, false);

        mMap = view.findViewById(R.id.mMap);
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

                GMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                    }
                });

                GMap.clear();
                MarkerOptions mo = new MarkerOptions();
                mo.position(Loc);
                mo.title(StoreName);
                mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                GMap.addMarker(mo);

                CameraPosition cameraPosition = new CameraPosition.Builder().target(Loc).zoom(16).build();
                GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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

}
