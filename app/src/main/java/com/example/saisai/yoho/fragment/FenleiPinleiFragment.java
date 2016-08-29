package com.example.saisai.yoho.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.FenleiPinleiPagerAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.fragment.fenlei_pinlei.FenleiPinleiBoyFragment;
import com.example.saisai.yoho.fragment.fenlei_pinlei.FenleiPinleiGirlFragment;
import com.example.saisai.yoho.fragment.fenlei_pinlei.FenleiPinleiLifeStyleFragment;
import com.example.saisai.yoho.view.FenLeiViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by saisai on 2016/8/24.
 */
public class FenleiPinleiFragment extends BaseFrament {
    @Bind(R.id.rd_boy)
    RadioButton rdBoy;
    @Bind(R.id.rd_girl)
    RadioButton rdGirl;
    @Bind(R.id.rd_lifrstyle)
    RadioButton rdLifrstyle;
    @Bind(R.id.pinlei_pager)
    FenLeiViewPager pager;
    @Bind(R.id.tab_group)
    RadioGroup tabGroup;
    private List<Fragment> list;
    private FenleiPinleiPagerAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View inflate = inflater.inflate(R.layout.fragment_fenlei_pinlei, null);
        ButterKnife.bind(this, inflate);
        rdBoy.performClick();
        return inflate;

    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        list.add(new FenleiPinleiBoyFragment());
        list.add(new FenleiPinleiGirlFragment());
        list.add(new FenleiPinleiLifeStyleFragment());
    }

    @Override
    public void initAdapter() {
        super.initAdapter();
//        pager.setOffscreenPageLimit(list.size()/2+1);
        adapter = new FenleiPinleiPagerAdapter(getFragmentManager(), list);
        pager.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rd_boy, R.id.rd_girl, R.id.rd_lifrstyle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rd_boy:
                pager.setCurrentItem(0, false);//false表示切换时候没有动画
                break;
            case R.id.rd_girl:
                pager.setCurrentItem(1, false);
                break;
            case R.id.rd_lifrstyle:
                pager.setCurrentItem(2, false);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
