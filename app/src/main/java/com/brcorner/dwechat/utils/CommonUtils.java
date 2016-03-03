package com.brcorner.dwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import com.brcorner.dwechat.R;
import com.brcorner.dwechat.model.KeyValue;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dxh on 16/3/2.
 */
public class CommonUtils {
    private CommonUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 生成随机数字
     * @return
     */
    public static String generateNum()
    {
        return "" + System.currentTimeMillis() + "_" + (int) (Math.random() * 10000);
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取状态栏的高度
     * @param activity
     * @return
     */
    public int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    /**
     * 获取versionCode
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int version = 0;
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名�代表是获取版本信�
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String dateFormate(String date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String sd = sdf.format(new Date(Long.parseLong(date)));

        return sd;
    }

    /**
     * 截取url的最后面一部分
     * @param url
     * @return
     */
    public static String getUrlLastPart(String url)
    {
        int i = url.lastIndexOf("/");
        return url.substring(i+1, url.length());
    }

    /**
     * 判断手机连接
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.v("error", e.toString());
        }
        return false;
    }

    /**
     * 关闭 Activity
     *
     * @param activity
     */
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    /**
     * 打开Activity
     *
     * @param activity
     * @param cls
     * @param name
     */
    public static void startActivity(Activity activity, Class<?> cls,
                                      KeyValue... name) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (name != null)
            for (int i = 0; i < name.length; i++) {
                intent.putExtra(name[i].getKey(), name[i].getValue());
            }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

    }
}
