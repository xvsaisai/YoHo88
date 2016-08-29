package com.example.saisai.yoho.adapter.fenlei_pinlei;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.PinLeiBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saisai on 2016/8/24.
 */
public class FenleiPinleiLVAdapter extends BasePinLeiAdapter<PinLeiBean> {


    public FenleiPinleiLVAdapter(List<PinLeiBean> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if(holder==null){
            convertView = View.inflate(context, R.layout.fenlei_pinlei_lv_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        PinLeiBean pinLeiBean = list.get(position);
        holder.ivTitle.setBackgroundResource(pinLeiBean.getResId());
        holder.tvTitle.setText(pinLeiBean.getTitle());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_title)
        ImageView ivTitle;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.iv_more)
        ImageView ivMore;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
