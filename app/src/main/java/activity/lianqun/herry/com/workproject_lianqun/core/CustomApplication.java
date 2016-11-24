package activity.lianqun.herry.com.workproject_lianqun.core;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.Trace;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.models.Companys;
import activity.lianqun.herry.com.workproject_lianqun.utils.ApiConfig;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.JsonUtil;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;


/**
 * Created by Administrator on 2016/11/16.
 */

public class CustomApplication extends Application {
    private Context mContext = null;

    /**
     * 轨迹服务
     */
    private Trace trace = null;

    /**
     * 轨迹服务客户端
     */
    private LBSTraceClient client = null;

    /**
     * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
     */
    private int serviceId = 129394;

    /**
     * entity标识
     */
    private String entityName = "myTrace";

    /**
     * 轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）
     */
    private int traceType = 2;

    private MapView bmapView = null;

    private BaiduMap mBaiduMap = null;

    private TrackHandler mHandler = null;

    public static List<Companys> companys = new ArrayList<Companys>();
    public  static List<String> data_list;

    @Override
    public void onCreate() {
        super.onCreate();
        AppStatusTracker.init(this);

        mContext = getApplicationContext();


        /**
         * 鹰眼
         */
        SDKInitializer.initialize(this);
        // 初始化轨迹服务客户端
        client = new LBSTraceClient(mContext);
        // 初始化轨迹服务
        trace = new Trace(mContext, serviceId, entityName, traceType);
        // 设置定位模式
        client.setLocationMode(LocationMode.High_Accuracy);
        mHandler = new TrackHandler(this);


        //OkHttp配置
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        client.setConnectTimeout(10000L, TimeUnit.MILLISECONDS);
        client.setReadTimeout(10000L, TimeUnit.MILLISECONDS);


        if (CommonUtils.isOnline(this)) {
            getdata_company_list();
        } else {
            Toast.makeText(this, getText(R.string.ac_login_error_net_txt), Toast.LENGTH_LONG).show();
        }

    }

    public void initBmap(MapView bmapView) {
        this.bmapView = bmapView;
        this.mBaiduMap = bmapView.getMap();
        this.bmapView.showZoomControls(false);
    }

    static class TrackHandler extends Handler {
        WeakReference<CustomApplication> trackApp;

        TrackHandler(CustomApplication CustomApplication) {
            trackApp = new WeakReference<CustomApplication>(CustomApplication);
        }

        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(trackApp.get().mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
        }
    }


    public Context getmContext() {
        return mContext;
    }

    public Trace getTrace() {
        return trace;
    }

    public LBSTraceClient getClient() {
        return client;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getEntityName() {
        return entityName;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public MapView getBmapView() {
        return bmapView;
    }

    public BaiduMap getmBaiduMap() {
        return mBaiduMap;
    }

    //获取公司列表
    private  void getdata_company_list() {
        OkHttpUtils
                .get()
                .url(ApiConfig.COMPANY_LISTS)
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
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
