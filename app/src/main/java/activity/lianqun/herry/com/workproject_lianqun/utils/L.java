package activity.lianqun.herry.com.workproject_lianqun.utils;

import android.util.Log;

import activity.lianqun.herry.com.workproject_lianqun.BuildConfig;


/**
 * Created by Administrator on 2016/11/16.
 */
public class L {
    private static final String TAG = "stay4it";
    public static boolean DEBUG = true;

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }
}
