package com.zhan.gallery.model.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhan.gallery.model.Channel;
import com.zhan.gallery.utils.GsonUtil;
import com.zhan.gallery.utils.PrefUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zah on 2017/6/6.
 */

public class ChannelManager {
    public static final String PER_KEY_MY_CHANNEL = "my_channel";
    private static ChannelManager manager;
    private List<Channel> allChannels;
    private List<Channel> myChannels;

    private ChannelManager() {
        allChannels = new ArrayList<>();
        myChannels = new ArrayList<>();
        String myChannelStr = PrefUtil.get().getString(PER_KEY_MY_CHANNEL, "");
        List<Channel> cacheData = GsonUtil.toChannels(myChannelStr);
        if (cacheData != null) {
            myChannels.addAll(cacheData);
        }
    }

    public static ChannelManager get() {
        if (manager == null) {
            manager = new ChannelManager();
            manager.initDefaultChannel(null);
        }
        return manager;
    }


    public void load() {
        AppService.get().requestData("/channel/v1/list", null, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObject = (JSONObject) object;
                List<Channel> topicList = GsonUtil.toChannels(jsonObject.optString("channels"));
                initDefaultChannel(topicList);
            }

            @Override
            public void onFailed(int code, String msg) {
                initDefaultChannel(null);
            }

        });
    }

    public List<Channel> getAllChannels() {
        return allChannels;
    }

    public List<Channel> getMyChannels() {
        return myChannels;
    }


    public List<Channel> getUnSelected() {
        List<Channel> unSelected = new ArrayList<>();
        for (Channel channel : allChannels) {
            if (!myChannels.contains(channel)) {
                channel.setItemType(Channel.TYPE_OTHER_CHANNEL);
                unSelected.add(channel);
            }
        }
        return unSelected;
    }

    public void parcelable(Context context) {
        PrefUtil.get().putString(PER_KEY_MY_CHANNEL, GsonUtil.toJsonStr(getMyChannels()));
    }

    private void initDefaultChannel(List<Channel> channels) {
        if (channels == null) {
            for (ChannelType channelType : ChannelType.values()) {
                Channel channel = new Channel(Channel.TYPE_OTHER_CHANNEL, channelType.getTitle(), channelType.ordinal());
                if (channel.type == 0) {
                    channel.isDefault = true;
                }
                allChannels.add(channel);
            }
        } else {
            allChannels = channels;
        }
        if (myChannels.size() == 0) {
            for (ChannelType channelType : ChannelType.values()) {
                myChannels.add(new Channel(Channel.TYPE_MY_CHANNEL, channelType.getTitle(), channelType.ordinal()));
            }
            myChannels.get(0).isDefault = true;
        }
        EventBus.getDefault().post(myChannels.get(0));
    }

    enum ChannelType {
        HOT(0, "热门"),
        FUNNY(1, "搞笑"),
        JUNTU(2, "囧图"),
        GIF(3, "GIF"),
        NEIHAN(4, "内涵"),
        VIDEO(5, "视频"),
        TUCAO(6, "吐槽"),
        GIRL(7, "美女");

        private String _title;
        private int _value;

        private ChannelType(int value, String title) {
            _value = value;
            _title = title;
        }

        public String getTitle() {
            return _title;
        }

        public int getValue() {
            return _value;
        }
    }

}
