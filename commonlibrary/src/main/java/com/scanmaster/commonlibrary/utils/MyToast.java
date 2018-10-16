package com.scanmaster.commonlibrary.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.scanmaster.commonlibrary.InitApplication;
import com.scanmaster.commonlibrary.R;


/**
 * toast工具 by Administrator on 2018/4/18.
 */

public class MyToast {

    private static Toast mToast;

    public static void showToastShort(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    @SuppressLint("ShowToast")
    private static void showToast(CharSequence text, int duration) {
        if (null == mToast) {
            mToast = Toast.makeText(InitApplication.getContext(), text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

}
