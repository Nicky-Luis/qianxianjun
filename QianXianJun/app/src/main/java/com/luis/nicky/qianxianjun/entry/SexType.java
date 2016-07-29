package com.luis.nicky.qianxianjun.entry;

import com.luis.nicky.qianxianjun.R;

/**
 * Created by Nicky on 2016/7/27.
 * 数据类型
 */
public class SexType {
    public static final int Man = 0;
    public static final int Female = 1;

    //获取性别的图片
    public static int getSexRes(int type) {
        if (type == Man) {
            return R.mipmap.ic_launcher;
        } else {
            return R.mipmap.ic_launcher;
        }
    }

    //获取性别的文字
    public static String getSexText(int type) {
        if (type == Man) {
            return "男";
        } else {
            return "女";
        }
    }
}
