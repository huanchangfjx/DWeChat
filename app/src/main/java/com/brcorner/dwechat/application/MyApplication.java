package com.brcorner.dwechat.application;

import android.app.Application;

import com.brcorner.dwechat.config.Config;

/**
 * Created by dxh on 16/3/2.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Config.isDebug = true;
    }
}
