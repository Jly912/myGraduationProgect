package com.jal.mvp.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jal.mvp.adapter.MyPagerAdapter;
import com.jal.weatherdemo.MainActivity;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by SEELE on 2017/3/8.
 */

public class NewsFragment extends BaseFragment {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.newVp)
    ViewPager newVp;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MyPagerAdapter adapter;

    private static String[] typeStr={"每日推荐","all","Android","iOS","拓展资源","前端"};

    @Override
    protected int getContentId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void init(View view) {
        ((MainActivity) getActivity()).initDrawer(toolbar);
        adapter=new MyPagerAdapter(getFragmentManager(),typeStr);
        newVp.setAdapter(adapter);
        tabs.setupWithViewPager(newVp);
        toolbarTitle.setText(R.string.menu_news);
    }


}
