package com.zhan.qiwen.model.item.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.zhan.qiwen.model.base.BaseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class Item extends BaseModel implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.qw_abstract);
        dest.writeString(this.small_img);
        dest.writeLong(this.create_time != null ? this.create_time.getTime() : -1);
        dest.writeString(this.detail_url);
        dest.writeInt(this.channelType);
        dest.writeList(this.nodes);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.qw_abstract = in.readString();
        this.small_img = in.readString();
        long tmpCreate_time = in.readLong();
        this.create_time = tmpCreate_time == -1 ? null : new Date(tmpCreate_time);
        this.detail_url = in.readString();
        this.channelType = in.readInt();
        this.nodes = new ArrayList<Node>();
        in.readList(this.nodes, Node.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
