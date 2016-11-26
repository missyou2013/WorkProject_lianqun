package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.SharedPreferencesUtils;
import activity.lianqun.herry.com.workproject_lianqun.widgets.pull.CircleImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.activity
 * Description: 我的
 * Date: 2016-11-24 15:15
 * User: lxj
 * version V1.0.0
 */

public class MyActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ac_my_photo)
    CircleImageView acMyPhoto;
    @BindView(R.id.ac_my_tel)
    TextView acMyTel;
    @BindView(R.id.ac_my_cname)
    TextView acMyCname;
    @BindView(R.id.ac_my_0_rl)
    RelativeLayout acMy0Rl;
    @BindView(R.id.ac_my_1_iv)
    ImageView acMy1Iv;
    @BindView(R.id.ac_my_1_tv)
    TextView acMy1Tv;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.ac_my_1_rl)
    RelativeLayout acMy1Rl;
    @BindView(R.id.ac_my_2_iv)
    ImageView acMy2Iv;
    @BindView(R.id.ac_my_2_tv)
    TextView acMy2Tv;
    @BindView(R.id.imageView72)
    ImageView imageView72;
    @BindView(R.id.ac_my_2_rl)
    RelativeLayout acMy2Rl;
    @BindView(R.id.ac_my_3_iv)
    ImageView acMy3Iv;
    @BindView(R.id.ac_my_3_tv)
    TextView acMy3Tv;
    @BindView(R.id.imageView73)
    ImageView imageView73;
    @BindView(R.id.ac_my_3_rl)
    RelativeLayout acMy3Rl;
    @BindView(R.id.ac_my_exit)
    TextView acMyExit;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_my, R.string.ac_my);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(SharedPreferencesUtils.getUserInfor(MyActivity.this).getPhone())) {
            acMyTel.setText(SharedPreferencesUtils.getUserInfor(MyActivity.this).getPhone());
        }
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getUserInfor(MyActivity.this).getCname())) {
            acMyTel.setText(SharedPreferencesUtils.getUserInfor(MyActivity.this).getCname());
        }


    }

    @OnClick({R.id.ac_my_0_rl, R.id.ac_my_1_rl, R.id.ac_my_2_rl, R.id.ac_my_3_rl, R.id.ac_my_exit})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ac_my_0_rl:
                //头像
                break;
            case R.id.ac_my_1_rl:
                //系统设置
                break;
            case R.id.ac_my_2_rl:
                //修改密码
                intent = new Intent();
                intent.setClass(this, ForgotPasswordActivity.class);
                break;
            case R.id.ac_my_3_rl:
                //关于我们
                intent = new Intent();
                intent.setClass(this, AboutUsActivity.class);
                break;
            case R.id.ac_my_exit:
                //退出
                CommonUtils.showLoadingDialog(MyActivity.this);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        CommonUtils.hideLoadingDialog(MyActivity.this);
                        SharedPreferencesUtils.setLoadingStatement(MyActivity.this, false);
                        SharedPreferencesUtils.clear(MyActivity.this);
                        Toast.makeText(MyActivity.this, getText(R.string.exit_msg), Toast.LENGTH_LONG).show();
                    }
                }, 2000);
                intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
