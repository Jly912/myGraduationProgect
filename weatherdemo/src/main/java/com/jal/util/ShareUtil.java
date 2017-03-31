package com.jal.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SEELE on 2017/3/16.
 * 分享工具类
 */

public class ShareUtil {

    //纯文本分享
    public static void shareText(Context context,String shareMsg){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,shareMsg);
        context.startActivity(Intent.createChooser(intent,"分享至"));
    }

    //图片分享
    public static void shareIMG(Context context, Uri uri,String title){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(intent,title));
    }

}
