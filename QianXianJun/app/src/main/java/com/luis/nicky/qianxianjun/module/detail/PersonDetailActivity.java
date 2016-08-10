package com.luis.nicky.qianxianjun.module.detail;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.basic.BaseActivityWithTitleBar;
import com.luis.nicky.qianxianjun.base.view.DialogUtil;
import com.luis.nicky.qianxianjun.base.view.TitleBar;
import com.luis.nicky.qianxianjun.databinding.ActivityPersonDetailBinding;
import com.luis.nicky.qianxianjun.helper.PersonItemBean;
import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.model.TargetPerson;
import com.luis.nicky.qianxianjun.module.detail.interfaces.IDetailPresenter;
import com.luis.nicky.qianxianjun.module.detail.presenter.DetailPresenter;

import java.util.List;

import de.greenrobot.event.EventBus;

import static com.luis.nicky.qianxianjun.module.detail.MatchPersonActivity.Intent_Key_Id;

public class PersonDetailActivity extends BaseActivityWithTitleBar {

    //presenter
    private IDetailPresenter detailPresenter;
    //rootView
    private View rootView;

    @Override
    public int setLayoutId() {
        return R.layout.activity_person_detail;
    }

    @Override
    public void initPresenter() {
        detailPresenter = new DetailPresenter(this);
    }

    @Override
    public boolean setDataBindingFlag() {
        return true;
    }

    @Override
    protected void onInitialize() {
        //注册EventBus
        EventBus.getDefault().register(this);
        this.rootView = getRootView();
        initTitleBar();
        //一进入页面就加载
        DialogUtil.instance().showLoadingDialog("加载数据中");
    }

    //eventbus
    public void onEventMainThread(PersonItemBean itemBean) {
        String personId = itemBean.person.getUUID();
        String headUrl = itemBean.photos.get(0);
        setImageView(R.id.img_person_head, headUrl);

        getPersonInfo(personId);
        initAlbumsView(itemBean.photos);
    }

    //初始化标头
    private void initTitleBar() {
        TitleBar titleBar = (TitleBar) rootView.findViewById(R.id.title_bar);
        titleBar.setTitle("用户的详情");
        //添加右侧按钮
        TextView nextBtn = new TextView(this);
        nextBtn.setTextSize(14);
        nextBtn.setText("查看匹配");
        nextBtn.setTextColor(ContextCompat.getColor(this, R.color.main_light_white));
        titleBar.setRightView(nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToMatch();
            }
        });
    }

    //初始化相册部分
    private void initAlbumsView(final List<String> photos) {
        setImageView(R.id.img_person_photo1, photos.get(1));
        setImageView(R.id.img_person_photo2, photos.get(2));
        setImageView(R.id.img_person_photo3, photos.get(3));

        //跳转至相册页面
        rootView.findViewById(R.id.ly_person_albums_root_view).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intentAlbums = new Intent(PersonDetailActivity.this,
                                PersonAlbumsActivity.class);
                        startActivity(intentAlbums);
                        //延时发送
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                EventBus.getDefault().post(photos);
                            }
                        }, 50);
                    }
                });
    }

    //获取用户信息
    private void getPersonInfo(String personId) {
        detailPresenter.getPersonInfo(personId, new DetailPresenter
                .ResultCallback() {
            @Override
            public void onSuccessed(Person person, TargetPerson targetPerson) {
                DialogUtil.dismissDialog();

                //databing绑定数据
                ActivityPersonDetailBinding binding = (ActivityPersonDetailBinding)
                        viewDataBinding;
                binding.setPerson(person);
                binding.setTarget(targetPerson);
            }

            @Override
            public void onFailed() {
                DialogUtil.dismissDialog();
            }
        });
    }

    //设置图片
    private void setImageView(int id, String imageUrl) {
        if (null == imageUrl || imageUrl.length() <= 0) {
            return;
        }
        Uri uri = Uri.parse(imageUrl);
        SimpleDraweeView view = (SimpleDraweeView) rootView.findViewById(id);
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

    //跳转到匹配页面
    private void startToMatch() {
        Intent intentMatch = new Intent(PersonDetailActivity.this, MatchPersonActivity
                .class);
        intentMatch.putExtra(Intent_Key_Id, "");
        startActivity(intentMatch);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

}
