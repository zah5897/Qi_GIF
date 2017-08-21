package com.zhan.gallery.ui.adapter.image;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhan.gallery.R;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.service.AppService;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zah on 2017/7/31.
 */

public class GalleryCommentAdapter extends ItemViewBinder<Comment, GalleryCommentAdapter.ViewHolder> {

    private RequestOptions options = new RequestOptions().placeholder(R.mipmap.default_avatar);

    @NonNull
    @Override
    protected GalleryCommentAdapter.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_detail_item_comment, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull GalleryCommentAdapter.ViewHolder holder, @NonNull Comment item) {
        if (!TextUtils.isEmpty(item.user.avatar)) {
            if (item.user.avatar.startsWith("http")) {
                Glide.with(holder.itemView.getContext()).load(item.user.avatar).apply(options).
                        into(holder.avatar);
            } else {
                Glide.with(holder.itemView.getContext()).load(AppService.get().getFullAvatarURL(item.user.avatar)).apply(options).
                        into(holder.avatar);
            }
        }
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
