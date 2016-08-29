package com.example.saisai.yoho.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.saisai.yoho.MyAdapter;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.FenLeiPagerAdapter;
import com.example.saisai.yoho.adapter.FenleiPinleiPagerAdapter;
import com.example.saisai.yoho.adapter.fenlei_pinpai.FenleiPinpaiBoyExpandAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.bean.PinPaiBean;
import com.example.saisai.yoho.bean.PinPaiParentBean;
import com.example.saisai.yoho.event.RemoveAddSearchViewEvent;
import com.example.saisai.yoho.fragment.fenlei_pinlei.FenleiPinleiBoyFragment;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiBoyFragment;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiGirlFragment;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiKidFragment;
import com.example.saisai.yoho.fragment.fenlei_pinpai.FenleiPinpaiLifeStyleFragment;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.view.FenLeiViewPager;
import com.example.saisai.yoho.view.HrizotalScrollView;
import com.example.saisai.yoho.view.MyBanner;
import com.example.saisai.yoho.view.MySearchView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */
public class FenleiPinpaiFragment extends BaseFrament implements View.OnClickListener {
    private com.example.saisai.yoho.view.MyBanner banner;
    private android.widget.ExpandableListView expand;
    private View search;
    private HrizotalScrollView tuijian;
    private List<String> letter;
    private FenleiPinpaiBoyExpandAdapter adapter;
    private List<PinPaiParentBean> list;
    private List<PinPaiParentBean> pinpaiParentList;
    private View empty;
    private ProgressBar pb_empty;
    private TextView tv_empty;
    private ListView lv_letter;
    private android.widget.RadioButton rdboy;
    private android.widget.RadioButton rdgirl;
    private android.widget.RadioButton rdkid;
    private android.widget.RadioButton rdlifrstyle;
    private android.widget.RadioGroup tabgroup;
    private TextView tvempty;
    private ProgressBar pbempty;
    private ListView lvletter;
    private FenLeiViewPager pager;
    private ViewGroup pinpai_group;
    private MySearchView search_view;

    private View root;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

        EventBus.getDefault().register(this);

        View inflate = inflater.inflate(R.layout.fragment_fenlei_pinpai, null);

        pinpai_group = (ViewGroup) inflate.findViewById(R.id.pinpai_group);
        search_view = new MySearchView(activity);
        root = inflate.findViewById(R.id.pinpai_pager_group);

        this.pager = (FenLeiViewPager) inflate.findViewById(R.id.pinpai_pager);
        this.tabgroup = (RadioGroup) inflate.findViewById(R.id.tab_group);
        this.rdlifrstyle = (RadioButton) inflate.findViewById(R.id.rd_lifrstyle);
        rdlifrstyle.setOnClickListener(this);
        this.rdkid = (RadioButton) inflate.findViewById(R.id.rd_kid);
        rdkid.setOnClickListener(this);
        this.rdgirl = (RadioButton) inflate.findViewById(R.id.rd_girl);
        rdgirl.setOnClickListener(this);
        this.rdboy = (RadioButton) inflate.findViewById(R.id.rd_boy);
        rdboy.setOnClickListener(this);
        rdboy.performClick();

        return inflate;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.rd_lifrstyle:
                Log.e("tag","rd_lifrstyle");
                pager.setCurrentItem(3,false);

                break;
            case R.id.rd_boy:
                Log.e("tag","rd_boy");
                pager.setCurrentItem(0,false);

                break;
            case R.id.rd_girl:
                Log.e("tag","rd_girl");
                pager.setCurrentItem(1,false);
                break;
            case R.id.rd_kid:
                Log.e("tag","rd_kid");
                pager.setCurrentItem(2,false);
                break;
        }

    }

    @Override
    public void initData() {
        List<Fragment> list=new ArrayList<>();
        list.add(new FenleiPinpaiBoyFragment());
        list.add(new FenleiPinpaiGirlFragment());
        list.add(new FenleiPinpaiKidFragment());
        list.add(new FenleiPinpaiLifeStyleFragment());
//        pager.setOffscreenPageLimit(list.size()/2+1);
        pager.setAdapter(new FenleiPinleiPagerAdapter(getFragmentManager(),list));

    }
    @Override
    public void onDetach() {

        EventBus.getDefault().unregister(this);
        super.onDetach();
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
}
