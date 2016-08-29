package com.example.saisai.yoho.event;

import java.util.List;

/**
 * Created by saisai on 2016/8/26.
 */
public class RemoveAddSearchViewEvent<T> {

    public List<T>[] list;
//    public List<T> hotList;
    public int flag=0;//i=0表示添加，i=1表示remove
    public RemoveAddSearchViewEvent(int flag,List<T> ... list){
        this.flag=flag;
        this.list=list;
    }

}
