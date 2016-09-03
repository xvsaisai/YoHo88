package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.NavigationViewAdapter;
import com.example.saisai.yoho.bean.ShangPinXiangQingBean;
import com.example.saisai.yoho.event.MainJumpPinpaiXiangqingActivityEvent;
import com.example.saisai.yoho.event.UpdateCartCountEvent;
import com.example.saisai.yoho.fragment.FenleiFragment;
import com.example.saisai.yoho.fragment.GuangFragment;
import com.example.saisai.yoho.fragment.ShouyeFragment;
import com.example.saisai.yoho.fragment.WodeFragment;
import com.example.saisai.yoho.util.LocalCartUtils;
import com.example.saisai.yoho.view.MyRadioButton;
import com.example.saisai.yoho.view.MySlidingPaneLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.lv_navigation)
    ListView lvnavigation;
    @Bind(R.id.navigation)
    RelativeLayout navigation;
    @Bind(R.id.fragment_group)
    FrameLayout fragmentGroup;
    @Bind(R.id.rd_shouye)
    MyRadioButton rdShouye;
    @Bind(R.id.rd_fenlei)
    MyRadioButton rdFenlei;
    @Bind(R.id.rd_guang)
    MyRadioButton rdGuang;
    @Bind(R.id.rd_gouwuche)
    MyRadioButton rdGouwuche;
    @Bind(R.id.rd_wode)
    MyRadioButton rdWode;
    @Bind(R.id.navigationbar_group)
    RadioGroup navigationbarGroup;
    @Bind(R.id.home)
    RelativeLayout home;
    @Bind(R.id.sliding)
    MySlidingPaneLayout sliding;
    private FragmentManager fm;
    private ShouyeFragment shouyeFragment;
    private int currentPosition = -1;
    private FenleiFragment fenleiFragment;
    private GuangFragment guangFragment;
    private WodeFragment wodeFragment;
    private List<MyRadioButton> myRadioButtonList;

    private int lastSelect=-1;
    private NavigationViewAdapter adapter;
    private String[] stringArray;

    private Map<String,Fragment> fragmentMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        initCartCount();

        if(findFragment()){
            initFragment();
        }else {
            initFragment();
            rdShouye.performClick();
        }

        initData();
        lvnavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sliding.closePane();
                adapter.setSelectChange(position);
            }
        });
    }

    private void initCartCount() {

        if (MyApplication.checkLogin()) {
//            new HttpUtils().loadData().setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
//                @Override
//                public void loadSuccess(String content) {
//
//                }
//
//                @Override
//                public void loadFailed(String msg) {
//
//                }
//            });
        } else {
            List<ShangPinXiangQingBean.GoodsBean> goodsBeen = LocalCartUtils.get();
            MyApplication.count = goodsBeen.size();
        }
    }

    private boolean findFragment() {

        ShouyeFragment shouyeFragment= (ShouyeFragment) getSupportFragmentManager().findFragmentByTag(ShouyeFragment.class.getSimpleName());
        FenleiFragment fenleiFragment= (FenleiFragment) getSupportFragmentManager().findFragmentByTag(FenleiFragment.class.getSimpleName());
        GuangFragment guangFragment= (GuangFragment) getSupportFragmentManager().findFragmentByTag(GuangFragment.class.getSimpleName());
        WodeFragment wodeFragment= (WodeFragment) getSupportFragmentManager().findFragmentByTag(WodeFragment.class.getSimpleName());

        if(shouyeFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_group,shouyeFragment).commit();
            fragmentMap.put(ShouyeFragment.class.getSimpleName(),shouyeFragment);
            return true;

        }else if(fenleiFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_group,fenleiFragment).commit();
            fragmentMap.put(FenleiFragment.class.getSimpleName(),fenleiFragment);
            return true;

        }else if(guangFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_group,guangFragment).commit();
            fragmentMap.put(GuangFragment.class.getSimpleName(),guangFragment);
            return true;

        }else if(wodeFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_group,wodeFragment).commit();
            fragmentMap.put(WodeFragment.class.getSimpleName(),wodeFragment);
            return true;

        }else {
            return false;
        }
    }


    private void initFragment() {

        // 放置之前可以判断有没有
        if(fragmentMap.get(WodeFragment.class.getSimpleName())==null){
            fragmentMap.put(WodeFragment.class.getSimpleName(), new WodeFragment());
        }
        if(fragmentMap.get(ShouyeFragment.class.getSimpleName())==null){
            fragmentMap.put(ShouyeFragment.class.getSimpleName(), new ShouyeFragment());
        }
        if(fragmentMap.get(GuangFragment.class.getSimpleName())==null){
            fragmentMap.put(GuangFragment.class.getSimpleName(), new GuangFragment());
        }
        if(fragmentMap.get(FenleiFragment.class.getSimpleName())==null){
            fragmentMap.put(FenleiFragment.class.getSimpleName(), new FenleiFragment());
        }
    }

    String fragmentTag="";
    String currentFragmengTag="";
    private void replaceFragment(String tag) {

        Fragment fragment = fragmentMap.get(tag);
        if (!fragmentTag.equals(tag)) {
            Log.e("das", "qiehuan");
            currentFragmengTag = tag;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_group, fragment, tag).commit();
        }
        fragmentTag = tag;
    }


    private void initData() {

        stringArray = getResources().getStringArray(R.array.navigation_lv_values);
        List<String> list = Arrays.asList(stringArray);
        adapter = new NavigationViewAdapter(list,this);
        lvnavigation.setAdapter(adapter);
        myRadioButtonList = new ArrayList<>();
        myRadioButtonList.add(rdShouye);
        myRadioButtonList.add(rdFenlei);
        myRadioButtonList.add(rdGuang);
        myRadioButtonList.add(rdGouwuche);
        myRadioButtonList.add(rdWode);

        rdGouwuche.setRedDotTextNum(MyApplication.count);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.choose_activity_in, R.anim.main_activity_out);
        finish();
    }

    @OnClick({R.id.rd_shouye, R.id.rd_fenlei, R.id.rd_guang, R.id.rd_gouwuche, R.id.rd_wode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rd_shouye:
                currentPosition = 0;
                replaceFragment(ShouyeFragment.class.getSimpleName());
                break;
            case R.id.rd_fenlei:
                currentPosition = 1;
                replaceFragment(FenleiFragment.class.getSimpleName());
                break;
            case R.id.rd_guang:
                currentPosition = 2;
                replaceFragment(GuangFragment.class.getSimpleName());
                break;
            case R.id.rd_gouwuche:
                startActivity(new Intent(MainActivity.this, GouWuCheActivity.class));
                overridePendingTransition(R.anim.gouwuche_activity_in, R.anim.main_activity_stail);

                break;
            case R.id.rd_wode:
                currentPosition = 4;
                replaceFragment(WodeFragment.class.getSimpleName());
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (currentPosition != -1) {
            myRadioButtonList.get(currentPosition).performClick();
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void subsMainnJumpPinpaiXiangqingEvent(MainJumpPinpaiXiangqingActivityEvent event){

        if(event.flag==0){
            startActivity(new Intent(this,PinPaiXiangqingActivity.class));
            overridePendingTransition(R.anim.pinpai_xiangqing_activity_in,R.anim.main_pinpai_xiangqing_activity_out);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void subsUpdateCartCountEvent(UpdateCartCountEvent event) {
        if (event != null) {
            rdGouwuche.setRedDotTextNum(MyApplication.count);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if(fragmentMap.get(ShouyeFragment.class.getSimpleName()).isAdded()){
            getSupportFragmentManager().putFragment(outState,"shouye",fragmentMap.get(ShouyeFragment.class.getSimpleName()));
        }
        if(fragmentMap.get(FenleiFragment.class.getSimpleName()).isAdded()){
            getSupportFragmentManager().putFragment(outState,"fenlei",fragmentMap.get(FenleiFragment.class.getSimpleName()));
        }
        if(fragmentMap.get(GuangFragment.class.getSimpleName()).isAdded()){
            getSupportFragmentManager().putFragment(outState,"guang",fragmentMap.get(GuangFragment.class.getSimpleName()));
        }
        if(fragmentMap.get(WodeFragment.class.getSimpleName()).isAdded()){
            getSupportFragmentManager().putFragment(outState,"wode",fragmentMap.get(WodeFragment.class.getSimpleName()));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void open(View view) {
        sliding.openPane();
    }


    public void shouyeOpenNavigetion(View view){
        sliding.openPane();
    }
    public void shouyeSearch(View view){
        Intent intent=new Intent(this,SearchActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pinpai_xiangqing_activity_in,R.anim.main_activity_toleft);
    }
    public void shouyeSaomiao(View view){
        Intent intent=new Intent(this,SaomiaoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pinpai_xiangqing_activity_in,R.anim.main_activity_toleft);


    }
}
