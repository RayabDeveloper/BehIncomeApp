package com.behincom.behincome.Datas.BaseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_ArchiveTypes {
    @SerializedName("ArchiveTypeID")
    public int ArchiveTypeID = 0;
    @SerializedName("ArchiveTypeUserId")
    public int ArchiveTypeUserId = 0;
    @SerializedName("ArchiveTypeTitle")
    public String ArchiveTypeTitle = "";
    @SerializedName("ArchiveTypeOrder")
    public String ArchiveTypeOrder = "";
    @SerializedName("ArchiveTypeFontIcon")
    public String ArchiveTypeFontIcon = "327";
    @SerializedName("ArchiveTypeColor")
    public String ArchiveTypeColor = "FFFFFF";
    @SerializedName("AdjustedByAdmin")
    public boolean AdjustedByAdmin = true;
    @SerializedName("Deleted")
    public boolean Deleted = false;
    @Expose
    public boolean isCheck = false;
}
