package com.example.saisai.yoho.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.ShangPinXiangQingBean;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saisai on 2016/9/3.
 */
public class GouwucheAddGoodsAdapter extends BaseSearchLVAdapter<ShangPinXiangQingBean.GoodsBean> {

    private List<ShangPinXiangQingBean.GoodsBean> list;
    private Map<ShangPinXiangQingBean.GoodsBean, Integer> map;
    private Context context;

    public GouwucheAddGoodsAdapter(List<ShangPinXiangQingBean.GoodsBean> list, Context context, Map<ShangPinXiangQingBean.GoodsBean, Integer> map) {
        super(list, context);
        this.map = map;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_lv_gouwuche, null);
            holder = new ViewHolder(convertView);
//
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ViewHolder finalHolder = holder;
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.cbSelect.setChecked(!finalHolder.cbSelect.isChecked());
            }
        });


        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.cb_select)
        CheckBox cbSelect;
        @Bind(R.id.iv_content)
        ImageView ivContent;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_yanse)
        TextView tvYanse;
        @Bind(R.id.tv_chima)
        TextView tvChima;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
