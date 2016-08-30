package com.example.saisai.yoho.fragment.fenlei_pinpai;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.activity.PinPaiXiangqingActivity;
import com.example.saisai.yoho.adapter.fenlei_pinpai.FenleiPinpaiBoyExpandAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.bean.PinPaiBean;
import com.example.saisai.yoho.bean.PinPaiParentBean;
import com.example.saisai.yoho.event.RemoveAddSearchViewEvent;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.view.HrizotalScrollView;
import com.example.saisai.yoho.view.MyBanner;
import com.example.saisai.yoho.view.MySearchView;
import com.google.gson.Gson;
import com.google.gson.internal.ConstructorConstructor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */
public abstract class FenleiPinpaiBaseFragment extends BaseFrament {
    protected android.widget.ExpandableListView expand;
    protected android.widget.TextView tvempty;
    protected android.widget.ProgressBar pbempty;
    protected android.widget.RelativeLayout empty;
    protected android.widget.ListView lvletter;
    protected View search;
    protected com.example.saisai.yoho.view.MyBanner banner;
    protected HrizotalScrollView tuijian;
    protected List<String> letter;
    protected List<PinPaiBean.BrandBean> brand;
    protected List<PinPaiParentBean> pinpaiParentList;
    protected FenleiPinpaiBoyExpandAdapter adapter;
    protected ArrayAdapter<String> letterAdapter;
    private View pinpai;
    private RadioButton rd_quanbu;
    private RadioButton rd_xin;
    private RadioButton rd_remen;
    private EditText edt_search;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {


        final View inflate = inflater.inflate(R.layout.fragment_fenlei_pinpai_child, null);



        this.lvletter = (ListView) inflate.findViewById(R.id.lv_letter);
        this.empty = (RelativeLayout) inflate.findViewById(R.id.empty);
        this.pbempty = (ProgressBar) inflate.findViewById(R.id.pb_empty);
        this.tvempty = (TextView) inflate.findViewById(R.id.tv_empty);
        this.expand = (ExpandableListView) inflate.findViewById(R.id.expand);
        expand.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

//                Toast.makeText(activity,pinpaiParentList.get(groupPosition).getList().get(childPosition).getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, PinPaiXiangqingActivity.class);
                PinPaiBean.BrandBean brandBean = pinpaiParentList.get(groupPosition).getList().get(childPosition);
                intent.putExtra("data", brandBean);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.pinpai_xiangqing_activity_in, R.anim.main_activity_toleft);
                return true;
            }
        });
        lvletter = (ListView) inflate.findViewById(R.id.lv_letter);
        lvletter.setDividerHeight(0);
        lvletter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getY();
                        int pointToPosition = lvletter.pointToPosition(0, (int) y);
                        if(pointToPosition>=0){
                            expand.setSelectedGroup(pointToPosition);
                            int currentChild=pointToPosition-lvletter.getFirstVisiblePosition();
                            for(int i=0;i<lvletter.getChildCount();i++){
                                // 设置阴影
                                lvletter.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                if(currentChild==i)
                                    lvletter.getChildAt(i).setBackgroundColor(Color.parseColor("#cccccc"));
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        int currentChild=-1;
                        for(int i=0;i<lvletter.getChildCount();i++){
                            // 设置阴影
                            lvletter.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        break;
                }
                return true;
            }
        });
        pbempty.setVisibility(View.VISIBLE);
        tvempty.setVisibility(View.GONE);
        tvempty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvempty.setVisibility(View.GONE);
                pbempty.setVisibility(View.VISIBLE);
