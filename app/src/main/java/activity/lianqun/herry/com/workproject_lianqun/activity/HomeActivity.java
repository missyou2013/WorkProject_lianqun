package activity.lianqun.herry.com.workproject_lianqun.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.trackshow.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {


    @BindView(R.id.btn_details)
    Button btnDetails;
    @BindView(R.id.btn_details2)
    Button btnDetails2;
    @BindView(R.id.btn_map)
    Button btn_map;
    @BindView(R.id.listMore)
    Button listMore;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_home, R.string.activity_home, MODE_HOME);
        ButterKnife.bind(HomeActivity.this);
    }

    @Override
    protected void setUpView() {
//findViewById

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
// data
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation


    }


    @OnClick({R.id.btn_details, R.id.btn_details2, R.id.btn_map,R.id.listMore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_details:
                Intent intent = new Intent(this, OtherActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_details2:
                Intent intent2 = new Intent(this, OtherActivity2.class);
                startActivity(intent2);
                break;
            case R.id.btn_map:
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.listMore:
                Intent intent4 = new Intent(this, ListDataActivity.class);
                startActivity(intent4);
                break;
        }
    }


}
