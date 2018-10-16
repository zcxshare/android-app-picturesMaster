package com.scanmaster.commonlibrary.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author:  zhouchaoxiang
 * date:    2018/4/19
 * explain:　联网管理类
 */

public class RetrofitManager<NetApi> {
    private static final long REPETITION_TIME = 500;
    private static final int sReadTimeout = 2;
    private static final int sWriteTimeout = 2;
    private static final int sConnectTimeout = 2;
    private static Class sNetApi;

    private final Retrofit retrofit;
    private static HttpUrl lastUrl;//最后一次的url
    private static long lastTime;
    private static final String mBaseUrl = "http://192.168.24.41:8081/";

    public NetApi getApiService() {
        return apiService;
    }

    private final NetApi apiService;

    public static RetrofitManager getInstance(Class netApi) {
        sNetApi = netApi;
        return Instance.instance;
    }

    private static class Instance {
        private static final RetrofitManager instance = new RetrofitManager();
    }

    private RetrofitManager() {
        this(getOkHttpClient());
    }

    private RetrofitManager(OkHttpClient client) {
        retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = (NetApi) ApiService(sNetApi);
    }

    public static Retrofit getNewRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private NetApi ApiService(Class<NetApi> netApi) {
        return retrofit.create(netApi);
    }

    public static OkHttpClient getOkHttpClient(Interceptor... addInterceptors) {
        return getOkHttpClient(sReadTimeout, sWriteTimeout, addInterceptors);
    }

    public static OkHttpClient getOkHttpClient(long readTimeout, long writeTimeout, Interceptor... addInterceptors) {
        HttpLogInterceptor interceptor = new HttpLogInterceptor(new HttpLogInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                if (!TextUtils.isEmpty(message) && !message.contains("users/token")) {
//                    FileManager.saveStringToHttpFile("\r\n" + DateUtils.format(System.currentTimeMillis(), Constant.Date.PATTERN_SECOND) + message);
                    Log.i("http", message);
                }
            }
        });

        interceptor.setLevel(HttpLogInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(sConnectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url();
                        if (url != null) {
                            long time = System.currentTimeMillis();
                            long difference = time - lastTime;
                            lastTime = time;
                            boolean equals = url.equals(lastUrl);
                            lastUrl = url;
                            boolean b = difference < REPETITION_TIME;
                            if (equals && b) {
//                                ESTLog.i("http", "intercept: 取消了请求时间:" + difference + "\r\nurl:" + url);
                                Call call = chain.call();
                                call.cancel();
                            }
                        }
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
//                        Request originalRequest = chain.request();
//                        String token = SPUtils.getString(Constant.TOKEN, null);
//                        String userId = SPUtils.getString(Constant.USER_ID, null);
//                        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(userId)) {
//                            return chain.proceed(originalRequest);
//                        }
//                        int versionCode = ESTAppUtils.getVersionCode();
//                        if ("POST".equals(originalRequest.method())) {
//                        originalRequest = originalRequest.newBuilder()
//                                .header("Authorization", token)
//                                .header("version", String.valueOf(versionCode))
//                                .header("userId", userId)
////                                .addHeader("Content-Type", "application/json; charset=UTF-8")
//                                .build();
////                        }
                        Request request = chain.request();
//                        Request.Builder requestBuilder = request.newBuilder();
//                        request = requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=GBK"), URLDecoder.decode(bodyToString(request.body()), "UTF-8"))).build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(interceptor);
        for (Interceptor addInterceptor : addInterceptors) {
            builder.addInterceptor(addInterceptor);
        }
        return builder
                .build();
    }

    public static <RESPONSE> Observable<RESPONSE> requestHandler(Observable<RESPONSE> response) {
        return response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
