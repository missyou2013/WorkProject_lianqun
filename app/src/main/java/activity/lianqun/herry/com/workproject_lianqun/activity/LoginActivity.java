package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.ypy.eventbus.EventBus;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.SpinnerAdapter;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication;
import activity.lianqun.herry.com.workproject_lianqun.models.Companys;
import activity.lianqun.herry.com.workproject_lianqun.models.User;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.FirstEvent;
import activity.lianqun.herry.com.workproject_lianqun.utils.JsonUtil;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;
import activity.lianqun.herry.com.workproject_lianqun.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication.companys;


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
    @BindView(R.id.ac_login_parent)
    LinearLayout acLoginParent;

    //    private List<Companys> companys = new ArrayList<Companys>();
    List<String> data_list = CustomApplication.data_list;
    private ArrayAdapter<String> arr_adapter;


    private SpinnerAdapter spinnerAdapter;
    private String Current_id = "";//当前选中的企业的id

    private User user;

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
//        if (CommonUtils.isOnline(this)) {
//            getdata_company_list();
//        } else {
//            Toast.makeText(this, getText(R.string.ac_login_error_net_txt), Toast.LENGTH_LONG)
// .show();
//        }
    }

    @OnClick({R.id.toolbar_title, R.id.toolbar, R.id.ac_login_forgot_pwd, R.id.ac_login_btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title:
                break;
            case R.id.toolbar:
                break;
            case R.id.ac_login_forgot_pwd:
                if (SharedPreferencesUtils.getLoadingStatement(this)) {
                    startActivity(new Intent(this, ForgotPasswordActivity.class));
                    finish();
                }
                break;
            case R.id.ac_login_btn_login:
                String name = acLoginUsernameEdit.getText().toString().trim();
                String pwd = acLoginPwdEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {
                    if (CommonUtils.isOnline(this)) {
                        if (!"".equals(Current_id)) {
//                            getfriend_Data(name, pwd);
                            getdata_login(name, pwd);
                        } else {
                            Toast.makeText(this, getText(R.string.ac_login_spinner_txt), Toast
                                    .LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, getText(R.string.ac_login_error_net_txt), Toast
                                .LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, getText(R.string.ac_login_error_promit_txt), Toast
                            .LENGTH_LONG).show();
                }
                break;
        }
    }

    //登录
    private void getdata_login(String uname, String pwd) {
        OkHttpUtils
                .post()
                .url(LOGIN_URL)
                .addParams("loginname", uname)
                .addParams("password", pwd)
                .addParams("companyid", Current_id)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onBefore(Request request) {
                        CommonUtils.showLoadingDialog(LoginActivity.this);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        L.d("登录response====" + e.getMessage());
                        CommonUtils.hideLoadingDialog(LoginActivity.this);
                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("登录response====" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String result = object.optString("result");
                                if ("true".equals(result)) {
                                    String c = object.optString("user");
                                    if (!TextUtils.isEmpty(c) && !c.equals("[]")) {
                                        user = JsonUtil.fromJson(c, User.class);
                                        SharedPreferencesUtils.setUID(LoginActivity.this, String
                                                .valueOf(user.getId()));
                                        SharedPreferencesUtils.setUserNickName(LoginActivity
                                                .this, user.getName());
                                        SharedPreferencesUtils.setUserInfor(LoginActivity.this,
                                                user);
                                        SharedPreferencesUtils.setLoadingStatement(LoginActivity
                                                .this, true);
                                        SharedPreferencesUtils.setCompanyId(LoginActivity.this,
                                                Current_id);
                                        CustomApplication.APP_ROLE_STATUS = user.getStatus();
                                        EventBus.getDefault().post(
                                                new FirstEvent( CustomApplication.APP_ROLE_STATUS));
                                        L.d("login---role_status===" + user.getStatus());
                                        startActivity(new Intent(LoginActivity.this, HomeActivity
                                                .class));
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, getText(R.string
                                            .ac_login_fail), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        CommonUtils.hideLoadingDialog(LoginActivity.this);
                    }
                });
    }


//    //获取公司列表
//    private  void getdata_company_list() {
//        OkHttpUtils
//                .get()
//                .url(COMPANY_LISTS)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        L.d("公司列表response====" + response);
//                        if (!TextUtils.isEmpty(response)) {
//                            try {
//                                JSONObject object = new JSONObject(response);
//                                String result = object.optString("result");
//                                if ("true".equals(result)) {
//                                    String c = object.optString("companys");
//                                    if (!TextUtils.isEmpty(c) && !c.equals("[]")) {
//
//
//                                        companys = JsonUtil.stringToArray(
//                                                object.optString("companys"),
//                                                Companys[].class);
//                                        data_list = new ArrayList<String>();
//                                        for (int i = 0; i < companys.size(); i++) {
//                                            data_list.add(i, String.valueOf(companys.get(i)
// .getName()));
//                                        }
//                                    }
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//    }


    @OnClick(R.id.ac_login_tv_spinner)
    public void onClick() {
        showPopupWindow(acLoginParent);
    }

    PopupWindow popupWindow;

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(LoginActivity.this).inflate(
                R.layout.spinner_listview, null);
        contentView.setFocusable(true); // 这个很重要
        contentView.setFocusableInTouchMode(true);
        // 设置按钮的点击事件
        ListView lv = (ListView) contentView.findViewById(R.id.spinner_listview);

        if (data_list.size() > 0) {

            spinnerAdapter = new SpinnerAdapter(this, data_list);
            lv.setAdapter(spinnerAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    acLoginTvSpinner.setText(data_list.get(position));
                    Current_id = String.valueOf(companys.get(position).getId());
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
            });
        }


        popupWindow = new PopupWindow(contentView,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams
                .WRAP_CONTENT, true);

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
        popupWindow.showAtLocation(acLoginParent, Gravity.CENTER, 0, 0);
//        popupWindow.showAsDropDown(view);

    }


    // 登录
    private void getfriend_Data(String uname, String pwd) {
//        http://192.168.1.102:8888/waiqin/client/manager/login?loginname=zhangsan&companyid=14
// &password=123456
        AjaxParams params = new AjaxParams();
        params.put("loginname", uname);
        params.put("password", pwd);
        params.put("companyid", "14");

        FinalHttp fh = new FinalHttp();
        String url = FinalHttp.getUrlWithQueryString(LOGIN_URL, params);
        L.d("===login===url===" + url);

        fh.post(LOGIN_URL, params, new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String t) {
                // 数据请求成功
                String result = t;
                L.d("===login===url===" + t);
//                CommonUtils.hideLoadingDialog(LinliQaunActivity.this);
            }

            @Override
            public void onStart() {
                // 开始http请求的时候回调
//                CommonUtils.showLoadingDialog(LinliQaunActivity.this, "加载中...");
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                L.d("===获取社区下话题===url===" + strMsg);
                // 加载失败的时候回调
//                CommonUtils.hideLoadingDialog(LinliQaunActivity.this);
            }
        });
    }


}
