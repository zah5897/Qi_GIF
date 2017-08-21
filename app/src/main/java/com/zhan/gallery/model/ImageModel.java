package com.zhan.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zah on 2017/7/19.
 */
public class ImageModel implements Parcelable {
    public String _id;
    public String img_url;
    public String txt;
    public int type;//jpg:0,gif:1,video:2
    public int channel;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.img_url);
        dest.writeString(this.txt);
        dest.writeInt(this.type);
        dest.writeInt(this.channel);
    }

    public ImageModel() {
    }

    protected ImageModel(Parcel in) {
        this._id = in.readString();
        this.img_url = in.readString();
        this.txt = in.readString();
        this.type = in.readInt();
        this.channel = in.readInt();
    }

    public static final Parcelable.Creator<ImageModel> CREATOR = new Parcelable.Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel source) {
            return new ImageModel(source);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
