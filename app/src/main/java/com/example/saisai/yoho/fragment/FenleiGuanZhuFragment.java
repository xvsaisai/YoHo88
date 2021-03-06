package com.example.saisai.yoho.fragment;

import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
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
import com.example.saisai.yoho.view.PullListView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisai on 2016/8/24.
 */

//public class FenleiGuanZhuFragment extends Fragment {
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        TextView tv=new TextView(getContext());
//        tv.setText(getClass().getSimpleName());
//        return tv;
//    }
//}
public class FenleiGuanZhuFragment extends LazyBaseFragment implements PullListView.OnPullListener {

    private View inflate;
    private ImageView iv_head;
    private TextView tv_name;
    private TextView tv_desc;
    private TextView tv_login;
    private PullListView lv_guanzhu;
    private List<FenleiGuanzhuBean.DataBean.ListBean> listBeen;
    private BaseSearchLVAdapter adapter;
    private View headView;
    private ImageView iv_load;


    android.os.Handler handler=new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fenlei_guanzhu;
    }

    @Override
    protected void initView() {

        iv_head = findView(R.id.iv_head);
        tv_name = findView(R.id.tv_name);
        tv_desc = findView(R.id.tv_desc);
        tv_login = findView(R.id.tv_login);
        lv_guanzhu = findView(R.id.lv_guanzhu);

        lv_guanzhu.setOnPullListener(this);

        lv_guanzhu.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("tag",scrollState+"====scrollState");

//                if(scrollState!=SCROLL_STATE_IDLE){
//                    isdrag=true;
//                }else {
//                    isdrag=false;
//                    adapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
//        headView = View.inflate(activity, R.layout.head_lv_load,null);
//        iv_load = (ImageView) headView.findViewById(R.id.iv_load);
//        lv_guanzhu.addHeaderView(headView);

    }

    boolean isdrag;

    @Override
    public void initData() {


        listBeen = new ArrayList<>();
        loadData(HttpModel.FENLEI_GUANZHU_REFRESH, "");
    }

    public void loadData(String url, String s){

        new HttpUtils().loadData(url,"").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {

                FenleiGuanzhuBean fenleiGuanzhuBean = new Gson().fromJson(content, FenleiGuanzhuBean.class);
                List<FenleiGuanzhuBean.DataBean.ListBean> list = fenleiGuanzhuBean.getData().getList();
                listBeen.clear();
                listBeen.addAll(list);
//                adapter.notifyDataSetChanged();
                lv_guanzhu.setAdapter(adapter);
//                lv_guanzhu.setSelection(1);
            }

            @Override
            public void loadFailed(String msg) {
                Toast.makeText(getContext(), "关注：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initAdapter() {
//        super.initAdapter();

        adapter = new BaseSearchLVAdapter(listBeen, getContext()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                Holder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_fenlei_guanzhu_lv, null);
                    holder = new Holder();
                    holder.iv_content_head = (ImageView) convertView.findViewById(R.id.iv_content_head);
                    holder.getIv_content_like = (ImageView) convertView.findViewById(R.id.iv_content_like);
                    holder.iv_content_desc1 = (ImageView) convertView.findViewById(R.id.iv_content_desc1);
                    holder.iv_content_desc2 = (ImageView) convertView.findViewById(R.id.iv_content_desc2);
                    holder.iv_content_desc3 = (ImageView) convertView.findViewById(R.id.iv_content_desc3);
                    holder.tv_content_name = (TextView) convertView.findViewById(R.id.tv_content_name);
                    holder.tv_content_time = (TextView) convertView.findViewById(R.id.tv_content_time);
                    holder.tv_content_flag = (TextView) convertView.findViewById(R.id.tv_content_flag);
                    holder.tv_content_prince1 = (TextView) convertView.findViewById(R.id.tv_content_prince1);
                    holder.tv_content_old1 = (TextView) convertView.findViewById(R.id.tv_content_old1);
                    holder.tv_content_prince2 = (TextView) convertView.findViewById(R.id.tv_content_prince2);
                    holder.tv_content_old2 = (TextView) convertView.findViewById(R.id.tv_content_old2);
                    holder.tv_content_prince3 = (TextView) convertView.findViewById(R.id.tv_content_prince3);
                    holder.tv_content_old3 = (TextView) convertView.findViewById(R.id.tv_content_old3);

                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }

                FenleiGuanzhuBean.DataBean.ListBean bean = (FenleiGuanzhuBean.DataBean.ListBean) list.get(position);

                holder.tv_content_name.setText(bean.getBrand_name());
                holder.tv_content_time.setText(bean.getDate());
                if (bean.getBrand_type().equals("recommend")) {
                    holder.tv_content_flag.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_content_flag.setVisibility(View.GONE);
                }

                Picasso.with(context).load(bean.getBrand_img().split("\\?")[0]).fit().into(holder.iv_content_head);
//                Log.e("img",bean.getBrand_img().split("\\?")[0]);

                switch (bean.getProduct().size()) {
                    case 3:
                        holder.tv_content_prince3.setText(bean.getProduct().get(2).getSale_price() + "");
                        holder.tv_content_old3.setText(bean.getProduct().get(2).getMarket_price() + "");
                        holder.tv_content_old3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    case 2:
                        holder.tv_content_prince2.setText(bean.getProduct().get(1).getSale_price() + "");
                        holder.tv_content_old2.setText(bean.getProduct().get(1).getMarket_price() + "");
                        holder.tv_content_old2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    case 1:
                        holder.tv_content_prince1.setText(bean.getProduct().get(0).getSale_price() + "");
                        holder.tv_content_old1.setText(bean.getProduct().get(0).getMarket_price() + "");
                        holder.tv_content_old1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                }

//                holder.iv_content_desc3.setImageResource(R.mipmap.ic_launcher);
//                holder.iv_content_desc2.setImageResource(R.mipmap.ic_launcher);
//                holder.iv_content_desc1.setImageResource(R.mipmap.ic_launcher);
//
//                if(!isdrag){

                Picasso.with(context).load(bean.getProduct().get(2).getProduct_img().split("\\?")[0]).fit().into(holder.iv_content_desc3);
                Picasso.with(context).load(bean.getProduct().get(1).getProduct_img().split("\\?")[0]).fit().into(holder.iv_content_desc2);
                Picasso.with(context).load(bean.getProduct().get(0).getProduct_img().split("\\?")[0]).fit().into(holder.iv_content_desc1);
//                }
                return convertView;
            }

            class Holder {
                ImageView iv_content_head, getIv_content_like, iv_content_desc1, iv_content_desc2, iv_content_desc3;
                TextView tv_content_name, tv_content_time, tv_content_flag, tv_content_prince1, tv_content_old1, tv_content_prince3, tv_content_old3, tv_content_prince2, tv_content_old2;
            }
        };


//        lv_guanzhu.setSelection(1);
    }


    @Override
    public void onSuccess() {
        new HttpUtils().loadData(HttpModel.FENLEI_GUANZHU_REFRESH,"").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {

                FenleiGuanzhuBean fenleiGuanzhuBean = new Gson().fromJson(content, FenleiGuanzhuBean.class);
                List<FenleiGuanzhuBean.DataBean.ListBean> list = fenleiGuanzhuBean.getData().getList();
                listBeen.clear();
                listBeen.addAll(list);
                adapter.notifyDataSetChanged();
                lv_guanzhu.pullSuccess();
//                lv_guanzhu.setSelection(1);
            }

            @Override
            public void loadFailed(String msg) {
                Toast.makeText(getContext(), "关注：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onError() {
        lv_guanzhu.pullSuccess();
        Toast.makeText(getContext(), "刷新失败。。。", Toast.LENGTH_SHORT).show();
    }

}
