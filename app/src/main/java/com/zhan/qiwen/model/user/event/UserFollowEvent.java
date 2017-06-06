package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.user.entity.UserFollow;

public class UserFollowEvent {
    private UserFollow userFollow;

    public UserFollowEvent(UserFollow userFollow) {
        this.userFollow = userFollow;
    }

    public UserFollow getUserFollow() {
        return userFollow;
    }
}
