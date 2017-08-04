package com.zhan.gallery.model.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.zhan.gallery.app.Application;
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.model.event.GalleriesEvent;
import com.zhan.gallery.utils.DBHelper;
import com.zhan.gallery.utils.GsonUtil;
import com.zhan.gallery.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Type;
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

    public List<Gallery> tempGalleries;

    public void requestGallery(final int type, final int cursor, int limit) {
        RequestParam params = new RequestParam();
        params.put("pageIndex", cursor);
        params.put("limit", limit);
        requestData(getFullURL("/list/" + type), params, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObj = (JSONObject) object;
                String array = jsonObj.optString("galleries");
                if (cursor == 0) {
                    AppService.get().getDBHelper(Application.getApp()).cacheGallerys(type, array);
                }
                List<Gallery> retList = GsonUtil.toGalleries(array);
                EventBus.getDefault().post(new GalleriesEvent(retList));
            }

            @Override
            public void onFailed(int code, String msg) {
                EventBus.getDefault().post(new GalleriesEvent(null));
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
