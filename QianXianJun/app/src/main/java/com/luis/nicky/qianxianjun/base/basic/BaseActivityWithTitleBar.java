package com.luis.nicky.qianxianjun.base.basic;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.luis.nicky.qianxianjun.MainActivity;
import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.view.TitleBar;


public abstract class BaseActivityWithTitleBar extends BaseActivity {

    private ImageView backImage;
    private TitleBar titleBar;

    @Override
    protected void loadLayout(View view) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        // titleBar.setTitle(R.string.smart_device_add_title);
        backImage = new ImageView(this);
        backImage.setImageResource(R.mipmap.back_button);
        backImage.setPadding(10, 10, 10, 10);
        backImage.setAdjustViewBounds(true);
        titleBar.setLeftView(backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 跳转到首页
     */
    public void gotoMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 获取titlebar
     */
    public TitleBar getTitleBar() {
        return titleBar;
    }

    /**
     * 获取左按钮
     */
    public View getLeftView() {
        return backImage;
    }


}
