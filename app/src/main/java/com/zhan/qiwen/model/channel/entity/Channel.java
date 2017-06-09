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
    public int type;
    public String remoteUrl;
    public String localPath;
    public Channel(int itemType, String title,int type) {
        this.title = title;
        this.itemType = itemType;
        this.type=type;
    }



    @Override
    public boolean equals(Object obj) {

        if(obj==null){
      return  false;
        }
        Channel channel= (Channel) obj;
        return this.title.equals(channel.title);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeString(this.remoteUrl);
        dest.writeString(this.localPath);
    }

    protected Channel(Parcel in) {
        this.title = in.readString();
        this.type = in.readInt();
        this.remoteUrl = in.readString();
        this.localPath = in.readString();
    }

    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
