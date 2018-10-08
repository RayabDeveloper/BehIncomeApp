package com.behincom.behincome.Datas.BaseData;

import com.behincom.behincome.SQL.RAnnot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic_ActivityFieldGroups {
    @SerializedName("ActivityFieldGroupID")
    public int ActivityFieldGroupID = 0;
    @SerializedName("ActivityFieldGroupTitle")
    public String ActivityFieldGroupTitle = "";
    @SerializedName("ActivityFieldGroupOrder")
    public String ActivityFieldGroupOrder = "";
    @SerializedName("ActivityFieldGroupFontIcon")
    public String ActivityFieldGroupFontIcon = "327";
    @SerializedName("ActivityFieldGroupColor")
    public String ActivityFieldGroupColor = "FFFFFF";
    @SerializedName("ActivityFieldGroupUserId")
    public int ActivityFieldGroupUserId = 0;
    @SerializedName("AdjustedByAdmin")
    public boolean AdjustedByAdmin = true;
    @Expose
    @RAnnot
    public boolean isCheck = false;

}
