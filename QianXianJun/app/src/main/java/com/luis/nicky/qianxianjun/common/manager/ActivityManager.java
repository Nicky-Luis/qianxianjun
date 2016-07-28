package com.luis.nicky.qianxianjun.common.manager;

import android.app.Activity;

import java.util.Stack;

public class ActivityManager {

    /**
     * Stack 中对应的Activity列表  （也可以写做 Stack<Activity>）
     */
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * @return ActivityManager
     * @描述 获取栈管理工具
     */
    public static ActivityManager getInstence() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 推出栈顶Activity
     */
    public void popActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获得当前栈顶Activity
     */
    public Activity currentActivity() {
        //lastElement()获取最后个子元素，这里是栈顶的Activity
        if (activityStack == null || activityStack.size() == 0) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 将当前Activity推入栈中
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 弹出最后一个
     */
    public void popTopActivity() {
        Activity activity = currentActivity();
        if (activity == null) {
            return;
        }
        popActivity(activity);
    }

    /**
     * 弹出指定的clsss所在栈顶部的中所有Activity
     *
     * @clsss : 指定的类
     */
    public void popTopActivitys(Class clsss) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(clsss)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 弹出栈中所有Activity
     */
    public void popAllActivitys() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }
}
