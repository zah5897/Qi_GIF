package com.zhan.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zah on 2017/7/19.
 */
public class ImageModel implements Parcelable {
    public String id;
    public String thumb_img_url;
    public String title;
    public int thumb_img_width;
    public int thumb_img_height;
    public int channel;
    public List<Node> nodes;

    public int collect_count;
    public int praise_count;
    public int comment_count;

    //local var
    public boolean collected;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.thumb_img_url);
        dest.writeString(this.title);
        dest.writeInt(this.thumb_img_width);
        dest.writeInt(this.thumb_img_height);
        dest.writeInt(this.channel);
        dest.writeList(this.nodes);
        dest.writeInt(this.collect_count);
        dest.writeInt(this.praise_count);
        dest.writeInt(this.comment_count);
    }

    public ImageModel() {
    }

    protected ImageModel(Parcel in) {
        this.id = in.readString();
        this.thumb_img_url = in.readString();
        this.title = in.readString();
        this.thumb_img_width = in.readInt();
        this.thumb_img_height = in.readInt();
        this.channel = in.readInt();
        this.nodes = new ArrayList<Node>();
        in.readList(this.nodes, Node.class.getClassLoader());
        this.collect_count = in.readInt();
        this.praise_count = in.readInt();
        this.comment_count = in.readInt();
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
