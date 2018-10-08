package com.behincom.behincome.Datas.Result;

import com.google.gson.annotations.SerializedName;

public class Loginer {
    @SerializedName("access_token")
    public String access_token = "";
    @SerializedName("token_type")
    public String token_type = "";
    @SerializedName("expires_in")
    public int expires_in = 0;
    @SerializedName("userName")
    public String userName = "";
    @SerializedName(".issued")
    public String issued = "";
    @SerializedName(".expires")
    public String expires = "";
}
