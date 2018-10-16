package com.scanmaster.www.scanmaster.model.net;

import com.scanmaster.commonlibrary.net.NetObserver;
import com.scanmaster.commonlibrary.net.RetrofitManager;

import okhttp3.ResponseBody;

/**
 * author:  zhouchaoxiang
 * date:    2018/10/5
 * explain:
 */
public class NetServer {
    private static NetApi sApiService = (NetApi) RetrofitManager.getInstance(NetApi.class).getApiService();

    public static void login(String username, String password, NetObserver<ResponseBody> observer) {
        RetrofitManager.requestHandler(sApiService.login(username, password))
                .subscribe(observer);
    }

    public static void register(String username, String password, NetObserver<ResponseBody> observer) {
        RetrofitManager.requestHandler(sApiService.register(username, password))
                .subscribe(observer);
    }
}
