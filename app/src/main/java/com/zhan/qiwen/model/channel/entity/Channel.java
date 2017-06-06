package com.zhan.qiwen.model.channel.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by plusend on 2016/11/24.
 */

public class Channel extends MultiItemEntity implements Parcelable {
    public static final int TYPE_MY_CHANNEL_TAG = 1;
    public static final int TYPE_OTHER_CHANNEL_TAG = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;
    public String title;
    public String titleCode;
    public String remoteUrl;
    public String localPath;

    public Channel(String title, String titleCode) {
        this(TYPE_MY_CHANNEL, title, titleCode);
    }

    public Channel(int type, String title, String titleCode) {
        this.title = title;
        this.titleCode = titleCode;
        itemType = type;
    }


    protected Channel(Parcel in) {
        title = in.readString();
        titleCode = in.readString();
        remoteUrl = in.readString();
        localPath = in.readString();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==null){
      return  false;
        }
        Channel channel= (Channel) obj;
        return this.title.equals(channel.title);
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(titleCode);
        dest.writeString(remoteUrl);
        dest.writeString(localPath);
    }
}
