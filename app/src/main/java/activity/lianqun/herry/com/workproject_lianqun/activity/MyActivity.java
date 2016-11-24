package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.activity
 * Description: 我的
 * Date: 2016-11-24 15:15
 * User: lxj
 * version V1.0.0
 */

public class MyActivity extends BaseActivity {

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_my, R.string.login_activity, MODE_HOME);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }
}
