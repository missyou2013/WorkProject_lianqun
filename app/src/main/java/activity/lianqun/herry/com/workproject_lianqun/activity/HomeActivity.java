package activity.lianqun.herry.com.workproject_lianqun.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huamaitel.api.HMDefines;
import com.squareup.okhttp.Request;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.GlideImageLoader;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication;
import activity.lianqun.herry.com.workproject_lianqun.isee.DeviceActivity;
import activity.lianqun.herry.com.workproject_lianqun.isee.LoginDemo;
import activity.lianqun.herry.com.workproject_lianqun.models.ContentSheXiangtou;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.JsonUtil;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;
import activity.lianqun.herry.com.workproject_lianqun.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {
    @BindView(R.id.relativeLayout_kaoQin)
    RelativeLayout relativeLayoutKaoQin;
    @BindView(R.id.relativeLayout_shenQing)
    RelativeLayout relativeLayoutShenQing;
    @BindView(R.id.relativeLayout_trace)
    RelativeLayout relativeLayoutTrace;
    @BindView(R.id.relativeLayout_ad)
    RelativeLayout relativeLayoutAd;
    @BindView(R.id.relativeLayout_my)
    RelativeLayout relativeLayoutMy;

    @BindView(R.id.imageView5)
    ImageView iv_5;
    @BindView(R.id.ac_home_my_5)
    TextView tv_1;
    @BindView(R.id.ac_home_my_5_2)
    TextView tv_2;
    private Banner banner;
    private List<String> list_images = new ArrayList<>();
    int role_status;


    //~~~~~~~~~~~~~~~
    List<ContentSheXiangtou> contentSheXiangtouList = new ArrayList<ContentSheXiangtou>();
    private ArrayList<String> list_devices = new ArrayList<String>();
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~
    private static final String TAG = "tag";
    private static final int EVENT_CONNECT_SUCCESS = 1;
    private static final int EVENT_CONNECT_FAIL = 2;
    private static final int EVENT_LOGIN_SUCCESS = 3;
    private static final int EVENT_LOGIN_FAIL = 4;

    // private static final String SERVER_ADDR = "home.see1000.com";
    // private static final String SERVER_ADDR
    // ="qdyidu.com";//"rvs.dev.huamaitel.com"; //"www.seeyun.cn";

    private static final String SERVER_ADDR = "isee.qdyibu.com";

    // private static final String SERVER_ADDR = "www.seebaobei.com";
    // private static final String SERVER_ADDR = "yn118960.com";

    private static final short SERVER_PORT = 80;
    private ProgressDialog loginProcessDialog;
    private Handler handler;
    private Thread mServerThread = null;
    private HMDefines.LoginServerInfo info = null;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.ac_home, R.string.activity_home, MODE_HOME);
        ButterKnife.bind(HomeActivity.this);

    }


    @Override
    protected void setUpView() {

        list_images.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        list_images.add("http://pic3.bbzhi" +
                ".com/fengjingbizhi/gaoqingkuanpingfengguangsheyingps/show_fengjingta_281299_11" +
                ".jpg");
        banner = (Banner) findViewById(R.id.home_banner);
        banner.setImages(list_images).setImageLoader(new GlideImageLoader()).start();

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

        role_status = SharedPreferencesUtils.getUserInfor(this).getStatus();
        if (role_status == 1) {
            //1  is  Adminstartor
            tv_1.setText("摄像头");
            tv_2.setText("实时监控");
        } else {
            //2  is  user
            tv_1.setText("我的");
            tv_2.setText("个人信息");
        }
        CustomApplication.getJni().init();

        getdata_shexiangtou("1", "100");

    }


    @OnClick({R.id.relativeLayout_kaoQin, R.id.relativeLayout_shenQing, R.id
            .relativeLayout_trace, R.id.relativeLayout_ad, R.id.relativeLayout_my})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.relativeLayout_kaoQin:
                intent = new Intent();
                intent.setClass(HomeActivity.this, KaoQinActivity.class);
                break;
            case R.id.relativeLayout_shenQing:
                break;
            case R.id.relativeLayout_trace:
                break;
            case R.id.relativeLayout_ad:
                intent = new Intent();
                intent.setClass(HomeActivity.this, HomeActivity.class);
                break;
            case R.id.relativeLayout_my:
                if (role_status == 1) {
                    //1  is  Adminstartor
//                    intent.setClass(HomeActivity.this, LoginDemo.class);
                    registerHander();
                    shipin();
                } else {
                    //2  is  user
                    intent = new Intent();
                    intent.setClass(HomeActivity.this, MyActivity.class);
                }
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }
    }

    //摄像头列表 传cid  pagenum  pagesize
    private void getdata_shexiangtou(String pagenum, String pagesize) {
        OkHttpUtils
                .post()
                .url(SHEXIANGTOU)
                .addParams("cid", SharedPreferencesUtils.getCompanyId(HomeActivity.this))
                .addParams("pagenum", pagenum)
                .addParams("pagesize", pagesize)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
                        CommonUtils.showLoadingDialog(HomeActivity.this);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        L.d("摄像头列表response====" + e.getMessage());
                        CommonUtils.hideLoadingDialog(HomeActivity.this);
                    }

                    @Override
                    public void onResponse(String response) {
                        L.d("摄像头列表response====" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject object = new JSONObject(response);
//                                String result = object.optString("result");
//                                if ("true".equals(result)) {

                                String c = object.optString("content");
                                if (!TextUtils.isEmpty(c) && !c.equals("[]")) {
                                    contentSheXiangtouList = JsonUtil.stringToArray(
                                            object.optString("content"),
                                            ContentSheXiangtou[].class);
                                    if (contentSheXiangtouList.size() > 0) {

                                        for (int i = 0; i < contentSheXiangtouList.size(); i++) {
                                            list_devices.add(contentSheXiangtouList.get(i)
                                                    .getSn());
                                        }

                                    }
                                }

//                                    Toast.makeText(HomeActivity.this, getText(R.string
// .ac_again_login), Toast.LENGTH_LONG).show();
//                                    startActivity(new Intent(ForgotPasswordActivity.this,
// LoginActivity.class));
//                                    finish();
//                                } else {
//                                    Toast.makeText(HomeActivity.this, getText(R.string
//                                            .ac_forgot_fail), Toast.LENGTH_LONG).show();
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        CommonUtils.hideLoadingDialog(HomeActivity.this);
                    }
                });
    }

    private void registerHander() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                closeWaitDoalog();
                switch (msg.what) {
                    case EVENT_LOGIN_SUCCESS:
                        // Intent intent = new Intent();
                        // intent.setClass(LoginDemo.this, DeviceActivity.class);
                        // startActivity(intent);
                        // LoginDemo.this.finish();

                        Intent intent = new Intent(HomeActivity.this,
                                DeviceActivity.class);
                        intent.putStringArrayListExtra("list_device",
                                list_devices);
                        startActivity(intent);

                        Log.d(TAG, "login success");
                        break;

                    case EVENT_LOGIN_FAIL:
                        CustomApplication.getJni().disconnectServer(
                                CustomApplication.serverId);
                        Toast.makeText(getApplicationContext(), "login fail",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case EVENT_CONNECT_SUCCESS:
                        int result = CustomApplication.getJni().getDeviceList(
                                CustomApplication.serverId);
                        if (result != HMDefines.HMEC_OK) {
                            sendEmptyMessage(EVENT_LOGIN_FAIL);
                            return;
                        }
                        // step 2: Get user information.
                        HMDefines.UserInfo userInfo = CustomApplication.getJni().getUserInfo(
                                CustomApplication.serverId);
                        if (userInfo == null) {
                            sendEmptyMessage(EVENT_LOGIN_FAIL);
                            return;
                        }

                        if (userInfo.useTransferService != 0) {
                            result = CustomApplication.getJni().getTransferInfo(
                                    CustomApplication.serverId);
                            if (result != HMDefines.HMEC_OK) {
                                sendEmptyMessage(EVENT_LOGIN_FAIL);
                                return;
                            }
                        }

                        // step 4: Get tree id.
                        CustomApplication.treeId = CustomApplication.getJni().getTree(
                                CustomApplication.serverId);
                        sendEmptyMessage(EVENT_LOGIN_SUCCESS);
                        break;

                    case EVENT_CONNECT_FAIL:
                        Toast.makeText(getApplicationContext(), msg.obj.toString(),
                                Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
    }

    public static String SHIPIN_USERNAME = "cuihuxinyuan";
    public static String SHIPIN_PASSWORD = "123456";

    private void shipin() {
        mServerThread = new Thread() {
            @Override
            public void run() {
                // ƽ̨���
                info = new HMDefines.LoginServerInfo();
                info.ip = SERVER_ADDR; // ƽ̨��ַ
                info.port = SERVER_PORT; // ƽ̨�˿�
                info.user = SHIPIN_USERNAME; // �û���
                info.password = SHIPIN_PASSWORD; // ����
                info.model = android.os.Build.MODEL; // �ֻ��ͺ�
                info.version = android.os.Build.VERSION.RELEASE; // �ֻ�ϵͳ�汾��

                String error = jni_connectServer();
                if (error != null) {
                    Log.d(TAG, "Connect server fail.");
                    sendEmptyMessage(EVENT_CONNECT_FAIL, error);
                } else {
                    Log.d(TAG, "Connect server success.");
                    sendEmptyMessage(EVENT_CONNECT_SUCCESS);
                }

            }
        };

        mServerThread.start();
    }

    private void sendEmptyMessage(int msgId) {
        sendEmptyMessage(msgId, null);
    }

    private void sendEmptyMessage(int msgId, String error) {
        if (handler == null) {
            return;
        }
        Message msg = new Message();
        msg.what = msgId;
        msg.obj = error;
        handler.sendMessage(msg);
    }

    private void closeWaitDoalog() {
        if (loginProcessDialog != null) {
            if (loginProcessDialog.isShowing()) {
                loginProcessDialog.dismiss();
                loginProcessDialog = null;
            }
        }
    }

    public String jni_connectServer() {
        StringBuilder error = new StringBuilder();
        CustomApplication.serverId = CustomApplication.getJni().connectServer(info,
                error);
        if (CustomApplication.serverId == -1) {
            return error.toString();
        }
        return null;
    }

    // 双击返回退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setAction("shouye");
            sendBroadcast(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomApplication.getJni().uninit();
        System.exit(0);
    }
}
