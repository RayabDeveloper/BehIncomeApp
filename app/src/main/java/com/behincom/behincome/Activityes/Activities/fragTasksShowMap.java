package com.behincom.behincome.Activityes.Activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.behincom.behincome.Datas.Activityes.Activities;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class fragTasksShowMap extends Fragment {

    static Context context;

    SupportMapFragment mMap;
    static GoogleMap GMap;

    public static List<Customers> lCustomer = new ArrayList<>();
    List<MarkerOptions> lMarker = new ArrayList<>();

    public static fragTasksShowMap newInstance(Context mContext) {
        fragTasksShowMap fragment = new fragTasksShowMap();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tasks_show_map, container, false);

        mMap = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mMap);
        try {
            mMap.onCreate(savedInstanceState);
        } catch (Exception Ex) {
            String Er = Ex.getMessage();
        }
        mMap.onResume();
        try {
            MapsInitializer.initialize(context);
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
                for (Customers data : lCustomer) {
                    MarkerOptions mo = new MarkerOptions();
                    LatLng loc = new LatLng(data.CustomerLatitude, data.CustomerLongitude);
                    mo.position(loc);
                    mo.title(data.CustomerName);
                    if (data.isCheck)
                        mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red));
                    else mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                    GMap.addMarker(mo);
                    lMarker.add(mo);
                }

                CameraPosition cameraPosition = new CameraPosition.Builder().target(lMarker.get(0).getPosition()).zoom(16).build();
                GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                GMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        for (Customers data : lCustomer) {
                            double l1 = data.CustomerLatitude;
                            double l2 = data.CustomerLongitude;
                            double ll1 = marker.getPosition().latitude;
                            double ll2 = marker.getPosition().longitude;
                            String mName = marker.getTitle();
                            String nName = data.CustomerName;
                            if (data.CustomerLatitude == marker.getPosition().latitude && data.CustomerLongitude == marker.getPosition().longitude && marker.getTitle().equalsIgnoreCase(data.CustomerName)) {
                                if (!data.isCheck) {
                                    if (fragAddTasks.CurrentCountNumber < fragAddTasks.MaxCount) {
                                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red));

                                        fragAddTasks.CurrentCountNumber++;
                                        data.count_number = (fragAddTasks.CurrentCountNumber);
                                        data.isCheck = (true);
                                        fragAddTasks act = new fragAddTasks();
                                        act.setList(lCustomer);
                                    } else {
                                        Toast.makeText(context, "نمیتوانید بیشتر از حد مجاز فروشگاه انتخاب کنید", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                                    data.isCheck = (false);

                                    int cCount = data.count_number;
                                    fragAddTasks.CurrentCountNumber--;
                                    for (Customers dataa : lCustomer) {
                                        if (dataa.isCheck) {
                                            if (dataa.count_number > cCount)
                                                dataa.count_number = (dataa.count_number - 1);
                                        }
                                    }
                                    data.count_number = (fragAddTasks.CurrentCountNumber);
                                    fragAddTasks act = new fragAddTasks();
                                    act.setList(lCustomer);
                                }
                            }
                        }
                        return true;
                    }
                });
            }
        });

        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMap != null) {
            mMap.onSaveInstanceState(outState);
        }
    }
    private void Finisher(){
        lCustomer = new ArrayList<>();
        lMarker = new ArrayList<>();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Finisher();
    }

}
