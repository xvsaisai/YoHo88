package com.example.saisai.yoho.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.BannerBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */
public class MyBanner extends RelativeLayout {

    private ViewPager pager;
    private RelativeLayout.LayoutParams dotGroupParams;
    private LinearLayout dotGroup;

    private List<ImageView> list;
    private List<BannerBean> listData;

    public MyBanner(Context context) {
        this(context,null);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        pager = new ViewPager(getContext());
//        RelativeLayout.LayoutParams layoutParams = (LayoutParams) pager.getLayoutParams();
        addView(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state!=ViewPager.SCROLL_STATE_IDLE){
                    handler.removeCallbacksAndMessages(null);
                }else {
                    handler.sendEmptyMessageDelayed(0,3000);
                }
            }
        });

        dotGroup = new LinearLayout(getContext());
        dotGroup.setOrientation(LinearLayout.HORIZONTAL);
        dotGroup.setGravity(CENTER_VERTICAL);
        dotGroupParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, DimensUtils.dp2px(10));
        dotGroupParams.addRule(ALIGN_PARENT_BOTTOM);
        dotGroupParams.addRule(CENTER_HORIZONTAL);
        dotGroupParams.bottomMargin=DimensUtils.dp2px(20);
        dotGroup.setLayoutParams(dotGroupParams);
        addView(dotGroup);

        list=new ArrayList<>();
        listData=new ArrayList<>();
    }

    private android.os.Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);

            pager.setCurrentItem(pager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };


    public void loadData(String url,String requestBody){

        new HttpUtils().loadData(url,requestBody).setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {
                List<BannerBean> temp = new Gson().fromJson(content, new TypeToken<List<BannerBean>>() {
                }.getType());

                listData.clear();
                listData.addAll(temp);
                inflate();

//                Log.e("tag",content);
            }

            @Override
            public void loadFailed(String msg) {
//                Log.e("tag","failed----------------------"+msg);
            }
        });
    }

    private void selectDot(int position){

        for(int i=0;i<listData.size();i++){
            dotGroup.getChildAt(i).setSelected(false);
            if(i==position%listData.size()){
                dotGroup.getChildAt(i).setSelected(true);
            }
        }
    }

    public void inflate(){
        list.clear();
        for(int i=0;i<listData.size();i++){
            ImageView iv = getIv();
            Picasso.with(getContext()).load(HttpModel.IMGHOST + listData.get(i).getImgpath())
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .fit().into(iv);
            list.add(iv);
        }
        dotGroup.removeAllViews();
        for (int i=0;i<list.size();i++){
            View dot = getDot(i == 0);
            dotGroup.addView(dot);
        }

        pager.setAdapter(new MyAdapter(list));
        pager.setCurrentItem(listData.size()*100);
        handler.sendEmptyMessageDelayed(0,5000);
    }


    public ImageView getIv(){
        ImageView iv=new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return iv;
    }

    public View getDot(boolean isSelect){
        View view=new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DimensUtils.dp2px(10), DimensUtils.dp2px(10));
        layoutParams.rightMargin=DimensUtils.dp2px(10);
        view.setLayoutParams(layoutParams);
        view.setSelected(isSelect);
        view.setBackgroundResource(R.drawable.selector_banner_dot);
        return view;
    }

    static class MyAdapter extends PagerAdapter{

        private List<ImageView> list;
        public MyAdapter(List<ImageView> list){
            this.list=list;
        }
        @Override
        public int getCount() {
//            return list.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
//            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = list.get(position % list.size());
            ViewGroup parent = (ViewGroup) imageView.getParent();
            if(parent!=null){
                parent.removeView(imageView);
            }
            container.addView(imageView);
            return list.get(position%list.size());
        }
    }
}
