package activity.lianqun.herry.com.workproject_lianqun.utils;

import android.util.Log;

import com.stay4it.framework.BuildConfig;

/**
 * @author Stay
 * @version create time：Sep 15, 2014 12:56:14 PM
 */
public class L {
    private static final String TAG = "stay4it";
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String msg) {
        if (DEBUG){
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG){
            Log.e(TAG, msg);
        }
    }
}
