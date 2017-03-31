package com.jal.mvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jal.mvp.fragments.news.EveryDayFragment;
import com.jal.mvp.fragments.news.NewsDetilFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2017/3/8.
 */

public class MyPagerAdapter extends FragmentPagerAdapter{

    private List<String> titles=new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        for(int i=0;i<titles.length;i++){
            this.titles.add(titles[i]);
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return NewsDetilFragment.getInstance(titles.get(position));
        }
        return new EveryDayFragment();
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
