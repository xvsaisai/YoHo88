package com.example.saisai.yoho.fragment.fenlei_pinlei;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.fenlei_pinlei.FenleiPinleiChildLVAdapter;
import com.example.saisai.yoho.adapter.fenlei_pinlei.FenleiPinleiLVAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.bean.PinLeiBean;
import com.example.saisai.yoho.bean.PinLeiIChildtemBean;
import com.example.saisai.yoho.util.DimensUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saisai on 2016/8/24.
 */
public class FenleiPinleiBaseFrament extends BaseFrament {
    @Bind(R.id.lv_pinlei)
    ListView lvPinlei;
    @Bind(R.id.iv_move)
    ImageView ivMove;
    @Bind(R.id.lv_xiangqing)
    ListView lvXiangqing;
    public List<PinLeiBean> list;
    @Bind(R.id.linear)
    LinearLayout linear;
    private int width;
    private ObjectAnimator animator;

    private int currentPosition=-1;
    private FenleiPinleiLVAdapter adapter;
    public List<String> childList;
    public FenleiPinleiChildLVAdapter childLVAdapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

        View inflate = inflater.inflate(R.layout.fragment_fenlei_pinlei_child, null);
        ButterKnife.bind(this, inflate);
        width = activity.getWindowManager().getDefaultDisplay().getWidth();

        initXiangqing();
        initAnimator();
        initListener();
        return inflate;
    }

    private void initListener() {

        lvPinlei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Log.e("tag",linear.getTranslationX()+"");
                ivMoveTranslate(position);

                if(currentPosition>=0&&currentPosition!=position){
//                    openLV();
                    requestChidData();
                    currentPosition=position;
                }else if(currentPosition==-1){
                    openLV();
                    requestChidData();
                    currentPosition=position;

                }else {//关闭
                    closeLV();
                    currentPosition=-1;
                }
            }
        });
    }

    public void requestChidData(){

    }

    private void openLV() {

        animator.cancel();
        animator.setFloatValues(width/2,0);
        animator.start();
    }

    private void closeLV() {

        animator.cancel();
        animator.setFloatValues(0,width/2);
        animator.start();
    }

    private void ivMoveTranslate(int position) {

        int firstVisiblePosition = lvPinlei.getFirstVisiblePosition();
        int i = position - firstVisiblePosition;
        View childAt = lvPinlei.getChildAt(position);
        int top = childAt.getTop();
        int moveToY = top + childAt.getHeight() / 2 - ivMove.getHeight() / 2;
        ivMove.setTranslationY(moveToY);
    }

    private void initAnimator() {

        animator = ObjectAnimator.ofFloat(linear,"translationX",0,0);
        animator.setDuration(1000);

    }

    private void initXiangqing() {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linear.getLayoutParams();
        layoutParams.width=width/2;
        linear.setLayoutParams(layoutParams);
        linear.setTranslationX(width/2);
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) lvXiangqing.getLayoutParams();
        layoutParams1.leftMargin= -DimensUtils.dp2px(7);
        lvXiangqing.setLayoutParams(layoutParams1);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        childList = new ArrayList<>();
    }

    @Override
    public void initAdapter() {
        super.initAdapter();
        adapter = new FenleiPinleiLVAdapter(list, activity);
        lvPinlei.setAdapter(adapter);

        childLVAdapter = new FenleiPinleiChildLVAdapter(childList,activity);
        lvXiangqing.setAdapter(childLVAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
