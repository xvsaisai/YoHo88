package com.example.saisai.yoho.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by saisai on 2016/8/23.
 */
public class FenLeiViewPager extends ViewPager {

    private float rawX;

    public FenLeiViewPager(Context context) {
        super(context);
    }

    public FenLeiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

       return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
