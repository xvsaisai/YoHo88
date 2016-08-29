package com.example.saisai.yoho.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.base.BaseAnimationListener;
import com.example.saisai.yoho.base.BaseAnimatorListener;
import com.example.saisai.yoho.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelComeActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.tv_jump)
    TextView tvJump;
    @Bind(R.id.view)
    View view;
    private AlphaAnimation animation;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SPUtils.save("isFirst","true");
        setContentView(R.layout.activity_wel_come);
        ButterKnife.bind(this);

        Log.e("tag","===========");
        view.post(new Runnable() {
            @Override
            public void run() {
                init();
                view.startAnimation(animation);
            }
        });
    }

    private void init() {

        iv.setScaleX(1.5f);
        iv.setScaleY(1.5f);
        tvJump.setOnClickListener(this);
        initAnimattion();
        initAnimator();
    }


    private void initAnimator() {

        animator = ValueAnimator.ofFloat(1.5f,1);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                iv.setScaleX(animatedValue);
                iv.setScaleY(animatedValue);
            }
        });
        animator.addListener(new BaseAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelComeActivity.this,ChooseActivity.class));
                        finish();
                    }
                }, 1000);
            }
        });
    }

    private void initAnimattion() {

        animation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.welcome_bg_alpha);
        animation.setFillAfter(true);
        animation.setDuration(1000);
        animation.setAnimationListener(new BaseAnimationListener(){
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                view.clearAnimation();
                view.setVisibility(View.GONE);
                animator.start();
            }
        });
    }

    @Override
    public void onClick(View view) {
//        animator.
        animator.removeAllListeners();
        startActivity(new Intent(this,ChooseActivity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
    }
}
