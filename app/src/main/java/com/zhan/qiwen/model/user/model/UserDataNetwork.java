package com.zhan.qiwen.model.user.model;

import android.util.Log;

import com.zhan.qiwen.model.user.entity.UserInfo;
import com.zhan.qiwen.model.user.event.UserEvent;
import com.zhan.qiwen.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserDataNetwork implements UserData {
    private static final String TAG = "UserNetworkData";
    private static UserDataNetwork userDataNetwork = new UserDataNetwork();
    private UserService userService;

    private UserDataNetwork() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.ROOT_HTTP_PATH + "/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    public static UserDataNetwork getInstance() {
        return userDataNetwork;
    }


    @Override
    public void getMe() {
        Call<UserInfo> call =
                userService.getMe(Constant.VALUE_TOKEN);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call,
                                   Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    UserInfo userDetailInfo = response.body();
                    Log.d(TAG, "userDetailInfo: " + userDetailInfo);
                    EventBus.getDefault().post(new UserEvent(userDetailInfo));
                } else {
                    Log.e(TAG, "getMe STATUS: " + response.code());
                    EventBus.getDefault().post(new UserEvent(null));
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new UserEvent(null));
            }
        });
    }

    public void fastRegist(String from, String device_id) {
        Call<UserInfo> call =
                userService.fastRegist(from, device_id);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call,
                                   Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    UserInfo userInfo = response.body();
                    EventBus.getDefault().post(new UserEvent(userInfo));
                } else {
                    Log.e(TAG, "getMe STATUS: " + response.code());
                    EventBus.getDefault().post(new UserEvent(null));
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new UserEvent(null));
            }
        });
    }
}
