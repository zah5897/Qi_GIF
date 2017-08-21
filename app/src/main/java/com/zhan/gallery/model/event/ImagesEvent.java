package com.zhan.gallery.model.event;

import com.zhan.gallery.model.ImageModel;

import java.util.List;

/**
 * Created by zah on 2017/7/24.
 */

public class ImagesEvent {

    public List<ImageModel> imageModels;
    public int channel;

    public ImagesEvent(List<ImageModel> imageModels, int channel) {
        this.imageModels = imageModels;
        this.channel = channel;
    }
}
