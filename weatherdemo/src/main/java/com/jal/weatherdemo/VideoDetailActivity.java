package com.jal.weatherdemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.jal.mvp.entity.VideoEntity;
import com.jal.widget.MyPlayer;
import com.qf.wrglibrary.base.BaseActivity;

import butterknife.Bind;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by SEELE on 2017/3/15.
 */

public class VideoDetailActivity extends BaseActivity {

    @Bind(R.id.myPlayer)
    MyPlayer myPlayer;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.tv_like)
    TextView tvLike;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    private VideoEntity.ItemListBeanX.DataBeanX.ItemListBean data;

    @Override
    protected int getContentId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        data = (VideoEntity.ItemListBeanX.DataBeanX.ItemListBean) intent.getSerializableExtra("video");

        Log.d("print", "--------url" + data.getData().getPlayUrl());
        myPlayer.setScaleType(VideoView.VIDEO_LAYOUT_ZOOM)
                .playVideo(data.getData().getPlayUrl());


        tvTitle.setText(data.getData().getTitle());
        int duration = data.getData().getDuration();
        int minute = duration / 60;
        int second = duration % 60;
        String min = minute > 10 ? minute + "'" : "0" + minute + "'";
        String sec = second > 10 ? second + "\"" : "0" + second + "\"";
        tvTime.setText(min + " " + sec);
        tvCategory.setText("#" + data.getData().getCategory());
        tvDescription.setText(data.getData().getDescription());
        int collectionCount = data.getData().getConsumption().getCollectionCount();
        int shareCount = data.getData().getConsumption().getShareCount();
        int replyCount = data.getData().getConsumption().getReplyCount();
        tvLike.setText((collectionCount > 0 ? collectionCount : 0) + "");
        tvShare.setText((shareCount > 0 ? shareCount : 0) + "");
        tvComment.setText((replyCount > 0 ? replyCount : 0) + "");
    }


    /**
     * 横竖屏切换调用此方法
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myPlayer != null) {
            myPlayer.onConfigurationChanged(new Configuration());
        }
    }

    @Override
    public boolean isOpenStatus() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myPlayer != null) {
            myPlayer.onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myPlayer != null) {
            myPlayer.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myPlayer != null) {
            myPlayer.onResume();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (myPlayer != null && myPlayer.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
