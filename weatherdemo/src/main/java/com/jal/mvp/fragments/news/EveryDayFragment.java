package com.jal.mvp.fragments.news;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jal.mvp.adapter.InfoRyAdapter;
import com.jal.mvp.entity.InfoBean;
import com.jal.weatherdemo.InfoDetilActivity;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by SEELE on 2017/3/8.
 */

public class EveryDayFragment extends BaseFragment {

    @Bind(R.id.day_ry)
    RecyclerView dayRy;
    @Bind(R.id.day_swipe)
    SwipeRefreshLayout daySwipe;
    private String url = "http://gank.io/api/day/%s/%s/%s";
    private InfoRyAdapter adapter;

    @Bind(R.id.ppb)
    ProgressBar progressBar;

    @Override
    protected int getContentId() {
        return R.layout.fragment_new_day;
    }

    @Override
    protected void init(View view) {
        progressBar.setVisibility(View.VISIBLE);
        dayRy.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new InfoRyAdapter(getContext());
        dayRy.setAdapter(adapter);

        String date = getDate();
        String[] split = date.split("-");
        url = String.format(url, split[0], split[1], split[2]);

        daySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDatas();
                        daySwipe.setRefreshing(false);

                    }
                },2000);
            }
        });
    }

    private String getDate() {
        long l = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date(l));
        return date;
    }


    @Override
    protected void loadDatas() {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        if(response!=null){
                            return response.body().string();
                        }
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        if(null!=response){
                            String json= (String) response;
                            try {
                                JSONObject jsonObject=new JSONObject(json);
                                JSONArray category = jsonObject.getJSONArray("category");
                                List<String> types=new ArrayList<>();
                                for(int i=0;i<category.length();i++){
                                    String type = category.getString(i);
                                    types.add(type);
                                }

                                JSONObject result = jsonObject.getJSONObject("results");
                                List<InfoBean.ResultsBean> data=new ArrayList<>();
                                for (int i = 0; i < types.size(); i++) {
                                    JSONArray jsonArray = result.getJSONArray(types.get(i));
                                    TypeToken<List<InfoBean.ResultsBean>> tt=new TypeToken<List<InfoBean.ResultsBean>>(){};
                                    List<InfoBean.ResultsBean> resultsBeen = new Gson().fromJson(jsonArray.toString(), tt.getType());
                                    data.addAll(resultsBeen);
                                }

                                adapter.setData(data);
                                adapter.setListener(new InfoRyAdapter.InfoListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent=new Intent(getContext(), InfoDetilActivity.class);
                                        intent.putExtra("url",adapter.getData().get(position).getUrl());
                                        intent.putExtra("type",adapter.getData().get(position).getType());
                                        startActivity(intent);
                                    }
                                });
                                progressBar.setVisibility(View.GONE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        loadDatas();
    }
}
