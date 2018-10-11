package com.behincom.behincome.Activityes.Main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.behincom.behincome.Accesories.ItemDecoration;
import com.behincom.behincome.Activityes.Map.cInfoWindow;
import com.behincom.behincome.Adapters.Main.adapMap;
import com.behincom.behincome.Datas.BaseData.Basic_ActivityFields;
import com.behincom.behincome.Datas.BaseData.Basic_NamePrefixes;
import com.behincom.behincome.Datas.Customer.Customers;
import com.behincom.behincome.Datas.Customer.MapStatusInfo.DataInfoData;
import com.behincom.behincome.Datas.Customer.MyCustomers;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;
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

public class fragCustomersMap extends Fragment {

    static Context context;
    private RSQLite SQL = new RSQLite();
    private RSQLGeter geter = new RSQLGeter();
    FragmentActivity fer = (FragmentActivity)((Activity)context);

    EditText txtSearch;
    ImageView btnDirection;
    RecyclerView lstMain;
    SupportMapFragment mMap;
    static GoogleMap GMap;
    adapMap adapter;
    RecyclerView.LayoutManager mLayoutManager;

    public static List<MyCustomers> lCustomer = new ArrayList<>();

    List<MarkerOptions> lOptions = new ArrayList<>();

    public static fragCustomersMap newInstance(Context mContext){
        fragCustomersMap fragment = new fragCustomersMap();
        context = mContext;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_customers_map, container, false);

        mMap = (SupportMapFragment)fer.getSupportFragmentManager().findFragmentById(R.id.mMap);
        txtSearch = view.findViewById(R.id.txtSearch);
        btnDirection = view.findViewById(R.id.btnDirection);
        lstMain = view.findViewById(R.id.lstMain);


        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        txtSearch.setTypeface(tFace);

        try {
            mMap.onCreate(savedInstanceState);
        }catch (Exception Ex){
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
                        Toast.makeText(context, "ASD", Toast.LENGTH_SHORT).show();
                    }
                });

                GMap.clear();
                int i = 0;
                for (MyCustomers data : lCustomer) {
                    try {
                        MarkerOptions mo = new MarkerOptions();
                        mo.position(new LatLng(data.Customers.CustomerLatitude, data.Customers.CustomerLongitude));
                        mo.title(data.Customers.CustomerName);
                        mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                        List<Basic_NamePrefixes> lPrefixNames = geter.getList(Basic_NamePrefixes.class);
                        List<Basic_ActivityFields> lField = geter.getList(Basic_ActivityFields.class, "WHERE id='" + data.Customers.Customers_ActivityFields.get(0).ActivityFieldID + "'");
                        String Fielde = "منسوخ شده";
                        if (lField.size() > 0)
                            Fielde = lField.get(0).ActivityFieldTitle;
                        String Prefix = "منسوخ شده";
                        if (lPrefixNames.size() > 0)
                            Prefix = lPrefixNames.get(0).NamePrefixTitle;

                        DataInfoData infoData = new DataInfoData();
                        infoData.lCustomer(data);
                        infoData.position(i);
                        infoData.customerId(data.Customers.CustomerID);
                        infoData.name(Prefix + " " + data.Customers.CustomerName);
                        infoData.activityField(Fielde);

                        cInfoWindow cInfoWindow = new cInfoWindow(context);
                        GMap.setInfoWindowAdapter(cInfoWindow);

                        Marker marker = GMap.addMarker(mo);
                        marker.setTag(infoData);
                        marker.showInfoWindow();
//                GMap.addMarker(mo);
                        lOptions.add(mo);
                    }catch (Exception Ex){
                        String Er = Ex.getMessage();
                    }
                    i++;
                }

                GMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        DataInfoData infoWindowData = (DataInfoData) marker.getTag();
//                        actStoreShow.Customer = infoWindowData.lCustomer();
//                        actStoreShow.position = infoWindowData.position();
//                        Intent intent = new Intent(context, actStoreShow.class);
//                        context.startActivity(intent);
                    }
                });

                LatLng MainCameraPoint = new LatLng(35.698399, 51.030049);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(MainCameraPoint).zoom(16).build();
                GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshList();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMap != null) {
            mMap.onSaveInstanceState(outState);
        }
    }
    public static void getLoc(LatLng loc){
        float zoom = GMap.getCameraPosition().zoom;
        CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(zoom).build();
        GMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    private void RefreshList(){
        adapter = new adapMap(lCustomer);
        lstMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        lstMain.setLayoutManager(mLayoutManager);
        lstMain.addItemDecoration(ItemDecoration.getDecoration(context));
        lstMain.setItemAnimator(new DefaultItemAnimator());
        lstMain.setAdapter(adapter);
    }

}
