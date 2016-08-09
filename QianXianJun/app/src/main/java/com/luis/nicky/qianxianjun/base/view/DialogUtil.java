package com.luis.nicky.qianxianjun.base.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.manager.ActivityManager;


public class DialogUtil {

    //实例
    private static DialogUtil dialogUtil;
    private static Dialog dialog;

    private DialogUtil() {
    }

    // 实例化
    public static DialogUtil instance() {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil();
        }
        return dialogUtil;
    }

    /**
     * 等待对话框
     */
    public void showLoadingDialog(int content) {
        Context context = ActivityManager.getAppManager().currentActivity();
        dismissDialog();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater
                .inflate(R.layout.layout_loading_dialog, null);

        TextView tvContent = (TextView) relativeLayout
                .findViewById(R.id.tv_loading);
        tvContent.setText(content);
        tvContent.setVisibility(View.VISIBLE);
        dialog = new Dialog(context, R.style.dialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(relativeLayout);
        try {
            if (!((Activity) context).isFinishing()) {
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        dialog.show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoadingDialog(Context context, String content,
                                  boolean cancelable, OnCancelListener listener) {
        dismissDialog();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater
                .inflate(R.layout.layout_loading_dialog, null);

        TextView tvContent = (TextView) relativeLayout
                .findViewById(R.id.tv_loading);
        tvContent.setText(content);
        tvContent.setVisibility(View.VISIBLE);
        dialog = new Dialog(context, R.style.dialogStyle);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.setCancelable(cancelable);
        dialog.setContentView(relativeLayout);
        dialog.setOnCancelListener(listener);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showLoadingDialog(Context context, String content,
                                  boolean cancelable, boolean cancelableTouch, OnCancelListener listener) {
        dismissDialog();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater
                .inflate(R.layout.layout_loading_dialog, null);

        TextView tvContent = (TextView) relativeLayout
                .findViewById(R.id.tv_loading);
        tvContent.setText(content);
        tvContent.setVisibility(View.VISIBLE);
        dialog = new Dialog(context, R.style.dialogStyle);
        dialog.setCanceledOnTouchOutside(cancelableTouch);
        dialog.setCancelable(cancelable);
        dialog.setContentView(relativeLayout);
        dialog.setOnCancelListener(listener);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showLoadingDialog(String content) {
        Context context = ActivityManager.getAppManager().currentActivity();
        dismissDialog();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater
                .inflate(R.layout.layout_loading_dialog, null);

        TextView tvContent = (TextView) relativeLayout
                .findViewById(R.id.tv_loading);
        tvContent.setText(content);
        tvContent.setVisibility(View.VISIBLE);
        dialog = new Dialog(context, R.style.dialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(relativeLayout);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public boolean getIsShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

}