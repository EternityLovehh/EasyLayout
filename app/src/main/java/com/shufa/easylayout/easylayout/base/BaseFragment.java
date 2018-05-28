package com.shufa.easylayout.easylayout.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shufa.easylayout.easylayout.MyApplication;

/**
 * fragment基类
 * Created by Administrator on 2018/5/14 0014.
 */

public abstract class BaseFragment extends Fragment {

    private Activity mActivity;
    private Toast toast;

    //获得layoutId
    public abstract int getLayoutId();
    //初始化
    public abstract void init();
    //初始化数据
    public abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initData();
    }

    /**
     * 简化toast
     * @param text
     */
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * toast到屏幕中间
     * @param text
     */
    public void showGravityToast(String text){
        if (!TextUtils.isEmpty(text)) {
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
