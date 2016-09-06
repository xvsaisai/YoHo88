package com.example.saisai.yoho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saisai.yoho.MyApplication;
import com.example.saisai.yoho.R;
import com.example.saisai.yoho.model.UserRequestState;
import com.example.saisai.yoho.user.User;
import com.example.saisai.yoho.util.LocalCartUtils;
import com.example.saisai.yoho.util.SPUtils;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;
    private Intent intent;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        }
    }
}
