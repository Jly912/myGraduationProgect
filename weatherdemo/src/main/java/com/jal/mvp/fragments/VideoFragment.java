package com.jal.mvp.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jal.mvp.adapter.VideoRyAdapter;
import com.jal.mvp.entity.VideoEntity;
import com.jal.weatherdemo.MainActivity;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by SEELE on 2017/3/14.
 */

public class VideoFragment extends BaseFragment {


    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ry_video_detail)
    RecyclerView ryVideoDetail;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private VideoRyAdapter adapter;
    private String url = "http://baobab.kaiyanapp.com/api/v3/video/9724/detail/related?udid=23703f3223004707bfc79975ace0827bb318a229&vc=134&vn=2.6.1&deviceModel=N1&first_channel=eyepetizer_wandoujia_market&last_channel=eyepetizer_wandoujia_market&system_version_code=22";

    @Override
    protected int getContentId() {
        return R.layout.fragment_videos;
    }

    @Override
    protected void init(View view) {

        ((MainActivity)getActivity()).initDrawer(toolbar);
        toolbarTitle.setText(R.string.menu_video);

        ryVideoDetail.setHasFixedSize(true);
        ryVideoDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        ryVideoDetail.setItemAnimator(new DefaultItemAnimator());

        adapter=new VideoRyAdapter(getContext());
        ryVideoDetail.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });

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
                        if(response!=null){
                            String json= (String) response;
                            VideoEntity videoEntity = new Gson().fromJson(json, VideoEntity.class);
                            List<VideoEntity.ItemListBeanX.DataBeanX.ItemListBean> datas=new ArrayList<>();
                            List<VideoEntity.ItemListBeanX> itemList = videoEntity.getItemList();
                            for (int i=0;i<itemList.size();i++){
                                VideoEntity.ItemListBeanX.DataBeanX data = itemList.get(i).getData();
                                List<VideoEntity.ItemListBeanX.DataBeanX.ItemListBean> itemList1 = data.getItemList();
                                datas.addAll(itemList1);
                            }

                            adapter.setData(datas);
                            swipeRefresh.setRefreshing(false);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
