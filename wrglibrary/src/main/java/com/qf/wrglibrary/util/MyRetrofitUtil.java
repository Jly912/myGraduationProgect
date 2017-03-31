package com.qf.wrglibrary.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/27 0027.
 * Author by RuiGan
 */

public class MyRetrofitUtil {

    private DownListener downListener;
    private Context context;
    private Handler handler = new Handler();


    public MyRetrofitUtil(Context context){
        this.context = context;
    }
    public MyRetrofitUtil setDownListener(DownListener downListener){
        this.downListener = downListener;
        return this;
    }

    public MyRetrofitUtil downJson(String url, final int requestCode) {
        RetrofitClient.getInstance(context)
                .create(MyApiService.class)
                .getJSON(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (downListener != null) {
                            final Object object = downListener.paresJson(s, requestCode);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    downListener.downSucc(object, requestCode);
                                }
                            });
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("print", "call: " + throwable.getMessage());
                    }
                });
        return this;
    }

    public interface DownListener {

        //解析JSON时回调
        Object paresJson(String json, int requestCode);



        //解析完成后回调
        void downSucc(Object object, int requestCode);
    }
}
