package com.behincom.behincome.Accesories;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import saman.zamani.persiandate.PersianDate;

public class DateConverter {

    private int y=0, m=0, d=0, h=0, mm2=0, s=0;
    private ir.mirrajabi.persiancalendar.core.models.PersianDate PD;

    public String Year(){return Integer.toString(this.y);}
    public String Month(){return Integer.toString(this.m);}
    public String Day(){return Integer.toString(this.d);}
    public String Hour(){return Integer.toString(this.h);}
    public String Minute(){return Integer.toString(this.mm2);}

    public DateConverter(int Year, int Month, int Day){
        PersianDate pDate = new PersianDate();
        pDate.setShYear(Year);
        pDate.setShMonth(Month);
        pDate.setShDay(Day);
        y = pDate.getGrgYear();
        m = pDate.getGrgMonth();
        d = pDate.getGrgDay();
    }
    public DateConverter(String Date){
        try {
            String[] mDate = Date.split("-");
            String[] aDate = mDate[0].trim().split("/");
            String[] bDate = mDate[1].trim().split(":");
            y = Integer.parseInt(aDate[0]);
            m = Integer.parseInt(aDate[1]);
            d = Integer.parseInt(aDate[2]);
            h = Integer.parseInt(bDate[0]);
            mm2 = Integer.parseInt(bDate[1]);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    public DateConverter(String Date, boolean type){
        try {
            String[] mDate = Date.split("T");
            String[] aDate = mDate[0].trim().split("-");
            String[] bDate = mDate[1].trim().split(":");
            y = Integer.parseInt(aDate[0]);
            m = Integer.parseInt(aDate[1]);
            d = Integer.parseInt(aDate[2]);
            h = Integer.parseInt(bDate[0]);
            mm2 = Integer.parseInt(bDate[1]);
            String[] Sec = bDate[2].split("\\.");
            s = Integer.parseInt(Sec[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    public DateConverter(String Date, String Time){
        String[] mDater = Date.split("/");
        String[] mTimer = Time.split(":");
        int yy = Integer.parseInt(mDater[0]);
        int mm = Integer.parseInt(mDater[1]);
        int dd = Integer.parseInt(mDater[2]);
        h = Integer.parseInt(mTimer[0]);
        mm2 = Integer.parseInt(mTimer[1]);
        PersianDate pDate = new PersianDate();
        pDate.setShYear(yy);
        pDate.setShMonth(mm);
        pDate.setShDay(dd);
        y = pDate.getGrgYear();
        m = pDate.getGrgMonth();
        d = pDate.getGrgDay();
    }
    public DateConverter(){

    }
    public DateConverter(ir.mirrajabi.persiancalendar.core.models.PersianDate PD){
        this.PD = PD;
    }
    public Date getCurrentDate(){
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(y, m, d, h, mm2, s);

            return cal.getTime();
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            Calendar cal = Calendar.getInstance();
            cal.set(0, 0, 0, 0, 0, 0);

            return new Date();
        }
    }
    public String getToMiladi(){

        String mY = Integer.toString(y);
        String mM = Integer.toString(m);
        String mD = Integer.toString(d);

        if(mM.length() == 1)
            mM = "0" + mM;
        if(mD.length() == 1)
            mD = "0" + mD;

        return mY + "/" + mM + "/" + mD;
    }
    public String getToHijri(String Date){
        try {
            String[] a = Date.split("T");
            String[] dd = a[0].split("-");
            PersianDate pDate = new PersianDate();
            pDate.setGrgYear(Integer.parseInt(dd[0]));
            pDate.setGrgMonth(Integer.parseInt(dd[1]));
            pDate.setGrgDay(Integer.parseInt(dd[2]));

            String mY = Integer.toString(pDate.getShYear());
            String mM = Integer.toString(pDate.getShMonth());
            String mD = Integer.toString(pDate.getShDay());

            if(mM.length() == 1)
                mM = "0" + mM;
            if(mD.length() == 1)
                mD = "0" + mD;

            return  mY + "/" + mM + "/" + mD;
        }catch (Exception Ex){
            return "";
        }
    }
    public String getToMiladi(String Date){
        try {
            String[] a = Date.split("T");
            String[] dd = a[0].split("-");
            PersianDate pDate = new PersianDate();
            pDate.setGrgYear(Integer.parseInt(dd[0]));
            pDate.setGrgMonth(Integer.parseInt(dd[1]));
            pDate.setGrgDay(Integer.parseInt(dd[2]));

            String mY = Integer.toString(pDate.getShYear());
            String mM = Integer.toString(pDate.getShMonth());
            String mD = Integer.toString(pDate.getShDay());

            if(mM.length() == 1)
                mM = "0" + mM;
            if(mD.length() == 1)
                mD = "0" + mD;

            return  mY + "/" + mM + "/" + mD;
        }catch (Exception Ex){
            return "";
        }
    }
    public String getCSharp(){
        String mm = "", dd = "";
        if(m<10)
            mm="0"+Integer.toString(m);
        else
            mm=Integer.toString(m);
        if(d<10)
            dd="0"+Integer.toString(d);
        else
            dd=Integer.toString(d);
        return Integer.toString(y) + "-" + mm + "-" + dd + "T00:00:00";
    }
    public String getCSharp2(){
        String mm = "", dd = "", hh = "", mmm = "";
        if(m<10)
            mm="0"+Integer.toString(m);
        else
            mm=Integer.toString(m);
        if(d<10)
            dd="0"+Integer.toString(d);
        else
            dd=Integer.toString(d);
        if(h>=10)
            hh=Integer.toString(h);
        else
            hh="0"+Integer.toString(h);
        if(mm2>=10)
            mmm=Integer.toString(mm2);
        else
            mmm="0"+Integer.toString(mm2);
        return Integer.toString(y) + "-" + mm + "-" + dd + "T" + hh + ":" + mmm + ":00";
    }
    public String getOnlyTime(){
        String hh = "", mmm = "";
        if(h>=10)
            hh=Integer.toString(h);
        else
            hh="0"+Integer.toString(h);
        if(mm2>=10)
            mmm=Integer.toString(mm2);
        else
            mmm="0"+Integer.toString(mm2);
        return hh + ":" + mmm;
    }
    public String getOnlyDate(){
        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(y);
        pDate.setGrgMonth(m);
        pDate.setGrgDay(d);
        String mY = Integer.toString(pDate.getShYear());
        String mM = Integer.toString(pDate.getShMonth());
        String mD = Integer.toString(pDate.getShDay());

        if(mM.length() == 1)
            mM = "0" + mM;
        if(mD.length() == 1)
            mD = "0" + mD;

        return mY + "/" + mM + "/" + mD;
    }
    private String getDate(int Year, int Month, int Day){
        String mY = Integer.toString(Year);
        String mM = Integer.toString(Month);
        String mD = Integer.toString(Day);

        if(mM.length() == 1)
            mM = "0" + mM;
        if(mD.length() == 1)
            mD = "0" + mD;

        return mY + "/" + mM + "/" + mD;
    }
    private String getDate(int Year, int Month, int Day, String Type){
        String mY = Integer.toString(Year);
        String mM = Integer.toString(Month);
        String mD = Integer.toString(Day);

        if(mM.length() == 1)
            mM = "0" + mM;
        if(mD.length() == 1)
            mD = "0" + mD;

        return mY + Type + mM + Type + mD;
    }
    public String getTime(int Houre, int Min){
        String mH = Integer.toString(Houre);
        String mM = Integer.toString(Min);

        if(mH.length() == 1)
            mH = "0" + mH;
        if(mM.length() == 1)
            mM = "0" + mM;

        return mH + ":" + mM;
    }
    public String getStringLongDate(int DayOfWeek, int DayOfMonth, int Month, int Year){
        return getDayOfWeek(DayOfWeek) + " " + Integer.toString(DayOfMonth) + " " + getMonthOfYead(Month) + " ماه " + Integer.toString(Year);
    }
    public String getLongDateTimeToMiladi(){
        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(y);
        pDate.setGrgMonth(m);
        pDate.setGrgDay(d);
        int mY = pDate.getShYear();
        int mM = pDate.getShMonth();
        int mD = pDate.getShDay();

        ir.mirrajabi.persiancalendar.core.models.PersianDate tPD = new ir.mirrajabi.persiancalendar.core.models.PersianDate(mY, mM, mD);
        String hH = Integer.toString(h);
        String hM = Integer.toString(mm2);
        if(hH.length() == 1)
            hH = "0" + hH;
        if(hM.length() == 1)
            hM = "0" + hM;
        return getStringLongDate(tPD.getDayOfWeek(), tPD.getDayOfMonth(), tPD.getMonth(), tPD.getYear()) + "  -  " + hH + ":" + hM;
    }
    public String getShortDateTimeToMiladi(){
        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(y);
        pDate.setGrgMonth(m);
        pDate.setGrgDay(d);
        int mY = pDate.getShYear();
        int mM = pDate.getShMonth();
        int mD = pDate.getShDay();

        ir.mirrajabi.persiancalendar.core.models.PersianDate tPD = new ir.mirrajabi.persiancalendar.core.models.PersianDate(mY, mM, mD);
        String hH = Integer.toString(h);
        String hM = Integer.toString(mm2);
        if(hH.length() == 1)
            hH = "0" + hH;
        if(hM.length() == 1)
            hM = "0" + hM;
        return mY + "/" + mM + "/" + mD + " - " + hH + ":" + hM;
    }
    public String GetDateToHijri(){
        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(y);
        pDate.setGrgMonth(m);
        pDate.setGrgDay(d);
        int mY = pDate.getShYear();
        int mM = pDate.getShMonth();
        int mD = pDate.getShDay();

        String mYa = Integer.toString(mY);
        String mMa = Integer.toString(mM);
        String mDa = Integer.toString(mD);

        if(mMa.length() == 1)
            mMa = "0" + mMa;
        if(mDa.length() == 1)
            mDa = "0" + mDa;

        return mYa + "/" + mMa + "/" + mDa;
    }
    private String getDayOfWeek(int Day){
        switch (Day){
            case 0:
                return "پنج شنبه";
            case 1:
                return "جمعه";
            case 2:
                return "شنبه";
            case 3:
                return "یک شنبه";
            case 4:
                return "دو شنبه";
            case 5:
                return "سه شنبه";
            case 6:
                return "چهار شنبه";
        }
        return "";
    }
    private String getMonthOfYead(int Month){
        switch (Month){
            case 1:
                return "فروردین";
            case 2:
                return "اردیبهشت";
            case 3:
                return "خرداد";
            case 4:
                return "تیر";
            case 5:
                return "مرداد";
            case 6:
                return "شهریور";
            case 7:
                return "مهر";
            case 8:
                return "آبان";
            case 9:
                return "آدر";
            case 10:
                return "دی";
            case 11:
                return "بهمن";
            case 12:
                return "اسفند";
        }
        return "";
    }
    public String getDate(){
        return getDate(PD.getYear(), PD.getMonth(), PD.getDayOfMonth());
    }
    public String getDate(String Type){
        return getDate(PD.getYear(), PD.getMonth(), PD.getDayOfMonth(), Type);
    }
    public String getStringLongDate(){
        return getStringLongDate(PD.getDayOfWeek(), PD.getDayOfMonth(), PD.getMonth(), PD.getYear());
    }
    public String getStringLongDateTime(){
        String mH = Integer.toString(h);
        String mM = Integer.toString(mm2);

        if(mH.length() == 1)
            mH = "0" + mH;
        if(mM.length() == 1)
            mM = "0" + mM;

        return getStringLongDate(PD.getDayOfWeek(), PD.getDayOfMonth(), PD.getMonth(), PD.getYear()) + " - " + mH + ":" + mM;
    }
    public String getStringLongDateTime(boolean Type){
        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(y);
        pDate.setGrgMonth(m);
        pDate.setGrgDay(d);
        String mY = Integer.toString(pDate.getShYear());
        String mM = Integer.toString(pDate.getShMonth());
        String mD = Integer.toString(pDate.getShDay());

        String mH = Integer.toString(h);
        String mMm = Integer.toString(mm2);

        if(mH.length() == 1)
            mH = "0" + mH;
        if(mMm.length() == 1)
            mMm = "0" + mMm;

        ir.mirrajabi.persiancalendar.core.models.PersianDate mPD = new ir.mirrajabi.persiancalendar.core.models.PersianDate(Integer.parseInt(mY), Integer.parseInt(mM), Integer.parseInt(mD));
        String test = getStringLongDate(mPD.getDayOfWeek(), mPD.getDayOfMonth(), mPD.getMonth(), mPD.getYear()) + " - " + mH + ":" + mMm;

        return test;
    }
    public String getStringLongDateTime(int Type){
        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(y);
        pDate.setGrgMonth(m);
        pDate.setGrgDay(d);
        String mY = Integer.toString(pDate.getShYear());
        String mM = Integer.toString(pDate.getShMonth());
        String mD = Integer.toString(pDate.getShDay());

        String mH = Integer.toString(h);
        String mMm = Integer.toString(mm2);

        if(mH.length() == 1)
            mH = "0" + mH;
        if(mMm.length() == 1)
            mMm = "0" + mMm;

        ir.mirrajabi.persiancalendar.core.models.PersianDate mPD = new ir.mirrajabi.persiancalendar.core.models.PersianDate(Integer.parseInt(mY), Integer.parseInt(mM), Integer.parseInt(mD));
        String test = getStringLongDate(mPD.getDayOfWeek(), mPD.getDayOfMonth(), mPD.getMonth(), mPD.getYear()) + " - " + mH + ":" + mMm;

        return test;
    }
    public String getCSharpDate(ir.mirrajabi.persiancalendar.core.models.PersianDate PD){
        int nY = PD.getYear();
        int nM = PD.getMonth();
        int nD = PD.getDayOfMonth();
        PersianDate pDate = new PersianDate();
        pDate.setShYear(nY);
        pDate.setShMonth(nM);
        pDate.setShDay(nD);
        int yy = pDate.getGrgYear();
        int mm = pDate.getGrgMonth();
        int dd = pDate.getGrgDay();

        String mYa = Integer.toString(yy);
        String mMa = Integer.toString(mm);
        String mDa = Integer.toString(dd);

        if(mMa.length() == 1)
            mMa = "0" + mMa;
        if(mDa.length() == 1)
            mDa = "0" + mDa;

        return mYa + "-" + mMa + "-" + mDa + "T00:00:00";
    }
    public String getCSharpOnlyDate(ir.mirrajabi.persiancalendar.core.models.PersianDate PD){
        int nY = PD.getYear();
        int nM = PD.getMonth();
        int nD = PD.getDayOfMonth();
        PersianDate pDate = new PersianDate();
        pDate.setShYear(nY);
        pDate.setShMonth(nM);
        pDate.setShDay(nD);
        int yy = pDate.getGrgYear();
        int mm = pDate.getGrgMonth();
        int dd = pDate.getGrgDay();

        String mYa = Integer.toString(yy);
        String mMa = Integer.toString(mm);
        String mDa = Integer.toString(dd);

        if(mMa.length() == 1)
            mMa = "0" + mMa;
        if(mDa.length() == 1)
            mDa = "0" + mDa;

        return mYa + "-" + mMa + "-" + mDa;
    }
    public String[] getMonthNames(){
        String[] names = new String[12];
        names[0] = "فروردین";
        names[1] = "اردیبهشت";
        names[2] = "خرداد";
        names[3] = "تیر";
        names[4] = "مرداد";
        names[5] = "شهریور";
        names[6] = "مهر";
        names[7] = "آبان";
        names[8] = "آذر";
        names[9] = "دی";
        names[10] = "بهمن";
        names[11] = "اسفند";
        return names;
    }
    public String getMonthName(int Month){
        String[] Months = getMonthNames();
        return Months[Month];
    }
    @SuppressLint("SimpleDateFormat")
    public boolean CompareTimes(String Query){
        try {
            String[] Compare = Query.split(" ");
            String StartTime = Compare[0].trim();
            String Equaler = Compare[1].trim();
            String EndTime = Compare[2].trim();
            boolean Result = false;

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date d1, d2;
            d1 = sdf.parse(StartTime);
            d2 = sdf.parse(EndTime);

            switch (Equaler){
                case ">":
                    if(d1.after(d2))
                        return true;
                    break;
                case ">=":
                    if(d1.after(d2) || d1 == d2)
                        return true;
                    break;
                case "<":
                    if(d1.before(d2))
                        return true;
                    break;
                case "<=":
                    if(d1.before(d2) || d1 == d2)
                        return true;
                    break;
                case "==":
                    if(d1 == d2)
                        return true;
                    break;
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        return false;
    }
    public String getTimeSpacing(int SumSpacing){
        String aTime = "00:" + Integer.toString(SumSpacing);
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            return timeFormat.parse(aTime).toString();
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            return "";
        }
    }
    public Date getDateTimeSpacing(int SumSpacing){
        String aTime = "00:" + Integer.toString(SumSpacing);
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            return timeFormat.parse(aTime);
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            return new Date();
        }
    }
    @SuppressLint("SimpleDateFormat")
    public Date SumTimes(Date Time1, int Time2){
        String TimeA = "", TimeB = "";
        TimeA = Integer.toString(Time1.getHours()) + ":" + Integer.toString(Time1.getMinutes());
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date date1 = timeFormat.parse(TimeA);
            Calendar cal = Calendar.getInstance();
            cal.set(date1.getYear(), date1.getMonth(), date1.getDay(), date1.getHours(), date1.getMinutes(), date1.getSeconds());
            Date now = cal.getTime();
            cal.add(Calendar.SECOND, Time2 * 60);
            Date later = cal.getTime();

            return later;
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            return new Date();
        }
    }
    @SuppressLint("SimpleDateFormat")
    public Date getMaxTime(String Time){
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            Date date1 = timeFormat.parse(Time);

            Calendar cal = Calendar.getInstance();
            cal.set(date1.getYear(), date1.getMonth(), date1.getDay(), date1.getHours(), date1.getMinutes(), date1.getSeconds());
            Date now = cal.getTime();
            cal.add(Calendar.SECOND, 0);
            Date later = cal.getTime();

            return later;
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            return new Date();
        }
    }
    @SuppressLint("SimpleDateFormat")
    public Date getMinTime(String Time){
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            Date date1 = timeFormat.parse(Time);

            Calendar cal = Calendar.getInstance();
            cal.set(date1.getYear(), date1.getMonth(), date1.getDay(), date1.getHours(), date1.getMinutes(), date1.getSeconds());
            Date now = cal.getTime();
            cal.add(Calendar.SECOND, 0);
            Date later = cal.getTime();

            return later;
        }catch (Exception Ex){
            String Er = Ex.getMessage();
            return new Date();
        }
    }
    public boolean KuchiktarMosavi(Date Date1, Date Date2){
        int a = Date1.getHours();
        int aa = Date1.getMinutes();
        int b = Date2.getHours();
        int bb = Date2.getMinutes();

        if(a <= b){
            if(a < b){
                return true;
            }
            if(a == b){
                if(aa <= bb)
                    return true;
            }
        }
        return false;
    }
    @SuppressLint("SimpleDateFormat")
    public String Minus(int day)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date = Calendar.getInstance();
        date.set(y, m - 1, d);
        date.add(Calendar.DATE, (day * -1));
        String[] cDate = dateFormat.format(date.getTime()).split(" ");
        String[] dDate = cDate[0].split("-");
        y = Integer.parseInt(dDate[0]);
        m = Integer.parseInt(dDate[1]);
        d = Integer.parseInt(dDate[2]);

        String mYa = Integer.toString(y);
        String mMa = Integer.toString(m);
        String mDa = Integer.toString(d);

        if(mMa.length() == 1)
            mMa = "0" + mMa;
        if(mDa.length() == 1)
            mDa = "0" + mDa;

        return mYa + "/" + mMa + "/" + mDa;
    }
    @SuppressLint("SimpleDateFormat")
    public String Add(int day)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date = Calendar.getInstance();
        date.set(y, m - 1, d);
        date.add(Calendar.DATE, day);
        String[] cDate = dateFormat.format(date.getTime()).split(" ");
        String[] dDate = cDate[0].split("-");
        y = Integer.parseInt(dDate[0]);
        m = Integer.parseInt(dDate[1]);
        d = Integer.parseInt(dDate[2]);

        String mYa = Integer.toString(y);
        String mMa = Integer.toString(m);
        String mDa = Integer.toString(d);

        if(mMa.length() == 1)
            mMa = "0" + mMa;
        if(mDa.length() == 1)
            mDa = "0" + mDa;

        return mYa + "/" + mMa + "/" + mDa;
    }
    @SuppressLint("SimpleDateFormat")
    public String MinusGetToMiladi(int day)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date = Calendar.getInstance();
        date.set(y, m - 1, d);
        date.add(Calendar.DATE, (day * -1));
        String[] cDate = dateFormat.format(date.getTime()).split(" ");
        String[] dDate = cDate[0].split("-");
        int yy = Integer.parseInt(dDate[0]);
        int mm = Integer.parseInt(dDate[1]);
        int dd = Integer.parseInt(dDate[2]);

