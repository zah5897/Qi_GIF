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

        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
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

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.ROOT_HTTP_PATH+"/channel/")
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
