package activity.lianqun.herry.com.workproject_lianqun.isee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * @Title: AnBaoAdapter.java
 * @Package com.herry.shequ.adapter
 * @Description: 智能安保Gridview
 * @author wang
 * @date 2016-1-4 下午1:55:22
 * @version V1.0
 */import activity.lianqun.herry.com.workproject_lianqun.R;

/**
 * @Title: AnBaoAdapterSheXiangTou.java
 * @Package com.herry.shequ.adapter
 * @Description: 摄像头设备列表
 * @author lxj
 * @date 2016-4-18 上午9:54:42
 * @version V1.0
 */
public class AnBaoAdapterSheXiangTou extends BaseAdapter {

	private String data[] = null;
	private int imgId[] = null;
	private Context context = null;
	private LayoutInflater inflater = null;
	private List<Map<String, Object>> mListData;

	public AnBaoAdapterSheXiangTou(Context context, List<Map<String, Object>> mListData) {
		super();
		this.mListData = mListData;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int l = mListData.size();
		if (l > 0) {
			return mListData.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private class Holder {

		TextView tv = null;
		ImageView img = null;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 获得holder以及holder对象中tv和img对象的实例
		Holder holder;
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.item_zhinenganbao_gridview, null);
			holder = new Holder();
			holder.img = (ImageView) convertView.findViewById(R.id.zhinenganbao_img);
			holder.tv = (TextView) convertView.findViewById(R.id.zhinenganbao_text);

			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();

		}
		// 为holder中的tv和img设置内容
		holder.tv.setText((String)mListData.get(position).get("name"));
		holder.img.setImageResource(R.mipmap.ic_launcher);
		// 注意 默认为返回null,必须得返回convertView视图
		return convertView;
	}

}
