package com.jal.mvp.fragments.pics;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.jal.util.ShareUtil;
import com.jal.weatherdemo.AppContext;
import com.jal.weatherdemo.R;
import com.qf.wrglibrary.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import okhttp3.Call;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by SEELE on 2017/3/23.
 */

public class PicFragment extends BaseFragment implements View.OnLongClickListener {

    @Bind(R.id.photo_iv)
    PhotoView photoIv;

    private String url;
    private String name;

    private AlertDialog dialog;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int getContentId() {
        return R.layout.fragment_pic;
    }

    @Override
    protected void init(View view) {
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }


        url = getArguments().getString("url");
        name = getArguments().getString("name");

        photoIv.setOnLongClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_down, null);
        LinearLayout llSave = (LinearLayout) inflate.findViewById(R.id.ll_save);
        LinearLayout llShare = (LinearLayout) inflate.findViewById(R.id.ll_share);
        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                OkHttpUtils.get()
                        .url(url)
                        .build()
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(getContext(), "保存失败！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(Bitmap response, int id) {
                                Log.e("print", "------------开始保存----");
                                savePic(response, name);
                            }
                        });
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareText(getContext(), url);
                dialog.dismiss();
            }
        });

        builder.setView(inflate);
        dialog = builder.create();
    }

    @Override
    protected void loadDatas() {
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .crossFade(0)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(photoIv);
    }

    @Override
    public boolean onLongClick(View v) {
        dialog.show();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    private void savePic(Bitmap bitmap, String name) {

        String path = getContext().getExternalCacheDir().getAbsolutePath() + "/image/";
        Log.e("print", "path+" + path);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            File[] files = dir.listFiles();
            for (File file : files) {
                String name1 = file.getName();
                if (name.equals(name1)) {
                    Toast.makeText(getContext(), "图片已经保存过！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        File file = new File(path + name);
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            outputStream.flush();
            outputStream.close();
            Log.e("print", "-------保存succ----" + file.getName());
            Toast.makeText(getContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            getActivity().sendBroadcast(intent);//通知相册

            MediaStore.Images.Media.insertImage(AppContext.getContext().getContentResolver(), bitmap, name, "");//通知相册
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
