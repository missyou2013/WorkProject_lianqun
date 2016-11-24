package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseListActivity;
import activity.lianqun.herry.com.workproject_lianqun.widgets.pull.BaseViewHolder;

/**
 * Created by Administrator on 2016/11/22.
 */

public class ListDataActivity extends BaseListActivity<String> {
    private int page = 1;

    @Override
    protected void setUpContentView() {
        super.setUpContentView();
        setContentView(R.layout.activity_kaoqin_details);
    }

    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.listData);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        super.setUpData(savedInstanceState);
        recycler.setRefreshing();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    }


    //    @Override
//    protected ILayoutManager getLayoutManager() {
//        MyGridLayoutManager layoutManager = new MyGridLayoutManager(getApplicationContext(), 3);
//        return layoutManager;
//    }

    //    @Override
//    protected ILayoutManager getLayoutManager() {
//        return new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//    }

    @Override
    public void onRefresh(int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
            mDataList.add("1");
            mDataList.add("2");
            mDataList.add("3");
            mDataList.add("4");
            mDataList.add("5");
            mDataList.add("6");

        }
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mSampleListItemImg;
        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.mSampleListItemImg);
        }

        @Override
        public void onBindViewHolder(int position) {
            mSampleListItemLabel.setText(mDataList.get(position));
            //  mSampleListItemLabel.setVisibility(View.GONE);
//            Glide.with(mSampleListItemImg.getContext())
//                    .load(mDataList.get(position).url)
//                    .centerCrop()
//                    .placeholder(R.color.app_primary_color)
//                    .crossFade()
//                    .into(mSampleListItemImg);
        }

        @Override
        public void onItemClick(View view, int position) {

        }

    }
}
