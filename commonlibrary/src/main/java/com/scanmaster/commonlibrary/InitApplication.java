package com.scanmaster.commonlibrary;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/21
 * explain: 
 */
public    class InitApplication extends Application   {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
