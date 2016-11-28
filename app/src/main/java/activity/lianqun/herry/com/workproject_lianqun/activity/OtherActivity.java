package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.MyKaoQinAdapter;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/11/16.
 */

public class OtherActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private MyKaoQinAdapter adapter;
    private List<String> mList;

    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.button_stop)
    Button buttonStop;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.other_activity, R.string.other_activity);
        ButterKnife.bind(OtherActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_kaoQin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //  mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        initData();
        adapter = new MyKaoQinAdapter(mList);
        mRecyclerView.setAdapter(adapter);

    }
    //初始化RecyclerView中每个item的数据
    private void initData(){
        mList = new ArrayList<String>();
        for (int i = 0; i < 20; i++){
            mList.add("item" + i);
        }
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
