package com.zhan.gallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhan.gallery.R;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.model.service.UserManager;
import com.zhan.gallery.ui.adapter.image.ImgDetailViewProvider;
import com.zhan.gallery.ui.base.BaseSwipeBackActivity;
import com.zhan.gallery.ui.user.LoginActivity;
import com.zhan.gallery.utils.DBHelper;
import com.zhan.gallery.utils.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

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

    private MultiTypeAdapter adapter;
    private Items items;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        imageModel = getIntent().getParcelableExtra("model");
        ButterKnife.bind(this);
        title.setText(imageModel.txt);
        initView();
        AppService.get().dbHelper = new DBHelper(getApplicationContext());
        EventBus.getDefault().register(this);
    }


    private void initView() {
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        adapter.register(ImageModel.class, new ImgDetailViewProvider());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        items.add(imageModel);
        adapter.notifyDataSetChanged();
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComment(Comment comment) {

    }

    @OnClick({R.id.title_bar_left_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_left_btn:
                finish();
                break;
//            case R.id.action_comment:
//                if (UserManager.get().isLogin()) {
//                    Intent intent = new Intent(this, SendCommentActivity.class);
//                    intent.putExtra("model", imageModel);
//                    to(intent);
//                } else {
//                    to(LoginActivity.class);
//                }
//                break;
//            case R.id.action_download:
//                RequestBuilder requestBuilder = Glide.with(this).load(imageModel.img_url);
//                requestBuilder.apply(new RequestOptions().priority(Priority.HIGH));
//                requestBuilder.into(new SimpleTarget<File>() {
//                    @Override
//                    public void onResourceReady(File resource, Transition<? super File> transition) {
//                        String fileName = imageModel.img_url.substring(imageModel.img_url.lastIndexOf("/"));
//                        FileUtils.copyfile(resource, new File(Environment.getExternalStorageDirectory() + "/" + FileUtils.ROOT_DIR + fileName), true);
//                        showToast("图片已经保存在" + FileUtils.ROOT_DIR + "目录下！");
//                    }
//                });
//                break;
//            case R.id.action_favor:
//                if (!UserManager.get().isLogin()) {
//                    to(LoginActivity.class);
//                    showToast("请先登陆");
//                    return;
//                }
////                Gallery cg = (Gallery) AppService.get().tempGalleries.get(viewPager.getCurrentItem());
////                RequestParam param = new RequestParam();
////                AppService.get().requestData("/main/collect/" + cg.channel, param, new Callback() {
////                    @Override
////                    public void onResult(Object object) {
////
////                    }
////
////                    @Override
////                    public void onFailed(int code, String msg) {
////
////                    }
////                });
//                break;
            case R.id.action_share:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AppService.get().getDBHelper(this).clearGalleryComments();
        AppService.get().dbHelper.close();
        super.onDestroy();
    }
}
