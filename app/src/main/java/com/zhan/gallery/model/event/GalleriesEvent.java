package com.zhan.gallery.model.event;

import com.zhan.gallery.model.Gallery;

import java.util.List;

/**
 * Created by zah on 2017/7/24.
 */

public class GalleriesEvent {

    public List<Gallery> galleries;

    public GalleriesEvent(List<Gallery> galleries) {
        this.galleries = galleries;
    }
}
