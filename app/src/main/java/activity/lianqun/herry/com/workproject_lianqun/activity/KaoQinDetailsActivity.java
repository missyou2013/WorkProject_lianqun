package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.trackutils.DateDialog;
import activity.lianqun.herry.com.workproject_lianqun.trackutils.DateUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/24.
 */

public class KaoQinDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.time_chose)
    TextView timeChose;
    private RecyclerView mRecyclerView;

    /**
     * 服务器端一共多少条数据
     */
    private static final int TOTAL_COUNTER = 34;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private HomeAdapter adpter;
    //Time
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int startTime = 0;
    private int endTime = 0;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_kaoqin_details, R.string.activity_kaoqin);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.ry_kaoqin_details);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adpter = new HomeAdapter();
        mRecyclerView.setAdapter(adpter);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {

    }


    @OnClick(R.id.time_chose)
    public void onClick() {
        chooseTime();
    }

    private void chooseTime() {
        // 选择日期
        int[] date = null;
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        if (year == 0 && month == 0 && day == 0) {
            String curDate = DateUtils.getCurrentDate();
            date = DateUtils.getYMDArray(curDate, "-");
        }

        if (date != null) {
            year = date[0];
            month = date[1];
            day = date[2];
        }

        final DateDialog dateDiolog = new DateDialog(this, new DateDialog.PriorityListener() {

            public void refreshPriorityUI(String sltYear, String sltMonth,
                                          String sltDay, DateDialog.CallBack back) {

                Log.d("TGA", sltYear + sltMonth + sltDay);
                year = Integer.parseInt(sltYear);
                month = Integer.parseInt(sltMonth);
                day = Integer.parseInt(sltDay);
                String st = year + "年" + month + "月" + day + "日0时0分0秒";
                String et = year + "年" + month + "月" + day + "日23时59分59秒";
                startTime = Integer.parseInt(DateUtils.getTimeToStamp(st));
                endTime = Integer.parseInt(DateUtils.getTimeToStamp(et));

                back.execute();
            }

        }, new DateDialog.CallBack() {

            public void execute() {

                timeChose.setText(month + "年" + day + " 月");
            }
        }, year, month, day, width, height, "选择日期", 1);

        Window window = dateDiolog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        dateDiolog.setCancelable(true);
        dateDiolog.show();
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(KaoQinDetailsActivity.this).inflate(R.layout.item_recycler_kaoqin, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //   holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            //   TextView tv;

            public MyViewHolder(View view) {
                super(view);

            }
        }
    }

}
