package com.example.saisai.yoho.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.BaseSearchLVAdapter;
import com.example.saisai.yoho.adapter.GoodsPagerAdapter;
import com.example.saisai.yoho.base.BaseAnimatorListener;
import com.example.saisai.yoho.bean.FllowItemBean;
import com.example.saisai.yoho.bean.ShangPinXiangQingBean;
import com.example.saisai.yoho.dialog.GouwucheDialog;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.util.MyLog;
import com.example.saisai.yoho.view.MyRadioButton;
import com.example.saisai.yoho.view.snapscrollview.McoyProductContentPage;
import com.example.saisai.yoho.view.snapscrollview.McoyProductDetailInfoPage;
import com.example.saisai.yoho.view.snapscrollview.McoySnapPageLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GoodsXiangqingActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private McoySnapPageLayout mcoySnapPageLayout = null;

    private McoyProductContentPage bottomPage = null;
    private McoyProductDetailInfoPage topPage = null;
    private View topView;
    private View bottomView;
    private ListView lv_bottom;
    private ViewPager pager;
    private TextView tv_title;
    private TextView tv_price;
    private TextView tv_discount;
    private List<ImageView> imgList;
    private ShangPinXiangQingBean shangPinXiangQingBean;
    private MyRadioButton rd_gouwuche;
    private Button btn_add;
    private ImageButton ib_like;
    private CircleImageView circle;
    private ViewGroup rootView;
    private int[] circleLocal;
    private int[] cheLocal;
    private ObjectAnimator animator;
    private FllowItemBean.FollowBean.GoodsBean bean;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        bean = (FllowItemBean.FollowBean.GoodsBean) intent.getSerializableExtra("bean");
        id = Integer.parseInt(bean.get_id());
        setContentView(R.layout.activity_goods_xiangqing);
        rootView = (ViewGroup) findViewById(R.id.rootview);
        mcoySnapPageLayout = (McoySnapPageLayout) findViewById(R.id.flipLayout);
        rd_gouwuche = (MyRadioButton) findViewById(R.id.rd_gouwuche);
        btn_add = (Button) findViewById(R.id.btn_add);
        ib_like = (ImageButton) findViewById(R.id.ib_like);
        rd_gouwuche.setRedDotTextNum(MyApplication.count);
