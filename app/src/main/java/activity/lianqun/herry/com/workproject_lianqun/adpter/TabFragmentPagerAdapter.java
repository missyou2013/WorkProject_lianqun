package activity.lianqun.herry.com.workproject_lianqun.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import activity.lianqun.herry.com.workproject_lianqun.fragment.TabFragment;

/**
 * Created by Administrator on 2016/11/25.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String[] mTitles = {"请假申请", "报销申请", "其他"};

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(mTitles[position]);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
