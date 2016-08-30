package com.example.saisai.yoho.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.BaseSearchLVAdapter;
import com.example.saisai.yoho.adapter.HomeListAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.bean.HomeBean;
import com.example.saisai.yoho.bean.ShouyeTuiguangBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.view.MyBanner;
import com.example.saisai.yoho.view.PullLoadListView;
import com.example.saisai.yoho.view.ShouyeGridVIew;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by saisai on 2016/8/23.
 */
public class ShouyeFragment extends BaseFrament{


    private PullLoadListView pullLoadListView;
    private MyBanner banner;
    private ShouyeGridVIew gridView;
    private ImageButton iv_saomiao;
    private ImageButton iv_search;
    private ImageButton iv_navigation;
    private ViewFlipper flipper;
    private HomeListAdapter adapter;
    private List<List<HomeBean.BrandBean>> homeList;

    @Override
    public View initView(LayoutInflater inflater, final ViewGroup container) {

       View  inflate =  inflater.inflate(R.layout.fragment_shouye, null);

        flipper = (ViewFlipper) inflate.findViewById(R.id.flipper_shouye);
        flipper.setAutoStart(true);//设置自动播放
        flipper.setInAnimation(AnimationUtils.loadAnimation(activity, R.anim.anim_flipper_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(activity, R.anim.anim_flipper_out));
        initBanner();

        initGridView();

        final List<String> list=new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.navigation_lv_values);
        list.addAll(Arrays.asList(stringArray));
        list.addAll(Arrays.asList(stringArray));
        list.addAll(Arrays.asList(stringArray));

        pullLoadListView= (PullLoadListView) inflate.findViewById(R.id.lv_pull_shouye);
        pullLoadListView.addHeadView(banner);
        pullLoadListView.addHeadView(gridView);
        pullLoadListView.setOnPullOrLoadListener(new PullLoadListView.OnPullOrLoadListener() {
            @Override
            public void pull() {

                new HttpUtils().loadData(HttpModel.HOMEPAGER, "parames={\\\"shop\\\":\\\"1\\\"}").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {

                    @Override
                    public void loadSuccess(String content) {

                        HomeBean homeBean = new Gson().fromJson(content, HomeBean.class);
                        List<List<HomeBean.BrandBean>> temp = new ArrayList<List<HomeBean.BrandBean>>();
                        temp.add(homeBean.getAccessories());
                        temp.add(homeBean.getBrand());
                        temp.add(homeBean.getMen());
                        temp.add(homeBean.getMenpants());
                        temp.add(homeBean.getOther());
                        homeList.clear();
                        homeList.addAll(temp);

                        adapter.notifyDataSetChanged();
                        pullLoadListView.pullSuccess();
                    }

                    @Override
                    public void loadFailed(String msg) {
                        Log.e("tag", "Failed-----" + msg);
                    }
                });
                adapter.notifyDataSetChanged();

                pullLoadListView.pullSuccess();

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



        return inflate;
    }

    private void initGridView() {

        List<ShouyeTuiguangBean> list=new ArrayList<>();
        list.add(new ShouyeTuiguangBean("新品到站",R.drawable.btn_cptj));
        list.add(new ShouyeTuiguangBean("潮流优选",R.drawable.btn_dpzn_n));
        list.add(new ShouyeTuiguangBean("年中热促",R.drawable.btn_mxcp_n));
        list.add(new ShouyeTuiguangBean("明星原创",R.drawable.btn_qbpl_n));
        list.add(new ShouyeTuiguangBean("全部分类",R.drawable.btn_qqyx_n));
        list.add(new ShouyeTuiguangBean("人气搭配",R.drawable.btn_qxsc_n));
        list.add(new ShouyeTuiguangBean("领券中心",R.drawable.btn_xpdz_n));
        list.add(new ShouyeTuiguangBean("全球购",R.drawable.btn_zkjx_n));
        gridView = new ShouyeGridVIew(activity);
        gridView.setNumColumns(4);
        AbsListView.LayoutParams gridParams=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.WRAP_CONTENT);
//        gridView.setPadding(DimensUtils.dp2px(5),DimensUtils.dp2px(5),DimensUtils.dp2px(5),DimensUtils.dp2px(5));
        gridView.setLayoutParams(gridParams);
        gridView.setAdapter(new BaseSearchLVAdapter<ShouyeTuiguangBean>(list,activity) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View inflate = View.inflate(activity, R.layout.item_gridview_shouye, null);
                ImageView iv_gridview= (ImageView) inflate.findViewById(R.id.iv_gridview);
                TextView tv_gridView= (TextView) inflate.findViewById(R.id.tv_gridview);

                ShouyeTuiguangBean shouyeTuiguangBean = list.get(position);
                iv_gridview.setImageResource(shouyeTuiguangBean.getResId());
                tv_gridView.setText(shouyeTuiguangBean.getTitle());
                return inflate;
            }
        });
    }

    private void initBanner() {
        banner = new MyBanner(activity);
        AbsListView.LayoutParams bannerParams=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,DimensUtils.dp2px(200));
        banner.setLayoutParams(bannerParams);
        banner.loadData(HttpModel.BANNER,"");
    }

    @Override
    public void initData() {
        new HttpUtils().loadData(HttpModel.HOMEPAGER, "parames={\\\"shop\\\":\\\"1\\\"}").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {

            @Override
            public void loadSuccess(String content) {

                HomeBean homeBean = new Gson().fromJson(content, HomeBean.class);
                homeList = new ArrayList<List<HomeBean.BrandBean>>();
                homeList.add(homeBean.getAccessories());
                homeList.add(homeBean.getBrand());
                homeList.add(homeBean.getMen());
                homeList.add(homeBean.getMenpants());
                homeList.add(homeBean.getOther());
                adapter = new HomeListAdapter(homeList, activity);
                pullLoadListView.setAdapter(adapter);
            }

            @Override
            public void loadFailed(String msg) {
                Log.e("tag", "Failed-----" + msg);
            }
        });
    }

}
