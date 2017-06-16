package com.zhan.qiwen.page.adapter.element;

import com.zhan.qiwen.model.base.BaseModel;

public class Header {
    private BaseModel model;
    public Header(){

    }
    public  Header(BaseModel model){
        this.model=model;
    }

    public BaseModel getModel() {
        return model;
    }
}