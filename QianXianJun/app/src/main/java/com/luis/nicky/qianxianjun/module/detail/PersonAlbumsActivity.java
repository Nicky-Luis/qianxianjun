package com.luis.nicky.qianxianjun.module.detail;

import android.widget.GridView;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.BaseAdapterHelper;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.QuickAdapter;
import com.luis.nicky.qianxianjun.base.basic.BaseActivityWithTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class PersonAlbumsActivity extends BaseActivityWithTitleBar {

    @InjectView(R.id.gv_person_albums)
    GridView albumsGridView;
    // 学生数据
    private List<String> photoList;
    // 适配器
    private QuickAdapter<String> albumsAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_person_albums;
    }

    @Override
    protected void onInitialize() {
        GridViewSetup();
    }

    @Override
    public void initPresenter() {
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
            }
        };
        albumsGridView.setAdapter(albumsAdapter);
    }

}
