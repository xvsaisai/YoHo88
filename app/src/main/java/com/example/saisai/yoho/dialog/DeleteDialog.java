package com.example.saisai.yoho.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.saisai.yoho.R;

/**
 * Created by saisai on 2016/8/27.
 */
public class DeleteDialog extends BaseDialog {

    private final TextView dialog_clear;
    private final TextView dialog_cancel;

    public DeleteDialog(Context context) {
        super(context);
        View inflate = View.inflate(context, R.layout.dialog_clear_searchhistroy, null);
        dialog_cancel = (TextView) inflate.findViewById(R.id.dialog_cancel);
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog_clear = (TextView) inflate.findViewById(R.id.dialog_clear);
        dialog_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAffirmClickListener!=null){
                    onAffirmClickListener.onOlick();
                }
            }
        });
        setContentView(inflate);

    }

    public interface OnAffirmClickListener{
        void onOlick();
    }
    OnAffirmClickListener onAffirmClickListener;
    public void setOnAffirmClickListener(OnAffirmClickListener onAffirmClickListener){
        this.onAffirmClickListener=onAffirmClickListener;
    }

}
