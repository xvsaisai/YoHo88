package com.example.saisai.yoho.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by saisai on 2016/8/22.
 */
public class BaseActivity extends AppCompatActivity {

    public int width;
    public int height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        width=getWindowManager().getDefaultDisplay().getWidth();
        height=getWindowManager().getDefaultDisplay().getHeight();
    }
}
