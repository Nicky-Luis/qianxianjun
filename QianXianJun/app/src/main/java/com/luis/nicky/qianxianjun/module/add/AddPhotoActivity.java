package com.luis.nicky.qianxianjun.module.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;
import com.luis.nicky.qianxianjun.common.utils.Logger;
import com.luis.nicky.qianxianjun.common.widget.gallery.imageloader.GalleyActivity;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddPhotoPresenter;
import com.luis.nicky.qianxianjun.module.add.presenter.AddPhotoPersenter;

import java.util.List;

import butterknife.OnClick;

public class AddPhotoActivity extends BaseActivity {

    // 相册选取
    public static final int ALBUM_SELECT = 2;
    //Intent_Key
    public static final String Intent_Key = "PERSON_ID";
    //presenter
    private IAddPhotoPresenter addPhotoPresenter;
    //用户ID
    private String personId;

    @OnClick(value = {R.id.btn_back, R.id.btn_save,R.id.btn_add_photos})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_back:
                finish();
                break;

            //添加
            case R.id.btn_save:
                startSaveInfo();
                break;

            case R.id.btn_add_photos:
                startAdd();
                break;

            default:
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_photo;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void onInitialize() {
        Intent intent = getIntent();
        personId = intent.getStringExtra(Intent_Key);
    }

    @Override
    public void initPresenter() {
        addPhotoPresenter = new AddPhotoPersenter(AddPhotoActivity.this);
    }

    private void startAdd() {
        // 相册
        Intent intentAlbum = new Intent(AddPhotoActivity.this, GalleyActivity.class);
        intentAlbum.putExtra(GalleyActivity.INTENT_KEY_SELECTED_COUNT, 0);
        startActivityForResult(intentAlbum, ALBUM_SELECT);
    }

    //保存照片信息
    private void startSaveInfo() {
        addPhotoPresenter.addNewPotos(null, personId, new IAddResultCallBack() {
            @Override
            public void onSuccess(String personId) {

            }

            @Override
            public void onFailure(int code, String arg0) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();

            // 检测sd是否可用
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Logger.i(this, "SD card is not avaiable/writeable right now." + 1);
                return;
            }

            @SuppressWarnings("unchecked")
            List<String> resultList = (List<String>) data
                    .getSerializableExtra(GalleyActivity.INTENT_KEY_PHOTO_LIST);
            long interval = System.currentTimeMillis() / 1000;
            // 循环处理图片
            for (String fileRealPath : resultList) {

            }
        }
    }
}
