package com.example.saisai.yoho.fragment.pinpai_xiangqing;

import com.example.saisai.yoho.bean.FllowItemBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.HttpUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by saisai on 2016/8/31.
 */
public class JiageFragment extends PinpaiXiangqingBaseFragment {


    public void loadData() {

        new HttpUtils().loadData(HttpModel.FLLOW, "").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {

            @Override
            public void loadSuccess(final String content) {
                FllowItemBean fllowItemBean = new Gson().fromJson(content, FllowItemBean.class);
                List<FllowItemBean.FollowBean> temp = fllowItemBean.getFollow();
                list.clear();
                for (int i = 0; i < temp.size(); i++) {
                    FllowItemBean.FollowBean bean = temp.get(i);
                    List<FllowItemBean.FollowBean.GoodsBean> goodsBeen = bean.getGoods();
                    for (int j = 0; j < goodsBeen.size(); j++) {

                        FllowItemBean.FollowBean.GoodsBean goods = goodsBeen.get(j);
                        list.add(goods);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void loadFailed(String msg) {

            }
        });

    }

}
