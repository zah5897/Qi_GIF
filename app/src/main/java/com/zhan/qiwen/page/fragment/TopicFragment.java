package com.zhan.qiwen.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopicFragment extends Fragment {
    public static final String TYPE = "type";

    public static TopicFragment newInstance(String channel) {
        TopicFragment topicFragment = new TopicFragment();
        Bundle b = new Bundle();
        b.putInt(TYPE, 1);
        topicFragment.setArguments(b);
        return topicFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tx = new TextView(getActivity());
        tx.setText("你好");
        return tx;
    }
}
