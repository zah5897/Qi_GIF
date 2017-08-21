package com.zhan.gallery.ui.adapter.image;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhan.gallery.R;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.ui.ImageDetailActivity;
import com.zhan.gallery.utils.FileUtils;
import com.zhan.gallery.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class ImgDetailViewProvider extends ItemViewBinder<ImageModel, ImgDetailViewProvider.ViewHolder> {
    private RequestOptions requestOptions;

    public ImgDetailViewProvider() {
        requestOptions = new RequestOptions().placeholder(R.mipmap.image_load_bg);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_image_detail, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ImageModel imageModel) {
        holder.title.setText(imageModel.txt);
        Glide.with(holder.itemView.getContext()).load(imageModel.img_url).apply(requestOptions).into(holder.img);

        holder.itemView.findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBuilder requestBuilder = Glide.with(v.getContext()).load(imageModel.img_url);
                requestBuilder.apply(new RequestOptions().priority(Priority.HIGH));
                requestBuilder.into(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        String fileName = imageModel.img_url.substring(imageModel.img_url.lastIndexOf("/"));
                        FileUtils.copyfile(resource, new File(Environment.getExternalStorageDirectory() + "/" + FileUtils.ROOT_DIR + fileName), true);
                        ToastUtil.showText("图片已经保存在" + FileUtils.ROOT_DIR + "目录下！");
                    }
                });
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