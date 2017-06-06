package com.zhan.qiwen.model.channel.data;


import com.zhan.qiwen.model.channel.entity.Channel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ChannelService {

    /**
     * 获取话题列表
     *
     * @param type 默认值 last_actived 范围 ["last_actived", "recent", "no_reply", "popular",
     * "excellent"]
     * @param nodeId 如果你需要只看某个节点的，请传此参数
     * @param offset 默认 0，从第 21 条开始就传 20
     * @param limit 默认 20 范围 [1..150]
     */
    @GET("topics.json") Call<List<Channel>> getChannels(@Query("type") String type,
                                                        @Query("node_id") Integer nodeId, @Query("offset") Integer offset,
                                                        @Query("limit") Integer limit);


}
