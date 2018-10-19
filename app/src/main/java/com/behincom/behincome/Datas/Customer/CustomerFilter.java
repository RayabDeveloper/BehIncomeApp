package com.behincom.behincome.Datas.Customer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomerFilter {

    @SerializedName("CustomerPrefixID")
    public List<Integer> CustomerPrefixID = new ArrayList<>();
    @SerializedName("ActivityFields")
    public List<Integer> ActivityFields = new ArrayList<>();
    @SerializedName("Tags")
    public List<Integer> Tags = new ArrayList<>();
    @SerializedName("CustomerStatues")
    public List<Integer> CustomerStatues = new ArrayList<>();
    @SerializedName("CustomerState")
    public List<Integer> CustomerState = new ArrayList<>();
    @SerializedName("FromCreateDate")
    public String FromCreateDate = "";
    @SerializedName("ToCreateDate")
    public String ToCreateDate = "";
    @SerializedName("FromArchiveDate")
    public String FromArchiveDate = "";
    @SerializedName("ToArchiveDate")
    public String ToArchiveDate = "";
    @SerializedName("ArchiveType")
    public List<Integer> ArchiveType = new ArrayList<>();
    @SerializedName("FromReturnArchiveDate")
    public String FromReturnArchiveDate = "";
    @SerializedName("ToReturnArchiveDate")
    public String ToReturnArchiveDate = "";

}
