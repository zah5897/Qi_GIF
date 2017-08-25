package com.zhan.gallery.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.gallery.R;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.model.User;
import com.zhan.gallery.model.event.CollectEvent;
import com.zhan.gallery.model.event.CommentEvent;
import com.zhan.gallery.model.event.CommentListEvent;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.model.service.UserManager;
import com.zhan.gallery.ui.adapter.element.Footer;
import com.zhan.gallery.ui.adapter.element.FooterCommentViewProvider;
import com.zhan.gallery.ui.adapter.image.ImgCommentAdapter;
import com.zhan.gallery.ui.adapter.image.ImgDetailViewProvider;
import com.zhan.gallery.ui.base.BaseSwipeBackActivity;
import com.zhan.gallery.ui.user.LoginActivity;
import com.zhan.gallery.ui.widget.DividerListItemDecoration;
import com.zhan.gallery.ui.widget.SpacesItemDecoration;
import com.zhan.gallery.utils.AppUtil;
import com.zhan.gallery.utils.DBHelper;
import com.zhan.gallery.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zah on 2017/8/21.
 */

public class ImageDetailActivity extends BaseSwipeBackActivity {

    ImageModel imageModel;
    @BindView(R.id.title_bar_left_btn)
    ImageView titleBarLeftBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_btn)
    TextView rightBtn;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.input)
    EditText input;

    private MultiTypeAdapter adapter;
    private Items items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        imageModel = getIntent().getParcelableExtra("model");
        ButterKnife.bind(this);
        title.setText(imageModel.title);
        initView();
        AppService.get().dbHelper = new DBHelper(getApplicationContext());
        EventBus.getDefault().register(this);
        if (UserManager.get().isLogin()) {
            AppService.get().getCollectStatus(imageModel);
        }
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    sendComment();
                    AppUtil.hiddenSoftInput(v);
                    return true;
                }
                return false;
            }
        });
        AppService.get().loadComment(imageModel, cursor, limit);
    }

    private void initView() {
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        adapter.register(ImageModel.class, new ImgDetailViewProvider());
        adapter.register(Comment.class, new ImgCommentAdapter());
        adapter.register(Footer.class, new FooterCommentViewProvider());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, 1));
        recyclerView.setAdapter(adapter);
        items.add(imageModel);
        items.add(new Footer(Footer.STATUS_NORMAL));
        adapter.notifyDataSetChanged();
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendComment(CommentEvent event) {
        if (event.comment != null) {
            Object footer = items.get(items.size() - 1);
            if (footer instanceof Footer) {
                Footer footer1 = (Footer) footer;
                if (footer1.status != Footer.STATUS_NORMAL) {
                    items.add(items.size() - 1, event.comment);
                    footer1.status = Footer.STATUS_NO_MORE;
                    adapter.notifyItemChanged(items.size() - 2);
                    adapter.notifyItemChanged(items.size() - 1);

                }
            }
            input.setText("");
            showToast("发送成功");
        } else {
            showToast("发送失败");
        }
    }


    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListComment(CommentListEvent event) {
        setData(event.comments);
    }

    private void setData(List<Comment> comments) {
        if (comments == null) {
            ((Footer) items.get(items.size() - 1)).status = Footer.STATUS_ERR;
            adapter.notifyDataSetChanged();
            return;
        }

        if (cursor == 0 && comments.size() == 0) {
            ((Footer) items.get(items.size() - 1)).status = Footer.STATUS_NODATA;
            adapter.notifyDataSetChanged();
            return;
        }

        for (Comment comment : comments) {
            // 插入 FooterView 前面
            items.add(items.size() - 1, comment);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        }
        if (comments.size() < limit) {
            ((Footer) items.get(items.size() - 1)).status = Footer.STATUS_NO_MORE;
        } else {
            ((Footer) items.get(items.size() - 1)).status = (Footer.STATUS_NORMAL);
            cursor++;
        }
        adapter.notifyItemChanged(adapter.getItemCount());
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollect(CollectEvent event) {
        if (!TextUtils.isEmpty(event.img_id) && event.img_id.equals(imageModel.id)) {
            imageModel.collected = event.isCollect;
            if (event.flag == 1) {
                if (event.isCollect) {
                    imageModel.collect_count++;
                } else {
                    int nowCount = imageModel.collect_count - 1;
                    if (nowCount >= 0) {
                        imageModel.collect_count = nowCount;
                    }
                }
            } else {
                adapter.notifyItemChanged(0);
            }
            if (event.isCollect && imageModel.collect_count == 0) {
                imageModel.collect_count = 1;
            }
        }
    }

    @OnClick({R.id.title_bar_left_btn, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_left_btn:
                finish();
                break;
            case R.id.send:
                sendComment();
                break;
        }
    }

    private void sendComment() {
        if (!UserManager.get().isLogin()) {
            showToast("您还没登录");
            to(LoginActivity.class);
            return;
        }
        String content = input.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("内容为空");
            return;
        }
        AppService.get().sendComment(imageModel, content);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AppService.get().getDBHelper(this).clearGalleryComments();
        AppService.get().dbHelper.close();
        super.onDestroy();
    }
}
