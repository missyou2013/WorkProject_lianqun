package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.constants.ConstantValues;
import activity.lianqun.herry.com.workproject_lianqun.core.AppStatusTracker;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2016/11/16.
 */

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_OFFLINE);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_welcome, -1, MODE_NONE);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        handler.sendEmptyMessageDelayed(0, 3500);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
<<<<<<< HEAD
 startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            //startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
=======
//            startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));

            if (SharedPreferencesUtils.getLoadingStatement(WelcomeActivity.this)) {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            }else{
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
>>>>>>> eb79dce94cbf84147b4bf9ad1b67c3564720cd84
            finish();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }
}
