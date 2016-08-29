package com.example.saisai.yoho.adapter.fenlei_pinlei;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saisai.yoho.bean.PinLeiIChildtemBean;

import java.util.List;

/**
 * Created by saisai on 2016/8/24.
 */
public class FenleiPinleiChildLVAdapter extends BasePinLeiAdapter<String> {

    public FenleiPinleiChildLVAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String s = list.get(position);
        TextView tv=new TextView(context);
        tv.setText(s);
        tv.setTextSize(20);
        return tv;
    }
}
