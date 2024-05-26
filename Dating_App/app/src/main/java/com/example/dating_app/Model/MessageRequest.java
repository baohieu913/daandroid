package com.example.dating_app.Model;

import com.google.gson.annotations.SerializedName;

public class MessageRequest {

    @SerializedName("userId")
    private String userId;

    @SerializedName("content")
    private String content;

    public MessageRequest(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    // getters and setters
}
