package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.MyKaoQinAdapter;
import activity.lianqun.herry.com.workproject_lianqun.adpter.MyLinearLayoutManager;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;

/**
 * Created by Administrator on 2016/11/23.
 */

public class KaoQinActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private MyKaoQinAdapter adapter;
    private List<String> mList;
    private ScrollView mScorllView;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_kaoqin, R.string.activity_kaoqin);


    }

    @Override
    protected void setUpView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_kaoQin);
        mScorllView = (ScrollView) findViewById(R.id.ScrollView_kaoqin);
        mScorllView.smoothScrollTo(0, 0);

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        initData();
        adapter = new MyKaoQinAdapter(mList);
        mRecyclerView.setAdapter(adapter);
        //添加footer
        setFooterView(mRecyclerView);

    }

    private void setFooterView(RecyclerView mRecyclerView) {
        View footer = LayoutInflater.from(this).inflate(R.layout.item_footer, mRecyclerView, false);
        adapter.setFooterView(footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KaoQinActivity.this, KaoQinDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mList.add("item" + i);
        }
    }
}
