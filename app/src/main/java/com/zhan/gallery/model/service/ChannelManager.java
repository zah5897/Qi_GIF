package com.zhan.gallery.model.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhan.gallery.model.Channel;
import com.zhan.gallery.utils.GsonUtil;
import com.zhan.gallery.utils.PrefUtil;

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
    private Gson mGson;

    private ChannelManager() {
        allChannels = new ArrayList<>();
        myChannels = new ArrayList<>();
        mGson = new Gson();
        String myChannelStr = PrefUtil.get().getString(PER_KEY_MY_CHANNEL, "");
        List<Channel> cacheData = mGson.fromJson(myChannelStr, new TypeToken<List<Channel>>() {
        }.getType());
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


    public Gson getGson() {
        return mGson;
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
        PrefUtil.get().putString(PER_KEY_MY_CHANNEL, mGson.toJson(getMyChannels()));
    }

    private void initDefaultChannel(List<Channel> channels) {
        if (channels == null) {
            String defaults[] = {"首页", "搞笑图集", "搞笑GIF", "吐槽囧图", "美女图片", "搞笑段子", "娱乐八卦"};
            for (int i = 0; i < defaults.length; i++) {
                Channel channel = new Channel(Channel.TYPE_OTHER_CHANNEL, defaults[i], i);
                if (i == 0) {
                    channel.isDefault = true;
                }
                allChannels.add(channel);
            }
        } else {
            allChannels = channels;
        }
        if (myChannels.size() == 0) {
            int limit = allChannels.size() >= 5 ? 5 : allChannels.size();
            for (int i = 0; i < limit; i++) {
                Channel temp = allChannels.get(i);
                myChannels.add(new Channel(Channel.TYPE_MY_CHANNEL, temp.title, temp.type));
            }
            myChannels.get(0).isDefault = true;
        }
    }
}
