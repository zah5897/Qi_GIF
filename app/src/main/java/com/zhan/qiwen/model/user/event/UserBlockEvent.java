package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.user.entity.UserBlock;

public class UserBlockEvent {
    private UserBlock userBlock;

    public UserBlockEvent(UserBlock userBlock) {
        this.userBlock = userBlock;
    }

    public UserBlock getUserBlock() {
        return userBlock;
    }
}
