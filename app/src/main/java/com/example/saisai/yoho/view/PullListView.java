package com.example.saisai.yoho.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.util.DimensUtils;

/**
 * Created by saisai on 2016/8/28.
 */
public class PullListView extends ListView {

    private ImageView headView;
    private ImageView footView;
    private int headHeight;
    private int footHeight;
    private float x;
    private float y;
    private LinearLayout.LayoutParams layoutParams;
    private ObjectAnimator animator;

    public PullListView(Context context) {
        this(context,null);
    }

    public PullListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        headView = new ImageView(getContext());
        AbsListView.LayoutParams headParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        headView.setImageResource(R.drawable.icon_loaing_frame_1);
        headView.measure(0,0);
        headHeight = headView.getMeasuredHeight();
        headView.setPadding(0,-headHeight,0,0);

        headView.setLayoutParams(headParams);
        addHeaderView(headView);

        footView = new ImageView(getContext());
        AbsListView.LayoutParams footParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        footView.setImageResource(R.drawable.icon_loaing_frame_1);
        footView.measure(0,0);
        footHeight = footView.getMeasuredHeight();
        footView.setPadding(0,-footHeight,0,0);

        footView.setLayoutParams(footParams);
        addFooterView(footView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                y = ev.getY();
//                Log.e("tag", y+"");
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY() - y;

//                Log.e("tag", moveY+"");
                int firstVisiblePosition = getFirstVisiblePosition();
                if(moveY>0&&firstVisiblePosition==0){
                    int paddingTop = headView.getPaddingTop();
//                    Log.e("tag","paddingTop==="+paddingTop);
                    headView.setPadding(0, (int) (paddingTop+moveY),0,0);
                    y=ev.getY();
                    return true;
                }

//                int lastVisiblePosition = getLastVisiblePosition();
//                Log.e("tag",lastVisiblePosition+"===="+getCount());

//                if(moveY<0&&){
//
//                }

                y=ev.getY();
                break;
            case MotionEvent.ACTION_UP:

                int paddingTop = headView.getPaddingTop();
                if(paddingTop>-headHeight){
                    if(paddingTop<0){
                        headView.setPadding(0, -headHeight,0,0);
                    }else {
                        headView.setPadding(0,0,0,0);
                        headView.setImageResource(R.drawable.load);
                        if(onPullListener!=null){
                            onPullListener.onSuccess();
//                            onPullListener.onError();
                        }
                    }
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface OnPullListener{
        void onSuccess();
        void onError();
    }
    OnPullListener onPullListener;
    public void setOnPullListener(OnPullListener onPullListener){
        this.onPullListener=onPullListener;
    }

    public void pullSuccess(){
        headView.setPadding(0, -headHeight,0,0);
        headView.setImageResource(R.drawable.icon_loaing_frame_1);

    }

}
