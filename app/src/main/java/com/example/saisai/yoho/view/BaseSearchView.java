package com.example.saisai.yoho.view;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by saisai on 2016/8/26.
 */
public abstract class BaseSearchView<T> {

    public View rootView;
    public Context context;
//
//    public BaseSearchView(Context context){
//        this.context=context;
//    }
//    public void createView(Context context){
//        if(rootView==null){
//            rootView=initView(context);
//            initData();
//
//            initAdapter();
//        }
//    }

    abstract void initView(Context context);

    abstract void initData(List<T> ... list);

    abstract void initAdapter();

}
