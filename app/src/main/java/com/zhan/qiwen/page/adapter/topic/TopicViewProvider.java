package com.zhan.qiwen.page.adapter.topic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.qiwen.R;
import com.zhan.qiwen.model.topic.entity.Topic;
import com.zhan.qiwen.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

public class TopicViewProvider extends ItemViewProvider<Topic, TopicViewProvider.ViewHolder> {

    @NonNull
    @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                                      @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Topic topic) {
        holder.name.setText(topic.getUser().getLogin());
        holder.topic.setText(topic.getNodeName());
        holder.title.setText(topic.getTitle());
        if (topic.getRepliedAt() != null) {
            holder.time.setText(TimeUtil.computePastTime(topic.getRepliedAt()));
        } else {
            holder.time.setText(TimeUtil.computePastTime(topic.getCreatedAt()));
        }
        if (topic.isPin()) {
            holder.pin.setVisibility(View.VISIBLE);
        } else {
            holder.pin.setVisibility(View.GONE);
        }
        Glide.with(holder.avatar.getContext())
            .load(topic.getUser().getAvatarUrl())
            .placeholder(R.mipmap.ic_avatar_error)
            .error(R.mipmap.ic_avatar_error)
            .crossFade()
            .into(holder.avatar);
        holder.itemTopic.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_topic) RelativeLayout itemTopic;
        @BindView(R.id.avatar) ImageView avatar;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.topic) TextView topic;
        @BindView(R.id.time) TextView time;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.pin) TextView pin;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}