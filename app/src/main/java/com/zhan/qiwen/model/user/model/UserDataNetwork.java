package com.zhan.qiwen.model.user.model;

import android.util.Log;

import com.zhan.qiwen.model.topic.entity.Reply;
import com.zhan.qiwen.model.topic.entity.Topic;
import com.zhan.qiwen.model.topic.event.RepliesEvent;
import com.zhan.qiwen.model.user.entity.Token;
import com.zhan.qiwen.model.user.entity.UserDetailInfo;
import com.zhan.qiwen.model.user.entity.UserInfo;
import com.zhan.qiwen.model.user.event.MeEvent;
import com.zhan.qiwen.model.user.event.RefreshTokenEvent;
import com.zhan.qiwen.model.user.event.TokenEvent;
import com.zhan.qiwen.model.user.event.UserDetailInfoEvent;
import com.zhan.qiwen.model.user.event.UserFavoriteTopicsEvent;
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        userService = retrofit.create(UserService.class);
    }

    public static UserDataNetwork getInstance() {
        return userDataNetwork;
    }

    @Override public void getToken(String username, String password) {
        Call<Token> call =
            userService.getToken(Constant.VALUE_CLIENT_ID, Constant.VALUE_CLIENT_SECRET,
                Constant.VALUE_GRANT_TYPE_PASSWORD, username, password);
        call.enqueue(new Callback<Token>() {
            @Override public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    //Log.d(TAG, "token: " + token);
                    EventBus.getDefault().post(new TokenEvent(token));
                } else {
                    EventBus.getDefault().post(new TokenEvent(null));
                    Log.e(TAG, "getToken STATUS: " + response.code());
                }
            }

            @Override public void onFailure(Call<Token> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                EventBus.getDefault().post(new TokenEvent(null));
            }
        });
    }

    @Override public void refreshToken(String refresh_token) {
        Call<Token> call =
            userService.refreshToken(Constant.VALUE_CLIENT_ID, Constant.VALUE_CLIENT_SECRET,
                Constant.VALUE_GRANT_TYPE_REFRESH_TOKEN, refresh_token);
        call.enqueue(new Callback<Token>() {
            @Override public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    EventBus.getDefault().post(new RefreshTokenEvent(token));
                } else {
                    EventBus.getDefault().post(new RefreshTokenEvent(null));
                    Log.e(TAG, "refreshToken STATUS: " + response.code());
                }
            }

            @Override public void onFailure(Call<Token> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                EventBus.getDefault().post(new RefreshTokenEvent(null));
            }
        });
    }

    @Override public void getMe() {
        Call<UserDetailInfo> call =
            userService.getMe(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN);
        call.enqueue(new Callback<UserDetailInfo>() {
            @Override public void onResponse(Call<UserDetailInfo> call,
                Response<UserDetailInfo> response) {
                if (response.isSuccessful()) {
                    UserDetailInfo userDetailInfo = response.body();
                    Log.d(TAG, "userDetailInfo: " + userDetailInfo);
                    EventBus.getDefault().post(new MeEvent(userDetailInfo));
                } else {
                    Log.e(TAG, "getMe STATUS: " + response.code());
                    EventBus.getDefault().post(new MeEvent(null));
                }
            }

            @Override public void onFailure(Call<UserDetailInfo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new MeEvent(null));
            }
        });
    }

    @Override public void getUser(String loginName) {
        Call<UserDetailInfo> call =
            userService.getUser(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName);
        call.enqueue(new Callback<UserDetailInfo>() {
            @Override public void onResponse(Call<UserDetailInfo> call,
                Response<UserDetailInfo> response) {
                if (response.isSuccessful()) {
                    UserDetailInfo userDetailInfo = response.body();
                    Log.d(TAG, "user: " + userDetailInfo);
                    EventBus.getDefault().postSticky(new UserDetailInfoEvent(userDetailInfo));
                } else {
                    Log.e(TAG, "getUser STATUS: " + response.code());
                    EventBus.getDefault().postSticky(new UserDetailInfoEvent(null));
                }
            }

            @Override public void onFailure(Call<UserDetailInfo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().postSticky(new UserDetailInfoEvent(null));
            }
        });
    }



    @Override public void getUserFavoriteTopics(String loginName, Integer offset, Integer limit) {
        Call<List<Topic>> call = userService.getUserFavoriteTopics(loginName, offset, limit);

        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {
                    List<Topic> topicList = response.body();
                    Log.v(TAG, "topicList:" + topicList);
                    EventBus.getDefault().post(new UserFavoriteTopicsEvent(topicList));
                } else {
                    Log.e(TAG, "getUserFavoriteTopics STATUS: " + response.code());
                    EventBus.getDefault().post(new UserFavoriteTopicsEvent(null));
                }
            }

            @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new UserFavoriteTopicsEvent(null));
            }
        });
    }

    @Override public void getUserReplies(String loginName, Integer offset, Integer limit) {
        Call<List<Reply>> call = userService.getUserReplies(loginName, offset, limit);
        call.enqueue(new Callback<List<Reply>>() {
            @Override
            public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
                if (response.isSuccessful()) {
                    List<Reply> replyList = response.body();
                    Log.v(TAG, "replyList: " + replyList);
                    EventBus.getDefault().postSticky(new RepliesEvent(replyList));
                } else {
                    Log.e(TAG, "getUserReplies STATUS: " + response.code());
                    EventBus.getDefault().postSticky(new RepliesEvent(null));
                }
            }

            @Override public void onFailure(Call<List<Reply>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().postSticky(new RepliesEvent(null));
            }
        });
    }
}
