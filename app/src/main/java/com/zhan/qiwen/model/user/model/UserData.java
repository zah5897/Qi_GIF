package com.zhan.qiwen.model.user.model;


import com.zhan.qiwen.model.base.BaseData;

interface UserData extends BaseData {

    /**
     * 验证账号
     */
    void getToken(String username, String password);

    /**
     * token 过期时获取新的 token
     *
     * @param refresh_token 验证账号时获取的 refresh_token
     */
    void refreshToken(String refresh_token);

    /**
     * 获取当然登录者的资料
     */
    void getMe();

    /**
     * 获取用户详细资料
     *
     * @param loginName 用户登录名
     */
    void getUser(String loginName);





    /**
     * 获取用户收藏的话题列表
     *
     * @param loginName 用户的登录名
     */
    void getUserFavoriteTopics(String loginName, Integer offset, Integer limit);

    /**
     * 获取用户创建的回帖列表
     *
     * @param loginName 用户的登录名
     * @param offset 默认 0，从第 21 条开始就传 20
     * @param limit 默认 20 范围 [1..150]
     */
    void getUserReplies(String loginName, Integer offset, Integer limit);
}
