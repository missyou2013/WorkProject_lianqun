/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package activity.lianqun.herry.com.workproject_lianqun.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yanzhenjie.album.task.ImageLocalLoader;

import java.util.List;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.utils.DisplayUtils;

/**
 * Created by Yan Zhenjie on 2016/10/30.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ImageViewHolder>   {

    private static int size = (DisplayUtils.screenWidth - 8) / 4;

    private List<String> mImagePathList;

    private LayoutInflater mLayoutInflater;

    private OnItemClickLitener mOnItemClickLitener;

    public GridAdapter() {
    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void notifyDataSetChanged(List<String> imagePathList) {
        this.mImagePathList = imagePathList;
        super.notifyDataSetChanged();
    }

    public void createLayoutInflater(Context context) {
        if (mLayoutInflater == null)
            mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        createLayoutInflater(parent.getContext());
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_main_image, parent, false));
    }
    public void removeData(int position) {

        notifyItemRemoved(position);
    }
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        holder.loadImage(mImagePathList.get(holder.getAdapterPosition()));
    if(mOnItemClickLitener!=null){

        holder.mIvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.mIvIcon,pos);
            }
        });

  holder.mIvIcon.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        int pos=holder.getLayoutPosition();
        mOnItemClickLitener.onItemLongClick(holder.mIvIcon,pos);
        return false;
    }
});



    }
    }

    @Override
    public int getItemCount() {
        return mImagePathList == null ? 0 : mImagePathList.size();
    }



    static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvIcon;

        public ImageViewHolder(View itemView) {
            super(itemView);
        itemView.getLayoutParams().height = size;
            itemView.getLayoutParams().width = size;
           itemView.requestLayout();
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }

        public void loadImage(String imagePath) {
            ImageLocalLoader.getInstance().loadImage(mIvIcon, imagePath, size, size);
        }
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
