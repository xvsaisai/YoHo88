package com.example.saisai.yoho.fragment.pinpai_xiangqing;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.BaseSearchLVAdapter;
import com.example.saisai.yoho.bean.FenleiGuanzhuBean;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.util.HttpUtils;
import com.example.saisai.yoho.util.MyLog;
import com.example.saisai.yoho.view.PullLoadGridView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/31.
 */
public class ZuixinFragment extends PinpaiXiangqingBaseFragment implements PullLoadGridView.OnPullDownListener, PullLoadGridView.OnPullUpListener, PullLoadGridView.OnScrollListener {
    private PullLoadGridView gv;
    private List<FenleiGuanzhuBean.DataBean.ListBean.ProductBean> list;
    private BaseSearchLVAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pinpaixiangqing;
    }

    @Override
    protected void initView() {
        gv = findView(R.id.gv_xiangqing);
        gv.setOnPullDownListener(this);
        gv.setOnPullUpListener(this);
        gv.setOnScrollListener(this);
    }

    public boolean isDrag;

    @Override
    protected void initData() {
        list = new ArrayList<FenleiGuanzhuBean.DataBean.ListBean.ProductBean>();
        adapter = new BaseSearchLVAdapter<FenleiGuanzhuBean.DataBean.ListBean.ProductBean>(list, getContext()) {


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                Holder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.item_pinpai_sort_gv, null);
                    holder = new Holder();
                    holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                    holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                    holder.iv = (ImageView) convertView.findViewById(R.id.iv_content);

                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.tv_price.setText(list.get(position).getSale_price() + "");
                holder.tv_desc.setText("啊方便时候搜服好哦啊手");

                if (!isDrag) {
                    Picasso.with(getContext()).load(list.get(position).getProduct_img().split("\\?")[0]).fit().into(holder.iv);
                } else {
                    holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.iv.setImageResource(R.drawable.goodslist_placeholder);
                }


                return convertView;
            }

            class Holder {
                TextView tv_desc, tv_price;
                ImageView iv;
            }
        };
        gv.setAdapter(adapter);
        gv.setPullDownUsable(false);
        gv.setSelector(new BitmapDrawable());
        loadData();
    }

    private void loadData() {

        new HttpUtils().loadData(HttpModel.FENLEI_GUANZHU_REFRESH, "").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {

            @Override
            public void loadSuccess(final String content) {
                FenleiGuanzhuBean fenleiGuanzhuBean = new Gson().fromJson(content, FenleiGuanzhuBean.class);
                List<FenleiGuanzhuBean.DataBean.ListBean> temp = fenleiGuanzhuBean.getData().getList();
                MyLog.m("=============" + temp.toString());
                list.clear();
                for (int i = 0; i < temp.size(); i++) {
                    FenleiGuanzhuBean.DataBean.ListBean bean = temp.get(i);
                    List<FenleiGuanzhuBean.DataBean.ListBean.ProductBean> product = bean.getProduct();
                    for (int j = 0; j < product.size(); j++) {

                        FenleiGuanzhuBean.DataBean.ListBean.ProductBean productBean = product.get(j);
                        list.add(productBean);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void loadFailed(String msg) {

            }
        });

    }

    @Override
    public void pullDown() {
        loadData();
        gv.pullDownSuccess();
    }

    @Override
    public void pullUp() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "加载完成", Toast.LENGTH_SHORT).show();
                ;
                gv.pullUpSuccess();
            }
        }, 2000);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                isDrag = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                isDrag = false;
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
