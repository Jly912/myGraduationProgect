package com.jal.mvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.AnimRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jal.mvp.entity.PhotoEntity;
import com.jal.weatherdemo.MainActivity;
import com.jal.weatherdemo.PhotoDetailActivity;
import com.jal.weatherdemo.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2017/3/14.
 */

public class PhotoRyAdapter extends RecyclerView.Adapter {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    private Context context;
    private List<PhotoEntity.ResultsBean> data;
    private List<String> images = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    private int mLastPosition = -1;
    boolean mIsShowFooter;

    public PhotoRyAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<PhotoEntity.ResultsBean> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            images.add(data.get(i).getUrl());
            names.add(data.get(i).get_id());
        }
        this.notifyDataSetChanged();
    }

    public void addData(List<PhotoEntity.ResultsBean> data) {
        this.data.addAll(data);
        for (int i = 0; i < data.size(); i++) {
            images.add(data.get(i).getUrl());
            names.add(data.get(i).get_id());
        }
        this.notifyDataSetChanged();
    }

    public List<PhotoEntity.ResultsBean> getData() {
        return data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_photo_girl_item, parent, false);
            return new PhotoViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_footer_item, parent, false);
            return new FooterViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            PhotoEntity.ResultsBean resultsBean = data.get(position);
            Glide.with(context)
                    .load(resultsBean.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.default_image)
                    .error(R.mipmap.error_image)
                    .into(photoViewHolder.iv);
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooterPosition(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    /**
     * 是否为 footer item
     *
     * @param position
     * @return
     */
    private boolean isFooterPosition(int position) {
        return (getItemCount() - 1) == position;
    }

    /**
     * item 加载动画
     *
     * @param holder
     * @param position
     * @param type
     */
    protected void setItemAppearAnimation(RecyclerView.ViewHolder holder, int position, @AnimRes int type) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), type);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    /**
     * 显示Footer
     */
    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    /**
     * 隐藏Footer
     */
    public void hideFooter() {
        mIsShowFooter = false;
        notifyItemRemoved(getItemCount());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.photo_iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    MainActivity activity = (MainActivity) context;
                    Intent intent = new Intent(activity, PhotoDetailActivity.class);
                    intent.putExtra("list", (Serializable) images);
                    intent.putExtra("index", adapterPosition);
                    intent.putExtra("name", (Serializable) names);
                    activity.startActivity(intent);
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
