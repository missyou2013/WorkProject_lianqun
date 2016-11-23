package activity.lianqun.herry.com.workproject_lianqun.utils;


import com.baidu.trace.T;
import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.utils
 * Description: (用一句话描述该文件做什么)
 * Date: 2016-11-23 14:28
 * User: lxj
 * version V1.0.0
 */

public abstract class ResultModelCallback extends Callback<T> {
    @Override
    public T parseNetworkResponse(Response response) throws IOException {
        String string = response.body().string();
        L.d("response====" + response);
        T t = new Gson().fromJson(string, T.class);
        return t;
    }


}
