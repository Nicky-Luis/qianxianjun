package com.luis.nicky.qianxianjun.module.detail;


import android.content.Intent;
import android.net.Uri;
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
import com.luis.nicky.qianxianjun.base.view.TitleBar;
import com.luis.nicky.qianxianjun.databinding.ActivityPersonDetailBinding;
import com.luis.nicky.qianxianjun.helper.PersonItemBean;
import com.luis.nicky.qianxianjun.module.detail.interfaces.IDetailPresenter;
import com.luis.nicky.qianxianjun.module.detail.presenter.DetailPresenter;

import java.util.List;

import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static com.luis.nicky.qianxianjun.module.detail.MatchPersonActivity.Intent_Key_Id;

public class PersonDetailActivity extends BaseActivityWithTitleBar {

    //presenter
    private IDetailPresenter detailPresenter;
    //rootView
    private View rootView;
    //用户id
    private String personId;


    @OnClick(value = {R.id.ly_person_albums_root_view})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.ly_person_albums_root_view:
                Intent intentAlbums = new Intent(PersonDetailActivity.this,
                        PersonAlbumsActivity.class);
                intentAlbums.putExtra(PersonAlbumsActivity.Intent_Key_Id, personId);
                startActivity(intentAlbums);
                break;

            default:
                break;
        }
    }

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
    }

    //eventbus
    public void onEventMainThread(PersonItemBean itemBean) {
        List<String> photoUrls = itemBean.photos;
        personId = itemBean.person.getUUID();
        String headUrl = photoUrls.get(0);
        setImageView(R.id.img_person_head, headUrl);

        //databing绑定数据
        ActivityPersonDetailBinding binding = (ActivityPersonDetailBinding)
                viewDataBinding;
        binding.setPerson(itemBean.person);
        binding.setTarget(itemBean.personTarget);
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
