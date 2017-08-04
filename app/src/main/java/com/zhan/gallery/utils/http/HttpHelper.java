package com.zhan.gallery.utils.http;

import com.zhan.gallery.app.Application;
import com.zhan.gallery.model.service.UserManager;
import com.zhan.gallery.utils.DeviceUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zah on 2016/11/23.
 */
public class HttpHelper {
    public static final int HTTP_CONNECT_TIME_OUT = 10;
    public static final int HTTP_READ_TIME_OUT = 10;


    private static RequestParam addIntereaptParam(RequestParam param) {
        if (param == null) {
            param = new RequestParam();
        }
        param.put("device_id", DeviceUtils.loadImei());
        param.put("version", DeviceUtils.getVersionCode());
        param.put("app", Application.getApp().getPackageName());
        if (UserManager.get().isLogin()) {
            param.put("uid", UserManager.get().getLoginUser().id);
            param.put("token", UserManager.get().getLoginUser().token);
        }
        return param;
    }

    public static String post(String url, RequestParam params) throws IOException {
        Response response = buildAndExecu(url, params);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("response is not success.");
        }
    }

    private static Response buildAndExecu(String url, RequestParam params) throws IOException {
        RequestBody requestBody = addIntereaptParam(params).prase();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIME_OUT, TimeUnit.SECONDS).writeTimeout(HTTP_READ_TIME_OUT * 2, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return client.newCall(request).execute();
    }


}
