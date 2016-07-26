package com.luis.nicky.qianxianjun.common.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Locale;

import cn.bmob.v3.Bmob;

/**
 * 自定义应用入口
 *
 * @author Ht
 */
public class CommonApp extends Application {
    private static CommonApp mInstance;
    //检测内存泄露
    //private RefWatcher refWatcher;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //检测内存泄露
       // LeakCanary.install(this);
        initScreenSize();
        bombInit();
    }

    ///////////////////////////////////////////////////////////

    /***
     * 初始化比目SDK
     */
    private void bombInit() {
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, "a47b827fafafaf9fe3884e12bfc4cec2");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
    ///////////////////////////////////////////////////////////

    /**
     * 在自己的Application中添加如下代码
     */
//    public static RefWatcher getRefWatcher(Context context) {
//        return mInstance.refWatcher;
//    }

    //获取实例
    public static CommonApp getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

}
