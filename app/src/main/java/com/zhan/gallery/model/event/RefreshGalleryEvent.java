package com.zhan.gallery.model.event;

import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.utils.http.RequestParam;

import java.util.List;

/**
 * Created by zah on 2017/7/28.
 */

public class RefreshGalleryEvent {
    public List<Gallery> galleries;

    public RefreshGalleryEvent(List<Gallery> galleries) {
        this.galleries = galleries;

    }
}