//                initData();
                requestData();
            }
        });

        initExpand();
        initHead();
        return inflate;
    }
    private void initHead() {
        search = View.inflate(activity, R.layout.item_search, null);
        edt_search = (EditText) search.findViewById(R.id.search);
        edt_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //切换回ViewPager
                List<PinPaiBean.BrandBean> list=new ArrayList<PinPaiBean.BrandBean>();
                for(int i=0;i<3;i++){
                    list.add(brand.get(i));
                }
                EventBus.getDefault().post(new RemoveAddSearchViewEvent<PinPaiBean.BrandBean>(0,brand,list));
                return false;
            }
        });
        ExpandableListView.LayoutParams layoutParams = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(70));
        search.setLayoutParams(layoutParams);

        banner=new MyBanner(activity);
        ExpandableListView.LayoutParams layoutParams2 = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(200));
        banner.setLayoutParams(layoutParams2);

        tuijian = new HrizotalScrollView(activity);
        ExpandableListView.LayoutParams layoutParams3 = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(150));
        tuijian.setLayoutParams(layoutParams3);

        pinpai = View.inflate(activity, R.layout.item_fenlei_pinpai_pinpaifenlei, null);
        rd_quanbu = (RadioButton) pinpai.findViewById(R.id.rd_quanbu);
        rd_quanbu.performClick();
        rd_xin = (RadioButton) pinpai.findViewById(R.id.rd_xin);
        rd_remen = (RadioButton) pinpai.findViewById(R.id.rd_remen);
        ExpandableListView.LayoutParams layoutParams4 = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(50));
        pinpai.setLayoutParams(layoutParams4);


        View view1=new View(activity);
        ExpandableListView.LayoutParams layoutParams5 = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(20));
        view1.setBackgroundColor(Color.parseColor("#eeeeee"));
        view1.setLayoutParams(layoutParams5);
        View view2=new View(activity);
        ExpandableListView.LayoutParams layoutParams6 = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(20));
        view2.setBackgroundColor(Color.parseColor("#eeeeee"));
        view2.setLayoutParams(layoutParams6);

        expand.addHeaderView(search);
        expand.addHeaderView(banner);
        expand.addHeaderView(view1);
        expand.addHeaderView(tuijian);
        expand.addHeaderView(view2);
        expand.addHeaderView(pinpai);
    }

    private void initExpand() {
        expand.setEmptyView(empty);//添加空的View
        expand.setGroupIndicator(null);//去掉图标
        expand.setVerticalScrollBarEnabled(false);//滚动条不可用
        //去掉点击事件
        expand.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    protected void openExpand() {

        for(int i=0;i<letter.size();i++){
            expand.expandGroup(i,false);//打开所有的item
        }
    }


    @Override
    public void initData() {
        brand = new ArrayList<>();

    }

    public abstract void requestData();

    @Override
    public void initAdapter() {
        super.initAdapter();
        requestData();

//                Log.e("tag","content------------"+pinpaiParentList.toString());
//        adapter = new FenleiPinpaiBoyExpandAdapter(pinpaiParentList,activity);
//        expand.setAdapter(adapter);
//        lvletter.setVisibility(View.VISIBLE);
//        letterAdapter = new ArrayAdapter<>(activity, R.layout.item_lv_letter, R.id.letter_item_tv, letter);
//        lvletter.setAdapter(letterAdapter);

    }

    protected List<PinPaiParentBean> getPinpaiParentList(List<String> keys, List<PinPaiBean.BrandBean> brand){

        List<PinPaiParentBean> listParent=new ArrayList<>();

        for (int i=0;i<keys.size();i++){
            List<PinPaiBean.BrandBean> list=new ArrayList<>();
            String s = keys.get(i);
            for(PinPaiBean.BrandBean bean:brand){
                boolean equals = bean.getLetter().equals(s);
                if(equals){
                    list.add(bean);
                }
            }
            listParent.add(new PinPaiParentBean(s,list));
        }
        return listParent;
    }

    protected List<String> getLetter(List<PinPaiBean.BrandBean> brand){

        List<String> list=new ArrayList<>();
        for(PinPaiBean.BrandBean bean:brand){
            String letter = bean.getLetter();
            boolean contains = list.contains(letter);
            if(!contains){
                list.add(letter);
            }
        }
        return list;
    }



}
