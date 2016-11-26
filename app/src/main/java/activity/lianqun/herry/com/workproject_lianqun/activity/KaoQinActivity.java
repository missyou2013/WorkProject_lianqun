package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.MyKaoQinAdapter;
import activity.lianqun.herry.com.workproject_lianqun.adpter.MyLinearLayoutManager;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.models.Config;
import activity.lianqun.herry.com.workproject_lianqun.models.Sign;
import activity.lianqun.herry.com.workproject_lianqun.models.Signs;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.DateUtil;
import activity.lianqun.herry.com.workproject_lianqun.utils.JsonUtil;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;
import activity.lianqun.herry.com.workproject_lianqun.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/23.
 */

public class KaoQinActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.kaoqin_flag)
    TextView kaoqinFlag;
    @BindView(R.id.ac_kaoqin_date_week)
    TextView dateWeek;
    @BindView(R.id.ac_kaoqin_date_Hms)
    TextView dateHms;
    @BindView(R.id.im_qiandao)
    ImageView imQiandao;
    @BindView(R.id.flag1)
    TextView flag1;
    @BindView(R.id.work_time)
    TextView workTime;
    @BindView(R.id.btn_qiandao)
    Button btnQiandao;
    @BindView(R.id.im_qiantui)
    ImageView imQiantui;
    @BindView(R.id.flag2)
    TextView flag2;
    @BindView(R.id.off_time)
    TextView offTime;
    @BindView(R.id.btn_qiantui)
    Button btnQiantui;
    @BindView(R.id.ac_kq_tv_qiandao)
    TextView acKqTvQiandao;
    @BindView(R.id.ac_kq_tv_qiantui)
    TextView acKqTvQiantui;
    @BindView(R.id.ac_kaoqin_more)
    TextView acKaoqinMore;
    private RecyclerView mRecyclerView;
    private MyKaoQinAdapter adapter;
    private List<String> mList;
    private ScrollView mScorllView;
    private Sign sign;
    private Config config;
    //每页的条数
    private final int PAGESIZ = 10;
    //第几页
    private int pageNum = 1;
    private List<Signs> signsList = new ArrayList<Signs>();
    private String up_down_date;

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
//        initData();
//        adapter = new MyKaoQinAdapter(mList);
//        mRecyclerView.setAdapter(adapter);
//        //添加footer
//        setFooterView(mRecyclerView);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        String currentTime = DateUtil.getFormatToday();
        String currentWeek = DateUtil.StringData();

        dateWeek.setText(currentWeek);
        dateHms.setText(currentTime);

        //检查是否签到签退
        if (CommonUtils.isOnline(this)) {
            getdata_qiandao(SharedPreferencesUtils.getUID(this));
        } else {
            Toast.makeText(this, getText(R.string.ac_login_error_net_txt), Toast.LENGTH_LONG)
                    .show();
        }



    }

    @OnClick({R.id.btn_qiandao, R.id.btn_qiantui})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qiandao:
                //签到
                try {
                    getdata_qindao_or_qiantui(SharedPreferencesUtils.getUID(this), "1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_qiantui:
                //签退
                try {
                    getdata_qindao_or_qiantui(SharedPreferencesUtils.getUID(this), "2");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //检查签到 传userid   type=1签到2签退
    private void getdata_qiandao(String userid) {
        OkHttpUtils
                .post()
                .url(CHECK_SIGN)
                .addParams("userid", userid)
                .addParams("cid", SharedPreferencesUtils.getCompanyId(KaoQinActivity.this))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
//                        CommonUtils.showLoadingDialog(KaoQinActivity.this);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        L.d("检查签到response====" + e.getMessage());
//                        CommonUtils.hideLoadingDialog(KaoQinActivity.this);
                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("检查签到response====" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String result = object.optString("result");
                                if ("true".equals(result)) {
                                    String c = object.optString("sign");
                                    if (!TextUtils.isEmpty(c)) {
                                        sign = JsonUtil.fromJson(c, Sign.class);
                                        //签到
                                        if (!TextUtils.isEmpty(String.valueOf(sign.getSignin()))
                                                && !"null".equals(String.valueOf(sign.getSignin()
                                        ))) {
                                            acKqTvQiandao.setText("已签到：" + String.valueOf(sign
                                                    .getSignin()));
                                            acKqTvQiandao.setVisibility(View.VISIBLE);
                                            btnQiandao.setVisibility(View.GONE);
                                        } else {
                                            acKqTvQiandao.setVisibility(View.GONE);
                                            btnQiandao.setVisibility(View.VISIBLE);
                                        }
                                        //签退
                                        if (!TextUtils.isEmpty(String.valueOf(sign.getSignout()))
                                                && !"null".equals(String.valueOf(sign.getSignout
                                                ()))) {
                                            acKqTvQiantui.setText("已签退：" + String.valueOf(sign
                                                    .getSignout()));
                                            acKqTvQiantui.setVisibility(View.VISIBLE);
                                            btnQiantui.setVisibility(View.GONE);
                                        } else {
                                            acKqTvQiantui.setVisibility(View.GONE);
                                            btnQiantui.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    String a = object.optString("config");
                                    if (!TextUtils.isEmpty(a)) {
                                        config = JsonUtil.fromJson(a, Config.class);
                                        up_down_date = config.getUpdate() +"-"+ config.getDowndate();
                                        if (!TextUtils.isEmpty(config.getUpdate())) {
                                            workTime.setText(config.getUpdate());
                                        }
                                        if (!TextUtils.isEmpty(config.getDowndate())) {
                                            offTime.setText(config.getDowndate());
                                        }
                                    }
                                } else {
                                    Toast.makeText(KaoQinActivity.this, getText(R.string
                                            .ac_forgot_fail), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(KaoQinActivity.this, getText(R.string.err_msg), Toast
                                    .LENGTH_LONG).show();
                        }

                        //查询签到记录
                        if (CommonUtils.isOnline(KaoQinActivity.this)) {
                            getdata_qiandao_jilu(SharedPreferencesUtils.getUID(KaoQinActivity.this), String.valueOf
                                            (SharedPreferencesUtils.getUserInfor(KaoQinActivity.this).getStatus()),
                                    String.valueOf(pageNum), String.valueOf(PAGESIZ));
                        } else {
                            Toast.makeText(KaoQinActivity.this, getText(R.string.ac_login_error_net_txt), Toast.LENGTH_LONG)
                                    .show();
                        }
//                        CommonUtils.hideLoadingDialog(KaoQinActivity.this);
                    }
                });
    }

    //签到-签退
    private void getdata_qindao_or_qiantui(String userid, final String type) throws
            UnsupportedEncodingException {
        String wifi_str = URLDecoder.decode(CommonUtils.getWIFIInfo(this).get("ssid"), "UTF-8");
        //    /sign/signin     传type=1签到2签退，userid,cid，cid，wifi，ip(公司id)   签到签退
//    其中E001是网络错误
//   其他都是内部错误
        OkHttpUtils
                .post()
                .url(QIANDAO_QIANTUI)
                .addParams("userid", userid)
                .addParams("type", type)
                .addParams("cid", SharedPreferencesUtils.getCompanyId(KaoQinActivity.this))
                .addParams("wifi", wifi_str)
                .addParams("ip", CommonUtils.getWIFIInfo(this).get("ip"))

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
                        CommonUtils.showLoadingDialog(KaoQinActivity.this);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        L.d("签到-签退response====" + e.getMessage());
                        CommonUtils.hideLoadingDialog(KaoQinActivity.this);
                    }

                    @Override
                    public void onResponse(String response) {
                        if ("1".equals(type)) {
                            L.d("签到response====" + response);
                        } else {
                            L.d("签退response====" + response);
                        }
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String result = object.optString("result");
                                if ("true".equals(result)) {
                                    String signdate=object.optString("signdate");
                                    if ("1".equals(type)) {
                                        acKqTvQiandao.setText("已签到：" + signdate);
                                        acKqTvQiandao.setVisibility(View.VISIBLE);
                                        btnQiandao.setVisibility(View.GONE);
                                        acKqTvQiantui.setVisibility(View.GONE);
                                        btnQiantui.setVisibility(View.VISIBLE);
                                    } else {
                                        acKqTvQiantui.setText("已签退：" + signdate);
                                        acKqTvQiantui.setVisibility(View.VISIBLE);
                                        btnQiantui.setVisibility(View.GONE);
                                        acKqTvQiandao.setVisibility(View.GONE);
                                        btnQiandao.setVisibility(View.VISIBLE);
                                    }
                                } else if ("E001".equals(result)) {
                                    Toast.makeText(KaoQinActivity.this, getText(R.string.err_msg)
                                            , Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        CommonUtils.hideLoadingDialog(KaoQinActivity.this);
                    }
                });
    }


    //查询签到记录
    private void getdata_qiandao_jilu(String userid, String status, String pagenum, String
            pagesize) {
        OkHttpUtils
                .post()
                .url(CHAXUN_QIANDAO)
                .addParams("userid", userid)
                .addParams("status", status)
                .addParams("pagenum", pagenum)
                .addParams("pagesize", pagesize)
                .addParams("cid", SharedPreferencesUtils.getCompanyId(KaoQinActivity.this))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
                        CommonUtils.showLoadingDialog(KaoQinActivity.this);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        L.d("查询签到记录response====" + e.getMessage());
                        CommonUtils.hideLoadingDialog(KaoQinActivity.this);
                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("查询签到记录response====" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String result = object.optString("result");
                                if ("true".equals(result)) {
                                    String c = object.optString("signs");
                                    if (!TextUtils.isEmpty(c) && !c.equals("[]")) {
                                        signsList = JsonUtil.stringToArray(
                                                c,
                                                Signs[].class);
                                        if (signsList.size() > 0) {
                                            adapter = new MyKaoQinAdapter(signsList, up_down_date);
                                            mRecyclerView.setAdapter(adapter);
                                            //添加footer
//                                            setFooterView(mRecyclerView);
//                                            View head=null;
//                                            adapter.setHeaderView(head);
                                        }
                                    }
                                } else {
                                    Toast.makeText(KaoQinActivity.this, getText(R.string
                                            .ac_forgot_fail), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        CommonUtils.hideLoadingDialog(KaoQinActivity.this);
                    }
                });
    }

    @OnClick(R.id.ac_kaoqin_more)
    public void onClick() {
    }
}
