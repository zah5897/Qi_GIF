package com.zhan.qiwen.model.item.data;

import com.zhan.qiwen.model.item.entity.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zah on 2017/6/15.
 */

public class ItemCallback implements Callback<List<Item>> {
    private int what;

    public ItemCallback(int what) {
        this.what = what;
    }

    public int getWhat() {
        return what;
    }

    @Override
    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

    }

    @Override
    public void onFailure(Call<List<Item>> call, Throwable t) {

    }
}
