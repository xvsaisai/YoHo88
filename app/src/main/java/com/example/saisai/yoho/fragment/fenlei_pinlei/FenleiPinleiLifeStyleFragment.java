package com.example.saisai.yoho.fragment.fenlei_pinlei;

import android.util.Log;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.PinLeiBean;
import com.example.saisai.yoho.bean.PinLeiChildGirlBean;
import com.example.saisai.yoho.bean.PinLeiLifeStyleBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.HttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/24.
 */
public class FenleiPinleiLifeStyleFragment extends FenleiPinleiBaseFrament {

    @Override
    public void initData() {
        super.initData();
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "数码3C"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "耳机"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "相机"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "手表"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "数码配件"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "美妆/个护"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "家居生活"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "雨伞/雨衣"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "杯子/水壶"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "自行车"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "玩具娱乐"));
        list.add(new PinLeiBean(R.drawable.goods_vip2_icon, "文具"));
    }

    @Override
    public void requestChidData() {
        super.requestChidData();
        new HttpUtils().loadData(HttpModel.PEILEILIFESTYLE, "").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {
                PinLeiLifeStyleBean pinLeiLifeStyleBean = new Gson().fromJson(content, PinLeiLifeStyleBean.class);
                childList.clear();
                //
                List<String> temp=new ArrayList<String>();
//                toast(peiLeiIChildtemBean.getSucessfully());
                for(PinLeiLifeStyleBean.LifeBean lifeBean:pinLeiLifeStyleBean.getLife()){
                    temp.add(lifeBean.getName());
                }
                childList.addAll(temp);
                childLVAdapter.notifyDataSetChanged();

                Log.e("tag",temp.toString());
            }

            @Override
            public void loadFailed(String msg) {

                Log.e("tag",msg);

            }
        });
    }
}
