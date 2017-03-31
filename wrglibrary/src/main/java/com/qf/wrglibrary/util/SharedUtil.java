package com.qf.wrglibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * @author Ken
 */
public class SharedUtil {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     */
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("loginuser", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    //清除个人数据
    public static void cleanSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("loginuser", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
/*
    public void saveDrawable(int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String imageBase64 = new String(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
        editor.putString("P", imageBase64);
        editor.commit();
    }

    public Drawable loadDrawable() {
        String temp = sharedPreferences.getString("P", "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        return Drawable.createFromStream(bais, "");
    }*/

    public static void putSet(Set set) {
        editor.putStringSet("cityInfo", set);
        editor.commit();
    }
}
