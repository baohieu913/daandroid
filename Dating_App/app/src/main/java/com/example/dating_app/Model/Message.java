package com.example.dating_app.Model;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("_id")
    private String id;

    @SerializedName("senderId")
    private String senderId;

    @SerializedName("receiverId")
    private String receiverId;

    @SerializedName("content")
    private String content;

    @SerializedName("timestamp")
    private String timestamp;

    // getters and setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
