package com.jal.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jal.mvp.entity.HeWeather5;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by SEELE on 2017/3/9.
 */

public class WeatherUtils {

    public static void getWeather2(String city, final OnDownListener listener) {
        String key = "b478f335a5114ba3b6013f6dd92bd422";
        String url = "https://free-api.heweather.com/v5/weather?key=" + key + "&" + "city=" + city;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        if (response != null) {
                            String string = response.body().string();
                            return string;
                        }
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        if (response != null) {
                            String json = (String) response;
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray heWeather5 = jsonObject.getJSONArray("HeWeather5");
                                TypeToken<List<HeWeather5>> tt = new TypeToken<List<HeWeather5>>() {
                                };
                                List<HeWeather5> weathers = new Gson().fromJson(heWeather5.toString(), tt.getType());
                                listener.onDownSucc(weathers);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onDownFiled();
                            }
                        }
                    }
                });
    }


    public static String getShareMessage(HeWeather5 weather) {
        StringBuffer message = new StringBuffer();
        message.append(weather.getBasic().getCity());
        message.append("天气：");
        message.append("\r\n");
        message.append(weather.getBasic().getUpdate().getLoc());
        message.append(" 发布：");
        message.append("\r\n");
        message.append(weather.getNow().getCond().getTxt());
        message.append("，");
        message.append(weather.getNow().getTmp()).append("℃");
        message.append("。");
        message.append("\r\n");
        message.append("PM2.5：").append(weather.getAqi().getCity().getPm25());
        message.append("，");
        message.append(weather.getAqi().getCity().getQlty());
        message.append("。");
        message.append("\r\n");
        message.append("今天：");
        message.append(weather.getDaily_forecast().get(0).getTmp().getMin()).append("℃-");
        message.append(weather.getDaily_forecast().get(0).getTmp().getMax()).append("℃");
        message.append("，");
        message.append(weather.getDaily_forecast().get(0).getCond().getTxt_d());
        message.append("，");
        message.append(weather.getDaily_forecast().get(0).getWind().getDir());
        message.append(weather.getDaily_forecast().get(0).getWind().getSc());
        message.append("级。");
        message.append("\r\n");
        message.append("明天：");
        message.append(weather.getDaily_forecast().get(1).getTmp().getMin()).append("℃-");
        message.append(weather.getDaily_forecast().get(1).getTmp().getMax()).append("℃");
        message.append("，");
        message.append(weather.getDaily_forecast().get(1).getCond().getTxt_d());
        message.append("，");
        message.append(weather.getDaily_forecast().get(1).getWind().getDir());
        message.append(weather.getDaily_forecast().get(1).getWind().getSc());
        message.append("级。");

        return message.toString();
    }

    public interface OnDownListener {
        void onDownSucc(Object o);

        void onDownFiled();
    }


}
