package com.example.saisai.yoho.util;

import com.example.saisai.yoho.bean.ShangPinXiangQingBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/9/1.
 */
public class LocalCartUtils {

    public static void save(ShangPinXiangQingBean.GoodsBean bean) {

        List<ShangPinXiangQingBean.GoodsBean> goodsBeen = get();
        if (goodsBeen == null) {
            goodsBeen = new ArrayList<>();
        }
        goodsBeen.add(bean);
        String s = new Gson().toJson(goodsBeen);
        SPUtils.save("cart", s);
    }

    public static List<ShangPinXiangQingBean.GoodsBean> get() {
        String string = SPUtils.get("cart");
        if ("".equals(string)) {
            return null;
        }
        List<ShangPinXiangQingBean.GoodsBean> o = new Gson().fromJson(string, new TypeToken<List<ShangPinXiangQingBean.GoodsBean>>() {
        }.getType());

        return o;
    }

    public static void remove(ShangPinXiangQingBean.GoodsBean bean) {

        List<ShangPinXiangQingBean.GoodsBean> goodsBeen = get();
        goodsBeen.remove(bean);
        String s = new Gson().toJson(goodsBeen);
        SPUtils.save("cart", s);
    }

    public static void clearCart() {
        SPUtils.clear("cart");
    }
}
