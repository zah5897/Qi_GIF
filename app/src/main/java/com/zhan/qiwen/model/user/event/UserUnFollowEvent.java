package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.user.entity.UserUnFollow;

public class UserUnFollowEvent {
    private UserUnFollow userUnFollow;

    public UserUnFollowEvent(UserUnFollow userUnFollow) {
        this.userUnFollow = userUnFollow;
    }

    public UserUnFollow getUserUnFollow() {
        return userUnFollow;
    }
}
