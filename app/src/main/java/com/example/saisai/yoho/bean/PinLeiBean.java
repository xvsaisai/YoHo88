package com.example.saisai.yoho.bean;

/**
 * Created by saisai on 2016/8/24.
 */
public class PinLeiBean {

    private int resId;
    private String title;

    public PinLeiBean(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
