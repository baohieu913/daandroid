package com.example.dating_app.Model;

import com.google.gson.annotations.SerializedName;

public class DisLikeRequest {
    @SerializedName("dislikeUserId")
    private String id;

    public DisLikeRequest(String id) {
        this.id = id;
    }
}
