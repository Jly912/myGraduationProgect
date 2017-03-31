package com.jal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.reflect.TypeToken;
import com.jal.http.RetrofitManager;
import com.jal.mvp.entity.HeWeather5;
import com.jal.mvp.entity.WeatherBean;
import com.jal.util.SizeUtil;
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

public class WeatherChartView extends LinearLayout {

    private List<HeWeather5.DailyForecastBean> data = new ArrayList<>();

    private Map<String, WeatherBean> weatherBeanMap = new HashMap<>();

    LayoutParams cellParams, rowParams, chartParams;

    public WeatherChartView(Context context) {
        this(context, null);
    }

    public WeatherChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
        cellParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        chartParams = new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtil.dp2px(context, 200));
        init();
    }

    public void setWeather(HeWeather5 heWeather5) {
        Log.e("jal","========="+weatherBeanMap);

        data.clear();
        data.addAll(heWeather5.getDaily_forecast());
        drawWeather();
    }


    private void init(){
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

    public void drawWeather() {
        removeAllViews();
        LinearLayout date = new LinearLayout(getContext());
        date.setLayoutParams(rowParams);
        date.setOrientation(HORIZONTAL);
        date.removeAllViews();

        LinearLayout icon = new LinearLayout(getContext());
        icon.setOrientation(HORIZONTAL);
        icon.setLayoutParams(rowParams);
        icon.removeAllViews();

        LinearLayout weatherStr = new LinearLayout(getContext());
        weatherStr.setOrientation(HORIZONTAL);
        weatherStr.setLayoutParams(rowParams);
        weatherStr.removeAllViews();

        List<Integer> minTemp = new ArrayList<>();
        List<Integer> maxTemp = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            TextView tvDate = new TextView(getContext());
            tvDate.setGravity(Gravity.CENTER);
            tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            TextView tvWeather = new TextView(getContext());
            tvWeather.setGravity(Gravity.CENTER);
            tvWeather.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            final ImageView ivIcon = new ImageView(getContext());
            ivIcon.setAdjustViewBounds(true);
            ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int padding = SizeUtil.dp2px(getContext(), 10);
            ivIcon.setPadding(padding, padding, padding, padding);
            if (i == 0) {
                tvDate.setText(R.string.today);
            } else if (i == 1) {
                tvDate.setText(R.string.tomorrow);
            } else {
                tvDate.setText(TimeUtils.getWeek(data.get(i).getDate(), TimeUtils.DATE_SDF));
            }
            tvWeather.setText(data.get(i).getCond().getTxt_d());
            String txt_d = data.get(i).getCond().getCode_d();
            Log.e("jal","================textd============"+txt_d+"-----map----"+weatherBeanMap);
            WeatherBean weatherBean = weatherBeanMap.get(txt_d);
            Log.e("jal","=======weather====="+weatherBean);
            Glide.with(getContext())
                    .load(weatherBean.getIcon())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform()
                    .into(ivIcon);

            minTemp.add(Integer.valueOf(data.get(i).getTmp().getMin()));
            maxTemp.add(Integer.valueOf(data.get(i).getTmp().getMax()));

            weatherStr.addView(tvWeather, cellParams);
            date.addView(tvDate, cellParams);
            icon.addView(ivIcon, cellParams);
        }

        addView(date);
        addView(weatherStr);
        addView(icon);

        ChartView chartView = new ChartView(getContext());
        chartView.setData(minTemp, maxTemp);
        chartView.setPadding(0, SizeUtil.dp2px(getContext(), 16), 0, SizeUtil.dp2px(getContext(), 16));
        addView(chartView, chartParams);
    }
}
