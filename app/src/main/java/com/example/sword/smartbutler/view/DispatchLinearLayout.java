package com.example.sword.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Created by sword on 2017/6/11.
 */

public class DispatchLinearLayout extends LinearLayout {

    private DispatchKeyEventListener mKeyEventListener;

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchKeyEventListener getKeyEventListener() {
        return mKeyEventListener;
    }

    public void setKeyEventListener(DispatchKeyEventListener keyEventListener) {
        mKeyEventListener = keyEventListener;
    }

    //接口
    public static interface DispatchKeyEventListener{
        boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空 说明调用了 去获取事件
        if (mKeyEventListener!=null){
            return mKeyEventListener.dispatchKeyEvent(event);
        }

        return super.dispatchKeyEvent(event);
    }


}
