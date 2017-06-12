package com.example.sword.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sword on 2017/3/3.
 */

public class ShareUtil {

    public static final String NAME = "config";

    public static void putString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();

    }

    public static String getString(Context context,String key,String defValue){
        SharedPreferences sp =context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }
    public static void putInt(Context context,String key,int value){
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();

    }

    public static int getInt(Context context,String key,int defValue){
        SharedPreferences sp =context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();

    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sp =context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    //删除 单个
    public static void deleteShare(Context context,String key){
        SharedPreferences sp =context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //删除全部
    public static void deleteAll(Context context){
        SharedPreferences sp =context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }




}
