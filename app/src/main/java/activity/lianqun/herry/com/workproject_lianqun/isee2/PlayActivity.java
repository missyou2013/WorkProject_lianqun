package activity.lianqun.herry.com.workproject_lianqun.isee2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huamaitel.api.HMCallback;
import com.huamaitel.api.HMDefines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication;

/**
 * ��Ƶ������
 * 
 * @author admin
 * 
 */
public class PlayActivity extends Activity {
	private static final String TAG = "PlayActivity";

	// Records/Pictures/Logs related.
	public static final String FOLDER_NAME_RECORD = "records";
	public static final String FOLDER_NAME_CAPTURE = "pictures";
	public static final String FOLDER_NAME_LOG = "log";
	public static final int FILE_TYPE_RECORD = 1;
	public static final int FILE_TYPE_CAPTURE = 2;
	public static final int FILE_TYPE_LOG = 3;

	// ¼�����
	public static final int MSG_SHOW_RECORD_TIME = 11;
	public static final int MSG_STOP_RECORD = 12;
	private Button mbtn_record = null;
	private Button mbtn_capture = null;
	private Button mbtn_arming = null;
	private Button mbtn_disarming = null;
	private Button mbtn_opentalk = null;
	private Button mbtn_closetalk = null;
	private Button mbtn_openlisten = null;
	private Button mbtn_closelisten = null;
	private ImageView mivrecordDot = null;
	private TextView mtvrecordTime = null;

	private boolean mIfLogin = false; // If login...
	private boolean mIsPlaying = false; // Is playing video...
	private boolean mIsListening = false; // Is listening...
	private boolean mIsTalking = false; // Is talking...
	private boolean mIsRecording = false; // Is Recording...
	private boolean mIfExit = false; // If exit the activity...

	private int mNodeId = 0; // The node id.
	private int mChannelIndex = 0; // The channel index.
	private int mVideoStream = HMDefines.CodeStream.MAIN_STREAM; // The video
																	// stream
																	// (Main/sub
																	// video
																	// stream.)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_activity);
		// ����Ļ���ֲ������ر�
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initView();


		// Get the node id from the DeviceActivity .
		Intent intent = getIntent();
		mNodeId = intent.getIntExtra(CustomApplication.NODE_ID, 0);
		mChannelIndex = intent.getIntExtra(CustomApplication.CHANNEL, 0);
		mVideoStream = intent.getIntExtra(CustomApplication.VIDEO_STREAM, HMDefines.CodeStream.SEC_STREAM);

