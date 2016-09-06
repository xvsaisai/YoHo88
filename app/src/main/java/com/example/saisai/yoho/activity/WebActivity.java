package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisai.yoho.R;

public class WebActivity extends AppCompatActivity {

    private android.widget.ImageView ivback;
    private android.widget.ImageView ivzan;
    private android.widget.TextView tvcount;
    private android.widget.ImageView ivlike;
    private android.widget.ImageView ivpinglun;
    private android.widget.ImageView ivfenxiang;
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        this.web = (WebView) findViewById(R.id.web);
        this.ivfenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        this.ivpinglun = (ImageView) findViewById(R.id.iv_pinglun);
        this.ivlike = (ImageView) findViewById(R.id.iv_like);
        this.tvcount = (TextView) findViewById(R.id.tv_count);
        this.ivzan = (ImageView) findViewById(R.id.iv_zan);
        this.ivback = (ImageView) findViewById(R.id.iv_back);

        initListener();


        initWebView();
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        web.loadUrl(url);

    }

    private void initListener() {
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (web.canGoBack()) {
            web.goBack();
            return;
        }
        super.onBackPressed();
    }

    private void initWebView() {

        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);


            }
        });
    }
}
