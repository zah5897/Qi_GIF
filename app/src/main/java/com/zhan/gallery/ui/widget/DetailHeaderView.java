package com.zhan.gallery.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhan.gallery.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class DetailHeaderView extends FrameLayout {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.from)
    TextView from;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.sub_layout)
    LinearLayout subLayout;
    @BindView(R.id.tvHtmlContent)
    HtmlTextView tvHtmlContent;

    public DetailHeaderView(Context context) {
        this(context, null);
    }

    public DetailHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.header_html_detail, this);
        ButterKnife.bind(this, this);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setContent(String html) {
        if (TextUtils.isEmpty(html))
            tvHtmlContent.setVisibility(GONE);
        tvHtmlContent.setRichText(html);
    }

    public void setContent(String from, Date time) {

        if (TextUtils.isEmpty(from) && time == null) {
            subLayout.setVisibility(GONE);
            return;
        } else {
            subLayout.setVisibility(VISIBLE);
        }

        if (!TextUtils.isEmpty(from)) {
            this.from.setText(from);
        }
        if (time != null) {
            this.time.setText(time.toLocaleString());
        }
    }
}
