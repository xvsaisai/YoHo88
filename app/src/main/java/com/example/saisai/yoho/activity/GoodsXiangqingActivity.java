package com.example.saisai.yoho.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.view.MyScrollView;

public class GoodsXiangqingActivity extends AppCompatActivity {

    private MyScrollView sc;
    private android.widget.ListView lv;
    private RelativeLayout.LayoutParams scParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_xiangqing);
        this.lv = (ListView) findViewById(R.id.lv);
        this.sc = (MyScrollView) findViewById(R.id.sc);
        scParams = (RelativeLayout.LayoutParams) sc.getLayoutParams();
        sc.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void listener() {
//                scParams.topMargin=;
            }
        });
    }
}
