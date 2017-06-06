package com.zhan.qiwen.model.channel.event;


import com.zhan.qiwen.model.channel.entity.Channel;

import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class ChannelsEvent {
    private List<Channel> channels;

    public ChannelsEvent(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Channel> getChannelList() {
        return channels;
    }
}
