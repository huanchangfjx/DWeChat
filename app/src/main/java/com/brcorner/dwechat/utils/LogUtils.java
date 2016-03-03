package com.brcorner.dwechat.utils;

import android.util.Log;

import com.brcorner.dwechat.config.Config;


/** 
 * Log统一管理类 
 * @author dong 
 *  
 *  
 */  
public class LogUtils  
{  
  
    private LogUtils()  
    {  
        throw new UnsupportedOperationException("cannot be instantiated");  
    }  
  
    private static final String TAG = "BR_Corner";
  
    // 下面四个是默认tag的函数  
    public static void i(String msg)  
    {  
        if (Config.isDebug)
            Log.i(TAG, msg);  
    }  
  
    public static void d(String msg)  
    {  
        if (Config.isDebug)
            Log.d(TAG, msg);  
    }  
  
    public static void e(String msg)  
    {  
        if (Config.isDebug)
            Log.e(TAG, msg);  
    }  
  
    public static void v(String msg)  
    {  
        if (Config.isDebug)
            Log.v(TAG, msg);  
    }  
  
    // 下面是传入自定义tag的函数  
    public static void i(String tag, String msg)  
    {  
        if (Config.isDebug)
            Log.i(tag, msg);  
    }  
  
    public static void d(String tag, String msg)  
    {  
        if (Config.isDebug)
            Log.i(tag, msg);  
    }  
  
    public static void e(String tag, String msg)  
    {  
        if (Config.isDebug)
            Log.i(tag, msg);  
    }  
  
    public static void v(String tag, String msg)  
    {  
        if (Config.isDebug)
            Log.i(tag, msg);  
    }  
}  
