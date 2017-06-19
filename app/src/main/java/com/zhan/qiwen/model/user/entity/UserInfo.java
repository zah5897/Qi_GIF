package com.zhan.qiwen.model.user.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 用于记录用户收藏的用户，屏蔽的用户等
 */
public class UserInfo {

    @SerializedName("id") private int id;
    @SerializedName("nickname") private String nickname;
    @SerializedName("avatar_url") private String avatarUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
