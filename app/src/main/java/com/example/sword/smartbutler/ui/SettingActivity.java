package com.example.sword.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.service.SmsService;
import com.example.sword.smartbutler.utils.ShareUtil;

/**
 * Created by sword on 2017/2/27.
 * 设置
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Switch mSpeakSw;
    private Switch mSms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        mSpeakSw = (Switch) findViewById(R.id.sw_speak);
        mSpeakSw.setOnClickListener(this);
        boolean isSpeak = ShareUtil.getBoolean(this, "isSpeak", false);
        mSpeakSw.setChecked(isSpeak);
        mSms = (Switch) findViewById(R.id.sw_sms);
        mSms.setOnClickListener(this);
        boolean isSms = ShareUtil.getBoolean(this, "isSms", false);
        mSms.setChecked(isSms);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sw_speak:
                mSpeakSw.setSelected(!mSpeakSw.isSelected());
                ShareUtil.putBoolean(this, "isSpeak", mSpeakSw.isChecked());
                break;
            case R.id.sw_sms:
                mSms.setSelected(!mSms.isSelected());
                ShareUtil.putBoolean(this, "isSms", mSms.isChecked());
                if (mSms.isChecked()){
                    startService(new Intent(this,SmsService.class));
                }else {
                    stopService(new Intent(this,SmsService.class));
                }
                break;
        }
    }
}
