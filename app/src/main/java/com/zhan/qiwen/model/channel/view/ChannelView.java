package com.zhan.qiwen.model.channel.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.channel.entity.Channel;

import java.util.List;

public interface ChannelView extends BaseView {
    void loadTopicFinish();

    void showFavorite(boolean bool);

    void showUnFavorite(boolean bool);

    void showFollow(boolean bool);

    void showUnFollow(boolean bool);

    void showLike(boolean bool);

    void showUnLike(boolean bool);

    void showSignIn();

    void showcChannels(List<Channel> channelList);
}
