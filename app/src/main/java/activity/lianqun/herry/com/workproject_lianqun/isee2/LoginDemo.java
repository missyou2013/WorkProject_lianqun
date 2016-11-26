package activity.lianqun.herry.com.workproject_lianqun.isee2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.huamaitel.api.HMDefines;
import com.huamaitel.api.HMDefines.UserInfo;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication;

/**
 * ��¼ƽ̨����ȡ�豸�б�
 * 
 * @author admin
 * 
 */
public class LoginDemo extends Activity implements OnClickListener {
	private static final String TAG = "LoginDemo";
	private static final int EVENT_CONNECT_SUCCESS = 1;
	private static final int EVENT_CONNECT_FAIL = 2;
	private static final int EVENT_LOGIN_SUCCESS = 3;
	private static final int EVENT_LOGIN_FAIL = 4;

	// TODO:�����Լ���ƽ̨��������
	// private static final String SERVER_ADDR = "home.see1000.com";
//	private static final String SERVER_ADDR ="qdyidu.com";//"rvs.dev.huamaitel.com"; //"www.seeyun.cn";
	
	private static final String SERVER_ADDR ="isee.qdyibu.com";
	
	// private static final String SERVER_ADDR = "www.seebaobei.com";
	// private static final String SERVER_ADDR = "yn118960.com";

	private static final short SERVER_PORT = 80;
	private ProgressDialog loginProcessDialog;
	private EditText etUsername;
	private EditText etPassword;
	private Handler handler;
	private Thread mServerThread = null;
	private HMDefines.LoginServerInfo info = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		findViewById(R.id.id_btn_login).setOnClickListener(this);
		etUsername = (EditText) findViewById(R.id.id_edt_username);
		etPassword = (EditText) findViewById(R.id.id_edt_password);

		// ���뱣��
		SharedPreferences set = getSharedPreferences("userinfo", MODE_PRIVATE);
		etUsername.setText(set.getString("name", ""));
		etPassword.setText(set.getString("pwd", ""));

		registerHander();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.id_btn_login) {
			showWaitDialog("logining...");

			final String username = etUsername.getText().toString().trim();
			final String password = etPassword.getText().toString().trim();

			SharedPreferences.Editor editor = getSharedPreferences("userinfo", MODE_PRIVATE).edit();
			editor.putString("name", username);
			editor.putString("pwd", password);
			editor.commit();

			mServerThread = new Thread() {
				@Override
				public void run() {
					// ƽ̨���
					info = new HMDefines.LoginServerInfo();
					info.ip = SERVER_ADDR; // ƽ̨��ַ
					info.port = SERVER_PORT; // ƽ̨�˿�
					info.user = username; // �û���
					info.password = password; // ����
					info.model = android.os.Build.MODEL; // �ֻ��ͺ�
					info.version = android.os.Build.VERSION.RELEASE; // �ֻ�ϵͳ�汾��

					String error = jni_connectServer();
					if (error != null) {
						Log.e(TAG, "Connect server fail.");
						sendEmptyMessage(EVENT_CONNECT_FAIL, error);
					} else {
						Log.i(TAG, "Connect server success.");
						sendEmptyMessage(EVENT_CONNECT_SUCCESS);
					}
					
					
					
				}
			};

			mServerThread.start();
		}
	}

	private void showWaitDialog(String str) {
		loginProcessDialog = ProgressDialog.show(this, null, str);
		loginProcessDialog.setCancelable(true);
	}

	private void registerHander() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				closeWaitDoalog();
				switch (msg.what) {
				case EVENT_LOGIN_SUCCESS:
					Intent intent = new Intent();
					intent.setClass(LoginDemo.this, DeviceActivity.class);
					startActivity(intent);
					LoginDemo.this.finish();
					Log.i(TAG, "login success");
					break;
				case EVENT_LOGIN_FAIL:
					CustomApplication.getJni().disconnectServer(CustomApplication.serverId);
					Toast.makeText(LoginDemo.this, "login fail", Toast.LENGTH_SHORT).show();
					break;

				case EVENT_CONNECT_SUCCESS:
					int result = CustomApplication.getJni().getDeviceList(CustomApplication.serverId);
					if (result != HMDefines.HMEC_OK) {
						sendEmptyMessage(EVENT_LOGIN_FAIL);
						return;
					}
					// step 2: Get user information.
					UserInfo userInfo = CustomApplication.getJni().getUserInfo(CustomApplication.serverId);
					if (userInfo == null) {
						sendEmptyMessage(EVENT_LOGIN_FAIL);
						return;
					}
					/**
					 * TODO:�ж�ѡ�� huamaiyun��see1000����Ҫ���userInfo.useTransferService !=8
					 * 
					 * ����ж� seebao����Ҫȥ�����������ױ���
					 */
					// step 3: Get transfer service.
					// if (userInfo.useTransferService != 0&&userInfo.useTransferService !=8) {
					if (userInfo.useTransferService != 0) {
						result = CustomApplication.getJni().getTransferInfo(CustomApplication.serverId);
						if (result != HMDefines.HMEC_OK) {
							sendEmptyMessage(EVENT_LOGIN_FAIL);
							return;
						}
					}

					// step 4: Get tree id.
					CustomApplication.treeId = CustomApplication.getJni().getTree(CustomApplication.serverId);
					sendEmptyMessage(EVENT_LOGIN_SUCCESS);
					break;

				case EVENT_CONNECT_FAIL:
					//��ʾ��¼ʧ��ԭ��
					Toast.makeText(LoginDemo.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					break;

				}
			}
		};
	}

	private void sendEmptyMessage(int msgId) {
		sendEmptyMessage(msgId, null);
	}

	private void sendEmptyMessage(int msgId, String error) {
		if (handler == null) {
			return;
		}
		Message msg = new Message();
		msg.what = msgId;
		msg.obj = error;
		handler.sendMessage(msg);
	}

	private void closeWaitDoalog() {
		if (loginProcessDialog != null) {
			if (loginProcessDialog.isShowing()) {
				loginProcessDialog.dismiss();
				loginProcessDialog = null;
			}
		}
	}

	public String jni_connectServer() {
		StringBuilder error = new StringBuilder();
		CustomApplication.serverId = CustomApplication.getJni().connectServer(info, error);
		if (CustomApplication.serverId == -1) {
			return error.toString();
		}
		return null;
	}
}
