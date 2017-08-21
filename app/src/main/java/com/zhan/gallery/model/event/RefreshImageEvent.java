package com.zhan.gallery.model.event;

import com.zhan.gallery.model.ImageModel;

import java.util.List;

/**
 * Created by zah on 2017/7/28.
 */

public class RefreshImageEvent {
    public List<ImageModel> imageModels;

    public RefreshImageEvent(List<ImageModel> imageModels) {
        this.imageModels = imageModels;

    }
}
