package com.du.gout_app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.du.gout_app.R;

public class News_Activity extends Activity {
    private WebView webview;
    private int itemid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        itemid = this.getIntent().getIntExtra("itemid",0);
        webview = (WebView)findViewById(R.id.news_webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://gout.suntao.science/app/news/render/" + itemid);
        //webview.loadUrl("https://gout.suntao.science/app/news/render/" + itemid);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });
        webview.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
                        webview.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

    }
}
