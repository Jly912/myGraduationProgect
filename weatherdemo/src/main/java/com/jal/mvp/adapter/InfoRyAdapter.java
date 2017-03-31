package com.jal.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jal.mvp.entity.InfoBean;
import com.jal.weatherdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2017/3/8.
 */

public class InfoRyAdapter extends RecyclerView.Adapter {

    private Context context;
    private InfoListener listener;
    private List<InfoBean.ResultsBean> data;

    public InfoRyAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<InfoBean.ResultsBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<InfoBean.ResultsBean> getData(){
        return data;
    }

    public void setListener(InfoListener listener){
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_ry_news, parent, false);
        return new FirstViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FirstViewHolder mHolder = (FirstViewHolder) holder;
        String who = data.get(position).getWho();
        if(who!=null){
            mHolder.tvAuthor.setText(who);
        }else {
            mHolder.tvAuthor.setText("未知");
        }

        mHolder.tvTitle.setText(data.get(position).getDesc());
        String createdAt = data.get(position).getCreatedAt();
        String[] ts = createdAt.split("T");
        mHolder.tvTime.setText(ts[0]);

        if(data.get(position).getImages()!=null){
            List<String> images = data.get(position).getImages();
            Glide.with(context).load(images.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.mipmap.default_image)
                    .error(R.mipmap.error_image)
                    .into(mHolder.iv);
        }else {
            mHolder.iv.setVisibility(View.GONE);
            mHolder.llContent.setGravity(Gravity.BOTTOM);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FirstViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTime, tvTitle, tvAuthor;
        ImageView iv;
        LinearLayout llContent;
        RelativeLayout ll;

        public FirstViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_date);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_who);
            iv = (ImageView) itemView.findViewById(R.id.iv_news);
            llContent= (LinearLayout) itemView.findViewById(R.id.ll);
            ll= (RelativeLayout) itemView.findViewById(R.id.rl_content);
            ll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onItemClick(getAdapterPosition());
            }
        }
    }

    public interface InfoListener{
        void onItemClick(int position);
    }

}
