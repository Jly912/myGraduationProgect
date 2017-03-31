package com.qf.wrglibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by 翁 on 2016/12/14.
 */

public class Network {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    //Toast.makeText(context, "当前有网络", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (info.getState() == NetworkInfo.State.DISCONNECTED) {
                    Toast.makeText(context, "当前没有网络，请连接网络。", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        return false;
    }
}
