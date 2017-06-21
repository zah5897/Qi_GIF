package com.zhan.qiwen.model.channel.data;

import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.utils.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class ChannelDataNetwork {
    private static final String TAG = "NetworkData";
    private ChannelService service;

    public ChannelDataNetwork() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.ROOT_HTTP_PATH + "/channel/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        service = retrofit.create(ChannelService.class);
    }

    public void getChannels(Callback callback) {
        Call<List<Channel>> call = service.getChannels("v1");
        call.enqueue(callback);
    }
}
