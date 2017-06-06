package com.zhan.qiwen.model.topic.event;


import com.zhan.qiwen.model.topic.entity.Like;

public class LikeEvent {
    private Like like;

    public LikeEvent(Like like) {
        this.like = like;
    }

    public void setLike(Like like) {
        this.like = like;
    }
}
