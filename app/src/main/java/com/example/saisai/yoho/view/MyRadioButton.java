package com.example.saisai.yoho.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.util.DimensUtils;

/**
 * Created by saisai on 2016/8/23.
 */
public class MyRadioButton extends RadioButton {

    private Paint paintCircle;
    private Paint paintText;
    private int redDotBGColor;
    private int redDotTextColor;
    private int redDotTextSize;
    private int redDotTextNum;
    private int redDotRedus;
    private Rect rect;
    private float v;

    public MyRadioButton(Context context) {
        this(context,null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
        redDotRedus = typedArray.getDimensionPixelSize(R.styleable.MyRadioButton_redDotRedus, DimensUtils.dp2px(10));
        redDotBGColor = typedArray.getColor(R.styleable.MyRadioButton_redDotBGColor, Color.RED);
        redDotTextColor = typedArray.getColor(R.styleable.MyRadioButton_redDotTextColor, Color.WHITE);
        redDotTextSize = typedArray.getDimensionPixelSize(R.styleable.MyRadioButton_redDotTextSize, DimensUtils.dp2px(16));
        redDotTextNum = typedArray.getInt(R.styleable.MyRadioButton_redDotTextNum, -1);
        typedArray.recycle();//释放
        init();
    }

    private void init() {

        paintCircle = new Paint();
        paintCircle.setColor(redDotBGColor);
        paintCircle.setAntiAlias(true);//设置抗锯齿
        paintCircle.setStyle(Paint.Style.FILL);//设置填充

        paintText = new Paint();
        paintText.setColor(redDotTextColor);
        paintText.setAntiAlias(true);
        paintText.setTextSize(redDotTextSize);
        rect = new Rect();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (redDotTextNum > 0) {
            canvas.drawCircle(getWidth()/4*3,getHeight()/4,redDotRedus,paintCircle);
            v = paintText.measureText(redDotTextNum + "", 0, (redDotTextNum + "").length());
            paintText.getTextBounds(redDotTextNum + "",0, (redDotTextNum + "").length(),rect);
            canvas.drawText(redDotTextNum + "",0,(redDotTextNum + "").length(),getWidth()/4*3-v/2,getHeight()/4+rect.height()/2,paintText);
        }
    }

    public void setRedDotTextNum(int num) {
        redDotTextNum = num;
    }

    public int getRedDotTextNum() {
        return redDotTextNum;
    }

//    public void setRedDotTextSize(int size){
//        this.redDotTextSize=size;
//    }
}
