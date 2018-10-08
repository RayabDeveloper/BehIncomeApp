package com.behincom.behincome.Activityes.Map;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.behincom.behincome.Datas.Customer.MapStatusInfo.DataInfoData;
import com.behincom.behincome.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class cInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public cInfoWindow(Context ctx){
        context = ctx;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.info_windows, null);

        CardView cardViewMain = view.findViewById(R.id.cardViewMain);
        TextView lblName = view.findViewById(R.id.lblName);
        TextView lblactField = view.findViewById(R.id.lblactField);
        TextView lblActivityField = view.findViewById(R.id.lblActivityField);

        final DataInfoData infoWindowData = (DataInfoData) marker.getTag();

        lblName.setText(infoWindowData.name());
        lblActivityField.setText(infoWindowData.activityField());

        return view;
    }
}
