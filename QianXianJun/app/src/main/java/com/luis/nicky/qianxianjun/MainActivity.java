package com.luis.nicky.qianxianjun;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.luis.nicky.qianxianjun.entry.SexType;
import com.luis.nicky.qianxianjun.helper.PersonItemBean;
import com.luis.nicky.qianxianjun.helper.PersonNetBean;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.BaseAdapter;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.BaseAdapterHelper;
import com.luis.nicky.qianxianjun.module.add.AddPersonActivity;
import com.luis.nicky.qianxianjun.base.basic.BaseActivity;
import com.luis.nicky.qianxianjun.base.utils.ToastUtil;
import com.luis.nicky.qianxianjun.module.search.SearchActivity;


import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.listview_main_data)
    ListView mainListView;
    //adapter
    private BaseAdapter<PersonItemBean> adapter;
    //数据集
    private List<PersonItemBean> listDatas;

    @OnClick(value = {R.id.btn_search, R.id.btn_add_person})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_search:
                startSearch();
                break;

            //添加
            case R.id.btn_add_person:
                startAdd();
                break;

            default:
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void onInitialize() {

    }

    @Override
    public void initPresenter() {

    }

    /**
     * 数据绑定
     */
    private void lisviewSet() {

        //初始化
        adapter = new BaseAdapter<PersonItemBean>(MainActivity.this,
                R.layout.item_main_person, listDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper, PersonItemBean item) {
                //绑定数据
                helper.setImageUrl(R.id.img_person_head, item.personHeadUrl);
                helper.setText(R.id.txt_person_age, item.personAge + "岁");
                helper.setText(R.id.txt_person_area, item.personArea);
                helper.setText(R.id.txt_person_target, item.personTarget);
                helper.setImageResource(R.id.img_person_sex, SexType.getSexRes(item.personSex));

                //绑定事件
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // 点击item

                    }
                };
                helper.setOnClickListener(R.id.layout_person_item_root, listener);
            }
        };

        // 设置不可点击
        adapter.setItemsClickEnable(false);
        //绑定adpter
        mainListView.setAdapter(adapter);
    }

    /***
     * 搜索
     */
    private void startSearch() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    /***
     * 添加
     */
    private void startAdd() {
        Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
        startActivity(intent);
    }

    //再按一次退出程序
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.show(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
