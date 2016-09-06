package com.example.saisai.yoho.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.FenleiPinleiPagerAdapter;
import com.example.saisai.yoho.event.RemoveAddSearchViewEvent;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiBoyFragment;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiGirlFragment;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiKidFragment;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiLifeStyleFragment;
import com.example.saisai.yoho.view.FenLeiViewPager;
import com.example.saisai.yoho.view.MySearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */

//public class FenleiPinpaiFragment extends Fragment{
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        TextView tv=new TextView(getContext());
//        tv.setText(getClass().getSimpleName());
//        return tv;
//    }
//}

public class FenleiPinpaiFragment extends LazyBaseFragment implements View.OnClickListener {
    private android.widget.RadioButton rdboy;
    private android.widget.RadioButton rdgirl;
    private android.widget.RadioButton rdkid;
    private android.widget.RadioButton rdlifrstyle;
    private FenLeiViewPager pager;
    private ViewGroup pinpai_group;
    private MySearchView search_view;
    private RadioGroup tabgroup;

    private View root;

    private List<Fragment> list;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

//    @Override

//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;

    protected int getLayoutId() {
        return R.layout.fragment_fenlei_pinpai;
    }

    @Override
    protected void initView() {
        pinpai_group = findView(R.id.pinpai_group);
        search_view = new MySearchView(getContext());
        root = findView(R.id.pinpai_pager_group);

        this.pager = findView(R.id.pinpai_pager);
        this.tabgroup = findView(R.id.tab_group);
        this.rdlifrstyle = findView(R.id.rd_lifrstyle);
        rdlifrstyle.setOnClickListener(this);
        this.rdkid = findView(R.id.rd_kid);
        rdkid.setOnClickListener(this);
        this.rdgirl = findView(R.id.rd_girl);
        rdgirl.setOnClickListener(this);
        this.rdboy = findView(R.id.rd_boy);
        rdboy.setOnClickListener(this);
        rdboy.performClick();
    }

    @Override
    public void initData() {

        list = new ArrayList<>();
        list.add(new FenleiPinpaiBoyFragment());
        list.add(new FenleiPinpaiGirlFragment());
        list.add(new FenleiPinpaiKidFragment());
        list.add(new FenleiPinpaiLifeStyleFragment());

    }
    @Override
    protected void initAdapter() {
//        pager.setOffscreenPageLimit(list.size()/2+1);
        pager.setOffscreenPageLimit(list.size());
        pager.setAdapter(new FenleiPinleiPagerAdapter(getFragmentManager(), list));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.rd_lifrstyle:
//                Log.e("tag","rd_lifrstyle");
                pager.setCurrentItem(3,false);

                break;
            case R.id.rd_boy:
//                Log.e("tag","rd_boy");
                pager.setCurrentItem(0,false);

                break;
            case R.id.rd_girl:
                Log.e("tag","rd_girl");
                pager.setCurrentItem(1,false);
                break;
            case R.id.rd_kid:
//                Log.e("tag","rd_kid");
                pager.setCurrentItem(2,false);
                break;
        }

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRemoveAddSearchEvent(RemoveAddSearchViewEvent event){
        if(event.flag==0){//add

            search_view.init(event.list[0],event.list[1]);
            pinpai_group.removeAllViews();
            pinpai_group.addView(search_view.rootView);
        }else {//remove
            pinpai_group.removeAllViews();
            pinpai_group.addView(root);
        }
    }

    @Override
    public void onDetach() {

        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

}
