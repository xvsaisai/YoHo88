package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.adapter.GouwucheAddGoodsAdapter;
import com.example.saisai.yoho.adapter.MyExpendLVAdapter;
import com.example.saisai.yoho.bean.ItemCartBean;
import com.example.saisai.yoho.bean.QuerendingdanExpendBean;
import com.example.saisai.yoho.event.DingDanEvent;
import com.example.saisai.yoho.util.SerialiBean;
import com.example.saisai.yoho.view.MyExpandableListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DingdanquerenActivity extends AppCompatActivity {

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private android.widget.ImageView ivback;
    private android.widget.ImageView ivlocal;
    private android.widget.ListView lv;
    private android.widget.TextView tvlocal;
    private List<QuerendingdanExpendBean> expendBeenList;
    private MyExpendLVAdapter expendLVAdapter;
    private List<ItemCartBean> listCartBean;
    private GouwucheAddGoodsAdapter addGoodsAdapter;
    private MyExpandableListView headView;
    private View footView;
    private TextView tv_shangpinjine;
    private TextView tv_yunfei;
    private TextView tv_huodongjine;
    private TextView tv_3;
    private float money;
    private TextView tvshifujine;
    private float yunfei;
    private float huodong;
    private View headLocal;
    private TextView tv_local;
    private Button queren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdanqueren);

        EventBus.getDefault().register(this);

        this.tvlocal = (TextView) findViewById(R.id.tv_local);
        this.lv = (ListView) findViewById(R.id.lv);
        this.ivlocal = (ImageView) findViewById(R.id.iv_local);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvshifujine = (TextView) findViewById(R.id.tv_shifujine);
        queren = (Button) findViewById(R.id.queren_btn);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数


        initData();
        initHeadView();
        initLocation();
        initFootView();
        initAdapter();


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
//            Address address = location.getAddress();
//            Log.e("tag",address.toString());
            switch (location.getLocType()) {

                case BDLocation.TypeGpsLocation:
                case BDLocation.TypeNetWorkLocation:
                case BDLocation.TypeOffLineLocation:
                    String addrStr = location.getAddrStr();
                    String city = location.getCity();
                    String province = location.getProvince();
                    String district = location.getDistrict();

                    Log.e("tag", addrStr);
                    Log.e("tag", province + city + district);

                    tv_local.setText(addrStr);
                    break;
                default:
                    Log.e("tag", "错误");
                    break;
            }

        }
    }

    private void initFootView() {

        footView = View.inflate(this, R.layout.footer_querendingdan_lv, null);
        tv_shangpinjine = (TextView) footView.findViewById(R.id.tv_shangpinjine);
        tv_yunfei = (TextView) footView.findViewById(R.id.tv_yunfei);
        tv_huodongjine = (TextView) footView.findViewById(R.id.tv_huodongjine);
        tv_huodongjine.setVisibility(View.GONE);
        tv_3 = (TextView) footView.findViewById(R.id.tv_3);
        tv_3.setVisibility(View.GONE);
        money = 0.00f;
        yunfei = 0.00f;
        huodong = 0.00f;
        for (int i = 0; i < listCartBean.size(); i++) {
            ItemCartBean itemCartBean = listCartBean.get(i);

            money += Integer.parseInt(itemCartBean.getPrice());
        }
        tv_shangpinjine.setText("￥" + money);
        tv_yunfei.setText("￥" + yunfei);
        tv_huodongjine.setText("￥0.00" + huodong);
        tvshifujine.setText("实付金额：￥" + (money + yunfei + huodong));
    }

    private void initAdapter() {
        addGoodsAdapter = new GouwucheAddGoodsAdapter(listCartBean, this);
        lv.addHeaderView(headLocal);
        lv.addHeaderView(headView);
        lv.addFooterView(footView);
        lv.setAdapter(addGoodsAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        SerialiBean data = (SerialiBean) intent.getSerializableExtra("data");
        listCartBean = (List<ItemCartBean>) data.object;
    }

    private void initHeadView() {

        headLocal = View.inflate(this, R.layout.head_local_lv_qingdanqueren, null);
        tv_local = (TextView) headLocal.findViewById(R.id.tv_local);
        headLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DingdanquerenActivity.this, "请选择收货地址", Toast.LENGTH_SHORT).show();
            }
        });
        headView = new MyExpandableListView(this);
        headView.setGroupIndicator(null);


        expendBeenList = new ArrayList<>();
        List<QuerendingdanExpendBean.SelectBean> selectBeenList = new ArrayList<>();
        selectBeenList.add(new QuerendingdanExpendBean.SelectBean("在线支付(推荐)", true));
        selectBeenList.add(new QuerendingdanExpendBean.SelectBean("货到付款", false));
        expendBeenList.add(new QuerendingdanExpendBean("支付方式", selectBeenList));
        List<QuerendingdanExpendBean.SelectBean> selectBeenList2 = new ArrayList<>();
        selectBeenList2.add(new QuerendingdanExpendBean.SelectBean("普通快递：运费￥0.00", true));
        selectBeenList2.add(new QuerendingdanExpendBean.SelectBean("顺丰速运：运费5.00(仅支持顺丰可配送的地区)", false));
        expendBeenList.add(new QuerendingdanExpendBean("配送方式", selectBeenList2));
        List<QuerendingdanExpendBean.SelectBean> selectBeenList3 = new ArrayList<>();
        selectBeenList3.add(new QuerendingdanExpendBean.SelectBean("只工作日配送", true));
        selectBeenList3.add(new QuerendingdanExpendBean.SelectBean("工作日、双休日和节假日均送货", false));
        selectBeenList3.add(new QuerendingdanExpendBean.SelectBean("只双休日、节假日送货(工作日不送货)", false));
        expendBeenList.add(new QuerendingdanExpendBean("送货时间", selectBeenList3));

        expendLVAdapter = new MyExpendLVAdapter(expendBeenList, this);
        headView.setAdapter(expendLVAdapter);
        for (int i = 0; i < expendLVAdapter.getGroupCount(); i++) {
            headView.expandGroup(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDingdanEvent(DingDanEvent event) {

        if (event != null) {
            expendLVAdapter.notifyDataSetChanged();
        }
    }
}
