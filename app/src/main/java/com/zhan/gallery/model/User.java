package com.zhan.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zah on 2017/7/5.
 */

public class User implements Parcelable {
    public String id;
    public String token;
    public String avatar;
    public String nickname;
    public String signature;
    public String sex;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.token);
        dest.writeString(this.avatar);
        dest.writeString(this.nickname);
        dest.writeString(this.signature);
        dest.writeString(this.sex);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.token = in.readString();
        this.avatar = in.readString();
        this.nickname = in.readString();
        this.signature = in.readString();
        this.sex = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