		// ��¼ & ����Ƶ
		openVideo();

	}

	private void initView() {
		mbtn_record = (Button) findViewById(R.id.id_btn_record);
		mbtn_capture = (Button) findViewById(R.id.id_btn_capture);
		mbtn_arming = (Button) findViewById(R.id.id_btn_arming);
		mbtn_disarming = (Button) findViewById(R.id.id_btn_disarming);
		mbtn_opentalk = (Button) findViewById(R.id.id_btn_opentalk);
		mbtn_closetalk = (Button) findViewById(R.id.id_btn_closetalk);
		mbtn_openlisten = (Button) findViewById(R.id.id_btn_openlisten);
		mbtn_closelisten = (Button) findViewById(R.id.id_btn_closelisten);
		mtvrecordTime = (TextView) findViewById(R.id.record_time);
		mivrecordDot = (ImageView) findViewById(R.id.record_dot);
		mivrecordDot.setBackgroundResource(R.drawable.record_anim);
		mivrecordDot.setImageDrawable(null);

	}

	 
	 

	private void openVideo() {
		new Thread() {
			@Override
			public void run() {
				super.run();

				if (CustomApplication.mIsUserLogin) {
					// �ӻ�������¼
					if (mNodeId == 0) {
						return;
					}
					// Step 1: Login the device.

					CustomApplication.mUserId = CustomApplication.getJni().loginEx(mNodeId, HMDefines.ConnectPolicy.CONN_POLICY_DEFAULT);
					if (CustomApplication.mUserId == -1) {
						return;
					}
				} else {
					/**
					 * �Ӿ�������¼ TODO:�쳣���� �Ӿ�������¼�������豸�� ����ʱ��Ҫע���ֻ�������Ϊͬһ�������� ����豸���������ã�������Ӧ���˺����롣Ĭ���˺�Ϊ��guest,���벻� �˴������������ȷ�ԣ�������û���������Ϣ��ȷ�Լ�顣
					 */
					String sIp = getIntent().getStringExtra("ip");
					String sPort = getIntent().getStringExtra("port");
					String sUser = getIntent().getStringExtra("user");
					String sPass = getIntent().getStringExtra("pass");
					String sSn = getIntent().getStringExtra("sn");
					if (sIp != null && sPort != null && sSn != null && sUser != null) {
						// Step 1: Login the device.
						CustomApplication.mUserId = CustomApplication.getJni().login(sIp, Short.parseShort(sPort), sSn, sUser, sPass);
						if (CustomApplication.mUserId == -1) {
							return;
						}
					}
				}

				mIfLogin = true;

				/**
				 * Step 2: Get device information. TODO:�쳣���� ���豸�����ߣ�ִ����һ�����׷���Ϊ�ա�����ʱ����һ���豸�Ƿ����ߣ��� �ɲο�SDK�ĵ� ��� Online����ӿڽ����жϡ�
				 */
				CustomApplication.mDeviceInfo = CustomApplication.getJni().getDeviceInfo(CustomApplication.mUserId);
				CustomApplication.mChannelCapacity = CustomApplication.getJni().getChannelCapacity(CustomApplication.mUserId);
				if (CustomApplication.mDeviceInfo == null) {
					return;
				}
				// Step 3: Start video
				HMDefines.OpenVideoParam param = new HMDefines.OpenVideoParam();
				param.channel = mChannelIndex;
				param.codeStream = HMDefines.CodeStream.SEC_STREAM;
				param.videoType = HMDefines.VideoStream.REAL_STREAM;
				HMDefines.OpenVideoRes res = new HMDefines.OpenVideoRes();

				CustomApplication.mVideoHandle = CustomApplication.getJni().startVideo(CustomApplication.mUserId, param, res);

				if (CustomApplication.mVideoHandle == -1) {
					mIsPlaying = false;
					return;
				}
				/**
				 * TODO���쳣���� startVideo�ɹ��� ��ִ�� setNetworkCallback �ص���������������ݡ� Ϊ��֤�豸��Ϣ��ȡ����������ִ��һ��getDeviceInfo������ ������˽��������ڴ�ʱ�����ͷš�
				 * 
				 */
				mIsPlaying = true;
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("ȷ��Ҫ�˳���Ƶ������").setIcon(android.R.drawable.ic_dialog_info).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Exit the play activity for exiting.
				if (!mIfExit) {
					exitPlayActivity();
				}
				PlayActivity.this.finish();
			}
		}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing����
			}
		}).show();
	}

	private void exitPlayActivity() {
		// Avoid calling this function many times.
		mIfExit = true;

		// Close some resources.
		new Thread() {
			public void run() {
				releaseResources();
			}

		}.start();

		// Back to the device list activity.
		backtoDeviceList();
	}

	private void backtoDeviceList() {
		PlayActivity.this.finish();
	}

	private void releaseResources() {
		if (mIsPlaying) {
			int result = CustomApplication.getJni().stopVideo(CustomApplication.mVideoHandle);
			if (result != 0) {
				Log.e(TAG, "stopvideo fail.");
			} else {
				Log.i(TAG, "stopvideo success.");
			}
			mIsPlaying = false;
		}

		if (mIsRecording) {
			int result = CustomApplication.getJni().stopLocalRecord(CustomApplication.mRecordHandle);
			if (result != 0) {
				Log.e(TAG, "stopLocalRecord fail.");
			} else {
				Log.i(TAG, "stopLocalRecord success.");
			}
			mIsRecording = false;
		}

		if (mIsTalking) {

			int result = CustomApplication.getJni().stopTalk(CustomApplication.mTalkHandle);
			if (result != 0) {
				Log.e(TAG, "stopTalk fail.");
			} else {
				Log.i(TAG, "stopTalk success.");
			}
			mIsTalking = false;
		}
		if (mIsListening) {
			int result = CustomApplication.getJni().stopAudio(CustomApplication.mAudioHandle);
			if (result != 0) {
				Log.e(TAG, "stopAudio fail.");
			} else {
				Log.i(TAG, "stopAudio success.");
			}

			mIsListening = false;
		}

		if (mIfLogin) {
			int result = CustomApplication.getJni().logout(CustomApplication.mUserId);
			if (result != 0) {
				Log.e(TAG, "logout fail.");
			} else {
				Log.i(TAG, "logout success.");
			}
			mIfExit = false;
		}

	}

	/**
	 * ��ʾ¼��ʱ��
	 */
	public Handler mUIHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SHOW_RECORD_TIME:
				int time = CustomApplication.getJni().getLocalRecordTime(CustomApplication.mRecordHandle);
				String timString = Duration2Time(time);
				mtvrecordTime.setText(timString);
				break;

			case MSG_STOP_RECORD:
				int result = CustomApplication.getJni().stopLocalRecord(CustomApplication.mUserId);
				if (result != 0) {
					Log.e(TAG, "close local record fail.");
				} else {
					Log.i(TAG, "close local record success.");
				}

				mIsRecording = false;
				showRecordAnim(false);
				break;
			}
		}

	};

	/************************************************* ���߷��� *******************************************************/
	/*
	 * ��byte�����ݱ���ΪΪJpg��Png��ͼƬ��ʽ��
	 */
	public static boolean saveCapturedPic(byte data[], String path) {
		Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
		FileOutputStream out = null;

		if (null == bmp) {
			Log.e(TAG, "bitmap is null.");
			return false;
		}

		try {
			out = new FileOutputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}

		bmp.compress(CompressFormat.PNG, 80, out);

		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * ʱ���ʽת��
	 * 
	 * @param duration
	 * @return
	 */
	public static String Duration2Time(int duration) {
		String time = "";
		long ms = duration * 1000;

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		time = formatter.format(ms);

		return time;
	}

	/**
	 * �Ƿ�ʼʱ�䶯��
	 * 
	 * @param isStart
	 */
	private void showRecordAnim(boolean isStart) {
		AnimationDrawable animator = (AnimationDrawable) mivrecordDot.getBackground();

		if (isStart) {
			mtvrecordTime.setVisibility(View.VISIBLE);
			mivrecordDot.setVisibility(View.VISIBLE);
			animator.start();
		} else {
			mtvrecordTime.setVisibility(View.GONE);
			mivrecordDot.setVisibility(View.GONE);
			animator.stop();
		}
	}

	/*
	 * Generate the path to save video/picture/log.
	 */
	public static String getFilePath(int fileType, String fileName) {
		String path = "";
		String sdPath = "";

		// Get the path of SD card.
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			return path;
		}
		// Generate the path of Android client.
		path = sdPath + File.separator + "HMSDKDemo";
		switch (fileType) {
		case FILE_TYPE_RECORD:
			path += File.separator + FOLDER_NAME_RECORD;
			break;

		case FILE_TYPE_CAPTURE:
			path += File.separator + FOLDER_NAME_CAPTURE;
			break;

		case FILE_TYPE_LOG:
			path += File.separator + FOLDER_NAME_LOG;
			break;

		default:
			break;
		}

		// Make sure the path is exist.
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}

		// Generate the file name.
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());
		String localTime = formatter.format(curDate);
		String filename = fileName + localTime;

		// Attach the extension name.
		switch (fileType) {
		case FILE_TYPE_RECORD:
			path += File.separator + filename + ".hmv";
			break;

		case FILE_TYPE_CAPTURE:
			path += File.separator + filename + ".png";
			break;

		case FILE_TYPE_LOG:
			path += File.separator + filename + ".txt";
			break;

		default:
			break;
		}

		return path;
	}

}

