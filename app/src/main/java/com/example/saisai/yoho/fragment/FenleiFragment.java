package com.example.saisai.yoho.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.FenLeiPagerAdapter;
import com.example.saisai.yoho.base.BaseFrament;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/23.
 */
public class FenleiFragment extends BaseFrament {

    private View inflate;
    private List<Fragment> list;
    private List<String> titles;
    private ViewPager pager;
    private TabLayout tab;
    private FenLeiPagerAdapter adapter;
    private FenleiPinleiFragment fenleiPinleiFragment;
    private FenleiPinpaiFragment fenleiPinpaiFragment;
    private FenleiGuanZhuFragment fenleiGuanZhuFragment;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

        inflate = inflater.inflate(R.layout.fragmment_fenlei, null);

        pager = (ViewPager) inflate.findViewById(R.id.pager);
        tab = (TabLayout) inflate.findViewById(R.id.tab);
        return inflate;
    }

    @Override
    public void initData() {

        list = new ArrayList<>();
        fenleiPinleiFragment = new FenleiPinleiFragment();
        list.add(fenleiPinleiFragment);
        fenleiPinpaiFragment = new FenleiPinpaiFragment();
        list.add(fenleiPinpaiFragment);
        fenleiGuanZhuFragment = new FenleiGuanZhuFragment();
        list.add(fenleiGuanZhuFragment);

        titles = new ArrayList<>();
        titles.add("品类");
        titles.add("品牌");
        titles.add("关注");

    }

    @Override
    public void initAdapter() {
        pager.setOffscreenPageLimit(list.size());//设置预缓存页数
        adapter = new FenLeiPagerAdapter(getFragmentManager(),list,titles);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

//                switch (position){
//
//                    case 0:
//                        Log.e("tag","品类");
//                        fenleiPinleiFragment.isSelect=true;
//                        fenleiPinpaiFragment.isSelect=false;
//                        fenleiGuanZhuFragment.isSelect=false;
//                        break;
//                    case 1:
//                        Log.e("tag","品牌");
//                        fenleiPinleiFragment.isSelect=false;
//                        fenleiPinpaiFragment.isSelect=true;
//                        fenleiGuanZhuFragment.isSelect=false;
//                        break;
//                    case 2:
//                        Log.e("tag","关注");
//                        fenleiPinleiFragment.isSelect=false;
//                        fenleiPinpaiFragment.isSelect=false;
//                        fenleiGuanZhuFragment.isSelect=true;
//                        break;
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
