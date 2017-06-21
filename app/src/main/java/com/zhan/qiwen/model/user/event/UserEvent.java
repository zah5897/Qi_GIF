package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.user.entity.UserInfo;

/**
 * Created by plusend on 2016/11/28.
 */

public class UserEvent {
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public UserEvent(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
