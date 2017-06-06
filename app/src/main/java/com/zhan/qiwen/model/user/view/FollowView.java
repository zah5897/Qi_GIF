package com.zhan.qiwen.model.user.view;


import com.zhan.qiwen.model.base.BaseView;

/**
 * Created by plusend on 2016/12/7.
 */

public interface FollowView extends BaseView {
    void setFollow(boolean isFollowed);

    void setFavorite(boolean isFavorite);
}
