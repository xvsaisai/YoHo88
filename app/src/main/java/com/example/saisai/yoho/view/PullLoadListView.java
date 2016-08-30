package com.example.saisai.yoho.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.util.DimensUtils;

import java.util.List;

/**
 * Created by saisai on 2016/8/29.
 */
public class PullLoadListView extends RelativeLayout {


    private ImageView headView;
    private LayoutParams headPramas;
    private ImageView footView;
    private LayoutParams footParams;
    public ListView lv;
    private LayoutParams lvParams;
    private int headHeight;
    private int footHeight;
    private float y;

    public PullLoadListView(Context context) {
        this(context,null);
    }

    public PullLoadListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullLoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {


        headView =new ImageView(getContext());
        headView.setImageResource(R.drawable.icon_loaing_frame_1);
        headPramas = new LayoutParams(LayoutParams.MATCH_PARENT, DimensUtils.dp2px(20));
        headPramas.addRule(CENTER_HORIZONTAL);
        headView.measure(0,0);
        headHeight = headView.getMeasuredHeight();
        headPramas.topMargin=-headHeight;
        headView.setLayoutParams(headPramas);
        headView.setId(R.id.pull_load_head);

        footView = new ImageView(getContext());
        footView.setImageResource(R.drawable.icon_loaing_frame_1);
        footParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        footParams.addRule(ALIGN_PARENT_BOTTOM);
        footView.measure(0,0);
        footHeight = footView.getMeasuredHeight();
        footParams.bottomMargin=-footHeight;
        footView.setLayoutParams(footParams);
        footView.setId(R.id.pull_load_foot);


        lv = new ListView(getContext());
        lvParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lvParams.addRule(BELOW,R.id.pull_load_head);
        lvParams.addRule(ABOVE,R.id.pull_load_foot);
        lv.setLayoutParams(lvParams);

        addView(headView);
        addView(footView);
        addView(lv);
    }

    public void setAdapter(BaseAdapter adapter){
        lv.setAdapter(adapter);
    }

    public void addHeadView(View head){
        lv.addHeaderView(head);
    }
    public void addFootView(View foot){
        lv.addFooterView(foot);
    }

    public int getFirstVisibilePosition(){
        return lv.getFirstVisiblePosition();
    }
    public void setSelection(int position){
        lv.setSelection(position);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                y=ev.getY();
                if(isPull||isLoad){
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY() - y;
                if(moveY>0&&lv.getFirstVisiblePosition()==0){
                    View childAt = lv.getChildAt(0);
                    if(childAt.getTop()>=0){
                        return true;
                    }
                }
                Log.e("tag",lv.getLastVisiblePosition()+"===="+(lv.getAdapter().getCount()-1));
                if(moveY<0&&lv.getLastVisiblePosition()==lv.getAdapter().getCount()-1){
                    int i = lv.getCount() - lv.getFirstVisiblePosition();
                    View childAt = lv.getChildAt(i - 1);
                    if(childAt.getBottom()<=lv.getMeasuredHeight()){
                        Log.e("tag","true");
                        return true;
                    }
                }

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:

                if(isPull||isLoad){
                    break;
                }
                float moveY = event.getY() - y;
                if(moveY>0&&lv.getFirstVisiblePosition()==0){
                    headPramas.topMargin= (int) (-headHeight+moveY);
                    headView.setLayoutParams(headPramas);
                }
                if(moveY<0&&lv.getLastVisiblePosition()==lv.getAdapter().getCount()-1){
                    footParams.bottomMargin= (int) (-footHeight-moveY);
                    footView.setLayoutParams(footParams);
                    lvParams.topMargin= (int) moveY;
                    lv.setLayoutParams(lvParams);
                }

                break;

            case MotionEvent.ACTION_UP:

                if(headPramas.topMargin>=0){
                    isPull=true;
                    headPramas.topMargin=0;
                    headView.setLayoutParams(headPramas);
                    headView.setImageResource(R.drawable.load);
                    if(onPullOrLoadListener!=null){
                        onPullOrLoadListener.pull();
                    }
                }else {
                    headPramas.topMargin=-headHeight;
                    headView.setLayoutParams(headPramas);
                }

                if(footParams.bottomMargin>=0){
                    isLoad=true;
                    footParams.bottomMargin=0;
                    footView.setLayoutParams(footParams);
                    footView.setImageResource(R.drawable.load);
                    if(onPullOrLoadListener!=null){
                        onPullOrLoadListener.load();
                    }
                }else {
                    footParams.bottomMargin=-footHeight;
                    footView.setLayoutParams(footParams);
                }
                break;
        }

        return true;
    }


    boolean isPull;
    boolean isLoad;

    public void pullSuccess(){
        isPull=false;
        headView.setImageResource(R.drawable.icon_loaing_frame_1);
        headPramas.topMargin=-headHeight;
        headView.setLayoutParams(headPramas);
    }

    public void loadSuccess(){
        isLoad=false;
        footView.setImageResource(R.drawable.icon_loaing_frame_1);
        footParams.bottomMargin=-footHeight;
        footView.setLayoutParams(footParams);
        lvParams.topMargin=0;
        lv.setLayoutParams(lvParams);
    }

    public interface OnPullOrLoadListener{
        void pull();
        void load();
    }

    private OnPullOrLoadListener onPullOrLoadListener;
    public void setOnPullOrLoadListener(OnPullOrLoadListener onPullOrLoadListener){
        this.onPullOrLoadListener=onPullOrLoadListener;
    }
}
