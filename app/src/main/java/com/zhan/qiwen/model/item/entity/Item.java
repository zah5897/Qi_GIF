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

    private String html;

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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    protected Item(Parcel in) {
        id = in.readString();
        title = in.readString();
        qw_abstract = in.readString();
        small_img = in.readString();
        detail_url = in.readString();
        channelType = in.readInt();
        html = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(qw_abstract);
        dest.writeString(small_img);
        dest.writeString(detail_url);
        dest.writeInt(channelType);
        dest.writeString(html);
    }
}
