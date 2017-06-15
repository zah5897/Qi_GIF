package com.zhan.qiwen.model.item.data;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.zhan.qiwen.model.item.entity.Item;
import com.zhan.qiwen.model.item.event.ItemsEvent;
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

public class ItemDataNetwork implements ItemData {
    private static final String TAG = "NetworkData";
    private static ItemDataNetwork networkData = new ItemDataNetwork();
    private ItemService service;

    private ItemDataNetwork() {
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
        service = retrofit.create(ItemService.class);
    }

    public static ItemDataNetwork getInstance() {
        return networkData;
    }

    @Override
    public void getItems(int type, Integer offset, Integer limit) {
        Call<List<Item>> call = service.getItems(type, offset, limit);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call,
                                   Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    List<Item> topicList = response.body();
                    Log.v(TAG, "topicList:" + topicList);
                    EventBus.getDefault().post(new ItemsEvent(topicList));
                } else {
                    Log.e(TAG, "getTopics STATUS: " + response.code());
                    EventBus.getDefault().post(new ItemsEvent(null));
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new ItemsEvent(null));
            }
        });
    }

    @Override
    public void getDetail(int type, String id) {
        Call<List<Item>> call = service.getDetail(type, id);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call,
                                   Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    List<Item> topicList = response.body();
                    Log.v(TAG, "topicList:" + topicList);
                    EventBus.getDefault().post(new ItemsEvent(topicList));
                } else {
                    Log.e(TAG, "getTopics STATUS: " + response.code());
                    EventBus.getDefault().post(new ItemsEvent(null));
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new ItemsEvent(null));
            }
        });
    }

}
