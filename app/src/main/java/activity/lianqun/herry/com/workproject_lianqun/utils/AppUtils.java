package activity.lianqun.herry.com.workproject_lianqun.utils;

import android.content.Context;

/**
 * Created by Administrator on 2016/11/22.
 */

public class AppUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
