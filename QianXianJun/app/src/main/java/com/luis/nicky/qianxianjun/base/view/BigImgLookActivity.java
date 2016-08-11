package com.luis.nicky.qianxianjun.base.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.basic.BaseActivity;
import com.luis.nicky.qianxianjun.base.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

@SuppressLint("NewApi")
public class BigImgLookActivity extends BaseActivity {

    // 传递单张图片
    public static final String INTENT_KEY = "Single_Image";
    // 传递图片URL列表
    public static final String INTENT_KEY_IMG_LIST = "Url_Path_List";
    // 传递当前图片的位置
    public static final String INTENT_KEY_INDEX = "Current_Index";
    // 图片路径
    private List<String> imageUrlList = new ArrayList<>();
    // 图片缩略图路径
    private List<String> imageSubUrlList = new ArrayList<>();
    // 所有的imageview
    private List<TouchImageView> imageViewList = new ArrayList<>();
    // 回调对象
    @InjectView(R.id.viewpager_big_image)
    ViewPager viewPager;
    // 提示性点点数组
    private List<ImageView> tipList = new ArrayList<>();
    // 当前展示的页码
    private int currentPage = 0;
    // 存放点点的容器
    @InjectView(R.id.tipsBox)
    LinearLayout tipsBoxLayout;
    // viewpage适配器
    private BigImageAdapter bigImageAdapter;
    // 是否是长按操作
    private boolean isLoneClick = false;
    // 加载动画
    private Dialog loadingDialog;
    // 保存dialog
    private CustomListViewDialog downDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_big_image_looker;
    }

    @Override
    protected void loadLayout(View v) {
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void onInitialize() {
        init();
        initIndicator();
        createPageImageView();
        setViewPageAdpter();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        // 创建加载dialog
        loadingDialog = CustomImageLoadingDialog
                .createLoadingDialog(BigImgLookActivity.this);
        Intent intent = this.getIntent();
        if (intent.hasExtra(INTENT_KEY)) {
            // 传递的是单张图片url
            if (intent.hasExtra(INTENT_KEY)) {
                imageUrlList.add(intent.getStringExtra(INTENT_KEY));
                imageSubUrlList = imageUrlList;
            } else {
                Logger.e("未传递图片地址");
            }
        } else if (intent.hasExtra(INTENT_KEY_IMG_LIST)) {
            // 传递图片地址list
            imageUrlList = (List<String>) intent
                    .getSerializableExtra(INTENT_KEY_IMG_LIST);

            imageSubUrlList = imageUrlList;
            if (intent.hasExtra(INTENT_KEY_INDEX)) {
                currentPage = intent.getIntExtra(INTENT_KEY_INDEX, 0);
            } else {
                Logger.v("未传递所点击图片的位置，默认为0.");
            }
        } else {
            Logger.e("未传递图片地址,图片数为：" + imageUrlList.size());
        }

    }

    /**
     * 初始化引导图标 动态创建多个小圆点，然后组装到线性布局里
     */
    private void initIndicator() {
        if (imageUrlList.size() > 1) {
            for (int index = 0; index < imageUrlList.size(); index++) {
                ImageView dotImage = new ImageView(this);
                dotImage.setLayoutParams(new LayoutParams(8, 8));
                if (index == currentPage) {
                    dotImage.setBackgroundResource(R.mipmap.cursor_point_selected);
                } else {
                    dotImage.setBackgroundResource(R.mipmap.cursor_point_not_selected);
                }
                tipList.add(dotImage);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
                params.leftMargin = 5;
                params.rightMargin = 5;
                tipsBoxLayout.addView(dotImage, params);
            }
        }
    }

    /**
     * viewpage初始化
     */
    private void createPageImageView() {
        for (int index = 0; index < imageUrlList.size(); index++) {
            TouchImageView tcView = new TouchImageView(this);
            tcView.setClickable(true);
            imageViewList.add(tcView);
            showImageView(tcView, imageUrlList.get(index));
            tcView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!isLoneClick) {
                        finish();
                    }
                }
            });

            tcView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {

                    isLoneClick = true;
                    imageLongClick();
                    return true;
                }
            });
        }
    }

    /**
     * 初始化PagerAdapter
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setViewPageAdpter() {

        bigImageAdapter = new BigImageAdapter(imageViewList);
        viewPager.setAdapter(bigImageAdapter);
        viewPager.setOnPageChangeListener(new BigImageListener());
        viewPager.setCurrentItem(currentPage);
    }

    /**
     * 长按操作
     */
    private void imageLongClick() {
        List<String> menuList = new ArrayList<String>();
        menuList.add("保存到手机");
        downDialog = new CustomListViewDialog(BigImgLookActivity.this, menuList);
        downDialog.setClickCallBack(new CustomListViewDialog.ClickCallBack() {

            @Override
            public void Onclick(View view, int which) {
                String imagePath = imageUrlList.get(currentPage);
                int nameIndex = imagePath.lastIndexOf("/");
                String imageName = imagePath.substring(nameIndex + 1);
                download(imagePath, imageName);
                downDialog.cancel();
            }
        });
        downDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                isLoneClick = false;
            }
        });
        downDialog.show();
    }

    //设置图片
    private void showImageView(SimpleDraweeView view, String imageUrl) {
        if (null == imageUrl || imageUrl.length() <= 0) {
            return;
        }
        Uri uri = Uri.parse(imageUrl);
        /**
         * 设置图片显示的品质，下载图片不受影响
         */
        int width = 60, height = 60;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(request)
                .setTapToRetryEnabled(true)

                .setOldController(view.getController())
                .build();

        view.setController(controller);
    }

    /**
     * 下载图片
     */
    private void download(String Url, String imageName) {
    }

    /**
     * 适配器，负责装配 、销毁 数据 和 组件 。
     */
    private class BigImageAdapter extends PagerAdapter {

        private List<TouchImageView> mList;

        public BigImageAdapter(List<TouchImageView> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(final ViewGroup container,
                                      final int position) {
            if (position == currentPage) {
                showImageView(imageViewList.get(currentPage), imageUrlList.get
                        (currentPage));
            }
            container.addView(mList.get(position));
            return mList.get(position);
        }
    }

    /**
     * 动作监听器
     */
    private class BigImageListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            tipList.get(currentPage).setBackgroundResource(
                    R.mipmap.cursor_point_not_selected);
            tipList.get(position).setBackgroundResource(
                    R.mipmap.cursor_point_selected);
            currentPage = position;
            showImageView(imageViewList.get(currentPage), imageUrlList.get(currentPage));
        }
    }

    /**
     * 重写返回操作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
