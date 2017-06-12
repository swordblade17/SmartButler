package com.example.sword.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by sword on 2017/2/26.
 */

public class UtilTools {

    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    public static void putImageToShare(Context mContext,ImageView imageView){
        //保存
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //第二步：利用base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //第三步：将string保存在shareUtils
        ShareUtil.putString(mContext,"image_title",imgString);
    }

    public static void getImageFromShare(Context mContext,ImageView imageView){
        //1.拿到imgString
        String imgString = ShareUtil.getString(mContext,"image_title","");
        if (!imgString.equals("")){
            //2.利用base64将String转换
            byte[] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }

}
