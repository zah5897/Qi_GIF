package com.zhan.qiwen.model.topic.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.topic.entity.TopicDetail;

public interface TopicView extends BaseView {
    void showTopic(TopicDetail topicDetail);

    void loadTopicFinish();

    void showFavorite(boolean bool);

    void showUnFavorite(boolean bool);

    void showFollow(boolean bool);

    void showUnFollow(boolean bool);

    void showLike(boolean bool);

    void showUnLike(boolean bool);

    void showSignIn();
}
