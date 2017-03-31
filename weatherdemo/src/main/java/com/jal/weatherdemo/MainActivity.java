package com.jal.weatherdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.jal.event.LocationEvent;
import com.jal.mvp.flush.FlushActivity;
import com.jal.mvp.fragments.NewsFragment;
import com.jal.mvp.fragments.PhotoFragment;
import com.jal.mvp.fragments.VideoFragment;
import com.jal.mvp.fragments.WeatherFragment;
import com.jal.util.DoubleClickExit;
import com.qf.wrglibrary.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Bind(R.id.nav_content)
    NavigationView navContent;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean isOpenStatus() {
        //关闭沉浸式
        return false;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);

        AppContext.addActivity(this);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });

        drawerLayout.closeDrawers();
        showFragment(R.id.fl_zw, new WeatherFragment());
        navContent.setItemTextColor(getBaseContext().getResources().getColorStateList(R.color.selector_menu));
        navContent.setNavigationItemSelectedListener(this);
        navContent.getMenu().getItem(0).setChecked(true);
    }

    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };

            mDrawerToggle.syncState();
            drawerLayout.addDrawerListener(mDrawerToggle);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_weather:
                item.setChecked(true);
                Log.d("print", "点击了天气");
                drawerLayout.closeDrawers();
                showFragment(R.id.fl_zw, new WeatherFragment());
                break;
            case R.id.nav_news:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                showFragment(R.id.fl_zw, new NewsFragment());
                Log.d("print", "点击了资讯");
                break;
            case R.id.nav_photo:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                showFragment(R.id.fl_zw,new PhotoFragment());
                Log.d("print", "点击了美图");
                break;
            case R.id.nav_video:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                showFragment(R.id.fl_zw,new VideoFragment());
                Log.d("print", "点击了视频");
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickExit.check()) {
                Snackbar.make(MainActivity.this.getWindow().getDecorView().findViewById(android.R.id.content), "再按一次退出 App!", Snackbar.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true,priority = 100)
    public void onLocateEvent(LocationEvent event) {
        String msg = event.getmMsg();
        if (msg != null && !msg.equals("")) {
            ArrayList<BaseActivity> activities = AppContext.activities;
            for (int i = 0; i < activities.size(); i++) {
                if (activities.get(i) instanceof FlushActivity) {
                    AppContext.removeActivity(activities.get(i));
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
