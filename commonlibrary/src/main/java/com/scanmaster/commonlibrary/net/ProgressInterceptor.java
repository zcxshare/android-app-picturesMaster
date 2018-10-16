package com.scanmaster.commonlibrary.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 进度条interceptor 2018/4/25.
 */

public class ProgressInterceptor implements Interceptor {

    private final ProgressListener mProgressListener;

    public ProgressInterceptor(ProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        okhttp3.Response response = chain.proceed(request);
        if (null == mProgressListener) {
            return response;
        } else {
            ResponseBody body = response.body();
            return response.newBuilder()
                    .body(new ProgressResponseBody(body, mProgressListener))
                    .build();
        }
    }
}
