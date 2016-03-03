package com.brcorner.dwechat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.brcorner.dwechat.config.Config;
import com.brcorner.dwechat.utils.CommonUtils;
import com.brcorner.dwechat.utils.LogUtils;

/**
 * Created by dxh on 16/3/2.
 */
public abstract class BaseActivity extends FragmentActivity{
    //在基类中封装网络请求的相关东西

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Config.isDebug)
        {
            LogUtils.v("ActivityName",getClass().getSimpleName());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CommonUtils.finish(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 绑定控件id
     */
    protected abstract void initControl();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
