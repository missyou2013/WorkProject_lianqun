package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.constants.ConstantValues;
import activity.lianqun.herry.com.workproject_lianqun.core.AppStatusTracker;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;

/**
 * Created by Administrator on 2016/11/16.
 */

public class OtherActivity2 extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.other_activity, R.string.other_activity);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }


    @Override
    protected void setUpMenu(int menuId) {
        super.setUpMenu(R.menu.menu, "筛选");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Toast.makeText(getApplicationContext(), "筛选", Toast.LENGTH_LONG).show();

                break;
        }
        return true;
    }
}
