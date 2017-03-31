package com.jal.mvp.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jal.mvp.entity.HeWeather5;
import com.jal.util.SizeUtil;
import com.jal.util.TimeUtils;
import com.jal.weatherdemo.R;
import com.jal.widget.WeatherChartView;
import com.jal.widget.WeatherDetailView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by SEELE on 2017/2/7.
 */

public class WeatherAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm", Locale.getDefault());

    private boolean showWeatherChart=true;

    public WeatherAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(HeWeather5.TYPE_NOW, R.layout.item_weather_container);
        addItemType(HeWeather5.TYPE_SUGGESTION,R.layout.item_suggestion_weather);
        addItemType(HeWeather5.TYPE_DAILYFORECAST,R.layout.item_weather_container);

    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        switch (baseViewHolder.getItemViewType()){
            case HeWeather5.TYPE_NOW:
                HeWeather5 weather= (HeWeather5) multiItemEntity;
                LinearLayout linearLayout=baseViewHolder.getView(R.id.contentLayout);
                linearLayout.removeAllViews();
                for (int i = 0; i < weather.getHourly_forecast().size(); i++) {
                    View view = View.inflate(mContext, R.layout.item_now_weather, null);
                    TextView tvTime= (TextView) view.findViewById(R.id.tv_now_time);
                    TextView tvTemp= (TextView) view.findViewById(R.id.tv_now_temp);
                    TextView tvPop= (TextView) view.findViewById(R.id.tv_now_pop);
                    TextView tvWind= (TextView) view.findViewById(R.id.tv_now_wind);
                    tvTime.setText(TimeUtils.date2String(TimeUtils.string2Date(weather.getHourly_forecast().get(i).getDate(),sdf1),sdf2));
                    tvTemp.setText(weather.getHourly_forecast().get(i).getTmp()+"℃");
                    tvPop.setText(weather.getHourly_forecast().get(i).getPop()+"%");
                    tvWind.setText(String.format("%s级",weather.getHourly_forecast().get(i).getWind().getSc()));
                    linearLayout.addView(view);
                }
                break;
            case HeWeather5.TYPE_DAILYFORECAST:
                HeWeather5 heWeather5= (HeWeather5) ((HeWeather5) multiItemEntity).clone();
                LinearLayout layout=baseViewHolder.getView(R.id.contentLayout);
                if(showWeatherChart){
                    layout.setPadding(0, SizeUtil.dp2px(mContext,16),0,SizeUtil.dp2px(mContext,16));
                    layout.removeAllViews();
                    layout.addView(getChartView(heWeather5));
                }else {
                    layout.removeAllViews();
                    layout.addView(getDetailView(heWeather5));
                }

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showWeatherChart=!showWeatherChart;
                        notifyItemChanged(baseViewHolder.getAdapterPosition());
                    }
                });
                break;
            case HeWeather5.TYPE_SUGGESTION:
                HeWeather5.SuggestionBean suggestionBean= (HeWeather5.SuggestionBean) multiItemEntity;
                baseViewHolder.setText(R.id.tv_suggestion_air, String.format("舒适指数 -- %s",suggestionBean.getComf().getBrf()));
                baseViewHolder.setText(R.id.tv_suggestion_air_info,suggestionBean.getAir().getTxt());
                baseViewHolder.setText(R.id.tv_suggestion_out,String.format("运动指数 -- %s",suggestionBean.getSport().getBrf()));
                baseViewHolder.setText(R.id.tv_suggestion_out_info,suggestionBean.getSport().getTxt());
                baseViewHolder.setText(R.id.tv_suggestion_car,String.format("洗车指数 -- %s",suggestionBean.getCw().getBrf()));
                baseViewHolder.setText(R.id.tv_suggestion_car_info,suggestionBean.getCw().getTxt());
                break;
        }
    }

    private WeatherChartView getChartView(HeWeather5 weather5){
        WeatherChartView chartView=new WeatherChartView(mContext);
        chartView.setWeather(weather5);
        return chartView;
    }

    private WeatherDetailView getDetailView(HeWeather5 weather5){
        WeatherDetailView detailView=new WeatherDetailView(mContext);
        detailView.setWeather5(weather5);
        return detailView;
    }

}
