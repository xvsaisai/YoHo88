package com.example.saisai.yoho.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saisai.yoho.base.BaseFrament;

/**
 * Created by saisai on 2016/8/23.
 */
public class WodeFragment extends BaseFrament {

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

        TextView tv=new TextView(activity);
        tv.setText(getClass().getSimpleName());
        tv.setTextSize(30);
        return tv;
    }

    @Override
    public void initData() {

    }
}
