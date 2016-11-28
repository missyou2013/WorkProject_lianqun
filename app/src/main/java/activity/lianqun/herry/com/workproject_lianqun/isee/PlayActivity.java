package activity.lianqun.herry.com.workproject_lianqun.isee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.huamaitel.api.HMCallback;
import com.huamaitel.api.HMCallback.NetworkCallback;
import com.huamaitel.api.HMDefines;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication;


/**
 * 视频主界面
 *
 * @author admin
 */
public class PlayActivity extends Activity implements OnClickListener {
    private static final String TAG = "tag";

    // Records/Pictures/Logs related.
    public static final String FOLDER_NAME_RECORD = "records";
    public static final String FOLDER_NAME_CAPTURE = "pictures";
    public static final String FOLDER_NAME_LOG = "log";
    public static final int FILE_TYPE_RECORD = 1;
    public static final int FILE_TYPE_CAPTURE = 2;
    public static final int FILE_TYPE_LOG = 3;

    // 录像相关
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
    private Button btn_xiaoguo; // video
    private static final int send_msg_code = 0x101;

    // stream.)
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            int what = msg.what;
            if (what == send_msg_code) {
                btn_xiaoguo.setVisibility(View.VISIBLE);
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        // 让屏幕保持不暗不关闭
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();

        setListener();

        // Get the node id from the DeviceActivity .
        Intent intent = getIntent();
        mNodeId = intent.getIntExtra(CustomApplication.NODE_ID, 0);
        mChannelIndex = intent.getIntExtra(CustomApplication.CHANNEL, 0);
        mVideoStream = intent.getIntExtra(CustomApplication.VIDEO_STREAM,
                HMDefines.CodeStream.SEC_STREAM);

        // 登录 & 打开视频
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
//        mivrecordDot.setBackgroundResource(R.drawable.record_anim);
        mivrecordDot.setImageDrawable(null);
        btn_xiaoguo = (Button) findViewById(R.id.btn_xiaoguo);
        btn_xiaoguo.setOnClickListener(this);

    }

    private void setListener() {
        // TODO:可以分别添加用boolean变量，防止听说等功能被多次重复调用。

        /**
         * 打开录像
         */
        mbtn_record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = CustomApplication.getJni().getNodeName(
                        CustomApplication.curNodeHandle);
                String path = getFilePath(FILE_TYPE_RECORD, fileName);
                if (mIsRecording) {
                    CustomApplication.getJni().stopLocalRecord(
                            CustomApplication.mRecordHandle);
                    mIsRecording = false;
                    mUIHandler.sendEmptyMessage(MSG_STOP_RECORD);
                    Toast.makeText(getApplicationContext(),
                            "停止录像，视频存放在：" + path, Toast.LENGTH_SHORT).show();
                } else {
                    CustomApplication.mCapturePath = path;
                    CustomApplication.mRecordHandle = CustomApplication.getJni()
                            .startLocalRecord(CustomApplication.mUserId, path);

                    if (CustomApplication.mRecordHandle == 0) {
                        Log.e(TAG, "start local record fail.");
                    } else {
                        Log.i(TAG, "start local record success.");
                        showRecordAnim(true);
                        Toast.makeText(getApplicationContext(), "开始录像",
                                Toast.LENGTH_SHORT).show();
                        showRecordTime();
                        mIsRecording = true;
                    }
                }
            }

