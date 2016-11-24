package activity.lianqun.herry.com.workproject_lianqun.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;

/**
 * @Title: AnBaoAdapter.java
 * @Package com.herry.shequ.adapter
 * @Description: 智能安保Gridview
 * @author wang
 * @date 2016-1-4 下午1:55:22
 * @version V1.0
 */

public class SpinnerAdapter extends BaseAdapter {


    private Context context = null;
    private LayoutInflater inflater = null;
    private List<String> list;

    public SpinnerAdapter(Context context, List<String> list) {
        super();
        this.list = list;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int l = 0;
        if (list.size() > 0) {
            l = list.size();
        }
        return l;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class Holder {

        TextView tv = null;
//        ImageView img = null;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 获得holder以及holder对象中tv和img对象的实例
        Holder holder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.spinner_item,
                    null);
            holder = new Holder();
//            holder.img = (ImageView) convertView
//                    .findViewById(R.id.zhinenganbao_img);
            holder.tv = (TextView) convertView
                    .findViewById(R.id.spinner_item_tv);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();

        }
   if(!TextUtils.isEmpty(list.get(position))){

       holder.tv.setText(list.get(position));
   }
//        holder.img.setImageResource(imgId[position]);
        // 注意 默认为返回null,必须得返回convertView视图
        return convertView;
    }

}
