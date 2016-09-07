package com.example.saisai.yoho.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.GuangPagerAdapter;
import com.example.saisai.yoho.base.BaseFrament;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/23.
 */
public class GuangFragment extends BaseFrament {
    private android.widget.ImageView ivnavigation;
    private android.widget.ImageView ivlike;
    private android.widget.RelativeLayout relat;
    private android.support.design.widget.TabLayout tab;
    private android.support.v4.view.ViewPager pager;
    private List<Fragment> list;
    private List<String> titles;
    private GuangPagerAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {


        View inflate = inflater.inflate(R.layout.fragment_guang, null);
        this.pager = (ViewPager) inflate.findViewById(R.id.pager);
        this.tab = (TabLayout) inflate.findViewById(R.id.tab);
        this.relat = (RelativeLayout) inflate.findViewById(R.id.relat);
        this.ivlike = (ImageView) inflate.findViewById(R.id.iv_like);
        this.ivnavigation = (ImageView) inflate.findViewById(R.id.iv_navigation);
        return inflate;
    }

    @Override
    public void initData() {

        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("话题");
        titles.add("搭配");
        titles.add("潮人");
        titles.add("潮品");
        titles.add("专题");
        list = new ArrayList<>();
        list.add(new GuangChildFragment());
        list.add(new GuangChildFragment());
        list.add(new GuangChildFragment());
        list.add(new GuangChildFragment());
        list.add(new GuangChildFragment());
        list.add(new GuangChildFragment());
    }


    @Override
    public void initAdapter() {
        super.initAdapter();
        adapter = new GuangPagerAdapter(getFragmentManager(), list, titles);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
    }


}
