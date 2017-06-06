package com.zhan.qiwen.model.topic.presenter;

import android.util.Log;

import com.zhan.qiwen.model.base.BaseData;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.topic.event.RepliesEvent;
import com.zhan.qiwen.model.topic.view.UserRepliesView;
import com.zhan.qiwen.model.user.model.UserDataNetwork;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserRepliesPresenter extends BasePresenter {
    private static final String TAG = "RepliesPresenter";
    private UserRepliesView userRepliesView;
    private BaseData data;

    public UserRepliesPresenter(UserRepliesView userRepliesView) {
        this.userRepliesView = userRepliesView;
        data = UserDataNetwork.getInstance();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void showReplies(RepliesEvent event) {
        Log.d(TAG, "showReplies");
        userRepliesView.showReplies(event.getReplyList());
        EventBus.getDefault().removeStickyEvent(event);
    }

    public void getReplies(String loginName, Integer offset) {
        ((UserDataNetwork) data).getUserReplies(loginName, offset, null);
    }

    @Override public void start() {
        EventBus.getDefault().register(this);
    }

    @Override public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
