package com.behincom.behincome.Adapters.Setting;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behincom.behincome.Accesories.DateConverter;
import com.behincom.behincome.Accesories.PersianCalendar;
import com.behincom.behincome.Activityes.Setting.Priods.Visitors.fragAddVisitorPriod;
import com.behincom.behincome.Activityes.Setting.actSetting;
import com.behincom.behincome.Datas.Keys.FragmentState;
import com.behincom.behincome.Datas.Marketing.MarketingVisitTours;
import com.behincom.behincome.Datas.RSQLGeter;
import com.behincom.behincome.R;
import com.behincom.behincome.SQL.RSQLite;

import java.util.List;

import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;

public class adapPriod extends RecyclerView.Adapter<adapPriod.AdapterMember>{

    Context context;
    RSQLite SQL = new RSQLite();
    RSQLGeter geter = new RSQLGeter();
    Dialog piDialog;

    public List<MarketingVisitTours> lList;
    public adapPriod(List<MarketingVisitTours> lList){
        this.lList = lList;
    }
    @Override
    public int getItemCount() {
        return lList.size();
    }
    public void Clear(){
        try{
            this.lList.clear();
        }catch (Exception ignored){}
    }
    @Override
    public AdapterMember onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_setting_priod, parent, false);

        return new AdapterMember(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AdapterMember holder, final int position) {
        TextView lblName1 = holder.lblName1;
        TextView lblFromDate1 = holder.lblFromDate1;
        TextView lblToDate1 = holder.lblToDate1;
        CardView cardView = holder.cardView;

        DateConverter DC = new DateConverter();
        String FromDate = DC.getToHijri(lList.get(position).DateFrom);
        String ToDate = DC.getToHijri(lList.get(position).DateTo);

        PersianCalendar pc = new PersianCalendar();
        String[] pDate = FromDate.split("/");
        pc.setPersainCalendarWithJalali(Integer.parseInt(pDate[0]), Integer.parseInt(pDate[1]), Integer.parseInt(pDate[2]));
        String FromDateLong = DC.getStringLongDate(pc.getDayOfWeek(), pc.getDay(), pc.getMonth(), pc.getYear());
        String[] pDate2 = ToDate.split("/");
        pc.setPersainCalendarWithJalali(Integer.parseInt(pDate2[0]), Integer.parseInt(pDate2[1]), Integer.parseInt(pDate2[2]));
        String ToDateLong = DC.getStringLongDate(pc.getDayOfWeek(), pc.getDay(), pc.getMonth(), pc.getYear());


        lblName1.setText(lList.get(position).VisitTourTitle);
        lblFromDate1.setText(FromDate + " " + FromDateLong);
        lblToDate1.setText(ToDate + " " + ToDateLong);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragAddVisitorPriod.mData = lList.get(position);
                fragAddVisitorPriod.toEdite = true;
                actSetting act = new actSetting();
                act.getFragByState(FragmentState.AddVisitTour);
            }
        });
    }
    public static class AdapterMember extends RecyclerView.ViewHolder {

        public TextView lblName;
        public TextView lblName1;
        public TextView lblFromDate;
        public TextView lblFromDate1;
        public TextView lblToDate;
        public TextView lblToDate1;
        public TextView lblDescription;
        public TextView lblDescription1;
        public CardView cardView;

        public AdapterMember(View itemView){
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            lblName1 = itemView.findViewById(R.id.lblName1);
            lblFromDate = itemView.findViewById(R.id.lblFromDate);
            lblFromDate1 = itemView.findViewById(R.id.lblFromDate1);
            lblToDate = itemView.findViewById(R.id.lblToDate);
            lblToDate1 = itemView.findViewById(R.id.lblToDate1);
            lblDescription = itemView.findViewById(R.id.lblDescription);
            lblDescription1 = itemView.findViewById(R.id.lblDescription1);
            cardView = itemView.findViewById(R.id.cardViewMain);
        }

    }
}