class PlayView extends GLSurfaceView {
	private PlayRenderer playRenderer;
	private boolean isFirstIn = true;

	public PlayView(Context context, AttributeSet attrs) {
		super(context);

		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		playRenderer = new PlayRenderer();
		setRenderer(playRenderer);

		// Set render mode.
		setRenderMode(RENDERMODE_WHEN_DIRTY);

		// Register the OpenGL render callback.
		CustomApplication.getJni().setRenderCallback(new HMCallback.RenderCallback() {
			@Override
			public void onRequest() {
				requestRender(); // Force to render if video data comes.
			}
		});
	}

	// ����ӿڶ�������һ��OpenGL��GLSurfaceView�л���ͼ������Ҫ�ķ�����
	class PlayRenderer implements GLSurfaceView.Renderer {

		// ����OpenGL�Ļ������������ǳ�ʼ��OpenGL��ͼ�����塣
		public void onSurfaceChanged(GL10 gl, int w, int h) {
			CustomApplication.getJni().gLResize(w, h);
		}

		// ���������Ҫ��ɻ���ͼ�εĲ�����
		public void onDrawFrame(GL10 gl) {
			if (isFirstIn) {
				isFirstIn = false;
				return;
			}

			CustomApplication.getJni().gLRender();
		}

		// ��Ҫ������GLSurfaceView�����ı仯������Ӧ��
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			CustomApplication.getJni().gLInit();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
		CustomApplication.getJni().gLUninit();
	}
}
