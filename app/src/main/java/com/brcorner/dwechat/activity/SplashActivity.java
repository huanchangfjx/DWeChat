package com.brcorner.dwechat.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brcorner.dwechat.R;
import com.brcorner.dwechat.utils.CommonUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread() {
            public void run() {
                SystemClock.sleep(1500);
                CommonUtils.startActivity(SplashActivity.this,MainActivity.class,null);
                CommonUtils.finish(SplashActivity.this);
            }
        }.start();
    }

    @Override
    protected void initControl() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
