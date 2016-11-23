package activity.lianqun.herry.com.workproject_lianqun.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.GlideImageLoader;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {
    @BindView(R.id.relativeLayout_kaoQin)
    RelativeLayout relativeLayoutKaoQin;
    @BindView(R.id.relativeLayout_shenQing)
    RelativeLayout relativeLayoutShenQing;
    @BindView(R.id.relativeLayout_trace)
    RelativeLayout relativeLayoutTrace;
    @BindView(R.id.relativeLayout_ad)
    RelativeLayout relativeLayoutAd;
    @BindView(R.id.relativeLayout_my)
    RelativeLayout relativeLayoutMy;
    private Banner banner;
    private List<String> list_images = new ArrayList<>();


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_home, R.string.activity_home, MODE_HOME);
        ButterKnife.bind(HomeActivity.this);

    }


    @Override
    protected void setUpView() {

        list_images.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        list_images.add("http://pic3.bbzhi.com/fengjingbizhi/gaoqingkuanpingfengguangsheyingps/show_fengjingta_281299_11.jpg");
        banner = (Banner) findViewById(R.id.home_banner);
        banner.setImages(list_images).setImageLoader(new GlideImageLoader()).start();

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation


    }


    @OnClick({R.id.relativeLayout_kaoQin, R.id.relativeLayout_shenQing, R.id.relativeLayout_trace, R.id.relativeLayout_ad, R.id.relativeLayout_my})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.relativeLayout_kaoQin:
                intent.setClass(HomeActivity.this, KaoQinActivity.class);
                break;
            case R.id.relativeLayout_shenQing:
                break;
            case R.id.relativeLayout_trace:
                break;
            case R.id.relativeLayout_ad:
                break;
            case R.id.relativeLayout_my:
                break;
        }

            startActivity(intent);

    }
}
