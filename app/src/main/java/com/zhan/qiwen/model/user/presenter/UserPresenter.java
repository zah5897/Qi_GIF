package com.zhan.qiwen.model.user.presenter;

import android.util.Log;

import com.zhan.qiwen.model.base.BaseData;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.user.event.UserEvent;
import com.zhan.qiwen.model.user.model.UserDataNetwork;
import com.zhan.qiwen.model.user.view.FastRegistView;
import com.zhan.qiwen.utils.PhoneInfoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserPresenter extends BasePresenter {
    private static final String TAG = "UserPresenter";
    private BaseView userView;
    private UserDataNetwork network;

    public UserPresenter(BaseView userView) {
        this.userView = userView;
        this.network = UserDataNetwork.getInstance();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMe(UserEvent event) {
        if (userView instanceof FastRegistView) {
            ((FastRegistView) userView).fastRegist(event.getUserInfo());
        }
    }

    public void getMe() {
        network.getMe();
    }

    public void fastRegist() {
        network.fastRegist(userView.getContext().getPackageName(), PhoneInfoUtils.loadImei(userView.getContext()));
    }

    @Override
    public void start() {
        Log.d(TAG, "register");
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        Log.d(TAG, "unregister");
        EventBus.getDefault().unregister(this);
    }
}
