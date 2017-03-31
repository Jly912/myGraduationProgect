package com.jal.http.api;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by SEELE on 2017/3/6.
 */

public interface WeatherController {

    @GET("v5/weather")
    Observable<ResponseBody> getWeather(@Query("city") String cityName, @Query("key") String key );

}
