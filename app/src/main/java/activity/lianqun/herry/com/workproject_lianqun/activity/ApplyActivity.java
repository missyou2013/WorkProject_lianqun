package activity.lianqun.herry.com.workproject_lianqun.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.album.Album;

import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.adpter.GridAdapter;
import activity.lianqun.herry.com.workproject_lianqun.adpter.MyGridLayoutManager;
import activity.lianqun.herry.com.workproject_lianqun.core.BaseActivity;
import activity.lianqun.herry.com.workproject_lianqun.utils.CommonUtils;
import activity.lianqun.herry.com.workproject_lianqun.utils.DisplayUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/24.
 */

public class ApplyActivity extends BaseActivity {
    //choose picture
    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 100;
    @BindView(R.id.picture_chose)
    ImageView pictureChose;
    @BindView(R.id.apply_type)
    TextView applyType;
    @BindView(R.id.apply_start_time)
    TextView applyStartTime;
    @BindView(R.id.apply_end_time)
    TextView applyEndTime;
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    private List<String> pathList;
    private String[] type_qingjia = {"病假", "事假", "年假", "婚假", "产假", "其他"};
    private String[] type_baoxiao = {"车费", "话费", "餐补", "差旅费", "其他"};

    @Override
    protected void setUpContentView() {
        DisplayUtils.initScreen(this);
        setContentView(R.layout.activity_apply, R.string.activity_apply);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new MyGridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view) + 1;
                if (position % 3 == 0)
                    outRect.set(2, 2, 2, 0);
                else if (position % 2 == 0 && (position + 1) % 3 == 0)
                    outRect.set(2, 2, 0, 0);
                else
                    outRect.set(2, 2, 0, 0);
            }
        });

        mGridAdapter = new GridAdapter();
        mRecyclerView.setAdapter(mGridAdapter);
        mGridAdapter.setOnItemClickLitener(new GridAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ApplyActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
                //  mGridAdapter.removeData(position);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ApplyActivity.this, position + " Long_click",
                        Toast.LENGTH_SHORT).show();
                mGridAdapter.removeData(position);
                pathList.remove(position);
            }
        });
    }

    @Override
    protected void setUpMenu(int menuId) {
        super.setUpMenu(R.menu.menu, "申请记录");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Toast.makeText(getApplicationContext(), "申请记录", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ApplyActivity.this, ApplyRecordActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    /**
     * 选择图片。
     */
    private void selectImage() {
        // 1. 使用默认风格，并指定选择数量：
        // 第一个参数Activity/Fragment； 第二个request_code； 第三个允许选择照片的数量，不填可以无限选择。
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO, 9);

        // 2. 使用默认风格，不指定选择数量：
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO); // 第三个参数不填的话，可以选择无数个。

        // 3. 指定风格，并指定选择数量，如果不想限制数量传入Integer.MAX_VALUE;
        Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO
                , 9                                                         // 指定选择数量。
                , ContextCompat.getColor(this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
    }

    /**
     * 处理选择的照片。
     */
    private void handleSelectImage(List<String> pathList) {
        mGridAdapter.notifyDataSetChanged(pathList);
        if (pathList == null || pathList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
        } else {

            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 成功选择了照片。
                // 选择好了照片后，调用这个方法解析照片路径的List。
                pathList = Album.parseResult(data);
                Log.e("tag", pathList.toString());
                handleSelectImage(pathList);
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_dialog_hint)
                        .setMessage(R.string.cancel_select_photo_hint);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.picture_chose)
    public void onClick() {
        selectImage();
    }

    @OnClick({R.id.apply_type, R.id.apply_start_time, R.id.apply_end_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_type:
                actionSheet(type_baoxiao);
                break;
            case R.id.apply_start_time:
                CommonUtils.showDialog(ApplyActivity.this, applyStartTime);
                break;
            case R.id.apply_end_time:
                CommonUtils.showDialog(ApplyActivity.this, applyStartTime);
                break;
        }
    }

    private void actionSheet(String[] type) {
        com.baoyz.actionsheet.ActionSheet.createBuilder(ApplyActivity.this, getSupportFragmentManager())
                .setCancelButtonTitle("取消(Cancel)")
                .setOtherButtonTitles(type)
                .setCancelableOnTouchOutside(true)
                .setListener(new com.baoyz.actionsheet.ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(com.baoyz.actionsheet.ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(com.baoyz.actionsheet.ActionSheet actionSheet, int index) {


                    }
                })
                .show();
    }
}
