package com.example.sword.smartbutler.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sword.smartbutler.MainActivity;
import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.utils.ShareUtil;
import com.example.sword.smartbutler.utils.StaticClass;
import com.example.sword.smartbutler.utils.UtilTools;

/**
 * Created by sword on 2017/3/3.
 * 闪屏页
 */

public class SplashActivity extends AppCompatActivity {
    /**
     * 1.延时2000ms
     * 2.判断是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     */
    private TextView mTvSplash;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    //初始化view
    private void initView() {
        mTvSplash = (TextView) findViewById(R.id.tv_splash);
        //延时2000ms
        mHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);

        //设置字体
        UtilTools.setFont(this,mTvSplash);
    }

    //判断是否是第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtil.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtil.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }


    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
