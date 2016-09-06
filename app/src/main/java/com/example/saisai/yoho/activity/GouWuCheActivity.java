package com.example.saisai.yoho.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.GouwucheAddGoodsAdapter;
import com.example.saisai.yoho.bean.CartItemBean;
import com.example.saisai.yoho.bean.ItemCartBean;
import com.example.saisai.yoho.bean.ShangPinXiangQingBean;
import com.example.saisai.yoho.event.QuanxuanCheckedEvent;
import com.example.saisai.yoho.event.UpdateCartCountEvent;
import com.example.saisai.yoho.event.UpdateCartItemEvent;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.model.UserRequestState;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.util.LocalCartUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
    private android.widget.CheckBox cb_quanxuan;
    private android.widget.TextView tvzongjia;
    private android.widget.TextView tvyunfei;
    private android.widget.Button btnjiesuan;
    private android.widget.RelativeLayout bottom;
    private List<ShangPinXiangQingBean.GoodsBean> listBean;
    private List<ShangPinXiangQingBean.GoodsBean> listKey;
    private List<ItemCartBean> list;
    private Map<ShangPinXiangQingBean.GoodsBean, Integer> map;
    private GouwucheAddGoodsAdapter addGoodsAdapter;
    private View jianqian;
    private Button btnMove;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_gouwuche);

        initView();
        initData();
        initAdapter();

    }

    private void initAdapter() {
        addGoodsAdapter = new GouwucheAddGoodsAdapter(list, GouWuCheActivity.this);
        lvgouwuche.setAdapter(addGoodsAdapter);
    }

    private void initData() {

        list = new ArrayList<>();
        map = new HashMap<>();
        listKey = new ArrayList<>();
        if (MyApplication.checkLogin()) {
            new HttpUtils().loadData(HttpModel.CART, "parames={\"userId\":" + MyApplication.user.id + "}").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
                @Override
                public void loadSuccess(String content) {

                    map.clear();
                    listKey.clear();
                    list.clear();
                    CartItemBean cartItemBean = new Gson().fromJson(content, CartItemBean.class);
                    List<CartItemBean.Cart> cart = cartItemBean.getCart();
                    int num = 0;
                    for (CartItemBean.Cart bean : cart) {
                        num += Integer.parseInt(bean.getNum());
                        ItemCartBean itemCartBean = new ItemCartBean();
                        itemCartBean.setNum(bean.getNum());
                        itemCartBean.setTitle(bean.getTitle());
                        itemCartBean.setSize(bean.getSize());
                        itemCartBean.setPrice(bean.getPrice());
                        itemCartBean.setColor(bean.getColor());
                        itemCartBean.setImgpath(bean.getImgpath());

//                        map.put(itemCartBean,Integer.parseInt(bean.getNum()));
                        list.add(itemCartBean);
                    }
                    MyApplication.count = num;
                    EventBus.getDefault().post(new UpdateCartCountEvent());
                    lvgouwuche.setAdapter(addGoodsAdapter);
                }

                @Override
                public void loadFailed(String msg) {
                    Toast.makeText(GouWuCheActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            List<ShangPinXiangQingBean.GoodsBean> goodsBeen = LocalCartUtils.get();
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
//            listKey = LocalCartUtils.get();

            for (int i = 0; i < goodsBeen.size(); i++) {
                if (!listKey.contains(goodsBeen.get(i))) {
                    listKey.add(goodsBeen.get(i));
                }
            }

            int num = 0;
            for (int i = 0; i < listKey.size(); i++) {
                ShangPinXiangQingBean.GoodsBean bean = listKey.get(i);
                ItemCartBean itemCartBean = new ItemCartBean();
                itemCartBean.setSize(bean.getSexId());
                itemCartBean.setTitle(bean.getTitle());
                itemCartBean.setPrice(bean.getPrice());
                itemCartBean.setImgpath("");
                itemCartBean.setColor(bean.getColorId());
                itemCartBean.setNum(map.get(bean) + "");
                num += map.get(bean);
                list.add(itemCartBean);
            }
            MyApplication.count = num;
            EventBus.getDefault().post(new UpdateCartCountEvent());

        }
    }

    private void initView() {
        this.bottom = (RelativeLayout) findViewById(R.id.bottom);
        this.btnjiesuan = (Button) findViewById(R.id.btn_jiesuan);
        this.tvyunfei = (TextView) findViewById(R.id.tv_yunfei);
        this.tvzongjia = (TextView) findViewById(R.id.tv_zongjia);
        this.cb_quanxuan = (CheckBox) findViewById(R.id.cb_quanxuan);
        this.lvgouwuche = (ListView) findViewById(R.id.lv_gouwuche);
        this.toolgouwwuche = (RelativeLayout) findViewById(R.id.tool_gouwwuche);
        this.tvbianji = (TextView) findViewById(R.id.tv_bianji);
        jianqian = findViewById(R.id.jiaqian);
        btnMove = (Button) findViewById(R.id.btn_move);
        //region 全选
        cb_quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double price = 0;
                int seletCount = 0;
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isChecked = cb_quanxuan.isChecked();
                    if (list.get(i).isChecked) {
                        price += Double.parseDouble(list.get(i).getPrice()) * Integer.parseInt(list.get(i).getNum());
                        seletCount += Integer.parseInt(list.get(i).getNum());
                    }
                }
                addGoodsAdapter.notifyDataSetChanged();
                setZongjia(price, seletCount);
            }
        });
        //endregion
        //region 编辑
        tvbianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvbianji.getText().toString().equals("编辑")) {
                    tvbianji.setText("完成");
                    btnjiesuan.setText("删除");
                    addGoodsAdapter.setType(GouwucheAddGoodsAdapter.EDIT);
                    btnMove.setVisibility(View.VISIBLE);
                    jianqian.setVisibility(View.GONE);
                } else {
                    tvbianji.setText("编辑");
                    btnjiesuan.setText("结算");
                    addGoodsAdapter.setType(GouwucheAddGoodsAdapter.NOMAL);
                    btnMove.setVisibility(View.GONE);
                    jianqian.setVisibility(View.VISIBLE);
                }
                addGoodsAdapter.notifyDataSetChanged();

            }
        });
        //endregion
        //region 收藏
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.checkLogin()) {
                    moveToShouCang();
                } else {
                    //登录
                    login();
                }
            }
        });
        //endregion
    }

    /**
     * 登录
     */
    private void login() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("state", UserRequestState.YIDONG_STATE);
        startActivityForResult(intent, UserRequestState.YIDONG_STATE);
    }

    /**
     * 移动到收藏夹
     */
    private void moveToShouCang() {

        showLoadDialog();
        shoucang();
    }

    //收藏
    private void shoucang() {

        new HttpUtils().loadData(HttpModel.COLLECTION, "").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {
                cancelLoadDialog();
                //
                List<ItemCartBean> temp = new ArrayList<ItemCartBean>();
                for (ItemCartBean bean : list) {

                    if (bean.isChecked) {
                        temp.add(bean);
                    }
                }
                list.removeAll(temp);
                addGoodsAdapter.notifyDataSetChanged();
                MyApplication.count = list.size();
                EventBus.getDefault().post(new UpdateCartItemEvent());
                Toast.makeText(GouWuCheActivity.this, content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void loadFailed(String msg) {
                cancelLoadDialog();
                Toast.makeText(GouWuCheActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UserRequestState.YIDONG_STATE && resultCode == RESULT_OK) {
            moveToShouCang();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.main_activity_stail,R.anim.gouwuche_activity_out);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setQauanxuanChecked(QuanxuanCheckedEvent event) {
        if (event != null) {
            cb_quanxuan.setChecked(event.isChecked);
//            tvzongjia.setText("总计：￥"+event.price+"("+event.selectCount+"件)");
            setZongjia(event.price, event.selectCount);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setUpdateCartItemEvent(UpdateCartItemEvent event) {

        if (event != null) {
            int count = 0;
            addGoodsAdapter.notifyDataSetChanged();
            for (int i = 0; i < list.size(); i++) {
                count += Integer.parseInt(list.get(i).getNum());
            }
            MyApplication.count = count;
            EventBus.getDefault().post(new UpdateCartCountEvent());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setZongjia(double price, int seletCount) {
        tvzongjia.setText("总计：￥" + price + "(" + seletCount + "件)");
    }
}
