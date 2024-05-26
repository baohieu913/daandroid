package com.example.dating_app.Model;

import com.google.gson.annotations.SerializedName;

public class LikeRequest {
    @SerializedName("likeUserId")
    private String id;

    public LikeRequest(String id) {
        this.id = id;
    }
}
