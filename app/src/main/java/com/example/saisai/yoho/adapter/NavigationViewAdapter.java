package com.example.saisai.yoho.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by saisai on 2016/8/23.
 */
public class NavigationViewAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    public NavigationViewAdapter(List<String> list, Context context){
        this.list=list;
        this.context=context;
    }

    int currentPosition=0;
    public void setSelectChange(int position){
        if(position!=currentPosition){
            currentPosition=position;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView tv=new TextView(context);
        tv.setText(getItem(position));
        tv.setTextSize(20);
        if(currentPosition==position){
            tv.setBackgroundColor(Color.GREEN);
        }
        return tv;
    }


}
