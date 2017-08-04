package com.zhan.gallery.model.event;

/**
 * Created by zah on 2017/7/28.
 */

public class LoopEvent {
    public int channel;
    public int position;

    public LoopEvent(int channel, int position) {
        this.channel = channel;
        this.position = position;
    }
}
