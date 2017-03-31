package com.jal.util;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by SEELE on 2017/2/6.
 * 讯飞语音播报
 */

public class TTSManager {
    private static TTSManager mInstance;
    private SpeechSynthesizer mTts;

    private TTSManager(Context context) {
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        //设置听写参数
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyu");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "30");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，0-100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);//设置云端
    }

    public static TTSManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (TTSManager.class) {
                mInstance = new TTSManager(context);
            }
        }

        return mInstance;
    }

    public void speak(String text, final TTSCallback ttsCallback) {
        if (TextUtils.isEmpty(text) || mTts.isSpeaking()) {
            return;
        }

        Log.d("print", "-----msg--" + text);

        mTts.startSpeaking(text, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {
                Log.e("print", "start");
                if (ttsCallback != null) {

                    ttsCallback.onStart();
                }
            }

            //缓冲速度回调
            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                Log.e("print", "completed");
                if (ttsCallback != null) {

                    ttsCallback.onCompleted();
                }
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
    }

    public interface TTSCallback {
        void onStart();

        void onCompleted();
    }

}
