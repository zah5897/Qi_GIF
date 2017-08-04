package com.zhan.gallery.model.service;

public interface Callback {
    void onResult(Object object);

    void onFailed(int code, String msg);
}