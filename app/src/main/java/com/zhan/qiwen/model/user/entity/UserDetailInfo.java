package com.zhan.qiwen.model.user.entity;

import com.google.gson.annotations.SerializedName;

public class UserDetailInfo {
    @SerializedName("id") private int id;
    @SerializedName("username") private String username;
    @SerializedName("nickname") private String nickname;
    @SerializedName("avatar_url") private String avatarUrl;
    @SerializedName("location") private Object location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }
}
