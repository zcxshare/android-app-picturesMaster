package com.scanmaster.commonlibrary.net;
/**
 * author:  zhouchaoxiang
 * date:    2018/8/29
 * explain: 
 */
public  interface ProgressListener {
    void progress(long bytesWritten, long contentLength, boolean done);
}