            /*
             * Show record time text
             */
            private void showRecordTime() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        while (mIsRecording) {
                            mUIHandler.sendEmptyMessage(MSG_SHOW_RECORD_TIME);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });

        /**
         * 拍照
         */
        mbtn_capture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = CustomApplication.getJni().getNodeName(
                        CustomApplication.curNodeHandle);
                String path = getFilePath(FILE_TYPE_CAPTURE, fileName);
                CustomApplication.mCapturePath = path;

                byte data[] = CustomApplication.getJni().localCapture(
                        CustomApplication.mUserId);
                if (null == data) {
                    Toast.makeText(getApplicationContext(), "拍照失败",
                            Toast.LENGTH_SHORT).show();
                } else {
                    boolean res = saveCapturedPic(data,
                            CustomApplication.mCapturePath);
                    if (res) {
                        Log.i(TAG, "Local capture success." + "拍照成功！图片存放在："
                                + path);
                        Toast.makeText(getApplicationContext(),
                                "拍照成功，图片存放在" + path, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Local capture fail.");
                    }
                }
            }
        });

        /**
         * 打开听
         */
        mbtn_openlisten.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startListen();
                mbtn_openlisten.setEnabled(false);
                mbtn_closelisten.setEnabled(true);

            }
        });

        /**
         * 关闭听
         */
        mbtn_closelisten.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                stopListen();

                mbtn_closelisten.setEnabled(false);
                mbtn_openlisten.setEnabled(true);
            }
        });

        /**
         * 打开说
         */
        mbtn_opentalk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startTalk();

                mbtn_opentalk.setEnabled(false);
                mbtn_closetalk.setEnabled(true);
            }
        });

        /**
         * 关闭说
         */
        mbtn_closetalk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                stopTalk();
                mbtn_opentalk.setEnabled(true);
                mbtn_closetalk.setEnabled(false);
            }
        });

        /**
         * 布防
         */
        mbtn_arming.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CustomApplication.getJni().arming(CustomApplication.mUserId, 1, "");
//                Toast.makeText(getApplicationContext(), "布防成功", 0).show();
                mbtn_arming.setEnabled(false);
                mbtn_disarming.setEnabled(true);

            }
        });

        /**
         * 撤防
         */
        mbtn_disarming.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CustomApplication.getJni().disarming(CustomApplication.mUserId, 1,
                        "");
