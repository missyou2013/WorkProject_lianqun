package activity.lianqun.herry.com.workproject_lianqun.adpter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2016/11/23.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);

    }
}
