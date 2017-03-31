package com.jal.weatherapp;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qf.wrglibrary.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by SEELE on 2017/2/27.
 */

public class Aactivity extends BaseActivity {


    @Bind(R.id.rb1)
    RadioButton rb1;
    @Bind(R.id.rb2)
    RadioButton rb2;
//    @Bind(R.id.rb3)
//    RadioButton rb3;
//    @Bind(R.id.rb4)
//    RadioButton rb4;
    @Bind(R.id.rg)
    RadioGroup rg;

    @Override
    protected int getContentId() {
        return R.layout.activity_a;
    }

    @Override
    protected void init() {
        rg.getChildAt(0).performClick();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }



}
