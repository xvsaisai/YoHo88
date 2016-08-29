package com.example.saisai.yoho.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by saisai on 2016/8/23.
 */
public class FenLeiPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private List<String> titles;
    public FenLeiPagerAdapter(FragmentManager fm,List<Fragment> list,List<String> titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
