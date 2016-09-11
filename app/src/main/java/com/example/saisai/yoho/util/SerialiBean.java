package com.example.saisai.yoho.util;

import java.io.Serializable;

/**
 * Created by saisai on 2016/9/9.
 */
public class SerialiBean implements Serializable {
    public Object object;

    public SerialiBean(Object object) {
        this.object = object;
    }
}