//        rd_gouwuche.setRedDotTextSize(DimensUtils.dp2px(20));
        initTopPage();
        initBottomPage();
        mcoySnapPageLayout.setSnapPages(topPage, bottomPage);


        initData();
        initListener();

    }

    private void initBottomPage() {
        bottomView = getLayoutInflater().inflate(R.layout.mcoy_product_content_page, null);
        bottomPage = new McoyProductContentPage(GoodsXiangqingActivity.this, bottomView);
        lv_bottom = (ListView) bottomView.findViewById(R.id.lv_bottom);
    }

    private void initTopPage() {

        topView = getLayoutInflater().inflate(R.layout.mcoy_produt_detail_layout, null);
        topPage = new McoyProductDetailInfoPage(GoodsXiangqingActivity.this, topView);
        pager = (ViewPager) topView.findViewById(R.id.pager_top);
        tv_title = (TextView) topView.findViewById(R.id.tv_title);
        tv_price = (TextView) topView.findViewById(R.id.tv_price);
        tv_discount = (TextView) topView.findViewById(R.id.tv_discount);

        pager.addOnPageChangeListener(this);
    }

    //
    private List<ImageView> listImgs = new ArrayList<>();
    private List<ShangPinXiangQingBean.ImgvaleBean> listBean = new ArrayList<>();

    private GoodsPagerAdapter pagerAdapter = new GoodsPagerAdapter(listImgs);

    BaseSearchLVAdapter lvAdapter = new BaseSearchLVAdapter(listBean, this) {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView iv = new ImageView(context);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(200));
            iv.setLayoutParams(params);
            ShangPinXiangQingBean.ImgvaleBean bean = listBean.get(position);
            Picasso.with(context).load(HttpModel.IMGHOST + bean.getImgpath()).fit().into(iv);
            MyLog.m(HttpModel.IMGHOST + bean.getImgpath());
            return iv;
        }
    };

    private void initListener() {


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listBean.size() > 0) {
                    GouwucheDialog dialog = new GouwucheDialog(GoodsXiangqingActivity.this, shangPinXiangQingBean);
                    dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                    dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
                    dialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().setWindowAnimations(R.style.add_dialog_anim);
                    dialog.show();
                    dialog.setOnAddListener(new GouwucheDialog.OnAddListener() {

                        @Override
                        public void addSuccess(final int i) {
                            circle = new CircleImageView(GoodsXiangqingActivity.this);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DimensUtils.dp2px(70), DimensUtils.dp2px(70));
                            params.addRule(RelativeLayout.CENTER_IN_PARENT);
                            circle.setLayoutParams(params);
                            Picasso.with(GoodsXiangqingActivity.this).load(HttpModel.IMGHOST + listBean.get(0).getImgpath()).fit().into(circle);
                            rootView.addView(circle);

                            animator = ObjectAnimator.ofFloat(circle, "translationX", 0, 0);
                            animator.setDuration(3000);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    float animatedFraction = animation.getAnimatedFraction();
                                    double v = circle.getTranslationX() * 1.0 / (cheLocal[0] - circleLocal[0]);
                                    circle.setTranslationY((float) (v * (cheLocal[1] - circleLocal[1])));

                                    circle.setScaleX((float) (1 - v * 0.8f));
                                    circle.setScaleY((float) (1 - v * 0.8f));
                                }
                            });
                            animator.addListener(new BaseAnimatorListener() {
                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    super.onAnimationEnd(animator);
                                    if (circle != null) {
                                        rootView.removeView(circle);
//                                        int redDotTextNum = rd_gouwuche.getRedDotTextNum();
//                                        MyLog.m(redDotTextNum+"==="+i);
//                                        if(redDotTextNum==-1){
//                                            redDotTextNum=0;
//                                        }
//                                        rd_gouwuche.setRedDotTextNum(redDotTextNum+i);
                                        //设置购物车中数量
                                        rd_gouwuche.setRedDotTextNum(MyApplication.count);
                                        rd_gouwuche.invalidate();
                                    }
                                }
                            });

                            rootView.post(new Runnable() {
                                @Override
                                public void run() {

                                    circleLocal = new int[2];
                                    circle.getLocationOnScreen(circleLocal);
                                    cheLocal = new int[2];
                                    rd_gouwuche.getLocationOnScreen(cheLocal);
                                    MyLog.m(cheLocal[0] + "=====" + circleLocal[0]);
                                    animator.setFloatValues(0, cheLocal[0] - circleLocal[0]);
                                    animator.start();

                                }
                            });


                        }
                    });
                }


            }
        });
    }

    private void initData() {

        loadData();
    }

    //
//
    private void loadData() {

        new HttpUtils().loadData(HttpModel.GOODSDETAILS, "parames={\\\"goods_id\\\":\\\"\"" + id + "\"\\\"}").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {
//                MyLog.m(HttpModel.GOODSDETAILS+"parames={\\\"goods_id\\\":\\\"\\\"\" + id + \"\"\\\"}");
                shangPinXiangQingBean = new Gson().fromJson(content, ShangPinXiangQingBean.class);
                List<ShangPinXiangQingBean.ImgBean> img = shangPinXiangQingBean.getImg();
                listImgs.clear();
                for (ShangPinXiangQingBean.ImgBean bean : img) {
                    ImageView iv = new ImageView(GoodsXiangqingActivity.this);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.with(GoodsXiangqingActivity.this).load(HttpModel.IMGHOST + bean.getImgpath()).fit().into(iv);
                    listImgs.add(iv);
                }
                ImageView iv = new ImageView(GoodsXiangqingActivity.this);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(GoodsXiangqingActivity.this).load(HttpModel.IMGHOST + img.get(0).getImgpath()).fit().into(iv);
                listImgs.add(iv);

                tv_title.setText(shangPinXiangQingBean.getGoods().get(0).getTitle());
                tv_price.setText(shangPinXiangQingBean.getGoods().get(0).getPrice());
                tv_discount.setText(shangPinXiangQingBean.getGoods().get(0).getDiscount());

                listBean.clear();
                listBean.addAll(shangPinXiangQingBean.getImgvale());

                pager.setAdapter(pagerAdapter);
                lv_bottom.setAdapter(lvAdapter);

            }

            @Override
            public void loadFailed(String msg) {
                Toast.makeText(GoodsXiangqingActivity.this, GoodsXiangqingActivity.class.getSimpleName() + "msg", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        MyLog.m(positionOffset+"========"+positionOffsetPixels);

        if (position == listImgs.size() - 2 && positionOffset > 0.05f) {
            mcoySnapPageLayout.snapToNext();
            pager.setCurrentItem(0);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