//                Toast.makeText(getApplicationContext(), "撤防成功", 0).show();
                mbtn_disarming.setEnabled(false);
                mbtn_arming.setEnabled(true);

            }
        });

    }

    private void startListen() {
        new Thread() {
            public void run() {
                HMDefines.OpenAudioParam param = new HMDefines.OpenAudioParam();
                param.channel = mChannelIndex;
                HMDefines.OpenAudioRes res = new HMDefines.OpenAudioRes();

                CustomApplication.mAudioHandle = CustomApplication.getJni()
                        .startAudio(CustomApplication.mUserId, param, res);

                if (CustomApplication.mAudioHandle == -1) {
                    mIsListening = false;
                    Log.e(TAG, "抱歉，该设备不支持音频功能");
                    return;
                } else {
                    mIsListening = true;
                }
            }
        }.start();
    }

    private void stopListen() {
        int ret = CustomApplication.getJni().stopAudio(
                CustomApplication.mTalkHandle);
        if (ret != 0) {
            Log.e(TAG, "close the audio fail");
        } else {
            Log.i(TAG, "close the audio success");
        }
        mIsListening = false;
    }

    private void startTalk() {
        new Thread() {
            public void run() {
                HMDefines.OpenTalkParam param = new HMDefines.OpenTalkParam();
                param.channel = mChannelIndex;
                param.audioEncode = CustomApplication.mChannelCapacity[0].audioCodeType;
                param.sample = CustomApplication.mChannelCapacity[0].audioSample;
                param.audioChannel = CustomApplication.mChannelCapacity[0].audioChannel;
                CustomApplication.mTalkHandle = CustomApplication.getJni()
                        .startTalk(CustomApplication.mUserId, param);
                if (CustomApplication.mTalkHandle == -1) {
                    mIsTalking = false;
                    return;
                } else {
                    mIsTalking = true;
                }
            }
        }.start();
    }

    private void stopTalk() {
        CustomApplication.getJni().stopTalk(CustomApplication.mTalkHandle);
        mIsTalking = false;
    }

    private void openVideo() {
        // if (!isFinishing()) {

//		CommonUtils.showLoadingDialog(PlayActivity.this, "加载中...");

        // }
        new Thread() {
            @Override
            public void run() {
                super.run();

                if (CustomApplication.mIsUserLogin) {
                    // 从互联网登录
                    if (mNodeId == 0) {
                        return;
                    }
                    // Step 1: Login the device.

                    CustomApplication.mUserId = CustomApplication.getJni().loginEx(
                            mNodeId, HMDefines.ConnectPolicy.CONN_POLICY_NVS);
                    if (CustomApplication.mUserId == -1) {
                        return;
                    }
                } else {
                    /**
                     * 从局域网登录 TODO:异常处理 从局域网登录（本地设备） 调试时需要注意手机的网络为同一个局域网
                     * 如果设备进行了设置，输入相应的账号密码。默认账号为：guest,密码不填。
                     * 此处请检查输入的正确性，代码中没有做相关信息正确性检查。
                     */
                    String sIp = getIntent().getStringExtra("ip");
                    String sPort = getIntent().getStringExtra("port");
                    String sUser = getIntent().getStringExtra("user");
                    String sPass = getIntent().getStringExtra("pass");
                    String sSn = getIntent().getStringExtra("sn");
                    if (sIp != null && sPort != null && sSn != null
                            && sUser != null) {
                        // Step 1: Login the device.
                        CustomApplication.mUserId = CustomApplication.getJni()
                                .login(sIp, Short.parseShort(sPort), sSn,
                                        sUser, sPass);
                        if (CustomApplication.mUserId == -1) {
                            return;
                        }
                    }
                }

                mIfLogin = true;

                /**
                 * Step 2: Get device information. TODO:异常处理
                 * 当设备不在线，执行这一步容易返回为空。测试时请检查一下设备是否在线！！ 可参考SDK文档 添加
                 * Online这个接口进行判断。
                 */
                CustomApplication.mDeviceInfo = CustomApplication.getJni()
                        .getDeviceInfo(CustomApplication.mUserId);
                CustomApplication.mChannelCapacity = CustomApplication.getJni()
                        .getChannelCapacity(CustomApplication.mUserId);
                if (CustomApplication.mDeviceInfo == null) {
                    return;
                }
                // Step 3: Start video
                HMDefines.OpenVideoParam param = new HMDefines.OpenVideoParam();
                param.channel = mChannelIndex;
                // param.codeStream = HMDefines.CodeStream.MAIN_STREAM;
                param.codeStream = mVideoStream;
                param.videoType = HMDefines.VideoStream.REAL_STREAM;
                HMDefines.OpenVideoRes res = new HMDefines.OpenVideoRes();

                CustomApplication.mVideoHandle = CustomApplication.getJni()
                        .startVideo(CustomApplication.mUserId, param, res);

                if (CustomApplication.mVideoHandle == -1) {
                    mIsPlaying = false;
                     Toast.makeText(PlayActivity.this, "连接超时", Toast.LENGTH_LONG).show();
                    System.out.println("连接超时");
//					EventBus.getDefault().post(new EventStr("video_timeout"));
                    finish();
                    return;
                }
                /**
                 * TODO：异常处理 startVideo成功后 ，执行 setNetworkCallback 回调函数检测网络数据。
                 * 为保证设备信息获取到，可以再执行一次getDeviceInfo操作。 如添加了进度条，在此时进行释放。
                 *
                 */
                CustomApplication.getJni().setNetworkCallback(
                        CustomApplication.mUserId, new NetworkCallback() {

                            @Override
                            public void onNetwork(int arg0) {
                                if (arg0 == -1) {
                                    System.out.println("没有获取到视频信息----------");
                                    finish();
                                }

                            }
                        });

                mIsPlaying = true;
                handler.sendEmptyMessage(send_msg_code);
//				CommonUtils.hideLoadingDialog(PlayActivity.this);

            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确定要退出视频播放吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the play activity for exiting.
                        if (!mIfExit) {
                            exitPlayActivity();
                        }
                        PlayActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing……
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
            int result = CustomApplication.getJni().stopVideo(
                    CustomApplication.mVideoHandle);
            if (result != 0) {
                Log.e(TAG, "stopvideo fail.");
            } else {
                Log.i(TAG, "stopvideo success.");
            }
            mIsPlaying = false;
        }

        if (mIsRecording) {
            int result = CustomApplication.getJni().stopLocalRecord(
                    CustomApplication.mRecordHandle);
            if (result != 0) {
                Log.e(TAG, "stopLocalRecord fail.");
            } else {
                Log.i(TAG, "stopLocalRecord success.");
            }
            mIsRecording = false;
        }

        if (mIsTalking) {

            int result = CustomApplication.getJni().stopTalk(
                    CustomApplication.mTalkHandle);
            if (result != 0) {
                Log.e(TAG, "stopTalk fail.");
            } else {
                Log.i(TAG, "stopTalk success.");
            }
            mIsTalking = false;
        }
        if (mIsListening) {
            int result = CustomApplication.getJni().stopAudio(
                    CustomApplication.mAudioHandle);
            if (result != 0) {
                Log.e(TAG, "stopAudio fail.");
            } else {
                Log.i(TAG, "stopAudio success.");
            }

            mIsListening = false;
        }

        if (mIfLogin) {
            int result = CustomApplication.getJni().logout(
                    CustomApplication.mUserId);
            if (result != 0) {
                Log.e(TAG, "logout fail.");
            } else {
                Log.i(TAG, "logout success.");
            }
            mIfExit = false;
        }

    }

    /**
     * 显示录像时间
     */
    public Handler mUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_RECORD_TIME:
                    int time = CustomApplication.getJni().getLocalRecordTime(
                            CustomApplication.mRecordHandle);
                    String timString = Duration2Time(time);
                    mtvrecordTime.setText(timString);
                    break;

                case MSG_STOP_RECORD:
                    int result = CustomApplication.getJni().stopLocalRecord(
                            CustomApplication.mUserId);
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

    /*************************************************
     * 工具方法
     *******************************************************/
    /*
     * 将byte的数据保存为为Jpg、Png等图片格式。
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
     * 时间格式转换
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
     * 是否开始时间动画
     *
     * @param isStart
     */
    private void showRecordAnim(boolean isStart) {
        AnimationDrawable animator = (AnimationDrawable) mivrecordDot
                .getBackground();

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
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
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

    @Override
    public void onClick(View arg0) {
        if ("切换至高清".equals(btn_xiaoguo.getText().toString())) {
            releaseResources();
            mVideoStream = HMDefines.CodeStream.MAIN_STREAM;
            openVideo();
            btn_xiaoguo.setText("切换至标清");
        } else {
            releaseResources();
            mVideoStream = HMDefines.CodeStream.SEC_STREAM;
            openVideo();
            btn_xiaoguo.setText("切换至高清");
        }

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
        CustomApplication.getJni().setRenderCallback(
                new HMCallback.RenderCallback() {
                    @Override
                    public void onRequest() {
                        requestRender(); // Force to render if video data comes.
                    }
                });
    }

    // 这个接口定义了在一个OpenGL的GLSurfaceView中绘制图形所需要的方法。
    class PlayRenderer implements Renderer {

        // 设置OpenGL的环境变量，或是初始化OpenGL的图形物体。
        public void onSurfaceChanged(GL10 gl, int w, int h) {
            CustomApplication.getJni().gLResize(w, h);
        }

        // 这个方法主要完成绘制图形的操作。
        public void onDrawFrame(GL10 gl) {
            if (isFirstIn) {
                isFirstIn = false;
                return;
            }

            CustomApplication.getJni().gLRender();
        }

        // 主要用来对GLSurfaceView容器的变化进行响应。
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
