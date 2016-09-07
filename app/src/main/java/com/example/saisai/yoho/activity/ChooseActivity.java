package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.saisai.yoho.R;

public class ChooseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

    }


    public void start(View view){
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.main_activity_in,R.anim.choose_activity_out);

    }
}
