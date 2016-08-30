package com.example.saisai.yoho.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by saisai on 2016/8/30.
 */
public class SlidingMenu extends FrameLayout {

    private View menu;
    private View xiangqing;
    private int width;
    private LayoutParams xiangqingParams;
    private LayoutParams menuParams;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {


    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        menu = getChildAt(0);
        xiangqing = getChildAt(1);
        width = xiangqing.getMeasuredWidth() / 5 * 4;

        Log.e("tag", "width===" + width);
        xiangqingParams = (LayoutParams) xiangqing.getLayoutParams();
        menuParams = (LayoutParams) menu.getLayoutParams();
        menuParams.width = width;
        menuParams.leftMargin = (xiangqing.getMeasuredWidth() - width);
        menu.setLayoutParams(menuParams);

    }
}
