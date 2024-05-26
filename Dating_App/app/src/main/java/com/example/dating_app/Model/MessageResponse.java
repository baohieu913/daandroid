package com.example.dating_app.Model;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {

    @SerializedName("message")
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
