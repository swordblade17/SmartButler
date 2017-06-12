package com.example.sword.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册页
 * Created by sword on 2017/4/5.
 */

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUserName, mAge, mDesc, mPassword, mRePassword, mEmail;
    private RadioGroup mRadioGroup;
    private Button mRegisteredBtn;

    private boolean isGender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();
    }

    private void initView() {
        mUserName = (EditText) findViewById(R.id.et_user);
        mAge = (EditText) findViewById(R.id.et_age);
        mDesc = (EditText) findViewById(R.id.et_desc);
        mPassword = (EditText) findViewById(R.id.et_pass);
        mRePassword = (EditText) findViewById(R.id.et_password);
        mEmail = (EditText) findViewById(R.id.et_email);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        mRegisteredBtn = (Button) findViewById(R.id.btnRegistered);
        mRegisteredBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistered:
                //获取输入框的值
                String name = mUserName.getText().toString().trim();
                String age = mAge.getText().toString().trim();
                String desc = mDesc.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String rePassword = mRePassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(name)&!TextUtils.isEmpty(age)&
                        !TextUtils.isEmpty(password)&!TextUtils.isEmpty(rePassword)&
                        !TextUtils.isEmpty(email)){
                    //判断密码是否一致
                    if (password.equals(rePassword)){
                        //判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                if (i==R.id.rb_boy){
                                    isGender = true;
                                }else if (i==R.id.rb_girl){
                                    isGender = false;
                                }
                            }
                        });

                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)){
                            desc = "这个人很懒，什么都没有写";
                        }
                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);
                        user.setDesc(desc);

                        user.signUp(new SaveListener<MyUser>() {


                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e==null){
                                    Toast.makeText(RegisteredActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(RegisteredActivity.this,"注册失败"+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();

                    }




                }else {
                    Toast.makeText(this,"输入框不为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
