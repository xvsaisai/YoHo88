package com.example.saisai.yoho.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.util.DimensUtils;

/**
 * Created by admin on 2016/8/26.
 */
public class LoadDialog extends BaseDialog {

    private final AnimationDrawable anim;

    public LoadDialog(Context context) {
        super(context);
        ImageView iv = new ImageView(getContext());
        iv.setBackgroundResource(R.drawable.load);
        setContentView(iv);
        getWindow().getAttributes().dimAmount = 0;
        getWindow().setLayout(DimensUtils.dp2px(100), DimensUtils.dp2px(50));
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        anim = (AnimationDrawable) iv.getBackground();
        anim.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        anim.stop();

    }
}
