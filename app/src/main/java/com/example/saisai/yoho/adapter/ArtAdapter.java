package com.example.saisai.yoho.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.ArBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saisai on 2016/9/5.
 */
public class ArtAdapter extends BaseAdapter {

    private List<ArBean.DataBean.ListBean.ArtListBean> list;
    private Context context;

    public ArtAdapter(List<ArBean.DataBean.ListBean.ArtListBean> list, Context context) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_lv_guang, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArBean.DataBean.ListBean.ArtListBean artListBean = list.get(position);
        Picasso.with(context).load(artListBean.getAuthor().getAvatar()).fit().into(holder.ivHead);
        holder.tvName.setText(artListBean.getAuthor().getName());
        Picasso.with(context).load(artListBean.getSrc()).fit().into(holder.ivContent);

        holder.tvTitle.setText(artListBean.getTitle());
        holder.tvDesc.setText(artListBean.getTitle());
        holder.tvTime.setText(artListBean.getPublish_time());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_head)
        CircleImageView ivHead;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.top)
        LinearLayout top;
        @Bind(R.id.iv_content)
        ImageView ivContent;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.iv_like)
        ImageView ivLike;
        @Bind(R.id.iv_fenxiang)
        ImageView ivFenxiang;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
