package com.scanmaster.www.scanmaster.model.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * author:  zhouchaoxiang
 * date:    2018/4/19
 * explain:联网接口
 */
public interface NetApi {
    /**
     * 登录
     *
     * @param username        帐号
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("/login")
    Observable<ResponseBody> login(@Field("username") String username,@Field("password") String password);

    /**
     * 注册
     *
     * @param username        帐号
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("/register")
    Observable<ResponseBody> register(@Field("username") String username,@Field("password") String password);

    /**
     * 下载文件
     *
     * @param url 下载地址
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

}
