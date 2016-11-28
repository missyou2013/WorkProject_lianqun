package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.utils.ApiConfig;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;
import activity.lianqun.herry.com.workproject_lianqun.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.activity
 * Description: 通知公告
 * Date: 2016-11-26 16:15
 * User: lxj
 * version V1.0.0
 */

public class TongZhiActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ac_tongzhi_list)
    ListView acTongzhiList;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_tongzhi, R.string.activity_tongzhi);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }


    //获取通知列表
    private void getdata_tongzhi_list() {
//        pagenum,pagesize

        OkHttpUtils
                .get()
                .url(ApiConfig.TONGZHI_LIST)
                .addParams("userid", SharedPreferencesUtils.getUID(this))
                .addParams("cid", SharedPreferencesUtils.getCompanyId(TongZhiActivity.this))
                .addParams("pagenum", "1")
                .addParams("pagesize", "100")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        L.d("通知列表response====" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("通知列表response====" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String result = object.optString("result");
                                if ("true".equals(result)) {
                                    String c = object.optString("companys");
                                    if (!TextUtils.isEmpty(c) && !c.equals("[]")) {
//                                        companys = JsonUtil.stringToArray(
//                                                object.optString("companys"),
//                                                Companys[].class);
//                                        data_list = new ArrayList<String>();
//                                        for (int i = 0; i < companys.size(); i++) {
//                                            data_list.add(i, String.valueOf(companys.get(i)
//                                                    .getName()));
//                                        }
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        if (CommonUtils.isOnline(this)) {
            getdata_tongzhi_list();
        } else {
            Toast.makeText(this, getText(R.string.ac_login_error_net_txt), Toast.LENGTH_LONG)
                    .show();
        }

    }
}
