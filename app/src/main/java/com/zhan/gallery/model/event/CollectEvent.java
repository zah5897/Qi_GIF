package com.zhan.gallery.model.event;

/**
 * Created by zah on 2017/8/21.
 */

public class CollectEvent {
    public String img_id;
    public boolean isCollect;
    public int flag;

    public CollectEvent(String img_id, boolean isCollect, int flag) {
        this.img_id = img_id;
        this.isCollect = isCollect;
        this.flag = flag;
    }
}
