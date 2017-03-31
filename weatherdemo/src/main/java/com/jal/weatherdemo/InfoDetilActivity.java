package com.jal.weatherdemo;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jal.util.ShareUtil;
import com.qf.wrglibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SEELE on 2017/3/9.
 */

public class InfoDetilActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.webLayout)
    WebView webLayout;
    @Bind(R.id.actionbar)
    Toolbar toolbar;

    @Bind(R.id.progress)
    ProgressBar pb;

    private String url="";

    @Override
    protected int getContentId() {
        return R.layout.activity_info_detil;
    }

    @Override
    protected void init() {
        pb.setVisibility(View.VISIBLE);
        WebSettings settings = webLayout.getSettings();
        settings.setJavaScriptEnabled(true);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        url = intent.getStringExtra("url");
        tvTitle.setText(type);
        webLayout.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pb.setVisibility(View.GONE);
            }
        });

        webLayout.loadUrl(url);
    }


        @Override
    public boolean isOpenStatus() {
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webLayout.canGoBack()) {
            webLayout.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                ShareUtil.shareText(this,url);
                break;
        }
    }
}
