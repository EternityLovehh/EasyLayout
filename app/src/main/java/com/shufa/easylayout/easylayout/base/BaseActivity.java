package com.shufa.easylayout.easylayout.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.shufa.easylayout.easylayout.MyApplication;

import java.util.Stack;

/**
 * activity基类
 */
public class BaseActivity extends AppCompatActivity {
    /** 用来保存所有已打开的Activity */
    private static Stack<Activity> listActivity = new Stack<>();
    private ProgressDialog progressDialog;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将activity推入栈中
        listActivity.push(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
//            StatusBarCompat.setStatusBarColor(this, getResources().getColor(android.R.color.black), true);
//        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.tabColor), true);
//        }

//        init();
//        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从栈中移除当前activity
        if (listActivity.contains(this)) {
            listActivity.remove(this);
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(BaseActivity.this,cls);
        startActivityForResult(intent, requestCode);
    }


    /**
     * 关闭所有(前台、后台)Activity,注意：请已BaseActivity为父类
     */
    protected static void finishAll() {
        int len = listActivity.size();
        for (int i = 0; i < len; i++) {
            Activity activity = listActivity.pop();
            activity.finish();
        }
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

    /**
     * 显示进度对话框
     */
    public void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
