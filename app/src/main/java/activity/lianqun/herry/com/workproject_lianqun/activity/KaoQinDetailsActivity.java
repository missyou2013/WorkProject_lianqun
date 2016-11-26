package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.Calendar;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/11/24.
 */

public class KaoQinDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.time_chose)
    TextView timeChose;
    @BindView(R.id.time_delete)
    ImageView timeDelete;
    @BindView(R.id.time_add)
    ImageView timeAdd;
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
    //
    private String text_year, text_month;

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
        time_dialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.time_delete, R.id.time_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.time_delete:
                // text_month = timeChose.getText().toString().substring(5, timeChose.getText().toString().length());

                break;
            case R.id.time_add:
                break;
        }
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


    private void time_dialog() {

        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(KaoQinDetailsActivity.this,
                DatePickerListener, iYear, iMonth, iDay);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.setButton(DialogInterface.BUTTON_POSITIVE, "设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        picker.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        picker.show();


    }

    private DatePickerDialog.OnDateSetListener DatePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            timeChose.setText(year + "-" + (monthOfYear + 1));
        }
    };

}
