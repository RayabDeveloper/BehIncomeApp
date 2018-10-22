package com.behincom.behincome.Datas.Message;

import com.google.gson.annotations.SerializedName;

public class Message_Message {

    @SerializedName("MessageId")
    public int MessageId = 0;
    @SerializedName("MessageText")
    public String MessageText = "";
    @SerializedName("MessageSendDate")
    public String MessageSendDate = "2018-01-01T11:11:11";
    @SerializedName("MessageReadDate")
    public String MessageReadDate = "2018-01-01T11:11:11";
    @SerializedName("MessageConversationId")
    public int MessageConversationId = 0;
    @SerializedName("MessageTitle")
    public String MessageTitle = "";
    @SerializedName("MessageFilePath")
    public String MessageFilePath = "";
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
