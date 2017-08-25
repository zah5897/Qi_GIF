package com.zhan.gallery.model.event;

import com.zhan.gallery.model.Comment;

import java.util.List;

/**
 * Created by zah on 2017/8/4.
 */

public class CommentListEvent {
    public String img_id;
    public List<Comment> comments;

    public CommentListEvent(String img_id, List<Comment> comments) {
        this.img_id = img_id;
        this.comments = comments;
    }
}
