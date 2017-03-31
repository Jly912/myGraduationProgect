package com.jal.weatherapp;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by SEELE on 2017/2/24.
 */

public class KeyboardAdapter extends BaseAdapter  {

    private Context context;

    private ArrayList<Map<String, String>> data;

    private OnItemListener listener;

    public KeyboardAdapter(Context context,ArrayList<Map<String,String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(OnItemListener listener){
        this.listener=listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.grid_item_virtual_keyboard, null);
            holder.tv = (TextView) convertView.findViewById(R.id.btn_keys);
            holder.ivDelete = (RelativeLayout) convertView.findViewById(R.id.imgDelete);

            convertView.setTag(holder);
        }

        if (position == 9) {//清空
            holder.ivDelete.setVisibility(View.GONE);
            holder.tv.setVisibility(View.VISIBLE);
            holder.tv.setText(data.get(position).get("name"));
            holder.tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 26);

        } else if (position == 11) {//删除
            holder.tv.setBackgroundResource(R.mipmap.keyboard_delete_img);
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.tv.setVisibility(View.GONE);

        } else {
            holder.ivDelete.setVisibility(View.GONE);
            holder.tv.setVisibility(View.VISIBLE);
            holder.tv.setText(data.get(position).get("name"));
        }

        if(listener!=null){
            listener.onItemClick(position);
        }

        return convertView;
    }

    public static class ViewHolder {
        private Button btnKey;
        private TextView tv;
        private RelativeLayout ivDelete;
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

}
