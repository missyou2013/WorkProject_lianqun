package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.models.User;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.JsonUtil;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;
import activity.lianqun.herry.com.workproject_lianqun.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.activity
 * Description: 修改密码
 * Date: 2016-11-24 12:56
 * User: lxj
 * version V1.0.0
 */

public class ForgotPasswordActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ac_forgot_username_edit)
    EditText acForgotUsernameEdit;
    @BindView(R.id.ac_forgot_old_pwd_edit)
    EditText acForgotOldPwdEdit;
    @BindView(R.id.ac_forgot_new_pwd_edit)
    EditText acForgotNewPwdEdit;
    @BindView(R.id.ac_forgot_btn_confirm)
    Button acForgotBtnConfirm;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_forgot_pwd, R.string.ac_login_forgot_pwd_txt);
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
    }

    @OnClick(R.id.ac_forgot_btn_confirm)
    public void onClick() {
        String old_pwd = acForgotOldPwdEdit.getText().toString().trim();
        String new_pwd = acForgotNewPwdEdit.getText().toString().trim();
        String new_pwd_again = acForgotUsernameEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(new_pwd_again) && !TextUtils.isEmpty(old_pwd) && !TextUtils.isEmpty(new_pwd) && new_pwd_again.equals(new_pwd)) {
            if (CommonUtils.isOnline(this)) {
                getdata_xiugai_mima(SharedPreferencesUtils.getUID(this), old_pwd, new_pwd_again);
            } else {
                Toast.makeText(this, getText(R.string.ac_forgot_error_promit_txt), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getText(R.string.ac_forgot_error_promit_txt), Toast.LENGTH_LONG).show();
        }
    }

    //修改密码 userid,oldpassword,newpassword
    private void getdata_xiugai_mima(String userid, String oldpwd, String newpwd) {
        OkHttpUtils
                .post()
                .url(XIUGAI_MIMA)
                .addParams("userid", userid)
                .addParams("oldpassword", oldpwd)
                .addParams("newpassword", newpwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
                        CommonUtils.showLoadingDialog(ForgotPasswordActivity.this);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        L.d("修改密码response====" + e.getMessage());
                        CommonUtils.hideLoadingDialog(ForgotPasswordActivity.this);
                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("修改密码response====" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String result = object.optString("result");
                                if ("true".equals(result)) {
                                    Toast.makeText(ForgotPasswordActivity.this, getText(R.string.ac_again_login), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, getText(R.string.ac_forgot_fail), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        CommonUtils.hideLoadingDialog(ForgotPasswordActivity.this);
                    }
                });
    }
}
