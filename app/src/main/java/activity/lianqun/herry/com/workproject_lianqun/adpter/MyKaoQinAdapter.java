package activity.lianqun.herry.com.workproject_lianqun.adpter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.models.Signs;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/24.
 */

public class MyKaoQinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的


    //获取从Activity中传递过来每个item的数据集合
//    private List<String> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;
    private List<Signs> signsList;
    private  String  up_down_date;//上下班时间

    //构造函数
    public MyKaoQinAdapter(List<Signs> signsList,String  up_down_date) {
        this.signsList = signsList;
        this.up_down_date=up_down_date;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_recycler_kaoqin, parent, false);
        return new ListHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ListHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                // ((ListHolder) holder).tv.setText(mDatas.get(position - 1));

                if (!TextUtils.isEmpty(signsList.get(position ).getDates())) {
                    ((ListHolder) holder).kaoqinDateLeft.setText(signsList.get(position)
                            .getDates());
                }
                if (!TextUtils.isEmpty(up_down_date)) {
                    ((ListHolder) holder).kaoqinDateMiddle.setText(up_down_date);
                }
                if (!TextUtils.isEmpty(String.valueOf(signsList.get(position ).getIslate()))) {
                    ((ListHolder) holder).kaoqinDateRight.setText("迟到"+String.valueOf(signsList.get
                            (position).getIslate())+"分钟");
                }
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder {
        TextView kaoqinDateLeft;
        TextView kaoqinDateMiddle;
        TextView kaoqinDateRight;

        public ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            kaoqinDateLeft = (TextView) itemView.findViewById(R.id.kaoqin_date_left);
            kaoqinDateMiddle = (TextView) itemView.findViewById(R.id.kaoqin_date_middle);
            kaoqinDateRight = (TextView) itemView.findViewById(R.id.kaoqin_date_right);
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView    
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return signsList.size();

        } else if (mHeaderView == null && mFooterView != null) {
            return signsList.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return signsList.size() + 1;
        } else {
            return signsList.size() + 2;
        }
    }


}