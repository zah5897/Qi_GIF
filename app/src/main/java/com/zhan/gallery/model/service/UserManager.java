package com.zhan.gallery.model.service;

import com.google.gson.Gson;
import com.zhan.gallery.model.User;
import com.zhan.gallery.model.event.LoginEvent;
import com.zhan.gallery.utils.GsonUtil;
import com.zhan.gallery.utils.PrefUtil;
import com.zhan.gallery.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by zah on 2016/10/21.
 */
public class UserManager extends BaseService {
    public static final String TENCENT_APP_ID = "1106247919";
    public static final String CACHE_ACCOUNT_KEY = "account";

    public static final String USER_GET_BY_OPENID = "/user/getByOpenid";
    public static final String USER_REGIST = "/user/regist";
    public static final String USER_EXIST = "/user/isExist";
    public static final String USER_LOGIN = "/user/login";
    public static final String USER_RESET_PWD = "/user/resetPwd";
    public static final String USER_DETAIL = "/user/detail";
    private static UserManager userManager;

    private Map<String, User> userMap;

    private UserManager() {
        userMap = new HashMap<>();
        String cache_json = PrefUtil.get().getString(CACHE_ACCOUNT_KEY, null);
        if (cache_json != null) {
            loginUser = GsonUtil.toUser(cache_json);
        }
    }

    private User loginUser;

    public User getLoginUser() {
        return loginUser;
    }

    public User getUser(String id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        }
        return null;
    }

    public void saveUser(User user) {
        userMap.put(String.valueOf(user.id), user);
    }

    public User getUser(long id) {
        return getUser(String.valueOf(id));
    }

    public static UserManager get() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public void getUserByUserName(final String username, final Callback callback) {
        RequestParam param = new RequestParam();
        param.put("openid", username);
        String url = getFullURL(USER_GET_BY_OPENID);
        requestData(url, param, new Callback() {
            @Override
            public void onResult(Object object) {
                String userJson = ((JSONObject) object).optString("user");
                User user = GsonUtil.toUser(userJson);
                if (user == null) {
                    onFailed(0, "user not exist.");
                    return;
                }
                loginUser = user;
                PrefUtil.get().putString(CACHE_ACCOUNT_KEY, userJson);
                callback.onResult(user);
            }

            @Override
            public void onFailed(int code, String msg) {
                callback.onFailed(code, msg);
            }
        });
    }

    public void regist(RequestParam param, final Callback callback) {
        String url = getFullURL(USER_REGIST);
        requestData(url, param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject userJson = ((JSONObject) object).optJSONObject("user");
                User user = GsonUtil.toUser(userJson.toString());
                loginUser = user;
                PrefUtil.get().putString(CACHE_ACCOUNT_KEY, userJson.toString());
                callback.onResult(user);
                EventBus.getDefault().post(new LoginEvent(false));
            }

            @Override
            public void onFailed(int code, String msg) {
                callback.onFailed(code, msg);
            }
        });

    }

    public void checkExist(final String username, final Callback callback) {

        RequestParam param = new RequestParam();
        param.put("username", username);
        requestData(getFullURL(USER_EXIST), param, new Callback() {
            @Override
            public void onResult(Object object) {
                boolean isExist = ((JSONObject) object).optBoolean("is_exist");
                callback.onResult(isExist);
            }

            @Override
            public void onFailed(int code, String msg) {
                callback.onFailed(code, msg);
            }
        });

    }

    public void login(final String un, final String pwd, final Callback callback) {


        RequestParam param = new RequestParam();
        param.put("username", un);
        param.put("password", pwd);
        requestData(getFullURL(USER_LOGIN), param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject userJson = ((JSONObject) object).optJSONObject("user");
                User user = GsonUtil.toUser(userJson.toString());
                loginUser = user;
                PrefUtil.get().putString(CACHE_ACCOUNT_KEY, userJson.toString());
                callback.onResult(user);
                EventBus.getDefault().post(new LoginEvent(false));
            }

            @Override
            public void onFailed(int code, String msg) {
                callback.onFailed(code, msg);
            }
        });
    }

    public boolean isLogin() {
        return loginUser != null;
    }

    public void logout() {
        loginUser = null;
        PrefUtil.get().remove(CACHE_ACCOUNT_KEY);
        EventBus.getDefault().post(new LoginEvent(false));
    }

    public void resetPwd(final String username, final String newPwdStr, final Callback callback) {

        RequestParam param = new RequestParam();
        param.put("username", username);
        param.put("password", newPwdStr);
        requestData(getFullURL(USER_RESET_PWD), param, new Callback() {
            @Override
            public void onResult(Object object) {
                callback.onResult("success");
            }

            @Override
            public void onFailed(int code, String msg) {
                callback.onFailed(code, msg);
            }
        });
    }

    public void getUserDetail(long uid, Callback callback) {
        RequestParam param = new RequestParam();
        param.put("user_id", uid);
        requestData(getFullURL(USER_DETAIL), param, callback);
    }

}
