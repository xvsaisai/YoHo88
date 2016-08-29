package com.example.saisai.yoho.util;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.example.saisai.yoho.MyApplication;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by saisai on 2016/8/24.
 */
public class HttpUtils {
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(new Cache(MyApplication.app.getCacheDir(), 1024 * 8 * 1024)).build();
    public static android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
    private OnLoadDataListener loadDataListener;

    public HttpUtils loadData(String url, String reuqestBody) {
//        RequestBody body=new FormBody.Builder().add().add().add().build();
        FormBody.Builder builder = new FormBody.Builder();
//        builder.add();
//        name=asda&age=10
        // 之前 将httpClient
        // XUTILS
        // httpUtils.get
        if(!TextUtils.isEmpty(reuqestBody)){

            String[] split = reuqestBody.split("&");
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split("=");
                builder.add(split1[0], split1[1]);
            }
        }
        FormBody build = builder.build();
        Request request = new Request.Builder().url(url).post(build).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (loadDataListener != null) {
                            loadDataListener.loadFailed(e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                if (TextUtils.isEmpty(string)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loadDataListener != null) {
                                loadDataListener.loadFailed("数据为空");
                            }
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loadDataListener != null) {
//                                Log.e("tag",string+"-->");
                                loadDataListener.loadSuccess(string+"");
                            }
                        }
                    });
                }
            }
        });
        return this;
    }

    public interface OnLoadDataListener {
        void loadSuccess(String content);

        void loadFailed(String msg);
    }

    public void setOnLoadDataListener(OnLoadDataListener listener) {
        this.loadDataListener = listener;
    }
}
