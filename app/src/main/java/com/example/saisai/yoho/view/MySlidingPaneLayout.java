package com.example.saisai.yoho.view;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.saisai.yoho.util.MyLog;

/**
 * Created by saisai on 2016/8/23.
 */
public class MySlidingPaneLayout extends SlidingPaneLayout {

    private float rawX;

    public MySlidingPaneLayout(Context context) {
        this(context,null);
    }

    public MySlidingPaneLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawX=ev.getRawX();
                isInter=false;
                break;
            case MotionEvent.ACTION_MOVE:
                float v = ev.getRawX() - rawX;
                if(v>0){
                    isInter=true;
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isInter)
            return false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawX = ev.getRawX();
                isInter=false;
                break;
            case MotionEvent.ACTION_MOVE:
                float v = ev.getRawX() - rawX;
                if(v>0&&!isOpen()){
                    isInter=true;
                    return false;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    boolean isInter;
}
