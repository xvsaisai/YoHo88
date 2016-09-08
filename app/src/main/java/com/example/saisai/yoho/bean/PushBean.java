package com.example.saisai.yoho.bean;

/**
 * Created by saisai on 2016/9/8.
 */
public class PushBean {

    String title;
    String desc;
    int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


    public PushBean() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
