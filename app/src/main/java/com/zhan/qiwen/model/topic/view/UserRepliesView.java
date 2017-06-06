package com.zhan.qiwen.model.topic.view;

import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.topic.entity.Reply;

import java.util.List;

public interface UserRepliesView extends BaseView {
    void showReplies(List<Reply> replyList);
}
