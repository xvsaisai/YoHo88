package com.example.saisai.yoho.fragment.pinpai_xiangqing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.base.BaseFrament;

/**
 * Created by saisai on 2016/8/27.
 */
public class PinpaiXiangqingBaseFragment extends BaseFrament {

    private GridView gridView;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

        View inflate = inflater.inflate(R.layout.fragment_pinpaixiangqing, null);
        gridView = (GridView) inflate.findViewById(R.id.gv_xiangqing);
        return inflate;
    }

    @Override
    public void initData() {

    }
}
