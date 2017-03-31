package com.jal.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2017/2/23.
 */

public class VPAdapter extends FragmentPagerAdapter {

    private List<String> title=new ArrayList<>();

    public VPAdapter(FragmentManager fm, String[] data) {
        super(fm);
        for (int i=0;i<data.length;i++){
            title.add(data[i]);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return title.size();
    }
}
