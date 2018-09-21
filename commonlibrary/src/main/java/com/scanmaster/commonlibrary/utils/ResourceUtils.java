package com.scanmaster.commonlibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.fuyekeji.www.FYTApplication;

/**
 * resource工具
 */

public class ResourceUtils {
    private ResourceUtils(){}
    public static int getColor(@NonNull Context context, @ColorRes int id){
        return ContextCompat.getColor(context,id);
    }
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int id){
        return ContextCompat.getDrawable(context,id);
    }

    public static String getString(@NonNull Context context,@StringRes int id) {
        return context.getString(id);
    }
    public static String getString(@StringRes int id) {
        return FYTApplication.context.getString(id);
    }
    public static String getString(@NonNull Context context,@StringRes int id,Object... formatArgs) {
        return context.getString(id,formatArgs);
    }
    public static String[] getStringArray(@NonNull Context context,@ArrayRes int id) {
        return context.getResources().getStringArray(id);
    }
}
