package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.TabFragmentPagerAdapter;


/**
 * Created by Administrator on 2016/11/25.
 */

public class ApplyRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private TabFragmentPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private PopupWindow mPopWindow;
    private TextView textView_status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_record);


        initView();


    }

    private void initView() {
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

        textView_status = (TextView) findViewById(R.id.apply_choose_status);
        textView_status.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_choose_status:
                showPopupWindow();
                break;
            case R.id.pop_computer: {
                Toast.makeText(this, "clicked computer", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            case R.id.pop_financial: {
                Toast.makeText(this, "clicked financial", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            case R.id.pop_manage: {
                Toast.makeText(this, "clicked manage", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
        }
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ApplyRecordActivity.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_computer);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_financial);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);


        mPopWindow.showAsDropDown(textView_status);

    }
}
