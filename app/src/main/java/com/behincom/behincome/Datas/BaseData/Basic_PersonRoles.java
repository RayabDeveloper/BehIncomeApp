package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_PersonRoles {
    @SerializedName("PersonRoleID")
    public int PersonRoleID = 0;
    @SerializedName("PersonRoleUserId")
    public int PersonRoleUserId = 0;
    @SerializedName("PersonRoleTitle")
    public String PersonRoleTitle = "";
    @SerializedName("PersonRoleOrder")
    public String PersonRoleOrder = "";
    @SerializedName("PersonRoleFontIcon")
    public String PersonRoleFontIcon = "327";
    @SerializedName("PersonRoleColor")
    public String PersonRoleColor = "FFFFFF";
    @SerializedName("PersonRoleAdjustedByAdmin")
    public boolean PersonRoleAdjustedByAdmin = true;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
