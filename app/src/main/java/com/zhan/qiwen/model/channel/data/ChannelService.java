package com.zhan.qiwen.model.channel.data;


import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.model.topic.entity.FavoriteTopic;
import com.zhan.qiwen.model.topic.entity.FollowTopic;
import com.zhan.qiwen.model.topic.entity.Like;
import com.zhan.qiwen.model.topic.entity.Topic;
import com.zhan.qiwen.model.topic.entity.TopicDetail;
import com.zhan.qiwen.model.topic.entity.TopicReply;
import com.zhan.qiwen.model.topic.entity.UnFavoriteTopic;
import com.zhan.qiwen.model.topic.entity.UnFollowTopic;

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
