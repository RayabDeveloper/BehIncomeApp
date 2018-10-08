package com.behincom.behincome.Datas.Activityes;

import java.util.List;

public class DataGroupTask {

    private List<DataDateSpacing> lSpacing;
    private String Date, DateToSend1, TimeToSend1, DateToSend2, TimeToSend2, Description;
    private boolean isAlarmSet;
    private int MaxStoreCount, SubActId, VisitorTourId;

    public List<DataDateSpacing> lSpacing() {
        return lSpacing;
    }
    public void lSpacing(List<DataDateSpacing> lSpacing) {
        this.lSpacing = lSpacing;
    }

    public String date() {
        return Date;
    }
    public void date(String date) {
        Date = date;
    }

    public String dateToSend1() {
        return DateToSend1;
    }
    public void dateToSend1(String dateToSend1) {
        DateToSend1 = dateToSend1;
    }

    public String timeToSend1() {
        return TimeToSend1;
    }
    public void timeToSend1(String timeToSend1) {
        TimeToSend1 = timeToSend1;
    }

    public String dateToSend2() {
        return DateToSend2;
    }
    public void dateToSend2(String dateToSend2) {
        DateToSend2 = dateToSend2;
    }

    public String timeToSend2() {
        return TimeToSend2;
    }
    public void timeToSend2(String timeToSend2) {
        TimeToSend2 = timeToSend2;
    }

    public String description() {
        return Description;
    }
    public void description(String description) {
        Description = description;
    }

    public boolean alarmSet() {
        return isAlarmSet;
    }
    public void alarmSet(boolean alarmSet) {
        isAlarmSet = alarmSet;
    }

    public int maxStoreCount() {
        return MaxStoreCount;
    }
    public void maxStoreCount(int maxStoreCount) {
        MaxStoreCount = maxStoreCount;
    }

    public int subActId() {
        return SubActId;
    }
    public void subActId(int subActId) {
        SubActId = subActId;
    }

    public int visitorTourId() {
        return VisitorTourId;
    }
    public void visitorTourId(int visitorTourId) {
        VisitorTourId = visitorTourId;
    }

}
