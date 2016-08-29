package com.example.saisai.yoho.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.BaseSearchLVAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.view.MyBanner;
import com.example.saisai.yoho.view.PullLoadListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by saisai on 2016/8/23.
 */
public class ShouyeFragment extends BaseFrament {


    private PullLoadListView pullLoadListView;
    private BaseAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, final ViewGroup container) {

       View  inflate =  inflater.inflate(R.layout.fragment_shouye, null);

        MyBanner banner=new MyBanner(activity);
        AbsListView.LayoutParams bannerParams=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,DimensUtils.dp2px(200));
        banner.setLayoutParams(bannerParams);
        banner.loadData(HttpModel.BANNER,"");


        final List<String> list=new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.navigation_lv_values);
        list.addAll(Arrays.asList(stringArray));
        list.addAll(Arrays.asList(stringArray));
        list.addAll(Arrays.asList(stringArray));

        pullLoadListView= (PullLoadListView) inflate.findViewById(R.id.lv_pull_shouye);
        pullLoadListView.addHeadView(banner);
        pullLoadListView.setOnPullOrLoadListener(new PullLoadListView.OnPullOrLoadListener() {
            @Override
            public void pull() {

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(0,"头部数据");
                        adapter.notifyDataSetChanged();
                        pullLoadListView.pullSuccess();
                    }
                },3000);

            }

            @Override
            public void load() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("尾部数据");
                        pullLoadListView.setSelection(pullLoadListView.getFirstVisibilePosition());
                        adapter.notifyDataSetChanged();
                        pullLoadListView.loadSuccess();
                    }
                },3000);
            }
        });

        adapter = new BaseSearchLVAdapter<String>(list,activity) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView tv=new TextView(getContext());
                tv.setText(list.get(position));
                tv.setHeight(DimensUtils.dp2px(30));
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        };
        pullLoadListView.setAdapter(adapter);

        return inflate;
    }

    @Override
    public void initData() {
        new HttpUtils().loadData(HttpModel.HOMEPAGER,"").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {

            }

            @Override
            public void loadFailed(String msg) {

            }
        });
    }
}
