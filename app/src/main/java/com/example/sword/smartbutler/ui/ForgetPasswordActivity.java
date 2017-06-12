package com.example.sword.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by sword on 2017/4/19.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button mForgetPassBtn;
    private EditText mEmailET;
    private EditText mETNow;
    private EditText mETNew;
    private EditText mETNewPsw;
    private Button mBtnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        mForgetPassBtn = (Button) findViewById(R.id.btn_forget_password);
        mForgetPassBtn.setOnClickListener(this);
        mEmailET = (EditText) findViewById(R.id.et_email);
        mETNow = (EditText) findViewById(R.id.et_now);
        mETNew = (EditText) findViewById(R.id.et_new);
        mETNewPsw = (EditText) findViewById(R.id.et_new_password);
        mBtnUpdate = (Button) findViewById(R.id.btn_update);
        mBtnUpdate.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_forget_password:
                //1.获取输入框的邮箱
                final String email = mEmailET.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                           if (e==null){
                               Toast.makeText(ForgetPasswordActivity.this,
                                       "已发送至"+email, Toast.LENGTH_SHORT).show();

                           }else {
                               Toast.makeText(ForgetPasswordActivity.this,
                                       "发送失败"+email, Toast.LENGTH_SHORT).show();

                           }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update:
                String nowPsw = mETNow.getText().toString().trim();
                String newPsw = mETNew.getText().toString().trim();
                String newPsw2 = mETNewPsw.getText().toString().trim();
                if (!TextUtils.isEmpty(nowPsw)&!TextUtils.isEmpty(newPsw)&!TextUtils.isEmpty(newPsw2)){
                    if (newPsw.equals(newPsw2)){
                        //重置密码
                        MyUser.updateCurrentUserPassword(nowPsw, newPsw, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
