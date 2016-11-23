package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.models.Companys;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.JsonUtil;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.activity
 * Description: 登录
 * Date: 2016-11-23 11:30
 * User: lxj
 * version V1.0.0
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ac_login_spinner)
    Spinner acLoginSpinner;
    @BindView(R.id.ac_login_username_edit)
    EditText acLoginUsernameEdit;
    @BindView(R.id.ac_login_pwd_edit)
    EditText acLoginPwdEdit;
    @BindView(R.id.ac_login_forgot_pwd)
    TextView acLoginForgotPwd;
    @BindView(R.id.ac_login_btn_login)
    Button acLoginBtnLogin;
    @BindView(R.id.ac_login_tv_spinner)
    TextView acLoginTvSpinner;

    private List<Companys> companys = new ArrayList<Companys>();
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_login, R.string.login_activity, MODE_HOME);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


        if (CommonUtils.isOnline(this)) {
            getdata_company_list();


//            acLoginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(LoginActivity.this, "11111111111", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });


        } else {
            Toast.makeText(this, getText(R.string.ac_login_error_net_txt), Toast.LENGTH_LONG).show();
        }
    }

    @OnClick({R.id.toolbar_title, R.id.toolbar, R.id.ac_login_forgot_pwd, R.id.ac_login_btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title:
                break;
            case R.id.toolbar:
                break;
            case R.id.ac_login_forgot_pwd:
                break;
            case R.id.ac_login_btn_login:
                break;
        }
    }

    //登录
    void getdata_login() {
        OkHttpUtils
                .post()
                .url(LOGIN_URL)
                .addParams("loginname", "hyman")
                .addParams("password", "123")
                .addParams("companyid", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("登录response====" + response);
                    }
                });
    }

    //获取公司列表
    void getdata_company_list() {
        OkHttpUtils
                .get()
                .url(COMPANY_LISTS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("公司列表response====" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String result = object.optString("result");
                                if ("true".equals(result)) {
                                    String c = object.optString("companys");
                                    if (!TextUtils.isEmpty(c) && !c.equals("[]")) {


                                        companys = JsonUtil.stringToArray(
                                                object.optString("companys"),
                                                Companys[].class);
                                        data_list = new ArrayList<String>();
                                        for (int i = 0; i < companys.size(); i++) {
                                            data_list.add(i, String.valueOf(companys.get(i).getName()));
                                        }

//                                        acLoginSpinner.attachDataSource(data_list);


//                                        //适配器
//                                        arr_adapter = new ArrayAdapter<String>(LoginActivity.this, R.layout.spinner_item, R.id.spinner_item_tv, data_list);
//                                        //设置样式
//                                        arr_adapter.setDropDownViewResource(R.layout.spinner_item);
//                                        //加载适配器
//                                        acLoginSpinner.setAdapter(arr_adapter);
//                                        acLoginSpinner.setText(companys.get(0).getName());
//                                        SharedPreferencesUtils.setUID(LoginActivity.this, String.valueOf(Users.get(0).getCompanys().get(0).getId()));
//                                        SharedPreferencesUtils.setUserNickName(LoginActivity.this, Users.get(0).getCompanys().get(0).getName());

                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    @OnClick(R.id.ac_login_tv_spinner)
    public void onClick() {
        showPopupWindow(acLoginTvSpinner);
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(LoginActivity.this).inflate(
                R.layout.spinner_listview, null);
        contentView.setFocusable(true); // 这个很重要
        contentView.setFocusableInTouchMode(true);
        // 设置按钮的点击事件
        ListView lv = (ListView) contentView.findViewById(R.id.spinner_listview);






        final PopupWindow popupWindow = new PopupWindow(contentView,
                (2 * CommonUtils.getScreenWidth(LoginActivity.this)) / 3, (1 * CommonUtils.getScreenHeight(LoginActivity.this)) / 3, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(
//                R.drawable.selectmenu_bg_downward));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }
}
