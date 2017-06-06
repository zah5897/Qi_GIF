package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.topic.entity.Topic;

import java.util.List;

public class UserFavoriteTopicsEvent {
    private List<Topic> topicList;

    public UserFavoriteTopicsEvent(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }
}
