package com.zhan.qiwen.model.user.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.user.entity.UserDetailInfo;

/**
 * Created by plusend on 2016/11/28.
 */

public interface UserView extends BaseView {
    void getMe(UserDetailInfo userDetailInfo);
    void getUser(UserDetailInfo userDetailInfo);
}
