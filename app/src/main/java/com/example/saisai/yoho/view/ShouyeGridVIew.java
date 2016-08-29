package com.example.saisai.yoho.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by saisai on 2016/8/29.
 */
public class ShouyeGridVIew extends GridView {
    public ShouyeGridVIew(Context context) {
        super(context);
    }

    public ShouyeGridVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShouyeGridVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
    }
}
