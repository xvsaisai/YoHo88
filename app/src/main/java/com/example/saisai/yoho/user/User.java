package com.example.saisai.yoho.user;

/**
 * Created by saisai on 2016/9/1.
 */
public class User {
    public int id;
    public String name;
    public String ic_path;//头像
    public String token;//用户两次登录（每次登录成功之后，服务器就会自动生成一个新的）

    //每次网络请求都需要携带Token，如果Token不一致 Token过期
    public long loginTime;//登录时间
    public long expireTime;//有效期

}
