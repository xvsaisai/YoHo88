package com.example.saisai.yoho.view.snapscrollview;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class McoyProductContentPage implements McoySnapPageLayout.McoySnapPage {

    private Context context;

    private ListView rootView = null;

    public McoyProductContentPage(Context context, View rootView) {
        this.context = context;
        this.rootView = (ListView) rootView;
        this.rootView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    isTop = true;
                } else {
                    isTop = false;
                }
            }
        });
    }

    @Override
    public View getRootView() {
        return rootView;
    }


    boolean isTop;

    @Override
    public boolean isAtTop() {
        return isTop;
    }

    @Override
    public boolean isAtBottom() {
        return false;
    }


}
