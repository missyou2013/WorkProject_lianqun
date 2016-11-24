package activity.lianqun.herry.com.workproject_lianqun.utils;

import com.baidu.trace.T;
import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;


/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.utils
 * Description: (用一句话描述该文件做什么)
 * Date: 2016-11-23 14:28
 * User: lxj
 * version V1.0.0
 */

public abstract class ResultListCallback extends Callback<List<T>> {


    @Override
    public List<T> parseNetworkResponse(Response response) throws IOException {
        String string = response.body().string();
        List<T> list = new Gson().fromJson(string, List.class);
        return list;
    }


}
