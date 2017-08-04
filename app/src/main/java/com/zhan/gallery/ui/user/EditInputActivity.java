package com.zhan.gallery.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhan.gallery.R;
import com.zhan.gallery.ui.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zah on 2016/11/24.
 */

public class EditInputActivity extends BaseSwipeBackActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.right)
    TextView right;
    @BindView(R.id.input)
    EditText inputView;

    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            barTitle.setText("昵称");
            inputView.setMaxEms(10);
        } else if (type == 1) {
            barTitle.setText("签名");
            inputView.setMaxEms(50);
            barTitle.setMinLines(3);
        }
        right.setText("确定");
    }


    @OnClick({R.id.back, R.id.right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right:
                String input = inputView.getText().toString();
                if (type == 0) {
                    if (TextUtils.isEmpty(input.trim())) {
                        showToast("昵称不能为空");
                        return;
                    }
                }
                Intent i = new Intent();
                i.putExtra("input", input);
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }
}
