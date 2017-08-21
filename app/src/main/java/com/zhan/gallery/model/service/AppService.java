package com.zhan.gallery.model.service;

import android.content.Context;

import com.zhan.gallery.app.Application;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.model.event.ImagesEvent;
import com.zhan.gallery.utils.DBHelper;
import com.zhan.gallery.utils.GsonUtil;
import com.zhan.gallery.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zah on 2017/7/7.
 */

public class AppService extends BaseService {
    public static final String APP_CHECK_VERSION = "/sys/checkVersion";
    private static AppService starService;
    public DBHelper dbHelper;

    private AppService() {
        super();
    }

    public static AppService get() {
        if (starService == null) {
            starService = new AppService();
        }
        return starService;
    }


    public void requestImgs(final int channel, final int cursor, int limit) {
        RequestParam params = new RequestParam();
        params.put("pageIndex", cursor);
        params.put("limit", limit);
        requestData(getFullURL("/data/list/" + channel), params, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObj = (JSONObject) object;
                String array = jsonObj.optString("data");
                if (cursor == 0) {
                    AppService.get().getDBHelper(Application.getApp()).cacheGallerys(channel, array);
                }
                List<ImageModel> retList = GsonUtil.toImages(array);
                EventBus.getDefault().post(new ImagesEvent(retList, channel));
            }

            @Override
            public void onFailed(int code, String msg) {
                EventBus.getDefault().post(new ImagesEvent(null, channel));
            }
        });
    }

    public DBHelper getDBHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }
}
