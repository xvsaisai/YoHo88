package com.example.saisai.yoho.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.ShangPinXiangQingBean;
import com.example.saisai.yoho.event.UpdateCartCountEvent;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.LocalCartUtils;
import com.example.saisai.yoho.util.MyLog;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/9/1.
 */
public class GouwucheDialog extends BaseDialog {

    private ShangPinXiangQingBean shangPinXiangQingBean;

    public ImageView iv_content;
    public TextView tv_price;
    public RadioButton rd_ys_1;
    public RadioButton rd_ys_2;
    public RadioButton rd_ys_3;
    public RadioButton rd_ys_4;
    public RadioButton rd_cm_1;
    public RadioButton rd_cm_2;
    public RadioButton rd_cm_3;
    public TextView tv_num_jian;
    public TextView tv_num_jia;
    public TextView tv_show_num;
    public Button btn_queding;

    public List<RadioButton> yanseList = new ArrayList<>();
    public List<RadioButton> chicunList = new ArrayList<>();
    public TextView tv_title;
    private TextView tv_num_shengyu;

    public GouwucheDialog(Context context, ShangPinXiangQingBean shangPinXiangQingBean) {
        super(context);
        View inflate = View.inflate(context, R.layout.dialog_add_gouwuche, null);
        this.shangPinXiangQingBean = shangPinXiangQingBean;
        setContentView(inflate);

        init(inflate);
        initData();
        initListener();
    }

    private void initData() {

        tv_title.setText(shangPinXiangQingBean.getGoods().get(0).getTitle());
        tv_price.setText(shangPinXiangQingBean.getGoods().get(0).getPrice());
        Picasso.with(getContext()).load(HttpModel.IMGHOST + shangPinXiangQingBean.getImgvale().get(0).getImgpath()).fit().into(iv_content);
    }

    private void init(View inflate) {
        iv_content = (ImageView) inflate.findViewById(R.id.iv_content);

        tv_price = (TextView) inflate.findViewById(R.id.tv_price);
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        rd_ys_1 = (RadioButton) inflate.findViewById(R.id.rd_ys_1);
        rd_ys_2 = (RadioButton) inflate.findViewById(R.id.rd_ys_2);
        rd_ys_3 = (RadioButton) inflate.findViewById(R.id.rd_ys_3);
        rd_ys_4 = (RadioButton) inflate.findViewById(R.id.rd_ys_4);
        rd_cm_1 = (RadioButton) inflate.findViewById(R.id.rd_cm_1);
        rd_cm_2 = (RadioButton) inflate.findViewById(R.id.rd_cm_2);
        rd_cm_3 = (RadioButton) inflate.findViewById(R.id.rd_cm_3);
        tv_num_jian = (TextView) inflate.findViewById(R.id.tv_num_jian);
        tv_num_jia = (TextView) inflate.findViewById(R.id.tv_num_jia);
        tv_show_num = (TextView) inflate.findViewById(R.id.tv_show_num);
        tv_show_num.setText("1");
        tv_num_shengyu = (TextView) inflate.findViewById(R.id.tv_num_shengyu);
        tv_num_shengyu.setText("剩余4件");
        btn_queding = (Button) inflate.findViewById(R.id.btn_queding);

        yanseList.add(rd_ys_1);
        yanseList.add(rd_ys_2);
        yanseList.add(rd_ys_3);
        yanseList.add(rd_ys_4);

        chicunList.add(rd_cm_1);
        chicunList.add(rd_cm_2);
        chicunList.add(rd_cm_3);

    }

    private void initListener() {
        btn_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = false;
                for (RadioButton rd : yanseList) {
                    if (rd.isChecked()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    Toast.makeText(getContext(), "颜色", Toast.LENGTH_SHORT).show();
                    return;
                }
                flag = false;
                for (RadioButton rd : chicunList) {
                    if (rd.isChecked()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    Toast.makeText(getContext(), "请选择尺寸", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tv_show_num.equals("0")) {
                    Toast.makeText(getContext(), "请选择要添加的数量", Toast.LENGTH_SHORT).show();
                }

                List<ShangPinXiangQingBean.GoodsBean> goods = shangPinXiangQingBean.getGoods();

                if (MyApplication.app.checkLogin()) {
                    dismiss();
//                    new HttpUtils().loadData().setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
//                        @Override
//                        public void loadSuccess(String content) {
//
//                        }
//
//                        @Override
//                        public void loadFailed(String msg) {
//
//                        }
//                    });
                } else {
                    dismiss();
                    //添加入本地购物车
                    LocalCartUtils.save(goods.get(0));
                    MyApplication.count += 1;//改变购物车中物品数量
                    //通知主界面购物车中数量发生改变
                    EventBus.getDefault().post(new UpdateCartCountEvent());
                }

                if (onAddListener != null) {
                    String text = tv_show_num.getText().toString();
                    onAddListener.addSuccess(Integer.parseInt(text));
                    setCurrentNum(getCurrentNum() - Integer.parseInt(text));
//                    tv_show_num.setText(Integer.parseInt(tv_show_num.getText().toString())+tv_show_num);
                }

            }
        });

        tv_num_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectNum = Integer.parseInt(tv_show_num.getText().toString());
                if (selectNum + 1 <= getCurrentNum()) {
                    tv_show_num.setText((++selectNum) + "");
//                    tv_show_num.invalidate();
//                    MyLog.m(Thread.currentThread().getName());

                } else {
                    Toast.makeText(getContext(), "库存不足", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tv_num_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String num_shengyu = tv_num_shengyu.getText().toString();
//                int curentNum = Integer.parseInt(num_shengyu.substring(2, 3));
                int selectNum = Integer.parseInt(tv_show_num.getText().toString());
                if (!(selectNum - 1 < 0)) {
                    tv_show_num.setText((--selectNum) + "");
                    MyLog.m(Thread.currentThread().getName());
                    tv_show_num.invalidate();
                }
            }
        });
    }

    public interface OnAddListener {
        void addSuccess(int i);
    }

    OnAddListener onAddListener;

    public void setOnAddListener(OnAddListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    public int getCurrentNum() {
        String num_shengyu = tv_num_shengyu.getText().toString();
        return Integer.parseInt(num_shengyu.substring(2, num_shengyu.length() - 1));
    }

    public void setCurrentNum(int num) {
        tv_num_shengyu.setText("剩余" + num + "件");
    }

}
