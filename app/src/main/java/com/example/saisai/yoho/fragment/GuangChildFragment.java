package com.example.saisai.yoho.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.saisai.share.bean.FenxiangBean;
import com.example.saisai.share.dialog.ShareDialog;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.activity.WebActivity;
import com.example.saisai.yoho.adapter.ArtAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.bean.ArBean;
import com.example.saisai.yoho.bean.BannerBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.DimensUtils;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.util.MyLog;
import com.example.saisai.yoho.view.MyBanner;
import com.example.saisai.yoho.view.PullLoadListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/9/5.
 */
public class GuangChildFragment extends BaseFrament implements AdapterView.OnItemClickListener, ArtAdapter.OnSharedListener {
    private com.example.saisai.yoho.view.PullLoadListView pulllistview;
    private List<ArBean.DataBean.ListBean.ArtListBean> list = new ArrayList<>();
    private List<BannerBean> beanList;
    private MyBanner banner;
    private ArtAdapter artAdapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

//        EventBus.getDefault().register(activity);
        View inflate = inflater.inflate(R.layout.fragment_child_guang, null);
        this.pulllistview = (PullLoadListView) inflate.findViewById(R.id.pulllistview);
        artAdapter = new ArtAdapter(list, getContext());
        pulllistview.setAdapter(artAdapter);
        pulllistview.setOnItemClickListener(this);
        pulllistview.setPullUpUsable(false);

        return inflate;
    }

    @Override
    public void initData() {

        banner = new MyBanner(getContext());
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, DimensUtils.dp2px(200));
        banner.setLayoutParams(params);

        pulllistview.addHeadView(banner);
        banner.loadData(HttpModel.BANNER, "");
        artAdapter.setOnSharedListener(this);
        loadData();
    }

    String path = "https://service.yoho.cn/guang/api/v2/article/getList?app_version=4.8.0&client_secret=dcc3362a5e809a6b62fec8c9da1ce970&client_type=android&os_version=android4.4.2%3AGT-I9268&page=1&screen_size=720x1280&sort_id=0&udid=864394010542381&v=7&yh_channel=4";

    private void loadData() {

        new HttpUtils().loadData(path, "").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {

                ArBean arBean = new Gson().fromJson(content, ArBean.class);
                List<ArBean.DataBean.ListBean.ArtListBean> artList = arBean.getData().getList().getArtList();
                list.addAll(artList);
                artAdapter.notifyDataSetChanged();
                MyLog.m("GuangFragment=============" + content);
            }

            @Override
            public void loadFailed(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArBean.DataBean.ListBean.ArtListBean artListBean = list.get(position);

        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra("url", artListBean.getUrl());
        startActivity(intent);
    }


    List<FenxiangBean> fenxiangBeanList = new ArrayList<>();

    @Override
    public void onShared(int position) {
        for (int i = 0; i < 5; i++) {
            FenxiangBean bean = new FenxiangBean();
            bean.setTitle("title1");
            bean.setTitleUrl("http://www.baidu.com");
            bean.setImageUrl("http://img05.tooopen.com/images/20150202/sy_80219211654.jpg");
            fenxiangBeanList.add(bean);
        }

        int i = position % list.size();
        FenxiangBean bean = fenxiangBeanList.get(i);
        ShareDialog dialog = new ShareDialog(activity, bean);
        dialog.show();
        fenxiangBeanList = null;
    }
}
