package com.example.saisai.yoho.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.saisai.yoho.R;

/**
 * Created by saisai on 2016/8/23.
 */
public class GouWuCheActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouwuche);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.main_activity_stail,R.anim.gouwuche_activity_out);
    }
}
