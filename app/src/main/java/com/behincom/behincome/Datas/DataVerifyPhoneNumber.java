package com.behincom.behincome.Datas;

import com.google.gson.annotations.SerializedName;

public class DataVerifyPhoneNumber {
    @SerializedName("PhoneNumber")
    public String PhoneNumber;
    @SerializedName("VerificationCode")
    public String VerificationCode;
}
