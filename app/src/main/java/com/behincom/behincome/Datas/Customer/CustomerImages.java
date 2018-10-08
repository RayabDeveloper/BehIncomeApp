package com.behincom.behincome.Datas.Customer;

import com.google.gson.annotations.SerializedName;

public class CustomerImages {

    @SerializedName("CustomerImageID")
    public int CustomerImageID = 0;
    @SerializedName("CustomerID")
    public int CustomerID = 0;
    @SerializedName("ImageFilename")
    public String ImageFilename = "";
    @SerializedName("ImagePrefix")
    public int ImagePrefix = 0;
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
