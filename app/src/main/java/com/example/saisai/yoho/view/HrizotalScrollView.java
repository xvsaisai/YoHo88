package com.example.saisai.yoho.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.saisai.yoho.MyAdapter;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.HotBannerBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */
public class HrizotalScrollView extends LinearLayout {

    private TextView tv;
    private RecyclerView recyclerView;

    public HrizotalScrollView(Context context) {
        this(context,null);
    }

    public HrizotalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HrizotalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
            setOrientation(VERTICAL);
        tv = new TextView(getContext());
        tv.setText("推荐品牌");
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams tvPramas=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        tvPramas.height= DimensUtils.dp2px(30);
//        tvPramas.addRule(CENTER_IN_PARENT);
        tvPramas.gravity= Gravity.CENTER;
        tv.setLayoutParams(tvPramas);
        addView(tv);

        recyclerView = new RecyclerView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(200));
        recyclerView.setLayoutParams(layoutParams);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);

        addView(recyclerView);
    }

    public void loadData(String url,String requestBody){
        new HttpUtils().loadData(url,requestBody).setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {

                Log.e("tag",content);
                HotBannerBean hotBannerBean = new Gson().fromJson(content, HotBannerBean.class);
                List<HotBannerBean.BrandBean> brand = hotBannerBean.getBrand();
                MyAdapter adapter=new MyAdapter(brand,getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void loadFailed(String msg) {
//                Log.e("tag",msg);
            }
        });
    }

    static class MyAdapter extends RecyclerView.Adapter<Holder>{

        private List<HotBannerBean.BrandBean> list;
        private Context context;
        public  MyAdapter(List<HotBannerBean.BrandBean> list,Context context){
            this.list=list;
            this.context=context;
        }
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(View.inflate(context,R.layout.item_recycle_fenlei_pinlei_tuijian,null));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            HotBannerBean.BrandBean brandBean = list.get(position);
            holder.textView.setText(brandBean.getName());
            Picasso.with(context).load(HttpModel.IMGHOST+brandBean.getImgpath()).fit().into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    static class Holder extends RecyclerView.ViewHolder{

        protected ImageView imageView;
        protected TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }

}
