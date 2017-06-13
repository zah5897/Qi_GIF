package com.zhan.qiwen.model.item.data;


import com.zhan.qiwen.model.item.entity.SimpleItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface SimpleItemService {

    /**
     * 获取话题列表
     *
     * @param type   默认值
     * @param offset 默认 0，从第 21 条开始就传 20
     * @param limit  默认 20 范围 [1..150]
     */
    @GET("list/{type}/")
    Call<List<SimpleItem>> getSimpleItems(@Path("type") int type, @Query("offset") Integer offset,
                                          @Query("limit") Integer limit);

}
