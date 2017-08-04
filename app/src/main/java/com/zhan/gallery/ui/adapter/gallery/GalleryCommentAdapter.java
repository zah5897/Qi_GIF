package com.zhan.gallery.ui.adapter.gallery;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhan.gallery.R;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.ui.adapter.GalleryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zah on 2017/7/31.
 */

public class GalleryCommentAdapter extends ItemViewBinder<Comment, GalleryCommentAdapter.ViewHolder> {
    @NonNull
    @Override
    protected GalleryCommentAdapter.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_detail_item_comment, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull GalleryCommentAdapter.ViewHolder holder, @NonNull Comment item) {
        holder.avatar.setImageResource(R.mipmap.default_avatar);
        holder.nick_name.setText(item.user.nickname);
        holder.content.setText(item.content);
        holder.create_time.setText(item.create_time.toLocaleString());


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.nick_name)
        TextView nick_name;
        @BindView(R.id.create_time)
        TextView create_time;
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
