package com.scanmaster.commonlibrary.net;

/**
 * author:  zhouchaoxiang
 * date:    2018/7/25
 * explain:
 */
public class HttpErrorBean {

    public HttpErrorBean(String message, int code, long timestamp) {
        this.message = message;
        this.code = code;
        this.timestamp = timestamp;
    }

    /**
     * message : 库存记录模块库存记录找不到
     * code : 74002
     * timestamp : 1532506506092
     */

    public String message;
    public int code;
    public long timestamp;
}
