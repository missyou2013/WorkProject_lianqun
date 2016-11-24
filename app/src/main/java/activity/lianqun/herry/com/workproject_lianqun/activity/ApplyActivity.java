package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;

/**
 * Created by Administrator on 2016/11/24.
 */

public class ApplyActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_apply, R.string.activity_apply);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected void setUpMenu(int menuId) {
        super.setUpMenu(R.menu.menu, "申请记录");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Toast.makeText(getApplicationContext(), "申请记录", Toast.LENGTH_LONG).show();

                break;
        }
        return true;
    }
}
