package com.example.saisai.yoho.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by saisai on 2016/8/27.
 */
public class BaseDialog extends Dialog {
    protected Activity activity;
    public BaseDialog(Context context) {
        super(context);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);//设置没有title
        activity= (Activity) context;

    }
}
