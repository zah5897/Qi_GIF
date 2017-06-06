package com.zhan.qiwen.model.user.event;


import com.zhan.qiwen.model.user.entity.Token;

public class RefreshTokenEvent {
    private Token token;

    public RefreshTokenEvent(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
