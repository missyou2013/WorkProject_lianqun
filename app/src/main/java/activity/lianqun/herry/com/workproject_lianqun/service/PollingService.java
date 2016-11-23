package activity.lianqun.herry.com.workproject_lianqun.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/18.
 */

public class PollingService extends Service {

    public static final String ACTION = "com.service.PollingService";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PollingThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     *
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;

    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count++;
            //当计数能被5整除时弹出通知
//            if (count % 5 == 0) {
//                showNotification();
            System.out.println("New message!");
            Log.e("tag", "New message!--------------" + count);
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
