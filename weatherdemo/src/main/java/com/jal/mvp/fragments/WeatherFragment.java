package com.jal.mvp.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jal.mvp.adapter.WeatherAdapter;
import com.jal.mvp.entity.HeWeather5;
import com.jal.util.NetUtil;
import com.jal.util.ShareUtil;
import com.jal.util.TTSManager;
import com.jal.util.WeatherUtils;
import com.jal.weatherdemo.MainActivity;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseFragment;
import com.qf.wrglibrary.util.SharedUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;

/**
 * Created by SEELE on 2017/3/6.
 */

public class WeatherFragment extends BaseFragment {
    @Bind(R.id.bannner)
    ImageView bannner;
    @Bind(R.id.tv_city_name)
    TextView tvCityName;
    @Bind(R.id.tv_weather_string)
    TextView tvWeatherString;
    @Bind(R.id.tv_weather_aqi)
    TextView tvWeatherAqi;
    @Bind(R.id.tv_temp)
    TextView tvTemp;
    @Bind(R.id.tv_update_time)
    TextView tvUpdateTime;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.actionbar)
    AppBarLayout appbar;
    @Bind(R.id.rv_weather)
    RecyclerView rvWeather;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private WeatherAdapter adapter;

    private HeWeather5 currentWeather;

    private String province, city, district, street, streetNumber;
    private static final int REQUEST_CODE_PICK_CITY = 233;

    @Bind(R.id.pb)
    ProgressBar pb;

    @Override
    protected int getContentId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void init(View view) {

        pb.setVisibility(View.VISIBLE);
        super.init(view);
        toolbar.setTitle(R.string.menu_weather);

        province = SharedUtil.getString("province");
        city = SharedUtil.getString("city");
        district = SharedUtil.getString("district");
        street = SharedUtil.getString("street");
        streetNumber = SharedUtil.getString("streetNumber");


        ((MainActivity) getActivity()).initDrawer(toolbar);
        toolbar.inflateMenu(R.menu.menu_weather);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_share) {
                    new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                shareWeather();
                            }
                        }
                    });
                    return true;
                } else if (id == R.id.menu_tts) {//语音播报
                    String shareMessage = WeatherUtils.getShareMessage(currentWeather);
                    if (shareMessage.equals("") || shareMessage == null) {
                        Toast.makeText(getContext(), "正在初始化！", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("print", "--------语音-----" + shareMessage);
                        TTSManager.getInstance(getActivity()).speak(shareMessage, null);
                    }
                    return true;
                } else if (id == R.id.menu_more) {
                    startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                            REQUEST_CODE_PICK_CITY);
                    return true;
                }
                return false;
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvWeather.setLayoutManager(manager);
        adapter = new WeatherAdapter(null);
        adapter.openLoadAnimation();
        rvWeather.setAdapter(adapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDatas();
                        swipeContainer.setRefreshing(false);
                    }
                }, 2000);
            }
        });


    }

    @Override
    protected void loadDatas() {
        String province = SharedUtil.getString("province");
        String city = SharedUtil.getString("city");
        String district = SharedUtil.getString("district");
        String street = SharedUtil.getString("street");
        String streetNumber = SharedUtil.getString("streetNumber");
        Log.d("print", province + city + district + street + streetNumber);
        loadData(city);
    }


    public void loadData(String cityName) {

        WeatherUtils.getWeather2(cityName, new WeatherUtils.OnDownListener() {
            @Override
            public void onDownSucc(Object o) {
                List<HeWeather5> weather5s = (List<HeWeather5>) o;
                if(weather5s.get(0).getStatus().equals("unknown city")){
//                    Toast.makeText(getContext(),"未查询到该地天气！",Toast.LENGTH_SHORT).show();
                    loadDatas();
                    return;
                }
                currentWeather = weather5s.get(0);
                pb.setVisibility(View.GONE);
                setWeather(currentWeather);
            }

            @Override
            public void onDownFiled() {

            }
        });
    }

    private void setWeather(HeWeather5 cacheWeather) {
        tvCityName.setText(cacheWeather.getBasic().getCity());
        tvWeatherString.setText(cacheWeather.getNow().getCond().getTxt());
        tvWeatherAqi.setText(cacheWeather.getAqi() == null ? "" : cacheWeather.getAqi().getCity().getQlty());
        tvTemp.setText(String.format("%s℃", cacheWeather.getNow().getTmp()));

        String loc = cacheWeather.getBasic().getUpdate().getLoc();
        String[] split = loc.split(" ");
        String s = split[1];
        tvUpdateTime.setText("截止" + s);

        List<MultiItemEntity> weathers = new ArrayList<>();
        HeWeather5 nowWeather = (HeWeather5) cacheWeather.clone();
        nowWeather.setItemType(HeWeather5.TYPE_NOW);

        weathers.add(nowWeather);

        weathers.add(nowWeather.getSuggestion());

        HeWeather5 dailyWeather = (HeWeather5) cacheWeather.clone();
        dailyWeather.setItemType(HeWeather5.TYPE_DAILYFORECAST);
        weathers.add(dailyWeather);

        adapter.setNewData(weathers);
    }


    private void shareWeather() {
        if (currentWeather != null) {
            ShareUtil.shareText(getContext(), WeatherUtils.getShareMessage(currentWeather));
        } else {
            Toast.makeText(getContext(), "正在加载数据哦！", Toast.LENGTH_SHORT);
            return;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                boolean networkAvailable = NetUtil.isNetworkAvailable();
                if(networkAvailable){
                    loadData(city);
                }else {
                    Toast.makeText(getContext(),"请检查网络是否正常！",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
