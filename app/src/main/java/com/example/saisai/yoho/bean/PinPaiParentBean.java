package com.example.saisai.yoho.bean;

import java.util.List;

/**
 * Created by saisai on 2016/8/25.
 */
public class PinPaiParentBean {
    private String title;
    private List<PinPaiBean.BrandBean> list;

    public PinPaiParentBean(String title, List<PinPaiBean.BrandBean> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PinPaiBean.BrandBean> getList() {
        return list;
    }

    public void setList(List<PinPaiBean.BrandBean> list) {
        this.list = list;
    }
}
