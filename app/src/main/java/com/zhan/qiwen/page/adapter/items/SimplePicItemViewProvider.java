package com.zhan.qiwen.page.adapter.items;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.qiwen.R;
import com.zhan.qiwen.model.item.entity.Item;
import com.zhan.qiwen.page.ItemDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class SimplePicItemViewProvider extends ItemViewBinder<Item, SimplePicItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_pic_item, parent, false);
        return new ViewHolder(root);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Item simpleItem) {
        holder.title.setText(simpleItem.getTitle());
        Glide.with(holder.img.getContext())
                .load(simpleItem.getSmall_img())
                .placeholder(R.mipmap.image_loading)
                .error(R.mipmap.image_loading)
                .crossFade()
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
                intent.putExtra("model", simpleItem);
                v.getContext().startActivity(intent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}