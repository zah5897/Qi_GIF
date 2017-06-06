package com.zhan.qiwen.model.topic.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.topic.entity.TopicDetail;

public interface CreateTopicView extends BaseView {
    void getNewTopic(TopicDetail topicDetail);
}
