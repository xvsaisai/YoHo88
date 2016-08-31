package com.example.saisai.yoho.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.saisai.yoho.util.MyLog;

/**
 * Created by saisai on 2016/8/31.
 */
public class MyScrollView extends ScrollView {

    private View childAt;
    private int measuredHeight;
    private float rawY;
    private RelativeLayout.LayoutParams params;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        params = (RelativeLayout.LayoutParams) getLayoutParams();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        int height = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getHeight();
//        Rect outRect = new Rect();
//        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
//        int y = outRect.height();
//        MyLog.m("textHeight="+childAt.getHeight()+"===heiht="+(y)+"===L="+l+"===T="+(t+height)+"old1="+oldl+"===oldt="+oldt);

        View view = (View) getChildAt(getChildCount() - 1);
        int d = view.getBottom();
        d -= (getHeight() + getScrollY());
        if (d == 0) {
            isDibu = true;
            //you are at the end of the list in scrollview
            //do what you wanna do here
            MyLog.m("daodibu");
            if (onScrollListener != null) {
                onScrollListener.listener();
            }
        } else
            super.onScrollChanged(l, t, oldl, oldt);

    }

    boolean isDibu;

    public interface OnScrollListener {
        void listener();
    }

    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        MyLog.m("onInterceptTouchEvent=="+ev.getAction());
//        switch (ev.getAction()){
//
//            case MotionEvent.ACTION_DOWN:
//                rawY = ev.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveY = ev.getRawY() - rawY;
//                if(moveY<0&&isDibu){
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        MyLog.m("onTouchEvent==" + ev.getAction());

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                rawY = ev.getRawY();
//                return false;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getRawY() - rawY;
//                if(moveY<0&&isDibu){
                params.topMargin += (int) moveY;
                setLayoutParams(params);
//                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
