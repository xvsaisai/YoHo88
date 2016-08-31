package com.example.saisai.yoho.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;

/**
 * Created by saisai on 2016/8/31.
 */
public class PullLoadGridView extends RelativeLayout {

    private ImageView headView;
    private LayoutParams headParams;
    private int headHeight;
    private ImageView footView;
    private LayoutParams footParams;
    private int footHeight;
    private GridView gv;
    private LayoutParams gvParams;
    private float rawY;

    public PullLoadGridView(Context context) {
        this(context, null);
    }

    public PullLoadGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullLoadGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        headView = new ImageView(getContext());
        headView.setImageResource(R.drawable.icon_loaing_frame_1);
        headView.setId(R.id.pull_load_head);
        headParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        headView.measure(0, 0);
        headHeight = headView.getMeasuredHeight();
        headParams.topMargin = -headHeight;
        headView.setLayoutParams(headParams);

        footView = new ImageView(getContext());
        footView.setImageResource(R.drawable.icon_loaing_frame_1);
        footView.setId(R.id.pull_load_foot);
        footParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        footView.measure(0, 0);

        footHeight = footView.getMeasuredHeight();
        footParams.bottomMargin = -footHeight;
        footParams.addRule(ALIGN_PARENT_BOTTOM);
        footView.setLayoutParams(footParams);

        gv = new GridView(getContext());
        gv.setNumColumns(2);
        gvParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        gvParams.addRule(BELOW, R.id.pull_load_head);
        gvParams.addRule(ABOVE, R.id.pull_load_foot);
        gv.setLayoutParams(gvParams);

        addView(headView);
        addView(footView);
        addView(gv);

        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    public void setAdapter(BaseAdapter adapter) {
        gv.setAdapter(adapter);
    }


    public interface OnScrollListener {
        void onScrollStateChanged(AbsListView view, int scrollState);

        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                rawY = ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:

                float moveY = ev.getRawY() - rawY;
//
                if (moveY > 0 && gv.getFirstVisiblePosition() == 0 && gv.getTop() >= 0) {
                    rawY = ev.getRawY();
                    return true;
                }

                int firstVisiblePosition = gv.getFirstVisiblePosition();

//                MyLog.log("tag",lastVisiblePosition+"==============lastVisiblePosition");
                if (moveY < 0 && gv.getLastVisiblePosition() == gv.getAdapter().getCount() - 1 && gv.getChildAt(gv.getAdapter().getCount() - firstVisiblePosition - 1).getBottom() <= gv.getHeight()) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                rawY = event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:

                float moveY = event.getRawY() - rawY;
                if (moveY > 0 && gv.getFirstVisiblePosition() == 0 && gv.getTop() >= 0) {

                    headParams.topMargin = (int) (-headHeight + moveY);
                    headView.setLayoutParams(headParams);
                }

                int firstVisiblePosition = gv.getFirstVisiblePosition();
//                MyLog.m(gv.getChildAt(gv.getAdapter().getCount()-firstVisiblePosition-1).getBottom()+"===="+gv.getHeight()+"");

                if (moveY < 0 && gv.getLastVisiblePosition() == gv.getAdapter().getCount() - 1) {
                    footParams.bottomMargin = (int) (-footHeight - moveY);
                    footView.setLayoutParams(footParams);
                    gvParams.topMargin = (int) moveY;
                    gv.setLayoutParams(gvParams);
                }
                break;
            case MotionEvent.ACTION_UP:

                if (headParams.topMargin >= 0) {
                    headView.setImageResource(R.drawable.load);
                    headParams.topMargin = 0;
                    headView.setLayoutParams(headParams);
                    if (onPullDownListener != null) {
                        onPullDownListener.pullDown();
                    }
                } else {
                    headView.setImageResource(R.drawable.icon_loaing_frame_1);
                    headParams.topMargin = -headHeight;
                    headView.setLayoutParams(headParams);
                }

                if (footParams.bottomMargin >= 0) {
                    footView.setImageResource(R.drawable.load);
                    footParams.bottomMargin = 0;
                    footView.setLayoutParams(footParams);
                    gvParams.topMargin = -footHeight;
                    gv.setLayoutParams(gvParams);
                    if (onPullUpListener != null) {
                        onPullUpListener.pullUp();
                    }
                } else {
                    footView.setImageResource(R.drawable.icon_loaing_frame_1);
                    footParams.bottomMargin = -footHeight;
                    footView.setLayoutParams(footParams);
                    gvParams.topMargin = 0;
                    gv.setLayoutParams(gvParams);
                }
                break;
        }
        return true;
    }


    public interface OnPullDownListener {
        void pullDown();
    }

    private OnPullDownListener onPullDownListener;

    public void setOnPullDownListener(OnPullDownListener onPullDownListener) {
        this.onPullDownListener = onPullDownListener;
    }

    public interface OnPullUpListener {
        void pullUp();
    }

    private OnPullUpListener onPullUpListener;

    public void setOnPullUpListener(OnPullUpListener onPullUpListener) {
        this.onPullUpListener = onPullUpListener;
    }


    public void pullDownSuccess() {

        headView.setImageResource(R.drawable.icon_loaing_frame_1);
        headParams.topMargin = -headHeight;
        headView.setLayoutParams(headParams);
    }

    public void pullUpSuccess() {
        footView.setImageResource(R.drawable.icon_loaing_frame_1);
        footParams.bottomMargin = -footHeight;
        footView.setLayoutParams(footParams);

        gvParams.topMargin = 0;
        gv.setLayoutParams(gvParams);
    }


}
