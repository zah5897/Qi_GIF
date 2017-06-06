package com.zhan.qiwen.model.user.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.user.entity.Token;

/**
 * Created by plusend on 2016/11/28.
 */

public interface SignInView extends BaseView {
    void getToken(Token token);
}
