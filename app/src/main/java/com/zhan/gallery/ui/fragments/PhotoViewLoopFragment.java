package com.zhan.gallery.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhan.gallery.R;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.model.event.CommentEvent;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.model.service.Callback;
import com.zhan.gallery.ui.adapter.element.Footer;
import com.zhan.gallery.ui.adapter.gallery.CommentFooterViewProvider;
import com.zhan.gallery.ui.adapter.gallery.GalleryCommentAdapter;
import com.zhan.gallery.ui.adapter.gallery.GalleryImageAdapter;
import com.zhan.gallery.ui.base.LazyFragment;
import com.zhan.gallery.utils.DBHelper;
import com.zhan.gallery.utils.GsonUtil;
import com.zhan.gallery.utils.ZLog;
import com.zhan.gallery.utils.http.RequestParam;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zah on 2017/7/28.
 */

public class PhotoViewLoopFragment extends LazyFragment {


    public int curPosition = -1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MultiTypeAdapter adapter;
    private Items items;

    public static PhotoViewLoopFragment get(int position) {
        PhotoViewLoopFragment fragment = new PhotoViewLoopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        adapter.register(Gallery.class, new GalleryImageAdapter());
        adapter.register(Comment.class, new GalleryCommentAdapter());
        registFooter();
    }

    private void registFooter() {
        adapter.register(Footer.class, new CommentFooterViewProvider() {
            @Override
            public void needLoadMore() {
                super.needLoadMore();
                loadComments();
            }
        });
    }


    public void refreshState(int position) {
        curPosition = position;
        if (items != null) {
            items.clear();
            cursor = 0;
            registFooter();
            lazyLoad();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (AppService.get().tempGalleries == null) {
            return;
        }
        if (curPosition >= AppService.get().tempGalleries.size()) {
            return;
        }
        Gallery gallery = AppService.get().tempGalleries.get(curPosition);
        items.add(gallery);
        items.add(new Footer());
        List<Comment> comments = AppService.get().getDBHelper(getContext()).loadGalleryComments(gallery.channelType, gallery._id);
        if (comments.size() > 0) {
            setComments(comments);
        } else {
            loadComments();
        }
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendComment(CommentEvent commentEvent) {
        if (commentEvent.gallery_id.equals(((Gallery) items.get(0))._id)) {
            AppService.get().getDBHelper(getContext()).removeGalleryComments(commentEvent.gallery_id);
            items.add(items.size() - 1, commentEvent.comment);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            adapter.notifyDataSetChanged();
        }
    }

    private void setComments(List<Comment> comments) {
        for (Comment comment : comments) {
            items.add(items.size() - 1, comment);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        }
        if (comments.size() < limit) {
            ((CommentFooterViewProvider) adapter.getBinderByClass(Footer.class)).refreshFooterStatus(Footer.STATUS_NO_MORE);
        } else {
            ((CommentFooterViewProvider) adapter.getBinderByClass(Footer.class)).refreshFooterStatus(Footer.STATUS_NORMAL);
            cursor++;
        }
    }

    private void loadComments() {
        final Gallery gallery = (Gallery) AppService.get().tempGalleries.get(curPosition);
        RequestParam param = new RequestParam();
        param.put("channel", gallery.channelType);
        param.put("galleryId", gallery._id);
        param.put("pageIndex", cursor);
        param.put("limit", limit);
        AppService.get().requestData("/comment/load", param, new Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject jsonObject = (JSONObject) object;
                String jsonArray = jsonObject.optString("comments");
                final List<Comment> comments = GsonUtil.toComments(jsonArray);
                AppService.get().getDBHelper(getContext()).cacheGalleryComments(gallery.channelType, gallery._id, jsonArray);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setComments(comments);
                    }
                });
            }

            @Override
            public void onFailed(int code, String msg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CommentFooterViewProvider) adapter.getBinderByClass(Footer.class)).refreshFooterStatus(Footer.STATUS_ERR);
                    }
                });
            }
        });
    }


}
