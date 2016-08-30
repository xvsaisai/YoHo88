package com.example.saisai.yoho.adapter.fenlei_pinpai;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.PinPaiBean;
import com.example.saisai.yoho.bean.PinPaiParentBean;

import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */
public class FenleiPinpaiBoyExpandAdapter extends FenleiPinpaiBaseExpandAdapter<PinPaiParentBean> {
    public FenleiPinpaiBoyExpandAdapter(List<PinPaiParentBean> list, Context context) {
        super(list, context);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupHolder holder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_fenlei_pinpai_boy_expand, null);
            holder=new GroupHolder();
            holder.textView= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else {
            holder= (GroupHolder) convertView.getTag();
        }
        PinPaiParentBean pinPaiParentBean = list.get(groupPosition);
        holder.textView.setText(pinPaiParentBean.getTitle());
        holder.textView.setBackgroundColor(Color.parseColor("#eeeeee"));
        return convertView;
    }

    static class GroupHolder{
        TextView textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_fenlei_pinpai_boy_depand_child, null);
            holder=new ChildHolder();
            holder.textView= (TextView) convertView.findViewById(R.id.tv_content);
            holder.imageView= (ImageView) convertView.findViewById(R.id.iv_content);
            convertView.setTag(holder);
        }else {
            holder= (ChildHolder) convertView.getTag();
        }
        PinPaiBean.BrandBean pinPaiParentBean = list.get(groupPosition).getList().get(childPosition);
        holder.textView.setText(pinPaiParentBean.getName());
        holder.textView.setTextSize(30);
        if(pinPaiParentBean.getHotflag().equals("1")){
            holder.imageView.setImageResource(R.drawable.sort_brand_hot_ico);
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ChildHolder{
        TextView textView;
        ImageView imageView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
