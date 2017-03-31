package com.jal.widget;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jal.util.ShareUtil;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseActivity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by SEELE on 2017/3/21.
 */

public class MyPlayer extends RelativeLayout implements MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {

    private VideoView videoView;
    private String url = "";

    private ImageView ivPlay, iv_screen;//播放按钮、切屏按钮
    private LinearLayout ll_bottom;
    private TextView tv_duration, tv_total;//播放时间、总时间
    private SeekBar seekBar;//进度条

    private GestureDetector gestureDetector;//手势检测器

    //中间层 缓冲
    private LinearLayout ll_middle;
    private TextView tv_progress;
    private LinearLayout ll_light, ll_voice;
    private ImageView iv_light, iv_voice;
    private TextView tvLight, tvVoice;

    private static final int SEND_MSG_PROGRESS = 1;//设置当前播放进度
    private static final int SEND_MSG_HIDE_MSG = 2;//隐藏底部控件

    //Activity对象
    private BaseActivity activity;

    //是否全屏
    private boolean isFullScreen = false;

    //默认缩放模式
    private int scaleType = VideoView.VIDEO_LAYOUT_ZOOM;
    private int videoWidth;//默认宽度
    private int videoHeight;//默认高度

    //声音管理器
    private AudioManager audioManager;
    private int currVoice = -1;//当前音量默认-1
    private int maxVoice;//最大音量

    private float light = -1;//当前亮度

    //头部
    private LinearLayout ll_head;
    private ImageView iv_back, iv_share;

    private static final int SEND_MSG_HIDE_HEAD = 3;
    /**
     * TODO handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_MSG_PROGRESS:
                    setProgress();
                    this.sendEmptyMessageDelayed(SEND_MSG_PROGRESS, 1000);
                    break;
                case SEND_MSG_HIDE_MSG:
                    hideBottom();
                    break;
                case SEND_MSG_HIDE_HEAD:
                    hideHead();
                    break;
            }
        }
    };


    public MyPlayer(Context context) {
        this(context, null);
    }

    public MyPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (context instanceof BaseActivity) {
            this.activity = (BaseActivity) context;
        }
        initView();
    }

    /**
     * TODO 初始化方法
     */
    private void initView() {
        //初始化vitamio
        Vitamio.isInitialized(getContext());

        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.widget_video, this, true);
        videoView = (VideoView) inflate.findViewById(R.id.vvv);
        videoView.setOnPreparedListener(this);
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnCompletionListener(this);

        //初始化底部控件
        ll_bottom = (LinearLayout) inflate.findViewById(R.id.bottom_layout);
        ivPlay = (ImageView) inflate.findViewById(R.id.iv_play);
        iv_screen = (ImageView) inflate.findViewById(R.id.iv_full_screen);
        tv_duration = (TextView) inflate.findViewById(R.id.tv_start_time);
        tv_total = (TextView) inflate.findViewById(R.id.tv_total_time);
        seekBar = (SeekBar) inflate.findViewById(R.id.seek_bar);

        //初始化手势检测器
        gestureDetector = new GestureDetector(getContext(), new GestureListener());

        videoView.setOnInfoListener(this);
        ivPlay.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        iv_screen.setOnClickListener(this);

        //初始化中间层 -- 缓冲
        ll_middle = (LinearLayout) inflate.findViewById(R.id.ll_middle);
        tv_progress = (TextView) inflate.findViewById(R.id.tv_progress);

        //亮度
        ll_light = (LinearLayout) inflate.findViewById(R.id.ll_center_light);
        iv_light = (ImageView) inflate.findViewById(R.id.iv_light);
        tvLight = (TextView) inflate.findViewById(R.id.tv_light);

