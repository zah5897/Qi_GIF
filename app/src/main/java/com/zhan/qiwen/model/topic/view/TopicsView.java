package com.zhan.qiwen.model.topic.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.topic.entity.Topic;

import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public interface TopicsView extends BaseView {
    void showTopics(List<Topic> topicList);
}
