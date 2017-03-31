package com.jal.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.jal.weatherdemo.AppContext;
import com.jal.weatherdemo.R;

/**
 * Created by SEELE on 2017/2/7.
 */

public class NetUtil {
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = connectivityManager.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return current.isAvailable();
    }


    /**
     *  检查当前网络是否可用
     * @return 是否连接到网络
     */
    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info != null && info.isConnected()){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkErrThenShowMsg(){
        if(!isNetworkAvailable()){
            Toast.makeText(AppContext.getContext(),
                    AppContext.getContext().getString(R.string.network_error),
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }
}
