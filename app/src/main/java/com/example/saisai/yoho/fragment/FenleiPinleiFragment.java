package com.example.saisai.yoho.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.FenleiPinleiPagerAdapter;
import com.example.saisai.yoho.fragment.fenlei_pinlei.FenleiPinleiBoyFragment;
import com.example.saisai.yoho.fragment.fenlei_pinlei.FenleiPinleiGirlFragment;
import com.example.saisai.yoho.fragment.fenlei_pinlei.FenleiPinleiLifeStyleFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/24.
 */

//public class FenleiPinleiFragment extends BaseFrament implements View.OnClickListener {
//
//    @Override
//    public View initView(LayoutInflater inflater, ViewGroup container) {
//
//        View inflate = inflater.inflate(R.layout.fragment_fenlei_pinlei, null);
//        rd_boy = (RadioButton) inflate.findViewById(R.id.rd_boy);
//        rd_girl = (RadioButton) inflate.findViewById(R.id.rd_girl);
//        rd_lifrstyle = (RadioButton) inflate.findViewById(R.id.rd_lifrstyle);
//        pinlei_pager = (ViewPager) inflate.findViewById(R.id.pinlei_pager);
//        tab_group = (RadioGroup) inflate.findViewById(R.id.tab_group);
//
//        rd_boy.performClick();
//        rd_boy.setOnClickListener(this);
//        rd_girl.setOnClickListener(this);
//        rd_lifrstyle.setOnClickListener(this);
//        return inflate;
//    }
//
//    public RadioButton rd_boy;
//    public RadioButton rd_girl;
//    public RadioButton rd_lifrstyle;
//    public ViewPager pinlei_pager;
//    public RadioGroup tab_group;
//
//    public List<Fragment> list;
//    public FenleiPinleiPagerAdapter adapter;
//
//    public boolean isSelect = true;
//
//
//    @Override
//    public void initData() {
//
//        list = new ArrayList<>();
//        list.add(new FenleiPinleiBoyFragment());
//        list.add(new FenleiPinleiGirlFragment());
//        list.add(new FenleiPinleiLifeStyleFragment());
//    }
//
//    @Override
//    public void initAdapter() {
//        adapter = new FenleiPinleiPagerAdapter(getChildFragmentManager(), list);
//        pinlei_pager.setAdapter(adapter);
//    }
//
//
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.rd_boy:
//                pinlei_pager.setCurrentItem(0, false);//false表示切换时候没有动画
//                break;
//            case R.id.rd_girl:
//                pinlei_pager.setCurrentItem(1, false);
//                break;
//            case R.id.rd_lifrstyle:
//                pinlei_pager.setCurrentItem(2, false);
//                break;
//        }
//    }
//
//
//}
public class FenleiPinleiFragment extends LazyBaseFragment implements View.OnClickListener {
    public RadioButton rd_boy;
    public RadioButton rd_girl;
    public RadioButton rd_lifrstyle;
    public ViewPager pinlei_pager;
    public RadioGroup tab_group;

    public List<Fragment> list;
    public FenleiPinleiPagerAdapter adapter;

    public boolean isSelect = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fenlei_pinlei;
    }

    @Override
    protected void initView() {
        rd_boy = findView(R.id.rd_boy);
        rd_girl = findView(R.id.rd_girl);
        rd_lifrstyle = findView(R.id.rd_lifrstyle);
        pinlei_pager = findView(R.id.pinlei_pager);
        tab_group = findView(R.id.tab_group);

        rd_boy.performClick();
        rd_boy.setOnClickListener(this);
        rd_girl.setOnClickListener(this);
        rd_lifrstyle.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        list = new ArrayList<>();
        list.add(new FenleiPinleiBoyFragment());
        list.add(new FenleiPinleiGirlFragment());
        list.add(new FenleiPinleiLifeStyleFragment());
    }

    @Override
    protected void initAdapter() {
        adapter = new FenleiPinleiPagerAdapter(getChildFragmentManager(), list);
        pinlei_pager.setAdapter(adapter);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rd_boy:
                pinlei_pager.setCurrentItem(0, false);//false表示切换时候没有动画
                break;
            case R.id.rd_girl:
                pinlei_pager.setCurrentItem(1, false);
                break;
            case R.id.rd_lifrstyle:
                pinlei_pager.setCurrentItem(2, false);
                break;
        }
    }

}
