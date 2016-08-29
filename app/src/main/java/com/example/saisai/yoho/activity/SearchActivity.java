package com.example.saisai.yoho.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.PinPaiBean;
import com.example.saisai.yoho.view.MySearchView;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saisai on 2016/8/29.
 */
public class SearchActivity extends BaseActivity {


    private TextView tv_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySearchView searchView=new MySearchView(this);

        searchView.init(new ArrayList<PinPaiBean.BrandBean>(),new ArrayList<PinPaiBean.BrandBean>());
        setContentView(searchView.rootView);
        tv_cancel = (TextView) searchView.rootView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.main_activity_fromleft, R.anim.pinpai_xiangqing_activity_out);
    }

}
