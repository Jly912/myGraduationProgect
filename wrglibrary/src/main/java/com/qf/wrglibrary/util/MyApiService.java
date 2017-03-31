package com.qf.wrglibrary.util;

        import retrofit2.http.GET;
        import retrofit2.http.Url;
        import rx.Observable;

/**
 * Created by Administrator on 2016/10/27 0027.
 * Author by RuiGan
 */

public interface MyApiService {

    String  BASE_URL="http://api.kaolafm.com/";

    @GET
    Observable<String> getJSON(@Url String url);
}
