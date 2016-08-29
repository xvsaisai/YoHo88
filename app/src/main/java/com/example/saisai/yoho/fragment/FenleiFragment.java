package com.example.saisai.yoho.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import butterknife.Bind;
import butterknife.ButterKnife;

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
        list.add(new FenleiPinleiFragment());
        list.add(new FenleiPinpaiFragment());
        list.add(new FenleiGuanZhuFragment());

        titles = new ArrayList<>();
        titles.add("品类");
        titles.add("品牌");
        titles.add("关注");

    }

    @Override
    public void initAdapter() {
//        pager.setOffscreenPageLimit(list.size()/2+1);//设置预缓存页数
        adapter = new FenLeiPagerAdapter(getFragmentManager(),list,titles);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
    }
}
