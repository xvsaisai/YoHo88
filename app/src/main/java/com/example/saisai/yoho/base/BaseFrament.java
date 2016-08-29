package com.example.saisai.yoho.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by saisai on 2016/8/23.
 */
public abstract class BaseFrament extends Fragment {

    public Activity activity;
    private View rootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity =activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView==null){
            rootView = initView(inflater,container);
            initData();
            initAdapter();
        }
        return rootView;
    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container);
    public abstract void initData();
    public  void initAdapter(){

    }

}
