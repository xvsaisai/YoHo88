package com.example.saisai.yoho.adapter.fenlei_pinlei;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by saisai on 2016/8/24.
 */
public abstract class BasePinLeiAdapter<T> extends BaseAdapter {
    public List<T> list;
    public Context context;

    public BasePinLeiAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
