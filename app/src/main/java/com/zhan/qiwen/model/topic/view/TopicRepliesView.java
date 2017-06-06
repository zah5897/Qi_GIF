package com.zhan.qiwen.model.topic.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.topic.entity.TopicReply;

import java.util.List;

/**
 * Created by plusend on 2016/11/26.
 */

public interface TopicRepliesView extends BaseView {
    void showReplies(List<TopicReply> topicReplyList);

    void addReplies(List<TopicReply> topicReplyList);

    void showNewReply();
}
