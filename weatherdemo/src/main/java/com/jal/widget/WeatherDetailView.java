package com.jal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.reflect.TypeToken;
import com.jal.http.RetrofitManager;
import com.jal.mvp.entity.HeWeather5;
import com.jal.mvp.entity.WeatherBean;
import com.jal.util.TimeUtils;
import com.jal.weatherdemo.AppContext;
import com.jal.weatherdemo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2017/2/8.
 */

public class WeatherDetailView extends LinearLayout {
    private List<HeWeather5.DailyForecastBean> dailyForecastList = new ArrayList<>();
    LayoutParams rowParams;

    private Map<String, WeatherBean> weatherBeanMap = new HashMap<>();


    public WeatherDetailView(Context context) {
        this(context, null);
    }

    public WeatherDetailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
        rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init() {
        List<WeatherBean> data = RetrofitManager.gson().fromJson(readFromAssets(), new TypeToken<List<WeatherBean>>() {
        }.getType());

        for (WeatherBean weatherBean : data) {
            weatherBeanMap.put(weatherBean.getCode(), weatherBean);
        }
    }

    private String readFromAssets() {
        InputStream in = null;
        try {
            in = AppContext.getContext().getAssets().open("weather1.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void drawInfo() {
        removeAllViews();
        for (int i = 0; i < dailyForecastList.size(); i++) {
            View view = View.inflate(getContext(), R.layout.item_daily_weather, null);
            TextView tvTemp = (TextView) view.findViewById(R.id.tv_day_temp);
            TextView tvWeek = (TextView) view.findViewById(R.id.tv_day_week);
            TextView tvInfo = (TextView) view.findViewById(R.id.tv_day_info);
            final ImageView ivWeek = (ImageView) view.findViewById(R.id.iv_day_weather);

            if (i == 0) {
                tvWeek.setText("今天");
            } else if (i == 1) {
                tvWeek.setText("明天");
            } else {
                tvWeek.setText(TimeUtils.getWeek(dailyForecastList.get(i).getDate(), TimeUtils.DATE_SDF));
            }

            tvTemp.setText(String.format(("%s℃ - %s℃"), dailyForecastList.get(i).getTmp().getMin(), dailyForecastList.get(i).getTmp().getMax()));
            tvInfo.setText(String.format("%s，降雨几率：%s%%，%s %s 级", dailyForecastList.get(i).getCond().getTxt_d(), dailyForecastList.get(i).getPop(), dailyForecastList.get(i).getWind().getDir(), dailyForecastList.get(i).getWind().getSc()));

            String code_d = dailyForecastList.get(i).getCond().getCode_d();
            WeatherBean weatherBean = weatherBeanMap.get(code_d);
            Glide.with(getContext())
                    .load(weatherBean.getIcon())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivWeek);

            addView(view, rowParams);
        }
    }

    public void setWeather5(HeWeather5 weather5) {
        dailyForecastList.clear();
        dailyForecastList.addAll(weather5.getDaily_forecast());
        drawInfo();
    }
}
