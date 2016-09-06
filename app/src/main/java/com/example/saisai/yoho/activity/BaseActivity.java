package com.example.saisai.yoho.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.saisai.yoho.dialog.LoadDialog;

/**
 * Created by saisai on 2016/8/22.
 */
public class BaseActivity extends AppCompatActivity {

    public int width;
    public int height;
    private LoadDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        width=getWindowManager().getDefaultDisplay().getWidth();
        height=getWindowManager().getDefaultDisplay().getHeight();
    }

    public void showLoadDialog() {
        dialog = new LoadDialog(this);
        dialog.show();
    }

    public void cancelLoadDialog() {
        dialog.dismiss();
    }

    public void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

}
