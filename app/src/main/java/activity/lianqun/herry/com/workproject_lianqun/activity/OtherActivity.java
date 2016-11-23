package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/11/16.
 */

public class OtherActivity extends BaseActivity {


    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.button_stop)
    Button buttonStop;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.other_activity, R.string.other_activity);
        ButterKnife.bind(OtherActivity.this);


    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }


    @OnClick({R.id.button_start, R.id.button_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start:
                //    PollingUtils.startPollingService(OtherActivity.this, 5, PollingService.class, PollingService.ACTION);
                break;
            case R.id.button_stop:
                //   PollingUtils.stopPollingService(OtherActivity.this, PollingService.class, PollingService.ACTION);
                break;
        }
    }


}
