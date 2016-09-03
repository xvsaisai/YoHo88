package com.example.saisai.yoho.fragment.pinpai_xiangqing;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.activity.GoodsXiangqingActivity;
import com.example.saisai.yoho.adapter.BaseSearchLVAdapter;
import com.example.saisai.yoho.bean.FllowItemBean;
import com.example.saisai.yoho.fragment.LazyBaseFragment;
import com.example.saisai.yoho.model.HttpModel;
import com.example.saisai.yoho.view.PullLoadGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by saisai on 2016/8/27.
 */
public abstract class PinpaiXiangqingBaseFragment extends LazyBaseFragment implements PullLoadGridView.OnPullDownListener, PullLoadGridView.OnPullUpListener, PullLoadGridView.OnScrollListener, AdapterView.OnItemClickListener {


    protected PullLoadGridView gv;
    protected List<FllowItemBean.FollowBean.GoodsBean> list;
    protected BaseSearchLVAdapter adapter;


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
        gv.setSelector(new BitmapDrawable());

        gv.setOnItemClickListener(this);
    }

    public boolean isDrag;

    @Override
    protected void initData() {
        list = new ArrayList<FllowItemBean.FollowBean.GoodsBean>();

        loadData();
    }

    protected abstract void loadData();

    @Override
    protected void initAdapter() {
        adapter = new BaseSearchLVAdapter<FllowItemBean.FollowBean.GoodsBean>(list, getContext()) {


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
                holder.tv_price.setText(list.get(position).getDistance() + "");
                holder.tv_desc.setText("啊方便时候搜服好哦啊手");

//                if (!isDrag) {
                Picasso.with(getContext()).load(HttpModel.IMGHOST + list.get(position).getGoodsimg()).fit().into(holder.iv);
//                } else {
//                    holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    holder.iv.setImageResource(R.drawable.goodslist_placeholder);
//                }


                return convertView;
            }

            class Holder {
                TextView tv_desc, tv_price;
                ImageView iv;
            }
        };
        gv.setAdapter(adapter);
        gv.setPullDownUsable(false);
    }

    public void shengxvData() {
        Collections.sort(list, new Comparator<FllowItemBean.FollowBean.GoodsBean>() {
            @Override
            public int compare(FllowItemBean.FollowBean.GoodsBean lhs, FllowItemBean.FollowBean.GoodsBean rhs) {

                int i = Integer.parseInt(lhs.getDistance()) - Integer.parseInt(rhs.getDistance());
                if (i > 0)
                    return 1;
                else if (i < 0)
                    return -1;
                return 0;
            }
        });
        adapter.notifyDataSetChanged();
        gv.setSelection(0);
    }

    public void jiangxvData() {
        Collections.sort(list, new Comparator<FllowItemBean.FollowBean.GoodsBean>() {
            @Override
            public int compare(FllowItemBean.FollowBean.GoodsBean lhs, FllowItemBean.FollowBean.GoodsBean rhs) {

                int i = Integer.parseInt(rhs.getDistance()) - Integer.parseInt(lhs.getDistance());
                if (i > 0)
                    return 1;
                else if (i < 0)
                    return -1;
                return 0;
            }
        });
        adapter.notifyDataSetChanged();
        gv.setSelection(0);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), GoodsXiangqingActivity.class);
        intent.putExtra("bean", list.get(position));
        startActivity(intent);
    }
}
