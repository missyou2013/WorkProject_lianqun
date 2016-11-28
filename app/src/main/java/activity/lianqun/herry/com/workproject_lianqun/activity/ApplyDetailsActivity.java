package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;


import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;


/**
 * Created by Administrator on 2016/11/26.
 */

public class ApplyDetailsActivity extends BaseActivity implements View.OnClickListener {
    private PopupWindow mPopWindow;
    private TextView textView;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_applydetails, R.string.apply_details);
    }

    @Override
    protected void setUpView() {
        textView = (TextView) findViewById(R.id.biaozhi);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected void setUpMenu(int menuId) {
        super.setUpMenu(R.menu.menu2);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                showPopupWindow();

                break;
        }
        return true;
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ApplyDetailsActivity.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_computer);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_financial);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);


        mPopWindow.showAsDropDown(textView);


    }

    @Override
    public void onClick(View v) {

    }
}
