package com.jal.weatherapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.vp)
    ViewPager vp;
    VPAdapter adapter;

    private String[] data={"娱乐","科技","新闻","游戏","音乐"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter=new VPAdapter(getSupportFragmentManager(),data);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
    }

}
