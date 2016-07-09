package com.uws.campus_app.impl.activities;

import com.uws.campus_app.R;
import com.uws.campus_app.UwsCampusApp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {
    private static String url;
    private WebView webView;

    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_library);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main);

        webView = new WebView(UwsCampusApp.getAppContext());
        webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setInitialScale(50);

        if(UwsCampusApp.isNetworkConnected()) {
            webView.loadUrl(url);
        } else {
            String customHtml = "<html><body><h1>You need an internet connection to view this page </h1></body></html>";
            webView.loadData(customHtml, "text/html", "UTF-8");
        }
        
        layout.addView(webView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
             startActivity(new Intent(getBaseContext(), UtilitiesActivity.class));
             overridePendingTransition(R.anim.in, R.anim.out);
             return true;
         }
         return super.onKeyDown(keyCode, event);
    }
}
