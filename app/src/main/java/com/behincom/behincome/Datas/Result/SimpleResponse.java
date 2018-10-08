package com.behincom.behincome.Datas.Result;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleResponse {

    @SerializedName("Type")
    public String Type = "";
    @SerializedName("Description")
    public String Description = "";
    @SerializedName("Errors")
    public Map<String, Object> Errors = new HashMap<>();
    @SerializedName("AdditionalData")
    public Map<String, Object> AdditionalData = new HashMap<>();

}
