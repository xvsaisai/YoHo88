package com.example.saisai.yoho.bean;

import java.util.List;

/**
 * Created by saisai on 2016/9/9.
 */
public class QuerendingdanExpendBean {
    public String title;
    public List<SelectBean> list;

    public QuerendingdanExpendBean(String title, List<SelectBean> list) {
        this.title = title;
        this.list = list;
    }

    public static class SelectBean {
        public String desc;
        public boolean isSelect;

        public SelectBean(String desc, boolean isSelect) {
            this.desc = desc;
            this.isSelect = isSelect;
        }
    }
}
