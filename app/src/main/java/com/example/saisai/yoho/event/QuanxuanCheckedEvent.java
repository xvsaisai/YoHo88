package com.example.saisai.yoho.event;

/**
 * Created by saisai on 2016/9/3.
 */
public class QuanxuanCheckedEvent {
    public boolean isChecked;
    public double price;
    public int selectCount;

    public QuanxuanCheckedEvent(boolean isChecked, double price, int selectCount) {
        this.isChecked = isChecked;
        this.price = price;
        this.selectCount = selectCount;
    }
}
