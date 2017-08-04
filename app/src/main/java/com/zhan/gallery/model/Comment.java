package com.zhan.gallery.model;

import java.util.Date;

/**
 * Created by zah on 2017/7/31.
 */

public class Comment {

    public long id;
    public int channel;
    public String gallery_id;
    public String content;
    public long uid;
    public Date create_time;
    public int prise_count;
    public User user;

}
