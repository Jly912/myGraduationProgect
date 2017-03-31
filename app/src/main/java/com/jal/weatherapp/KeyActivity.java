package com.jal.weatherapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by SEELE on 2017/2/24.
 */

public class KeyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.et_number)
    EditText etNumber;
    //    @Bind(R.id.keyboard)
    VirtualKeyboardView keyboard;

    private GridView gridView;

    private ArrayList<Map<String, String>> valueList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);
        initView();
        valueList = keyboard.getValueList();
    }


    private void initView() {
        keyboard = (VirtualKeyboardView) findViewById(R.id.keyboard);
        etNumber= (EditText) findViewById(R.id.et_number);
        etTel= (EditText) findViewById(R.id.et_tel);


        gridView = keyboard.getGridView();
        gridView.setOnItemClickListener(this);

        InputMethodManager manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(etTel.getWindowToken(),0);

        int sdkInt = Build.VERSION.SDK_INT;
        Log.d("print", "SDK________" + sdkInt);

        // 设置不调用系统键盘
        if (sdkInt <= 10) {
            etTel.setInputType(InputType.TYPE_NULL);
            etNumber.setInputType(InputType.TYPE_NULL);
        } else {

            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {


                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(etTel, false);
                setShowSoftInputOnFocus.invoke(etNumber, false);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.d("print","onItem-------------------"+position);
            if (position < 11 && position != 9) {//点击0-9按钮
                String tel = etTel.getText().toString().trim();
                tel = tel + valueList.get(position).get("name");

                etTel.setText(tel);

                Editable ea = etTel.getText();
                etTel.setSelection(ea.length());
            } else {
                if (position == 9) {//点击清空
                    etTel.setText("");

                    Editable ea = etTel.getText();
                    etTel.setSelection(ea.length());
                }

                if (position == 11) {//点击back
                    String tel = etTel.getText().toString().trim();
                    if (tel.length() > 0) {
                        tel = tel.substring(0, tel.length() - 1);
                        etTel.setText(tel);

                        Editable ea = etTel.getText();
                        etTel.setSelection(ea.length());
                    }

                }
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("print","onItem-------------------"+position);
        if (position < 11 && position != 9) {//点击0-9按钮
            String tel = etTel.getText().toString().trim();
            tel = tel + valueList.get(position).get("name");

            etTel.setText(tel);

            Editable ea = etTel.getText();
            etTel.setSelection(ea.length());
        } else {
            if (position == 9) {//点击清空
                etTel.setText("");

                Editable ea = etTel.getText();
                etTel.setSelection(ea.length());
            }

            if (position == 11) {//点击back
                String tel = etTel.getText().toString().trim();
                if (tel.length() > 0) {
                    tel = tel.substring(0, tel.length() - 1);
                    etTel.setText(tel);

                    Editable ea = etTel.getText();
                    etTel.setSelection(ea.length());
                }

            }
        }
    }
}
