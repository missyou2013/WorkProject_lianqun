package activity.lianqun.herry.com.workproject_lianqun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.activity.ApplyDetailsActivity;
import activity.lianqun.herry.com.workproject_lianqun.activity.KaoQinDetailsActivity;
import activity.lianqun.herry.com.workproject_lianqun.adpter.GridAdapter;

/**
 * Created by Administrator on 2016/11/25.
 */

public class TabFragment extends Fragment {

    public static final String PAGE_TITLE = "PAGE_TITLE";
    private String title;

    private RecyclerView mRecylcerView;
    private ApplyStatusAdapter adapter;


    public static TabFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(PAGE_TITLE, title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(PAGE_TITLE);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        TextView titleTv = (TextView) view.findViewById(R.id.title_tv);
        titleTv.setText(title);
        mRecylcerView = (RecyclerView) view.findViewById(R.id.apply_recyclerView);
        mRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ApplyStatusAdapter();
        mRecylcerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), ApplyDetailsActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    class ApplyStatusAdapter extends RecyclerView.Adapter<ApplyStatusAdapter.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;


        public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }


        @Override
        public ApplyStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ApplyStatusAdapter.MyViewHolder holder = new ApplyStatusAdapter.MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_applystatus, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final ApplyStatusAdapter.MyViewHolder holder, int position) {
            //   holder.tv.setText(mDatas.get(position));
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });

//                holder.mIvIcon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int pos=holder.getLayoutPosition();
//                        mOnItemClickLitener.onItemClick(holder.mIvIcon,pos);
                //  }
                //      });
            }

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {


            public MyViewHolder(View view) {
                super(view);

            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}