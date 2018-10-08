package com.behincom.behincome.Datas.Customer.ToSend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EditCustomerToSend {

    @SerializedName("CustomerID")
    public int CustomerID = 0;
    public int CustomerOwenerUserID = 0;
    @SerializedName("CustomerStateID")
    public int CustomerStateID = 0;
    @SerializedName("CustomerStatusID")
    public int CustomerStatusID = 0;
    @SerializedName("CityID")
    public int CityID = 0;
    @SerializedName("NamePrefixID")
    public int NamePrefixID = 0;
    @SerializedName("ArchiveDate")
    public String ArchiveDate = "";
    @SerializedName("CustomerName")
    public String CustomerName = "";
    @SerializedName("CustomerAddress")
    public String CustomerAddress = "";
    @SerializedName("CustomerLatitude")
    public double CustomerLatitude = 0.0;
    @SerializedName("CustomerLongitude")
    public double CustomerLongitude = 0.0;
    @SerializedName("CustomerDescription")
    public String CustomerDescription = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @SerializedName("LastUpdateDate")
    public String LastUpdateDate = "";
    @SerializedName("Customers_ActivityFields")
    public List<EditCustomerToSendActivityFields> Customers_ActivityFields = new ArrayList<>();
    @SerializedName("Customers_Images")
    public List<EditCustomerToSendImages> Customers_Images = new ArrayList<>();
    @SerializedName("Customers_Personnel")
    public List<EditCustomerToSendPersonnels> Customers_Personnel = new ArrayList<>();
    @SerializedName("Customers_Properties")
    public List<EditCustomerToSendProperties> Customers_Properties = new ArrayList<>();
    @SerializedName("Customers_Tags")
    public List<EditCustomerToSendTags> Customers_Tags = new ArrayList<>();

}
