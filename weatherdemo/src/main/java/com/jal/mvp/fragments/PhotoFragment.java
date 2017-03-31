package com.jal.mvp.fragments;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jal.http.api.PhotoController;
import com.jal.mvp.adapter.PhotoRyAdapter;
import com.jal.mvp.entity.PhotoEntity;
import com.jal.weatherdemo.MainActivity;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseFragment;

import java.util.List;

import butterknife.Bind;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jal.http.RetrofitManager.gson;

/**
 * Created by SEELE on 2017/3/14.
 */

public class PhotoFragment extends BaseFragment {


    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.photo_ry)
    RecyclerView photoRy;
    private PhotoRyAdapter adapter;

    private int size = 20;
    private int startPage = 1;

    private static final String BASE_URL = "http://gank.io/api/";

    private boolean isLoading = false;

    private Retrofit retrofit;

    @Override
    protected int getContentId() {
        return R.layout.fragmen_photo;
    }

    @Override
    protected void init(View view) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ((MainActivity) getActivity()).initDrawer(toolbar);
        toolbarTitle.setText(R.string.menu_photo);
        photoRy.setHasFixedSize(true);
        photoRy.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        photoRy.setItemAnimator(new DefaultItemAnimator());
        photoRy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null);

                int childCount = layoutManager.getChildCount();
                int itemCount = layoutManager.getItemCount();
                if (!isLoading && childCount > 0 && (newState == RecyclerView.SCROLL_STATE_IDLE)
                        && ((lastVisibleItemPositions[0] >= itemCount - 1) ||
                        (lastVisibleItemPositions[1] >= itemCount - 1))) {
                    isLoading = true;//加载状态设置成true
                    startPage++;//页面加一
                    loadData(size,startPage);
                    adapter.showFooter();//显示底部加载动画
                    photoRy.scrollToPosition(adapter.getItemCount()-1);
                }
            }
        });

        adapter = new PhotoRyAdapter(getContext());
        photoRy.setAdapter(adapter);
    }

    @Override
    protected void loadDatas() {
        loadData(size,startPage);
    }

    public void loadData(int size, int startPage) {
        retrofit.create(PhotoController.class).getPhotoList(size, startPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PhotoEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getContext(),"加载完成！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PhotoEntity photoEntity) {
                        if (photoEntity != null) {
                            List<PhotoEntity.ResultsBean> results = photoEntity.getResults();
                            if(adapter.getData().size()==0){
                                adapter.setData(results);

                            }else {
                                adapter.addData(results);
                                isLoading=false;
                                adapter.hideFooter();
                            }

                        }
                    }
                });
    }

}
