package com.zhan.qiwen.model.channel;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhan.qiwen.model.channel.data.ChannelDataNetwork;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.utils.PrefUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
             manager.initDefaultChannel(null);
        }
        return manager;
    }


    public void load() {
        final String[] threadName = {Thread.currentThread().getName()};
        ChannelDataNetwork channelDataNetwork = new ChannelDataNetwork();
        channelDataNetwork.getChannels(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call,
                                   Response<List<Channel>> response) {
                String threadNames=Thread.currentThread().getName();
                List<Channel> topicList = null;
                if (response.isSuccessful()) {
                    topicList = response.body();
                }
                initDefaultChannel(topicList);
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                initDefaultChannel(null);
            }
        });
        int i=0;
        System.out.print("i="+i);
    }

    public static ChannelManager get() {
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

    private void initDefaultChannel(List<Channel> channels) {
        if(channels==null){
            String defaults[] = {"奇闻趣事", "社会奇闻", "历史趣事", "神仙奇谈", "未解之谜", "奇图说事", "娱乐八卦", "军事天地"};
            for (int i = 0; i < defaults.length; i++) {
                allChannels.add(new Channel(Channel.TYPE_OTHER_CHANNEL, defaults[i], i + 1));
            }
        }else{
            allChannels=channels;
        }
        if (myChannels.size() == 0) {
            int limit=allChannels.size()>=5?5:allChannels.size();
            for (int i = 0; i < limit; i++) {
                Channel temp=allChannels.get(i);
                myChannels.add(new Channel(Channel.TYPE_MY_CHANNEL, temp.title,temp.type));
            }
        }
    }
}