        //声音
        ll_voice = (LinearLayout) inflate.findViewById(R.id.ll_center_voice);
        iv_voice = (ImageView) inflate.findViewById(R.id.iv_voice);
        tvVoice = (TextView) inflate.findViewById(R.id.tv_voice);

        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);//获得系统音频管理器
        maxVoice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获得媒体音量最大音量

        //初始化头部
        ll_head = (LinearLayout) inflate.findViewById(R.id.ll_top);
        iv_back = (ImageView) inflate.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_share = (ImageView) inflate.findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);

    }

    /**
     * 播放方法
     */
    public void playVideo(String url) {
        Log.e("print", "--widget--" + url);
        playVideo(url, 0);
    }

    /**
     * 设置Activity
     *
     * @param baseActivity
     * @return
     */
    public MyPlayer setActivity(BaseActivity baseActivity) {
        this.activity = baseActivity;
        return this;
    }


    /**
     * 按进度播放
     *
     * @param url
     * @param position
     */
    public void playVideo(String url, int position) {
        this.url = url;
        if (url != null) {
            Log.e("print", "--widget-1111-" + url);
            videoView.setVideoPath(url);
            if (position > 0) {
                //设置进度
                videoView.seekTo(position);
            }

            //开始播放
            videoView.start();
            //正在播放
            ivPlay.setImageResource(R.mipmap.pause);

            //设置当前进度
            handler.sendEmptyMessageDelayed(SEND_MSG_PROGRESS, 1000);
        }
    }


    /**
     * 视频准备后回调
     *
     * @param mp the MediaPlayer that is ready for playback
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        videoView.setVideoLayout(scaleType, 0);
        //获得控件宽高
        videoWidth = getWidth();
        videoHeight = getHeight();
    }

    public MyPlayer setScaleType(int scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    /**
     * TODO 状态改变的监听
     *
     * @param mp    the MediaPlayer the info pertains to.
     * @param what  the type of info or warning.
     * @param extra an extra code, specific to the info. Typically implementation
     *              dependant.
     * @return
     */
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:

                setProgress();
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                //正在缓冲
                ll_middle.setVisibility(VISIBLE);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                缓冲结束
                ll_middle.setVisibility(GONE);
                break;
        }
        return false;
    }

    /**
     * 设置当前播放进度
     */
    private void setProgress() {

        //设置进度条
        int position = (int) videoView.getCurrentPosition();//获得当前进度
        int duration = (int) videoView.getDuration();//获得总耗时

        if (!isTouch) {//如果没有拖动
            if (seekBar != null && !isTouch) {
                seekBar.setMax(duration);
                seekBar.setProgress(position);
            }
            //设置时间文本
            tv_duration.setText(getTime(position));
            tv_total.setText(getTime(duration));

        }
    }

    /**
     * 将毫秒转为00:00:00
     *
     * @param time
     * @return
     */
    private String getTime(long time) {
        int h = (int) (time / 1000 / 60 / 60);
        int m = (int) (time / 1000 / 60 % 60);
        int s = (int) (time / 1000 % 60);
        return (h >= 10 ? h : ("0" + h)) + ":" + (m >= 10 ? m : ("0" + m)) + ":" + (s >= 10 ? s : ("0" + s));
    }


    /**
     * TODO 控件点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                if (videoView != null && videoView.isPlaying()) {
                    videoView.pause();
                    ivPlay.setImageResource(R.mipmap.play);
                } else {
                    videoView.start();
                    ivPlay.setImageResource(R.mipmap.pause);
                }
                break;
            case R.id.iv_full_screen:
                //横竖屏切换
                toggleScreen();
                break;
            case R.id.iv_back:
                if (activity != null) {
                    if (isFullScreen) {
                        toggleScreen();
                    } else {
                        activity.finish();
                    }
                }
                break;
            case R.id.iv_share:
                ShareUtil.shareText(getContext(), this.url);
                break;
        }
    }


    /**
     * TODO 设置横竖屏
     */
    public void toggleScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //当前为横屏
            if (activity != null) {
                //设置为竖屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                isFullScreen = false;
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //当前为竖屏
            if (activity != null) {
                //设置为横屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                isFullScreen = true;
            }
        }
    }

    /**
     * 横竖屏切换
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //隐藏底部状态栏
        hideBottom();

        //隐藏顶部
        hideHead();

        if (isFullScreen) {
            //横屏
            iv_screen.setImageResource(R.mipmap.exit_full);

            //隐藏状态栏
            if (activity != null) {
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

            //设置播放器的宽度
            int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;

            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            layoutParams.width = screenWidth;
            layoutParams.height = screenHeight;
            setLayoutParams(layoutParams);

        } else {
            //竖屏

            //显示状态栏
            if (activity != null) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

            //设置宽高
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            layoutParams.width = videoWidth;
            layoutParams.height = videoHeight;
            setLayoutParams(layoutParams);
        }

        //重新设置拉伸模式
        videoView.setVideoLayout(scaleType, 0);

    }

    /**
     * 拖动条方法
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    boolean isTouch = false;//是否正在拖动

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv_duration.setText(getTime(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouch = true;
        //撤销隐藏底部控件消息
        handler.removeMessages(SEND_MSG_HIDE_MSG);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isTouch = false;
        int progress = seekBar.getProgress();
        videoView.seekTo(progress);

        handler.sendEmptyMessageDelayed(SEND_MSG_HIDE_MSG, 2000);//发送隐藏底部控件的消息
    }

    /**
     * 缓冲进度的百分比 回调方法
     *
     * @param mp      the MediaPlayer the update pertains to
     * @param percent the percentage (0-100) of the buffer that has been filled thus
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (percent > 100) {
            percent = 100;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll_middle.setVisibility(GONE);
                }
            },1000);

        }
        tv_progress.setText(percent + "%");
    }

    /**
     * 播放完成的回调
     *
     * @param mp the MediaPlayer that reached the end of the file
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        //播放完成后移除消息
        handler.removeMessages(SEND_MSG_PROGRESS);

        //播放后设置进度到0
        ivPlay.setImageResource(R.mipmap.play);
        tv_duration.setText("00:00:00");
        videoView.seekTo(0);

    }

    /**
     * TODO 手势监听器
     */
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean isFirst = false;//是否第一次滑动
        private boolean isLand = false;//处理的滑动方向
        private boolean isLight = false;//处理亮度声音

        @Override
        public boolean onDown(MotionEvent e) {
            isFirst = true;
            Log.e("print", "按下");
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("print", "轻按");
            //显示或者隐藏底部控件
            if (isShow(ll_bottom)) {
                hideBottom();
            } else {
                showBottom(2000);
            }

            //显示或隐藏头部控件
            if (isShow(ll_head)) {
                hideHead();
            } else {
                showHead(2000);
            }

            return true;
        }

        /**
         * @param e1        第一次按下的坐标
         * @param e2        最后滑动的坐标
         * @param distanceX
         * @param distanceY
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("print", "滑动");
            float beginX = e1.getX();
            float beginY = e1.getY();

            float endX = (int) e2.getX();
            float endY = (int) e2.getY();

            float mx = endX - beginX;
            float my = beginY - endY;

            if (isFirst) {
                //第一次处理滑动
                if (Math.abs(distanceX) <= Math.abs(distanceY)) {
                    //处理纵向滑动
                    isLand = false;
                    if (beginX <= getWidth() / 2) {
                        //左半屏
                        isLight = true;
                    } else {
                        isLight = false;
                    }

                } else {
                    //处理横向滑动
                    isLand = true;
                }

                isFirst = false;
            }

            //处理滑动事件
            if (isLand) {
                //控制播放进度
            } else {
                float p = my / getHeight();//获得滑动百分比
                if (isLight) {
                    //控制亮度
                    setLight(p);
                } else {
                    //控制声音
                    setVoice(p);
                }
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("print", "双击");
            return true;
        }
    }

    /**
     * TODO 控制音量
     *
     * @param p
     */
    public void setVoice(float p) {
        if (currVoice == -1) {//当前音量为-1
            currVoice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获得当前音量
        }

        //获得当前音量
        int voice = (int) (p * maxVoice + currVoice);
        if (voice > maxVoice) {
            voice = maxVoice;
        } else if (voice < 0) {
            voice = 0;
        }

        //设置音量
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, voice, 0);

        //显示控件
        ll_voice.setVisibility(VISIBLE);
        String percent = (int) (((float) currVoice / maxVoice) * 100) + "%";
        tvVoice.setText(percent);
        iv_voice.setImageResource(currVoice == 0 ? R.mipmap.no_voice : R.mipmap.voice);
    }

    /**
     * TODO 控制亮度 设置应用亮度
     */
    public void setLight(float p) {
        if (light == -1) {
            //0-1 获得当前亮度
            light = activity.getWindow().getAttributes().screenBrightness;
        }

        float currLight = p * 1 + light;
        if (currLight > 1) {
            currLight = 1;
        } else if (currLight < 0.01) {
            currLight = 0.01f;
        }

        //设置亮度
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.screenBrightness = currLight;
        activity.getWindow().setAttributes(attributes);

        //控制文本
        ll_light.setVisibility(VISIBLE);
        tvLight.setText((int) (currLight * 100) + "%");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.e("print", "手势处理完成");
                ll_voice.setVisibility(GONE);
                currVoice = -1;

                ll_light.setVisibility(GONE);
                light = -1;
                break;
        }

        return false;
    }

    /**
     * 显示底部控件
     */
    private void showBottom() {
        showBottom(0);
    }

    /**
     * 显示多少毫秒
     *
     * @param time
     */
    private void showBottom(long time) {
        if (ll_bottom != null && ll_bottom.getVisibility() == GONE) {
            ll_bottom.setVisibility(VISIBLE);
            if (time > 0) {
                handler.sendEmptyMessageDelayed(SEND_MSG_HIDE_MSG, 2000);
            }
        }
    }


    /**
     * 是否显示
     *
     * @param view
     * @return
     */
    private boolean isShow(View view) {
        return view.getVisibility() == VISIBLE ? true : false;
    }

    /**
     * 隐藏底部控件
     */
    private void hideBottom() {
        if (ll_bottom != null && ll_bottom.getVisibility() == VISIBLE) {
            ll_bottom.setVisibility(GONE);
        }
    }

    /**
     * 显示底部控件
     */
    private void showHead() {
        showHead(0);
    }

    /**
     * 显示多少毫秒
     *
     * @param time
     */
    private void showHead(long time) {
        if (ll_head != null && ll_head.getVisibility() == GONE) {
            ll_head.setVisibility(VISIBLE);
            if (time > 0) {
                handler.sendEmptyMessageDelayed(SEND_MSG_HIDE_HEAD, 2000);
            }
        }
    }


    /**
     * 隐藏头部控件
     */
    private void hideHead() {
        if (ll_head != null && ll_head.getVisibility() == VISIBLE) {
            ll_head.setVisibility(GONE);
        }
    }

    /**
     * 生命周期方法
     */
    int currentPosition;

    public void onPause() {
        if (videoView != null) {
            videoView.pause();
            //记录当前播放位置
            currentPosition = (int) videoView.getCurrentPosition();
        }
    }

    public void onResume() {
        if (videoView != null) {
            videoView.resume();

            //根据记录的位置重新开始播放
            if (currentPosition != -1) {
                playVideo(this.url, currentPosition);
            }
        }
    }

    public void onDestroy() {
        if (videoView != null) {
            videoView.stopPlayback();//回收资源
            //清空handler的所有事件
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isFullScreen) {
            toggleScreen();
            return true;
        }
        return false;
    }
}
