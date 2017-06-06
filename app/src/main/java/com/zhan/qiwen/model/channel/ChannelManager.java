package com.zhan.qiwen.model.channel;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.utils.PrefUtil;

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

    private ChannelManager(Context context) {
        allChannels = new ArrayList<>();
        myChannels = new ArrayList<>();
        mGson = new Gson();
        String myChannelStr = PrefUtil.getInstance(context).getString(PER_KEY_MY_CHANNEL, "");
        List<Channel> cacheData = mGson.fromJson(myChannelStr, new TypeToken<List<Channel>>() {
        }.getType());
        if (cacheData != null) {
            myChannels.addAll(cacheData);
        }
    }

    public static ChannelManager get(Context context) {
        if (manager == null) {
            manager = new ChannelManager(context);
            manager.initDefaultChannel();
        }
        return manager;
    }

    public static ChannelManager get(){
        return manager;
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
                unSelected.add(channel);
            }
        }
        return unSelected;
    }

    public void parcelable(Context context) {
        PrefUtil.getInstance(context).putString(PER_KEY_MY_CHANNEL, mGson.toJson(getMyChannels()));
    }

    private void initDefaultChannel() {
        String defaults[] = {"推荐", "奇闻趣事", "灵异奇谈", "探索发现", "社会热点", "灵异谜团", "灵异事件", "灵异常识", "鬼话连篇", "民间故事", "神秘地球", "探索宇宙"};
        for (String tag : defaults) {
            allChannels.add(new Channel(Channel.TYPE_OTHER_CHANNEL, tag, ""));
        }
        if(myChannels.size()==0){
           for(int i=0;i<5;i++){
               myChannels.add(new Channel(Channel.TYPE_MY_CHANNEL, defaults[i], ""));
           }
        }
    }
}
