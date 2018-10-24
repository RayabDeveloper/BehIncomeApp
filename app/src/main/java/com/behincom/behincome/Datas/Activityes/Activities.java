package com.behincom.behincome.Datas.Activityes;

import com.behincom.behincome.Datas.Customer.Customers;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Activities {

    @SerializedName("ActivityID")
    public int ActivityID = 0;
    @SerializedName("ActivityType")
    public int ActivityType = 0;
    @SerializedName("ActivityAddedByUserID")
    public int ActivityAddedByUserID = 0;
    @SerializedName("ActivityOwnerUserID")
    public int ActivityOwnerUserID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
    @SerializedName("Title")
    public String Title = "";
    @SerializedName("ActID")
    public int ActID = 0;
    @SerializedName("Description")
    public String ActivityDescription = "";
    @SerializedName("EnterDate")
    public String EnterDate = "";
    @SerializedName("EnterLatutide")
    public double EnterLatutide = 0.0;
    @SerializedName("EnterLongitude")
    public double EnterLongitude = 0.0;
    @SerializedName("ExitLatitude")
    public double ExitLatitude = 0.0;
    @SerializedName("ExitLongitude")
    public double ExitLongitude = 0.0;
    @SerializedName("ParentID")
    public int ParentID = 0;
    @SerializedName("VisitTourID")
    public int VisitTourID = 0;
    @SerializedName("StateID")
    public int StateID = 0;
    @SerializedName("ActResultID")
    public int ActivityResultID = 0;
    @SerializedName("TodoDate")
    public String  TodoDate = "";
    @SerializedName("ExitDate")
    public String  ExitDate = "";
    @SerializedName("DurationDate")
    public String  DurationDate = "";
    @SerializedName("ActivityPointStateId")//BasicPointStates (( 1 = Wating )
    public int ActivityPointStateId = 0;
    @SerializedName("ActivityPoint")
    public int ActivityPoint = 0;
    @SerializedName("ActivityPointDate")//Date of Accept Point
    public String  ActivityPointDate = "2018-01-01T11:11:11";
    @SerializedName("ActivityPointDecription")
    public String  ActivityPointDecription = "";
    @SerializedName("ActivityEditDateTime")
    public String  ActivityEditDateTime = "2018-01-01T11:11:11";
    @SerializedName("ActivityCreateDate")
    public String  ActivityCreateDate = "2018-01-01T11:11:11";
    @SerializedName("TodoDateEnd")
    public String  TodoDateEnd = "2018-01-01T11:11:11";
    @SerializedName("ActivitySend")
    public boolean  ActivitySend = false;
    @SerializedName("ActivityUserEditId")
    public int ActivityUserEditId = 0;
    @SerializedName("Invoice")
    public List<Invoice> Invoice = new ArrayList<>();
    @SerializedName("Customers")
    public com.behincom.behincome.Datas.Customer.Customers Customers = new com.behincom.behincome.Datas.Customer.Customers();

    public boolean hasReminder = false;

}
