package com.zhan.qiwen.model.topic.event;


import com.zhan.qiwen.model.topic.entity.Like;

public class UnLikeEvent {
    private Like like;

    public UnLikeEvent(Like like) {
        this.like = like;
    }

    public Like getLike() {
        return like;
    }
}
