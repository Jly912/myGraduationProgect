package com.jal.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by SEELE on 2017/2/7.
 */

public class FileUtil {

    //判断SDCard是否存在
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getAppCacheDir(Context context) {
        if (context.getExternalCacheDir() != null && isExistSDCard()) {
            return context.getExternalCacheDir().toString();
        } else {
            return context.getCacheDir().toString();
        }
    }

    public static String getInternalCacheDir(Context context) {
        return context.getCacheDir().toString();
    }

    public static String getExternalCacheDir(Context context) {
        if (context.getExternalCacheDir() != null && isExistSDCard()) {
            return context.getExternalCacheDir().toString();
        } else {
            return null;
        }
    }

    public static boolean delete(String path) {
        return delete(new File(path));
    }

    private static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return file.delete();
            }
            for (File file1 : files) {
                delete(file1);
            }

            return file.delete();
        }
        return false;
    }


}
