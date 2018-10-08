package com.behincom.behincome.Datas.Customer;

import com.google.gson.annotations.SerializedName;

public class CustomerPersonnel {

    @SerializedName("CustomerPersonnelID")
    public int CustomerPersonnelID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
    @SerializedName("PersonnelRoleID")
    public int PersonnelRoleID = 0;
    @SerializedName("ContactTypeID")
    public int ContactTypeID = 0;
    @SerializedName("Name")
    public String Name = "";
    @SerializedName("PersonnelRoleName")
    public String PersonnelRoleName = "";
    @SerializedName("ContactInfo")
    public String ContactInfo = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;


}
