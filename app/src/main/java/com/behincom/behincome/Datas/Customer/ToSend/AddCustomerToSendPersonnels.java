package com.behincom.behincome.Datas.Customer.ToSend;

import com.google.gson.annotations.SerializedName;

public class AddCustomerToSendPersonnels {

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
