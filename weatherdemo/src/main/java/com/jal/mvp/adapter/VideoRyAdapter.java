package com.jal.mvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jal.mvp.entity.VideoEntity;
import com.jal.weatherdemo.R;
import com.jal.weatherdemo.VideoDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2017/3/15.
 */

public class VideoRyAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<VideoEntity.ItemListBeanX.DataBeanX.ItemListBean> data;

    public VideoRyAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<VideoEntity.ItemListBeanX.DataBeanX.ItemListBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<VideoEntity.ItemListBeanX.DataBeanX.ItemListBean> getData() {
        return data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_video_item, parent, false);
        return new VideoRyAdapter.VideoViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VideoViewHolder viewHolder = (VideoViewHolder) holder;
        VideoEntity.ItemListBeanX.DataBeanX.ItemListBean itemListBean = data.get(position);
        viewHolder.tv_title.setText(itemListBean.getData().getTitle());
        viewHolder.tv_category.setText("#" + itemListBean.getData().getCategory());
        int duration = itemListBean.getData().getDuration();
        int minute = duration / 60;
        int second = duration % 60;
        String min = minute > 10 ? minute + "'" : "0" + minute + "'";
        String sec = second > 10 ? second + "\"" : "0" + second + "\"";
        viewHolder.tv_time.setText(min + " " + sec);
        String detail = itemListBean.getData().getCover().getDetail();
        Glide.with(context)
                .load(detail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.error_image)
                .into(viewHolder.iv);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv_title, tv_category, tv_time;
        LinearLayout ll;

        public VideoViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_video);
            tv_title = (TextView) itemView.findViewById(R.id.tv_video_title);
            tv_category = (TextView) itemView.findViewById(R.id.tv_video_category);
            tv_time = (TextView) itemView.findViewById(R.id.tv_video_time);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_video_content);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity= (Activity) context;
                    Intent intent=new Intent(activity, VideoDetailActivity.class);
                    intent.putExtra("video",data.get(getAdapterPosition()));
                    ActivityOptionsCompat videoIv = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv, "videoIv");
                    ActivityCompat.startActivity(activity,intent,videoIv.toBundle());
                }
            });
        }
    }


}
