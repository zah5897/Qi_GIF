package com.zhan.gallery.model.service;

import android.content.Context;

import com.zhan.gallery.app.Application;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.model.event.CollectEvent;
import com.zhan.gallery.model.event.CommentEvent;
import com.zhan.gallery.model.event.CommentListEvent;
import com.zhan.gallery.model.event.ImagesEvent;
import com.zhan.gallery.model.event.PraiseEvent;
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

    public void collectAction(final ImageModel imageModel, boolean cancel) {
        RequestParam param = new RequestParam();
        param.put("channel", imageModel.channel);
        param.put("img_id", imageModel.id);
        String url = cancel ? "/data/cancel_collect" : "/data/collect";
        requestData(getFullURL(url), param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObj = (JSONObject) object;
                JSONObject data = jsonObj.optJSONObject("data");
                String id = null;
                if (data != null) {
                    id = data.optString("img_id");
                }
                EventBus.getDefault().post(new CollectEvent(id, !imageModel.collected, 1));
            }

            @Override
            public void onFailed(int code, String msg) {
                EventBus.getDefault().post(new CollectEvent(null, false, 1));
            }
        });
    }

    public void praiseAction(final ImageModel imageModel) {
        RequestParam param = new RequestParam();
        param.put("channel", imageModel.channel);
        param.put("img_id", imageModel.id);
        String url = "/data/praise";
        requestData(getFullURL(url), param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObj = (JSONObject) object;
                JSONObject data = jsonObj.optJSONObject("data");
                String id = null;
                if (data != null) {
                    id = data.optString("img_id");
                }
                EventBus.getDefault().post(new PraiseEvent(id));
            }

            @Override
            public void onFailed(int code, String msg) {
                EventBus.getDefault().post(new PraiseEvent(null));
            }
        });
    }

    public void getCollectStatus(final ImageModel imageModel) {
        RequestParam param = new RequestParam();
        param.put("channel", imageModel.channel);
        param.put("img_id", imageModel.id);
        requestData(getFullURL("/data/collect_status"), param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObj = (JSONObject) object;
                JSONObject data = jsonObj.optJSONObject("data");
                String id = null;
                if (data != null) {
                    id = data.optString("img_id");
                }
                boolean is_collected = data.optBoolean("is_collected");
                EventBus.getDefault().post(new CollectEvent(id, is_collected, 0));
            }

            @Override
            public void onFailed(int code, String msg) {
                EventBus.getDefault().post(new CollectEvent(null, false, 0));
            }
        });
    }

    public DBHelper getDBHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    public void loadComment(final ImageModel imageModel, int pageIndex, int limit) {
        RequestParam param = new RequestParam();
        param.put("channel", imageModel.channel);
        param.put("img_id", imageModel.id);
        param.put("pageIndex", pageIndex);
        param.put("limit", limit);
        requestData(getFullURL("/comment/list"), param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObj = (JSONObject) object;
                EventBus.getDefault().post(new CommentListEvent(imageModel.id, GsonUtil.toComments(jsonObj.optString("data"))));
            }

            @Override
            public void onFailed(int code, String msg) {
                EventBus.getDefault().post(new CommentListEvent(imageModel.id, null));
            }
        });
    }

    public void sendComment(final ImageModel imageModel, String content) {
        RequestParam param = new RequestParam();
        param.put("channel", imageModel.channel);
        param.put("img_id", imageModel.id);
        param.put("content", content);
        requestData(getFullURL("/comment/send"), param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObj = (JSONObject) object;
                Comment comment = GsonUtil.toComment(jsonObj.optString("data"));
                EventBus.getDefault().post(new CommentEvent(imageModel.id, comment));
            }

            @Override
            public void onFailed(int code, String msg) {
                EventBus.getDefault().post(new CommentEvent(imageModel.id, null));
            }
        });
    }
}
