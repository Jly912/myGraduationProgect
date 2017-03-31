package com.jal.mvp;

import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jal.weatherapp.R;
import com.qf.wrglibrary.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by SEELE on 2017/2/28.
 */

public class WebActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView web;

    private final static String URL = "http://192.168.1.8:8080/#/dingdan/evaluate";

    @Override
    protected int getContentId() {
        return R.layout.web_activity;
    }

    @Override
    public boolean isOpenStatus() {
        return false;
    }

    @Override
    protected void init() {
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(URL);
                return true;
            }
        });

        web.setWebChromeClient(new MyWebChromeClient());
        web.addJavascriptInterface(new MyJavaScriptInterface(), "demo");
        web.loadUrl(URL);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    final class MyJavaScriptInterface {
        MyJavaScriptInterface() {
        }

        @JavascriptInterface
        public void clickAndroid() {

        }
    }

    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
    }

}
