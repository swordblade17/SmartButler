package com.example.sword.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.utils.L;
import com.example.sword.smartbutler.utils.StaticClass;
import com.example.sword.smartbutler.view.DispatchLinearLayout;

/**
 * Created by sword on 2017/6/9.
 */

public class SmsService extends Service implements View.OnClickListener {

    public static final String SYSTEM_DIALOGS_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";

    private SmsReceiver mSmsReceiver;
    //发件人号码
    private String mSmsPhone;
    //短信内容
    private String mSmsContent;
    //窗口管理器
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams mLayoutParams;
    //View
    private DispatchLinearLayout mView;

    private TextView mTVPhone;
    private TextView mTVContent;
    private Button mBtnSms;

    private HomeWatchReceiver mHomeWatchReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        L.i("init service");
        //动态注册
        mSmsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        //添加Action
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(mSmsReceiver, intent);

        mHomeWatchReceiver = new HomeWatchReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatchReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("stop service");
        unregisterReceiver(mSmsReceiver);
        unregisterReceiver(mHomeWatchReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_sms:
                sendSms();
                //关掉窗口
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                break;
        }
    }

    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + mSmsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);

    }

    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_ACTION.equals(action)) {
                L.i("来短信了");

                //获取短信内容
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                String format = intent.getStringExtra("format");

                //遍历数组，得到相关数据
                for (Object obj : objs) {
                    SmsMessage sms;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        sms = SmsMessage.createFromPdu((byte[]) obj);
                    } else {
                        sms = SmsMessage.createFromPdu((byte[]) obj, format);
                    }
                    mSmsPhone = sms.getOriginatingAddress();
                    mSmsContent = sms.getMessageBody();
                    L.i("短信的内容" + mSmsPhone + ":" + mSmsContent);
                    showWindow();
                }
            }
        }
    }

    //窗口提示
    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        mLayoutParams = new WindowManager.LayoutParams();
        //定义宽高
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);

        mTVPhone = (TextView) mView.findViewById(R.id.tv_phone);
        mTVContent = (TextView) mView.findViewById(R.id.tv_content);
        mBtnSms = (Button) mView.findViewById(R.id.btn_send_sms);
        mBtnSms.setOnClickListener(this);
        mTVPhone.setText("发件人：" + mSmsPhone);
        mTVContent.setText("内容：" + mSmsContent);

        //添加view到窗口
        wm.addView(mView, mLayoutParams);

        mView.setKeyEventListener(new DispatchLinearLayout.DispatchKeyEventListener() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                //判断是否是按返回键
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    L.i("我按了返回键");
                    if (mView.getParent() != null) {
                        wm.removeView(mView);
                    }
                    return true;
                }
                return false;
            }
        });



    }


    //监听home键的广播
    class HomeWatchReceiver extends BroadcastReceiver{



        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){

                String reason = intent.getStringExtra(SYSTEM_DIALOGS_REASON_KEY);
                if (SYSTEM_DIALOGS_HOME_KEY.equals(reason)){
                    L.i("我点击了HOME键");
                    if (mView.getParent()!=null){
                        wm.removeView(mView);
                    }
                }
            }
        }
    }

}
