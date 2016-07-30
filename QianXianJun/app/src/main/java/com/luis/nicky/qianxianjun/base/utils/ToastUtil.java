package com.luis.nicky.qianxianjun.base.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.luis.nicky.qianxianjun.base.manager.ActivityManager;

/**
 * Created by Nicky on 2016/7/24.
 * 吐司
 */
public class ToastUtil {

    public static void show(int id, String str) {
        if (str == null) {
            return;
        }
        Context ctx = ActivityManager.getAppManager().currentActivity();
        Toast toast = Toast.makeText(ctx, ctx.getString(id) + str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show( String errInfo) {
        if (errInfo == null) {
            return;
        }
        Context ctx = ActivityManager.getAppManager().currentActivity();
        Toast toast = Toast.makeText(ctx, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
