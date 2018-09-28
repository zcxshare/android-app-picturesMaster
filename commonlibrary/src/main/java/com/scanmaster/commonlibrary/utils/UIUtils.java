package com.scanmaster.commonlibrary.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain: 
 */
public    class UIUtils   {
    public static int dip2px(Context context,double dip) {
        //dp和px的转换关系比例值
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    public static int getColor(Context context,@ColorRes int id) {
        return ContextCompat.getColor(context,id);
    }

}
