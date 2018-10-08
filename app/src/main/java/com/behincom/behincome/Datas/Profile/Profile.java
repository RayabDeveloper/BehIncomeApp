package com.behincom.behincome.Datas.Profile;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("UserID")
    public int UserID = 0;
    @SerializedName("UserProfileTypeID")
    public int UserProfileTypeID = 0;
    @SerializedName("GenderTypeID")
    public int GenderTypeID = 0;
    @SerializedName("MaritalStatusID")
    public int MaritalStatusID = 0;
    @SerializedName("MilitaryStatusID")
    public int MilitaryStatusID = 0;
    @SerializedName("ProvinceID")
    public int ProvinceID = 0;
    @SerializedName("CityID")
    public int CityID = 0;
    @SerializedName("ReferedCode")
    public String ReferedCode = "";
    @SerializedName("RefererID")
    public String RefererID = "";
    @SerializedName("NationalCode")
    public String NationalCode = "";
    @SerializedName("Firstname")
    public String Firstname = "";
    @SerializedName("Lastname")
    public String Lastname = "";
    @SerializedName("BirthDate")
    public String BirthDate = "2018-01-01T00:00:00";
    @SerializedName("Address")
    public String Address = "";
    @SerializedName("PhotoFilename")
    public String PhotoFilename = "";

}
