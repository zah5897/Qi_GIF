package com.zhan.qiwen.model.channel.data;


import com.zhan.qiwen.model.channel.entity.Channel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ChannelService {

    /**
     * 获取渠道列表
     */
    @GET("list/{version}")
    Call<List<Channel>> getChannels(@Path("version") String version);


}
