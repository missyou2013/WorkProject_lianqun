package activity.lianqun.herry.com.workproject_lianqun.isee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.huamaitel.api.HMDefines;
import com.huamaitel.api.HMDefines.ChannelInfo;
import com.huamaitel.api.HMDefines.NodeTypeInfo;
import com.huamaitel.api.HMJniInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.core.CustomApplication;
import activity.lianqun.herry.com.workproject_lianqun.utils.L;

public class DeviceActivity extends BaseActivity {
    private static final String TAG = "tag";
    private List<Map<String, Object>> mListData;
    private int mVideoStream = HMDefines.CodeStream.SEC_STREAM;
    GridView gridView;
    private AnBaoAdapterSheXiangTou adapter = null;
    private ArrayList<String> list_devices = new ArrayList<String>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.device_activity, R.string.she_xiang_tou_list);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.device_activity);
        list_devices = getIntent().getStringArrayListExtra("list_device");
        L.d("传过来的=" + list_devices.toString());
        gridView = (GridView) findViewById(R.id.id_device_list);
        mListData = new ArrayList<Map<String, Object>>();

        // Get the root of the tree.
        int treeId = CustomApplication.treeId;
        int rootId = CustomApplication.getJni().getRoot(treeId);

        CustomApplication.rootList.clear();
        CustomApplication.rootList.add(rootId);

        getChildrenByNodeId(rootId);

        if (mListData.size() > 0) {
            adapter = new AnBaoAdapterSheXiangTou(DeviceActivity.this, mListData);

            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) arg0
                            .getItemAtPosition(position);
                    int nodeType = (Integer) map.get("type");
                    Log.d("DeviceActivity:", "nodeType:" + nodeType);
                    int nodeId = (Integer) map.get("id");
                    Log.d("DeviceActivity:", "nodeId:" + nodeId);

                    CustomApplication.curNodeHandle = nodeId;

                    if (nodeType == NodeTypeInfo.NODE_TYPE_DVS
                            || nodeType == NodeTypeInfo.NODE_TYPE_GROUP) {
                        CustomApplication.rootList.add(nodeId);
                        getChildrenByNodeId(nodeId);
                        ((AnBaoAdapterSheXiangTou) gridView.getAdapter())
                                .notifyDataSetChanged();
                    }
                    if (nodeType == NodeTypeInfo.NODE_TYPE_CHANNEL) {

                        int nodeDvsId = CustomApplication.getJni()
                                .getParentId(nodeId);
                        ChannelInfo info = CustomApplication.getJni().getChannelInfo(
                                nodeId);

                        Log.d(TAG, "info:" + info.index + "+" + info.name);
                        gotoPlayActivity(nodeDvsId, info.index, mVideoStream);
                    } else if (nodeType == NodeTypeInfo.NODE_TYPE_DEVICE) {
                        gotoPlayActivity(nodeId, 0, mVideoStream);

                    }
                    // DeviceActivity.this.finish();
                }

            });
        } else {
            Toast.makeText(this, "没有摄像头", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (CustomApplication.treeId != 0) {
            CustomApplication.getJni().releaseTree(CustomApplication.treeId);
        }

        if (CustomApplication.serverId != 0) {
            CustomApplication.getJni().disconnectServer(CustomApplication.serverId);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (CustomApplication.rootList.size() != 1) {
                int nodeId = CustomApplication.rootList
                        .get(CustomApplication.rootList.size() - 2);
                CustomApplication.rootList
                        .remove(CustomApplication.rootList.size() - 1);

                getChildrenByNodeId(nodeId);

                ((AnBaoAdapterSheXiangTou) gridView.getAdapter())
                        .notifyDataSetChanged();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    // Get the children list by this parent node.
    private void getChildrenByNodeId(int nodeId) {
        Log.d(TAG, "getDeviceListByNodeId nodeId: " + nodeId);
        if (nodeId != 0) {
            HMJniInterface sdk = CustomApplication.getJni();
            mListData.clear();

            int count = sdk.getChildrenCount(nodeId);
            Log.d(TAG, "getChildrenCount: " + count);
            for (int i = 0; i < count; ++i) {
                Map<String, Object> obj = new HashMap<String, Object>();
                int childrenNode = sdk.getChildAt(nodeId, i);
                int nodeType = sdk.getNodeType(childrenNode);

                obj.put("type", nodeType);

                if (nodeType == NodeTypeInfo.NODE_TYPE_GROUP) {
                    obj.put("img", R.drawable.folder);
                } else if (nodeType == NodeTypeInfo.NODE_TYPE_DEVICE) {
                    obj.put("img", R.drawable.device);
                } else if (nodeType == NodeTypeInfo.NODE_TYPE_DVS) {
                    obj.put("img", R.drawable.dvs);
                } else if (nodeType == NodeTypeInfo.NODE_TYPE_CHANNEL) {
                    obj.put("img", R.drawable.device);
                }

                Log.d(TAG, " childNode: " + childrenNode);
                Log.d(TAG, "childNode Url: " + sdk.getNodeUrl(childrenNode));
                Log.d(TAG, "childNode sn: " + sdk.getDeviceSn(childrenNode));

                obj.put("id", childrenNode);
                obj.put("name", sdk.getNodeName(childrenNode));
                Log.d(TAG, " sdk.getDeviceSn(childrenNode): " + sdk.getDeviceSn(childrenNode));
                if (list_devices.contains(sdk.getDeviceSn(childrenNode))) {
                    mListData.add(obj);
                } else {
//					UtilsLog.d("不包含在内的=" + sdk.getDeviceSn(childrenNode));
                }

            }
        }
    }

    private void gotoPlayActivity(int nodeId, int channel, int videoStream) {
        Intent intent = new Intent();
        intent.putExtra(CustomApplication.NODE_ID, nodeId);
        intent.putExtra(CustomApplication.CHANNEL, channel);
        intent.putExtra(CustomApplication.VIDEO_STREAM, videoStream);
        CustomApplication.mIsUserLogin = true;
//		UtilsLog.d("打开摄像头的参数====" + nodeId + "   " + channel + "     "
//				+ videoStream);
        intent.setClass(DeviceActivity.this, PlayActivity.class);
        startActivity(intent);
    }


}
