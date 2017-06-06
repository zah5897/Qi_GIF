package com.zhan.qiwen.model.channel.data;

import android.util.Log;

import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.model.channel.event.ChannelsEvent;
import com.zhan.qiwen.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChannelDataNetwork implements ChannelData {
    private static final String TAG = "NetworkData";
    private static ChannelDataNetwork networkData = new ChannelDataNetwork();
    private ChannelService service;

    private ChannelDataNetwork() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.addInterceptor(new Interceptor() {
            @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (Constant.VALUE_TOKEN != null) {
                    builder.addHeader(Constant.KEY_TOKEN,
                        Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN);
                }
                return chain.proceed(builder.build());
            }
        });
        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
        service = retrofit.create(ChannelService.class);
    }

    public static ChannelDataNetwork getInstance() {
        return networkData;
    }

    @Override public void getTopics(String type, Integer nodeId, Integer offset, Integer limit) {
        Call<List<Channel>> call = service.getChannels(type, nodeId, offset, limit);
        call.enqueue(new Callback<List<Channel>>() {
            @Override public void onResponse(Call<List<Channel>> call,
                Response<List<Channel>> response) {
                if (response.isSuccessful()) {
                    List<Channel> topicList = response.body();
                    Log.v(TAG, "topicList:" + topicList);
                    EventBus.getDefault().post(new ChannelsEvent(topicList));
                } else {
                    Log.e(TAG, "getTopics STATUS: " + response.code());
                    EventBus.getDefault().post(new ChannelsEvent(null));
                }
            }

            @Override public void onFailure(Call<List<Channel>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new ChannelsEvent(null));
            }
        });
    }

    @Override public void getTopTopics() {
        new Thread(new Runnable() {
            @Override public void run() {
//                try {
//                    Document doc = Jsoup.connect("https://www.diycode.cc/").get();
//                    int size = doc.getElementsByClass("fa fa-thumb-tack").size();
//                    Log.d(TAG, "top size: " + size);
//                    Elements elements = doc.getElementsByClass("panel-body");
//                    Elements topics = elements.get(0).children();
//                    Log.d(TAG, "topics size: " + topics.size());
//                    List<Channel> topicList = new ArrayList<>();
//                    for (int i = 0; i < size; i++) {
//                        Element topic = topics.get(i);
//                        Channel temp = new Channel();
//                        String href = topic.getElementsByClass("title media-heading")
//                            .get(0)
//                            .getElementsByTag("a")
//                            .attr("href");
//                        temp.setId(Integer.valueOf(href.substring(href.lastIndexOf("/") + 1)));
//                        temp.setTitle(
//                            topic.getElementsByClass("title media-heading").get(0).text());
//                        temp.setNodeName(topic.getElementsByClass("node").get(0).text());
//                        String time = topic.getElementsByClass("timeago").get(0).attr("title");
//                        StringBuilder sb = new StringBuilder(time);
//                        sb.insert(19, ".000");
//                        time = sb.toString();
//                        temp.setRepliedAt(time);
//                        Channel.User user = new Channel.User();
//                        user.setAvatarUrl(topic.getElementsByTag("img").get(0).attr("src"));
//                        user.setLogin(topic.getElementsByClass("hacknews_clear").get(1).text());
//                        temp.setUser(user);
//                        temp.setPin(true);
//                        topicList.add(temp);
//                    }
//                    EventBus.getDefault().post(new ChannelsEvent(topicList));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    EventBus.getDefault().post(new ChannelsEvent(null));
//                }
            }
        }).start();
    }



}
