package com.zhan.gallery.model.event;

import com.zhan.gallery.model.Comment;

import java.util.List;

/**
 * Created by zah on 2017/8/4.
 */

public class CommentEvent {
    public String img_id;
    public Comment comment;

    public CommentEvent(String img_id, Comment comment) {
        this.img_id = img_id;
        this.comment = comment;
    }
}
