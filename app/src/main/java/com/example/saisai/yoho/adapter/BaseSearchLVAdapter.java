package com.example.saisai.yoho.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by saisai on 2016/8/26.
 */
public abstract class BaseSearchLVAdapter<T> extends BaseAdapter {

    public List<T> list;
    public Context context;
    public BaseSearchLVAdapter(List<T> list,Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
