package com.behincom.behincome.Datas.Customer.ToSend;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ToSendCustomersAssigns {

    @SerializedName("CustomerIDs")
    public List<Integer> CustomerIDs = new ArrayList<>();
    @SerializedName("MarketerIDs")
    public List<Integer> MarketerIDs = new ArrayList<>();

}
