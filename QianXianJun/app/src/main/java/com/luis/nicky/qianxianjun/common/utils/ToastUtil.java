package com.luis.nicky.qianxianjun.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Nicky on 2016/7/24.
 * 吐司
 */
public class ToastUtil {

    public static void show(Context ctx, int id, String str) {
        if (str == null) {
            return;
        }

        Toast toast = Toast.makeText(ctx, ctx.getString(id) + str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show(Context ctx, String errInfo) {
        if (errInfo == null) {
            return;
        }

        Toast toast = Toast.makeText(ctx, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
