package com.zhan.qiwen.model.item.entity;

/**
 * Created by zah on 2017/6/15.
 */

public class ImgNode extends  Node{
    public ImgNode(Node node){
        this.type=node.type;
        this.title=node.title;
        this.url=node.url;
    }
}
