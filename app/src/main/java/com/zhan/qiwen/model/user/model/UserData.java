package com.zhan.qiwen.model.user.model;


import com.zhan.qiwen.model.base.BaseData;

interface UserData extends BaseData {


    /**
     * 获取当然登录者的资料
     */
    void getMe();


}
