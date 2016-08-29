package com.example.saisai.yoho.util;

import android.content.SharedPreferences;

import com.example.saisai.yoho.MyApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by saisai on 2016/8/22.
 */
public class SPUtils {

    //保存设置信息
    public static void save(String key,String value){
        MyApplication.app.getSharedPreferences("Config",0).edit().putString(key,value).commit();

    }

    //获取设置信息
    public static String get(String key){
        return MyApplication.app.getSharedPreferences("Config",0).getString(key,"");
    }

    //保存搜索记录
    public static void setSearchHistroy(String key,String value){
        MyApplication.app.getSharedPreferences("SearchHistroy", 0).edit().putString(key,value).commit();

    }

    //获取搜索历史记录
    public static List<String> getSearchHistroy(){
        Map<String, String> searchHistroy = (Map<String, String>) MyApplication.app.getSharedPreferences("SearchHistroy", 0).getAll();
        Set<String> strings = searchHistroy.keySet();
        Iterator<String> iterator = strings.iterator();
        List<String> list=new ArrayList<>();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    //删除历史搜索记录
    public static void clearSearchHistory(){
        SharedPreferences.Editor searchHistroy = MyApplication.app.getSharedPreferences("SearchHistroy", 0).edit();
        searchHistroy.clear();
        searchHistroy.commit();
    }
}
