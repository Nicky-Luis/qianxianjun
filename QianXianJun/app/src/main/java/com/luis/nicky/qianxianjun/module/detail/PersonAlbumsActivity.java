package com.luis.nicky.qianxianjun.module.detail;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.BaseAdapterHelper;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.QuickAdapter;
import com.luis.nicky.qianxianjun.base.basic.BaseActivityWithTitleBar;
import com.luis.nicky.qianxianjun.base.utils.ToastUtil;
import com.luis.nicky.qianxianjun.base.view.BigImgLookActivity;
import com.luis.nicky.qianxianjun.base.view.DialogUtil;
import com.luis.nicky.qianxianjun.base.view.TitleBar;
import com.luis.nicky.qianxianjun.model.Photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class PersonAlbumsActivity extends BaseActivityWithTitleBar {

    //key
    public static final String Intent_Key_Id = "person_id";

    @InjectView(R.id.gv_person_albums)
    GridView albumsGridView;
    // 适配器
    private QuickAdapter<String> albumsAdapter;
    //所有照片列表
    private List<String > photoList=new ArrayList<>();

    @Override
    public int setLayoutId() {
        return R.layout.activity_person_albums;
    }

    @Override
    protected void onInitialize() {
        initTitleBar();
        GridViewSetup();

        //查询信息
        String personId = getIntent().getStringExtra(Intent_Key_Id);
        searchPhotos(personId);
    }

    @Override
    public void initPresenter() {
    }

    //初始化标头
    private void initTitleBar() {
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setTitle("全部照片");
    }

    /***
     * gridview的初始化
     */
    private void GridViewSetup() {
        //适配器
        albumsAdapter = new QuickAdapter<String>(
                PersonAlbumsActivity.this, R.layout.item_person_albums, new
                ArrayList<String>()) {

            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setImageUrl(R.id.img_person_photos, item);

                //点击事件
                helper.setOnClickListener(R.id.img_person_photos, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PersonAlbumsActivity.this,
                                BigImgLookActivity.class);
                        intent.putExtra(BigImgLookActivity.INTENT_KEY_IMG_LIST,
                                (Serializable) photoList);
                        intent.putExtra(BigImgLookActivity.INTENT_KEY_INDEX,
                                0);
                        startActivity(intent);
                    }
                });
            }
        };
        albumsGridView.setAdapter(albumsAdapter);
    }

    /**
     * 查询照片信息
     */
    private void searchPhotos(String personId) {
        DialogUtil.instance().showLoadingDialog("数据加载中");
        BmobQuery<Photo> query = new BmobQuery<>();
        query.addWhereEqualTo("mUserID", personId);
        query.findObjects(PersonAlbumsActivity.this, new FindListener<Photo>() {

            @Override
            public void onSuccess(List<Photo> list) {
                DialogUtil.dismissDialog();
                photoList.clear();
                albumsAdapter.clear();
                for (Photo bean : list) {
                    photoList.add(bean.getmPhotoUrl());
                    albumsAdapter.add(bean.getmPhotoUrl());
                }
            }

            @Override
            public void onError(int i, String s) {
                DialogUtil.dismissDialog();
                ToastUtil.show("获取照片失败");
            }
        });
    }
}
