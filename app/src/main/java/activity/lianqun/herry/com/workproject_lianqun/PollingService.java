package activity.lianqun.herry.com.workproject_lianqun;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:test
 * Description: (用一句话描述该文件做什么)
 * Date: 2016-11-17 16:20
 * User: lxj
 * version V1.0.0
 */

public class PollingService extends Service {

    public static final String ACTION = "com.ryantang.service.PollingService";

//    private Notification mNotification;
//    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();

//        PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
//        if(true){
//            stopSelf();
//        }

    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count ++;
            //当计数能被5整除时弹出通知
//            if (count % 2 == 0) {
//                showNotification();
                System.out.println("New message!");
            Log.e("tag", "New message!--------------"+count);
//            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("tag", "Service:onDestroy--------------");
        System.out.println("Service:onDestroy");
    }
}
