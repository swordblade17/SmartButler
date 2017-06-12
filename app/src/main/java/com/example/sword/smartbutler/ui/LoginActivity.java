package com.example.sword.smartbutler.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sword.smartbutler.MainActivity;
import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.MyUser;
import com.example.sword.smartbutler.utils.L;
import com.example.sword.smartbutler.utils.ShareUtil;
import com.example.sword.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button mRegisterBtn;
    private EditText mName;
    private EditText mPass;
    private Button mLogin;
    private CheckBox mKeepPass;
    private Context mContext;
    private TextView mForgetTV;

    private CustomDialog mDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        initView();
    }

    private void initView() {
        mRegisterBtn = (Button) findViewById(R.id.btn_registered);
        mRegisterBtn.setOnClickListener(this);
        mName = (EditText) findViewById(R.id.et_name);
        mPass = (EditText) findViewById(R.id.et_pass);
        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(this);
        mForgetTV = (TextView) findViewById(R.id.tv_forget);
        mForgetTV.setOnClickListener(this);
        mKeepPass = (CheckBox) findViewById(R.id.checkBox);
        mDialog = new CustomDialog(this, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,R.layout.dialog_loading,R.style.theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        mDialog.setCancelable(false);


        //设置选中状态
        boolean isCHecked = ShareUtil.getBoolean(mContext,"keeppass",false);
        mKeepPass.setChecked(isCHecked);
        if (isCHecked){
            mName.setText(ShareUtil.getString(mContext,"name",""));
            mPass.setText(ShareUtil.getString(mContext,"pass",""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                //获取输入框的值
                String name = mName.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                if (!TextUtils.isEmpty(name)&!TextUtils.isEmpty(pass)){
                    mDialog.show();
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(pass);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {

                            mDialog.dismiss();
                            //判断结果
                            if (e==null){
                                //判断邮箱是否验证
                                if (user.getEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareUtil.putBoolean(mContext,"keeppass",mKeepPass.isChecked());

        if (mKeepPass.isChecked()){
            //记住用户名和密码
            ShareUtil.putString(mContext,"name",mName.getText().toString().trim());
            ShareUtil.putString(mContext,"pass",mPass.getText().toString().trim());
        }else {
            ShareUtil.deleteShare(mContext,"name");
            ShareUtil.deleteShare(mContext,"pass");
        }
    }
}
