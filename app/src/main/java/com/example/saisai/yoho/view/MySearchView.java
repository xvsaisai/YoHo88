package com.example.saisai.yoho.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.activity.PinPaiXiangqingActivity;
import com.example.saisai.yoho.adapter.BaseSearchLVAdapter;
import com.example.saisai.yoho.adapter.FenleiPinpaiSearchAdapter;
import com.example.saisai.yoho.bean.HotBannerBean;
import com.example.saisai.yoho.bean.PinPaiBean;
import com.example.saisai.yoho.dialog.DeleteDialog;
import com.example.saisai.yoho.event.MainJumpPinpaiXiangqingActivityEvent;
import com.example.saisai.yoho.event.RemoveAddSearchViewEvent;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.SPUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by saisai on 2016/8/26.
 */
public class MySearchView extends BaseSearchView implements TextWatcher {


    private EditText edt_search;
    private TextView tv_cancel;
    private LinearLayout bottom;
    private TagFlowLayout tagflow_history;
    private TagFlowLayout tagflow_hot;
    private ListView lv_search;
    private List<PinPaiBean.BrandBean> listBean;
    private List<PinPaiBean.BrandBean> pipeiList;
    private FenleiPinpaiSearchAdapter adapter;
    private List<PinPaiBean.BrandBean> hotValues;
    private RelativeLayout history_top;
    private TagFlowLayout tagflow_history1;
    private View search_line;
    private List<PinPaiBean.BrandBean> search_list;
    private FenleiPinpaiSearchAdapter searchLVAdapter;
    private View bottom1;
    private List<String> searchHistroy;
    private TagAdapter<String> histroyAdapter;
    private ImageView search_clear;
    private ImageView histroy_clear;
    private DeleteDialog deleteDialog;

    public MySearchView(Context context){
        this.context=context;
    }

    public void init(List<PinPaiBean.BrandBean> list,List<PinPaiBean.BrandBean> listHot){
        initView(context);
        initData(list,listHot);
        initAdapter();
    }

    @Override
    void initView(final Context context) {

        //初始化dialog
        deleteDialog = new DeleteDialog(context);
        deleteDialog.setOnAffirmClickListener(new DeleteDialog.OnAffirmClickListener() {
            @Override
            public void onOlick() {
                                //清空历史记录
                SPUtils.clearSearchHistory();
                searchHistroy.clear();
                searchHistroy = SPUtils.getSearchHistroy();
                if(searchHistroy.size()<=0){
                    tagflow_history.setVisibility(View.GONE);
                    search_line.setVisibility(View.GONE);
                    history_top.setVisibility(View.GONE);
                }
                histroyAdapter.notifyDataChanged();
                deleteDialog.dismiss();
            }
        });

        rootView = View.inflate(context, R.layout.view_search_fenlei_pinpai, null);
        search_clear = (ImageView) rootView.findViewById(R.id.iv_clear);
        search_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空EditText
                edt_search.setText("");
            }
        });
        histroy_clear = (ImageView) rootView.findViewById(R.id.histroy_clear);
        histroy_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示dialog
                deleteDialog.show();
            }
        });
        edt_search = (EditText) rootView.findViewById(R.id.search);
        edt_search.addTextChangedListener(this);
        tv_cancel = (TextView) rootView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空editext,remove当前view，添加之前的view，使用eventbus
                edt_search.setText("");
                lv_search.setVisibility(View.GONE);
                EventBus.getDefault().post(new RemoveAddSearchViewEvent(1,null,null));
            }
        });
        bottom = (LinearLayout) rootView.findViewById(R.id.bottom);
        tagflow_history = (TagFlowLayout) rootView.findViewById(R.id.tagflow_history);
        tagflow_hot = (TagFlowLayout) rootView.findViewById(R.id.tagflow_hot);
        lv_search = (ListView) rootView.findViewById(R.id.lv_search);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = pipeiList.get(position).getName();
                searchHistroy.add(name);

                histroyAdapter.notifyDataChanged();
                if(searchHistroy.size()>=0){
                    tagflow_history.setVisibility(View.VISIBLE);
                    search_line.setVisibility(View.VISIBLE);
                    history_top.setVisibility(View.VISIBLE);
                }
                SPUtils.setSearchHistroy(name,name);

                EventBus.getDefault().post(new MainJumpPinpaiXiangqingActivityEvent(0));
//                context.startActivity(new Intent(context, PinPaiXiangqingActivity.class));
            }
        });
        history_top = (RelativeLayout) rootView.findViewById(R.id.history_top);
        search_line = rootView.findViewById(R.id.search_line);
    }

    @Override
    void initData(List ... list) {

        listBean = new ArrayList<>();
        listBean.clear();
        listBean.addAll(list[0]);

        pipeiList = new ArrayList<>();

        hotValues = new ArrayList<>();
        hotValues.clear();
        hotValues.addAll(list[1]);

        tagflow_hot.setAdapter(new TagAdapter<PinPaiBean.BrandBean>(hotValues)
        {

            @Override
            public View getView(FlowLayout parent, int position, PinPaiBean.BrandBean brandBean)
            {
                TextView tv = new TextView(context);
                tv.setText(brandBean.getName());
                tv.setBackgroundResource(R.drawable.search_item_bg);
                tv.setHeight(DimensUtils.dp2px(20));
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        });
        tagflow_hot.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                Toast.makeText(context,hotValues.get(position).getName(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        searchHistroy = SPUtils.getSearchHistroy();//获取历史搜索记录
        if(searchHistroy .size()<=0){//判断是否存在历史搜索记录，没有就隐藏控件
            tagflow_history.setVisibility(View.GONE);
            search_line.setVisibility(View.GONE);
            history_top.setVisibility(View.GONE);
        }

        histroyAdapter = new TagAdapter<String>(searchHistroy) {//适配
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                tagflow_history.setVisibility(View.VISIBLE);
                search_line.setVisibility(View.VISIBLE);
                history_top.setVisibility(View.VISIBLE);
                TextView tv = new TextView(context);
                tv.setText(s);
                tv.setBackgroundResource(R.drawable.search_item_bg);
                tv.setHeight(DimensUtils.dp2px(20));
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        };
        tagflow_history.setAdapter(histroyAdapter);
        tagflow_history.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(context,searchHistroy.get(position),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    void initAdapter() {

        adapter = new FenleiPinpaiSearchAdapter(pipeiList,context);
        lv_search.setAdapter(adapter);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length()>0){
            search_clear.setVisibility(View.VISIBLE);
            lv_search.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            pipeiList.clear();
            for( PinPaiBean.BrandBean bean:listBean){
                if(bean.getName().contains(s)){
                    pipeiList.add(bean);
                }
            }
            adapter.notifyDataSetChanged();

        }else {
            bottom.setVisibility(View.VISIBLE);
            lv_search.setVisibility(View.GONE);
            search_clear.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
