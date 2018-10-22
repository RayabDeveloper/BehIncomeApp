package com.behincom.behincome.Datas.Message;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Message_Conversation {

    @SerializedName("ConversationId")
    public int ConversationId = 0;
    @SerializedName("ConversationSenderId")
    public int ConversationSenderId = 0;
    @SerializedName("ConversationSenderName")
    public String ConversationSenderName = "";
    @SerializedName("ConversationSenderLogoFilePath")
    public String ConversationSenderLogoFilePath = "";
    @SerializedName("ConversationRecieverId")
    public int ConversationRecieverId = 0;
    @SerializedName("ConversationIsSystem")
    public boolean ConversationIsSystem = false;
    @SerializedName("ConversationBusinessManagerId")
    public int ConversationBusinessManagerId = 0;
    @SerializedName("Message_Message")
    public List<Message_Message> Message_Message = new ArrayList<>();
    @SerializedName("Deleted")
    public boolean Deleted = false;

}
