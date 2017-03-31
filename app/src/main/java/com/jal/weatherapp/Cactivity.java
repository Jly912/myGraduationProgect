package com.jal.weatherapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.qf.wrglibrary.base.BaseActivity;

/**
 * Created by SEELE on 2017/2/27.
 */

public class Cactivity extends BaseActivity {

    Button button;

    @Override
    protected int getContentId() {
        return R.layout.activity_a;
    }

    @Override
    protected void init() {
//        button= (Button) findViewById(R.id.btn_a);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cactivity.this,Aactivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


}
