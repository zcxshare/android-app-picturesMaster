package com.scanmaster.commonlibrary;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/21
 * explain: 
 */
public    class InitApplication extends Application   {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static List<Activity> activitys = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void addActivity(Activity activity) {
        activitys.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activitys.remove(activity);
    }

    public static void removeAllActivity() {
        for (Activity activity : activitys) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static Activity getTopActivity() {
        if (activitys.size() > 0) {
            return activitys.get(activitys.size() - 1);
        }
        return null;
    }

}
