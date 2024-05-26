package com.example.dating_app.Api;

import com.example.dating_app.Model.DisLikeRequest;
import com.example.dating_app.Model.LikeRequest;
import com.example.dating_app.Model.LoginBody;
import com.example.dating_app.Model.LoginResponse;
import com.example.dating_app.Model.User;
import com.example.dating_app.Model.Message;
import com.example.dating_app.Model.MessageRequest;
import com.example.dating_app.Model.MessageResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.22.105:3000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("/v1/user/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @GET("/v1")
    Call<List<User>> getUsers(@Header("Token") String token);
    @POST("/v1/like")
    Call<String> likeUser(@Header("Token") String token, @Body LikeRequest likeRequest);

    @GET("/v1/likedUsers")
    Call<List<User>> getLikedUsers(@Header("Token") String token);

    @POST("/v1/dislike")
    Call<String> dislikeUser(@Header("Token") String token, @Body DisLikeRequest disLikeRequest);

    @POST("/v1/message/send")
    Call<MessageResponse> sendMessage(@Header("Token") String token, @Body MessageRequest messageRequest);

    @GET("/v1/message")
    Call<List<Message>> getMessages(@Header("Token") String token, @Query("userId") String userId);
}
