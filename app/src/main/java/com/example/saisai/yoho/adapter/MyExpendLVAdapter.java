package com.example.saisai.yoho.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.QuerendingdanExpendBean;
import com.example.saisai.yoho.event.DingDanEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by saisai on 2016/9/9.
 */
public class MyExpendLVAdapter extends BaseExpandableListAdapter {

    private List<QuerendingdanExpendBean> listData;
    private Context context;

    public MyExpendLVAdapter(List<QuerendingdanExpendBean> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(groupPosition).list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(groupPosition).list.get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        TextView v = new TextView(context);
        v.setText(listData.get(groupPosition).title);
        return v;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_child_expand_querenndingdan_activity, null);
            holder = new ChildViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv_item_child_expand_title);
            holder.rd = (RadioButton) convertView.findViewById(R.id.rd_item_child_expand_select);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.tv.setText(listData.get(groupPosition).list.get(childPosition).desc);
        holder.rd.setChecked(listData.get(groupPosition).list.get(childPosition).isSelect);
        final ChildViewHolder finalHolder = holder;
        holder.rd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listData.get(groupPosition).list.get(childPosition).isSelect) {
                    for (int i = 0; i < listData.get(groupPosition).list.size(); i++) {
                        listData.get(groupPosition).list.get(i).isSelect = false;
                    }
                    listData.get(groupPosition).list.get(childPosition).isSelect = true;
                    EventBus.getDefault().post(new DingDanEvent());
                }
            }
        });
        return convertView;
    }

    static class ChildViewHolder {
        TextView tv;
        RadioButton rd;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
