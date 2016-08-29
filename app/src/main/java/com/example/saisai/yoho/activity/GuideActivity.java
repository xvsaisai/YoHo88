package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.util.DimensUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private List<View> list;
    private int[] imgArr={R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3,R.drawable.guide_4,R.drawable.guide_5};
    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        init();

    }

    private void init() {

        list=new ArrayList<>();

        list.add(getIV(R.drawable.guide_1,false));
        list.add(getIV(R.drawable.guide_2,false));
        list.add(getIV(R.drawable.guide_3,false));
        list.add(getIV(R.drawable.guide_4,false));
        list.add(getIV(R.drawable.guide_5,true));
        MyPagerAdapter adapter=new MyPagerAdapter();
        pager.setAdapter(adapter);

    }

    private View getIV(int guide_1, boolean b) {

        RelativeLayout relativeLayout=new RelativeLayout(this);

        ImageView iv=new ImageView(this);
        iv.setImageResource(guide_1);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        iv.setLayoutParams(layoutParams);
        relativeLayout.addView(iv);
        if(b){
            ImageButton iv2=new ImageButton(this);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(DimensUtils.dp2px(120), DimensUtils.dp2px(40));
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams2.bottomMargin=DimensUtils.dp2px(30);
            layoutParams2.rightMargin=DimensUtils.dp2px(20);
            iv2.setLayoutParams(layoutParams2);
            iv2.setBackgroundResource(R.drawable.selector_guide_iv_enter);
            iv2.setOnClickListener(this);
            relativeLayout.addView(iv2);
        }
        return relativeLayout;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,WelComeActivity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
    }

    class MyPagerAdapter extends PagerAdapter{

//        private List<View> list;
//        public MyPagerAdapter(List<View> list){
//            this.list=list;
//        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(list.get(position));
            return list.get(position);
        }
    }
}
