package com.example.saisai.yoho.fragment.fenlei_pinlei;

import android.util.Log;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.PinLeiBean;
import com.example.saisai.yoho.bean.PinLeiChildGirlBean;
import com.example.saisai.yoho.bean.PinLeiIChildtemBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.HttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/24.
 */
public class FenleiPinleiGirlFragment extends FenleiPinleiBaseFrament {


    @Override
    public void initData() {
        super.initData();
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "上衣"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "裤装"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "裙装"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "鞋靴"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "包类"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "服饰"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "内衣"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "美妆/个护"));
    }

    @Override
    public void requestChidData() {
        super.requestChidData();
        new HttpUtils().loadData(HttpModel.PEILEIGIRL, "").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {
                PinLeiChildGirlBean pinLeiChildGirlBean = new Gson().fromJson(content, PinLeiChildGirlBean.class);
                childList.clear();
                //
                List<String> temp=new ArrayList<String>();
//                toast(peiLeiIChildtemBean.getSucessfully());
                for(PinLeiChildGirlBean.GirlBean girlBean:pinLeiChildGirlBean.getGirl()){
                    temp.add(girlBean.getName());
                }
                childList.addAll(temp);
                childLVAdapter.notifyDataSetChanged();

//                Log.e("tag",temp.toString());
            }

            @Override
            public void loadFailed(String msg) {

                Log.e("tag", "Failed----" + msg);

            }
        });
    }
}
