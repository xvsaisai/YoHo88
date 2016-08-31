package com.example.saisai.yoho.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.HomeBean;
import com.example.saisai.yoho.model.HttpModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/8/30.
 */
public class HomeListAdapter extends BaseSearchLVAdapter<List<HomeBean.BrandBean>> {

    public boolean isDrag = false;

    public void setDrag(boolean isDrag) {
        this.isDrag = isDrag;
    }
    public HomeListAdapter(List<List<HomeBean.BrandBean>> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_fragment_shouye_home, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        List<HomeBean.BrandBean> brandBeen = list.get(position);
        String name = brandBeen.get(0).getName();
        List<ImageView> ivs = new ArrayList<>();
        ivs.add(holder.ivImg1);
        ivs.add(holder.ivImg2);
        ivs.add(holder.ivImg3);
        ivs.add(holder.ivImg4);
        ivs.add(holder.ivImg5);
        ivs.add(holder.ivImg6);
        holder.tv_Title.setText(name);
        for (int i = 0; i < 6; i++) {
            HomeBean.BrandBean brandBean = brandBeen.get(i);
            String imgpath = brandBean.getImgpath();
            if (!isDrag) {
                Picasso.with(context).load(HttpModel.IMGHOST + imgpath).fit().placeholder(R.drawable.product_icon_loading_default).into(ivs.get(i));
            } else {
                ivs.get(i).setImageResource(R.drawable.product_icon_loading_default);
            }
        }
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_Title;
        @Bind(R.id.iv_img1)
        ImageView ivImg1;
        @Bind(R.id.iv_img2)
        ImageView ivImg2;
        @Bind(R.id.iv_img3)
        ImageView ivImg3;
        @Bind(R.id.iv_img4)
        ImageView ivImg4;
        @Bind(R.id.iv_img5)
        ImageView ivImg5;
        @Bind(R.id.iv_img6)
        ImageView ivImg6;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
