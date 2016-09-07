package com.example.saisai.yoho.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.activity.LoginActivity;
import com.example.saisai.yoho.base.BaseFrament;
import com.example.saisai.yoho.model.UserRequestState;
import com.example.saisai.yoho.user.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saisai on 2016/8/23.
 */
public class WodeFragment extends BaseFrament implements View.OnClickListener {

    private android.widget.Button login;
    private android.widget.RelativeLayout head;
    private android.widget.ImageView ivshezhi;
    private android.widget.ImageView ivxiaoxi;
    private android.widget.TextView shangpin;
    private android.widget.TextView pinpai;
    private android.widget.TextView jilu;
    private android.widget.LinearLayout shoucang;
    private android.widget.ImageView ivgonggao;
    private android.widget.ViewFlipper flipper;
    private android.widget.LinearLayout zimu;
    private de.hdodenhof.circleimageview.CircleImageView ivhead;
    private TextView tvname;
    private Animation flipperIn;
    private Animation flipperOut;
    private User user = null;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {

        View inflate = inflater.inflate(R.layout.fragment_wode, null);
        this.tvname = (TextView) inflate.findViewById(R.id.tv_name);
        this.ivhead = (CircleImageView) inflate.findViewById(R.id.iv_head);
        this.zimu = (LinearLayout) inflate.findViewById(R.id.zimu);
        this.flipper = (ViewFlipper) inflate.findViewById(R.id.flipper);
        this.ivgonggao = (ImageView) inflate.findViewById(R.id.iv_gonggao);
        this.shoucang = (LinearLayout) inflate.findViewById(R.id.shoucang);
        this.jilu = (TextView) inflate.findViewById(R.id.jilu);
        this.pinpai = (TextView) inflate.findViewById(R.id.pinpai);
        this.shangpin = (TextView) inflate.findViewById(R.id.shangpin);
        this.ivxiaoxi = (ImageView) inflate.findViewById(R.id.iv_xiaoxi);
        this.ivshezhi = (ImageView) inflate.findViewById(R.id.iv_shezhi);
        this.head = (RelativeLayout) inflate.findViewById(R.id.head);
        this.login = (Button) inflate.findViewById(R.id.login);

        if (MyApplication.checkLogin()) {
            user = MyApplication.user;
            Picasso.with(activity).load(user.ic_path).fit().into(ivhead);
            tvname.setText(user.name);
            login.setVisibility(View.GONE);
            ivhead.setVisibility(View.VISIBLE);
            tvname.setVisibility(View.VISIBLE);
        }
        initAnimation();
        initListener();


        return inflate;
    }

    private void initAnimation() {
        flipper.setAutoStart(true);
        flipper.setInAnimation(AnimationUtils.loadAnimation(activity, R.anim.zimu_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(activity, R.anim.zimu_out));
    }

    private void initListener() {

        login.setOnClickListener(this);
        shangpin.setOnClickListener(this);
        pinpai.setOnClickListener(this);
        jilu.setOnClickListener(this);
        zimu.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login:
                login();
                break;
            case R.id.shangpin:
                shangpin();
                break;
            case R.id.pinpai:
                pinpai();
                break;
            case R.id.jilu:
                jilu();
                break;
            case R.id.zimu:
                zimu();
                break;
        }
    }


    private void zimu() {

        Toast.makeText(activity, "通知公告", Toast.LENGTH_SHORT).show();
    }

    private void jilu() {


    }

    private void pinpai() {


    }

    private void shangpin() {


    }

    private void login() {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("state", UserRequestState.WODE);
        startActivityForResult(intent, UserRequestState.WODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK && requestCode == UserRequestState.WODE) {

            Toast.makeText(activity, "登录成功", Toast.LENGTH_SHORT).show();
            if (MyApplication.checkLogin()) {//登陆成功
                user = MyApplication.user;
                Picasso.with(activity).load(user.ic_path).fit().into(ivhead);
                tvname.setText(user.name);
                login.setVisibility(View.GONE);
                ivhead.setVisibility(View.VISIBLE);
                tvname.setVisibility(View.VISIBLE);
            } else {
                login.setVisibility(View.VISIBLE);
                ivhead.setVisibility(View.GONE);
                tvname.setVisibility(View.GONE);
            }
        }
    }
}
