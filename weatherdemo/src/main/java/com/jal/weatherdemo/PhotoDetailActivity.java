package com.jal.weatherdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jal.mvp.adapter.PciFragmentAdapter;
import com.jal.mvp.fragments.pics.PicFragment;
import com.qf.wrglibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by SEELE on 2017/3/14.
 */

public class PhotoDetailActivity extends BaseActivity {

    @Bind(R.id.picturePager)
    ViewPager picturePager;
    PciFragmentAdapter adapter;

    private List<String> images = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_detail_photo;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        images = (List<String>) intent.getSerializableExtra("list");
        int index = intent.getIntExtra("index", -1);
        names = (List<String>) intent.getSerializableExtra("name");
        adapter = new PciFragmentAdapter(getSupportFragmentManager());

        for (int i = 0; i < images.size(); i++) {
            PicFragment fragment = new PicFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", images.get(i));
            bundle.putString("name",names.get(i));
            fragment.setArguments(bundle);
            adapter.addFragment(fragment);
        }

        picturePager.setAdapter(adapter);
        picturePager.setCurrentItem(index);
        picturePager.setOffscreenPageLimit(picturePager.getAdapter().getCount());
    }

}
