package com.jal.weatherapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SEELE on 2017/2/24.
 */

public class VirtualKeyboardView extends RelativeLayout  {

    Context context;

    private GridView gridView;

    private ArrayList<Map<String, String>> valueList;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        View view =View.inflate(context,R.layout.keyboard_layout, null);

        valueList=new ArrayList<>();

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        setView();

        setupView();

        addView(view);
    }

    private void setView() {
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "清空");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }

            valueList.add(map);

        }
    }

    public GridView getGridView() {
        return gridView;
    }

    public ArrayList<Map<String,String>> getValueList(){
        return valueList;
    }

    private void setupView() {

        KeyboardAdapter keyBoardAdapter = new KeyboardAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
    }
}
