package com.zhan.qiwen.page.adapter.topic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.qiwen.R;
import com.zhan.qiwen.model.topic.entity.TopicDetail;
import com.zhan.qiwen.model.topic.event.LoadTopicDetailFinishEvent;
import com.zhan.qiwen.model.topic.event.SignInEvent;
import com.zhan.qiwen.page.widget.DWebView;
import com.zhan.qiwen.utils.PrefUtil;
import com.zhan.qiwen.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

public class TopicDetailViewProvider
        extends ItemViewProvider<TopicDetail, TopicDetailViewProvider.ViewHolder> {
    private static final String TAG = "TopicDetailViewProvider";

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_topic_detail, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder,
                                    @NonNull final TopicDetail topicDetail) {
        holder.name.setText(topicDetail.getUser().getLogin());
        holder.time.setText(TimeUtil.computePastTime(topicDetail.getUpdatedAt()));
        holder.title.setText(topicDetail.getTitle());
        Glide.with(holder.avatar.getContext())
                .load(topicDetail.getUser().getAvatarUrl())
                .placeholder(R.mipmap.ic_avatar_error)
                .error(R.mipmap.ic_avatar_error)
                .crossFade()
                .centerCrop()
                .into(holder.avatar);
        holder.topic.setText(topicDetail.getNodeName());
        holder.repliesCount.setText("共收到 " + topicDetail.getRepliesCount() + " 条回复");
        updateLike(topicDetail, holder.like, holder.likeCount, false);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLike(topicDetail, holder.like, holder.likeCount, true);
            }
        });
        if (topicDetail.getLikesCount() > 0) {
            holder.likeCount.setText(topicDetail.getLikesCount() + "");
        }
        updateFavorite(topicDetail, holder.favorite, false);
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFavorite(topicDetail, holder.favorite, true);
            }
        });
        holder.content.loadDetailDataAsync(topicDetail.getBodyHtml(), new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new LoadTopicDetailFinishEvent());
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        holder.avatar.setOnClickListener(listener);
        holder.name.setOnClickListener(listener);
    }

    private void updateFavorite(TopicDetail topicDetail, ImageView imageView, boolean reverse) {
        String loginName = PrefUtil.getMe(imageView.getContext()).getLogin();
        if (TextUtils.isEmpty(loginName) && reverse) {
            EventBus.getDefault().post(new SignInEvent());
            return;
        }
        if (reverse) {
            topicDetail.setFavorited(!topicDetail.isFavorited());
        }
        if (topicDetail.isFavorited()) {
            imageView.setImageResource(R.drawable.ic_favorite);
        } else {
            imageView.setImageResource(R.drawable.ic_favorite_not);
        }
    }

    private void updateLike(TopicDetail topicDetail, ImageView imageView, TextView textView,
                            boolean click) {
        String loginName = PrefUtil.getMe(imageView.getContext()).getLogin();
        if (TextUtils.isEmpty(loginName) && click) {
            EventBus.getDefault().post(new SignInEvent());
            return;
        }
        if (click) {
            topicDetail.setLiked(!topicDetail.isLiked());
            if (topicDetail.isLiked()) {
                topicDetail.setLikesCount(topicDetail.getLikesCount() + 1);
                textView.setText(topicDetail.getLikesCount() + "");
            } else {
                topicDetail.setLikesCount(topicDetail.getLikesCount() - 1);
                if (topicDetail.getLikesCount() > 0) {
                    textView.setText(topicDetail.getLikesCount() + "");
                } else if (topicDetail.getLikesCount() == 0) {
                    textView.setText("");
                }
            }
        } else {
            if (topicDetail.getLikesCount() > 0) {
                textView.setText(topicDetail.getLikesCount() + "");
            }
        }
        if (topicDetail.isLiked()) {
            imageView.setImageResource(R.drawable.ic_like);
        } else {
            imageView.setImageResource(R.drawable.ic_like_not);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.topic)
        TextView topic;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.content)
        DWebView content;
        @BindView(R.id.replies_count)
        TextView repliesCount;
        @BindView(R.id.favorite)
        ImageView favorite;
        @BindView(R.id.like_count)
        TextView likeCount;
        @BindView(R.id.like)
        ImageView like;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}