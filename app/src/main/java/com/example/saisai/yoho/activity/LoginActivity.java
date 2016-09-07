package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.model.UserRequestState;
import com.example.saisai.yoho.user.User;
import com.example.saisai.yoho.util.LocalCartUtils;
import com.example.saisai.yoho.util.SPUtils;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, PlatformActionListener {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;
    private Intent intent;
    private int state;
    private android.support.design.widget.TextInputLayout name;
    private android.support.design.widget.TextInputLayout pwd;
    private android.widget.ImageView ivqq;
    private android.widget.ImageView ivweixin;
    private android.widget.ImageView ivxinlang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.ivxinlang = (ImageView) findViewById(R.id.iv_xinlang);
        ivxinlang.setOnClickListener(this);
        this.ivweixin = (ImageView) findViewById(R.id.iv_weixin);
        ivweixin.setOnClickListener(this);
        this.ivqq = (ImageView) findViewById(R.id.iv_qq);
        ivqq.setOnClickListener(this);
        this.login = (Button) findViewById(R.id.login);
        this.pwd = (TextInputLayout) findViewById(R.id.pwd);
        this.password = (EditText) findViewById(R.id.password);
        this.name = (TextInputLayout) findViewById(R.id.name);
        this.username = (EditText) findViewById(R.id.username);
        ButterKnife.bind(this);
        intent = getIntent();
        //获取操作状态
        state = intent.getIntExtra("state", -1);
    }

    @OnClick(R.id.login)
    public void onClick() {

        if (!TextUtils.isEmpty(username.getText()) && !TextUtils.isEmpty(password.getText())) {

            User user = new User();
            user.name = "张三";
            user.loginTime = System.currentTimeMillis();
            user.expireTime = 1000 * 60;
            user.ic_path = "";
            user.token = "登陆了";

            MyApplication.user = user;
            //保存用户信息到本地
            SPUtils.save("user", new Gson().toJson(MyApplication.user));
            //登录成功，首先将本地购物车中的商品上传到服务器，

            //删除本地购物车中的商品
            LocalCartUtils.clearCart();

            chuli();
        } else {
            Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    //登陆完成后返回上一层
    private void chuli() {

        switch (state) {

            case UserRequestState.YIDONG_STATE:
                setResult(RESULT_OK);
                finish();
                break;
            case UserRequestState.JIESUAN_STATE:
                setResult(RESULT_OK);
                finish();
                break;
            case UserRequestState.WODE:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_qq:
                loginQQ();
                break;
            case R.id.iv_weixin:
                loginWeixin();
                break;
            case R.id.iv_xinlang:
                loginXInlang();

                break;
        }
    }

    private void loginXInlang() {

        ShareSDK.initSDK(this);
        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.authorize();
    }

    private void loginWeixin() {

        ShareSDK.initSDK(this);
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.authorize();
    }

    private void loginQQ() {
        ShareSDK.initSDK(this);
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.authorize();

    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.e("tag", "登录成功");
        User user = new User();

        //得到用户信息
        user.token = platform.getDb().getToken();
        user.ic_path = platform.getDb().getUserIcon();
        user.name = platform.getDb().getUserName();
        user.id = platform.getDb().getUserId();
        user.expireTime = platform.getDb().getExpiresIn();
        user.loginTime = platform.getDb().getExpiresTime();

        //保存到SP
        String s = new Gson().toJson(user);
        SPUtils.save("user", s);
        //清空本地购物车信息
        LocalCartUtils.clearCart();
        //同步MyApplication
        MyApplication.user = user;
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("tag", "登录失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e("tag", "取消登录");
    }
}
