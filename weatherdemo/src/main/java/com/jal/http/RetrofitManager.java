package com.jal.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jal.util.FileUtil;
import com.jal.util.NetUtil;
import com.jal.weatherdemo.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by SEELE on 2017/2/5.
 * retrofit缓存
 */

public class RetrofitManager {
    private static RetrofitManager manager;
    private static Retrofit retrofit;
    private static Gson gson;

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://free-api.heweather.com/")
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public void reset(){
        manager=null;
    }

    public static RetrofitManager getInstacne(){
        if(manager==null){
            synchronized (RetrofitManager.class){
                manager=new RetrofitManager();
            }
        }
        return manager;
    }

    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }

    private static OkHttpClient httpClient() {
        File file = new File(FileUtil.getAppCacheDir(AppContext.getContext()), "/HttpCache");//设置缓存路径
        Cache cache = new Cache(file, 1024 * 1024 * 100);//设置缓存大小100M
        Interceptor cacheInterceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!NetUtil.isNetworkReachable(AppContext.getContext())){
                    request=request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)//强制走缓存
                            .build();
                }

                Response response = chain.proceed(request);
                if(NetUtil.isNetworkReachable(AppContext.getContext())){
                    int maxAge=0;
                    //有网时 设置缓存超时时间0小时
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache_Control","public,max-age="+maxAge)
                            .build();
                }else {
                    int maxStale=60*60*24*28;
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache_Control","public,only-if-cached,max-stale="+maxStale)
                            .build();
                }
                return response;
            }
        };

        return new OkHttpClient.Builder()
                .cache(cache).addInterceptor(cacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static Gson gson() {
        if (gson == null) {
            synchronized (RetrofitManager.class) {
                gson = new GsonBuilder().setLenient().create();
            }
        }
        return gson;
    }
}
