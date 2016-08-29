package com.example.saisai.yoho.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.PinPaiBean;

import java.util.List;

/**
 * Created by saisai on 2016/8/26.
 */
public class FenleiPinpaiSearchAdapter extends BaseSearchLVAdapter<PinPaiBean.BrandBean> {
    public FenleiPinpaiSearchAdapter(List<PinPaiBean.BrandBean> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_fenlei_pinpai_search_lv,null);
            holder=new Holder();
            holder.textView= (TextView) convertView.findViewById(R.id.tv_item_fenlei_pinpai_search_lv);

            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }

        PinPaiBean.BrandBean item = getItem(position);

        holder.textView.setText(item.getName());
        return convertView;
    }

    static class Holder{
        TextView textView;
    }
}
