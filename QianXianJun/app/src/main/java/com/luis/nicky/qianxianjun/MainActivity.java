package com.luis.nicky.qianxianjun;

import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.BaseAdapterHelper;
import com.luis.nicky.qianxianjun.base.base_adapter_helper.QuickAdapter;
import com.luis.nicky.qianxianjun.base.basic.BaseActivity;
import com.luis.nicky.qianxianjun.base.utils.ToastUtil;
import com.luis.nicky.qianxianjun.entry.SexType;
import com.luis.nicky.qianxianjun.helper.PersonItemBean;
import com.luis.nicky.qianxianjun.module.add.AddPersonActivity;
import com.luis.nicky.qianxianjun.module.detail.PersonDetailActivity;
import com.luis.nicky.qianxianjun.module.main.interfaces.IMainPresenter;
import com.luis.nicky.qianxianjun.module.main.interfaces.IMainView;
import com.luis.nicky.qianxianjun.module.main.presenter.MainPresenter;
import com.luis.nicky.qianxianjun.module.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity implements IMainView {

    // 动态listview
    @InjectView(R.id.person_listview)
    PullToRefreshListView personListView;
    //adapter
    private QuickAdapter<PersonItemBean> adapter;
    //presenter 对象
    private IMainPresenter mainPresenter;


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
        lisviewSet();
        //第一次加载
        mainPresenter.refreshData(true, new MainPresenter.ResultCallback() {
            @Override
            public void onSucceed(List<PersonItemBean> result) {
                personListView.onRefreshComplete();
                adapter.clear();
                adapter.replaceAll(result);
            }

            @Override
            public void onFailed(int code) {
                personListView.onRefreshComplete();
            }
        });
    }

    @Override
    public void initPresenter() {
        mainPresenter = new MainPresenter(MainActivity.this);
    }

    /**
     * 数据绑定
     */
    private void lisviewSet() {

        //初始化
        adapter = new QuickAdapter<PersonItemBean>(MainActivity.this, R.layout
                .item_main_person,
                new ArrayList<PersonItemBean>()) {

            @Override
            protected void convert(BaseAdapterHelper helper, final PersonItemBean item) {
                //绑定数据

                helper.setImageUrl(R.id.img_person_head, item.photos.get(0))
                        .setText(R.id.txt_person_age, item.person.getBirthday() + "岁")
                        .setText(R.id.txt_person_area, item.person.getArea())
                        .setText(R.id.txt_person_target, item.personTarget
                                .getDescription())
                        .setImageResource(R.id.img_person_sex, SexType.getSexRes(item
                                .person.getmUserSex()));

                //绑定事件
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,
                                PersonDetailActivity.class);
                        startActivity(intent);

                        //延时发送
                        new Handler().postDelayed(new Runnable(){
                            public void run() {
                                EventBus.getDefault().post(item);
                            }
                        }, 50);
                    }
                };
                helper.setOnClickListener(R.id.layout_person_item_root, listener);
            }
        };

        // 设置不可点击
        adapter.setItemsClickEnable(false);
        // 设置刷新模式
        personListView.setMode(PullToRefreshBase.Mode.BOTH);
        //绑定adpter
        personListView.setAdapter(adapter);
        personListView.setOnRefreshListener(new PullToRefreshBase
                .OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView>
                                                    pullToRefreshBase) {
                //下拉
                mainPresenter.refreshData(false, new MainPresenter.ResultCallback() {
                    @Override
                    public void onSucceed(List<PersonItemBean> result) {
                        personListView.onRefreshComplete();
                        adapter.clear();
                        adapter.replaceAll(result);
                    }

                    @Override
                    public void onFailed(int code) {
                        personListView.onRefreshComplete();
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                //下拉
                mainPresenter.addMoreData(new MainPresenter.ResultCallback() {
                    @Override
                    public void onSucceed(List<PersonItemBean> result) {
                        personListView.onRefreshComplete();
                        adapter.addAll(result);
                    }

                    @Override
                    public void onFailed(int code) {
                        personListView.onRefreshComplete();
                    }
                });
            }
        });
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
                ToastUtil.show("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //////////////////////////////////////////////////////
}
