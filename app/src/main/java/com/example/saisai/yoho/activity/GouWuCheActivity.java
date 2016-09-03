package com.example.saisai.yoho.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.ShangPinXiangQingBean;
import com.example.saisai.yoho.util.LocalCartUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saisai on 2016/8/23.
 */
public class GouWuCheActivity extends BaseActivity {


    private android.widget.TextView tvbianji;
    private android.widget.RelativeLayout toolgouwwuche;
    private android.widget.ListView lvgouwuche;
    private android.widget.RadioButton rdquanxuan;
    private android.widget.TextView tvzongjia;
    private android.widget.TextView tvyunfei;
    private android.widget.Button btnjiesuan;
    private android.widget.RelativeLayout bottom;
    private List<ShangPinXiangQingBean.GoodsBean> listBean;
    private List<ShangPinXiangQingBean.GoodsBean> listKey;
    private Map<ShangPinXiangQingBean.GoodsBean, Integer> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouwuche);

        initView();
        initData();
        initAdapter();

    }

    private void initAdapter() {

    }

    private void initData() {
        if (MyApplication.checkLogin()) {
//            new HttpUtils().loadData().setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
//                @Override
//                public void loadSuccess(String content) {
//
//                }
//
//                @Override
//                public void loadFailed(String msg) {
//
//                }
//            });
        } else {
            List<ShangPinXiangQingBean.GoodsBean> goodsBeen = LocalCartUtils.get();
//            goodsBeen.get(0).get
            map = new HashMap<>();
            for (int i = 0; i < goodsBeen.size(); i++) {
                ShangPinXiangQingBean.GoodsBean bean = goodsBeen.get(i);
                Integer integer = map.get(bean);
                if (integer == null) {
                    map.put(bean, 1);
                } else {
                    map.put(bean, integer + 1);
                }
            }
            listKey = LocalCartUtils.get();

            for (int i = 0; i < goodsBeen.size(); i++) {
                if (!listKey.contains(goodsBeen.get(i))) {
                    listKey.add(goodsBeen.get(i));
                }
            }


        }
    }

    private void initView() {
        this.bottom = (RelativeLayout) findViewById(R.id.bottom);
        this.btnjiesuan = (Button) findViewById(R.id.btn_jiesuan);
        this.tvyunfei = (TextView) findViewById(R.id.tv_yunfei);
        this.tvzongjia = (TextView) findViewById(R.id.tv_zongjia);
        this.rdquanxuan = (RadioButton) findViewById(R.id.rd_quanxuan);
        this.lvgouwuche = (ListView) findViewById(R.id.lv_gouwuche);
        this.toolgouwwuche = (RelativeLayout) findViewById(R.id.tool_gouwwuche);
        this.tvbianji = (TextView) findViewById(R.id.tv_bianji);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.main_activity_stail,R.anim.gouwuche_activity_out);
    }
}
