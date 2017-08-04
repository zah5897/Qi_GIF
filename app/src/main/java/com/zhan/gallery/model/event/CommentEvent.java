package com.zhan.gallery.model.event;

import com.zhan.gallery.model.Comment;

/**
 * Created by zah on 2017/8/4.
 */

public class CommentEvent {
    public String gallery_id;
    public Comment comment;

    public CommentEvent(String gallery_id, Comment comment) {
        this.gallery_id = gallery_id;
        this.comment = comment;
    }
}
