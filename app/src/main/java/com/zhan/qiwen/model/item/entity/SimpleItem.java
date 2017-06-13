package com.zhan.qiwen.model.item.entity;


import java.util.Date;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class SimpleItem {
    protected String id;
    protected String title;
    protected String qw_abstract;
    protected String small_img;
    protected Date create_time;
    protected String detail_url;

    protected int channelType;
    private List<Node> nodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQw_abstract() {
        return qw_abstract;
    }

    public void setQw_abstract(String qw_abstract) {
        this.qw_abstract = qw_abstract;
    }

    public String getSmall_img() {
        return small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
