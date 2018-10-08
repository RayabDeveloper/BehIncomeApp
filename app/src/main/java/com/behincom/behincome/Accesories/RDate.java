package com.behincom.behincome.Accesories;

public class RDate {

    private static String mDate = "";

    public RDate(String Date){
        mDate = Date;
    }

    public String getMiladiDateToString() {
        String[] obj = mDate.split(" ");
        String DayOfWeek = obj[0].substring(0, 3);
        String DayOfMonth = obj[1];
        String Month = obj[2];
        String Year = obj[3];
        String[] Times = obj[4].split(":");
        String Hour = Times[0];
        String Minute = Times[1];
        String Second = Times[2];
        return Year + "-" + convertMonthStringToInt(Month) + "-" + DayOfMonth + "T" + Hour + ":" + Minute + ":" + Second;
    }

    private String convertMonthStringToInt(String Month){
        switch (Month){
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
        }
        return "";
    }
}
