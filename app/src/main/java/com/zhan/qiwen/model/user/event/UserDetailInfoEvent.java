package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.user.entity.UserDetailInfo;

public class UserDetailInfoEvent {
    private UserDetailInfo userDetailInfo;

    public UserDetailInfoEvent(UserDetailInfo userDetailInfo) {
        this.userDetailInfo = userDetailInfo;
    }

    public UserDetailInfo getUserDetailInfo() {
        return userDetailInfo;
    }
}
