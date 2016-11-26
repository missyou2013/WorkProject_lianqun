package activity.lianqun.herry.com.workproject_lianqun.isee2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication;

/**
 * �������
 * 
 * @author jun.yang
 * 
 */
public class HMApiDemoMain extends Activity {
	ListView mListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView text = (TextView) findViewById(R.id.text_Info);
		text.setTextColor(Color.BLUE);
		text.setText("�ɶ�����ͨ�ż������޹�˾-SDK-DEMO.");

		mListView = (ListView) findViewById(R.id.listView);
		mListView.setAdapter(new DemoListAdapter());
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int index,
									long arg3) {
				onListItemClick(index);
			}
		});

		CustomApplication.getJni().init();
	}

	private void onListItemClick(int index) {
		Intent intent = null;
		intent = new Intent(HMApiDemoMain.this, demos[index].demoClass);
		this.startActivity(intent);
	}

	private static final DemoInfo[] demos = {

	new DemoInfo(R.string.demo_title_login, R.string.demo_login_getDeviceList,
			LoginDemo.class) };

	@Override
	protected void onDestroy() {
		super.onDestroy();

		CustomApplication.getJni().uninit();
		System.exit(0);
	}

	private class DemoListAdapter extends BaseAdapter {
		public DemoListAdapter() {
			super();
		}

		public int getCount() {
			return demos.length;
		}

		public Object getItem(int index) {
			return demos[index];
		}

		public long getItemId(int id) {
			return id;
		}

		public View getView(int index, View convertView, ViewGroup parent) {
			convertView = View.inflate(HMApiDemoMain.this,
					R.layout.demo_info_item, null);
			TextView title = (TextView) convertView.findViewById(R.id.title);
			TextView desc = (TextView) convertView.findViewById(R.id.desc);

			title.setText(demos[index].title);
			desc.setText(demos[index].desc);
			return convertView;
		}
	}

	private static class DemoInfo {
		private final int title;
		private final int desc;
		private final Class<? extends Activity> demoClass;

		public DemoInfo(int title, int desc,
				Class<? extends Activity> demoClass) {
			this.title = title;
			this.desc = desc;
			this.demoClass = demoClass;
		}
	}
}
