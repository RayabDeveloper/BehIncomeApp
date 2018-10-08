package com.behincom.behincome.Datas.Marketing;

import com.google.gson.annotations.SerializedName;

public class MarketingVisitTours {

    @SerializedName("VisitTourID")
    public int VisitTourID = 0;
    @SerializedName("UserID")
    public int UserID = 0;
    @SerializedName("VisitTourTitle")
    public String VisitTourTitle = "";
    @SerializedName("VisitTourDescription")
    public String VisitTourDescription = "";
    @SerializedName("DateFrom")
    public String DateFrom;
    @SerializedName("DateTo")
    public String DateTo;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
