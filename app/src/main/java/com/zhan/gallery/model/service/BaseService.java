package com.zhan.gallery.model.service;

import com.google.gson.Gson;
import com.zhan.gallery.utils.http.HttpHelper;
import com.zhan.gallery.utils.http.RequestParam;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zah on 2017/7/24.
 */

public class BaseService {
    public static String URL_ROOT_PREFIX = "http://117.143.221.190:8899";
//    public static String URL_ROOT_PREFIX = "http://117.143.221.190:8899";

    private ExecutorService cachedThreadPool;

    public BaseService() {
        cachedThreadPool = Executors.newCachedThreadPool();
    }


    public String getFullURL(String subUrl) {
        return URL_ROOT_PREFIX + subUrl;
    }

    public String getFullAvatarURL(String subUrl) {
        return URL_ROOT_PREFIX + "/avatar/" + subUrl;
    }

    public String getFullGalleryThumbURL(String subUrl) {
        return URL_ROOT_PREFIX + "/img/thumb/" + subUrl;
    }

    public String getFullGalleryOriginURL(String subUrl) {
        return URL_ROOT_PREFIX + "/img/origin/" + subUrl;
    }


    public void doTask(Runnable runnable) {
        cachedThreadPool.execute(runnable);
    }

    public void requestData(final String url, final RequestParam params, final Callback callback) {
        doTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String newUrl = url;
                    if (!url.startsWith("http")) {
                        newUrl = getFullURL(url);
                    }
                    String result = HttpHelper.post(newUrl, params);
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    if (code == 0) {
                        if (callback != null)
                            callback.onResult(object);
                    } else {
                        if (callback != null)
                            callback.onFailed(code, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null)
                        callback.onFailed(-1, "网络异常");
                }
            }
        });
    }
}
