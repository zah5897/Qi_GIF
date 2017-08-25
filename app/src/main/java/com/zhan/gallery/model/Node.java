package com.zhan.gallery.model;

/**
 * Created by zah on 2017/6/7.
 */
public class Node {
    protected int type = 0;
    protected String txt;
    protected String url;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
