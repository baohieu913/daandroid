package com.example.dating_app.Model;

import com.google.gson.annotations.SerializedName;

public class LoginBody {
    @SerializedName("username")
    private String userName;
    @SerializedName("password")
    private String passWord;

    public LoginBody(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
