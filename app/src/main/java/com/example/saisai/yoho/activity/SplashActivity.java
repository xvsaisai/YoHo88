package com.example.saisai.yoho.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.base.BaseAnimatorListener;
import com.example.saisai.yoho.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.iv)
    ImageView iv;
    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initAnimator();
    }

    private void initAnimator() {

        animator = ObjectAnimator.ofFloat(iv,"translationY",-height,0);
        animator.setDuration(2000);
        animator.setInterpolator(new BounceInterpolator());
        animator.addListener(new BaseAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animator) {

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        String isFirst = SPUtils.get("isFirst");
                        if(!isFirst.equals("true")){
                            startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                        }else {
                            startActivity(new Intent(SplashActivity.this,WelComeActivity.class));
                        }
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                },500);

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){//是否获得焦点，获得焦点才执行
            animator.start();
            Log.e("tag","hasfocus");
        }

    }
}
