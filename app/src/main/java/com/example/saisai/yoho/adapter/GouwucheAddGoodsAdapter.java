package com.example.saisai.yoho.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.ItemCartBean;
import com.example.saisai.yoho.dialog.GouwucheDialog;
import com.example.saisai.yoho.event.QuanxuanCheckedEvent;
import com.example.saisai.yoho.event.UpdateCartItemEvent;
import com.example.saisai.yoho.model.HttpModel;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saisai on 2016/9/3.
 */
public class GouwucheAddGoodsAdapter extends BaseSearchLVAdapter<ItemCartBean> {

//    private List<ItemCartBean> list;
//    private Map<ItemCartBean, Integer> map;

//    public GouwucheAddGoodsAdapter(List<ItemCartBean> list, Context context, Map<ItemCartBean, Integer> map) {
//        super(list, context);
//        this.map = map;
//    }

    public static final int NOMAL = 0;
    public static final int EDIT = 1;
    private int type = NOMAL;

    public GouwucheAddGoodsAdapter(List<ItemCartBean> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (getItemViewType(position) == NOMAL) {
            NomalHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_lv_gouwuche, null);
                holder = new NomalHolder(convertView);
//
                convertView.setTag(holder);
            } else {
                holder = (NomalHolder) convertView.getTag();
            }

            bindNomalData(holder, position);

        } else {
            EditHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_bianji_lv_gouwuche, null);
                holder = new EditHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (EditHolder) convertView.getTag();
            }

            bindEditData(holder, position);
        }


        return convertView;
    }

    private void bindEditData(final EditHolder holder, int position) {

        final ItemCartBean itemCartBean = list.get(position);

        holder.cbSelect.setChecked(itemCartBean.isChecked);
        Picasso.with(context).load(HttpModel.IMGHOST + itemCartBean.getImgpath()).fit().into(holder.ivContent);
        holder.tvAddNum.setText(itemCartBean.getNum());
        holder.tvCount.setText("x" + itemCartBean.getNum());
        holder.tvSelect.setText(" 颜色：" + itemCartBean.getColor() + " 尺码：" + itemCartBean.getSize());
        holder.tvPrice.setText("￥" + itemCartBean.getPrice());


        //region 设置选中事件
        final EditHolder finalHolder = holder;
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCartBean.isChecked = finalHolder.cbSelect.isChecked();

                double price = 0;
                int selectCount = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked) {
                        price += Double.parseDouble(list.get(i).getPrice());
                        selectCount++;
                    }
                }

                if (selectCount < list.size()) {
                    EventBus.getDefault().post(new QuanxuanCheckedEvent(false, price, selectCount));
                } else {
                    EventBus.getDefault().post(new QuanxuanCheckedEvent(true, price, selectCount));
                }

            }

        });
        //endregion

        //region 减
        holder.tvNumJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(holder.tvAddNum.getText().toString());
                if (num > 1) {
                    num--;
//                    holder.tvAddNum.setText(num+"");
//                    holder.tvCount.setText("x"+num);
                    itemCartBean.setNum(num + "");
                    EventBus.getDefault().post(new UpdateCartItemEvent());
                } else {
                    Toast.makeText(context, "最后一件", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //endregion

        //region 增加
        holder.tvNumJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(holder.tvAddNum.getText().toString());
                if (num < 100) {//应该网络请求判断库存是否足够
                    num++;
//                    holder.tvAddNum.setText(num+"");
//                    holder.tvCount.setText("x"+num);
                    itemCartBean.setNum(num + "");
                    EventBus.getDefault().post(new UpdateCartItemEvent());
                } else {
                    Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //endregion

        //region 修改颜色款式
        holder.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) GouwucheAddGoodsAdapter.this.context;
                GouwucheDialog dialog = new GouwucheDialog(activity, null);
                dialog.getWindow().getAttributes().width = activity.getWindowManager().getDefaultDisplay().getWidth();
                dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
                dialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setWindowAnimations(R.style.add_dialog_anim);
                dialog.show();
                dialog.setOnAddListener(new GouwucheDialog.OnAddListener() {

                    @Override
                    public void addSuccess(final int i) {


                    }
                });
            }
        });
        //endregion
    }

    private void bindNomalData(NomalHolder holder, int position) {

        final ItemCartBean itemCartBean = list.get(position);

        holder.cbSelect.setChecked(itemCartBean.isChecked);
        Picasso.with(context).load(HttpModel.IMGHOST + itemCartBean.getImgpath()).fit().into(holder.ivContent);
        holder.tvTitle.setText(itemCartBean.getTitle());
        holder.tvCount.setText("x" + itemCartBean.getNum());
        holder.tvYanse.setText("颜色：" + itemCartBean.getColor());
        holder.tvChima.setText("尺码：" + itemCartBean.getSize());
        holder.tvPrice.setText("￥" + itemCartBean.getPrice());


        //region 设置选中事件
        final NomalHolder finalHolder = holder;
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCartBean.isChecked = finalHolder.cbSelect.isChecked();

                double price = 0;
                int selectCount = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked) {
                        price += Double.parseDouble(list.get(i).getPrice());
                        selectCount++;
                    }
                }

                if (selectCount < list.size()) {
                    EventBus.getDefault().post(new QuanxuanCheckedEvent(false, price, selectCount));
                } else {
                    EventBus.getDefault().post(new QuanxuanCheckedEvent(true, price, selectCount));
                }

            }

        });

        //endregion

    }


    static class NomalHolder {
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

        NomalHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class EditHolder {
        @Bind(R.id.cb_select)
        CheckBox cbSelect;
        @Bind(R.id.iv_content)
        ImageView ivContent;
        @Bind(R.id.tv_num_jian)
        TextView tvNumJian;
        @Bind(R.id.tv_add_num)
        TextView tvAddNum;
        @Bind(R.id.tv_num_jia)
        TextView tvNumJia;
        @Bind(R.id.tv_select)
        TextView tvSelect;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_count)
        TextView tvCount;

        EditHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


}
