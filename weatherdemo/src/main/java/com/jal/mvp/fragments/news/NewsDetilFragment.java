package com.jal.mvp.fragments.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.jal.mvp.adapter.InfoRyAdapter;
import com.jal.mvp.entity.InfoBean;
import com.jal.weatherdemo.InfoDetilActivity;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by SEELE on 2017/3/8.
 * android ios
 */

public class NewsDetilFragment extends BaseFragment {


    @Bind(R.id.ry_news_detail)
    RecyclerView ryNewsDetail;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.pb)
    ProgressBar pb;


    private InfoRyAdapter adapter;

    private static final String BASE_URL = "http://gank.io/api/data/%s/%s/%s";
    private int num = 20;//默认请求数量10条
    private int page = 1;//默认请求页1
    private String type="";

    public static NewsDetilFragment getInstance(String type) {
        NewsDetilFragment fragment = new NewsDetilFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_news_detil;
    }


    @Override
    protected void init(View view) {
        pb.setVisibility(View.VISIBLE);
        adapter = new InfoRyAdapter(getContext());
        ryNewsDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        ryNewsDetail.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData(type);
                        refreshLayout.setRefreshing(false);
                    }
                },2000);

            }
        });
    }

    @Override
    protected void getDatas(Bundle bundle) {
        String type = bundle.getString("type");
        Log.e("print", "type----" + type);
        loadData(type);
    }

    private void loadData(String  type){
        OkHttpUtils.get()
                .url(String.format(BASE_URL,type,num,page))
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
                        String json= (String) response;
                        InfoBean infoBean = new Gson().fromJson(json, InfoBean.class);
                        Log.e("print","---------infobean----"+infoBean);
                        adapter.setData(infoBean.getResults());
                        adapter.setListener(new InfoRyAdapter.InfoListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent=new Intent(getContext(), InfoDetilActivity.class);
                                intent.putExtra("type",adapter.getData().get(position).getType());
                                intent.putExtra("url",adapter.getData().get(position).getUrl());
                                startActivity(intent);
                            }
                        });

                        pb.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        loadDatas();
    }
}
