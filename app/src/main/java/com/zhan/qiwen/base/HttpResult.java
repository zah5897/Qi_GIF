package com.zhan.qiwen.base;

import com.google.gson.annotations.SerializedName;
import com.zhan.qiwen.utils.Constant;

/**
 * Created by zah on 2017/6/13.
 */

public class HttpResult {
    @SerializedName("code")
    private int mCode;
    @SerializedName("msg")
    private String mMessage;

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }
    public boolean isCodeInvalid() {
        return mCode != Constant.API_STATUS.OK;
    }
}
