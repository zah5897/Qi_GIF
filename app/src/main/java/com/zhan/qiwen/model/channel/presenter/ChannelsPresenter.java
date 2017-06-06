package com.zhan.qiwen.model.channel.presenter;

import android.util.Log;

import com.zhan.qiwen.model.base.BaseData;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.channel.data.ChannelDataNetwork;
import com.zhan.qiwen.model.channel.event.ChannelsEvent;
import com.zhan.qiwen.model.channel.view.ChannelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChannelsPresenter extends BasePresenter {
    private static final String TAG = "TopicsPresenter";

    private BaseData data;
    private ChannelView channelView;

    public ChannelsPresenter(ChannelView channelView) {
        this.data = ChannelDataNetwork.getInstance();
        this.channelView = channelView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showTopics(ChannelsEvent channelsEvent) {
        channelView.showcChannels(channelsEvent.getChannelList());
    }



    public void getTopics(Integer offset) {
        Log.d(TAG, "getTopics: offset: " + offset);
        ((ChannelDataNetwork) data).getTopics(null, null, offset, null);
    }

    public void getTopTopics() {
        Log.d(TAG, "getTopTopics");
        ((ChannelDataNetwork) data).getTopTopics();
    }

    @Override public void start() {
        Log.d(TAG, "register");
        EventBus.getDefault().register(this);
    }

    @Override public void stop() {
        Log.d(TAG, "unregister");
        EventBus.getDefault().unregister(this);
    }
}
