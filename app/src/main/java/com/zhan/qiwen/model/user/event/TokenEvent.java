package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.user.entity.Token;

/**
 * Created by plusend on 2016/11/28.
 */

public class TokenEvent {
    private Token token;

    public TokenEvent(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