        String mYa = Integer.toString(yy);
        String mMa = Integer.toString(mm);
        String mDa = Integer.toString(dd);

        if(mMa.length() == 1)
            mMa = "0" + mMa;
        if(mDa.length() == 1)
            mDa = "0" + mDa;

        return mYa + "-" + mMa + "-" + mDa + "T00:00:00";
    }
    @SuppressLint("SimpleDateFormat")
    public String AddGetToMiladi(int day)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date = Calendar.getInstance();
        date.set(y, m - 1, d);
        date.add(Calendar.DATE, day);
        String[] cDate = dateFormat.format(date.getTime()).split(" ");
        String[] dDate = cDate[0].split("-");
        int yy = Integer.parseInt(dDate[0]);
        int mm = Integer.parseInt(dDate[1]);
        int dd = Integer.parseInt(dDate[2]);

        String mYa = Integer.toString(yy);
        String mMa = Integer.toString(mm);
        String mDa = Integer.toString(dd);

        if(mMa.length() == 1)
            mMa = "0" + mMa;
        if(mDa.length() == 1)
            mDa = "0" + mDa;

        return mYa + "-" + mMa + "-" + mDa + "T00:00:00";
    }
    @SuppressLint("SimpleDateFormat")
    public String MinusGetToShamsi(int day)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date = Calendar.getInstance();
        date.set(y, m - 1, d);
        date.add(Calendar.DATE, (day * -1));
        String[] cDate = dateFormat.format(date.getTime()).split(" ");
        String[] dDate = cDate[0].split("-");
        int yy = Integer.parseInt(dDate[0]);
        int mm = Integer.parseInt(dDate[1]);
        int dd = Integer.parseInt(dDate[2]);

        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(yy);
        pDate.setGrgMonth(mm);
        pDate.setGrgDay(dd);
        String mY = Integer.toString(pDate.getShYear());
        String mM = Integer.toString(pDate.getShMonth());
        String mD = Integer.toString(pDate.getShDay());

        if(mM.length() == 1)
            mM = "0" + mM;
        if(mD.length() == 1)
            mD = "0" + mD;

        return mY + "/" + mM + "/" + mD;
    }
    @SuppressLint("SimpleDateFormat")
    public String AddGetToShamsi(int day)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date = Calendar.getInstance();
        date.set(y, m - 1, d);
        date.add(Calendar.DATE, day);
        String[] cDate = dateFormat.format(date.getTime()).split(" ");
        String[] dDate = cDate[0].split("-");
        int yy = Integer.parseInt(dDate[0]);
        int mm = Integer.parseInt(dDate[1]);
        int dd = Integer.parseInt(dDate[2]);

        PersianDate pDate = new PersianDate();
        pDate.setGrgYear(yy);
        pDate.setGrgMonth(mm);
        pDate.setGrgDay(dd);
        String mY = Integer.toString(pDate.getShYear());
        String mM = Integer.toString(pDate.getShMonth());
        String mD = Integer.toString(pDate.getShDay());

        if(mM.length() == 1)
            mM = "0" + mM;
        if(mD.length() == 1)
            mD = "0" + mD;

        return mY + "/" + mM + "/" + mD;
    }
    @SuppressLint("SimpleDateFormat")
    public int CompareDates(String Date1, String Date2){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(Date1);
            Date date2 = sdf.parse(Date2);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);

            if (cal1.after(cal2)) {
                return 3;
            }
            if (cal1.equals(cal2)) {
                return 2;
            }
            if (cal1.before(cal2)) {
                return 1;
            }
        }catch (Exception Ex){
            return -1;
        }
        return 0;
    }
    @SuppressLint("SimpleDateFormat")
    public int CompareDates(String EndDate){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(y + "-" + m + "-" + d);
            Date date2 = sdf.parse(EndDate);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);

            if (cal1.after(cal2)) {
                return 3;
            }
            if (cal1.equals(cal2)) {
                return 2;
            }
            if (cal1.before(cal2)) {
                return 1;
            }
        }catch (Exception Ex){
            return -1;
        }
        return 0;
    }
    public boolean CompareAlarmTimes(Date aTime, Date cTime){
        int y1 = aTime.getYear();
        int y2 = cTime.getYear();
        int m1 = aTime.getMonth();
        int m2 = cTime.getMonth();
        int d1 = aTime.getDay();
        int d2 = cTime.getDay();
        int h1 = aTime.getHours();
        int h2 = cTime.getHours();
        int mm1 = aTime.getMinutes();
        int mm2 = cTime.getMinutes();
        return ((y1 == y2) &&
                (m1 == m2) &&
                (d1 == d2) &&
                (h1 == h2) &&
                (mm1 == mm2));
    }

}
