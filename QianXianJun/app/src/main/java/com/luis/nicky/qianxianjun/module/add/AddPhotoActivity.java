package com.luis.nicky.qianxianjun.module.add;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;
import com.luis.nicky.qianxianjun.common.manager.ActivityManager;
import com.luis.nicky.qianxianjun.common.utils.Logger;
import com.luis.nicky.qianxianjun.common.utils.ToastUtil;
import com.luis.nicky.qianxianjun.common.widget.DialogUtil;
import com.luis.nicky.qianxianjun.common.widget.TitleBar;
import com.luis.nicky.qianxianjun.common.widget.gallery.imageloader.GalleyActivity;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddPhotoPresenter;
import com.luis.nicky.qianxianjun.module.add.presenter.AddPhotoPersenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddPhotoActivity extends BaseActivity {

    //Intent_Key
    public static final String Intent_Key = "PERSON_ID";
    //标头栏
    @InjectView(R.id.title_bar)
    TitleBar titleBar;
    //选择照片按钮
    @InjectView(R.id.btn_add_photos)
    Button selectPicBtn;
    //presenter
    private IAddPhotoPresenter addPhotoPresenter;
    //用户ID
    private String personId = "123456";
    //已经选择了的照片
    private List<String> currentPhotos;

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_photo;
    }

    @Override
    protected void loadLayout(View v) {
        titleBar.setTitle("添加照片");
        //添加右侧按钮
        TextView saveBtn = new TextView(this);
        saveBtn.setTextSize(14);
        saveBtn.setTextColor(ContextCompat.getColor(this, R.color.main_light_white));
        saveBtn.setText("保存");
        titleBar.setRightView(saveBtn);
        //保存信息
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSaveInfo();
            }
        });

        //选择照片
        selectPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToGalley();
            }
        });
    }

    @Override
    protected void onInitialize() {
        //注册EventBus
        EventBus.getDefault().register(this);
        //获取用户id
        Intent intent = getIntent();
        if (intent.hasExtra(Intent_Key)) {
            personId = intent.getStringExtra(Intent_Key);
        }
        if (null == personId) {
            finish();
        }
        //初始化
        currentPhotos = new ArrayList<>();
    }

    @Override
    public void initPresenter() {
        addPhotoPresenter = new AddPhotoPersenter(AddPhotoActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }


    ////////////////////////////private method//////////////////////////////////

    // 跳转到相册
    private void startToGalley() {
        Intent intentAlbum = new Intent(AddPhotoActivity.this, GalleyActivity.class);
        intentAlbum.putExtra(GalleyActivity.INTENT_KEY_SELECTED_COUNT, currentPhotos.size());
        startActivity(intentAlbum);
    }

    //保存照片信息
    private void startSaveInfo() {
        if (currentPhotos.size() <= 0) {
            ToastUtil.show(AddPhotoActivity.this, "照片为空");
            return;
        }

        DialogUtil.instance().showLoadingDialog(AddPhotoActivity.this, "照片上传中");
        addPhotoPresenter.addNewPotos(currentPhotos, personId, new IAddResultCallBack() {
            @Override
            public void onSuccess(String msg) {
                DialogUtil.dismissDialog();
                ToastUtil.show(AddPhotoActivity.this, "照片上传成功");

                //通过event bus 关闭前两个activity
                EventBus.getDefault().post(new AddFinish());
                finish();
            }

            @Override
            public void onFailure(int code, String arg0) {
                DialogUtil.dismissDialog();
                ToastUtil.show(AddPhotoActivity.this, "照片上传失败");
            }
        });
    }

    //接收返回的图片信息
    public void onEventMainThread(GalleyActivity.PhotoFiles photoFiles) {

        currentPhotos = photoFiles.getFiles();

        if (currentPhotos.size() <= 0) {
            selectPicBtn.setText("请选择照片");
        } else {
            selectPicBtn.setText("已经选择了" + currentPhotos.size() + "张");
        }
        for (String fileRealPath : currentPhotos) {
            Logger.i(this, "获取到的文件是：" + fileRealPath);
        }
    }


    /**
     * 添加完成
     */
    public class AddFinish {
        public AddFinish() {
        }
    }
}
