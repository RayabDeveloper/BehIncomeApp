package com.behincom.behincome.Datas.Customer;

import com.google.gson.annotations.SerializedName;

public class CustomerAssign {

    @SerializedName("AssignID")
    public int AssignID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
    @SerializedName("MarketerUserID")
    public int MarketerUserID = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @SerializedName("CreationDateTime")
    public String CreationDateTime = "";
    @SerializedName("CreationAccountId")
    public int CreationAccountId = 0;
    @SerializedName("LastUpdateDate")
    public String LastUpdateDate = "";
    @SerializedName("LastUpdateAccountId")
    public int LastUpdateAccountId = 0;
    @SerializedName("DeletedDate")
    public String DeletedDate = "";
    @SerializedName("DeletedAccountId")
    public int DeletedAccountId = 0;


}
