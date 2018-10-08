package com.behincom.behincome.Datas.Profile.ToSend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ToSendEditProfileAdmin {

    @SerializedName("UserID")
    public int UserID = 0;
    @SerializedName("CityID")
    public int CityID = 0;
//    @SerializedName("ActivityFieldID")
//    public int ActivityFieldID = 0;
    @SerializedName("LogoFilename")
    public String LogoFilename = "";
    @SerializedName("CompanyTitle")
    public String CompanyTitle = "";
    @SerializedName("ManagerName")
    public String ManagerName = "";
    @SerializedName("EconomicalNumber")
    public String EconomicalNumber = "";
    @SerializedName("NationalCode")
    public String NationalCode = "";
    @SerializedName("OtherActivitieyFields")
    public String OtherActivitieyFields = "";
    @SerializedName("ActivityExperience")
    public String ActivityExperience = "";
    @SerializedName("Phone")
    public String Phone = "";
    @SerializedName("Mobile")
    public String Mobile = "";
    @SerializedName("Email")
    public String Email = "";
    @SerializedName("Website")
    public String Website = "";
    @SerializedName("Address")
    public String Address = "";
    @SerializedName("Description")
    public String Description = "";
    @SerializedName("Latitude")
    public double Latitude = 0.0;
    @SerializedName("Longitude")
    public double Longitude = 0.0;

}
