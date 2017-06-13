package com.zhan.qiwen.model.item.data;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.zhan.qiwen.model.item.entity.SimpleItem;
import com.zhan.qiwen.model.item.event.SimpleItemEvent;
import com.zhan.qiwen.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SimpleItemDataNetwork implements SimpleItemData {
    private static final String TAG = "NetworkData";
    private static SimpleItemDataNetwork networkData = new SimpleItemDataNetwork();
    private SimpleItemService service;

    private SimpleItemDataNetwork() {
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.ROOT_HTTP_PATH + "/data/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .client(client)
                .build();
        service = retrofit.create(SimpleItemService.class);
    }

    public static SimpleItemDataNetwork getInstance() {
        return networkData;
    }

    @Override
    public void getSimpleItems(int type, Integer offset, Integer limit) {
        Call<List<SimpleItem>> call = service.getSimpleItems(type, offset, limit);
        call.enqueue(new Callback<List<SimpleItem>>() {
            @Override
            public void onResponse(Call<List<SimpleItem>> call,
                                   Response<List<SimpleItem>> response) {
                if (response.isSuccessful()) {
                    List<SimpleItem> topicList = response.body();
                    Log.v(TAG, "topicList:" + topicList);
                    EventBus.getDefault().post(new SimpleItemEvent(topicList));
                } else {
                    Log.e(TAG, "getTopics STATUS: " + response.code());
                    EventBus.getDefault().post(new SimpleItemEvent(null));
                }
            }

            @Override
            public void onFailure(Call<List<SimpleItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new SimpleItemEvent(null));
            }
        });
    }

}
