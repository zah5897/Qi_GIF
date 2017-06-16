package com.zhan.qiwen.model.item.entity;

import java.util.Date;

/**
 * Created by zah on 2017/6/15.
 */

public class TitleNode extends Node {
    public String time;
    public String from;

    public TitleNode(Node node) {
        this.type = node.type;
        this.title = node.title;
        this.url = node.url;
    }

    public TitleNode(String title) {
        this.type = -1;
        this.title = title;
    }

    public TitleNode(String title, Date time) {
        this.type = -1;
        this.title = title;
        if (time != null) {
            this.time = time.toLocaleString();
        }
    }

    public TitleNode(String title, Date time, String from) {
        this.type = -1;
        this.title = title;
        if (time != null) {
            this.time = time.toLocaleString();
        }
        this.from = from;
    }

}
