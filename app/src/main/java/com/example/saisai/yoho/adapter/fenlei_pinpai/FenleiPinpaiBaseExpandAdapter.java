package com.example.saisai.yoho.adapter.fenlei_pinpai;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;

import com.example.saisai.yoho.bean.PinPaiParentBean;

import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */
public abstract class FenleiPinpaiBaseExpandAdapter<T>   extends BaseExpandableListAdapter {

    public List<T> list;
    public Context context;
    public FenleiPinpaiBaseExpandAdapter(List<T> list,Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
