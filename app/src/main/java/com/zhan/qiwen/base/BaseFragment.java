package com.zhan.qiwen.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public abstract class BaseFragment extends Fragment {
    protected boolean mHasLoadData = false;
    protected boolean isVisible = false;
    protected View rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstInit();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!mHasLoadData&&isVisible){
            mHasLoadData=true;
            lazyLoad();
        }
    }

    protected abstract void firstInit();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e("BaseFragment","setUserVisibleHint:isVisibleToUser="+isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        isVisible=isVisibleToUser;
    }

    protected  void lazyLoad(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=loadLayout(inflater,container);
        }
        return rootView;
    }
    protected abstract  View loadLayout(LayoutInflater inflater, ViewGroup container);

    /**
     * 获取控件
     *
     * @param id  控件的id
     * @param <E>
     * @return
     */
    protected <E extends View> E get(int id) {
        return (E) rootView.findViewById(id);
    }

    /**
     * 界面跳转
     *
     * @param tarActivity
     */
    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(getActivity(), tarActivity);
        startActivity(intent);
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
