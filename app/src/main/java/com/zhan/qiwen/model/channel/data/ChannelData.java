package com.zhan.qiwen.model.channel.data;


import com.zhan.qiwen.model.base.BaseData;

interface ChannelData extends BaseData {

    /**
     * 获取话题列表
     *
     * @param type 默认值 last_actived 范围 ["last_actived", "recent", "no_reply", "popular",
     * "excellent"]
     * @param nodeId 如果你需要只看某个节点的，请传此参数
     * @param offset 默认 0，从第 21 条开始就传 20
     * @param limit 默认 20 范围 [1..150]
     */
    void getTopics(String type, Integer nodeId, Integer offset, Integer limit);

    /**
     * 获取置顶话题
     */
    void getTopTopics();


}
