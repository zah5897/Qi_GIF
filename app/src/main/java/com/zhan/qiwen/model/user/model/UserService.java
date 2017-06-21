package com.zhan.qiwen.model.user.model;


import com.zhan.qiwen.model.user.entity.UserInfo;
import com.zhan.qiwen.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface UserService {
    /**
     * 获取当然登录者的资料
     *
     * @param token 当然登录者的 Token
     */
    @GET("users/me.json")
    Call<UserInfo> getMe(@Header("token") String token);

    @POST("fastRegist")
    Call<UserInfo> fastRegist(@Query("from") String from, @Query("device_id") String device_id);
}
