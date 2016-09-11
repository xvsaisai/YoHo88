package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;

public class ChooseActivity extends AppCompatActivity {


    private android.widget.ImageButton ib1;
    private android.widget.ImageButton ib2;
    private android.widget.ImageButton ib3;
    private android.widget.ImageButton ib4;
    private android.widget.RelativeLayout rootchoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        this.rootchoose = (RelativeLayout) findViewById(R.id.root_choose);
        this.ib4 = (ImageButton) findViewById(R.id.ib_4);
        this.ib3 = (ImageButton) findViewById(R.id.ib_3);
        this.ib2 = (ImageButton) findViewById(R.id.ib_2);
        this.ib1 = (ImageButton) findViewById(R.id.ib_1);

        rootchoose.setBackgroundResource(R.drawable.home_banner);

    }


    public void start(View view){
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.main_activity_in,R.anim.choose_activity_out);

    }
}
