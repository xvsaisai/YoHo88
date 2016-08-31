package com.example.saisai.yoho.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.FenLeiPagerAdapter;
import com.example.saisai.yoho.base.BaseAnimatorListener;
import com.example.saisai.yoho.bean.PinPaiBean;
import com.example.saisai.yoho.fragment.pinpai_xiangqing.JiageFragment;
import com.example.saisai.yoho.fragment.pinpai_xiangqing.ZhekouFragment;
import com.example.saisai.yoho.fragment.pinpai_xiangqing.ZuixinFragment;
import com.example.saisai.yoho.view.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saisai on 2016/8/27.
 */
public class PinPaiXiangqingActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_indicator)
    ImageView ivIndicator;
    @Bind(R.id.iv_shoucang)
    ImageView ivShoucang;
    @Bind(R.id.iv_fenxiang)
    ImageView ivFenxiang;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.jianjie)
    ScrollView jianjie;
    @Bind(R.id.pinpai_zhanshi)
    RelativeLayout pinpaiZhanshi;
    @Bind(R.id.pinpai_menu_lv)
    ListView pinpaiMenuLv;
    @Bind(R.id.pinpai_select)
    RelativeLayout pinpaiSelect;
    @Bind(R.id.pinpai_drawer)
    SlidingMenu pinpaiDrawer;
    @Bind(R.id.btn_clear)
    Button btnClear;
    @Bind(R.id.btn_com)
    Button btnCom;
    @Bind(R.id.mubu)
    View mubu;
    private ObjectAnimator animatorIn;
    private ObjectAnimator animatorOut;
    private int y;
    private int toolbarHeight;
    private List<Fragment> list;
    private List<String> listTitles;
    private FenLeiPagerAdapter pagerAdapter;
    private PinPaiBean.BrandBean brandBean;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ObjectAnimator openMenuAnim;
    private FrameLayout.LayoutParams pinpaiParams;
    private PopupWindow popupWindow;
    private JiageFragment jiageFragment;
    private ZhekouFragment zhekouFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinpai_xiangqing);
        ButterKnife.bind(this);
        brandBean = (PinPaiBean.BrandBean) getIntent().getSerializableExtra("data");

        ivIndicator.setBackgroundResource(R.drawable.zhanshi);

        initTab();
        initData();
        initAdapter();
        initJianJie();
        initAnimator();

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PinPaiXiangqingActivity.this, "清空", Toast.LENGTH_SHORT).show();
            }
        });
        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mubu.setVisibility(View.GONE);
                openMenuAnim.setFloatValues(-width / 5 * 4, 0);
                openMenuAnim.start();
            }
        });

//        pinpaiParams = (SlidingMenu.LayoutParams) pinpaiZhanshi.getLayoutParams();
        mubu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinpaiZhanshi.getTranslationX() == -width / 5 * 4) {
                    mubu.setVisibility(View.GONE);
                    openMenuAnim.setFloatValues(-width / 5 * 4, 0);
                    openMenuAnim.start();

                }
            }
        });

    }


    int tabCurrentPosition = -1;
    int flagJiage = 0;
    int flagZhekou = 0;
    private void initTab() {

        tablayout.setSmoothScrollingEnabled(true);
        tablayout.setSelectedTabIndicatorHeight(10);
        LayoutInflater mLayoutInflater = this.getLayoutInflater();
        TabLayout.Tab tab = tablayout.newTab();
        View view = mLayoutInflater.inflate(R.layout.item1_tab_custom, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
                tabCurrentPosition = 0;
            }
        });
        tab.setCustomView(view);
        tablayout.addTab(tab);

        View view2 = mLayoutInflater.inflate(R.layout.item2_tab_custom, null);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
                if (tabCurrentPosition == 1) {//再次点击当前页面时候才排序
                    if (flagJiage % 2 == 0) {//升序
                        jiageFragment.shengxvData();
                    } else {//降序
                        jiageFragment.jiangxvData();
                    }
                    flagJiage++;
                }
                tabCurrentPosition = 1;
            }
        });
        TabLayout.Tab tab2 = tablayout.newTab();
        tab2.setCustomView(view2);
        tablayout.addTab(tab2);
        View view3 = mLayoutInflater.inflate(R.layout.item3_tab_custom, null);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
                if (tabCurrentPosition == 2) {//再次点击当前页面时候才排序
                    if (flagZhekou % 2 == 0) {//正序
                        zhekouFragment.shengxvData();
                    } else {//倒叙
                        zhekouFragment.jiangxvData();
                    }
                    flagZhekou++;
                }
                tabCurrentPosition = 2;
            }
        });
        TabLayout.Tab tab3 = tablayout.newTab();
        tab3.setCustomView(view3);
        tablayout.addTab(tab3);

    }

    private void initAdapter() {

        pagerAdapter = new FenLeiPagerAdapter(getSupportFragmentManager(), list, listTitles);
        pager.setAdapter(pagerAdapter);
//        tablayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
        pager.setOffscreenPageLimit(list.size());
    }

    private void initData() {

        list = new ArrayList<>();
        list.add(new ZuixinFragment());
        jiageFragment = new JiageFragment();
        list.add(jiageFragment);
        zhekouFragment = new ZhekouFragment();
        list.add(zhekouFragment);
        listTitles = new ArrayList<>();
        listTitles.add("最新");
        listTitles.add("价格");
        listTitles.add("折扣");
    }

    private void initJianJie() {

        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbarHeight = toolbar.getHeight();
                y = PinPaiXiangqingActivity.this.height - toolbarHeight;
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, y);
                jianjie.setLayoutParams(layoutParams);
                jianjie.setTranslationY(-y);
            }
        });
    }

    private void initAnimator() {

        animatorIn = ObjectAnimator.ofFloat(jianjie, "translationY", 0, 0);
        animatorIn.setDuration(1000);
        animatorIn.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                isRun = false;
            }
        });
        animatorOut = ObjectAnimator.ofFloat(jianjie, "translationY", 0, 0);
        animatorOut.setDuration(1000);
        animatorOut.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                isRun = false;
            }
        });

        openMenuAnim = ObjectAnimator.ofFloat(pinpaiZhanshi, "translationX", 0, 0);
        openMenuAnim.setDuration(1000);
    }

    public void backClick(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.main_pinpai_xiangqing_activity_in, R.anim.pinpai_xiangqing_activity_out);
    }

    public void shoucangClick(View view) {
        Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
    }

    public void fenxiangClick(View view) {
        Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
    }

    int clickCount = 0;
    boolean isRun = false;

    public void jianjieOnclick(View view) {
        if (!isRun) {

            isRun = true;
            if (clickCount % 2 == 0) {//展示
                ivIndicator.setBackgroundResource(R.drawable.shouqi);
                showJianJie();

            } else {//收起
                ivIndicator.setBackgroundResource(R.drawable.zhanshi);
                shouqiJianJie();

            }
            clickCount++;
        }


    }

    //收起
    private void shouqiJianJie() {
        animatorOut.setFloatValues(toolbarHeight, -y);
        animatorOut.start();
    }

    //展示
    private void showJianJie() {

        animatorIn.setFloatValues(-y, toolbarHeight);
        animatorIn.start();
    }

    //筛选
    public void shaxuanClick(View view) {
        mubu.setVisibility(View.VISIBLE);
        openMenuAnim.setFloatValues(0, -width / 5 * 4);
        openMenuAnim.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
