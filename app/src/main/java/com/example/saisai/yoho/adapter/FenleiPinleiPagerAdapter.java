package com.example.saisai.yoho.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by saisai on 2016/8/24.
 */
public class FenleiPinleiPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public FenleiPinleiPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
