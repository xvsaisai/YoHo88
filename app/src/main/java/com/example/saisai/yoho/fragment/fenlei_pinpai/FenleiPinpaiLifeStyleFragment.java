package com.example.saisai.yoho.fragment.fenlei_pinpai;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.fenlei_pinpai.FenleiPinpaiBoyExpandAdapter;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.bean.PinPaiBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.HttpUtils;
import com.google.gson.Gson;

import java.util.Collections;

/**
 * Created by saisai on 2016/8/25.
 */
public class FenleiPinpaiLifeStyleFragment extends FenleiPinpaiBaseFragment {
    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void requestData() {
        new HttpUtils().loadData(HttpModel.ALLBANNER,"parames={\\\"page\\\":\\\"10\\\"}").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {
                PinPaiBean pinPaiBean = new Gson().fromJson(content, PinPaiBean.class);
                brand = pinPaiBean.getBrand();
//                Log.e("tag",content);


                letter = getLetter(brand);
                Collections.sort(letter);
                pinpaiParentList = getPinpaiParentList(letter, brand);
                adapter = new FenleiPinpaiBoyExpandAdapter(pinpaiParentList,activity);
                expand.setAdapter(adapter);
                openExpand();
                lvletter.setVisibility(View.VISIBLE);
                lvletter.setAdapter(new ArrayAdapter<String>(activity, R.layout.item_lv_letter,R.id.letter_item_tv,letter));
//                pbempty.setVisibility(View.VISIBLE);
//                tvempty.setVisibility(View.GONE);
//                lvletter.setVisibility(View.VISIBLE);
            }

            @Override
            public void loadFailed(String msg) {
                Log.e("tag", "Failed------" + msg);
                pbempty.setVisibility(View.GONE);
                tvempty.setVisibility(View.VISIBLE);
                lvletter.setVisibility(View.GONE);
            }
        });

        banner.loadData(HttpModel.BANNER,"");
        tuijian.loadData(HttpModel.HOTBANNER,"");
    }
}
