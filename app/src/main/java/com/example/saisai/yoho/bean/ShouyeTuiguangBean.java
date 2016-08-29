package com.example.saisai.yoho.bean;

/**
 * Created by saisai on 2016/8/29.
 */
public class ShouyeTuiguangBean {
    private String title;
    private int resId;

    public ShouyeTuiguangBean(String title, int resId) {
        this.title = title;
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
